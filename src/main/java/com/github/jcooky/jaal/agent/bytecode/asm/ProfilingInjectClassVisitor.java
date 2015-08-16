package com.github.jcooky.jaal.agent.bytecode.asm;

/*
 * #%L
 * jaal
 * %%
 * Copyright (C) 2015 JCooky
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.github.jcooky.jaal.agent.ThreadStateFactory;
import com.github.jcooky.jaal.agent.bytecode.InjectorException;
import com.github.jcooky.jaal.agent.bytecode.Modifier;
import com.github.jcooky.jaal.agent.config.ProfilingInjectorStrategy;
import com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy;
import com.github.jcooky.jaal.org.objectweb.asm.*;
import com.github.jcooky.jaal.org.objectweb.asm.commons.GeneratorAdapter;
import com.github.jcooky.jaal.org.objectweb.asm.commons.InstructionAdapter;
import com.github.jcooky.jaal.org.objectweb.asm.commons.Method;
import com.github.jcooky.jaal.org.objectweb.asm.tree.*;
import com.github.jcooky.jaal.util.VersionUtils;

import java.util.*;

/**
 * <p>ProfilingInjectClassVisitor class.</p>
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class ProfilingInjectClassVisitor extends ClassVisitor implements Constants, Opcodes {

  private final ProfilingInjectorStrategy injectorStrategy;
  private Type classType;
  private String superName;

  /**
   * <p>Constructor for InjectClassVisitor.</p>
   *
   * @param injectorStrategy a {@link com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy} object.
   * @param visitor          a {@link com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor} object.
   */
  public ProfilingInjectClassVisitor(ProfilingInjectorStrategy injectorStrategy, ClassVisitor visitor) {
    super(Opcodes.ASM5, visitor);
    this.injectorStrategy = injectorStrategy;
  }

  /** {@inheritDoc} */
  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    classType = Type.getType("L" + name.replace('.', '/') + ";");
    this.superName = superName;

    super.visit(version, access, name, signature, superName, interfaces);
  }

  /** {@inheritDoc} */
  @Override
  public void visitEnd() {
//    addCommentField();
    super.visitEnd();
  }

  private void addCommentField() {
    FieldVisitor commentField = super.visitField(Modifier.PRIVATE_STATIC_FINAL,
        COMMENT_FIELD_NAME, "Ljava/lang/String;", null,
        "Class enhanced on " + new Date() + " w/ version JAal v"
            + VersionUtils.getBuiltOn() + " built on " + VersionUtils.getBuiltOn());
    commentField.visitEnd();
  }

  /** {@inheritDoc} */
  public FieldVisitor visitField(final int access, final String name, final String desc, final String signature,
                                 final Object value) {
    if (name.equals(COMMENT_FIELD_NAME)) {
      throw new InjectorException("this class was previously injected by JAal");
    }
    return super.visitField(access, name, desc, signature, value);
  }

  /** {@inheritDoc} */
  @Override
  public MethodVisitor visitMethod(final int access, final String name,
                                   final String descriptor, final String signature,
                                   final String[] exceptions) {
    if (Modifier.isAbstract(access) || Modifier.isNative(access) || Modifier.isSynthetic(access)) {
      // LOG.debug("skipping " + name);
      return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    return new MethodVisitor(ASM5, new GeneratorAdapter(ASM5, new Method(name, descriptor), super.visitMethod(access, name, descriptor, signature, exceptions))) {

      GeneratorAdapter insts = (GeneratorAdapter) mv;

      Label beginTry = insts.newLabel(), endTry = insts.newLabel(), beginCatch = insts.newLabel();
      int state;
      int startTime;
      int threadStateFactory;

      public void visitCode() {
        super.visitCode();

        insts.visitTryCatchBlock(beginTry, endTry, beginCatch, Throwable.TYPE.getInternalName());

        // threadStateFactory = new ThreadStateFactory(profilerFactoryClass)
        threadStateFactory = insts.newLocal(ThreadStateFactory.TYPE);
        insts.newInstance(ThreadStateFactory.TYPE);
        insts.dup();
        insts.push(injectorStrategy.getProfilerFactoryClass());
        insts.invokeConstructor(ThreadStateFactory.TYPE, ThreadStateFactory.constructor);
        insts.storeLocal(threadStateFactory, ThreadStateFactory.TYPE);

        // ThreadState state = $fieldName$.get()
        state = insts.newLocal(ThreadState.TYPE);
        insts.loadLocal(threadStateFactory, ThreadStateFactory.TYPE);
        insts.invokeVirtual(ThreadStateFactory.TYPE, ThreadStateFactory.get);
        insts.checkCast(ThreadState.TYPE);
        insts.storeLocal(state, ThreadState.TYPE);

        startTime = insts.newLocal(Type.LONG_TYPE);
        insts.loadLocal(state);
        insts.push(classType.getClassName());
        insts.push(access);
        insts.push(name);
        insts.push(descriptor);
        insts.invokeVirtual(ThreadState.TYPE, ThreadState.begin);
        insts.storeLocal(startTime);

        insts.mark(beginTry);
      }

      @Override
      public void visitInsn(int opcode) {
        if (opcode == ARETURN || opcode == DRETURN || opcode == IRETURN || opcode == FRETURN || opcode == LRETURN || opcode == RETURN) {
          insts.loadLocal(state);
          insts.push(classType.getClassName());
          insts.push(access);
          insts.push(name);
          insts.push(descriptor);
          insts.loadLocal(startTime);
          insts.visitInsn(ACONST_NULL);
          insts.invokeVirtual(ThreadState.TYPE, ThreadState.end);
        }

        super.visitInsn(opcode);
      }

      @Override
      public void visitMaxs(int maxStack, int maxLocals) {

        insts.mark(endTry);
        insts.mark(beginCatch);

        int exception = insts.newLocal(Throwable.TYPE);
        insts.storeLocal(exception);

        insts.loadLocal(state);
        insts.push(classType.getClassName());
        insts.push(access);
        insts.push(name);
        insts.push(descriptor);
        insts.loadLocal(startTime);
        insts.loadLocal(exception);
        insts.invokeVirtual(ThreadState.TYPE, ThreadState.end);

        insts.loadLocal(exception);
        insts.throwException();

        super.visitMaxs(maxStack, maxLocals);
      }

    };
  }
}
