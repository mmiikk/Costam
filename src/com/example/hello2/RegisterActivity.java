package com.example.hello2;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;
import android.text.format.Time;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity {

	Button btnLogin;
	Button btnAdd;
	EditText tbPassword;
	EditText tbLogin;
	EditText tbRepeat;
	TextView tvMessage;
	JSONParser jsonParser = new JSONParser();
	Boolean dbOk = false;
	
	private ProgressDialog pDialog;
	private static String url_create_product = "http://testowastrona.comli.com/AddProfile.php";
	 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		tbPassword = (EditText) findViewById(R.id.tbPassword);
		tbRepeat = (EditText) findViewById(R.id.tbRepeat);
		tbLogin = (EditText) findViewById(R.id.tbLogin);
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		
		
		btnAdd.setEnabled(false);
		btnLogin.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);	
				finish();
			}
		});
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbOk = false;
				String hashedPwd;
				Hash hash = new Hash();
				hashedPwd = hash.getHash(tbPassword.getText().toString());
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				
				Profile profile = db.getProfile(tbLogin.getText().toString(),tbPassword.getText().toString());       
			       
			    if(profile.getLogin() == null || profile.getPassword() == null)
			    {
			    	new CreateNewProfile().execute();
			    
					
			    }
			    else
			    {
			    	tvMessage.setVisibility(View.VISIBLE);
			    	tvMessage.setTextColor(Color.RED);
					tvMessage.setText("U¿ytkownik o takiej nazwie ju¿ istnieje");
			    }
		        
			}
		});
		
		tbRepeat.addTextChangedListener(new TextWatcher(){
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
				
				if(!TextUtils.equals(tbPassword.getText().toString(), tbRepeat.getText().toString()))
					tbRepeat.setError("Podane has³a ró¿ni¹ siê");
				else
					tbRepeat.setError(null);
					
				enableAdd();
				
			};
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
		});
		tbPassword.addTextChangedListener(new TextWatcher(){
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
				
				if(!TextUtils.equals(tbPassword.getText().toString(), tbRepeat.getText().toString()))
					tbRepeat.setError("Podane has³a ró¿ni¹ siê");
				else
					tbRepeat.setError(null);
					
				enableAdd();
				
			};
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
		});
		tbLogin.addTextChangedListener(new TextWatcher(){
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
				enableAdd();
				
			};
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	private void enableAdd(){
		Boolean enable = true;
		
		if(TextUtils.isEmpty(tbPassword.getText().toString()))
			enable = false;
		
		if(TextUtils.isEmpty(tbRepeat.getText().toString()))
			enable = false;
		
		if(TextUtils.isEmpty(tbLogin.getText().toString()))
			enable = false;
		
		btnAdd.setEnabled(enable);
	
	}
	
	
	
	
	class CreateNewProfile extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Tworzenie profilu..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String name = tbLogin.getText().toString();
            String phone = "555";
            String hashedPwd;
			Hash hash = new Hash();
			hashedPwd = hash.getHash(tbPassword.getText().toString());
			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phone", phone));
            
           
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
            	id = json.getString("id");
                if (success == 1) {
                   
                    db.addProfile(new Profile(tbLogin.getText().toString(),
    		    			hashedPwd,
    		    			tbLogin.getText().toString()+id));
    		   
                    dbOk = true;
                    Log.d("MSG","dodano");
                } else {
                    // failed to create product
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
            pDialog.dismiss();
                 if(dbOk)
	    	{
                		tvMessage.setVisibility(View.VISIBLE);
     				tvMessage.setTextColor(Color.GREEN);
     				tvMessage.setText("Dodano profil, mo¿esz siê zalogowaæ");
		    	
	    	}
	    	else
	    	{
	    		tvMessage.setVisibility(View.VISIBLE);
		    	tvMessage.setTextColor(Color.RED);
				tvMessage.setText("B³¹d tworzenia u¿ytkownika");
	    	}
            
        }
	}

}
