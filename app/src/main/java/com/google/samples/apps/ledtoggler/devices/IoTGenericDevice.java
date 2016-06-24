package com.google.samples.apps.ledtoggler.devices;

/**
 * Created by ffsouza on 24/06/16.
 */
public class IoTGenericDevice extends IoTDevice {

    private String mType;

    private IoTGenericDevice(String type) {
        mType = type;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public boolean processCommand(IoTUiCommand commandType, String data) {
        if (commandType == IoTUiCommand.CLICK) {
            // TODO: send command
        } else if (commandType == IoTUiCommand.SELECT) {
            // TODO: send command
        }

        return true;
    }

}
