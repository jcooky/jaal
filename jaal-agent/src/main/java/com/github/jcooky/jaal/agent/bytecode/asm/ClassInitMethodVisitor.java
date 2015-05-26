package com.github.jcooky.jaal.agent.bytecode.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * All this visitor does is add a single instruction to the start of the static
 * intitializer to call the JRat initializer method.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class ClassInitMethodVisitor extends MethodVisitor implements Constants {

//    private static final Logger LOG = Logger.getLogger(ClassInitMethodVisitor.class);
    private final String className;

    public ClassInitMethodVisitor(String className, MethodVisitor mv) {

        super(Opcodes.ASM5, mv);

        this.className = className;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        super.visitMethodInsn(Opcodes.INVOKESTATIC, className, initializeName, "()V", false);
    }
}
