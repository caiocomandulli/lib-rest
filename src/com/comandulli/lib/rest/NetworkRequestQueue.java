package com.comandulli.lib.rest;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.comandulli.lib.rest.exception.NoInternetConnectionException;

/**
 * Request queue singleton that handles all REST requests.
 * <p>
 * {@link #getNetworkState(Context)} must be called to ensure this singleton
 * has its required context, network state and queue.
 * <p>
 * You can check if you are able to make any requests through the method {@link #isConnected()}.
 * If false you either have no connection or this context has not yet been initialized through {@link #getNetworkState(Context)}
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class NetworkRequestQueue {

    /**
     * The system network request queue.
     * Allows us to know if we have any connection.
     */
    private static NetworkRequestQueue networkState;
    /**
     * The current android context.
     */
    private final Context context;
    /**
     * The volley request queue.
     */
    private final RequestQueue requestQueue;

    /**
     * Instantiates a network request queue responsible for the handling of request
     * and the network state.
     *
     * @param context Android context.
     */
    private NetworkRequestQueue(Context context) {
        networkState = this;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Ensures the network request queue has been initialized.
     *
     * @param context Android context.
     * @return This queue.
     */
    public static synchronized NetworkRequestQueue getNetworkState(Context context) {
        if (networkState == null) {
            networkState = new NetworkRequestQueue(context);
        }
        return networkState;
    }

    /**
     * Checks if this queue is able to handle requests, if false either no connection is available
     * or the context has not yet been initialized.
     *
     * @return if this queue is able to handle requests.
     */
    public static boolean isConnected() {
        return networkState != null && networkState.isNetworkAvailable();
    }

    /**
     * Check if there is any connection available to make requests.
     *
     * @return if there is any connection available.
     */
    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    /**
     * Makes a REST request.
     *
     * @param request The request.
     * @throws NoInternetConnectionException if there is no connection.
     */
    public static void makeRequest(Request<?> request) throws NoInternetConnectionException {
        if (!networkState.isNetworkAvailable()) {
            throw new NoInternetConnectionException();
        }
        networkState.requestQueue.add(request);
    }

}
