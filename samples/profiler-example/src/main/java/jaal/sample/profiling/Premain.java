package jaal.sample.profiling;

import com.github.jcooky.jaal.agent.JaalAgentBootstrap;
import com.github.jcooky.jaal.agent.criteria.Restrictions;

import java.lang.instrument.Instrumentation;

/**
 * @author JCooky
 * @since 2015-08-11
 */
public class Premain {
  public static void premain(String agentArgs, Instrumentation inst) {
    new JaalAgentBootstrap()
        .excludePackage("jaal.sample")
        .addProfilingInjectorStrategy(Restrictions.any(), ProfilerFactory.class)
        .build(inst);
  }
}
