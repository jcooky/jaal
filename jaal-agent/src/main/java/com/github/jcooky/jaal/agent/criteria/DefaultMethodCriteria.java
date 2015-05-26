package com.github.jcooky.jaal.agent.criteria;

/*
 * #%L
 * jaal-agent
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

public class DefaultMethodCriteria implements MethodCriteria {
    private String className, methodName, signature;
    private Long modifiers;

    public DefaultMethodCriteria(String className, String methodName, String signature, Long modifiers) {
      this.className = className;
      this.methodName = methodName;
      this.signature = signature;
      this.modifiers = modifiers;
    }

    @Override
    public boolean isMatch(String className) {
      return (this.className == null || className.equals(this.className));
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifiers) {
      return (this.className == null || className.equals(this.className)) &&
          (this.methodName == null || methodName.equals(this.methodName)) &&
          (this.signature == null || signature.equals(this.signature)) &&
          (this.modifiers == null || modifiers == this.modifiers);
    }
  }