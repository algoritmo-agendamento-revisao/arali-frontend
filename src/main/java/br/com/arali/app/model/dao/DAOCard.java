package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
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
        Card card = new Card();
        card.setId(id);
        em.remove(card);
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }
}
