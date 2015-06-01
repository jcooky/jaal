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

/**
 * Created by JCooky on 15. 2. 25..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class ReplaceInjectorStrategy implements InjectorStrategy {
  private String targetClass;
  private String replacementClass;

  /**
   * <p>Constructor for ReplaceInjectorStrategy.</p>
   *
   * @param targetClass a {@link java.lang.String} object.
   * @param replacementClass a {@link java.lang.String} object.
   */
  public ReplaceInjectorStrategy(String targetClass, String replacementClass) {
    this.targetClass = targetClass;
    this.replacementClass = replacementClass;
  }

  /** {@inheritDoc} */
  @Override
  public InjectorStrategyType getType() {
    return InjectorStrategyType.REPLACE;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isMatch(String className) {
    return true;
  }

  /**
   * <p>Getter for the field <code>targetClass</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getTargetClass() {
    return targetClass;
  }

  /**
   * <p>Getter for the field <code>replacementClass</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getReplacementClass() {
    return replacementClass;
  }
}
