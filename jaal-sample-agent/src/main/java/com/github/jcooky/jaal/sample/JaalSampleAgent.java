package com.github.jcooky.jaal.sample;

/*
 * #%L
 * jaal-sample-agent
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
