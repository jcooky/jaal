package com.github.jcooky.jaal.util;

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

import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Created by JCooky on 15. 3. 8..
 *
 * @author JCooky
 * @version $Id: $Id
 */
public class VersionUtils {
  private static VersionUtils versionUtil = null;
  private String version;
  private String buildOn;
  private String builtBy;

  private VersionUtils() {
    try {
      Manifest manifest = new Manifest(ClassLoader.getSystemResourceAsStream("META-INF/MANIFEST.MF"));
      Attributes properties =  manifest.getMainAttributes();

      version = (String) properties.get("Jexi-Version");
      buildOn = (String) properties.get("Jexi-Built-On");
      builtBy = (String) properties.get("Jexi-Built-By");
    } catch (Exception e) {
      version = buildOn = builtBy = "?";
    }
  }

  private static synchronized VersionUtils getVersionUtil() {
    if (versionUtil == null) {
      versionUtil = new VersionUtils();
    }

    return versionUtil;
  }

  /**
   * <p>Getter for the field <code>version</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public static String getVersion() {
    return getVersionUtil().version;
  }

  /**
   * <p>Getter for the field <code>builtBy</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public static String getBuiltBy() {
    return getVersionUtil().builtBy;
  }

  /**
   * <p>getBuiltOn.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public static String getBuiltOn() {
    return getVersionUtil().buildOn;
  }
}
