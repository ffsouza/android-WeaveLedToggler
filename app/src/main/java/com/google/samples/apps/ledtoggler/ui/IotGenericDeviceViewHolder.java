package com.google.samples.apps.ledtoggler.ui;

import android.view.View;

import com.google.samples.apps.ledtoggler.IoTDevicesControlPanelAdapter;
import com.google.samples.apps.ledtoggler.devices.IoTDevice;
import com.google.samples.apps.ledtoggler.devices.IoTGenericDevice;

/**
 * Created by ffsouza on 24/06/16.
 */

public class IotGenericDeviceViewHolder extends IoTDevicesControlPanelAdapter.AbstractViewHolder {

    public IotGenericDeviceViewHolder(final View parentView) {
        super(parentView);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IotGenericDeviceViewHolder.this.mDevice == null) {
                    return;
                }
                final IoTGenericDevice led = (IoTGenericDevice) IotGenericDeviceViewHolder.this.mDevice;
                processCommand(mDevice, IoTDevice.IoTUiCommand.CLICK, (String) null,
                        new IoTDevice.IoTCommandCallback() {
                            @Override
                            public void onCommandFinished(boolean success) {
                                if (mDevice != null) {
                                    //TODO: Update UI
                                }
                            }
                        });
            }
        };
    }

    @Override
    public void bindView(IoTDevice device, int position) {
        super.bindView(device, position);

        IoTGenericDevice led = (IoTGenericDevice) device;


    }

}

