package com.example.hello2;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceModule extends Service {

	
	 Timer timer;
	 MyTimerTask myTimerTask;
	 
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


	      timer.schedule(myTimerTask, 3000, 1500);   
	      
	      
	      return START_STICKY;
	   }
	   
	   class MyTimerTask extends TimerTask {
	        public void run() {
	        	Log.d("tim", "a");
	        }
	    }
	   
	   @Override
	   public void onDestroy() {
	      super.onDestroy();
	      timer.cancel();
	      Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	   }
	
	   
	

}

