package com.comandulli.lib.rest.exception;

/**
 * Exception thrown when errors in the request are not handled.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class RequestException extends RuntimeException {

    private static final long serialVersionUID = 7824272926943249087L;

    public RequestException() {
        super();
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(Throwable throwable) {
        super(throwable);
    }

    public RequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
