package com.telefonica.euro_iaas.commons.properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * A propeties entry to be stored.
 *
 * @author Sergio Arroyo
 */
@Entity
@Table(name="configuration_properties")
@IdClass(PersistentPropertyPK.class)
public class PersistentProperty {

    @Id
    private String namespace;

    @Id
    @Column(name = "\"key\"")
    private String key;

    @Column(length = 32672)
    private String value;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(final String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final String value) {
        this.value = value;
    }

}
