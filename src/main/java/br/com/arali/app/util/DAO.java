package br.com.arali.app.util;

import java.sql.SQLException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 * The Interface DAO.
 *
 * @param <E>
 *            the element type
 */
public interface DAO<E> {

    /**
     * Insert.
     *
     * @param obj the obj
     * @return the e
     * @throws SQLException the SQL exception
     * @throws ClassNotFoundException the class not found exception
     * @throws javax.xml.bind.JAXBException
     */
    E insert(E obj) throws SQLException, ClassNotFoundException, JAXBException;

    /**
     * Edits the.
     *
     * @param obj the obj
     * @return true, if successful
     * @throws SQLException the SQL exception
     * @throws ClassNotFoundException the class not found exception
     * @throws JAXBException the JAXB exception
     */
    boolean edit(E obj) throws SQLException, ClassNotFoundException, JAXBException;

    /**
     * Find.
     *
     * @param id                        the id
     * @return the e
     * @throws SQLException             the SQL exception
     * @throws ClassNotFoundException   the class not found exception
     * @throws JAXBException            the JAXB exception
     */
    E find(Long id) throws SQLException, ClassNotFoundException, JAXBException;

    /**
     * Find all.
     *
     * @return the list
     * @throws SQLException             the SQL exception
     * @throws ClassNotFoundException   the class not found exception
     * @throws JAXBException            the JAXB exception
     */
    List<E> findAll() throws SQLException, ClassNotFoundException, JAXBException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws SQLException the SQL exception
     * @throws ClassNotFoundException the class not found exception
     * @throws JAXBException the JAXB exception
     */
    boolean delete(Long id) throws SQLException, ClassNotFoundException, JAXBException;
}