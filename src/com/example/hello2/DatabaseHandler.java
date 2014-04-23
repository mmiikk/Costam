package com.example.hello2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.*;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DB_VERSION = 2;
	private static final String DB_NAME = "costamDB";
	private static final String TABLE_NAME = "profiles";
	
	private static final String KEY_ID = "_id";
	private static final String KEY_LGN = "login";
	private static final String KEY_PWD = "password";
	private static final String KEY_MYSQL = "mysqlId";
	
	public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
	
	@Override
    public void onCreate(SQLiteDatabase db) {
		// Create table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LGN + " TEXT,"
                + KEY_PWD + " TEXT" + ", " + KEY_MYSQL + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
	
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
 
        // Create tables again
        onCreate(db);
    }
	
	public void addProfile(Profile profile){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(KEY_LGN, profile.getLogin());
		content.put(KEY_PWD, profile.getPassword());
		content.put(KEY_MYSQL, profile.getMysqlId());
		
		db.insert(TABLE_NAME, null, content);
		db.close();
	}
	
	public List<Profile> getAllProfiles(){
		List<Profile> profileList= new ArrayList<Profile>();
		String getAll = "select * from " + TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(getAll, null);
		
		if (cursor.moveToFirst()) {
	        do {
	        	
	            Profile profile = new Profile();
	            profile.setId(Integer.parseInt(cursor.getString(0)));
	            profile.setLogin(cursor.getString(1));
	            profile.setPassword(cursor.getString(2));
	            profile.setMysqlId(cursor.getString(3));
	            // Adding contact to list
	            profileList.add(profile);
	        } while (cursor.moveToNext());
	    }
		
		
		
		return profileList;
	}
	
	public Profile getProfile(String login, String password){
		Log.d("DB",login);
		Profile profile = new Profile();
		Log.d("DB",login);
		Hash hash = new Hash();
		String hashedPwd = hash.getHash(password);
		String getAll = "select * from " + TABLE_NAME + " where login=\'" + login +"\'";
		getAll += " and password=\'" + hashedPwd + "\'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(getAll, null);
		
		if (cursor.moveToFirst()) {
	        do {
	            
	            profile.setId(Integer.parseInt(cursor.getString(0)));
	            profile.setLogin(cursor.getString(1));
	            profile.setPassword(cursor.getString(2));
	            profile.setMysqlId(cursor.getString(3));
	            // Adding contact to list
	           
	        } while (cursor.moveToNext());
	    }
		
		
		
		return profile;
	}
	
	
}
