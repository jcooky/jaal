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
import com.github.jcooky.jaal.agent.config.ProfilingInjectorStrategy;
import com.github.jcooky.jaal.agent.criteria.Restrictions;
import com.github.jcooky.jaal.common.profile.ClassType;
import com.github.jcooky.jaal.common.profile.MethodType;
import com.github.jcooky.jaal.common.profile.Profiler;
import com.github.jcooky.jaal.java.ClassLoaderImpl;
import com.github.jcooky.jaal.org.objectweb.asm.ClassReader;
import com.github.jcooky.jaal.org.objectweb.asm.util.ASMifier;
import com.github.jcooky.jaal.org.objectweb.asm.util.TraceClassVisitor;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import sun.security.action.PutAllAction;
import sun.security.ec.SunEC;

import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ASM5ProfilingTest {

  private ClassLoaderImpl classLoader = new ClassLoaderImpl();

  public static class Target {

    public Target() {
      super();
    }

    public Object methodTarget() throws Throwable {
      int i = 123;
      String value = "hello world" + i;

      return value;
    }

    public static Object method(boolean b) {
      return "Test";
    }
  }

  @Test
  public void testInject() throws Throwable {
    InjectorStrategy is = new ProfilingInjectorStrategy(ProfilerFactory.class.getName(), Restrictions.any());
    ASM5Injector injector = new ASM5Injector(is);

    Class<?> cls = classLoader.defineClass(injector.inject(IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(Target.class.getName().replace('.', '/') + ".class"))));

    Object self = cls.newInstance();
    String str = (String) cls.getMethod("methodTarget").invoke(self);
    assertThat(str, is("hello world123"));
    str = (String) cls.getMethod("method", boolean.class).invoke(null, false);
    assertThat(str, is("Test"));

    verify(ProfilerFactory.PROFILER, times(3)).begin(any(ClassType.class), any(MethodType.class), anyLong());
    verify(ProfilerFactory.PROFILER, times(3)).end(any(ClassType.class), any(MethodType.class), anyLong(), any(Throwable.class), anyLong());
  }

  public static class ProfilerFactory {
    public static Profiler PROFILER = mock(Profiler.class);

    public static Profiler getProfiler() {
      return PROFILER;
    }
  }
}