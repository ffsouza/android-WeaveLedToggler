package com.google.samples.apps.ledtoggler.devices;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffsouza on 24/06/16.
 */

public abstract class IoTDevice {

    public static abstract class DataChangeListener {
        public abstract void onDataUpdated(String type, Object newValue);
    }

    public enum IoTUiCommand {
        CLICK,
        SELECT;
    }

    public interface IoTCommandCallback {
        void onCommandFinished(boolean success);
    }

    public abstract String getType();

    public abstract boolean processCommand(IoTUiCommand commandType, String data);

    private List<DataChangeListener> mDataChangeListeners = new ArrayList<>();

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

    public void registedDataChangeListener(DataChangeListener listener) {
        this.mDataChangeListeners.add(listener);
    }

    public void removeDataChangeListener(DataChangeListener listener) {
        this.mDataChangeListeners.remove(listener);
    }

    public void clearDataChangeListener() {
        this.mDataChangeListeners.clear();
    }

    protected void onDataUpdated(String type, Object data) {
        for (DataChangeListener listener : mDataChangeListeners) {
            listener.onDataUpdated(type, data);
        }
    }
}
