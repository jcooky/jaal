package jaal.sample;

import com.github.jcooky.jaal.common.invocation.DefaultInvocationHandler;
import com.github.jcooky.jaal.common.invocation.InvocationHandler;

import java.lang.reflect.Method;

/**
 * Created by JCooky on 15. 5. 28..
 */
public class AgentFactory {
  public static InvocationHandler getInvocationHandler() {
    return new DefaultInvocationHandler() {
      @Override
      public Object invoke(Object self, Method method, Object[] args) throws Throwable {
        System.out.println("test");

        return super.invoke(self, method, args);
      }
    };
  }
}
