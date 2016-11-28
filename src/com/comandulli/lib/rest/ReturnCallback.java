package com.comandulli.lib.rest;

/**
 * Implementation of Request Callback {@see com.comandulli.lib.rest.RequestCallback}
 * used at a higher level, where the {@link #onReturn(java.lang.Object)} disregards any
 * request processing and cares only about the final parsed object.
 *
 * @param <T> Type of the object returned by this request's callback.
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public abstract class ReturnCallback<T> extends RequestCallback {

    /**
     * Instantiate a callback for a request expecting a specific return.
     */
    public ReturnCallback() {
        super();
    }

    /**
     * Instatiate a callback expecting a specific return with another callback as its parent. This allow chaining of behaviour
     * inside a single request.
     *
     * @param parent Parent of this callback.
     */
    public ReturnCallback(ReturnCallback<?> parent) {
        super(parent);
    }

    /**
     * Callback when a request if fully succesful and the RequestResponse {@see package com.comandulli.lib.rest.RequestResponse}
     * has already been parsed into an object of type T.
     *
     * @param value The resulting value.
     */
    public abstract void onReturn(T value);

    /**
     * This implementation regards this callback as not responsible for any parsing of the
     * RequestResponse {@see package com.comandulli.lib.rest.RequestResponse} object.
     * <p>
     * If this callback is not a parent in a chained behaviour you need to override this
     * to produce any value for {@link #onReturn(java.lang.Object)}
     *
     * @param response RequestResponse produced.
     */
    @Override
    public void onResponse(RequestResponse response) {
        onReturn(null);
    }

}
