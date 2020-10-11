package com.example.careservice.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.careservice.R;
import com.example.careservice.models.ReadingModel;
import com.example.careservice.utils.DateFormatUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<ReadingModel> implements View.OnClickListener{

    private List<ReadingModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView timestampText;
        TextView syncText;
        TextView valueText;
        ImageView valueTypeIcon;
    }

    public HistoryAdapter(List<ReadingModel> data, Context context) {
        super(context, R.layout.listitem_history, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        /*int position=(Integer) v.getTag();
        Object object= getItem(position);
        ReadingModel dataModel=(ReadingModel) object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
    }

    private int lastPosition = -1;

    SimpleDateFormat todayDateFormat = new SimpleDateFormat("hh:mm");
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ReadingModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listitem_history, parent, false);
            viewHolder.timestampText = (TextView) convertView.findViewById(R.id.textview_date_time_listitem_history);
            viewHolder.syncText = (TextView) convertView.findViewById(R.id.textview_cloud_sync_listitem_history);
            viewHolder.valueText = (TextView) convertView.findViewById(R.id.textview_value_listitem_history);
            viewHolder.valueTypeIcon = (ImageView) convertView.findViewById(R.id.icon_listitem_history);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.timestampText.setText(DateFormatUtil.formatDate(dataModel.timestamp));
        viewHolder.syncText.setText((dataModel.cloudUpdate  == 1) ? "SUCCESS" : "FAIL");
        viewHolder.valueText.setText(dataModel.value);
        viewHolder.valueTypeIcon.setImageResource(dataModel.valueType.equalsIgnoreCase("level") ? R.drawable.fluid_menu_icon : R.drawable.gas_polution_icon);
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}