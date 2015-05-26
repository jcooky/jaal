package com.github.jcooky.jaal.agent.config;

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
