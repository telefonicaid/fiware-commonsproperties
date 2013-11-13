/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

package com.telefonica.euro_iaas.commons.properties;

import java.util.Iterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;

/**
 * Utility MBean that exposes a PropertiesUtil through JMX.
 *
 * @author Sergio Arroyo
 */
public class PropertiesProviderMBean implements DynamicMBean {

    private final String namespace;
    private final PropertiesProvider propUtil;
    private Properties properties;

    /**
     * Constructor of the class.
     *
     * @param namespace The namespace
     * @param propUtil The PropertiesUtil to expose through JMX
     * @param classForLoading Reference class for class loading
     */
    public PropertiesProviderMBean(final String namespace,
            final PropertiesProvider propUtil) {
        this.namespace = namespace;
        this.properties = propUtil.load(namespace);
        this.propUtil = propUtil;
    }

    /**
     * @see javax.management.DynamicMBean#getAttribute(java.lang.String)
     */
    public synchronized String getAttribute(String name)
            throws AttributeNotFoundException {
        String value = properties.getProperty(name);
        if (value != null) {
            return value;
        } else {
            throw new AttributeNotFoundException("No such property: " + name);
        }
    }

    /**
     * @see DynamicMBean#setAttribute(javax.management.Attribute)
     */
    public synchronized void setAttribute(Attribute attribute)
            throws InvalidAttributeValueException, MBeanException,
            AttributeNotFoundException {
        String name = attribute.getName();
        if (properties.getProperty(name) == null) {
            throw new AttributeNotFoundException(name);
        }
        Object value = attribute.getValue();
        if (!(value instanceof String)) {
            throw new InvalidAttributeValueException(
                    "Attribute value not a string: " + value);
        }

        // Store the current property value. It is needed if there is a
        // validation problem
        String currentValue = properties.getProperty(name);

        properties.setProperty(name, (String) value);

        try {
            save();
        } catch (Exception e) {

            // If the current value is not restored next calls to this method
            // will launch a validation exception, because this.properties
            // contains an invalid value (the one that launched this exception)
            properties.setProperty(name, currentValue);
            throw new InvalidAttributeValueException(e.getMessage());
        }
    }

    /**
     * @see javax.management.DynamicMBean#getAttributes(java.lang.String[])
     */
    public synchronized AttributeList getAttributes(String[] names) {
        AttributeList list = new AttributeList();
        for (String name : names) {
            String value = properties.getProperty(name);
            if (value != null) {
                list.add(new Attribute(name, value));
            }
        }
        return list;
    }

    /**
     * @see DynamicMBean#setAttributes(javax.management.AttributeList)
     */
    public synchronized AttributeList setAttributes(AttributeList list) {
        Attribute[] attrs = (Attribute[]) list.toArray(new Attribute[0]);
        AttributeList retlist = new AttributeList();
        for (Attribute attr : attrs) {
            String name = attr.getName();
            Object value = attr.getValue();
            if (properties.getProperty(name) != null
                    && value instanceof String) {
                properties.setProperty(name, (String) value);
                retlist.add(new Attribute(name, value));
            }
        }

        save();
        return retlist;
    }

    /**
     * @see javax.management.DynamicMBean#invoke(java.lang.String, java.lang.Object[], java.lang.String[])
     */
    public Object invoke(String name, Object[] args, String[] sig)
            throws MBeanException, ReflectionException {
        if (name.equals("reload") &&
                (args == null || args.length == 0) &&
                (sig == null || sig.length == 0)) {
            load();
            return null;
        }
        throw new ReflectionException(new NoSuchMethodException(name));
    }

    /**
     * @see javax.management.DynamicMBean#getMBeanInfo()
     */
    public synchronized MBeanInfo getMBeanInfo() {
        SortedSet<String> names = new TreeSet<String>();
        for (Object name : properties.keySet())
            names.add((String) name);
        MBeanAttributeInfo[] attrs = new MBeanAttributeInfo[names.size()];
        Iterator<String> it = names.iterator();
        for (int i = 0; i < attrs.length; i++) {
            String name = it.next();
            attrs[i] = new MBeanAttributeInfo(
                    name,
                    "java.lang.String",
                    "Property " + name,
                    true,   // isReadable
                    true,   // isWritable
                    false); // isIs
        }
        MBeanOperationInfo[] opers = {
            new MBeanOperationInfo(
                    "reload",
                    "Reload properties from file",
                    null,   // no parameters
                    "void",
                    MBeanOperationInfo.ACTION)
        };
        return new MBeanInfo(
                this.getClass().getName(),
                "Property Manager MBean",
                attrs,
                null,  // constructors
                opers,
                null); // notifications
    }

    /**
     * Load a properties util.
     */
    private void load() {
        properties = propUtil.load(namespace);
    }

    /**
     * Stores a properties util.
     *
     * @throws InvalidPropertyValueException If there is a validation problem
     */
    private void save() {
        propUtil.store(properties, namespace);
    }

}
