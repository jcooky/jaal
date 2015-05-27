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

import java.util.HashSet;

/**
 * Created by JCooky on 15. 2. 25..
 */
public class Configuration {

  private String name;
  private HashSet<InjectorStrategy> injectorStrategies = new HashSet<InjectorStrategy>();

  public Configuration(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public HashSet<InjectorStrategy> getInjectorStrategies() {
    return this.injectorStrategies;
  }

//  public static Set<Configuration> parseArguments(String arguments) throws IOException {
//    Set<Configuration> configurations = new HashSet<Configuration>();
//    if (arguments != null && arguments.length() != 0) {
//      String[] jarFilePaths = arguments.split(",");
//      for (String path : jarFilePaths) {
//        Configuration configuration = new Configuration(path);
//        JarFile jarFile = null;
//        try {
//          jarFile = new JarFile(new File(path));
//          configuration.loadAndAdd(jarFile.getInputStream(jarFile.getEntry("META-INF/jexi.yml")));
//          configurations.add(configuration);
//        } finally {
//          if (jarFile != null) jarFile.close();
//        }
//      }
//    }
//
//    return configurations;
//  }

}
