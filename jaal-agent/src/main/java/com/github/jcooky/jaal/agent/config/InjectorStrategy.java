package com.github.jcooky.jaal.agent.config;

/**
 * Created by JCooky on 15. 2. 25..
 */
public interface InjectorStrategy {
  InjectorStrategyType getType();

  boolean isMatch(String className);
}
