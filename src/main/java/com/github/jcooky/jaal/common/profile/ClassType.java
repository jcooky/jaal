package com.github.jcooky.jaal.common.profile;

import java.io.Serializable;

/**
 * Created by JCooky on 15. 6. 22..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class ClassType {
  private String packageName;
  private String name;

  /**
   * <p>Constructor for ClassType.</p>
   *
   * @param packageName a {@link java.lang.String} object.
   * @param className a {@link java.lang.String} object.
   */
  public ClassType(String packageName, String className) {
    this.packageName = packageName;
    this.name = className;
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
   * <p>Getter for the field <code>packageName</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getPackageName() {
    return packageName;
  }

}
