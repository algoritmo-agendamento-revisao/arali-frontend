package br.com.arali.app.model;

import br.com.arali.app.model.dao.DAOStudy;
import br.com.arali.app.util.DAO;

import java.util.Date;

public class DeckList extends Deck {
    private Integer qtyCards;
    private Integer qtyStudiedCards;
    private Date dateOfLastStudy;

    public DeckList(){}

    public DeckList(Deck deck, DAOStudy daoStudy){
        this.id       = deck.getId();
        this.cards    = deck.getCards();
        this.label    = deck.getLabel();
        this.qtyCards = this.cards.size();
    }

    public Integer getQtyCards() {
        return qtyCards;
    }

    public void setQtyCards(Integer qtyCards) {
        this.qtyCards = qtyCards;
    }

    public Integer getQtyStudiedCards() {
        return qtyStudiedCards;
    }

    public void setQtyStudiedCards(Integer qtyStudiedCards) {
        this.qtyStudiedCards = qtyStudiedCards;
    }

    public Date getDateOfLastStudy() {
        return dateOfLastStudy;
    }

    public void setDateOfLastStudy(Date dateOfLastStudy) {
        this.dateOfLastStudy = dateOfLastStudy;
    }


}
