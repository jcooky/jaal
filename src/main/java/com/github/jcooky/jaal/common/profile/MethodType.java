package com.github.jcooky.jaal.common.profile;

/**
 * Created by JCooky on 15. 6. 22..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class MethodType {
  private int access;
  private String name;
  private String descriptor;

  /**
   * <p>Constructor for MethodType.</p>
   *
   * @param access a int.
   * @param name a {@link java.lang.String} object.
   * @param descriptor a {@link java.lang.String} object.
   */
  public MethodType(int access, String name, String descriptor) {
    this.access = access;
    this.name = name;
    this.descriptor = descriptor;
  }

  /**
   * <p>Getter for the field <code>access</code>.</p>
   *
   * @return a int.
   */
  public int getAccess() {
    return access;
  }

  /**
   * <p>Getter for the field <code>name</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return name;
  }

  /**
   * <p>Getter for the field <code>descriptor</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getDescriptor() {
    return descriptor;
  }
}
