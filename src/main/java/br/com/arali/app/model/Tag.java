package br.com.arali.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String value;
    @OneToMany
    private List<Card> cards;
}