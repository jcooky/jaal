# Jaal - JAva Agent Library [![Build Status](https://travis-ci.org/jcooky/jaal.svg?branch=master)](https://travis-ci.org/jcooky/jaal)

Jaal is helper for javaagent library. quickly make prototype javaagent using Jaal. Javaagent can be inject .class file from memory inner JVM. If you don`t want to handle byte code directly, use Jaal to make javaagent. Then, you indirectly handle byte code through Jaal

#Requirements
* JDK version 1.6
* Maven

#How to use
See an [sample](https://github.com/jcooky/jaal/tree/master/jaal-sample-servlet).<br>
It is sample to run the webapp with spring-boot. [jaal-sample-servlet](https://github.com/jcooky/jaal/tree/master/jaal-sample-servlet) is run on JVM with [jaal-sample-agent](https://github.com/jcooky/jaal/tree/master/jaal-sample-agent) that is purpose for feature of method proxy.
These is how to use Jaal.
