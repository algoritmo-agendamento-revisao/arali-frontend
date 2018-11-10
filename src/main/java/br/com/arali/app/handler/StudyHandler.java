package br.com.arali.app.handler;

import br.com.arali.app.model.AIResponse;
import br.com.arali.app.model.Study;
import br.com.arali.app.util.RestClient;
import org.springframework.web.client.RestTemplate;

public class StudyHandler {
    private RestClient client;

    public StudyHandler(){
        this.client = new RestClient("http://192.168.0.100:5000/estudo");
        this.client.setRestTemplate(new RestTemplate());
    }

    public AIResponse learn(Study study){
        return this.client.create(study, AIResponse.class);
    }
}
