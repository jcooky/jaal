package jaal.sample;

import com.github.jcooky.jaal.agent.JaalAgentBootstrap;
import com.github.jcooky.jaal.agent.criteria.DefaultMethodCriteria;

import java.lang.instrument.Instrumentation;

public class Premain {
  public static void premain(String agentArgs, Instrumentation inst) {
    new JaalAgentBootstrap()
        .setName("jaal-sample")
        .addProxyInjectorStrategy(new DefaultMethodCriteria("jaal.sample.Main", "A", null, null), AgentFactory.class.getName())
        .build(inst);
  }
}
