package com.example.hello2;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	Button btnAddProfile;
	Button btnLogin;
	TextView loginError;
	EditText tbLogin;
	EditText tbPassword;
	DatabaseHandler db;
	SharedPreferences pref;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnAddProfile = (Button) findViewById(R.id.btnAddProfile);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		tbLogin = (EditText) findViewById(R.id.tbLogin);
		tbPassword = (EditText) findViewById(R.id.tbPassword);
		loginError = (TextView) findViewById(R.id.loginError);
		
		pref = getApplicationContext().getSharedPreferences("cosTamSettings", 0); 
    	editor = pref.edit();
		
    	if(pref.getBoolean("isLogged", false))
    	{
    		Log.d("loged","yes");
    		
    	}
    	else
    	{
    		Log.d("loged","nope");
    	}
    	
    	
    	
		Log.d("a", "a");
		db = new DatabaseHandler(this);
        
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting .."); 
       // db.addProfile(new Profile("a1", "9100000000"));        
        
         
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts.."); 
        List<Profile> profiles = db.getAllProfiles();       
         
        for (Profile cn : profiles) {
            String log = "Id: "+cn.getId()+" ,Name: " + cn.getLogin() + " ,Phone: " + cn.getPassword();
                // Writing Contacts to log
        Log.d("Name: ", log);
        }
       
        
        btnAddProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);	
				finish();
			}
		});
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			    Profile profile = db.getProfile(tbLogin.getText().toString(),tbPassword.getText().toString());       
		       
			    if(profile.getLogin() == null || profile.getPassword() == null)
			    	loginError.setVisibility(View.VISIBLE);
			    else
			    {
			    		    	
			    	editor.putBoolean("isLogged", true); // Storing boolean - true/false
			    	editor.commit();

			    	
			    	Intent i = new Intent(getApplicationContext(),MainActivity.class);
			    	i.putExtra("login", profile.getLogin());
			    	i.putExtra("Id", profile.getId());
			    	startActivity(i);
			    	finish();
			    	
			    }
			  
			}
				
			
		});
        
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
	

}
