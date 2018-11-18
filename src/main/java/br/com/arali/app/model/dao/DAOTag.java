package br.com.arali.app.model.dao;

import br.com.arali.app.model.Card;
import br.com.arali.app.model.Deck;
import br.com.arali.app.model.Tag;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOTag extends DAODefault<Tag> {

    public Tag insert(Card card) throws JAXBException, SQLException, ClassNotFoundException {
        Tag tag = this.findByLabel(card.getTag().getLabel());
        if(tag == null){
            tag = this.insert(card.getTag());
        }
        return tag;
    }

    private Tag findByLabel(String label) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        Query query = session.createNativeQuery("SELECT * from tags where label = :label", Tag.class);
        query.setParameter("label", label);
        List<Tag> tags = query.getResultList();
        return (tags.size() > 0) ? tags.get(0) : null;
    }
}
