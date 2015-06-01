package com.github.jcooky.jaal.agent.config;

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

import com.github.jcooky.jaal.agent.criteria.MethodCriteria;

/**
 * Created by JCooky on 15. 2. 25..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class ProxyInjectorStrategy implements InjectorStrategy {
  private MethodCriteria methodCriteria;
  private String factoryClass;

  /**
   * <p>Constructor for ProxyInjectorStrategy.</p>
   *
   * @param methodCriteria a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   * @param factoryClass a {@link java.lang.String} object.
   */
  public ProxyInjectorStrategy(MethodCriteria methodCriteria, String factoryClass) {
    this.methodCriteria = methodCriteria;
    this.factoryClass = factoryClass;
  }

  /**
   * <p>Getter for the field <code>methodCriteria</code>.</p>
   *
   * @return a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   */
  public MethodCriteria getMethodCriteria() {
    return this.methodCriteria;
  }

  /**
   * <p>Getter for the field <code>factoryClass</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getFactoryClass() {
    return this.factoryClass;
  }

  /** {@inheritDoc} */
  @Override
  public InjectorStrategyType getType() {
    return InjectorStrategyType.PROXY;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isMatch(String className) {
    return methodCriteria.isMatch(className);
  }

}
