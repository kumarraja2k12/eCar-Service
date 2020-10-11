package com.example.blegattclient.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.blegattclient.BaseActivity;
import com.example.blegattclient.R;
import com.example.blegattclient.storage.preferences.Preferences;
import com.example.blegattclient.services.IServiceCallback;
import com.example.blegattclient.services.IoTService;
import com.example.blegattclient.services.requests.RegisterVehicleRequest;
import com.example.blegattclient.services.responses.ErrorResponse;
import com.example.blegattclient.services.responses.RegisterVehicleResponse;

public class RegisterActivity extends BaseActivity {

    private EditText customerName;
    private EditText phoneNumber;
    private EditText vehicleNumber;
    private EditText model;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBar.setTitle("Register");

        customerName = findViewById(R.id.edittext_customer_name);
        phoneNumber = findViewById(R.id.edittext_phone_number);
        vehicleNumber = findViewById(R.id.edittext_vehicle_number);
        model = findViewById(R.id.edittext_model);
    }

    public void Register(View view) {
        //TODO:: Validation

        RegisterVehicleRequest request = new RegisterVehicleRequest(customerName.getText().toString(),
                phoneNumber.getText().toString(),
                vehicleNumber.getText().toString(),
                model.getText().toString());

        //TODO:: Show progress dialog
        IoTService.getInstance(getApplicationContext()).RegisterVehicle(request, new IServiceCallback() {
            @Override
            public void OnCompleted(Object response) {
                RegisterVehicleResponse registeredVehicle = (RegisterVehicleResponse)response;
                Preferences preferences = Preferences.getInstance(getApplicationContext());
                boolean result = preferences.writeVehicleNumber(registeredVehicle.assetId);
                //result = preferences.writeThresholds(registeredVehicle.thresholds);
                showDialog("Success", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(RegisterActivity.this, MenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }

            @Override
            public void onError(Object response) {
                try {
                    //((ErrorResponse)response).message
                    String message = ((ErrorResponse)response).message;
                    showDialog(message, null);
                } catch (Exception ex) {
                    showDialog("Unknown error", null);
                }
            }
        });

    }
}
