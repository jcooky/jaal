package com.github.jcooky.jaal.agent.transformer;

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

import com.github.jcooky.jaal.agent.bytecode.Injector;
import com.github.jcooky.jaal.agent.bytecode.asm.ASM5Injector;
import com.github.jcooky.jaal.agent.config.InjectorStrategy;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Created by JCooky on 15. 2. 11..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class InjectClassFileTransformer implements ClassFileTransformer {
  private final Injector injector;

  /**
   * <p>Constructor for InjectClassFileTransformer.</p>
   *
   * @param injectorStrategy a {@link com.github.jcooky.jaal.agent.config.InjectorStrategy} object.
   */
  public InjectClassFileTransformer(InjectorStrategy injectorStrategy) {
    this.injector = new ASM5Injector(injectorStrategy);
  }

  /** {@inheritDoc} */
  @Override
  public byte[] transform(ClassLoader classLoader, String className, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) {

    return injector.inject(bytes);
  }
}
