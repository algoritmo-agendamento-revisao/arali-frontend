package br.com.arali.app.controller;
import br.com.arali.app.model.dao.DAOCard;
import br.com.arali.app.util.Controller;
import br.com.arali.app.util.DefaultController;
import com.github.mirreck.FakeFactory;
import com.google.gson.Gson;
import br.com.arali.app.model.Card;
import spark.Request;
import spark.Response;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.List;

@Controller(resource="card")
public class CardController extends DefaultController {

    @Override
    public Object create(Request req, Response res) {
        res.type("application/json");
        Gson gson = new Gson();
        Card card = gson.fromJson(req.body(), Card.class);
        DAOCard dao = new DAOCard();
        try {
            card = dao.insert(card);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        res.status(202);
        return gson.toJson(card);
    }

    @Override
    public Object update(Request req, Response res) {
        return null;
    }

    @Override
    public Object delete(Request req, Response res) {
        return null;
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
