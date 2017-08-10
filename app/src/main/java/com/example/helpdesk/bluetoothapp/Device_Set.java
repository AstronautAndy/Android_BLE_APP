package com.example.helpdesk.bluetoothapp;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by ortiza on 8/7/17.
 */

/**
 * I want to use this class to be able to temporarily store data that an activity will need for operations related to modifying associated Device information.
 */
public class Device_Set {
    private static ArrayList<ble_Device> ble_DeviceSet = new ArrayList();
    private static Map<String,ble_Device> nameMap = new Map<String, ble_Device>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public ble_Device get(Object key) {
            return null;
        }

        @Override
        public ble_Device put(String key, ble_Device value) {
            return null;
        }

        @Override
        public ble_Device remove(Object key) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends ble_Device> m) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<ble_Device> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, ble_Device>> entrySet() {
            return null;
        }
    }; //Used for quick lookups when the senior's name is known in an activity
    private static Map<String, ble_Device> macAddMap = new Map<String, ble_Device>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public ble_Device get(Object key) {
            return null;
        }

        @Override
        public ble_Device put(String key, ble_Device value) {
            return null;
        }

        @Override
        public ble_Device remove(Object key) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends ble_Device> m) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<ble_Device> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, ble_Device>> entrySet() {
            return null;
        }
    }; //Used for lookups when the senior's name is not known

    /**
     * Function used to add a new ble_device to the set of devices.
     */
    static void addNewDevice(String device_name, String mac_address, String senior_name, String senior_rm_nmber){
        ble_Device newDevice = new ble_Device(device_name,mac_address,senior_name,senior_rm_nmber);
        ble_DeviceSet.add(newDevice);
        macAddMap.put(mac_address,newDevice);
        if(nameMap.get(senior_name) == null){
            nameMap.put(senior_name,newDevice);
        }
    }

    /**
     * Method used to retrieve the name of a senior given the MAC address
     * @param macAddress
     * @return
     */
    public static String getSeniorname(String macAddress){
        return( macAddMap.get(macAddress).getSenior_name() );
    }

    public static String getSeniorRmNmber(String macAddress){
        return( macAddMap.get(macAddress).getSenior_rm_nmber() );
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
        public ble_Device(String device_name, String mac_address, String senior_name, String senior_rm_nmber){

        }

        public void setSavedState(){
            state = 1;
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

        public String getMac_address() {
            return mac_address;
        }

        public String getSenior_name() {
            return senior_name;
        }
    }
}
