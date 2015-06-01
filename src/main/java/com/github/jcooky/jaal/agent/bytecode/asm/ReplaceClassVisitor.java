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

import com.github.jcooky.jaal.agent.config.ReplaceInjectorStrategy;
import com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.MethodVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;

/**
 * Created by JCooky on 15. 3. 25..
 */
public class ReplaceClassVisitor extends ClassVisitor implements Opcodes {
  private final String targetClass;
  private final String replacementClass;

  public ReplaceClassVisitor(ReplaceInjectorStrategy injectorStrategy, ClassVisitor visitor) {
    super(ASM5, visitor);
    this.targetClass = injectorStrategy.getTargetClass().replace('.', '/');
    this.replacementClass = injectorStrategy.getReplacementClass().replace('.', '/');
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
      private boolean replaced = false;
      @Override
      public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (replaced && !itf
            && INVOKESPECIAL == opcode && "<init>".equals(name) && ReplaceClassVisitor.this.targetClass.equals(owner)) {
          owner = ReplaceClassVisitor.this.replacementClass;
          replaced = false;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
      }

      @Override
      public void visitTypeInsn(int opcode, String type) {
        if (NEW == opcode
            && targetClass.equals(type)) {
          replaced = true;
          type = replacementClass;
        }
        super.visitTypeInsn(opcode, type);
      }
    };
  }
}
