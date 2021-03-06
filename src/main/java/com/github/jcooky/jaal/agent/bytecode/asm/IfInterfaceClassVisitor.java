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
import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;

/**
 * <p>IfInterfaceClassVisitor class.</p>
 *
 * @author jeff@shiftone.org (Jeff Drost)
 * @version $Id: $Id
 */
public class IfInterfaceClassVisitor extends ClassVisitor {

//    private static final Logger LOG = Logger.getLogger(IfInterfaceClassVisitor.class);
    private final ClassVisitor interfaceClassVisitor;
    private final ClassVisitor concreteClassVisitor;

    /**
     * <p>Constructor for IfInterfaceClassVisitor.</p>
     *
     * @param interfaceClassVisitor a {@link com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor} object.
     * @param concreteClassVisitor a {@link com.github.jcooky.jaal.org.objectweb.asm.ClassVisitor} object.
     */
    public IfInterfaceClassVisitor(ClassVisitor interfaceClassVisitor, ClassVisitor concreteClassVisitor) {
        super(Opcodes.ASM5, null);
        this.interfaceClassVisitor = interfaceClassVisitor;
        this.concreteClassVisitor = concreteClassVisitor;
    }

    /** {@inheritDoc} */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (Modifier.isInterface(access)) {
            cv = interfaceClassVisitor;
            // LOG.debug("is interface " + name);
        } else {
            cv = concreteClassVisitor;
            // LOG.debug("is NOT interface " + name);
        }

        super.visit(version, access, name, signature, superName, interfaces);
    }

    /** {@inheritDoc} */
    @Override
    public void visitEnd() {
        super.visitEnd();
        cv = null;
    }
}
