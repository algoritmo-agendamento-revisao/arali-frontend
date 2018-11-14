package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
import br.com.arali.app.model.Option;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.List;

public class DAOOption implements DAO<Option> {

    @Override
    public Option insert(Option obj) throws SQLException, ClassNotFoundException, JAXBException {
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
    public boolean edit(Option obj) throws SQLException, ClassNotFoundException, JAXBException {
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
    public Option find(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        Option option = em.find(Option.class, id);
        em.close();
        return option;
    }

    @Override
    public List<Option> findAll() throws SQLException, ClassNotFoundException, JAXBException {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        List<Option> options = session.createCriteria(Option.class).list();
        session.close();
        return options;
    }

    @Override
    public boolean delete(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.remove(em.getReference(Option.class, id));
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }

    public List<Option> findAll(Card card) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        NativeQuery query = session.createNativeQuery("SELECT o.* from cards c" +
                " INNER JOIN options o ON o.card_fk = c.id" +
                " WHERE c.id = :card", Option.class);
        query.setParameter("card", card.getId());
        List<Option> list = query.getResultList();
        session.close();
        EntityFactory.close();
        return list;
    }
}
