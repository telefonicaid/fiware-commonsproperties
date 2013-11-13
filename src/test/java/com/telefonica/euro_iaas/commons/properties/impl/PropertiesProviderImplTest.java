package com.telefonica.euro_iaas.commons.properties.impl;

import java.util.Properties;

import junit.framework.TestCase;

import org.easymock.EasyMock;

/**
 *
 * @author david
 *
 */
public class PropertiesProviderImplTest extends TestCase {

    public void testNonExistingDefaultProperties() throws Exception {

        String ns = "/non/existing/namespace.properties";

        PropertiesDAO dao = EasyMock.createMock(PropertiesDAO.class);
        dao.load(ns);
        EasyMock.expectLastCall().andReturn(new Properties());
        Properties returnedProp = new Properties();
        returnedProp.setProperty("aKey", "first value");
        dao.store(EasyMock.eq(returnedProp), EasyMock.eq(ns));
        dao.load(ns);
        EasyMock.expectLastCall().andReturn(returnedProp);
        EasyMock.replay(dao);

        // replay
        PropertiesProviderImpl propUtil = new PropertiesProviderImpl(dao);

        Properties prop = propUtil.load(ns);
        assertTrue(prop.isEmpty());

        prop.setProperty("aKey", "first value");
        propUtil.store(prop, ns);
        Properties prop2 = propUtil.load(ns);
        assertEquals(1, prop2.size());
        assertEquals("first value", prop2.getProperty("aKey"));

        EasyMock.verify(dao);
    }

    public void testWithDefaultProperties() throws Exception {
        String ns = "/com/almiralabs/afc/properties/sample.properties";

        PropertiesDAO dao = EasyMock.createMock(PropertiesDAO.class);
        dao.load(ns);
        EasyMock.expectLastCall().andReturn(new Properties());
        Properties returnedProp = new Properties();
        returnedProp.setProperty("aKey", "first value");
        dao.store((Properties) EasyMock.anyObject(), EasyMock.eq(ns));
        dao.load(ns);
        EasyMock.expectLastCall().andReturn(returnedProp);
        EasyMock.replay(dao);

        // replay
        PropertiesProviderImpl propUtil = new PropertiesProviderImpl(dao);

        Properties prop = propUtil.load(ns);

        Properties sample = new Properties();
        sample.load(this.getClass().getResourceAsStream(ns));

        assertEquals(sample, prop);

        prop.setProperty("aKey", "first value");
        propUtil.store(prop, ns);
        Properties prop2 = propUtil.load(ns);
        assertEquals(2, prop2.size());
        assertEquals("first value", prop2.getProperty("aKey"));
    }

}
