package com.isp.locationisp;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.TextView;
//import android.location.Location;
//import android.location.LocationManager;
//import android.content.Context;
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int REQUEST_LOCATION_PERMISSION = 1;
//    Button btnGetLocation;
//    TextView textLocation;
//    LocationManager locationManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnGetLocation = findViewById(R.id.btnGetLocation);
//        textLocation = findViewById(R.id.textLocation);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        btnGetLocation.setOnClickListener(view -> {
//            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                }, REQUEST_LOCATION_PERMISSION);
//            } else {
//                getSingleLocationUpdate();
//            }
//        });
//    }
//
//
//    @SuppressLint("SetTextI18n")
//    private void getSingleLocationUpdate() {
//        textLocation.setText("Test location");
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            try {
//                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, location -> {
////                    Toast.makeText(getApplicationContext(), location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT)
////                            .show();
//
//                    Toast.makeText(
//                            getBaseContext(),
//                            "Location changed: Lat: " + location.getLatitude() + " Lng: "
//                                    + location.getLongitude(), Toast.LENGTH_SHORT).show();
//                    String longitude = "Longitude: " + location.getLongitude();
//
//                    String latitude = "Latitude: " + location.getLatitude();
//
//                    textLocation.setText(longitude + ", " + latitude);
//                }, null);
//            }catch (Exception e){
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
//                       .show();
//            }
//
//        }else{
//            Toast.makeText(getApplicationContext(), "Location permission not granted", Toast.LENGTH_SHORT)
//                   .show();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_LOCATION_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getSingleLocationUpdate();
//            }
//        }
//    }
//}

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Button btnGetLocation;
    TextView textLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetLocation = findViewById(R.id.btnGetLocation);
        textLocation = findViewById(R.id.textLocation);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnGetLocation.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            } else {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    textLocation.setText(location.getLatitude() + ", " + location.getLongitude());
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnGetLocation.performClick();
            }
        }
    }
}
