package com.github.jcooky.jaal.agent;

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
