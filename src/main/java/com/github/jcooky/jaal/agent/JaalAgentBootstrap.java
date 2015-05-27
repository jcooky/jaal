package com.github.jcooky.jaal.agent;

/*
 * #%L
 * jaal-agent
 * %%
 * Copyright (C) 2015 JCooky
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
