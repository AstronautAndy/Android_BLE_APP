package com.example.helpdesk.bluetoothapp;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ortiza on 8/7/17.
 * This class is used to store a list of ble_Device objects (an internal class), each of which hold
 * important information pertaining to the devices that the application might need to connect to.
 * Remember that the application needs to discover ble devices and connect to specific ones depending
 * on the user's input. This class is used to store information that's accessible to all activities
 * to avoid ugly code that attempts to pass large amounts Device information via intent.
 */

/**
 * I want to use this class to be able to temporarily store data that an activity will need for operations related to modifying associated Device information.
 */
public class Device_Set {
    private static ArrayList<ble_Device> ble_DeviceSet = new ArrayList();
    private static HashMap<String,ble_Device> macAddMap = new HashMap<String, ble_Device>();
    private static ArrayList<ble_Device> savedDevices = new ArrayList<>(); //List of devices that have been "saved" by the user
    /**
     * Function used to add a new ble_device to the set of devices.
     */
    public static void addNewDevice(String device_name, String mac_address, String senior_name, String senior_rm_nmber){
        ble_Device newDevice = new ble_Device(device_name,mac_address,senior_name,senior_rm_nmber);
        ble_DeviceSet.add(newDevice);
        macAddMap.put(mac_address,newDevice);
        Log.d("DEBUG","MacAdd map size: " + macAddMap.size());
    }

    /**
     * Returns the number of devices located in the ble_DeviceSet ArrayList
     * @return
     */
    public static int getSize(){
        return ble_DeviceSet.size();
    }

    public static void setNewName(String macAddress, String newName){
        macAddMap.get(macAddress).setSenior_name(newName);
    }

    public static void setNewRmNumber(String macAddress, String newNumber){
        macAddMap.get(macAddress).setSenior_rm_nmber(newNumber);
    }

    public static void setDeviceState(String macAddress, int newState){
        macAddMap.get(macAddress).setNewState(newState);
    }

    /**
     * Method used to retrieve the name of a senior given the MAC address
     * @param macAddress
     * @return
     */
    public static String getSeniorname(String macAddress){
        Log.d("DEBUG", "Found Senior name: " + macAddMap.get(macAddress).getSenior_name());
        return( macAddMap.get(macAddress).getSenior_name() );
    }

    public static String getSeniorRmNmber(String macAddress){
        return( macAddMap.get(macAddress).getSenior_rm_nmber() );
    }

    public static String getDeviceName(String macAddress){
        return( macAddMap.get(macAddress).getDevice_name() );
    }

    public static class ble_Device {
        private String senior_name;
        private String mac_address;
        private String senior_rm_nmber;
        private String device_name;
        private int state = 0; //not saved = 0 (default), saved = 1, blocked = 2

        /*
        Default constructor
         */
        private ble_Device(String Device_name, String Mac_address, String Senior_name, String Senior_rm_nmber){
            senior_name = Senior_name;
            mac_address = Mac_address;
            senior_rm_nmber = Senior_rm_nmber;
            device_name = Device_name;
        }

        public void setNewState(int newState){
            state = newState;
        }

        public void setUnsavedState(){
            state = 0;
        }

        public void setBlockedState(){
            state = 2;
        }

        public String getDevice_name() {
            return device_name;
        }

        public String getSenior_rm_nmber() {
            return senior_rm_nmber;
        }

        public void setSenior_rm_nmber(String newRoom) {senior_rm_nmber = newRoom;}

        public String getMac_address() {
            return mac_address;
        }

        public String getSenior_name() {
            return senior_name;
        }

        public void setSenior_name(String newName) {senior_name = newName;}
    }
}
