package br.com.arali.app.model.dao;

import br.com.arali.app.model.Tag;
import br.com.arali.app.util.DAO;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.List;

public class DAOTag implements DAO<Tag> {
    @Override
    public Tag insert(Tag obj) throws SQLException, ClassNotFoundException, JAXBException {
        return null;
    }

    @Override
    public boolean edit(Tag obj) throws SQLException, ClassNotFoundException, JAXBException {
        return false;
    }

    @Override
    public Tag find(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        return null;
    }

    @Override
    public List<Tag> findAll() throws SQLException, ClassNotFoundException, JAXBException {
        return null;
    }

    @Override
    public boolean delete(Long id) throws SQLException, ClassNotFoundException, JAXBException {
        return false;
    }
}
