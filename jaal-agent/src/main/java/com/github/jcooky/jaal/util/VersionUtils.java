package com.github.jcooky.jaal.util;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Created by JCooky on 15. 3. 8..
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

  public static String getVersion() {
    return getVersionUtil().version;
  }

  public static String getBuiltBy() {
    return getVersionUtil().builtBy;
  }

  public static String getBuiltOn() {
    return getVersionUtil().buildOn;
  }
}
