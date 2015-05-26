package com.github.jcooky.jaal.agent.bytecode;

/**
 * JRat can swap out bytecode manipulation libraries - each supported library
 * must have a InjectorStrategy implementation.
 *
 */
public interface Injector {
    
    byte[] inject(byte[] inputClassData);
}
