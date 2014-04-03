/**
 * Copyright 2014 Telefonica Investigación y Desarrollo, S.A.U <br>
 * This file is part of FI-WARE project.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 * </p>
 * <p>
 * You may obtain a copy of the License at:<br>
 * <br>
 * http://www.apache.org/licenses/LICENSE-2.0
 * </p>
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * </p>
 * <p>
 * See the License for the specific language governing permissions and limitations under the License.
 * </p>
 * <p>
 * For those usages not covered by the Apache version 2.0 License please contact with opensource@tid.es
 * </p>
 */

package com.telefonica.euro_iaas.commons.properties.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Properties;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 * @author david
 */
public class PropertiesProviderImplTest {

    @Test
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

    @Test
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
