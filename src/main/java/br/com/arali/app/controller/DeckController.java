package br.com.arali.app.controller;

import br.com.arali.app.model.*;
import br.com.arali.app.model.dao.*;
import br.com.arali.app.util.Controller;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.DefaultController;
import br.com.arali.app.util.XMLParser;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.CharSet;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import javax.servlet.MultipartConfigElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
            try {
                if(params.equals("cards")) {
                    decks = dao.findAllWithoutCards();
                    decks.forEach((deck) -> {
                        Integer total      = dao.getQtyCards(deck.getId());
                        Integer qtyStudied = dao.getQtyOfStudiedCards(deck.getId());
                        Date lastStudy     = daoStudy.getLastStudyByDeck(deck);
                        deck.setTotal(total);
                        deck.setQtyStudied(qtyStudied);
                        deck.setLastStudy(lastStudy);
                    });
                }
            } catch (SQLException | ClassNotFoundException | JAXBException e) {
                e.printStackTrace();
            }
            return new Gson().toJson(decks);
        });

        Spark.get("/export/:id", (req, res) -> {
            res.type("text/xml");
            Long id               = Long.parseLong(req.params("id"));
            DAODeck daoDeck       = new DAODeck();
            DAOOption daoOption   = new DAOOption();
            Deck deck             = daoDeck.find(id);
            for (Card card : deck.getCards()) {
                List<Option> options = daoOption.findAll(card);
                card.setOptions(options);
            }
            File xml              = new File("export.xml");
            XMLParser<Deck> parse = new XMLParser<Deck>(xml);
            parse.saveFile(deck, new Class[]{
                Deck.class,
                Option.class,
                Card.class,
                Tag.class
            });
            return Files.toString(xml, Charsets.UTF_8);
        });

        Spark.post("/import", (req, res) ->{
            boolean isOk  = true;
            try {
                File file = new File("import.xml");
                req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
                try (InputStream input = req.raw().getPart("fileUpload").getInputStream()) {
                    FileUtils.copyInputStreamToFile(input, file);
                }
                XMLParser<Deck> parse = new XMLParser<Deck>(file);
                Deck deck = parse.toObject(new Class[]{
                        Deck.class,
                        Option.class,
                        Card.class,
                        Tag.class
                });

                DAODeck daoDeck = new DAODeck();
                DAOCard daoCard = new DAOCard();
                daoCard.insert(deck);
                List<Card> cards = new ArrayList<>(deck.getCards());
                deck.setCards(new ArrayList<>());
                daoDeck.insert(deck);
                deck.setCards(cards);
                daoCard.saveRelations(deck);
            }catch (Exception e){
                isOk = false;
            }
            return "{ \"isOk\":"+ isOk + "}";
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
