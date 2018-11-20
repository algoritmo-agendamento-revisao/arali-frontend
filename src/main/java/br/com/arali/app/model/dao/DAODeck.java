package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
import br.com.arali.app.model.Deck;
import br.com.arali.app.model.Study;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import org.hibernate.bytecode.enhance.internal.bytebuddy.EnhancerImpl;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAODeck extends DAODefault<Deck> {

    public List<Deck> findAllWithoutCards() throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em      = EntityFactory.getInstance().createEntityManager();
        Query query           = em.createQuery("select id, label from decks");
        List<Object[]> result = query.getResultList();
        List<Deck> decks      = new ArrayList<>();
        for (Object[] row : result) {
            Deck deck = new Deck();
            deck.setId((Long) row[0]);
            deck.setLabel(row[1].toString());
            decks.add(deck);
        }
        em.close();
        EntityFactory.close();
        return decks;
    }

    public Integer getQtyCards(Long id) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        Query query = session.createNativeQuery("SELECT c.* from decks d " +
                "                INNER JOIN decks_cards dc ON d.id = dc.deck_fk " +
                "                INNER JOIN cards c ON c.id = dc.card_fk  " +
                "                WHERE dc.deck_fk = :deck GROUP BY dc.card_fk", Card.class);
        query.setParameter("deck", id);
        List<Card> result = query.getResultList();
        session.close();
        EntityFactory.close();
        return result.size();
    }

    public Integer getQtyOfStudiedCards(Long id) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        NativeQuery query = session.createNativeQuery(" SELECT st.* FROM decks d\n" +
                "                INNER JOIN decks_cards dc ON dc.deck_fk = d.id\n" +
                "                LEFT JOIN studies st ON st.card_fk = dc.card_fk\n" +
                "                WHERE dc.deck_fk = :deck AND datediff(st.nextRepetition, now()) > 0 " +
                "                GROUP BY dc.card_fk, st.id", Study.class);
        query.setParameter("deck", id);
        List<Study> result = query.getResultList();
        session.close();
        EntityFactory.close();
        return result.size();
    }
}
