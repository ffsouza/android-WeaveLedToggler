package com.google.samples.apps.ledtoggler.devices;

import android.os.AsyncTask;

/**
 * Created by ffsouza on 24/06/16.
 */

public abstract class IoTDevice {

    public enum IoTUiCommand {
        CLICK,
        SELECT;
    }

    public interface IoTCommandCallback {
        void onCommandFinished(boolean success);
    }

    public abstract String getType();

    public abstract boolean processCommand(IoTUiCommand commandType, String data);

    public boolean processCommand(IoTUiCommand commandType) {
        return processCommand(commandType, (String) null);
    }

    public void processCommand(final IoTUiCommand commandType, IoTCommandCallback callback) {
        processCommand(commandType, null, callback);
    }

    public void processCommand(final IoTUiCommand commandType, final String data,
                                    final IoTCommandCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                return processCommand(commandType, data);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (callback != null) {
                    callback.onCommandFinished(result);
                }
            }
        }.execute();

    }
}
