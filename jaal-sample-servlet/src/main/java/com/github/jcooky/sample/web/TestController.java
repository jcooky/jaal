package com.github.jcooky.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author user1
 * @date 2015-05-26
 */
@Controller
@RequestMapping("/")
public class TestController {

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public String helloWorld() throws Exception {
    String str = "hello World";

    return str;
  }
}
