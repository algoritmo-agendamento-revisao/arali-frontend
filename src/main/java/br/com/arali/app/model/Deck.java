package br.com.arali.app.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@XmlRootElement
@Entity(name = "decks")
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue
    protected Long id;
    protected String label;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "decks_cards", joinColumns = @JoinColumn(name = "deck_fk", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "card_fk", referencedColumnName = "id"))
    @XmlElementWrapper(name = "cards")
    @XmlElement(name ="card")
    protected List<Card> cards;
    @Transient
    private Integer totalCards;
    @Transient
    private Integer qtyStudiedCards;
    @Transient
    private Date lastStudy;

    public Deck(){
        this.cards = new ArrayList<>();
    }


    @XmlTransient
    public Long getId() {
        return this.id;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public String getLabel() {
        return this.label;
    }

    public void setTotal(Integer total) {
        this.totalCards = total;
    }

    public void setQtyStudied(Integer qtyStudied) {
        this.qtyStudiedCards = qtyStudied;
    }

    public void setLastStudy(Date lastStudy) {
        this.lastStudy = lastStudy;
    }

    @XmlTransient
    public void setCards(List<Card> objects) {
        this.cards = objects;
    }
}
