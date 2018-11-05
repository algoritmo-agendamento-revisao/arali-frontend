package br.com.arali.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue
    private Long id;
    private String label;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "decks_cards", joinColumns = @JoinColumn(name = "deck_fk", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "card_fk", referencedColumnName = "id"))
    private List<Card> cards;

    public Long getId() {
        return this.id;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }
}
