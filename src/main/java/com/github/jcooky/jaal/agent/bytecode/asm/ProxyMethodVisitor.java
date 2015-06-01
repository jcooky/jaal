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
import com.github.jcooky.jaal.org.objectweb.asm.Label;
import com.github.jcooky.jaal.org.objectweb.asm.MethodVisitor;
import com.github.jcooky.jaal.org.objectweb.asm.Opcodes;
import com.github.jcooky.jaal.org.objectweb.asm.Type;
import com.github.jcooky.jaal.org.objectweb.asm.commons.GeneratorAdapter;
import com.github.jcooky.jaal.org.objectweb.asm.commons.Method;

import java.io.PrintStream;

/**
 * <p>ProxyMethodVisitor class.</p>
 *
 * @author jeff@shiftone.org (Jeff Drost)
 * @version $Id: $Id
 */
public class ProxyMethodVisitor extends GeneratorAdapter implements Constants, Opcodes {

  //    private static final Logger LOG = Logger.getLogger(ProxyMethodVisitor.class);
  private final boolean isStatic;
  private final boolean isVoidReturn;
  private final Type classType;
  private final String handlerFieldName;
  private final String targetMethodName;
  private final Method method;

  /**
   * <p>Constructor for ProxyMethodVisitor.</p>
   *
   * @param access a int.
   * @param method a {@link com.github.jcooky.jaal.org.objectweb.asm.commons.Method} object.
   * @param mv a {@link com.github.jcooky.jaal.org.objectweb.asm.MethodVisitor} object.
   * @param classType a {@link com.github.jcooky.jaal.org.objectweb.asm.Type} object.
   * @param targetMethodName a {@link java.lang.String} object.
   * @param handlerFieldName a {@link java.lang.String} object.
   */
  public ProxyMethodVisitor(int access, Method method, MethodVisitor mv, Type classType, String targetMethodName,
                            String handlerFieldName) {

    super(Opcodes.ASM5, mv, access, null, method.getDescriptor());

    this.method = method;
    this.isStatic = Modifier.isStatic(access);
    this.isVoidReturn = Type.VOID_TYPE.equals(method.getReturnType());
    this.classType = classType;
    this.targetMethodName = targetMethodName;
    this.handlerFieldName = handlerFieldName;
  }

//    private void invoke() {
//        if (isStatic) {
//            loadArgs();    // push the args on the stack
//            invokeStatic(classType, new Method(targetMethodName, method.getDescriptor()));
//        } else {
//            loadThis();    // push this on the stack (for non-static methods)
//            loadArgs();    // push the args on the stack
//            invokeVirtual(classType, new Method(targetMethodName, method.getDescriptor()));
//        }
//    }

  private void println(String str) {
    getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
    push(str);
    invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println(Object)"));
  }

  private void println(int local) {
    getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
    loadLocal(local);
    invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println(Object)"));
  }

  /**
   * <p>visitCode.</p>
   */
  public void visitCode() {
    super.visitCode();

    Label beginTry = newLabel(), endTry = newLabel(),
        beginCatch1 = newLabel(), beginCatch2 = newLabel(), beginCatch3 = newLabel();

    visitTryCatchBlock(beginTry, endTry, beginCatch1, Type.getType(NoSuchMethodException.class).getInternalName());
    visitTryCatchBlock(beginTry, endTry, beginCatch2, Type.getType(ClassNotFoundException.class).getInternalName());
    visitTryCatchBlock(beginTry, endTry, beginCatch3, InvocationTargetException.TYPE.getInternalName());

    mark(beginTry); // try {

    // Class cls = this.getClass();
    int cls = newLocal(Clazz.TYPE);
    loadThis();
    invokeVirtual(Object.TYPE, Object.getClass);
    storeLocal(cls);

//    this.println(cls);

    // Method method = cls.getMethod("method_$jexi", [args]);
    int method = newLocal(Type.getType(java.lang.reflect.Method.class));
    loadLocal(cls);
    push(targetMethodName);

    push(this.method.getArgumentTypes().length);
    newArray(Clazz.TYPE);
    for (int i = 0; i < this.method.getArgumentTypes().length; i++) {
      dup();
      push(i);
      push(this.method.getArgumentTypes()[i].getInternalName().replace('/', '.'));
      invokeStatic(Clazz.TYPE, Clazz.forName);
      box(Clazz.TYPE);
      arrayStore(Clazz.TYPE);
    }

    invokeVirtual(Clazz.TYPE, Clazz.getMethod);
    storeLocal(method);

//    this.println("Test");

    // Object result = invocationHandler.invoke(this, method, null);
    int result = newLocal(Object.TYPE);
    getStatic(classType, handlerFieldName, InvocationHandler.TYPE);
    loadThis();
    loadLocal(method);
    loadArgArray();
    invokeInterface(InvocationHandler.TYPE, InvocationHandler.invoke);
    storeLocal(result);

    // return $ReturnType.class.cast($result);
    if (!this.isVoidReturn) {
      loadLocal(result);
      checkCast(this.method.getReturnType());
    }
    returnValue();

    mark(endTry); // } catch (NoSuchMethodExcpetion e) {

    mark(beginCatch1);
    int e1 = newLocal(Type.getType(NoSuchMethodException.class));
    storeLocal(e1);

    // throw e1;
    loadLocal(e1);
    throwException();

    mark(beginCatch2);
    int e2 = newLocal(Type.getType(ClassNotFoundException.class));
    storeLocal(e2);

    // throw e2;
    loadLocal(e2);
    throwException();

    mark(beginCatch3);
    int e3 = newLocal(InvocationTargetException.TYPE);
    storeLocal(e3);

    int throwable = newLocal(Throwable.TYPE);
    // Throwable throwable = e3.getCause()
    loadLocal(e3);
    invokeVirtual(InvocationTargetException.TYPE, InvocationTargetException.getCause);
    storeLocal(throwable);

    // throw throwable;
    loadLocal(throwable);
    throwException();

    endMethod();

  }

//
//    @Override
//    public void visitCode() {
//        super.visitCode();
//
//        Label monitor = newLabel();
//        int state = newLocal(ThreadState.TYPE);
//
//        // ThreadState state = ThreadState.getInstance();
//        invokeStatic(ThreadState.TYPE, ThreadState.getInstance);
//        storeLocal(state);
//
//        // if (!state.isInHandler) goto monitor
//        if (false) {
//            loadLocal(state);
//            invokeVirtual(ThreadState.TYPE, ThreadState.isInHandler);
//
//            ifZCmp(EQ, monitor);  //  not(isInHandler) == isInHandler is false == zero
//
//            invoke();
//            returnValue();
//        }
//
//        // -------------------------------------------------------------------------------
//        mark(monitor);
//
//        // long startTime = state.begin(METHOD_HANDLER);
//        loadLocal(state);
//        getStatic(classType, handlerFieldName, InvocationHandler.TYPE);
//        invokeVirtual(ThreadState.TYPE, ThreadState.begin);
//
//        int startTime = newLocal(Type.LONG_TYPE);
//        storeLocal(startTime, Type.LONG_TYPE);
//
//        Label beginTry = new Label();
//        Label endTry = new Label();
//        Label beginCatch = new Label();
//        visitTryCatchBlock(beginTry, endTry, beginCatch, Throwable.TYPE.getInternalName());
//
//        mark(beginTry);    // try {
//
//        invoke();
//
//        int result = -1;
//
//        if (!isVoidReturn) {
//            result = newLocal(method.getReturnType());
//            storeLocal(result);
//        }
//
//        // -------------------------------------------------------------------------------
//        //  state.end(METHOD_HANDLER, startTime, null);
//        loadLocal(state);
//        // end : param 1
//        getStatic(classType, handlerFieldName, InvocationHandler.TYPE);
//        // end : param 2
//        loadLocal(startTime);
//        // end : param 3
//        visitInsn(ACONST_NULL);
//        invokeVirtual(ThreadState.TYPE, ThreadState.end);
//
//        // -------------------------------------------------------------------------------
//        // return result;
//        if (!isVoidReturn) {
//            loadLocal(result);
//        }
//
//        returnValue();
//
//        mark(endTry);    // } catch (Throwable e) {
//        // this is the beginning of the catch block
//        mark(beginCatch);
//
//        // -------------------------------------------------------------------------------
//        // Throwable exception = e;
//        int exception = newLocal(Throwable.TYPE);
//
//        storeLocal(exception);
//
//        // -------------------------------------------------------------------------------
//        //  state.end(METHOD_HANDLER, startTime, null);
//        loadLocal(state);
//        // end : param 1
//        getStatic(classType, handlerFieldName, InvocationHandler.TYPE);
//        // end : param 2
//        loadLocal(startTime);
//        // end : param 3
//        loadLocal(exception);
//        invokeVirtual(ThreadState.TYPE, ThreadState.end);
//
//        // -------------------------------------------------------------------------------
//        // throw e;
//        loadLocal(exception);
//        throwException();
//
//        // -------------------------------------------------------------------------------
//        endMethod();
//    }

}
