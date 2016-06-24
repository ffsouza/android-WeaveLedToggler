package com.google.samples.apps.ledtoggler.ui;

import android.view.View;
import android.widget.Switch;

import com.google.samples.apps.ledtoggler.IoTDevicesControlPanelAdapter;
import com.google.samples.apps.ledtoggler.R;
import com.google.samples.apps.ledtoggler.devices.Led;
import com.google.samples.apps.ledtoggler.devices.IoTDevice;

/**
 * Created by ffsouza on 24/06/16.
 */
public class LedViewHolder extends IoTDevicesControlPanelAdapter.AbstractViewHolder {

    public final Switch lightToggler;

    public LedViewHolder(final View parentView) {
        super(parentView);
        this.lightToggler = (Switch) parentView.findViewById(R.id.toggler);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LedViewHolder.this.mDevice == null) {
                    return;
                }
                final Led led = (Led) LedViewHolder.this.mDevice;
                processCommand(mDevice, IoTDevice.IoTUiCommand.CLICK, (String) null,
                        new IoTDevice.IoTCommandCallback() {
                            @Override
                            public void onCommandFinished(boolean success) {
                                if (lightToggler != null && mDevice != null) {
                                    lightToggler.setChecked(led.isLightOn());
                                }
                            }
                        });
            }
        };

        // parentView.setOnClickListener(clickListener);
        lightToggler.setOnClickListener(clickListener);
    }

    @Override
    public void bindView(IoTDevice device, int position) {
        super.bindView(device, position);

        Led led = (Led) device;
        if (lightToggler != null && mDevice != null) {
            // The LED label is the same for every view on this adapter, so we cache it for
            // performance reasons.
            String mLedLabel = parentView.getResources().getString(R.string.led_text);

            int normalizedPosition = position + 1;
            lightToggler.setText(String.format(mLedLabel, normalizedPosition));

            lightToggler.setChecked(led.isLightOn());
        }
    }

}
