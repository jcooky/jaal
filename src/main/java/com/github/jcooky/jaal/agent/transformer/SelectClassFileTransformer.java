package com.github.jcooky.jaal.agent.transformer;

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

import com.github.jcooky.jaal.agent.config.Configuration;
import com.github.jcooky.jaal.agent.config.InjectorStrategy;

import java.io.FileNotFoundException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by JCooky on 15. 2. 11..
 */
public class SelectClassFileTransformer implements ClassFileTransformer {
  private final String basePackage;
  private List<InjectorStrategy> injectorStrategies = new ArrayList<InjectorStrategy>(4);

  public SelectClassFileTransformer(String basePackage, Iterable<? extends Configuration> configurations) {
    this.basePackage = basePackage;
    for (Configuration configuration : configurations) {
      injectorStrategies.addAll(configuration.getInjectorStrategies());
    }
  }

  @Override
  public byte[] transform(ClassLoader classLoader, String className, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
    className = className.replace('/', '.');
    try {

      if (!className.startsWith("com.github.jcooky.jaal") &&
          (basePackage == null || !className.startsWith(basePackage))) {

        debug(className, bytes);

        for (InjectorStrategy injectorStrategy : this.injectorStrategies) {
          if (injectorStrategy.isMatch(className)) {
            byte []result = new InjectClassFileTransformer(injectorStrategy).transform(classLoader, className, aClass, protectionDomain, bytes);
            if (result != null) {
              bytes = result;
            }
          }
        }

        return bytes;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private void debug(String className, byte[] bytes) throws FileNotFoundException {

  }
}
