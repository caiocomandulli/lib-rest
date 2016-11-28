package com.comandulli.lib.rest.exception;

/**
 * Exception thrown when there is no internet connection.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class NoInternetConnectionException extends Exception {

    private static final long serialVersionUID = -8008073238765589177L;

    public NoInternetConnectionException() {
        super();
    }

    public NoInternetConnectionException(String message) {
        super(message);
    }

    public NoInternetConnectionException(Throwable throwable) {
        super(throwable);
    }

    public NoInternetConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
