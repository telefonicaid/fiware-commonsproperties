/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

package com.telefonica.euro_iaas.commons.properties.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.telefonica.euro_iaas.commons.properties.PersistentProperty;

/**
 * JPA DAO implementation of PropertiesDAO.
 * 
 * @author Sergio Arroyo
 */
public class PropertiesDAOJPAImpl implements PropertiesDAO {

    private static final String NAMESPACE = "namespace";

    private static final String FIND_BY_NAMESPACE = "select p from PersistentProperty p where p.namespace = :namespace";

    private static final String FIND_NAMESPACES = "select distinct(namespace) from PersistentProperty";

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Default constructor of the class.
     */
    public PropertiesDAOJPAImpl() {
    }

    /**
     * Constructor of the class.
     * 
     * @param entityManager
     *            The entity manager to use
     */
    public PropertiesDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @see PropertiesDAO#load(String)
     */
    public Properties load(String namespace) {
        Properties prop = new Properties();
        Query query = entityManager.createQuery(FIND_BY_NAMESPACE);
        query.setParameter(NAMESPACE, namespace);
        for (Iterator i = query.getResultList().iterator(); i.hasNext();) {
            PersistentProperty p = (PersistentProperty) i.next();
            prop.setProperty(p.getKey(), p.getValue());
        }
        return prop;
    }

    /**
     * @see PropertiesDAO#store(Properties, String)
     */
    public void store(Properties properties, String namespace) {
        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            String value = properties.getProperty(key);
            PersistentProperty propEntity = new PersistentProperty();
            propEntity.setNamespace(namespace);
            propEntity.setKey(key);
            propEntity.setValue(value);
            entityManager.merge(propEntity);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> findNamespaces() {
        Query query = entityManager.createQuery(FIND_NAMESPACES);
        return query.getResultList();
    }

}
