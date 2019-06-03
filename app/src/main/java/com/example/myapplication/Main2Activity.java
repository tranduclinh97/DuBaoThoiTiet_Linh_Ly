package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    String nameCity = "";
    ImageView image_back;
    TextView text_name;
    ListView list_view;
    CustomAdapter customAdapter;
    ArrayList<Thoitiet> arrayThoitiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        AnhSangCuaDang();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua", "Du lieu truyen qua" + city);
        if(city.equals("")) {
            nameCity = "Saigon";
            Get7DaysData(nameCity);
        }
        else {
            nameCity = city;
            Get7DaysData(nameCity);
        }
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhSangCuaDang() {
        image_back = (ImageView) findViewById(R.id.imageBack);
        text_name = (TextView) findViewById(R.id.textViewCity);
        list_view = (ListView) findViewById(R.id.listView);
        arrayThoitiet = new ArrayList<Thoitiet>();
        customAdapter = new CustomAdapter(Main2Activity.this, arrayThoitiet);
        list_view.setAdapter(customAdapter);
    }

    private void Get7DaysData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=d09dedd896a416b8397ca0fb56823074";
        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonObject1City = jsonObject.getJSONObject("city");
                        String name = jsonObject1City.getString("name");
                        text_name.setText(name);

                        JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                        for(int i =0; i < jsonArrayList.length(); i++) {
                            JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                            String ngay = jsonObjectList.getString("dt");

                            long l = Long.valueOf(ngay);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-mm-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                            String max = jsonObjectTemp.getString("max");
                            String min = jsonObjectTemp.getString("min");

                            Double aa = Double.valueOf(max);
                            Double bb = Double.valueOf(min);
                            String NHIETDO_MAX = String.valueOf(aa.intValue());
                            String NHIETDO_MIN = String.valueOf(bb.intValue());

                            JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("description");
                            String icon = jsonObjectWeather.getString("icon");

                            arrayThoitiet.add(new Thoitiet(Day, status, icon, NHIETDO_MAX, NHIETDO_MIN));

                        }

                        customAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
