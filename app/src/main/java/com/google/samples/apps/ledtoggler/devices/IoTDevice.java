package com.google.samples.apps.ledtoggler.devices;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.android.apps.weave.apis.data.Command;
import com.google.android.apps.weave.apis.data.CommandResult;
import com.google.android.apps.weave.apis.data.WeaveApiClient;
import com.google.android.apps.weave.apis.data.responses.Response;
import com.google.android.apps.weave.framework.apis.Weave;
import com.google.samples.apps.ledtoggler.LedSwitchesFragment;
import com.google.samples.apps.ledtoggler.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffsouza on 24/06/16.
 */

public abstract class IoTDevice {

    private static String TAG = IoTDevice.class.getSimpleName();

    public interface WeaveCommandCallback {
        void onResult(boolean success);
    }

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

    private String mDeviceId;

    protected IoTDevice(String deviceId) {
        mDeviceId = deviceId;
    }

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

    protected void sendWeaveCommand(final Command command, final WeaveCommandCallback callback) {
        // Network call, punt off the main thread.
        new AsyncTask<Void, Void, Response<CommandResult>>() {

            @Override
            protected Response<CommandResult> doInBackground(Void... params) {
                WeaveApiClient apiClient = IoTWeaveManager.getApiClient();
                return Weave.COMMAND_API.execute(
                        apiClient, mDeviceId, command);
            }

            @Override
            protected void onPostExecute(Response<CommandResult> result) {
                super.onPostExecute(result);
                if (result != null) {
                    if (!result.isSuccess() || result.getError() != null) {
                        Log.e(TAG, "Failure setting light state: " + result.getError());
                        if (callback != null) {
                            callback.onResult(false);
                        }
                    } else {
                        Log.i(TAG, "Success setting light state!");
                        if (callback != null) {
                            callback.onResult(true);
                        }
                    }
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
