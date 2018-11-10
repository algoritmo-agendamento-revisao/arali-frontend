package br.com.arali.app.model;

import com.google.gson.Gson;

import java.util.Date;

public class AIResponse {
    private Card card;
    private Date nextRepetition;
    private Boolean completed;

    public String toString(){
        return new Gson().toJson(this);
    }
}
