package com.github.jcooky.jaal.agent.config;

import com.github.jcooky.jaal.agent.criteria.MethodCriteria;

/**
 * Created by JCooky on 15. 6. 23..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class ProfilingInjectorStrategy implements InjectorStrategy {
  private final String profilerFactoryClass;
  private final MethodCriteria methodCriteria;

  /**
   * <p>Constructor for ProfilingInjectorStrategy.</p>
   *
   * @param profilerFactoryClass a {@link java.lang.String} object.
   * @param methodCriteria a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   */
  public ProfilingInjectorStrategy(String profilerFactoryClass, MethodCriteria methodCriteria) {
    this.profilerFactoryClass = profilerFactoryClass;
    this.methodCriteria = methodCriteria;
  }

  /** {@inheritDoc} */
  @Override
  public InjectorStrategyType getType() {
    return InjectorStrategyType.PROFILING;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isMatch(String className) {
    return methodCriteria.isMatch(className);
  }

  /**
   * <p>Getter for the field <code>profilerFactoryClass</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getProfilerFactoryClass() {
    return profilerFactoryClass;
  }

  /**
   * <p>Getter for the field <code>methodCriteria</code>.</p>
   *
   * @return a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   */
  public MethodCriteria getMethodCriteria() {
    return methodCriteria;
  }
}
