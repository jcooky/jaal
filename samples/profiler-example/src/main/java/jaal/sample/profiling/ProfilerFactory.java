package jaal.sample.profiling;

import com.github.jcooky.jaal.common.profile.ClassType;
import com.github.jcooky.jaal.common.profile.MethodType;
import com.github.jcooky.jaal.common.profile.Profiler;

/**
 * @author JCooky
 * @since 2015-08-11
 */
public class ProfilerFactory {
  public static Profiler getProfiler() {
    return new Profiler() {

      public void begin(ClassType owner, MethodType method) {
        System.out.println("test1");
      }

      public void end(ClassType owner, MethodType method, long startTime, Throwable throwable, long elapsedTime) {
        System.out.println("test2");
      }
    };
  }
}
