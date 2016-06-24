package com.google.samples.apps.ledtoggler.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.samples.apps.ledtoggler.IoTDevicesControlPanelAdapter;
import com.google.samples.apps.ledtoggler.R;
import com.google.samples.apps.ledtoggler.devices.AirConditioner;
import com.google.samples.apps.ledtoggler.devices.IoTDevice;

/**
 * Created by ffsouza on 24/06/16.
 */

public class AirConditionerViewHolder extends IoTDevicesControlPanelAdapter.AbstractViewHolder {

    public final TextView temperatureDisplay;
    public final Button clickUpButton;
    public final Button clickDownButton;


    public AirConditionerViewHolder(final View parentView) {
        super(parentView);

        this.temperatureDisplay = (TextView) parentView.findViewById(R.id.temperature_display);

        this.clickUpButton = (Button) parentView.findViewById(R.id.button_temperature_up);
        this.clickDownButton = (Button) parentView.findViewById(R.id.button_temperature_down);


        // parentView.setOnClickListener(clickListener);
        clickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AirConditionerViewHolder.this.mDevice == null) {
                    return;
                }
                final AirConditioner led = (AirConditioner) AirConditionerViewHolder.this.mDevice;

                processCommand(mDevice, IoTDevice.IoTUiCommand.CLICK, "TEMPERATURE_UP",
                        new IoTDevice.IoTCommandCallback() {
                            @Override
                            public void onCommandFinished(boolean success) {
                                updateDisplay();
                            }
                        });
            }
        });
        clickDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AirConditionerViewHolder.this.mDevice == null) {
                    return;
                }
                final AirConditioner led = (AirConditioner) AirConditionerViewHolder.this.mDevice;

                processCommand(mDevice, IoTDevice.IoTUiCommand.CLICK, "TEMPERATURE_DOWN",
                        new IoTDevice.IoTCommandCallback() {
                            @Override
                            public void onCommandFinished(boolean success) {
                                updateDisplay();
                            }
                        });
            }
        });

    }

    @Override
    public void bindView(IoTDevice device, int position) {
        super.bindView(device, position);

        updateDisplay();
    }

    private void updateDisplay() {
        if (temperatureDisplay == null) {
            return;
        }

        if (mDevice != null) {
            AirConditioner airConditioner = (AirConditioner) mDevice;
            temperatureDisplay.setText(String.valueOf(airConditioner.getTemperature()));
        } else {
            temperatureDisplay.setText("Device lost");
        }
    }
}