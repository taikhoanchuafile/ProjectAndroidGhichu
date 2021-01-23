package com.example.ghichu;

import java.util.ArrayList;

import com.example.ghichu.R.string;

import android.R.raw;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



public class MainActivity extends Activity {
	
	SQLiteDatabase db;
	
	ListView lvContent;
	ArrayList<Content> arrayContent;
	ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lvContent = (ListView) findViewById(R.id.listViewContent);
        arrayContent = new ArrayList<Content>();
        
        adapter = new ContentAdapter(this,R.layout.content_layout, arrayContent);
        lvContent.setAdapter(adapter);
        
        //create database note
        db=openOrCreateDatabase("ghichu.sqlite", MODE_PRIVATE, null);
        CursorContent();
        
    }

   private void InsertData() {
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO ACCOUNT VALUES(NULL,'admin','123','abc@gmail.com')");
		db.execSQL("INSERT INTO WORK VALUES(NULL,'Thứ 2 - Tiết 2 - Android- B2.202')");
		
		
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
   
   
   
   public void CursorContent() {
	   Cursor dataContent = db.rawQuery("SELECT * FROM WORK",null);
	   arrayContent.clear();
       dataContent.moveToFirst();
       while (dataContent.isAfterLast()==false) {
    	   int id = dataContent.getInt(0);
    	   String content=dataContent.getString(1);
    	   arrayContent.add(new Content(id, content));
    	   dataContent.moveToNext();
		}
       adapter.notifyDataSetChanged();
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_content, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	if (item.getItemId() == R.id.menuAdd) {
    		 DialogAdd();
		}
        return super.onOptionsItemSelected(item);
    }
    
    private void DialogAdd() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_add_content);
		
		final EditText etContent = (EditText) dialog.findViewById(R.id.etContent);
		Button btnAddConten = (Button) dialog.findViewById(R.id.btnAddContent);
		Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
		
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		btnAddConten.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = etContent.getText().toString();
				if(content.equals("")){
					Toast.makeText(MainActivity.this,R.string.please_enter_content, Toast.LENGTH_SHORT).show();
				}else {
					db.execSQL("INSERT INTO WORK VALUES(NULL,'"+content+"')");
					Toast.makeText(MainActivity.this,R.string.added_successfully, Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					CursorContent();
				}
			}
		});
		dialog.show();
	}
    public void DialogEdit(final int id,String content){
 	   final Dialog dialog = new Dialog(MainActivity.this);
 	   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
 	   dialog.setContentView(R.layout.dialog_edit);
 	   
 	   final EditText etEditContent = (EditText) dialog.findViewById(R.id.etEditContent);
 	   Button btnEditContent = (Button) dialog.findViewById(R.id.btnEditContent);
 	   Button btnEditExit = (Button) dialog.findViewById(R.id.btnEditExit);
 	   
 	   etEditContent.setText(content);
		
		
		
		btnEditContent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String newcontent = etEditContent.getText().toString().trim();
				db.execSQL("UPDATE WORK SET CONTENT='"+newcontent+"' WHERE ID='"+id+"'");
				Toast.makeText(MainActivity.this,R.string.the_update_was_successful, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				CursorContent();
			}
		});
		
		btnEditExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
 	   dialog.show();
    }
    
    public void DialogDelete(final int id,String content){
    	AlertDialog.Builder dialogDelete = new AlertDialog.Builder(MainActivity.this);
    	dialogDelete.setMessage(R.string.are_you_sure);
    	
    	dialogDelete.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				db.execSQL("DELETE FROM WORK WHERE ID='"+id+"' ");
				Toast.makeText(MainActivity.this,R.string.deleted_successfully, Toast.LENGTH_SHORT).show();
				CursorContent();
			}
		});
    	
    	dialogDelete.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		} );
    	dialogDelete.show();
    }
    
}
