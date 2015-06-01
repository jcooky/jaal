package com.github.jcooky.jaal.agent.bytecode;

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

import com.github.jcooky.jaal.agent.JaalException;

/**
 * <p>InjectorException class.</p>
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class InjectorException extends JaalException {

    /**
     * <p>Constructor for InjectorException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public InjectorException(String message) {
        super(message);
    }

    /**
     * <p>Constructor for InjectorException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause a {@link java.lang.Throwable} object.
     */
    public InjectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
