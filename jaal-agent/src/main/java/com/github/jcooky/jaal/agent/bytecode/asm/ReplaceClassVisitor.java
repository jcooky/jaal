package com.github.jcooky.jaal.agent.bytecode.asm;

import com.github.jcooky.jaal.agent.config.ReplaceInjectorStrategy;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
