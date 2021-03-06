package br.com.arali.app.controller;

import br.com.arali.app.handler.StudyHandler;
import br.com.arali.app.model.*;
import br.com.arali.app.model.dao.*;
import br.com.arali.app.util.Controller;
import br.com.arali.app.util.DefaultController;
import br.com.arali.app.util.Util;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller(resource = "studies")
public class StudyController extends DefaultController {

    public StudyController(){
        Spark.get("/study/deck/:id", (req, resp) -> {
            Integer id           = Integer.parseInt(req.params("id"));
            DAOCard daoCard      = new DAOCard();
            Card  card           = daoCard.findByDeckAndNotStudied(id);
            if(card != null && card.getOptions() != null && card.getOptions().size() > 0) {
                Collections.shuffle(card.getOptions());
            }
            return new Gson().toJson((card != null) ? card : new Object());
        });

        Spark.get("/study/:id", (req, resp) -> {
            HashMap data       = new HashMap();
            DAODeck daoDeck    = new DAODeck();
            Long id            = Long.parseLong(req.params("id"));
            Integer total      = daoDeck.getQtyCards(id);
            Integer qtyStudied = daoDeck.getQtyOfStudiedCards(id);
            total              = (total == 0) ? 1 : total;
            Double percent     = (qtyStudied.doubleValue() / total.doubleValue()) * 100;
            data.put("total", total);
            data.put("qtyStudied", qtyStudied);
            data.put("percentProgress", (int) Math.round(percent));
            data.put("deck_id", id);
            return new ModelAndView(data, "src/main/resources/template/study.html");
        }, new MustacheTemplateEngine());
    }

    @Override
    public Object create(Request req, Response res) {
        res.type("application/json");
        Gson gson         = new Gson();
        Study study       = gson.fromJson(req.body(), Study.class);
        study.setCurrentDate(new Date());
        study.setNextRepetition(Util.sumDays(new Date(), 1));
        DAOStudy daoStudy = new DAOStudy();
        try {
            Student student = new Student();
            student.setId(11l);
            study.setStudent(student);
            study = daoStudy.insert(study);
            sendToAI(study);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
            res.status(505);
        } catch (Exception e) {
            res.status(505);
            e.printStackTrace();
        }
        return gson.toJson(study);
    }

    @Override
    public Object update(Request req, Response res) {
        return null;
    }

    @Override
    public Object delete(Request req, Response res, Long id) {
        return null;
    }

    @Override
    public Object find(Request req, Response res, Long id) {
        return null;
    }

    @Override
    public Object findAll(Request req, Response res) {
        return null;
    }

    public Thread sendToAI(final Study study) {
        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DAOStudy daoStudy        = new DAOStudy();
                    DAOCard daoCard          = new DAOCard();
                    StudyHandler handler     = new StudyHandler();
                    daoStudy.prepStudy(study);
                    AIResponse response = handler.learn(study);
                    System.out.println(response.toString());
                    study.setNextRepetition(response.getNextRepetition());
                    daoStudy.edit(study);
                    daoCard.updateCardByAIResponse(response);
                }catch (Exception e){
                    System.out.println("Erro na comunicação com o CORE");
                }
            }
        });
        run.start();
        return run;
    }





}
