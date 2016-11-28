package com.comandulli.lib.rest;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Object that wraps mappings to query params for requests.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class RequestParams {

    /**
     * Hashmap that holds all mappings.
     */
    private final Hashtable<String, String> query;

    /**
     * Instantiate an empty query params.
     */
    public RequestParams() {
        this.query = new Hashtable<>();
    }

    /**
     * Add a new query params to a key.
     *
     * @param name  Key name of the param.
     * @param value Value.
     */
    public void addQueryParam(String name, String value) {
        if (value != null) {
            this.query.put(name, value);
        }
    }

    /**
     * Builds the query param string.
     *
     * @param url Url to be appended the query string.
     * @return The url back.
     */
    public String buildQuery(String url) {
        Iterator<Entry<String, String>> it = query.entrySet().iterator();
        char c = '?';
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            url += c + entry.getKey() + "=" + entry.getValue();
            c = '&';
        }
        return url;
    }
}
