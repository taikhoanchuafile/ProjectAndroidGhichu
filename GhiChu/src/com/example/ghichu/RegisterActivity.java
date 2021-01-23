package com.example.ghichu;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	SQLiteDatabase db;
	 EditText etName, etEmail, etPassword;
	 Button btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		 etName = (EditText) findViewById(R.id.et_name);
	     etEmail = (EditText) findViewById(R.id.et_reg_email);
	     etPassword = (EditText) findViewById(R.id.et_reg_password);
	     btnRegister = (Button) findViewById(R.id.btn_register);
	     
	     db=openOrCreateDatabase("ghichu.sqlite", MODE_PRIVATE, null);
	     
	     btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = etName.getText().toString();
				String password = etPassword.getText().toString();
				String email = etEmail.getText().toString();
				
				if (username.equals("")||password.equals("")||email.equals("")) {
					Toast.makeText(RegisterActivity.this,R.string.notnull, Toast.LENGTH_SHORT).show();
				}else {
					db.execSQL("INSERT INTO ACCOUNT VALUES (null,'"+username+"','"+password+"','"+email+"')");
					Toast.makeText(RegisterActivity.this,R.string.Registered_successfully, Toast.LENGTH_SHORT).show();
					Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
					startActivity(i);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
