package com.example.blegattclient;

import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public void showDialog(String message, DialogInterface.OnClickListener listener) {

        final AlertDialog alertDialog1 = new AlertDialog.Builder(
                BaseActivity.this).create();

        // Setting Dialog Title
        alertDialog1.setTitle(R.string.app_name);

        // Setting Dialog Message
        alertDialog1.setMessage(message);

        // Setting OK Button
        alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (listener == null) ? new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        } : listener);

        /* Showing Alert Message */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog1.show();
            }
        });
    }

    public void showLongToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText( BaseActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showShortToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText( BaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
