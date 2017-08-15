package com.example.helpdesk.bluetoothapp;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.security.AccessController.getContext;

/**
 * This class (activity) is used to run a scan that discovers all available BLE devices in range and
 * displays each devices' manufacturer name and MAC address. The user then has the option to modify
 * information the application associates with each device, such as the associated resident's name
 * and room number. Note that modifying the device's associated information, by default, will add it
 * to the "saved devices" list, which the application uses to connect to the GATT servers located on
 * the designated "saved" devices. Note that most of the work being done is through the 
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DeviceScanActivity extends Activity {
    private List<ScanFilter> filterList; //Used as a parameter in the startScan method used below
    private ScanSettings scanSettings;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private boolean mScanning;
    private Handler mHandler;
    private final static int REQUEST_ENABLE_BT = 1; //Note that this must be consistent with the value used in the return method
    BLEDeviceDbHelper mDbHelper; //Create an instance of the class that'll allow us to store data to the disk
    SQLiteDatabase db; //Retrives the database that'll allow us to store data
    ContentValues values = new ContentValues(); //Place data into this object to be inserted into the SQLiteDatabase
    LinearLayout BLEDevicesSet; //Layout used for storing the widgets that represent the number of nearby available BLE devices
    RelativeLayout.LayoutParams buttonParams;
    public static final String[] QUERY_UUID = new String[1];

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        buttonParams = new RelativeLayout.LayoutParams( //this object is used to store parameter information about the
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scan);
        BLEDevicesSet = (LinearLayout) findViewById(R.id.BLEList);
        //code used for initializing the database that will be used in this program
        //mDbHelper.onCreate(db);
        Context context = getApplicationContext();
        mDbHelper = new BLEDeviceDbHelper(context);
        db = mDbHelper.getWritableDatabase();

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner(); //use the scanner to scan for BLE devices instead of the adapter
        mHandler = new Handler(); //create a new handler with an empty constructor
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        filterList = new List<ScanFilter>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<ScanFilter> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(ScanFilter scanFilter) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends ScanFilter> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends ScanFilter> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public ScanFilter get(int index) {
                return null;
            }

            @Override
            public ScanFilter set(int index, ScanFilter element) {
                return null;
            }

            @Override
            public void add(int index, ScanFilter element) {

            }

            @Override
            public ScanFilter remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<ScanFilter> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<ScanFilter> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<ScanFilter> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        createFilters();
        createScanSettings();
        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void beginScan(View view){
        scanLeDevice(true);
    }
    //Replace startLeScan with StartScan
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothLeScanner.stopScan(mScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothLeScanner.startScan(filterList,scanSettings, mScanCallback);

        } else {
            mScanning = false;
            mBluetoothLeScanner.stopScan(mScanCallback);
        }
    }

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;
        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = DeviceScanActivity.this.getLayoutInflater(); //This class uses an inflator
        }
        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
                LinearLayout newDevice = (LinearLayout) mLeDeviceListAdapter.getView(mLeDeviceListAdapter.getCount() - 1, null, null );
                updateUI(newDevice);
                Log.d("DEBUG","Device name being added: " + device.getName() );
                Device_Set.addNewDevice(device.getName(),device.getAddress(),"Senior_name","Room_number");
            }
            Log.d("LeDeviceManager", "Number of devices in adapter: " + Integer.toString( mLeDevices.size() ) );
            Log.d("DEBUG","Number of devices in device set: " + Integer.toString(Device_Set.getSize()) );
        }
        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }
        public void clear() {
            mLeDevices.clear();
        }
        @Override
        public int getCount() {
            return mLeDevices.size();
        }
        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) { //Use this method to obtain the views representing each device available.
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                Log.d("getView","Passed in View is NULL");
                view = (LinearLayout) mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                Log.d("getView","Passed in View is not NULL");
                viewHolder = (ViewHolder) view.getTag();
            }
            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());
            return view;
        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }

    /**
     * This class is used to serve as the callBack for the BLE scan performed in this activity
     */
    private ScanCallback mScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            Log.d("ScanCallback","onScanResult() is being called");
            Log.d("ScanCallback","Call back type: " + Integer.toString(callbackType));
            BluetoothDevice device = result.getDevice();
            mLeDeviceListAdapter.addDevice(device);
            Log.d("ScanCallback",Integer.toString(BLEDevicesSet.getChildCount()) ) ;
            mLeDeviceListAdapter.notifyDataSetChanged();
        }


        /**
         * Performs the same function as the above method, only with a batch of BLE devices.
         * @param results
         */
        /**
        @Override
        public void onBatchScanResults(List<ScanResult> results){
            Log.d("ScanCallback","onScanBatchResults() is being called");
            for(int i=0; i<results.size(); i++){
                BluetoothDevice currDevice = results.get(i).getDevice();
                mLeDeviceListAdapter.addDevice(currDevice);
                LinearLayout newDevice = (LinearLayout) mLeDeviceListAdapter.getView(mLeDeviceListAdapter.getCount() - 1, null, null );
                updateUI(newDevice);
                mLeDeviceListAdapter.notifyDataSetChanged();
            }

             //Code for adding newly discovered Devices to the database
             QUERY_UUID[0] = BLEContract.BLEEntry.UUID_TITLE + " = " + device.getUuids().toString(); //Initialize argument that will be placed into the query command
             //The following IF statement is saying "if the query asking for the UUID we just found returns 0, meaning it can't find the UUID, add a row with the newly found device's information"
             if(db.query(BLEContract.BLEEntry.TABLE_NAME,BLEContract.BLEEntry.ALL_COLUMNS,BLEContract.BLEEntry.UUID_TITLE,QUERY_UUID,null,null,null).getCount() == 0) {
             values.put(BLEContract.BLEEntry.UUID_TITLE, device.getUuids().toString()); //Places the UUID in String form in the UUID column of the DB
             values.put(BLEContract.BLEEntry.DEVICE_NAME, device.getName());
             values.put(BLEContract.BLEEntry.SENIOR_NAME_TITLE, "Senior_name"); //Insert a default name for a newly discovered senior
             values.put(BLEContract.BLEEntry.ROOM_NUMBER_TITLE, "Room_Number");
             }

        }
        */
    };

    /**
     * This method will be used to create the set of filters used in the startScan method
     */
    private void createFilters() {
        ScanFilter sf1 = new ScanFilter.Builder().build(); //Creates a blank scan filter
        filterList.add(sf1);
    }

    private void createScanSettings(){
        ScanSettings.Builder scanSettingsBuilder = new ScanSettings.Builder();
        scanSettingsBuilder = scanSettingsBuilder.setScanMode(1); //Sets the mode to SCAN_MODE_LOW_POWER
        scanSettings = scanSettingsBuilder.build(); //Build the scan settings using the builder we created.

    }

    /**
     * This method will be used to update the number of buttons that are presented onscreen. Should be called after initiating a scan.
     */
    void updateUI(final ViewGroup newDevice){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BLEDevicesSet.addView(newDevice,buttonParams);
            }
        });
    }

    /**
     * Method used to open the edit BLE device information interface
     * @param view
     */
    public void editInfo(View view){
        //view.setBackgroundColor(Color.BLUE);
        Intent intent = new Intent(this,EditDeviceInfo.class);
        TextView mAddress = (TextView) view.findViewById(R.id.device_address);
        intent.putExtra("MAC_Address",mAddress.getText().toString() );
        //intent.putExtra("SigStrength",) //Figure out how to do this. May need to fennagle with this later
        startActivity(intent);
    }
}