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

import com.github.jcooky.jaal.agent.bytecode.Modifier;
import com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.MethodVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;

/**
 * <p>ClassInitClassVisitor class.</p>
 *
 * @author jeff@shiftone.org (Jeff Drost)
 * @version $Id: $Id
 */
public class ClassInitClassVisitor extends ClassVisitor implements Constants, Opcodes {

//    private static final Logger LOG = Logger.getLogger(ClassInitClassVisitor.class);
    private boolean clinitVisited = false;
    private String className;

    /**
     * <p>Constructor for ClassInitClassVisitor.</p>
     *
     * @param cv a {@link com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor} object.
     */
    public ClassInitClassVisitor(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    /** {@inheritDoc} */
    @Override
    public void visit(final int version, final int access, final String name, final String signature,
            final String superName, final String[] interfaces) {

        clinitVisited = false;
        className = name;

        super.visit(version, access, name, signature, superName, interfaces);
    }

    /** {@inheritDoc} */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
            final String[] exceptions) {

        MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);

        if (classInitName.equals(name)) {
            clinitVisited = true;
            visitor = new ClassInitMethodVisitor(className, visitor);
        }

        return visitor;
    }

    /** {@inheritDoc} */
    @Override
    public void visitEnd() {

        if (!clinitVisited) {

            // LOG.info("adding new <clinit> method to " + className);
            MethodVisitor clinit = visitMethod(Modifier.PRIVATE_STATIC, classInitName, classInitDesc, null, null);

            clinit.visitCode();
            clinit.visitInsn(RETURN);
            clinit.visitEnd();
        } else {

            // LOG.info("class already had <clinit> method " + className);
        }

        super.visitEnd();
    }
}
