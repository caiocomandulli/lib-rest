package com.comandulli.lib.rest;

import com.comandulli.lib.rest.exception.RequestException;

/**
 * Defines a high level callback for REST requests.
 * RequestCallback is a class that can be chained, allowing
 * in a single request to have a chain of behaviours.
 * <p>
 * Use {@link #setParent(com.comandulli.lib.rest.RequestCallback)} to set another callback as
 * the origin of this callback.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public abstract class RequestCallback {

    /**
     * The parent callback.
     */
    protected RequestCallback parent;

    /**
     * Instatiate a callback for a request.
     */
    public RequestCallback() {
    }

    /**
     * Instatiate a callback with another callback as its parent. This allow chaining of behaviour
     * inside a single request.
     *
     * @param parent Parent of this callback.
     */
    public RequestCallback(RequestCallback parent) {
        this.parent = parent;
    }

    /**
     * Assigns another callback as its parent. This allow chaining of behaviour
     * inside a single request.
     *
     * @param parent Parent of this callback.
     */
    public void setParent(RequestCallback parent) {
        this.parent = parent;
    }

    /**
     * Callback when a request if fully succesful and produced a RequestResponse {@see package com.comandulli.lib.rest.RequestResponse}
     *
     * @param response RequestResponse produced.
     */
    public abstract void onResponse(RequestResponse response);

    /**
     * Callback when a 401 (Unauthorized) is responded.
     * Unauthorized usually means when authentication is required and has failed or has not yet been provided.
     */
    public void onUnauthorized() {
        if (parent == null) {
            this.onInternalError();
        } else {
            parent.onUnauthorized();
        }
    }

    /**
     * Callback when a 400 (Bad Request) is responded.
     * Bad Request usually means that the server cannot or will not process the request due to an apparent client error.
     */
    public void onInvalidRequest() {
        if (parent == null) {
            this.onInternalError();
        } else {
            parent.onInvalidRequest();
        }
    }

    /**
     * Callback when a 403 (Forbidden) is responded.
     * Forbidden usually means that the request was a valid request, but the server is refusing to respond to it. The user might be logged in but does not have the necessary permissions for the resource.
     */
    public void onForbidden() {
        if (parent == null) {
            this.onInternalError();
        } else {
            parent.onForbidden();
        }
    }

    /**
     * Callback when a 409 (Conflict) is responded.
     * Conflict usually means  that the request could not be processed because of conflict in the request.
     */
    public void onConflict() {
        if (parent == null) {
            this.onInternalError();
        } else {
            parent.onConflict();
        }
    }

    /**
     * Callback when the request turns out to have no connection.
     */
    public void onNoConnection() {
        if (parent == null) {
            this.onInternalError();
        } else {
            parent.onNoConnection();
        }
    }

    /**
     * Callback when a 408 (Request Timeout) is responded.
     * Request Timeout usually means the server took too long to produce any response.
     */
    public void onTimeout() {
        if (parent == null) {
            this.onInternalError();
        } else {
            parent.onTimeout();
        }
    }

    /**
     * Callback when any 5XX (Server Error) is responded.
     * Also used as a fallback when any other error is not handled.
     * Usually indicates that the server is aware that it has encountered an error or is otherwise incapable of performing the request.
     */
    public void onInternalError() {
        if (parent == null) {
            throw new RequestException();
        } else {
            parent.onInternalError();
        }
    }

    /**
     * Callback that is callback in EVERY request.
     */
    public void always() {
        if (parent != null) {
            parent.always();
        }
    }

}
