package com.comandulli.lib.rest;

import com.android.volley.Request.Method;
import com.comandulli.lib.rest.exception.NoInternetConnectionException;

import org.json.JSONObject;

/**
 * A REST Request.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class RestRequest {

    /**
     * Request Method Enumerator.
     * Mapping the 4 base HTTP methods of this REST Library.
     * GET, POST, PUT and DELETE.
     */
    public enum RequestMethod {
        GET(Method.GET), POST(Method.POST), PUT(Method.PUT), DELETE(Method.DELETE);

        /**
         * Int value of the method in {@see com.android.volley.Request.Method}
         */
        private final int method;

        /**
         * Constructor for this enumrator.
         *
         * @param httpMethod Int value at {@see com.android.volley.Request.Method}
         */
        RequestMethod(int httpMethod) {
            this.method = httpMethod;
        }

        /**
         * Returns the int value at {@see com.android.volley.Request.Method}
         * that will be used in the VolleyRequest {@see com.comandulli.lib.rest.VolleyRequest}
         *
         * @return Int value at {@see com.android.volley.Request.Method}
         */
        public int getMethod() {
            return method;
        }
    }

    /**
     * Url of the request.
     */
    private String url;
    /**
     * HTTP Method of the request.
     */
    private final RequestMethod method;
    /**
     * Request parameters for this request.
     */
    private final RequestParams params;
    /**
     * Content body of this request in JSON format.
     */
    private JSONObject content;
    /**
     * Default server path to be appended at the beginning of any request.
     */
    private static String defaultServerPath = "";
    /**
     * Server path to be appended at the beginning of any request.
     */
    private String serverPath;

    /**
     * Sets the default server path.
     *
     * @param path Server path.
     */
    public static void setDefaultServerPath(String path) {
        defaultServerPath = path;
    }

    /**
     * Instantiate a new REST Request.
     *
     * @param method HTTP Method.
     * @param url    Url path.
     */
    public RestRequest(RequestMethod method, String url) {
        this.method = method;
        this.url = url;
        this.params = null;
        this.serverPath = defaultServerPath;
    }

    /**
     * Insantiate a new REST Request with parameters.
     *
     * @param method HTTP Method.
     * @param url    Url path.
     * @param params Request parameters.
     */
    public RestRequest(RequestMethod method, String url, RequestParams params) {
        this.method = method;
        this.url = url;
        this.params = params;
        this.serverPath = defaultServerPath;
    }

    /**
     * Adds a content body in JSON format.
     *
     * @param content
     */
    public void addContent(JSONObject content) {
        this.content = content;
    }

    /**
     * Executes this request without any callbacks.
     */
    public void execute() {
        execute(null);
    }

    /**
     * Set server path for this specific request.
     * It will be appended at the beginning of the url.
     *
     * @param serverPath Server path.
     */
    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    /**
     * Executes this request with a callback.
     *
     * @param callback Callback.
     */
    public void execute(final RequestCallback callback) {
        if (params != null) {
            url = params.buildQuery(url);
        }
        String path = serverPath + url;
        VolleyRequest request = new VolleyRequest(method.getMethod(), path, content, callback);
        try {
            NetworkRequestQueue.makeRequest(request);
        } catch (NoInternetConnectionException e) {
            if (callback != null) {
                callback.always();
                callback.onNoConnection();
            }
        }
    }

}
