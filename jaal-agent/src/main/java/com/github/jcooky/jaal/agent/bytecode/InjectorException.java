package com.github.jcooky.jaal.agent.bytecode;

import com.github.jcooky.jaal.agent.JaalException;

public class InjectorException extends JaalException {

    public InjectorException(String message) {
        super(message);
    }

    public InjectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
