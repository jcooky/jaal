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

import java.util.HashSet;

/**
 * Created by JCooky on 15. 2. 25..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class Configuration {

  private String name;
  private HashSet<InjectorStrategy> injectorStrategies = new HashSet<InjectorStrategy>();

  /**
   * <p>Constructor for Configuration.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  public Configuration(String name) {
    this.name = name;
  }

  /**
   * <p>Setter for the field <code>name</code>.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * <p>Getter for the field <code>name</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return this.name;
  }

  /**
   * <p>Getter for the field <code>injectorStrategies</code>.</p>
   *
   * @return a {@link java.util.HashSet} object.
   */
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
