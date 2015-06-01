package com.github.jcooky.jaal.agent.bytecode;

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

import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;

/**
 * <p>Modifier class.</p>
 *
 * @author jeff@shiftone.org (Jeff Drost)
 * @version $Id: $Id
 */
public class Modifier extends java.lang.reflect.Modifier implements Opcodes {

    /** Constant <code>PUBLIC_STATIC=PUBLIC | STATIC</code> */
    public static final int PUBLIC_STATIC = PUBLIC | STATIC;
    /** Constant <code>PUBLIC_STATIC_FINAL=PUBLIC_STATIC | FINAL</code> */
    public static final int PUBLIC_STATIC_FINAL = PUBLIC_STATIC | FINAL;
    /** Constant <code>PRIVATE_STATIC=PRIVATE | STATIC</code> */
    public static final int PRIVATE_STATIC = PRIVATE | STATIC;
    /** Constant <code>PRIVATE_STATIC_FINAL=PRIVATE_STATIC | FINAL</code> */
    public static final int PRIVATE_STATIC_FINAL = PRIVATE_STATIC | FINAL;
    private static final int NO_PUBLIC_PRIVATE_PROTECTED = ~(PRIVATE | PUBLIC | PROTECTED);

    /** {@inheritDoc} */
    public static boolean isSynthetic(int modifier) {
        return (modifier & ACC_SYNTHETIC) != 0;
    }

    /**
     * <p>makePrivate.</p>
     *
     * @param modifier a int.
     * @return a int.
     */
    public static int makePrivate(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PRIVATE;
    }

    /**
     * <p>makePublic.</p>
     *
     * @param modifier a int.
     * @return a int.
     */
    public static int makePublic(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PUBLIC;
    }

    /**
     * <p>makeProtected.</p>
     *
     * @param modifier a int.
     * @return a int.
     */
    public static int makeProtected(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PROTECTED;
    }

    /**
     * <p>makeNonNative.</p>
     *
     * @param modifier a int.
     * @return a int.
     */
    public static int makeNonNative(int modifier) {
        return (modifier & ~(NATIVE));
    }
}
