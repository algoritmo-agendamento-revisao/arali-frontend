package br.com.arali.app.model;

import br.com.arali.app.model.dao.DAOCard;
import br.com.arali.app.model.dao.DAODeck;
import br.com.arali.app.model.dao.DAOOption;
import br.com.arali.app.model.dao.DAOTag;
import br.com.arali.app.util.DAO;

import javax.persistence.*;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Entity(name = "cards")
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
    @OneToOne(optional=true, cascade=CascadeType.ALL)
    @JoinColumn(name="multimedia_fk", referencedColumnName = "id", nullable = true)
    private Multimedia multimedia;
    @ManyToMany(mappedBy = "cards")
    @Transient
    @XmlTransient
    private List<Deck> decks;
    @ManyToOne
    @JoinColumn(name = "tag_fk")
    @XmlElement(name="tag")
    private Tag tag;
    @OneToMany
    @Transient
    @XmlElementWrapper(name = "options")
    @XmlElement(name ="option")
    private List<Option> options;
    private String info;


    @XmlTransient
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
                if(!this.optionCorrect.getValue().equals(option.getValue())) {
                    option = (Option) dao.insert(option);
                }
            } catch (SQLException | ClassNotFoundException | JAXBException e) {
                exception.set(e);
            }
        });
        this.optionCorrect.setCardId(this.id);
        dao.edit(this.optionCorrect);
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

    @XmlTransient
    public void setOptions(List<Option> all) {
        this.options = all;
    }

    public List<Option> getOptions() {
        return this.options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Option getOptionCorrect() {
        return optionCorrect;
    }

    public void setOptionCorrect(Option optionCorrect) {
        this.optionCorrect = optionCorrect;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlTransient
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @XmlTransient
    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public List<Deck> getDecks(){
        return this.decks;
    }

    public void addDeck(Deck deck) {
        if(this.decks == null){ this.decks = new ArrayList<>(); }
        this.decks.add(deck);
    }
}
