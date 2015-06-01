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

/**
 * Created by JCooky on 15. 2. 10..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public interface InvocationHandler {
  /**
   * <p>invoke.</p>
   *
   * @param self a {@link java.lang.Object} object.
   * @param method a {@link java.lang.reflect.Method} object.
   * @param args an array of {@link java.lang.Object} objects.
   * @return a {@link java.lang.Object} object.
   * @throws java.lang.Throwable if any.
   */
  public Object invoke(Object self, java.lang.reflect.Method method, Object[] args) throws Throwable;
}
