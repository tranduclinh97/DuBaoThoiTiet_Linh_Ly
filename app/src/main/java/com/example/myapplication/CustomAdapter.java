package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.line_text_view, null);

        Thoitiet thoitiet = arrayList.get(position);

        TextView txtDay = (TextView) convertView.findViewById(R.id.textViewDislay2);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.textViewStatusDisplay2);
        TextView txtMaxTemp = (TextView) convertView.findViewById(R.id.textViewMaxTemp);
        TextView txtMinTemp = (TextView) convertView.findViewById(R.id.textViewMinTemp);
        ImageView imageStatus = (ImageView) convertView.findViewById(R.id.imageViewStatus);

        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.Status);
        txtMaxTemp.setText(thoitiet.MaxTemp+"°C");
        txtMinTemp.setText(thoitiet.MinTemp+"°C");

        Picasso.with(context).load("https://openweathermap.org/img/w/"+thoitiet.Image+".png").into(imageStatus);
        return convertView;
    }
}
