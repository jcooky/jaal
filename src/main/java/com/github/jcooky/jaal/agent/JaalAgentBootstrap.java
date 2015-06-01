package com.github.jcooky.jaal.agent;

/*
 * #%L
 * jaal
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

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by JCooky on 15. 2. 5..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class JaalAgentBootstrap {

  private Configuration configuration = new Configuration("jaal-agent-bootstrap");
  private String pkg;

  /**
   * <p>excludePackage.</p>
   *
   * @param pkg a {@link java.lang.String} object.
   * @return a {@link com.github.jcooky.jaal.agent.JaalAgentBootstrap} object.
   */
  public JaalAgentBootstrap excludePackage(String pkg) {
    this.pkg = pkg;
    return this;
  }

  /**
   * <p>setName.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @return a {@link com.github.jcooky.jaal.agent.JaalAgentBootstrap} object.
   */
  public JaalAgentBootstrap setName(String name) {
    configuration.setName(name);

    return this;
  }

  /**
   * <p>addProxyInjectorStrategy.</p>
   *
   * @param methodCriteria a {@link com.github.jcooky.jaal.agent.criteria.MethodCriteria} object.
   * @param factoryClsName a {@link java.lang.String} object.
   * @return a {@link com.github.jcooky.jaal.agent.JaalAgentBootstrap} object.
   */
  public JaalAgentBootstrap addProxyInjectorStrategy(MethodCriteria methodCriteria, String factoryClsName) {
    this.addInjectorStrategy(new ProxyInjectorStrategy(methodCriteria, factoryClsName));
    return this;
  }


  /**
   * <p>addInjectorStrategy.</p>
   *
   * @param injectorStrategy a {@link com.github.jcooky.jaal.agent.config.InjectorStrategy} object.
   * @return a {@link com.github.jcooky.jaal.agent.JaalAgentBootstrap} object.
   */
  public JaalAgentBootstrap addInjectorStrategy(InjectorStrategy injectorStrategy) {
    configuration.getInjectorStrategies().add(injectorStrategy);

    return this;
  }

  /**
   * <p>addInjectorStrategies.</p>
   *
   * @param injectorStrategies a {@link java.util.Collection} object.
   * @return a {@link com.github.jcooky.jaal.agent.JaalAgentBootstrap} object.
   */
  public JaalAgentBootstrap addInjectorStrategies(Collection<? extends InjectorStrategy> injectorStrategies) {
    configuration.getInjectorStrategies().addAll(injectorStrategies);

    return this;
  }

  /**
   * <p>build.</p>
   *
   * @param inst a {@link java.lang.instrument.Instrumentation} object.
   */
  public void build(Instrumentation inst) {
    inst.addTransformer(new SelectClassFileTransformer(pkg, Arrays.asList(configuration)));
  }
}
