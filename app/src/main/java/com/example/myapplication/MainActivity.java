package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    EditText editSearch;
    Button btnSearch, btnChangeActivity;
    TextView txtName, txtCountry, txtTemp, txtStatus, txtHumidity, txtCloud, txtWind, txtDay;
    ImageView imgIcon;
    String city = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhSangCuaDang();
        //Location default
        GetCurrentWeatherData("Saigon");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = editSearch.getText().toString();
                if(location.equals("")){
                    city = "Saigon";
                    GetCurrentWeatherData(city);
                }
                else {
                    city= location;
                    GetCurrentWeatherData(city);
                }
                GetCurrentWeatherData(location);
            }
        });
        // Display 2
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Giu mang hinh va dua qua mang hinh thu 2
                String location = editSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("name", location);
                startActivity(intent);
            }
        });
    }


    public void GetCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=d09dedd896a416b8397ca0fb56823074";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Tên thành phố: " + name);
                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-mm-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            txtDay.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");

                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");
                            Picasso.with(MainActivity.this).load("https://openweathermap.org/img/w/"+icon+".png").into(imgIcon);

                            txtStatus.setText(status);
                            JSONObject jsonOjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonOjectMain.getString("temp");
                            String doam = jsonOjectMain.getString("humidity");
                            Double aa = Double.valueOf(nhietdo);
                            String NHIETDO = String.valueOf(aa.intValue());

                            txtTemp.setText(NHIETDO);
                            txtHumidity.setText((doam+"%"));

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String GIO = jsonObjectWind.getString("speed");
                            txtWind.setText(GIO+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String MAY = jsonObjectClouds.getString("all");
                            txtCloud.setText(MAY+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String COUNTRY = jsonObjectClouds.getString("country");
                            txtCountry.setText("Tên quốc gia: " + COUNTRY);

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

    private void AnhSangCuaDang() {
        editSearch = (EditText) findViewById(R.id.edittextSearch);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        btnChangeActivity = (Button) findViewById(R.id.buttonChangeActivity);
        txtCountry = (TextView) findViewById(R.id.textviewCountry);
        txtTemp = (TextView) findViewById(R.id.textviewTemp);
        txtStatus = (TextView) findViewById(R.id.textviewStatus);
        txtHumidity = (TextView) findViewById(R.id.textviewHumidity);
        txtCloud = (TextView) findViewById(R.id.textviewCloud);
        txtWind = (TextView) findViewById(R.id.textviewWind);
        txtDay = (TextView) findViewById(R.id.textviewDay);
        txtName = (TextView) findViewById(R.id.textviewName);
        imgIcon = (ImageView) findViewById(R.id.imageIcon);
    }
}
