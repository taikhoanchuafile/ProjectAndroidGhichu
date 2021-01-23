package com.example.ghichu;

import java.util.ArrayList;

import android.R.raw;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Contacts.Intents;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText etEmail, etPassword;
    TextView tvRegister;
    Button btnLogin;
    SQLiteDatabase db;
    String username;
    String password;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);

        db=openOrCreateDatabase("ghichu.sqlite", MODE_PRIVATE, null);
        CreateTable();
        			
        		
        		
        tvRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(i);
			}
		});
        
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String u = etEmail.getText().toString();
				String p = etPassword.getText().toString();
				String data="";
				if(u.equals("") || p.equals(""))
				{
					Toast.makeText(LoginActivity.this,R.string.notnull, Toast.LENGTH_SHORT).show();
					
				}else {
					Cursor c = db.rawQuery("SELECT * FROM ACCOUNT WHERE UserName='"+u+"' AND PassWord='"+p+"'",null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						data += c.getString(1)+"-"+c.getString(2);
						c.moveToNext();
					}
					c.close();
					if(data != ""){
						Toast.makeText(LoginActivity.this,R.string.logged_in_successfully, Toast.LENGTH_SHORT).show();
						Intent i = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(i);
						
					}else {
						Toast.makeText(LoginActivity.this,R.string.wrong_account_or_password, Toast.LENGTH_SHORT).show();
						}
					
					}
				}
		});
        
      }

	private void CreateTable(){
		   db.execSQL("CREATE TABLE IF NOT EXISTS ACCOUNT (" +
	       		"ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
	       		"UserName TEXT, " +
	       		"PassWord TEXT, " +
	       		"Email TEXT);");
	       db.execSQL("CREATE TABLE IF NOT EXISTS WORK (" +
	          		"ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
	          		"CONTENT TEXT);");
	   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
