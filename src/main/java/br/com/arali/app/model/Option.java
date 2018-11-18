package br.com.arali.app.model;

import com.sun.xml.txw2.annotation.XmlElement;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigInteger;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "card_fk")
    private Long cardId;
    private String value;


    @XmlTransient
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }


    @XmlTransient
    public Long getId() {
        return this.id;
    }

    public String getValue(){
        return this.value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

}