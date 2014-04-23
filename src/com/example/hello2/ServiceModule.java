package com.example.hello2;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ServiceModule extends Service {

	
	 Timer timer;
	 MyTimerTask myTimerTask;
	 private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	 private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	    
	 double latitude;
	 double longitude;
	 double latitudePrev;
	 double longitudePrev;
	 
	 JSONParser jsonParser = new JSONParser();
	private static String url_create_product = "http://testowastrona.comli.com/AddLocation.php";
	 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	 
	 String mysqlId;
	 SharedPreferences pref;
	Editor editor;
	 protected LocationManager locationManager;
	 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		
		return null;
	}
	
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
	      // Let it continue running until it is stopped.
	      Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
	      myTimerTask  = new MyTimerTask();
	      timer = new Timer();
	      
	      pref = getApplicationContext().getSharedPreferences("cosTamSettings", 0); 
	      //editor = pref.edit();
	      
	      //mysqlId = pref.getString("mysqlId", "0");
	      
	     // Log.d("mysql",);
	     
	      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        
			locationManager.requestLocationUpdates(
			        LocationManager.GPS_PROVIDER, 
			        MINIMUM_TIME_BETWEEN_UPDATES, 
			        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
			        new MyLocationListener()
			);
	     
	      timer.schedule(myTimerTask, 5000, 3000);   
	      
	      
	      return START_STICKY;
	   }
	   
	   class MyTimerTask extends TimerTask {
	        public void run() {
	        	//Log.d("mysql",mysqlId);
	        	if(latitude != latitudePrev && longitude != longitudePrev)
	        	{
	        		latitudePrev = latitude;
	        		longitudePrev = longitude;
	        		Log.d("GPS", Double.toString(latitude));
	        		new AddLocation().execute();
	        	}
	        }
	    }
	   
	   @Override
	   public void onDestroy() {
	      super.onDestroy();
	      timer.cancel();
	      Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	   }
	
	   private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
			latitude=location.getLatitude();
			longitude=location.getLongitude();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		   
	   }
	   
	   class AddLocation extends AsyncTask<String, String, String> {
			 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }
	 
	        /**
	         * Creating product
	         * */
	        protected String doInBackground(String... args) {
	            	 
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("name", pref.getString("mysqlId", "0").toString()));
	            params.add(new BasicNameValuePair("lat", Double.toString(latitude)));
	            params.add(new BasicNameValuePair("lon", Double.toString(longitude)));
	           
	            Time now = new Time();
	    		now.setToNow();
	    		String date = now.format("%Y-%m-%d %H:%M:%S");
	    		//Log.d("date", date);
	    		params.add(new BasicNameValuePair("date", date));	    
	    		Log.d("params", params.toString());
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
	                    "POST", params);
	 
	            // check log cat fro response
	            Log.d("Create Response", "Start");
	            Log.d("Create Response", json.toString());
	 
	            // check for success tag
	            String id="999";
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	            	if (success == 1) {
	                   Log.d("GPS service",json.getString("Message"));
	                  
	                } 
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	      
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	            
	            
	        }
		}
	
	   

}


