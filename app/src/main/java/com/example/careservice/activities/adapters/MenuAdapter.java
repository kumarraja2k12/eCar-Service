package com.example.careservice.activities.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.careservice.R;
import com.example.careservice.models.MainMenuItem;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    Context context;
    List<MainMenuItem> items;
    LayoutInflater layoutInflater;
    public MenuAdapter(Context applicationContext, List<MainMenuItem> items) {
        this.context = applicationContext;
        this.items = items;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.menu_item, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon_menu_item); // get the reference of ImageView
        icon.setImageResource(items.get(i).icon); // set logo images

        TextView title = (TextView) view.findViewById(R.id.title_menu_item); // get the reference of ImageView
        title.setText(items.get(i).title);
        return view;
    }
}
