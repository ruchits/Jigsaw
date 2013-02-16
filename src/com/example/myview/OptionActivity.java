package com.example.myview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

public class OptionActivity extends Activity{

	private static final String TAG = "com.example.myView.OptionActivity";
	public static int LEVEL = 0;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
		 	setContentView(R.layout.activity_option);
	
		 	Typeface face = HomeActivity.face;
		 	
	        // Get intent from main activity
	        Intent intent = getIntent();
	        Integer diff = intent.getIntExtra(HomeActivity.OPTIONS_MESSAGE, 0);
	        
	        TextView title = (TextView) findViewById(R.id.option_title);
	        CheckBox easy = (CheckBox) findViewById(R.id.easy);
	        CheckBox medium = (CheckBox) findViewById(R.id.medium);
	        CheckBox boss = (CheckBox) findViewById(R.id.boss);
	        
	        title.setTypeface(face);
	        easy.setTypeface(face);
	        medium.setTypeface(face);
	        boss.setTypeface(face);
	        
	    	easy.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
	    		if (((CheckBox) v).isChecked()) {
	    			LEVEL = 0;
	    	        CheckBox medium = (CheckBox) findViewById(R.id.medium);
	    	        CheckBox boss = (CheckBox) findViewById(R.id.boss);
	    			medium.setChecked(false);
	    	        boss.setChecked(false);
	    		}
	     
	    	  }
	    	});
	    	
	    	medium.setOnClickListener(new OnClickListener() {
		    	  @Override
		    	  public void onClick(View v) {
		    		if (((CheckBox) v).isChecked()) {
		    			LEVEL = 1;
		    			CheckBox easy = (CheckBox) findViewById(R.id.easy);
		    	        CheckBox boss = (CheckBox) findViewById(R.id.boss);
		    			easy.setChecked(false);
		    	        boss.setChecked(false);
		    		}
		     
		    	  }
		    	});
		     
	    	boss.setOnClickListener(new OnClickListener() {
		    	  @Override
		    	  public void onClick(View v) {
		    		if (((CheckBox) v).isChecked()) {
		    			LEVEL = 2;
		    			CheckBox medium = (CheckBox) findViewById(R.id.medium);
		    	        CheckBox easy = (CheckBox) findViewById(R.id.easy);
		    			medium.setChecked(false);
		    	        easy.setChecked(false);
		    		}
		     
		    	  }
		    	});       
		    
	 }
	 
	 
	 protected void onResume() {
		 super.onResume();
		 
		 CheckBox e = (CheckBox)findViewById(R.id.easy);
		 CheckBox m = (CheckBox)findViewById(R.id.medium);
		 CheckBox h = (CheckBox)findViewById(R.id.boss);
		 
		 switch(LEVEL) {
		 case 0:
			 e.setChecked(true);
			 m.setChecked(false);
			 h.setChecked(false);
			 break;
		 case 1:
			 e.setChecked(false);
			 m.setChecked(true);
			 h.setChecked(false);
			 break;
		 case 2:
			 e.setChecked(false);
			 m.setChecked(false);
			 h.setChecked(true);
			 break;
		 default:
			 // do nothing
			 e.setChecked(false);
			 m.setChecked(false);
			 h.setChecked(false);
		 }
	 }
	 
	 protected void onSaveInstanceState(Bundle b) {
		  super.onSaveInstanceState(b);
		  b.putInt("LEVEL", LEVEL);
		}
}
