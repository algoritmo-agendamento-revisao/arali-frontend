package br.com.arali.app;
import br.com.arali.app.handler.StudyHandler;
import br.com.arali.app.model.AIResponse;
import br.com.arali.app.model.Card;
import br.com.arali.app.model.Study;
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


        Spark.get("/study", (req, resp) -> {
            return new ModelAndView(new HashMap(), "src/main/resources/template/study.html");
        }, new MustacheTemplateEngine());


        Study study = new Study();
        study.setCard(new Card());
        study.setNumberOfRepetitions(456);
        study.setRight(true);
        study.setTimeForResolution(456465);

        StudyHandler handler  = new StudyHandler();
        AIResponse response = handler.learn(study);
        System.out.println(response.toString());
    }
}
