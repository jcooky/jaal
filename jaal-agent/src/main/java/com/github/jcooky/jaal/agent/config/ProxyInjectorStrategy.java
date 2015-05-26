package com.github.jcooky.jaal.agent.config;

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

import com.github.jcooky.jaal.agent.criteria.MethodCriteria;
import com.google.common.base.Objects;

/**
 * Created by JCooky on 15. 2. 25..
 */
public class ProxyInjectorStrategy implements InjectorStrategy {
  private MethodCriteria methodCriteria;
  private String factoryClass;

  public ProxyInjectorStrategy(MethodCriteria methodCriteria, String factoryClass) {
    this.methodCriteria = methodCriteria;
    this.factoryClass = factoryClass;
  }

  public MethodCriteria getMethodCriteria() {
    return this.methodCriteria;
  }

  public String getFactoryClass() {
    return this.factoryClass;
  }

  @Override
  public InjectorStrategyType getType() {
    return InjectorStrategyType.PROXY;
  }

  @Override
  public boolean isMatch(String className) {
    return methodCriteria.isMatch(className);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("methodCriteria", methodCriteria)
        .add("factoryClass", factoryClass)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProxyInjectorStrategy that = (ProxyInjectorStrategy) o;
    return Objects.equal(methodCriteria, that.methodCriteria) &&
        Objects.equal(factoryClass, that.factoryClass);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(methodCriteria, factoryClass);
  }
}
