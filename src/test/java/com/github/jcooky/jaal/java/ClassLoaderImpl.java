package com.github.jcooky.jaal.java;

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
 * Created by JCooky on 15. 2. 11..
 */
public class ClassLoaderImpl extends ClassLoader {

  public Class<?> defineClass(byte []data) {
    return super.defineClass(data, 0, data.length);
  }

  public Class<?> defineClass(String name, byte[] data) throws ClassFormatError {
    return super.defineClass(name, data, 0, data.length);
  }


}