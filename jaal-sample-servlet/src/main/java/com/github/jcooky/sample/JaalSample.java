package com.github.jcooky.sample;


import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * @author user1
 * @date 2015-05-26
 */
@SpringBootApplication
public class JaalSample {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(JaalSample.class, args);
  }
}