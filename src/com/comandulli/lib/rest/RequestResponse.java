package com.comandulli.lib.rest;

import org.json.JSONObject;

/**
 * Class that defines the response of a REST Request.
 * REST Requests used by this library always have a status code (such as 200, 300, 400...),
 * a message describing the status (such as "Item create.") and a content body if any.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class RequestResponse {
    /**
     * Message of the response.
     */
    private String message;
    /**
     * Response code.
     */
    private int code;
    /**
     * Response content.
     */
    private Object content;
    /**
     * If this response is in JSON.
     */
    private boolean json;

    /**
     * Instantiate a new response.
     */
    public RequestResponse() {
        message = null;
        code = 0;
        content = null;
    }

    /**
     * Sets the message of the response.
     *
     * @param message The message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the status code of the response.
     *
     * @param code The status code.
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Sets the content of the response in JSON format.
     *
     * @param content The response content in JSON format.
     */
    public void setContent(JSONObject content) {
        this.json = true;
        this.content = content;
    }

    /**
     * Sets the content of the response as plain text.
     *
     * @param content The response content in plain text.
     */
    public void setContent(String content) {
        this.json = false;
        this.content = content;
    }

    /**
     * Returns the message of the response.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the status code of the response.
     *
     * @return The status code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the content of this response, in plain text.
     *
     * @return The content.
     */
    public String getContentString() {
        if (json) {
            return content.toString();
        } else {
            return (String) content;
        }
    }

    /**
     * Returns the content of this response, in JSON format.
     *
     * @return The content.
     */
    public JSONObject getContent() {
        if (json) {
            return (JSONObject) content;
        } else {
            return null;
        }
    }
}
