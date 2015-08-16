package com.github.jcooky.jaal.agent.criteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>RegexMethodCriteria class.</p>
 *
 * @author JCooky
 * @since 2015-08-10
 * @version $Id: $Id
 */
public class RegexMethodCriteria extends DefaultMethodCriteria {

  private Pattern className, methodName, signature;

  /**
   * <p>Constructor for DefaultMethodCriteria.</p>
   *
   * @param className  a {@link java.lang.String} object.
   * @param methodName a {@link java.lang.String} object.
   * @param signature  a {@link java.lang.String} object.
   * @param modifiers  a {@link java.lang.Long} object.
   */
  public RegexMethodCriteria(String className, String methodName, String signature, Long modifiers) {
    super(className, methodName, signature, modifiers);

    this.className = Pattern.compile(className);
    this.methodName = Pattern.compile(methodName);
    this.signature = Pattern.compile(signature);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isMatch(String className) {
    return super.isMatch(className) || this.className.matcher(className).matches();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isMatch(String className, String methodName, String signature, long modifiers) {
    return super.isMatch(className, methodName, signature, modifiers) ||
        (this.className.matcher(className).matches() && this.methodName.matcher(methodName).matches() && this.signature.matcher(signature).matches());
  }
}
