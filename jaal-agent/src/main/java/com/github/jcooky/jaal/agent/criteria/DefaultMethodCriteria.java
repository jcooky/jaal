package com.github.jcooky.jaal.agent.criteria;

public class DefaultMethodCriteria implements MethodCriteria {
    private String className, methodName, signature;
    private Long modifiers;

    public DefaultMethodCriteria(String className, String methodName, String signature, Long modifiers) {
      this.className = className;
      this.methodName = methodName;
      this.signature = signature;
      this.modifiers = modifiers;
    }

    @Override
    public boolean isMatch(String className) {
      return (this.className == null || className.equals(this.className));
    }

    @Override
    public boolean isMatch(String className, String methodName, String signature, long modifiers) {
      return (this.className == null || className.equals(this.className)) &&
          (this.methodName == null || methodName.equals(this.methodName)) &&
          (this.signature == null || signature.equals(this.signature)) &&
          (this.modifiers == null || modifiers == this.modifiers);
    }
  }