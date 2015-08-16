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

import com.github.jcooky.jaal.agent.bytecode.InjectorException;
import com.github.jcooky.jaal.agent.bytecode.Modifier;
import com.github.jcooky.jaal.util.VersionUtils;
import com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy;
import com.github.jcooky.jaal.org.objectweb.asm.*;
import com.github.jcooky.jaal.org.objectweb.asm.commons.GeneratorAdapter;
import com.github.jcooky.jaal.org.objectweb.asm.commons.Method;
import com.github.jcooky.jaal.org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>InjectClassVisitor class.</p>
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class InjectClassVisitor extends ClassVisitor implements Constants, Opcodes {

  private final ProxyInjectorStrategy injectorStrategy;
  //    private static final Logger LOG = Logger.getLogger(InjectClassVisitor.class);
  private int handlerCount;
  private Type classType;
  private GeneratorAdapter initializer;
  private Method handlerMethod = Method.getMethod(InvocationHandler.CLASS.getName() + " getInvocationHandler()");

  /**
   * <p>Constructor for InjectClassVisitor.</p>
   *
   * @param injectorStrategy a {@link com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy} object.
   * @param visitor a {@link com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor} object.
   */
  public InjectClassVisitor(ProxyInjectorStrategy injectorStrategy, ClassVisitor visitor) {
    super(Opcodes.ASM5, visitor);
    this.injectorStrategy = injectorStrategy;
  }

  /** {@inheritDoc} */
  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    handlerCount = 0;
    classType = Type.getType("L" + name.replace('.', '/') + ";");

    super.visit(version, access, name, signature, superName, interfaces);

    initializer = addInitializer();
  }

  private GeneratorAdapter addInitializer() {
    int access = Modifier.PRIVATE_STATIC;
    String descriptor = "()V";
    MethodVisitor initMethodVisitor = super.visitMethod(access, initializeName, descriptor, null, null);
    GeneratorAdapter initializer = new GeneratorAdapter(initMethodVisitor, access, initializeName, descriptor);

    initMethodVisitor.visitCode();

    return initializer;
  }

  /** {@inheritDoc} */
  @Override
  public void visitEnd() {
    initializer.returnValue();
    initializer.endMethod();
    addCommentField();
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


  private void addMethodHandlerField(String fieldName, String methodName, String descriptor) {
    Type factoryType = Type.getType("L" + injectorStrategy.getFactoryClass().replace('.', '/') + ";");

    FieldVisitor handler = super.visitField(Modifier.PRIVATE_STATIC_FINAL, fieldName,
        InvocationHandler.TYPE.getDescriptor(), null, null);
    handler.visitEnd();
//      initializer.push(classType.getClassName());
//      initializer.push(methodName);
//      initializer.push(descriptor);
    initializer.invokeStatic(factoryType, handlerMethod);
    initializer.putStatic(classType, fieldName, InvocationHandler.TYPE);
  }

  /**
   * <p>pushThis.</p>
   *
   * @param adapter a {@link com.github.jcooky.jaal.org.objectweb.asm.commons.GeneratorAdapter} object.
   * @param isStatic a boolean.
   */
  public void pushThis(GeneratorAdapter adapter, boolean isStatic) {
    if (isStatic) {
      adapter.push("test");
    } else {
      adapter.loadThis();
    }
  }

  /** {@inheritDoc} */
  @Override
  public MethodVisitor visitMethod(final int access, final String name,
                                   final String descriptor, final String signature,
                                   final String[] exceptions) {
    if (name.equals("<clinit>") || name.equals("<init>")
        || Modifier.isAbstract(access) || Modifier.isNative(access) || Modifier.isSynthetic(access)) {
      // LOG.debug("skipping " + name);
      return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    int index = (handlerCount++);
    final String handlerFieldName = HANDLER_PREFIX + index;
    final String targetMethodName = name + METHOD_POSTFIX;

    MethodVisitor parent = super.visitMethod(Modifier.makePublic(access), targetMethodName, descriptor, signature, exceptions);
    MethodVisitor child = new MethodVisitor(Opcodes.ASM5, parent) {
      class Annotation extends AnnotationNode {

        private final boolean visible;

        public Annotation(String desc, boolean visible) {
          super(Opcodes.ASM5, desc);
          this.visible = visible;
        }

      }

      private final List<Annotation> annotations = new ArrayList<Annotation>();

      @Override
      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Annotation annotation = new Annotation(desc, visible);
        annotations.add(annotation);
        return annotation;
      }

      @Override
      public void visitEnd() {
        super.visitEnd();

        addMethodHandlerField(handlerFieldName, name, descriptor);

        // -- [ Proxy Method ] --
        {
          Method method = new Method(name, descriptor);
          MethodVisitor mv = InjectClassVisitor.super.visitMethod(access, name, descriptor, signature, exceptions);
          ProxyMethodVisitor visitor = new ProxyMethodVisitor(access, method, mv, classType, targetMethodName,
              handlerFieldName);

          for (Annotation annotation : annotations) {
            AnnotationVisitor v = visitor.visitAnnotation(annotation.desc, annotation.visible);
            annotation.accept(v);
          }
          visitor.visitCode();    // Calls visitEnd() via endMethod()
        }
      }
    };

    // -- [ Target Method ] --
    return child;
  }
}
