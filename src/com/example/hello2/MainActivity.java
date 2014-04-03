package com.example.hello2;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.google.android.gms.*;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity {

	//TextView textView1;
	//Button button1;
	public GoogleMap googleMap;
	
	Button button;
	GPSTracker gps;
	
	private LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.change);
		
		
		
		try {
            // Loading map
            initilizeMap();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
			button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//googleMap.moveCamera(CameraUpdateFactory.newLatLng(KIEL));
               // googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
				
				 startService(new Intent(MainActivity.this, ServiceModule.class));
			}
			
			
		});
		
		
	}
	
	

	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Nie mo¿na utworzyæ mapy", Toast.LENGTH_SHORT)
                        .show();
            }
            else {
            	
            	 
            	gps = new GPSTracker(MainActivity.this,googleMap);
            	
            	double latitude =0;
            	double longitude = 0;
                // check if GPS enabled     
                if(gps.canGetLocation() && gps.isGPSEnabled){
                     
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                     
                    Log.d("gps", Double.toString(latitude));
                    googleMap.setMyLocationEnabled(true);
                    
                    LatLng latLng = new LatLng(latitude, longitude);      
            		
                    gps.onLocationChanged(gps.getLocation());
            		
            		
                 
                    
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
                    // \n is for new line
                 //   Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
           
                
            }
            
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	
	

}
