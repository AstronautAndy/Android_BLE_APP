package com.example.helpdesk.bluetoothapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EditDeviceInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device_info);
    }

    /**
     * This method will be used to modify the senior name.
     * @param view
     */
    public void editSeniorName(View view){
        TextView textView = (TextView) view.findViewById(R.id.Senior_Name);
        textView.setVisibility(View.INVISIBLE); //Set the senior name text view invisible
    }

    /**
     * This method will be used to modify the senior's room number
     * @param view
     */
    public void editRoomNumber(View view){

    }

    /**
     *This method will use SQL to save the current state of the associated
     *Device's information to the database.
     */
    public void saveData(View view){

    }
}
