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

import com.github.jcooky.jaal.org.objectweb.asm.AnnotationVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.Attribute;
import com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.FieldVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.MethodVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;

/**
 * <p>DebugClassVisitor class.</p>
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class DebugClassVisitor extends ClassVisitor {
  
    /**
     * <p>Constructor for DebugClassVisitor.</p>
     *
     * @param cv a {@link com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor} object.
     */
    public DebugClassVisitor(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    /** {@inheritDoc} */
    @Override
    public void visit(final int version, final int access, final String name, final String signature,
            final String superName, final String[] interfaces) {

        System.out.println("visit " + version + ", " + access + ", " + name + ", " + signature + ", " + superName + ", "
                + interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /** {@inheritDoc} */
    @Override
    public void visitSource(final String source, final String debug) {
        System.out.println("visitSource " + source + " " + debug);
        super.visitSource(source, debug);
    }

    /** {@inheritDoc} */
    @Override
    public void visitOuterClass(final String owner, final String name, final String desc) {
        System.out.println("visitOuterClass " + owner + " " + name + " " + desc);
        super.visitOuterClass(owner, name, desc);
    }

    /** {@inheritDoc} */
    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    /** {@inheritDoc} */
    @Override
    public void visitAttribute(final Attribute attr) {
        System.out.println("visitAttribute " + attr);
        super.visitAttribute(attr);
    }

    /** {@inheritDoc} */
    @Override
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        System.out.println("visitInnerClass " + name + " " + outerName + " " + innerName + " " + access);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    /** {@inheritDoc} */
    @Override
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature,
            final Object value) {

        System.out.println("visitField " + access + " " + name + " " + desc + " " + signature + " " + value);

        return super.visitField(access, name, desc, signature, value);
    }

    /** {@inheritDoc} */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
            final String[] exceptions) {

        System.out.println("visitMethod " + access + " " + name + " " + desc + " " + signature + " " + exceptions);

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    /** {@inheritDoc} */
    @Override
    public void visitEnd() {
        System.out.println("visitEnd");
        super.visitEnd();
    }
}
