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
 * <p>ClassLoaderScope class.</p>
 *
 * @author JCooky
 * @version $Id: $Id
 */
public enum ClassLoaderScope {
  SYSTEM(0), BOOTSTRAP(1);

  private final int value;

  ClassLoaderScope(int value) {
    this.value = value;
  }

  /**
   * <p>Getter for the field <code>value</code>.</p>
   *
   * @return a int.
   */
  public int getValue() {
    return value;
  }
}
