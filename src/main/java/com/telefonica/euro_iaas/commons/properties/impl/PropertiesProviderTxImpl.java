/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

package com.telefonica.euro_iaas.commons.properties.impl;

import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.telefonica.euro_iaas.commons.properties.PropertiesProvider;

/**
 * Implementation of the Interface PropertiesUtils which manages the creation of EntityManagers.
 * <p>
 * Each method invocation is executed as one different transaction.
 * 
 * @author Sergio Arroyo
 */
public class PropertiesProviderTxImpl implements PropertiesProvider {

    private final EntityManagerFactory emf;

    /**
     * Constructor of the class.
     * 
     * @param dao
     *            to persist and load properties
     */
    public PropertiesProviderTxImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * @see PropertiesUtil#load(String)
     */
    public Properties load(final String namespace) {
        EntityManager em = emf.createEntityManager();
        try {
            return createPropertiesProvider(em).load(namespace);
        } finally {
            em.close();
        }
    }

    /**
     * @throws InvalidPropertyValueException
     * @throws PropertiesUtilException
     * @see PropertiesUtil#store(Properties, String)
     */
    public void store(final Properties prop, final String namespace) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        boolean isActive = true;
        try {
            tx = em.getTransaction();
            if (!tx.isActive()) {
                isActive = false;
                tx.begin();
            }
            createPropertiesProvider(em).store(prop, namespace);
            if (!isActive) {
                tx.commit();
            }
        } finally {
            em.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getNamespaces() {
        EntityManager em = emf.createEntityManager();
        try {
            return createPropertiesProvider(em).getNamespaces();
        } finally {
            em.close();
        }
    }

    /**
     * Builds default impl of properties util.
     * 
     * @param em
     * @return
     */
    private PropertiesProvider createPropertiesProvider(EntityManager em) {
        return new PropertiesProviderImpl(new PropertiesDAOJPAImpl(em));
    }

}
