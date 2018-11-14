package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
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

public class DAOCard implements DAO<Card> {

    @Override
    public Card insert(Card obj) throws SQLException, ClassNotFoundException, JAXBException {
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
    public boolean edit(Card obj) throws SQLException, ClassNotFoundException, JAXBException {
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
    public Card find(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        Card card = em.find(Card.class, id);
        em.close();
        return card;
    }

    @Override
    public List<Card> findAll() throws SQLException, ClassNotFoundException, JAXBException {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        List<Card> cards = session.createCriteria(Card.class).list();
        session.close();
        return cards;
    }

    @Override
    public boolean delete(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.remove(em.getReference(Card.class, id));
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }

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
}