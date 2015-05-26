package com.github.jcooky.jaal.agent.bytecode.asm;

/*
 * #%L
 * jaal-agent
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

import com.github.jcooky.jaal.agent.criteria.MethodCriteria;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class MethodCriteriaClassVisitor extends ClassVisitor {

  //    private static final Logger LOG = Logger.getLogger(MethodCriteriaClassVisitor.class);
  private final ClassVisitor bypass, injector;
  private MethodCriteria criteria;
  private String className;

  public MethodCriteriaClassVisitor(ClassVisitor injector, ClassVisitor bypass) {
    super(Opcodes.ASM5, null);
    this.bypass = bypass;
    this.injector = injector;
  }

  public void setCriteria(MethodCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

//    try {
//      ClassReader classReader = new ClassReader(IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(interfaces[0] + ".class")));
//      ClassNode classNode = new ClassNode();
//      classReader.accept(classNode, 0);
//
//      System.out.println(classNode);
//
//    } catch (Throwable e) {
//      e.printStackTrace();
//    }
//
//    String []ifaces = new String[interfaces.length];
//    for (int i = 0; i < interfaces.length; ++i) {
//      ifaces[i] = interfaces[i].replace('/', '.');
//    }
    className = name.replace('/', '.');
    if (criteria.isMatch(className)) {
      cv = injector;
//      LOG.debug("not filtering class " + className);
    } else {
      cv = bypass;
    }

    super.visit(version, access, name, signature, superName, interfaces);
  }

  /**
   * when this method is called MethodCriteria.isMatch(className, methodName,
   * ...) is checked to see if any injection is necessary for this method. If
   * not, then the bypass visitor is used. Otherwise, the default visitor is
   * used (which was set in the class visit method).
   */
  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    if (criteria.isMatch(className, name, desc, access)) {
      return super.visitMethod(access, name, desc, signature, exceptions);
    } else {
      return bypass.visitMethod(access, name, desc, signature, exceptions);
    }
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
    cv = null;
    className = null;
  }
}
