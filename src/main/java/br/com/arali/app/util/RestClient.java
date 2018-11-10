package br.com.arali.app.util;

import com.google.gson.Gson;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RestClient {
    private String baseUrl;
    private RestTemplate restTemplate;

    public RestClient(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public <T> T create(Object obj, Class<T> class1){
        try{
            String res 	= this.restTemplate.postForObject(baseUrl, obj, String.class);
            if(res instanceof String){;
                return (T) new Gson().fromJson(res, class1);
            }else{
                return (T) obj;
            }
        }catch(HttpClientErrorException e){
            System.out.println("deu erro");
            System.out.println(e.getMessage());
            System.out.println(e.getResponseBodyAsString());
            return null;
        }
    }
}
