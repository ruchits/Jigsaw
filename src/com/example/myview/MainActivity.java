package com.example.myview;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final String TAG = "com.example.myView.MainActivity";
    private myAdapter adapter;
    private HashMap<Integer, Object> imageMap;
    public final static String EXTRA_MESSAGE = "com.example.myview.MESSAGE";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get intent from main activity
        Intent intent = getIntent();
        Integer resId = intent.getIntExtra(HomeActivity.PLAY_MESSAGE, 0);
        
        if(resId == 1) 
        	 Log.e(TAG, " SUCCESS INTENT resID= " + resId);
        
        GridView grid = (GridView) findViewById(R.id.grid_view);
        
        imageMap = new HashMap<Integer, Object>();
        imageMap.put(0, R.drawable.bnb);
        imageMap.put(1, R.drawable.mulah);
        imageMap.put(2, R.drawable.pocahontas);
        imageMap.put(3, R.drawable.tarzan);
        imageMap.put(4, R.drawable.thelionking);
    
        // Getting adapter by passing ArrayList of images
        adapter = new myAdapter(this, this, imageMap);
        grid.setAdapter(adapter);
 
        
        // Click event for single list row
        grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.e(TAG, "CLick went unregistered - position= " + position);
				
				// Do something in response to click
		    	Intent intent = new Intent(MyApplication.getAppContext(), ImageActivity.class);
		    	Integer resId = (Integer) imageMap.get(position);
		    	intent.putExtra(EXTRA_MESSAGE, resId);
		    	startActivity(intent);
		    	
			}
		});
		
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}