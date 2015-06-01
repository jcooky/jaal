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

import com.github.jcooky.jaal.org.objectweb.asm.commons.*;
import com.github.jcooky.jaal.org.objectweb.asm.*;

interface Constants {

  /** Constant <code>METHOD_POSTFIX="_$jaal"</code> */
  public static final String METHOD_POSTFIX = "_$jaal";
  /** Constant <code>HANDLER_PREFIX="jaal$"</code> */
  public static final String HANDLER_PREFIX = "jaal$";
  /** Constant <code>COMMENT_FIELD_NAME="$jaal_was_here"</code> */
  public static final String COMMENT_FIELD_NAME = "$jaal_was_here";

  /** Constant <code>initializeName="$clinit + METHOD_POSTFIX"</code> */
  public static String initializeName = "$clinit" + METHOD_POSTFIX;
  /** Constant <code>initialize</code> */
  public static Method initialize = Method.getMethod("void " + initializeName + "()");
  /** Constant <code>classInitName="<clinit>"</code> */
  public static String classInitName = "<clinit>";
  /** Constant <code>classInitDesc="()V"</code> */
  public static String classInitDesc = "()V";
  /** Constant <code>classInit</code> */
  public static Method classInit = Method.getMethod("void " + classInitName + "()");

  public interface Clazz {
    public static Class<?> CLASS = Class.class;
    public static Type TYPE = Type.getType(CLASS);
    public static Method getMethod = Method.getMethod(java.lang.reflect.Method.class.getName() + " getMethod(String, Class[])");
    public static Method forName = Method.getMethod(Class.class.getName() + " forName(String)");
    public static Method getClassLoader = Method.getMethod(ClassLoader.class.getName() + " getClassLoader()");
  }

  public interface Object {
    public static Class<?> CLASS = java.lang.Object.class;
    public static Type TYPE = Type.getType(CLASS);
    public static Method getClass = Method.getMethod(Class.class.getName() + " getClass()");
  }

  public interface Throwable {

    public static Class<?> CLASS = java.lang.Throwable.class;
    public static Type TYPE = Type.getType(CLASS);
  }

  public interface InvocationHandler {

    public static Class<?> CLASS = com.github.jcooky.jaal.common.invocation.InvocationHandler.class;
    public static Type TYPE = Type.getType(CLASS);
    public static Method invoke = Method.getMethod("Object invoke(Object, java.lang.reflect.Method, Object[]) throws Throwable");
  }

  public interface InvocationTargetException {
    public static Class<?> CLASS = java.lang.reflect.InvocationTargetException.class;
    public static Type TYPE = Type.getType(CLASS);
    public static Method getCause = Method.getMethod(Throwable.CLASS.getName() + " getCause()");
  }

  public interface XFactory {
    public static Method getInvocationHandler = Method.getMethod(InvocationHandler.CLASS.getName() + " getInvocationHandler()");
  }
}
