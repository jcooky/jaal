package com.github.jcooky.jaal.agent;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>ThreadStateFactory class.</p>
 *
 * @author JCooky
 * @since 2015-08-06
 * @version $Id: $Id
 */
public class ThreadStateFactory extends ThreadLocal<ThreadState> {
  private final Class<?> profilerFactoryClass;

  /**
   * <p>Constructor for ThreadStateFactory.</p>
   *
   * @param profilerFactoryClass a {@link java.lang.String} object.
   * @throws java.lang.ClassNotFoundException if any.
   */
  public ThreadStateFactory(String profilerFactoryClass) throws ClassNotFoundException {
    this.profilerFactoryClass = Class.forName(profilerFactoryClass);
  }

  /** {@inheritDoc} */
  @Override
  protected ThreadState initialValue() {
    try {
      return new ThreadState(profilerFactoryClass);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}
