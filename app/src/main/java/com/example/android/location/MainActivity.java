package com.example.android.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

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


}
