/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

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
 */
public class PropertiesProviderFactoryImpl implements PropertiesProviderFactory {

    /**
     * {@inheritDoc}
     */
    public PropertiesProvider createPropertiesProvider(EntityManager em) throws PropertiesProviderRuntimeException {
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
