package com.google.samples.apps.ledtoggler.devices;

/**
 * Created by ffsouza on 24/06/16.
 */
public class IoTGenericDevice extends IoTDevice {

    private String mType, mJsonDescriptor;

    private IoTGenericDevice(String type, String jsonDescriptor) {
        mType = type;
        mJsonDescriptor = jsonDescriptor;
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

    public String getmJsonDescriptor() {
        return mJsonDescriptor;
    }
}
