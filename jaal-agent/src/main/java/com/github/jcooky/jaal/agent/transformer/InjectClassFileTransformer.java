package com.github.jcooky.jaal.agent.transformer;

import com.github.jcooky.jaal.agent.bytecode.Injector;
import com.github.jcooky.jaal.agent.bytecode.asm.ASM5Injector;
import com.github.jcooky.jaal.agent.config.InjectorStrategy;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Created by JCooky on 15. 2. 11..
 */
public class InjectClassFileTransformer implements ClassFileTransformer {
  private final Injector injector;

  public InjectClassFileTransformer(InjectorStrategy injectorStrategy) {
    this.injector = new ASM5Injector(injectorStrategy);
  }

  @Override
  public byte[] transform(ClassLoader classLoader, String className, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) {

    return injector.inject(bytes);
  }
}
