package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ScriptEngine javaScriptEngine() throws ScriptException {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        scriptEngine.eval(String.format("load('%s/jvm-npm.js');", System.getProperty("user.home")));
        scriptEngine.eval(String.format("const jp = require('%s/jsonpath.js');", System.getProperty("user.home")));
        return scriptEngine;
    }

}
