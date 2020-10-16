package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.util.ResourceUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ScriptEngineExample {
    static String json;
    static String pathExpression = "$[?(@.integer == 12343 || @.boolean == true)]";

    static {
        try {
            json = FileUtils.readFileToString(ResourceUtils.getFile("classpath:test.json"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) throws ScriptException, IOException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        String condition = "$[?(@.integer == 12343 || @.boolean == true)]";

        File file = ResourceUtils.getFile("classpath:test.json");
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

//        String javaScript = "var obj = new jsonpath().query(jsonInput, condition);";

        engine.eval(new FileReader(new File("/Users/kashif/Documents/workspace/javascript/script-engine/src/main/resources/jsonpath.js")));
        Invocable invocable = (Invocable) engine;
        Object query = invocable.invokeFunction("query", json, condition);

//        Bindings bindings = engine.createBindings();
//        bindings.put("condition", condition);
//        bindings.put("jsonInput", json);

//        engine.eval(javaScript, bindings);

//        Object output = engine.get("obj");
        log.info("{}", query);
    }*/

    public static void main(String[] args) throws IOException, ScriptException {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        scriptEngine.eval("const jp = load('/Users/kashif/Documents/workspace/javascript/script-engine/src/main/resources/jsonpath.js');");
        scriptEngine.eval(String.format("let output = jp.query(JSON.parse('%s'), '%s');", json, pathExpression));
        Object output = scriptEngine.get("output");
        log.info("{}", output);
    }
}
