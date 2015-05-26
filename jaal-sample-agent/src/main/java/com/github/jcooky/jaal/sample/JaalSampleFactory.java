package com.github.jcooky.jaal.sample;

/*
 * #%L
 * jaal-sample-agent
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
