package com.example.helpdesk.bluetoothapp;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.helpdesk.bluetoothapp.R;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/**
 * This activity is used to allow the user to edit the information associated with a particular BLE
 * device (resident name and room number) as well as allow the user to view information contained on
 * the device, such as it's manufactured name, its MAC address, and its signal strength (for clarity,
 * this last quantity is measured by the Android Device, not contained on the device)
 */

public class EditDeviceInfo extends AppCompatActivity {

    private String recentlySetName;
    private String recentlySetRoomNumber;
    private boolean edit_name_state = false;
    private boolean edit_number_state = false;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG","MAC_Address retrieved from intent: " + getIntent().getExtras().getString("MAC_Address") );
        fragmentManager = this.getSupportFragmentManager();
        recentlySetName = Device_Set.getSeniorname(getIntent().getExtras().getString("MAC_Address"));
        recentlySetRoomNumber = Device_Set.getSeniorRmNmber(getIntent().getExtras().getString("MAC_Address"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device_info);
        updateDisplayData();

        TextView mName = (TextView) findViewById(R.id.ManufacturerDeviceName);
        TextView macAdd = (TextView) findViewById(R.id.MAC_Address);
        TextView SigStrength = (TextView) findViewById(R.id.Signal_Strength);

        mName.setText(Device_Set.getDeviceName(getIntent().getExtras().getString("MAC_Address") ) );
        macAdd.setText(getIntent().getExtras().getString("MAC_Address"));

    }

    /**
     * This method will be used by a "cancel" button when the user wants to cancel editing to have the software reset the
     * UI to the state it was in before they began editing information.
     * @param view
     */
    public void reset(View view){

    }

    /**
     * This method will be used to modify the senior name.
     * @param view
     */
    public void editSeniorName(View view){
        final TextView textView = (TextView) findViewById(R.id.Senior_Name);
        final Button button = (Button) view.findViewById(R.id.Edit_Name);
        final EditText editText = (EditText) findViewById(R.id.SeniorNameEdit);

        if(edit_name_state){
            recentlySetName = String.valueOf( editText.getText() );
            editText.setVisibility(View.INVISIBLE); //Set the text editing widget to INVISIBLE
            button.setText(R.string.Edit_Snr_nme_button); //Set the text on the edit senior name button to "save
            textView.setVisibility(View.VISIBLE); //Set the senior name text view VISIBLE
            edit_name_state = false;
        }
        else{
            editText.setVisibility(View.VISIBLE); //Set the text editing widget to "Visible"
            button.setText(R.string.Edit_Snr_nme_button_save); //Set the text on the edit senior name button to "save
            textView.setVisibility(View.INVISIBLE); //Set the senior name text view invisible
            edit_name_state = true;
        }
        updateDisplayData();
    }

    /**
     * This method will be used to modify the senior's room number
     * @param view
     */
    public void editRoomNumber(View view){

    }

    public void updateDisplayData(){
        final TextView textView1 = (TextView) findViewById(R.id.Senior_Name);
        final TextView textView2 = (TextView) findViewById(R.id.Senior_RmNumber);
        textView1.setText(recentlySetName);
        textView2.setText(recentlySetRoomNumber);
    }

    /**
     *This method will use SQL to save the current state of the associated
     *Device's information to the database. note that changing information will automatically
     */
    public void saveData(View view){
        Device_Set.setNewName(getIntent().getExtras().getString("MAC_Address"),recentlySetName);
        Device_Set.setNewRmNumber(getIntent().getExtras().getString("MAC_Address"), recentlySetRoomNumber);
        Device_Set.setDeviceState(getIntent().getExtras().getString("MAC_Address"), 1); //Indicate that the device should be set to "saved"
        SaveDialog sd = new SaveDialog();
        sd.show(fragmentManager,"SAVE_DATA");
    }

    public void cancel(View view){
        this.finish();
    }
}
