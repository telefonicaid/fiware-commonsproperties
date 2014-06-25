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

package com.telefonica.euro_iaas.commons.properties.mbeans;

import java.util.ArrayList;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * Convenient methods for registering and unregistering mbeans.
 * <p>
 * TODO (DLM) copied from afc project, should be moved to a new afc-util project
 * 
 * @author Sergio Arroyo
 */
public class MBeanUtils {

    /**
     * Register the given mbean in all MBeanServers that could be found.
     * 
     * @param object
     * @param name
     * @throws InstanceAlreadyExistsException
     * @throws MBeanRegistrationException
     * @throws NotCompliantMBeanException
     */
    public static void register(Object object, ObjectName name) throws InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException {
        ArrayList<MBeanServer> mbservers = MBeanServerFactory.findMBeanServer(null);
        if (mbservers.size() == 0) {
            mbservers.add(MBeanServerFactory.createMBeanServer());
        }
        for (Object mbs : MBeanServerFactory.findMBeanServer(null)) {
            ((MBeanServer) mbs).registerMBean(object, name);
        }
    }

    /**
     * Unregister the given mbean name in all MBeanServers found.
     * 
     * @param name
     * @throws InstanceNotFoundException
     * @throws MBeanRegistrationException
     */
    public static void unregister(ObjectName name) throws InstanceNotFoundException, MBeanRegistrationException {
        for (Object mbs : MBeanServerFactory.findMBeanServer(null)) {
            ((MBeanServer) mbs).unregisterMBean(name);
        }
    }

    /**
     * Convenience method for registering mbeans.
     * 
     * @see #register(Object, ObjectName)
     * @param object
     * @param objectName
     * @throws InstanceAlreadyExistsException
     * @throws MBeanRegistrationException
     * @throws NotCompliantMBeanException
     * @throws MalformedObjectNameException
     * @throws NullPointerException
     */
    public static void register(Object object, String objectName) throws InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, NullPointerException {
        register(object, new ObjectName(objectName));
    }

    /**
     * Convenience method for unregistering mbeans.
     * 
     * @see #unregister(ObjectName)
     * @param objectName
     * @throws InstanceNotFoundException
     * @throws MBeanRegistrationException
     * @throws MalformedObjectNameException
     * @throws NullPointerException
     */
    public static void unregister(String objectName) throws InstanceNotFoundException, MBeanRegistrationException,
            MalformedObjectNameException, NullPointerException {
        unregister(new ObjectName(objectName));
    }

}
