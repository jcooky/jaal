package com.github.jcooky.jaal.common.invocation;

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>DefaultInvocationHandler class.</p>
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class DefaultInvocationHandler implements InvocationHandler {
    /** {@inheritDoc} */
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
