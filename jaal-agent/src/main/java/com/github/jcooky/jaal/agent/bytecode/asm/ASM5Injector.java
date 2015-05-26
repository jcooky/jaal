package com.github.jcooky.jaal.agent.bytecode.asm;

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
