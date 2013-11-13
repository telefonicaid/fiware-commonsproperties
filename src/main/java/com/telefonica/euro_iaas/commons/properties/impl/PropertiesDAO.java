package com.telefonica.euro_iaas.commons.properties.impl;

import java.util.List;
import java.util.Properties;

/**
 * DAO to load and store properties in a persistence environment.
 *
 * @author Sergio Arroyo
 */
public interface PropertiesDAO {

    /**
     * Load the properties in the given namespace. If there are no properties
     * defined in the given namespace, returns an empty properties object.
     *
     * @param namespace The namespace to be loaded
     *
     * @return Properties for the given namespace
     */
    Properties load(final String namespace);

    /**
     * Store the properties in the persistence environment. Moreover, preserves
     * the existing properties in the persistence environment and only updates
     * the modified (o newer) ones.
     *
     * @param properties Properties to stored
     * @param namespace Associated namespace
     */
    void store(Properties properties, final String namespace);

    /**
     * Load every namespaces stored in DB.
     * @return the namespaces.
     */
    List<String> findNamespaces();

}
