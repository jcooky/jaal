package com.github.jcooky.jaal.sample;

import com.github.jcooky.jaal.agent.JaalAgentBootstrap;
import com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy;
import com.github.jcooky.jaal.agent.criteria.DefaultMethodCriteria;

import java.lang.instrument.Instrumentation;

/**
 * Created by jcooky on 15. 5. 25..
 */
public class JaalSampleAgent {

  public static void premain(String agentArgs, Instrumentation inst) {

    new JaalAgentBootstrap()
        .setName("jaal-sample-agent")
        .basePackage("com.github.jcooky.jaal.sample")
        .addProxyInjectorStrategy(new DefaultMethodCriteria("com.github.jcooky.sample.web.TestController", "helloWorld", null, null), JaalSampleFactory.class.getName())
        .build(inst);
  }
}
