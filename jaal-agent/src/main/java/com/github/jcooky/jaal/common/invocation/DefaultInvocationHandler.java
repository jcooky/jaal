package com.github.jcooky.jaal.common.invocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object self, Method method, Object[] args) throws Throwable {
      try {
        return method.invoke(self, args);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        throw e.getCause();
      }

      return null;
    }
  }