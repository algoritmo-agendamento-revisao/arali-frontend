package br.com.arali.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="cards")
public class Card {
    @Id
    @GeneratedValue
    private Long id;
    private Double difficulty;
    private List<Tag> tags;
    private List<Option> options;
    private Option optionCorrect;
    private Multimedia multimedia;

    public Long getId() {
        return id;
    }

    public Card setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public Card setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
        return this;
    }
}
