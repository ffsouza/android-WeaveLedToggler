package com.google.samples.apps.ledtoggler.devices;

/**
 * Created by ffsouza on 24/06/16.
 */

public class AirConditioner extends IoTDevice {

    private static String TYPE = "AIR_CONDITIONER";

    private static String TEMPERATURE_UP = "TEMPERATURE_UP";
    private static String TEMPERATURE_DOWN = "TEMPERATURE_DOWN";

    private float mTemperature;

    public AirConditioner(float temperature) {
        mTemperature = temperature;
    }


    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean processCommand(IoTDevice.IoTUiCommand commandType, String data) {
        if (commandType == IoTDevice.IoTUiCommand.CLICK) {
            if (TEMPERATURE_UP.equals(data)) {
                // Update the internal model
                temperatureUp();
            } else if (TEMPERATURE_DOWN.equals(data)) {
                // Update the internal model
                temperatureDown();
            }
        }
        return true;
    }

    public float getTemperatura() {
        return mTemperature;
    }

    public boolean temperatureUp() {
        ++mTemperature;
        return true;
    }

    public boolean temperatureDown() {
        --mTemperature;
        return true;
    }

}
