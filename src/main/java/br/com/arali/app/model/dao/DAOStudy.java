package br.com.arali.app.model.dao;

import br.com.arali.app.model.Deck;
import br.com.arali.app.model.Student;
import br.com.arali.app.model.Study;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.EntityFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOStudy extends DAODefault<Study> {

    public List<Study> findAllByStudent(Student student){
        EntityManager em      = EntityFactory.getInstance().createEntityManager();
        Query query           = em.createQuery("select st.* from study sy inner join student st on sy.student_fk = st.id where st.id = ?");
        query.setParameter(1, student.getId());
        List<Study> result    = query.getResultList();
        /*
        List<Study> studies   = new ArrayList<>();
        for (Object[] row : result) {
            Study study = new Study();
            study.setId((Long) row[0]);
            studies.add(study);
         */
        em.close();
        EntityFactory.close();
        return result;
    }

    public List<Study> findAllByStudentAndDeck(Student student, Deck deck){
        EntityManager em      = EntityFactory.getInstance().createEntityManager();
        Query query           = em.createQuery("select st.* from study sy " +
                "inner join student st on sy.student_fk = st.id " +
                "inner join card c on c.id = sy.card_fk " +
                "inner join decks_cards dc on c.id = dc.card_fk" +
                "where st.id = ? and dc.deck_fk = ?");
        query.setParameter(1, student.getId());
        query.setParameter(2, deck.getId());
        List<Study> result    = query.getResultList();
        /*
        List<Study> studies   = new ArrayList<>();
        for (Object[] row : result) {
            Study study = new Study();
            study.setId((Long) row[0]);
            studies.add(study);
         */
        em.close();
        EntityFactory.close();
        return result;
    }

    public Date getLastStudyByDeck(Deck deck) {
        Session session  = (Session) EntityFactory.getInstance().createEntityManager().getDelegate();
        NativeQuery query = session.createNativeQuery(" SELECT st.*, max(st.currentDate) as hidden FROM decks d\n" +
                "                                INNER JOIN decks_cards dc ON dc.deck_fk = d.id\n" +
                "                                INNER JOIN studies st ON st.card_fk = dc.card_fk\n" +
                "                                WHERE dc.deck_fk = :deck  \n" +
                "                                GROUP BY dc.deck_fk, st.id", Study.class);
        query.setParameter("deck", deck.getId());
        List<Study> result = query.getResultList();
        Study study        = new Study();
        if(result.size() > 0) {
            study = result.get(0);
        }
        return study.getCurrentDate();
    }
}
