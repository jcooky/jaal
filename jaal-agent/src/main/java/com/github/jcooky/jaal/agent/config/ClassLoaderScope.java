package com.github.jcooky.jaal.agent.config;

public enum ClassLoaderScope {
  SYSTEM(0), BOOTSTRAP(1);

  private final int value;

  ClassLoaderScope(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
