package com.github.jcooky.jaal.agent.transformer;

import com.github.jcooky.jaal.agent.config.Configuration;
import com.github.jcooky.jaal.agent.config.InjectorStrategy;
import org.apache.commons.lang3.StringUtils;

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

  public SelectClassFileTransformer(String basePackage, Set<Configuration> configurations) {
    this.basePackage = basePackage;
    for (Configuration configuration : configurations) {
      injectorStrategies.addAll(configuration.getInjectorStrategies());
    }
  }

  @Override
  public byte[] transform(ClassLoader classLoader, String className, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
    className = className.replace('/', '.');
    try {

      if (!StringUtils.startsWithAny("com.github.jcooky.jaal", basePackage)) {

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
