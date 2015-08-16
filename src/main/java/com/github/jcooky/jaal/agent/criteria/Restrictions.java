package com.github.jcooky.jaal.agent.criteria;

/**
 * <p>Restrictions class.</p>
 *
 * @author JCooky
 * @since 2015-08-08
 * @version $Id: $Id
 */
public class Restrictions {
  /**
   * <p>and.</p>
   *
   * @param criteria a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   * @return a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   */
  public static MethodCriteria and(final MethodCriteria ...criteria) {
    return new MethodCriteria() {
      @Override
      public boolean isMatch(String className) {
        for (MethodCriteria c : criteria) {
          if (!c.isMatch(className))
            return false;
        }

        return true;
      }

      @Override
      public boolean isMatch(String className, String methodName, String signature, long modifiers) {
        for (MethodCriteria c : criteria) {
          if (!c.isMatch(className, methodName, signature, modifiers))
            return false;
        }

        return true;
      }
    };
  }

  /**
   * <p>or.</p>
   *
   * @param criteria a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   * @return a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   */
  public static MethodCriteria or(final MethodCriteria ...criteria) {
    return new MethodCriteria() {
      @Override
      public boolean isMatch(String className) {
        for (MethodCriteria c : criteria) {
          if (c.isMatch(className))
            return true;
        }

        return false;
      }

      @Override
      public boolean isMatch(String className, String methodName, String signature, long modifiers) {
        for (MethodCriteria c : criteria) {
          if (c.isMatch(className, methodName, signature, modifiers))
            return true;
        }

        return false;
      }
    };
  }

  /**
   * <p>not.</p>
   *
   * @param criteria a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   * @return a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   */
  public static MethodCriteria not(final MethodCriteria criteria) {
    return new MethodCriteria() {
      @Override
      public boolean isMatch(String className) {
        return !criteria.isMatch(className);
      }

      @Override
      public boolean isMatch(String className, String methodName, String signature, long modifiers) {
        return !criteria.isMatch(className, methodName, signature, modifiers);
      }
    };
  }

  /**
   * <p>any.</p>
   *
   * @return a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   */
  public static MethodCriteria any() {
    return new MethodCriteria() {
      @Override
      public boolean isMatch(String className) {
        return true;
      }

      @Override
      public boolean isMatch(String className, String methodName, String signature, long modifiers) {
        return true;
      }
    };
  }
}
