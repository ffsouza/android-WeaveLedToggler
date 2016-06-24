package com.google.samples.apps.ledtoggler.ui;

import com.google.samples.apps.ledtoggler.R;

/**
 * Created by ffsouza on 24/06/16.
 */

public enum AdapterViewTypes {
    GENERIC(0, R.string.iotitem_led_description),
    LED(1, R.string.iotitem_led_description),
    AIR_CONDITIONER(2, R.string.iotitem_air_conditioner_description),
    MICROWEAVE(3, R.string.iotitem_microwave_description),
    TV(4, R.string.iotitem_tv_description),
    OVEN(5, R.string.iotitem_oven_description);


    private int mId;
    private int mResourceId;

    AdapterViewTypes(int id, int descriptionResourceId) {
        mId = id;
        mResourceId = descriptionResourceId;
    }

    public int getId() {
        return this.mId;
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public AdapterViewTypes fromId(int id) {
        for (AdapterViewTypes type : AdapterViewTypes.values()) {
            if (type.mId == id) {
                return type;
            }
        }
        return null;
    }
}
