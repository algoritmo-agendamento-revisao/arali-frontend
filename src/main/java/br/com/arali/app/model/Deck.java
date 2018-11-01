package br.com.arali.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private List<Card> cards;
}
