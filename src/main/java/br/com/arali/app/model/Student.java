package br.com.arali.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany
    @JoinColumn(name = "deck_fk")
    private List<Deck> decks;

    public Study learn(Card card) {
        return new Study();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }
}
