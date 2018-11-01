package br.com.arali.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany
    private List<Deck> decks;

    public Study learn(Card card) {
        return new Study();
    }
}
