package com.example.helpdesk.bluetoothapp;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.helpdesk.bluetoothapp.R;


public class EditDeviceInfo extends AppCompatActivity {

    private String recentlySetName;
    private String recentlySetRoomNumber;
    private boolean edit_name_state = false;
    private boolean edit_number_state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        recentlySetName = Device_Set.getSeniorname(getIntent().getExtras().getString("MAC_Address"));
        recentlySetRoomNumber = Device_Set.getSeniorRmNmber(getIntent().getExtras().getString("MAC_Address"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device_info);
        updateDisplayData();
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
     *Device's information to the database.
     */
    public void saveData(View view){

    }
}
