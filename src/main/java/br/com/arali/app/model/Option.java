package br.com.arali.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "Options")
public class Option {
    @Id
    @GeneratedValue
    private Long id;
    private String value;
}