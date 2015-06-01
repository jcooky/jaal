package com.github.jcooky.jaal.agent.bytecode;

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

import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;

/**
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class Modifier extends java.lang.reflect.Modifier implements Opcodes {

    public static final int PUBLIC_STATIC = PUBLIC | STATIC;
    public static final int PUBLIC_STATIC_FINAL = PUBLIC_STATIC | FINAL;
    public static final int PRIVATE_STATIC = PRIVATE | STATIC;
    public static final int PRIVATE_STATIC_FINAL = PRIVATE_STATIC | FINAL;
    private static final int NO_PUBLIC_PRIVATE_PROTECTED = ~(PRIVATE | PUBLIC | PROTECTED);

    public static boolean isSynthetic(int modifier) {
        return (modifier & ACC_SYNTHETIC) != 0;
    }

    public static int makePrivate(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PRIVATE;
    }

    public static int makePublic(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PUBLIC;
    }

    public static int makeProtected(int modifier) {
        return (modifier & NO_PUBLIC_PRIVATE_PROTECTED) | PROTECTED;
    }

    public static int makeNonNative(int modifier) {
        return (modifier & ~(NATIVE));
    }
}
