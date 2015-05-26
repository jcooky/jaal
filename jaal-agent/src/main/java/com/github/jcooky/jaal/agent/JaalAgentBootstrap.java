package com.github.jcooky.jaal.agent;

import com.github.jcooky.jaal.agent.config.Configuration;
import com.github.jcooky.jaal.agent.config.InjectorStrategy;
import com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy;
import com.github.jcooky.jaal.agent.criteria.MethodCriteria;
import com.github.jcooky.jaal.agent.transformer.SelectClassFileTransformer;
import com.google.common.collect.Sets;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Collection;

/**
 * Created by JCooky on 15. 2. 5..
 */
public class JaalAgentBootstrap {

  private Configuration configuration = new Configuration("jaal-agent-bootstrap");
  private String pkg;

  public JaalAgentBootstrap basePackage(String pkg) {
    this.pkg = pkg;
    return this;
  }

  public JaalAgentBootstrap setName(String name) {
    configuration.setName(name);

    return this;
  }

  public JaalAgentBootstrap addProxyInjectorStrategy(MethodCriteria methodCriteria, String factoryClsName) {
    this.addInjectorStrategy(new ProxyInjectorStrategy(methodCriteria, factoryClsName));
    return this;
  }


  public JaalAgentBootstrap addInjectorStrategy(InjectorStrategy injectorStrategy) {
    configuration.getInjectorStrategies().add(injectorStrategy);

    return this;
  }

  public JaalAgentBootstrap addInjectorStrategies(Collection<? extends InjectorStrategy> injectorStrategies) {
    configuration.getInjectorStrategies().addAll(injectorStrategies);

    return this;
  }

  public void build(Instrumentation inst) {
    inst.addTransformer(new SelectClassFileTransformer(pkg, Sets.newHashSet(configuration)));
  }
}
