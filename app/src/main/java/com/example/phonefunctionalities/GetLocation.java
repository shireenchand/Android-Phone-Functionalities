package com.example.phonefunctionalities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetLocation extends AppCompatActivity {

    private TextView tv_currentlocation;
    private Button btn_getlocation;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION = 1;
    private Double lon;
    private Double lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        tv_currentlocation = findViewById(R.id.currentlocation);
        btn_getlocation = findViewById(R.id.getlocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btn_getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                tv_currentlocation.setText("Lat = " + lat + " Long = " + lon);
            }
        });
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(GetLocation.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GetLocation.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(GetLocation.this,
                                        Locale.getDefault());

                                try {
                                    List <Address> addresses = geocoder.getFromLocation(
                                            location.getLatitude(),location.getLongitude(),1);
                                    //tv_currentlocation.setText("Lat= "+addresses.get(0).getLatitude()+" Lon= "+addresses.get(0).getLongitude());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }
}
