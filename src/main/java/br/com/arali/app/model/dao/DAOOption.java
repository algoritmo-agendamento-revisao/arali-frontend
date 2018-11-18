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

public class DAOOption extends DAODefault<Option> {
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
