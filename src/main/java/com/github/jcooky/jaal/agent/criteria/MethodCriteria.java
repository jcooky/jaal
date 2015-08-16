package com.github.jcooky.jaal.agent.criteria;

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
 * <p>MethodCriteria interface.</p>
 *
 * @author JCooky
 * @version $Id: $Id
 */
public interface MethodCriteria {

  /**
   * <p>isMatch.</p>
   *
   * @param className a {@link java.lang.String} object.
   * @return a boolean.
   */
  boolean isMatch(String className);
  /**
   * should a method with these attributes be matched?
   *
   * @param className a {@link java.lang.String} object.
   * @param methodName a {@link java.lang.String} object.
   * @param signature a {@link java.lang.String} object.
   * @param modifiers a long.
   * @return a boolean.
   */
  boolean isMatch(String className, String methodName, String signature, long modifiers);
}
