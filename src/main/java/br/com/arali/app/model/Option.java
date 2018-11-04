package br.com.arali.app.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "Options")
public class Option {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "card_fk")
    private Long cardId;
    private String value;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getId() {
        return this.id;
    }

    public String getValue(){
        return this.value;
    }

    public void setId(Long id) {
        this.id = id;
    }
}