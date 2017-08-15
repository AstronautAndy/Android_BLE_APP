package com.example.helpdesk.bluetoothapp;

import android.provider.BaseColumns;

/**
 * Created by ortiza on 7/26/17. This class is to be used to store the names of each of the columns
 * used in the database. Code in the BLEDeviceDbHelper will use the data contained in this class when
 * it makes SQL function calls, such as CREATE TABLE [TABLE_NAME]... and so on. This class is helpful
 * because we can change the names of the columns and tables here rather than making changes to every
 * activity that uses SQL operations. In other terms, changes made here will "ripple" throughout the
 * rest of the program.
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
