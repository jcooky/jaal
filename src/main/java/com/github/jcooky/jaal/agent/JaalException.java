package com.github.jcooky.jaal.agent;

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
 * Created by JCooky on 15. 2. 6..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class JaalException extends RuntimeException {
  /**
   * <p>Constructor for JaalException.</p>
   */
  public JaalException() {
  }

  /**
   * <p>Constructor for JaalException.</p>
   *
   * @param s a {@link java.lang.String} object.
   */
  public JaalException(String s) {
    super(s);
  }

  /**
   * <p>Constructor for JaalException.</p>
   *
   * @param s a {@link java.lang.String} object.
   * @param throwable a {@link java.lang.Throwable} object.
   */
  public JaalException(String s, Throwable throwable) {
    super(s, throwable);
  }

  /**
   * <p>Constructor for JaalException.</p>
   *
   * @param throwable a {@link java.lang.Throwable} object.
   */
  public JaalException(Throwable throwable) {
    super(throwable);
  }
}
