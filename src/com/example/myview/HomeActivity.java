package com.example.myview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity{

	public final static String PLAY_MESSAGE = "com.example.myview.PLAY";
	public final static String OPTIONS_MESSAGE = "com.example.myview.OPTIONS";
	public final static String EXIT_MESSAGE = "com.example.myview.EXIT";
	public static Typeface face;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_home);
	        
	        face=Typeface.createFromAsset(getAssets(), "fonts/Origami.ttf");

	        Button btnPlay = (Button) findViewById(R.id.btnPlay);
	        Button btnOption = (Button) findViewById(R.id.btnOption);
	        Button btnExit = (Button) findViewById(R.id.btnExit);
	        
	        btnPlay.setTypeface(face);
	        btnOption.setTypeface(face);
	        btnExit.setTypeface(face);
	   	 
	        //Click Play Button
	        btnPlay.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View v) {
	                //Starting a new Intent
	                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	                intent.putExtra(PLAY_MESSAGE, 1);
			    	startActivity(intent);
	            }
	        });
	        
	        //Click Option Button
	        btnOption.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View v) {
	                //Starting a new Intent
	                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
	                intent.putExtra(OPTIONS_MESSAGE, 1);
			    	startActivity(intent);
	            }
	        });
	        
	        //Click Exit Button
	        btnExit.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View v) {
	                //Exit the activity
	            }
	        });
	        
	 }
}
