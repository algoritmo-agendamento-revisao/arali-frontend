package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
import br.com.arali.app.model.Deck;
import br.com.arali.app.model.Study;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAODeck implements DAO<Deck> {
    @Override
    public Deck insert(Deck obj) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return obj;
    }

    @Override
    public boolean edit(Deck obj) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }

    @Override
    public Deck find(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        Deck deck = em.find(Deck.class, id);
        em.close();
        return deck;
    }

    @Override
    public List<Deck> findAll() throws SQLException, ClassNotFoundException, JAXBException {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        List<Deck> decks = session.createCriteria(Deck.class).list();
        session.close();
        EntityFactory.close();
        return decks;
    }

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
        return decks;
    }

    @Override
    public boolean delete(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.remove(em.getReference(Deck.class, id));
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }

    public Integer getQtyCards(Long id) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        Query query = session.createNativeQuery("SELECT c.* from decks d " +
                "                INNER JOIN decks_cards dc ON d.id = dc.deck_fk " +
                "                INNER JOIN cards c ON c.id = dc.card_fk  " +
                "                WHERE dc.deck_fk = :deck GROUP BY dc.card_fk", Card.class);
        query.setParameter("deck", id);
        List<Card> result = query.getResultList();
        return result.size();
    }

    public Integer getQtyOfStudiedCards(Long id) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        NativeQuery query = session.createNativeQuery(" SELECT st.* FROM decks d\n" +
                "                INNER JOIN decks_cards dc ON dc.deck_fk = d.id\n" +
                "                INNER JOIN studies st ON st.card_fk = dc.card_fk\n" +
                "                WHERE dc.deck_fk = :deck AND datediff(st.nextRepetition, now()) > 0 " +
                "                GROUP BY dc.card_fk", Study.class);
        query.setParameter("deck", id);
        List<Study> result = query.getResultList();
        return result.size();
    }
}
