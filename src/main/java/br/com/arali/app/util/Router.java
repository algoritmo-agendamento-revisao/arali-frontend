package br.com.arali.app.util;

import org.reflections.Reflections;

import java.util.Set;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class Router {

    public static void load(){
        try {
            port(8080);
            staticFiles.externalLocation("src/main/resources/assets");
            Router.loadRoutes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadRoutes() throws Exception{
        Reflections reflections = new Reflections("br/com/arali/app/controller");
        Set<Class<? extends DefaultController>> classes = reflections.getSubTypesOf(DefaultController.class);
        for (Class c : classes){
            DefaultController controller = (DefaultController) c.getDeclaredConstructor().newInstance();
            if(c.isAnnotationPresent(Controller.class)){
                Controller annotation = (Controller) c.getAnnotation(Controller.class);
                controller.setResource(annotation.resource());
            }
            controller.load();
        }
    }

}
