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

import com.github.jcooky.jaal.org.objectweb.asm.MethodVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;

/**
 * All this visitor does is add a single instruction to the start of the static
 * intitializer to call the JRat initializer method.
 *
 * @author jeff@shiftone.org (Jeff Drost)
 * @version $Id: $Id
 */
public class ClassInitMethodVisitor extends MethodVisitor implements Constants {

//    private static final Logger LOG = Logger.getLogger(ClassInitMethodVisitor.class);
    private final String className;

    /**
     * <p>Constructor for ClassInitMethodVisitor.</p>
     *
     * @param className a {@link java.lang.String} object.
     * @param mv a {@link com.github.jcooky.jaal.org.objectweb.asm.MethodVisitor} object.
     */
    public ClassInitMethodVisitor(String className, MethodVisitor mv) {

        super(Opcodes.ASM5, mv);

        this.className = className;
    }

    /** {@inheritDoc} */
    @Override
    public void visitCode() {
        super.visitCode();
        super.visitMethodInsn(Opcodes.INVOKESTATIC, className, initializeName, "()V", false);
    }
}
