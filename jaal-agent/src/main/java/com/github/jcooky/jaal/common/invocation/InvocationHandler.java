package com.github.jcooky.jaal.common.invocation;

/**
 * Created by JCooky on 15. 2. 10..
 */
public interface InvocationHandler {
  public Object invoke(Object self, java.lang.reflect.Method method, Object[] args) throws Throwable;
}
