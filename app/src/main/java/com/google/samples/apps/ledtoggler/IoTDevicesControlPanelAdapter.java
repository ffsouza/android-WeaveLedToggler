/*
 * Copyright (C) 2015 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.samples.apps.ledtoggler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.samples.apps.ledtoggler.devices.IoTDevice;
import com.google.samples.apps.ledtoggler.devices.Led;
import com.google.samples.apps.ledtoggler.ui.AdapterViewTypes;
import com.google.samples.apps.ledtoggler.ui.IotGenericDeviceViewHolder;
import com.google.samples.apps.ledtoggler.ui.LedViewHolder;

import java.util.ArrayList;

/**
 * Adapter for the list of switches representing device LEDs. For each {@link Led} added to this
 * adapter, a corresponding {@link Switch} will be created that, when clicked, will trigger a
 * callback on the given {@link OnLightToggledListener}.
 */
public class IoTDevicesControlPanelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = IoTDevicesControlPanelAdapter.class.getSimpleName();

    private final OnLightToggledListener lightToggledListener;

    private final ArrayList<IoTDevice> mDataSet;

    public abstract static class AbstractViewHolder extends RecyclerView.ViewHolder {

        protected final View parentView;
        protected IoTDevice mDevice;

        public AbstractViewHolder(final View parentView) {
            super(parentView);
            this.parentView = parentView;
        }

        public void bindView(IoTDevice device, int position) {
            this.mDevice = device;
        }

        protected void processCommand(IoTDevice device, IoTDevice.IoTUiCommand command,
                                      String data, IoTDevice.IoTCommandCallback callback) {
            device.processCommand(command, data, callback);
        }
    }


    public IoTDevicesControlPanelAdapter(OnLightToggledListener lightToggledListener) {
        this.mDataSet = new ArrayList<>();
        this.lightToggledListener = lightToggledListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AdapterViewTypes.LED.getId()) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_device_led, parent, false);
            return new LedViewHolder(v);
        } else if (viewType == AdapterViewTypes.AIR_CONDITIONER.getId()) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_device_air_conditioner, parent, false);
            return new LedViewHolder(v);

        } else {//default
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_device_generic, parent, false);
            return new IotGenericDeviceViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IoTDevice device = mDataSet.get(position);

        if (holder instanceof AbstractViewHolder) {
            ((AbstractViewHolder) holder).bindView(device, position);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void add(IoTDevice iotDevice) {
        mDataSet.add(iotDevice);
        notifyItemInserted(mDataSet.size() - 1);
    }

    public void clear() {
        mDataSet.clear();
        notifyDataSetChanged();
    }
}
