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

/**
 * Created by JCooky on 15. 2. 25..
 */
public class ReplaceInjectorStrategy implements InjectorStrategy {
  private String targetClass;
  private String replacementClass;

  public ReplaceInjectorStrategy(String targetClass, String replacementClass) {
    this.targetClass = targetClass;
    this.replacementClass = replacementClass;
  }

  @Override
  public InjectorStrategyType getType() {
    return InjectorStrategyType.REPLACE;
  }

  @Override
  public boolean isMatch(String className) {
    return true;
  }

  public String getTargetClass() {
    return targetClass;
  }

  public String getReplacementClass() {
    return replacementClass;
  }
}
