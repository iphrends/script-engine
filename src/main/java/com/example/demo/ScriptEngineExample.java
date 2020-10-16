package com.example.demo;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ScriptEngineExample {
    static String pathExpression = "$[?(@.integer == 12343 || @.boolean == true)]";
    private final ScriptEngine javaScriptEngine;
    String json;

    public ScriptEngineExample(ScriptEngine javaScriptEngine) {
        this.javaScriptEngine = javaScriptEngine;
        try {
            json = FileUtils.readFileToString(ResourceUtils.getFile("classpath:test.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * have to pass below vm argument
     * -Dnashorn.args=--language=es6
     *
     * @throws ScriptException while processing script
     */
    @PostConstruct
    public void test() throws ScriptException {
        javaScriptEngine.eval(String.format("var output = jp.query(JSON.parse('%s'), '%s');", json, pathExpression));
        Object output = javaScriptEngine.get("output");
        ScriptObjectMirror mirror = (ScriptObjectMirror) output;
        boolean isArray = mirror.isArray(); //always true - just for safe check
        if (isArray) {
            Set<Map.Entry<String, Object>> entries = mirror.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                ScriptObjectMirror object = (ScriptObjectMirror) entry.getValue();
                if (!object.isArray()) {
                    object.forEach((key, value) -> log.info("output -> {} : {}", key, value));
                }
            }
        }
    }
}
