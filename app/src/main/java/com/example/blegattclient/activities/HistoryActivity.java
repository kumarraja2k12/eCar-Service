package com.example.blegattclient.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.blegattclient.BaseActivity;
import com.example.blegattclient.R;
import com.example.blegattclient.activities.adapters.HistoryAdapter;
import com.example.blegattclient.models.ReadingModel;
import com.example.blegattclient.storage.db.DBHelper;

import java.util.List;

public class HistoryActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBar.setTitle(R.string.title_history);

        listView = findViewById(R.id.list_history);

        List<ReadingModel> dataModels = DBHelper.getInstance(getApplicationContext()).getAllReadings();

        HistoryAdapter adapter= new HistoryAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            /*case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;*/
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
