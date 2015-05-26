package com.github.jcooky.jaal.agent.bytecode.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DebugClassVisitor extends ClassVisitor {
  
    public DebugClassVisitor(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(final int version, final int access, final String name, final String signature,
            final String superName, final String[] interfaces) {

        System.out.println("visit " + version + ", " + access + ", " + name + ", " + signature + ", " + superName + ", "
                + interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitSource(final String source, final String debug) {
        System.out.println("visitSource " + source + " " + debug);
        super.visitSource(source, debug);
    }

    @Override
    public void visitOuterClass(final String owner, final String name, final String desc) {
        System.out.println("visitOuterClass " + owner + " " + name + " " + desc);
        super.visitOuterClass(owner, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(final Attribute attr) {
        System.out.println("visitAttribute " + attr);
        super.visitAttribute(attr);
    }

    @Override
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        System.out.println("visitInnerClass " + name + " " + outerName + " " + innerName + " " + access);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature,
            final Object value) {

        System.out.println("visitField " + access + " " + name + " " + desc + " " + signature + " " + value);

        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
            final String[] exceptions) {

        System.out.println("visitMethod " + access + " " + name + " " + desc + " " + signature + " " + exceptions);

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        System.out.println("visitEnd");
        super.visitEnd();
    }
}
