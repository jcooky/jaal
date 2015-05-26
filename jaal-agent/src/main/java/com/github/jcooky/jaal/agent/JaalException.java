package com.github.jcooky.jaal.agent;

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
 * Created by JCooky on 15. 2. 6..
 */
public class JaalException extends RuntimeException {
  public JaalException() {
  }

  public JaalException(String s) {
    super(s);
  }

  public JaalException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public JaalException(Throwable throwable) {
    super(throwable);
  }
}
