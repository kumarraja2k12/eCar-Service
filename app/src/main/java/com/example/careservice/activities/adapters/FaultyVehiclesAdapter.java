package com.example.careservice.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.careservice.R;
import com.example.careservice.models.FaultyVehicleModel;

import java.util.ArrayList;
import java.util.List;

public class FaultyVehiclesAdapter extends ArrayAdapter<FaultyVehicleModel> {

    private List<FaultyVehicleModel> dataSet = new ArrayList<>();

    public void setDataSet(List<FaultyVehicleModel> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public FaultyVehiclesAdapter(List<FaultyVehicleModel> data, Context context) {
        super(context, R.layout.listitem_faulty_vehicles, data);
        this.dataSet = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FaultyVehicleModel dataModel = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.listitem_faulty_vehicles, parent, false);
        ((TextView) convertView.findViewById(R.id.textview_name_listitem_faulty_vehicles)).setText(dataModel.registrationNumber + " - " + dataModel.customerName);
        ((TextView) convertView.findViewById(R.id.textview_work_orders_faulty_vehicles)).setText("Active work orders: " + dataModel.activeWorkOrders);

        return convertView;
    }
}
