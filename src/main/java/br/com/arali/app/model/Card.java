package br.com.arali.app.model;

import br.com.arali.app.model.dao.DAOCard;
import br.com.arali.app.model.dao.DAODeck;
import br.com.arali.app.model.dao.DAOOption;
import br.com.arali.app.model.dao.DAOTag;
import br.com.arali.app.util.DAO;
import javax.persistence.*;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name="cards")
public class Card {
    @Id
    @GeneratedValue
    private Long id;
    private String question;
    private Double difficulty;
    @OneToOne(optional=true, cascade=CascadeType.ALL)
    @JoinColumn(name="option_correct_id", referencedColumnName = "id", nullable = true)
    private Option optionCorrect;
    @Transient
    private Multimedia multimedia;
    @ManyToMany(mappedBy = "cards")
    @Transient
    private List<Deck> decks;
    @ManyToOne
    @JoinColumn(name = "tag_fk")
    private Tag tag;
    @OneToMany
    @JoinColumn(name = "card_fk")
    @Transient
    private List<Option> options;

    public Long getId() {
        return id;
    }

    public Card setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public Card setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void saveOptions(DAOOption dao) throws Exception {
        AtomicReference<Exception> exception = null;
        this.options.forEach((option) -> {
            try {
                option.setCardId(this.id);
                if(this.optionCorrect.getValue().equals(option.getValue())) {
                    option.setId(this.optionCorrect.getId());
                    dao.edit(option);
                }else {
                    option = (Option) dao.insert(option);
                }
            } catch (SQLException | ClassNotFoundException | JAXBException e) {
                exception.set(e);
            }
        });
        if(exception != null) throw exception.get();
    }


    public void saveRelations(DAODeck daoDeck) throws Exception {
        AtomicReference<Exception> exception = null;
        if(this.decks != null) {
            this.decks.forEach((deck) -> {
                try {
                    deck = daoDeck.find(deck.getId());
                    deck.addCard(this);
                    daoDeck.edit(deck);
                } catch (SQLException | ClassNotFoundException | JAXBException e) {
                    exception.set(e);
                }
            });
        }
        if(exception != null) throw exception.get();
    }
}
