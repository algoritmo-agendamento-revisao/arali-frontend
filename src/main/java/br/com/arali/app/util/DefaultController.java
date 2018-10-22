package br.com.arali.app.util;

import spark.Request;
import spark.Response;

public abstract class DefaultController {
    protected String resource;

    public DefaultController(){
        this.resource = resource;
    }

    public abstract Object create(Request req, Response res);
    public abstract Object update(Request req, Response res);
    public abstract Object delete(Request req, Response res);
    public abstract Object find(Request req, Response res, Long id);
    public abstract Object findAll(Request req, Response res);

    public void setResource(String resource){
        this.resource = resource;
    }

    public void load(){
        spark.Spark.post(resource, (req, res) -> create(req, res));
        spark.Spark.put(resource, (req, res) -> update(req, res));
        spark.Spark.delete(resource, (req, res) -> delete(req, res));
        spark.Spark.get(resource, (req, res) -> findAll(req, res));
        spark.Spark.get(resource + "/:id", (req, res) -> {
            return find(req, res, Long.parseLong(req.params("id")));
        });
    }
}
