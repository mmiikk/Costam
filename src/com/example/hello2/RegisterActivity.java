package com.example.hello2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.text.TextUtils;

public class RegisterActivity extends Activity {

	Button btnLogin;
	Button btnAdd;
	EditText tbPassword;
	EditText tbLogin;
	EditText tbRepeat;
	TextView tvMessage;
	
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
				String hashedPwd;
				Hash hash = new Hash();
				hashedPwd = hash.getHash(tbPassword.getText().toString());
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				
				db.addProfile(new Profile(tbLogin.getText().toString(),hashedPwd));
				tvMessage.setVisibility(View.VISIBLE);
				tvMessage.setText("Dodano profil, mo¿esz siê zalogowaæ");
		        
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

}
