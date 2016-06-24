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

public class Led extends IoTDevice {

    private static String TYPE = "LED";

    // State
    public interface DataItems {
        String LED_STATE_ON = "LED_STATE_ON";
    }

    private boolean mLightOn;

    public Led(boolean lightOn) {
        mLightOn = lightOn;
    }

    public boolean isLightOn() {
        return mLightOn;
    }

    public boolean toggleLight() {
        mLightOn = !mLightOn;
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
            toggleLight();
            onDataUpdated(DataItems.LED_STATE_ON, mLightOn);
        }
        return true;
    }
}
