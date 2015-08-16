package com.github.jcooky.jaal.common.profile;

/**
 * Created by JCooky on 15. 6. 22..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public interface Profiler {
  /**
   * <p>begin.</p>
   *
   * @param owner a {@link com.github.jcooky.jaal.common.profile.ClassType} object.
   * @param method a {@link com.github.jcooky.jaal.common.profile.MethodType} object.
   * @param startTime a long.
   */
  void begin(ClassType owner, MethodType method, long startTime);
  /**
   * <p>end.</p>
   *
   * @param owner a {@link com.github.jcooky.jaal.common.profile.ClassType} object.
   * @param method a {@link com.github.jcooky.jaal.common.profile.MethodType} object.
   * @param startTime a long.
   * @param throwable a {@link java.lang.Throwable} object.
   * @param elapsedTime a long.
   */
  void end(ClassType owner, MethodType method, long startTime, Throwable throwable, long elapsedTime);
}
