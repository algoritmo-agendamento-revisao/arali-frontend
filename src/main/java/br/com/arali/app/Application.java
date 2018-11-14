package br.com.arali.app;
import br.com.arali.app.handler.StudyHandler;
import br.com.arali.app.model.AIResponse;
import br.com.arali.app.model.Card;
import br.com.arali.app.model.Study;
import br.com.arali.app.model.Deck;
import br.com.arali.app.model.dao.DAOCard;
import br.com.arali.app.model.dao.DAODeck;
import br.com.arali.app.util.Router;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.List;

public class Application {
    public static void main(String args[]){
        Router.load();

        Spark.get("/", (req, resp) -> {
            return new ModelAndView(null, "src/main/resources/template/index.html");
        }, new MustacheTemplateEngine());

        Spark.get("/study/deck/:id", (req, resp) -> {
            Integer id         = Integer.parseInt(req.params("id"));
            DAOCard daoCard    = new DAOCard();
            Card  card         = daoCard.findByDeckAndNotStudied(id);
            return new Gson().toJson(card);
        });

        Spark.get("/study/:id", (req, resp) -> {
            HashMap data       = new HashMap();
            DAODeck daoDeck    = new DAODeck();
            Integer id         = Integer.parseInt(req.params("id"));
            Integer total      = daoDeck.getQtyCards(id);
            Integer qtyStudied = daoDeck.getQtyOfStudiedCards(id);
            total              = (total == 0) ? 1 : total;
            data.put("total", total);
            data.put("qtyStudied", qtyStudied);
            data.put("percentProgress", Math.floor(qtyStudied/total));
            data.put("deck_id", id);
            return new ModelAndView(data, "src/main/resources/template/study.html");
        }, new MustacheTemplateEngine());


        /*
        Study study = new Study();
        study.setCard(new Card());
        study.setNumberOfRepetitions(456);
        study.setRight(true);
        study.setTimeForResolution(456465);

        StudyHandler handler  = new StudyHandler();
        AIResponse response = handler.learn(study);
        System.out.println(response.toString());
        */
    }
}
