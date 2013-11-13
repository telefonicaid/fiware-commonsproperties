package com.telefonica.euro_iaas.commons.properties.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.telefonica.euro_iaas.commons.properties.PropertiesProvider;
import com.telefonica.euro_iaas.commons.properties.PropertiesProviderFactory;
import com.telefonica.euro_iaas.commons.properties.PropertiesProviderRuntimeException;

/**
 * Implementation of the PropertiesUtilFactory interface.
 *
 * @author Sergio Arroyo
 *
 */
public class PropertiesProviderFactoryImpl implements PropertiesProviderFactory {

    /**
     * {@inheritDoc}
     */
    public PropertiesProvider createPropertiesProvider(EntityManager em)
            throws PropertiesProviderRuntimeException {
        return new PropertiesProviderImpl(new PropertiesDAOJPAImpl(em));
    }

    /**
     * {@inheritDoc}
     */
    public PropertiesProvider createPropertiesProvider(EntityManagerFactory emf)
            throws PropertiesProviderRuntimeException {
        return new PropertiesProviderTxImpl(emf);
    }
}
