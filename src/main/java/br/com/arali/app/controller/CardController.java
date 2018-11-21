package br.com.arali.app.controller;
import br.com.arali.app.model.Multimedia;
import br.com.arali.app.model.dao.*;
import br.com.arali.app.util.Controller;
import br.com.arali.app.util.DefaultController;
import com.github.mirreck.FakeFactory;
import com.google.gson.Gson;
import br.com.arali.app.model.Card;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Controller(resource="cards")
public class CardController extends DefaultController {

    public CardController(){
        Spark.get("/card", (req, res) -> {
            return new ModelAndView(new HashMap(), "src/main/resources/template/card.html");
        }, new MustacheTemplateEngine());

        Spark.get("/card/create", (req, res) -> {
            return new ModelAndView(new HashMap(), "src/main/resources/template/card_create.html");
        }, new MustacheTemplateEngine());
    }

    @Override
    public Object create(Request req, Response res) {
        res.type("application/json");
        Gson gson = new Gson();
        Card card = gson.fromJson(req.body(), Card.class);
        DAOCard daoCard = new DAOCard();
        DAODeck daoDeck = new DAODeck();
        DAOTag daoTag   = new DAOTag();
        try {
            card = daoCard.insert(card);
            card.saveOptions(new DAOOption());
            card.saveRelations(daoDeck);
            res.status(202);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
            res.status(505);
        } catch (Exception e) {
            res.status(505);
            e.printStackTrace();
        }
        return gson.toJson(card);
    }

    @Override
    public Object update(Request req, Response res) {
        return null;
    }

    @Override
    public Object delete(Request req, Response res, Long id) {
        Gson gson      = new Gson();
        DAOCard dao    = new DAOCard();
        boolean result = false;
        try {
            result = dao.delete(id);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    @Override
    public Object find(Request req, Response res, Long id) {
        Card card = null;
        try {
            card = new DAOCard().find(id);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(card);
    }

    @Override
    public Object findAll(Request req, Response res) {
        List<Card> cards = null;
        try {
            cards = new DAOCard().findAll();
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(cards);
    }
}