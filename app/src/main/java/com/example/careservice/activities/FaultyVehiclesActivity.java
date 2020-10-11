package com.example.careservice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.careservice.BaseActivity;
import com.example.careservice.R;
import com.example.careservice.activities.adapters.FaultyVehiclesAdapter;
import com.example.careservice.models.FaultyVehicleModel;
import com.example.careservice.services.IServiceCallback;
import com.example.careservice.services.IoTService;
import com.example.careservice.services.responses.FaultyVehiclesResponse;

import java.util.ArrayList;
import java.util.List;

public class FaultyVehiclesActivity extends BaseActivity {

    private ListView listView;
    private FaultyVehiclesAdapter adapter;
    private List<FaultyVehicleModel> models = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faulty_vehicles);

        listView = findViewById(R.id.list_faulty_vehicles);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FaultyVehiclesActivity.this, VehicleDetailsActivity.class);
                intent.putExtra(VehicleDetailsActivity.EXTRA_DATA, models.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    private void refresh() {
        showProgressDialog("Please wait...");
        IoTService.getInstance(getApplicationContext()).GetFaultyVehicles(new IServiceCallback() {
            @Override
            public void OnCompleted(final Object response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        models = ((FaultyVehiclesResponse)response).getFaultyVehicleModels();
                        adapter= new FaultyVehiclesAdapter(models, getApplicationContext());
                        listView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onError(Object error) {
                hideProgressDialog();
                showDialog("Failed to load faulty vehicles at this time..");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_refresh:
                refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
