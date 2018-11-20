package br.com.arali.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "multimedia")
public class Multimedia {
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