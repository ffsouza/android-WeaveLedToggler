package com.google.samples.apps.ledtoggler.devices;

import android.content.Context;

import com.google.android.apps.weave.apis.data.WeaveApiClient;

/**
 * Created by ffsouza on 24/06/16.
 */

public class IoTWeaveManager {

    static WeaveApiClient mApiClient;

    /**
     * Generates an Api client instance, sets up a listener to react to devices being either
     * discovered or lost.  All code related to initializing the Weave API client should go here.
     */
    public static void initializeApiClient(Context context) {
        mApiClient = new WeaveApiClient(context);
    }

    public static WeaveApiClient getApiClient() {
        return mApiClient;
    }
}
