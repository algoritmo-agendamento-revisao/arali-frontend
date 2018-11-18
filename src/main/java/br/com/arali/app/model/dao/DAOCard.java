package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
import br.com.arali.app.model.Deck;
import br.com.arali.app.model.Option;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DAOCard extends DAODefault<Card> {

    public Card findByDeckAndNotStudied(Integer id) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        NativeQuery query = session.createNativeQuery("SELECT c.* from cards c \n" +
                "INNER JOIN decks_cards dc ON dc.card_fk = c.id \n" +
                "LEFT JOIN studies s ON s.card_fk = c.id\n" +
                " WHERE dc.deck_fk = :deck AND (datediff(s.nextRepetition, now()) <= 0 OR s.nextRepetition is null)", Card.class);
        query.setParameter("deck", id);
        List<Card> list = query.getResultList();
        session.close();

        //mistura os cards
        Collections.shuffle(list);

        EntityFactory.close();
        Card card = null;
        if(list.size() > 0) {
            card = list.get(0);
            DAOOption dao = new DAOOption();
            card.setOptions(dao.findAll(card));
        }
        return card;
    }

    public void insert(Deck deck) throws Exception {
        DAOOption daoOption = new DAOOption();
        DAOTag daoTag       = new DAOTag();
        DAODeck daoDeck     = new DAODeck();
        for(Card card : deck.getCards()){
            card.setTag(daoTag.insert(card));
            card = this.insert(card);
            card.saveOptions(daoOption);
        }
    }

    public void saveRelations(Deck deck) throws Exception {
        DAODeck daoDeck = new DAODeck();
        for(Card card : deck.getCards()){
            card.addDeck(deck);
            card.saveRelations(daoDeck);
        }
    }
}