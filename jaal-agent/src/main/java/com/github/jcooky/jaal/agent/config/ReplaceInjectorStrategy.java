package com.github.jcooky.jaal.agent.config;

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
