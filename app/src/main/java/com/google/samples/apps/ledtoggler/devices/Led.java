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

package com.google.samples.apps.ledtoggler.devices;

import com.google.android.apps.weave.apis.data.Command;

import java.util.HashMap;

public class Led extends IoTDevice {

    private static String TYPE = "LED";

    // State
    public interface DataItems {
        String LED_STATE_ON = "LED_STATE_ON";
    }

    private boolean mLightOn;
    private int mLedNumber;

    public Led(String deviceId, boolean lightOn) {
        super(deviceId);
        mLightOn = lightOn;
        mLedNumber = 0;
    }

    public Led(String deviceId, int ledNumber, boolean lightOn) {
        super(deviceId);
        mLightOn = lightOn;
        mLedNumber = ledNumber;
    }

    public boolean isLightOn() {
        return mLightOn;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean processCommand(IoTUiCommand commandType, String data) {
        if (commandType == IoTUiCommand.CLICK) {
            // Update the internal model

            setDeviceLightState(!isLightOn());
        }
        return true;
    }


    /**
     * Sets the state of a single LED
     *
     * @param lightState Whether the LED should be "on" or not.
     */
    public void setDeviceLightState(final boolean lightState) {

        // Listview is 0-based, Led index in the brillo app is 1-based.
        final int normalizedLedIndex = mLedNumber + 1;

        Command command = getSetLightStateCommand(normalizedLedIndex, lightState);

        sendWeaveCommand(command, new WeaveCommandCallback() {
            @Override
            public void onResult(boolean success) {
                if (success) {
                    mLightOn = lightState;

                    onDataUpdated(DataItems.LED_STATE_ON, lightState);
                }

            }
        });
    }

    /**
     * Creates a Weave command for adjusting a single LED.
     *
     * @param ledIndex The index of the LED to adjust.
     * @param lightOn  Whether the LED should be on or not.
     * @return an executable weave command to toggle the LED to the desired state.
     */
    private Command getSetLightStateCommand(int ledIndex, boolean lightOn) {
        HashMap<String, Object> commandParams = new HashMap<>();
        commandParams.put("_led", ledIndex);
        commandParams.put("_on", lightOn);
        return new Command()
                .setName("_ledflasher._set")
                .setParameters(commandParams);
    }

}
