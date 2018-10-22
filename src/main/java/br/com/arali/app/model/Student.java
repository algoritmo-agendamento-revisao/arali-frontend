package br.com.arali.app.model;

import java.util.List;

public class Student {
    private Integer id;
    private String name;
    private List<Deck> decks;

    public Study study(Card card) {
        return new Study();
    }
}
