/**
 * (c) Copyright 2013 Telefonica, I+D. Printed in Spain (Europe). All Rights Reserved.<br>
 * The copyright to the software program(s) is property of Telefonica I+D. The program(s) may be used and or copied only
 * with the express written consent of Telefonica I+D or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 */

package com.telefonica.euro_iaas.commons.properties;

/**
 * This general exception will be launched when a runtime exception is thrown
 * when using the {@link PropertiesUtils} class.
 *
 * @author Sergio Arroyo
 */
@SuppressWarnings("serial")
public class PropertiesProviderRuntimeException extends RuntimeException {

    /**
     * Constructor of the class.
     *
     * @param message Brief description of the exception
     * @param cause Root exception
     */
    public PropertiesProviderRuntimeException(
            final String message, final Throwable cause) {
        super(message, cause);
    }

}
