package br.com.arali.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "multimedia")
@Table(name = "multimedia")
public class Multimedia {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String src;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}