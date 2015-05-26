package com.github.jcooky.jaal.sample;

import com.github.jcooky.jaal.common.invocation.DefaultInvocationHandler;
import com.github.jcooky.jaal.common.invocation.InvocationHandler;

import java.lang.reflect.Method;

/**
 * Created by jcooky on 15. 5. 25..
 */
public class JaalSampleFactory {

  public static InvocationHandler getInvocationHandler() throws Exception {
    return new DefaultInvocationHandler() {
      @Override
      public Object invoke(Object self, Method method, Object[] args) throws Throwable {
        System.out.println("test");

        return super.invoke(self, method, args);
      }
    };
  }
}
