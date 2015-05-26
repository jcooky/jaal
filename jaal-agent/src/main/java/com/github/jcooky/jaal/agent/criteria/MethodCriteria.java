package com.github.jcooky.jaal.agent.criteria;

public interface MethodCriteria {

//  boolean instanceOf(String superName, String []interfaces);

  boolean isMatch(String className);
  /**
   * should a method with these attributes be matched?
   */
  boolean isMatch(String className, String methodName, String signature, long modifiers);
}
