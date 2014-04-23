package com.example.hello2;



public class Profile {

	int _id;
	String _Login;
	String _Password;
	String _MysqlId;
	
	public Profile() {
		
	}
	
	public Profile(String login, String password) {
		this._Login=login;
		this._Password=password;
	}
	
	public Profile(String login, String password, String mysqlId) {
		this._Login=login;
		this._Password=password;
		this._MysqlId=mysqlId;
	}
	
	public Profile(int id, String login, String password) {
		this._id=id;
		this._Login=login;
		this._Password=password;
	}
	
	public Profile(int id, String login, String password, String MysqlId) {
		this._id=id;
		this._Login=login;
		this._Password=password;
		this._MysqlId=MysqlId;
	}
	
	public void setId(int id){
		this._id=id;
	}
	
	public void setLogin(String login){
		this._Login=login;
	}
	
	public void setPassword(String password){
		this._Password=password;
	}
	
	public void setMysqlId(String mysqqlId){
		this._MysqlId=mysqqlId;
	}
	
	public int getId(){
		return this._id;
	}
	
	public String getLogin(){
		return this._Login;
	}
	
	public String getPassword(){
		return this._Password;
	}
	
	public String getMysqlId(){
		return this._MysqlId;
	}
	
	
	
}
