package com.example.careservice.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;

import com.example.careservice.BaseActivity;
import com.example.careservice.R;
import com.example.careservice.models.FaultyVehicleModel;
import com.example.careservice.services.IServiceCallback;
import com.example.careservice.services.IoTService;
import com.example.careservice.services.requests.CreateWorkOrderRequest;
import com.example.careservice.services.responses.AddReadingResponse;
import com.example.careservice.services.responses.ErrorResponse;

public class VehicleDetailsActivity  extends BaseActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";

    private FaultyVehicleModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_vehicle_details);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBar.setTitle("Vehicle Details");

        model = (FaultyVehicleModel) getIntent().getSerializableExtra(EXTRA_DATA);

        ((TextView)findViewById(R.id.edittext_customer_name)).setText(model.customerName);
        ((TextView)findViewById(R.id.edittext_phone_number)).setText(model.phone);
        ((TextView)findViewById(R.id.edittext_vehicle_number)).setText(model.registrationNumber);
        ((TextView)findViewById(R.id.edittext_model)).setText(model.activeWorkOrders);

    }

    public void CallCustomer(View view) {
        if(isPermissionGranted()) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + model.phone));
            startActivity(intent);
        }
    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallCustomer(null);
                } else {
                    showShortToast("Permission denied");
                }
                return;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ScheduleService(View view) {

        CreateWorkOrderRequest request = new CreateWorkOrderRequest(model.registrationNumber);

        showProgressDialog("Please wait...");
        IoTService.getInstance(getApplicationContext()).CreateWorkOrder(request, new IServiceCallback() {
            @Override
            public void OnCompleted(Object response) {
                hideProgressDialog();
                if(((AddReadingResponse) response).Status.equalsIgnoreCase("Success")) {
                    showDialog("Success", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                } else {
                    showDialog("Unknown error");
                }
            }

            @Override
            public void onError(Object response) {
                try {
                    hideProgressDialog();
                    String message = ((ErrorResponse)response).message;
                    showDialog(message, null);
                } catch (Exception ex) {
                    showDialog("Unknown error");
                }
            }
        });

    }
}