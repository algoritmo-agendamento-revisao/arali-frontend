package br.com.arali.app.model;

import com.google.gson.Gson;

import java.util.Date;

public class AIResponse {
    private Card card;
    private Date nextRepetition;
    private Boolean completed;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Date getNextRepetition() {
        return nextRepetition;
    }

    public void setNextRepetition(Date nextRepetition) {
        this.nextRepetition = nextRepetition;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String toString(){
        return new Gson().toJson(this);
    }
}
