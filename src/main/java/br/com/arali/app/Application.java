package br.com.arali.app;
import br.com.arali.app.util.Router;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Application {
    public static void main(String args[]){
        Router.load();

        Spark.get("/", (req, resp) -> {
            return new ModelAndView(new HashMap(), "src/main/resources/template/index.html");
        }, new MustacheTemplateEngine());
    }
}
