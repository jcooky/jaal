package com.github.jcooky.jaal.agent;

import com.github.jcooky.jaal.common.profile.ClassType;
import com.github.jcooky.jaal.common.profile.MethodType;
import com.github.jcooky.jaal.common.profile.Profiler;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>ThreadState class.</p>
 *
 * @author JCooky
 * @since 2015-08-06
 * @version $Id: $Id
 */
public class ThreadState {
  private final Profiler profiler;

  /**
   * <p>Constructor for ThreadState.</p>
   *
   * @param profilerFactoryClass a {@link java.lang.Class} object.
   * @throws java.lang.NoSuchMethodException if any.
   * @throws java.lang.reflect.InvocationTargetException if any.
   * @throws java.lang.IllegalAccessException if any.
   */
  public ThreadState(Class<?> profilerFactoryClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    this.profiler = (Profiler)profilerFactoryClass.getMethod("getProfiler").invoke(null);
  }

  /**
   * <p>begin.</p>
   *
   * @param className a {@link java.lang.String} object.
   * @param access a int.
   * @param methodName a {@link java.lang.String} object.
   * @param descriptor a {@link java.lang.String} object.
   * @return a long.
   */
  public long begin(String className, int access, String methodName, String descriptor) {
    long startTime = System.nanoTime();

    profiler.begin(new ClassType(className.substring(0, className.lastIndexOf('.')), className.substring(className.lastIndexOf('.')+1)),
        new MethodType(access, methodName, descriptor),
        startTime
    );

    return startTime;
  }

  /**
   * <p>end.</p>
   *
   * @param className a {@link java.lang.String} object.
   * @param access a int.
   * @param methodName a {@link java.lang.String} object.
   * @param descriptor a {@link java.lang.String} object.
   * @param startTime a long.
   * @param throwable a {@link java.lang.Throwable} object.
   */
  public void end(String className, int access, String methodName, String descriptor, long startTime, Throwable throwable) {
    long elapsedTime = System.nanoTime() - startTime;

    profiler.end(new ClassType(className.substring(0, className.lastIndexOf('.')), className.substring(className.lastIndexOf('.')+1)),
        new MethodType(access, methodName, descriptor),
        startTime,
        throwable,
        elapsedTime
    );
  }
}
