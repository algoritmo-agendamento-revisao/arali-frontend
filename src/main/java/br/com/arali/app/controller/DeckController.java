package br.com.arali.app.controller;

import br.com.arali.app.model.*;
import br.com.arali.app.model.dao.DAOCard;
import br.com.arali.app.model.dao.DAODeck;
import br.com.arali.app.model.dao.DAOStudy;
import br.com.arali.app.model.dao.DAOTag;
import br.com.arali.app.util.Controller;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.DefaultController;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller(resource = "decks")
public class DeckController extends DefaultController {

    public DeckController(){
        Spark.get("/deck", (req, res) -> {
            return new ModelAndView(new HashMap(), "src/main/resources/template/deck.html");
        }, new MustacheTemplateEngine());

        Spark.get("/decks/not/:params", (req, res) -> {
            String params     = req.params("params");
            List<Deck> decks  = null;
            DAODeck dao       = new DAODeck();
            DAOStudy daoStudy = new DAOStudy();
            List<DeckList> list = new ArrayList<>();
            try {
                if(params.equals("cards")) {
                    decks = dao.findAllWithoutCards();
                    Student student = new Student();
                    student.setId(1l);
                    for(Deck deck : decks) {
                        DeckList dl    = new DeckList();
                        List<Study> st = daoStudy.findAllByStudentAndDeck(student, deck);
                        dl.setQtyStudiedCards(st.size());
                        dl.setDateOfLastStudy(Study.getDateOfLastStudy(st));
                        list.add(dl);
                    }
                }
            } catch (SQLException | ClassNotFoundException | JAXBException e) {
                e.printStackTrace();
            }
            return new Gson().toJson(list);
        });
    }

    @Override
    public Object create(Request req, Response res) {
        DAO<Deck> dao = new DAODeck();
        Gson gson     = new Gson();
        Deck deck     = gson.fromJson(req.body(), Deck.class);
        try {
            deck = dao.insert(deck);
            res.status(202);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
            res.status(505);
        }
        return gson.toJson(deck);
    }

    @Override
    public Object update(Request req, Response res) {
        return null;
    }

    @Override
    public Object delete(Request req, Response res, Long id) {
        Gson gson      = new Gson();
        DAODeck dao    = new DAODeck();
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
        Deck deck = null;
        try {
            deck = new DAODeck().find(id);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(deck);
    }

    @Override
    public Object findAll(Request req, Response res) {
        List<Deck> decks = null;
        try {
            decks = new DAODeck().findAll();
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(decks);
    }

}
