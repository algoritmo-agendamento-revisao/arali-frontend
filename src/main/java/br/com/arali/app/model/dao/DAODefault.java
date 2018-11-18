package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
import br.com.arali.app.model.Deck;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

public class DAODefault<E> implements DAO<E> {
    @Override
    public E insert(E obj) throws SQLException, ClassNotFoundException, JAXBException {
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
    public boolean edit(E obj) throws SQLException, ClassNotFoundException, JAXBException {
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
    public E find(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        E obj = (E) em.find(getClassOfGeneric(), id);
        em.close();
        return obj;
    }

    @Override
    public List<E> findAll() throws SQLException, ClassNotFoundException, JAXBException {
        System.out.println("teste");
        Class a = getClassOfGeneric();
        Session session = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        List<E> objs    = session.createCriteria(getClassOfGeneric()).list();
        session.close();
        return objs;
    }

    @Override
    public boolean delete(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        EntityManager em = EntityFactory.getInstance().createEntityManager();
        em.getTransaction().begin();
        em.remove(em.getReference(getClassOfGeneric(), id));
        em.getTransaction().commit();
        boolean result = em.getTransaction().getRollbackOnly();
        if(result) em.getTransaction().rollback();
        em.close();
        EntityFactory.close();
        return !result;
    }

    public Class<E> getClassOfGeneric(){
        return (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
