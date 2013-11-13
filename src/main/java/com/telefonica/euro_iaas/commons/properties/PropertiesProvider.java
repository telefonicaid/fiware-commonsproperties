package com.telefonica.euro_iaas.commons.properties;

import java.util.List;
import java.util.Properties;

/**
 * This interface gives us a way to manage pairs of properties (key,value)
 * Reads properties from a filename and store them on the database.
 *
 * @author Sergio Arroyo
 */
public interface PropertiesProvider {

    /**
     * This method load an object with all the properties we have associated
     * to the namespace.
     *
     * <p>If there is a file with the same name as the namespace (in the path)
     * first we make a merge with the ones saved on the database and then
     * overwrite the values from the file with the ones load from the database.
     * </p>
     *
     * @param namespace The namespace of the properties to be load (also the
     * filename)
     * @param referenceClassLoader A class reference we want to use to load
     *   the file
     *
     * @return a Properties Object with all the properties that belongs to the
     * namespace
     *
     * @throws PropertiesProviderRuntimeException If any error happens
     */
    Properties load(final String namespace)
            throws PropertiesProviderRuntimeException;


    /**
     * This method save the Properties object into the database.
     *
     * @param properties Object to save on the database
     * @param namespace The namespace of the object
     *
     * @throws InvalidPropertyValueException If any of the properties has
     * invalid format
     * @throws PropertiesProviderRuntimeException If any error happens
     */
    void store(final Properties properties, final String namespace)
            throws PropertiesProviderRuntimeException;

    /**
     * Get the list with every namespaces.
     * @return the list of namespaces.
     */
    List<String> getNamespaces();

}
