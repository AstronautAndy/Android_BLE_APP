package com.example.helpdesk.bluetoothapp;

import android.provider.BaseColumns;

/**
 * Created by ortiza on 7/26/17. This class is to be used to store the names of each of the columns
 * used in the
 */

public final class BLEContract {
    private BLEContract() {}

    public static class BLEEntry implements BaseColumns {
        public static final String TABLE_NAME = "BLE_Devices";
        public static final String UUID_TITLE = "UUID";
        public static final String DEVICE_NAME = "Device_Name";
        public static final String SENIOR_NAME_TITLE = "Senior_Name";
        public static final String ROOM_NUMBER_TITLE = "Room_Number";
        public static final String[] ALL_COLUMNS = {UUID_TITLE,DEVICE_NAME,SENIOR_NAME_TITLE,ROOM_NUMBER_TITLE};
        //public static final String[] QUERY_UUID = {}
    }
}
