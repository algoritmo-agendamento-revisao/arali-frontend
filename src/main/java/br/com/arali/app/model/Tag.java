package br.com.arali.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String label;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "tags_cards", joinColumns = @JoinColumn(name = "tag_fk", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "card_fk", referencedColumnName = "id"))
    private List<Card> cards;

    public Tag(){
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public Long getId() {
        return this.id;
    }
}