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
