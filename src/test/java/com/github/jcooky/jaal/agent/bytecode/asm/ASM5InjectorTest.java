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

import com.github.jcooky.jaal.agent.config.InjectorStrategy;
import com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy;
import com.github.jcooky.jaal.agent.config.ReplaceInjectorStrategy;
import com.github.jcooky.jaal.agent.criteria.MethodCriteria;
import com.github.jcooky.jaal.common.invocation.InvocationHandler;
import com.github.jcooky.jaal.java.ClassLoaderImpl;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class ASM5InjectorTest {

  public static class Target {
    public void methodTarget() {

    }
  }

  public static class TestFactory {
    private static InvocationHandler mockInvocationHandler = mock(InvocationHandler.class, new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        System.out.println("test");
        return null;
      }
    });
    public static InvocationHandler getInvocationHandler() {
      return mockInvocationHandler;
    }
  }

  public static class Target2 {
    public Class<? extends Properties> a() {
      Properties properties = new Properties();
      return properties.getClass();
    }
  }

  public static class ChangedProperties extends Properties {
    public ChangedProperties() {
    }

    public ChangedProperties(Properties properties) {
      super(properties);
    }
  }

  private ClassLoaderImpl classLoader = new ClassLoaderImpl();

  @After
  public void tearDown() throws Exception {
    reset(TestFactory.getInvocationHandler());
  }

  @Test
  public void testInject() throws Throwable {
    InjectorStrategy is = new ProxyInjectorStrategy(new MethodCriteria() {
      @Override
      public boolean isMatch(String className) {
        return true;
      }

      @Override
      public boolean isMatch(String className, String methodName, String signature, long modifiers) {
        return true;
      }
    }, TestFactory.class.getName());

    ASM5Injector injector = new ASM5Injector(is);

    Class<?> cls = classLoader.defineClass(injector.inject(IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(Target.class.getName().replace('.', '/') + ".class"))));

    Object self = cls.newInstance();
    cls.getMethod("methodTarget").invoke(self);

    InvocationHandler invocationHandler = TestFactory.getInvocationHandler();
    verify(invocationHandler).invoke(anyObject(), any(Method.class), any(Object[].class));
  }

  @Test
  public void testInjectUsingReplace() throws Throwable {
    InjectorStrategy is = new ReplaceInjectorStrategy(Properties.class.getName(), ChangedProperties.class.getName());

    ASM5Injector injector = new ASM5Injector(is);

    Class<?> cls = classLoader.defineClass(injector.inject(IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(Target2.class.getName().replace('.', '/') + ".class"))));

    Object self = cls.newInstance();
    Class<? extends Properties> result = (Class<? extends Properties>)cls.getMethod("a").invoke(self);
    assertThat(result.getName(), is(ChangedProperties.class.getName()));
  }
}