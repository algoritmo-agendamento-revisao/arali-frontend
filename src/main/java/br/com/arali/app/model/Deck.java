package br.com.arali.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue
    private Long id;
    private String label;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Transient
    private List<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }
}
