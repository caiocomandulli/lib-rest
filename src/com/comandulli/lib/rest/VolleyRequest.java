package com.comandulli.lib.rest;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Implementation of a JsonObjectRequest {@see package com.android.volley.toolbox.JsonObjectRequest}
 * that handles volley requests with this library's responses.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class VolleyRequest extends JsonObjectRequest {

    /**
     * JSON mapping of the request message.
     */
    public static final String JSON_PROPERTY_MESSAGE = "message";
    /**
     * JSON mapping of the request content.
     */
    public static final String JSON_PROPERTY_CONTENT = "content";
    /**
     * JSON mapping of the request code.
     */
    public static final String JSON_PROPERTY_CODE = "code";
    /**
     * Header mappings of the request.
     */
    public static final Map<String, String> headers = new HeaderMap();

    /**
     * Override the user agent of all requests.
     *
     * @param agent User agent to be used.
     */
    public static void setUserAgent(String agent) {
        headers.put(HeaderMap.PROPERTY_USER_AGENT, agent);
    }

    /**
     * Adds a new header to the request.
     *
     * @param name  Header name key.
     * @param value Header value.
     */
    public static void addHeader(String name, String value) {
        headers.put(name, value);
    }

    /**
     * Instantiate a new volley request.
     *
     * @param method      Method of the request.
     * @param url         Url of the request.
     * @param jsonRequest Body of the request in a JSONObject, to be sent in JSON. {@see package org.json.JSONObject}
     * @param callback    Callback, to receive the request's results.
     */
    public VolleyRequest(int method, String url, JSONObject jsonRequest, final RequestCallback callback) {
        super(method, url, jsonRequest, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    RequestResponse requestResponse = new RequestResponse();
                    requestResponse.setMessage(response.getString(JSON_PROPERTY_MESSAGE));
                    if (response.has(JSON_PROPERTY_CONTENT)) {
                        requestResponse.setContent(response.getJSONObject(JSON_PROPERTY_CONTENT));
                    }
                    requestResponse.setCode(response.getInt(JSON_PROPERTY_CODE));
                    if (callback != null) {
                        callback.always();
                        callback.onResponse(requestResponse);
                    }
                } catch (JSONException e) {
                    if (callback != null) {
                        callback.always();
                        callback.onInternalError();
                    }
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.getClass().equals(TimeoutError.class)) {
                        if (callback != null) {
                            callback.always();
                            callback.onTimeout();
                        }
                    } else {
                        if (error.networkResponse != null) {
                            RequestResponse requestResponse = new RequestResponse();
                            requestResponse.setMessage(new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers, PROTOCOL_CHARSET)));
                            requestResponse.setCode(error.networkResponse.statusCode);
                            if (callback != null) {
                                callback.always();
                                callback.onResponse(requestResponse);
                            }
                        } else {
                            if (callback != null) {
                                callback.always();
                                callback.onInternalError();
                            }
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    if (callback != null) {
                        callback.always();
                        callback.onInternalError();
                    }
                }
            }
        });
    }

    /**
     * {@link #getHeaders()} override, so HeaderMap {@see com.comandulli.lib.rest.HeaderMap} is returned.
     *
     * @return
     */
    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

}
