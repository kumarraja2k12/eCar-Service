package com.example.blegattclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.example.blegattclient.BaseActivity;
import com.example.blegattclient.R;
import com.example.blegattclient.activities.adapters.MenuAdapter;
import com.example.blegattclient.models.MainMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends BaseActivity {

    private GridView gridView;
    private List<MainMenuItem> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        gridView  = findViewById(R.id.grid_menu);

        menuItems.add(new MainMenuItem("Scan & Connect", R.mipmap.ic_launcher));
        menuItems.add(new MainMenuItem("History", R.mipmap.ic_launcher));
        menuItems.add(new MainMenuItem("Test", R.mipmap.ic_launcher));


        // Create an object of CustomAdapter and set Adapter to GirdView
        MenuAdapter customAdapter = new MenuAdapter(getApplicationContext(), menuItems);
        gridView.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(menuItems.get(position).title.equalsIgnoreCase("Scan & Connect")) {
                    // set an Intent to Another Activity
                    Intent intent = new Intent(MenuActivity.this, DeviceScanActivity.class);
                    startActivity(intent); // start Intent*/
                }
            }
        });
    }
}
