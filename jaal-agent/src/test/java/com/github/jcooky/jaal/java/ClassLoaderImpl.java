package com.github.jcooky.jaal.java;

/**
 * Created by JCooky on 15. 2. 11..
 */
public class ClassLoaderImpl extends ClassLoader {

  public Class<?> defineClass(byte []data) {
    return super.defineClass(data, 0, data.length);
  }

  public Class<?> defineClass(String name, byte[] data) throws ClassFormatError {
    return super.defineClass(name, data, 0, data.length);
  }


}
