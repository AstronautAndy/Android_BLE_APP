package com.example.helpdesk.bluetoothapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Used for opening the BLE scanner interface. Remember that the 'view' is synonymous with 'widget'
    public void startBLEScan(View view) {
        Intent intent = new Intent(this,DeviceScanActivity.class); //Create a new intent to activate the bluetooth scan activity
        startActivity(intent);
    }
}

