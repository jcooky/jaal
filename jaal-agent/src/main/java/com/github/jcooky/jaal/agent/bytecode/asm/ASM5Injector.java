package com.github.jcooky.jaal.agent.bytecode.asm;

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

import com.github.jcooky.jaal.agent.bytecode.Injector;
import com.github.jcooky.jaal.agent.config.InjectorStrategy;
import com.github.jcooky.jaal.agent.config.ProxyInjectorStrategy;
import com.github.jcooky.jaal.agent.config.ReplaceInjectorStrategy;
import com.github.jcooky.jaal.agent.criteria.MethodCriteria;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.SerialVersionUIDAdder;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Created by JCooky on 15. 3. 3..
 */
public class ASM5Injector implements Injector {

  private InjectorStrategy injectorStrategy;

  public ASM5Injector(InjectorStrategy injectorStrategy) {
    this.injectorStrategy = injectorStrategy;
  }

  @Override
  public byte[] inject(byte[] bytes) {
    ClassReader cr = new ClassReader(bytes);

    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
    ClassVisitor target = new CheckClassAdapter(cw, false);

    SerialVersionUIDAdder serialVersionUIDAdder = new SerialVersionUIDAdder(target);
    ClassInitClassVisitor classInitClassVisitor = new ClassInitClassVisitor(target);

    ClassVisitor visitor = null;
    switch(this.injectorStrategy.getType()) {
      case PROXY:
        visitor = getInjectClassVisitor((ProxyInjectorStrategy)this.injectorStrategy, target, classInitClassVisitor);
        break;
      case REPLACE:
        visitor = getReplaceClassVisitor((ReplaceInjectorStrategy)this.injectorStrategy, target, target);
    }
    cr.accept(visitor, 0);

    return cw.toByteArray();
  }

  private ClassVisitor getReplaceClassVisitor(ReplaceInjectorStrategy injectorStrategy, ClassVisitor interfaceClsasVisitor, ClassVisitor parentVisitor) {
    ReplaceClassVisitor replaceClassVisitor = new ReplaceClassVisitor(injectorStrategy, parentVisitor);
    MethodCriteriaClassVisitor criteriaClassVisitor = new MethodCriteriaClassVisitor(replaceClassVisitor, parentVisitor);
    ClassVisitor visitor = new IfInterfaceClassVisitor(interfaceClsasVisitor, criteriaClassVisitor);

    criteriaClassVisitor.setCriteria(new MethodCriteria() {
      @Override
      public boolean isMatch(String className) {
        return !className.startsWith("java.") && !className.startsWith("com.sun.") && !className.startsWith("javax.") && !className.startsWith("sun.");
      }

      @Override
      public boolean isMatch(String className, String methodName, String signature, long modifiers) {
        return isMatch(className);
      }
    });

    return visitor;
  }

  private ClassVisitor getInjectClassVisitor(ProxyInjectorStrategy injectorStrategy, ClassVisitor interfaceClassVisitor, ClassVisitor parentVisitor) {
    InjectClassVisitor injectClassVisitor = new InjectClassVisitor(injectorStrategy, parentVisitor);
    MethodCriteriaClassVisitor criteriaClassVisitor = new MethodCriteriaClassVisitor(injectClassVisitor, parentVisitor);
    ClassVisitor visitor = new IfInterfaceClassVisitor(interfaceClassVisitor, criteriaClassVisitor);

    criteriaClassVisitor.setCriteria(injectorStrategy.getMethodCriteria());

    return visitor;
  }
}
