package com.example.helpdesk.bluetoothapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This code is simply used to display a prompt in the editDeviceInfo activity indicating that the
 * information associated with the BLE device (Resident name, Room number) has been edited and saved.
 * Closing this fragment will also close the activity that launched it.
 */

public class SaveDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.Dialog_text)
                .setPositiveButton(R.string.Confirm_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().getSupportFragmentManager().popBackStack(); //Close the fragment
                        getActivity().finish();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
