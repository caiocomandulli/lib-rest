package com.comandulli.lib.rest;

import java.util.HashMap;

/**
 * HashMap implementation to use for mapping header entries, automatically includes
 * User Agent header.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class HeaderMap extends HashMap<String, String> {

    private static final long serialVersionUID = -8833280974238775824L;

    /**
     * Static constant for user agent property.
     */
    public static final String DEFAULT_USER_AGENT = "AndroidRestLibrary1.0";
    /**
     * Static constant for user agent property key.
     */
    public static final String PROPERTY_USER_AGENT = "User-Agent";

    /**
     * Instantiate a HashMap to map your headers, includes an user agent.
     */
    public HeaderMap() {
        put(PROPERTY_USER_AGENT, DEFAULT_USER_AGENT);
    }

}
