package com.example.android.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    TextView width, height, provider;
    LocationManager lm; //Inicjujemy menadżer lokalizacji
    LocationProvider locationProvider;
    LocationListener ll;
    Criteria kr; //Klasa służąca do wybierania odpowiedniego menadżera lokalizacji
    Location loc; //Służy do przekazywania położenia
    String najlepszyDostawca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        width = (TextView) findViewById(R.id.width);
        height = (TextView) findViewById(R.id.height);
        provider = (TextView) findViewById(R.id.dostawca);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = lm.getProvider(LocationManager.GPS_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }

        LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                provider.setText("lattitude "+location.getLatitude()+" Longlitude "+location.getLongitude());
                width.setText(String.valueOf(location.getLatitude()));
                height.setText(String.valueOf(location.getLongitude()));
                List<String> list = new ArrayList<>();
                list.add(width.getText().toString());
                list.add(height.getText().toString());
                String url ="http://api.openweathermap.org/data/2.5/weather?lat="+width.getText().toString()+"&lon="+height.getText()+"&APPID=b6d39c6ba02087c6bf2352ef6e2c8316";
                Log.d("a",url );
                new Request().execute(list);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        lm.requestLocationUpdates(locationProvider.getName(), 0, 0, ll);
    }

    private class Request extends AsyncTask<List<String>,Void,String> {
        JSONObject stream = null;
        String preasure;

        @Override
        protected String doInBackground(List<String>list[]) {
            String url ="http://api.openweathermap.org/data/2.5/weather?lat="+width.getText().toString()+"&lon="+height.getText()+"&APPID=b6d39c6ba02087c6bf2352ef6e2c8316";
            System.out.println(url);
            HttpGetData httpDataHelper= new HttpGetData();
            stream= httpDataHelper.getHttpData(url);
            Log.d("A",stream.toString());
            try {
                preasure=stream.getString("pressure");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("A",stream.toString());
            return  stream.toString();
        }

        @Override
        protected void onPostExecute(String s) {
           //String
        }
    }
}

