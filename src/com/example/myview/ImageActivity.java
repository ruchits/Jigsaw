package com.example.myview;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

@TargetApi(11)
public class ImageActivity extends Activity {
	private Context context;
	private static final String TAG = "com.example.myView.ImageActivity";
	public static int MAX_WIDTH;
	public static int MAX_HEIGHT;
	private int numCols = 6;
	private int numRows = 6;
	// Create a string for the ImageView label
	private static final String IMAGEVIEW_TAG = "icon bitmap";
    private int level = 0;
    private int resId;
    private GridView grid;
    
	public class tile {
		Bitmap bitmap;
		int position;
		
		public tile(Bitmap bm, int pos) {
			// TODO Auto-generated constructor stub
			this.bitmap = bm;
			this.position = pos;
		}
	}

	public static ArrayList<tile> imageMap;
	public static ArrayList<tile> shuffleMap;
	
	@TargetApi(13)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        GridView gridView = (GridView) findViewById(R.id.image_view);
        grid = gridView;
        context = MyApplication.getAppContext();

        // Get the level of the game.
        level = OptionActivity.LEVEL;
        Log.e(TAG, "Creating puzzle for level= " + level);
        
        switch(level) {
        case 0:
        	numCols = 3;
        	numRows = 3;
        	break;
        case 1:
        	numCols = 4;
        	numRows = 4;
        	break;
        case 2:
        	numCols = 6;
        	numRows = 6;
        	break;
        	
        }
        
        gridView.setNumColumns(numCols);
        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int myHeight = 0;

        switch (metrics.densityDpi) {
        	case DisplayMetrics.DENSITY_XHIGH:
        		 Log.i("display", "Xhigh");
                 myHeight = (int) (screenHeight - (3.5 * 48));
                 break;
            case DisplayMetrics.DENSITY_HIGH:
                Log.i("display", "high");
                myHeight = (int) (screenHeight - (3.5 * 36));
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                Log.i("display", "medium/default");
                myHeight = (int) (screenHeight - (3.5 * 24));
                break;
            case DisplayMetrics.DENSITY_LOW:
                Log.i("display", "low");
                myHeight = (int) (screenHeight - (3.5 * 18));
                break;
            default:
                Log.i("display", "Unknown density");
        }
        
        screenHeight = myHeight;
        Log.e(TAG, "screen width= " + screenWidth + "height= " + screenHeight);
        MAX_WIDTH = (int) Math.ceil(screenWidth / (double) numCols);
        MAX_HEIGHT = (int) Math.ceil(screenHeight / (double) numRows);
        Log.e(TAG, "MAX_WIDTH= " + MAX_WIDTH + "MAX_HEIGHT= " + MAX_HEIGHT);
        
        
        // Get intent from main activity
        Intent intent = getIntent();
        resId = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        Log.e(TAG, "resID= " + resId);
        
        // Get the width and height of the image first
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        Log.e(TAG, "options width= " + options.outWidth + " height= " + options.outHeight);

        // Set the tile size based on column attributes
        //int numCols = gridView.getNumColumns();
        Log.e(TAG, "numofCol= " + numCols);
        int tileWidth = (int) Math.ceil(options.outWidth / (double) numCols);
        int tileHeight = (int) Math.ceil(options.outHeight / (double) numCols);
        Log.e(TAG, "tileWidth= " + tileWidth + "tileHeight= " + tileHeight);
        
        //Open Raw resource into a bitmap, we need the input stream to use RegionDecoder.
        InputStream is = context.getResources().openRawResource(resId);
        
        //Split the image into bitmaps each MAX_SIZExMAX_SIZE.
        BitmapRegionDecoder decoder = null;
        try {
			decoder = BitmapRegionDecoder.newInstance(is, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        imageMap = new ArrayList<tile>();
        
        int position=0;
        int i,j;
        for(i=0; i<options.outHeight;){
        	int h = i;
        	for(j=0; j<options.outWidth;) {
        		Log.e(TAG, "RECT ( " + j + ", " + h + ", " + (j + tileWidth) +", " +(h + tileHeight) + " )");
        		Bitmap region = decoder.decodeRegion(new Rect(j, h, (j + tileWidth), (h + tileHeight)), null);
        		Bitmap bm = Bitmap.createScaledBitmap(region, (MAX_WIDTH-1), (MAX_HEIGHT-1), false);
        		tile t = new tile(bm, position);
        		imageMap.add(position, t);
        		
        		if(region != bm) {
        			region.recycle();
        		}
        		j += tileWidth;
        		position +=1;
        	}
        	i += tileHeight;
        }
        
        Log.e(TAG, "imagMap size = " + imageMap.size()); 
        ImageAdapter tileAdapter = new ImageAdapter(context, imageMap);
        gridView.setAdapter(tileAdapter);
        Log.e(TAG, "gridView adapter set");
        
        
        shuffleMap = new ArrayList<tile>(imageMap.size());
        for(tile t : imageMap) {
            shuffleMap.add(t);
        }
        //Collections.copy(shuffleMap, imageMap);
        //Shuffle the arraylist now
        Collections.shuffle(shuffleMap);

        
        // Click event for single list row
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e(TAG, "onItemLongClick - position= " + position);
				
				// Make phone vibrate
				Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
				vibe.vibrate(10);
				
				// Get the imageView at this position
				ImageView currentView = (ImageView) parent.getChildAt(position);
			    currentView.setTag(IMAGEVIEW_TAG);
	            
				// Create a new ClipData.
			    // This is done in two steps to provide clarity. The convenience method
			    // ClipData.newPlainText() can create a plain text ClipData in one step.

			    // Create a new ClipData.Item from the ImageView object's tag
			    ClipData.Item item = new ClipData.Item((CharSequence) currentView.getTag());
			    String[] mimeType =  {ClipDescription.MIMETYPE_TEXT_PLAIN};

			    // Create a new ClipData using the tag as a label, the plain text MIME type, and
			    // the already-created item. This will create a new ClipDescription object within the
			    // ClipData, and set its MIME type entry to "text/plain"
			    ClipData dragData = new ClipData((CharSequence)currentView.getTag(), mimeType, item);
			    
			    // Instantiates the drag shadow builder.
			    View.DragShadowBuilder myShadow = new MyDragShadowBuilder(currentView);
			    
			    // Starts the drag
	            currentView.startDrag(dragData,  // the data to be dragged
	                        myShadow,  // the drag shadow builder
	                        view,      // no need to use local data
	                        0          // flags (not currently used, set to 0)
	            );
	            
				return false;
			}
		});
		
        gridView.setOnTouchListener(new OnTouchListener() {

			@TargetApi(16)
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ImageView thumbImage = (ImageView) findViewById(R.id.thumb_image);
				/*
				if( (event.getEdgeFlags() & MotionEvent.EDGE_LEFT) != 0 ) {
				    //code to handle swipe from left edge
					Log.e(TAG, "WOO - edge touch works!!");
				}
				*/
				if( event.getAction() == MotionEvent.ACTION_DOWN && event.getX() < 5.0f ){
				    //handle swipe from left edge
					Log.e(TAG, "WOO - something works!!");
					thumbImage.setVisibility(1);
        			thumbImage.setScaleType(ScaleType.FIT_XY);
        			thumbImage.setAlpha((float) 20);
        			thumbImage.setBackgroundResource(resId);		
				}
				else if ( event.getAction() == MotionEvent.ACTION_UP ) {
					thumbImage.setVisibility(0);
					thumbImage.setBackground(null);
				}
				else {
					Log.e(TAG, "WOO - nothing works :(");
				}
				
				return false;
			}
        	
        });

        Button btnAnimate = (Button) findViewById(R.id.animate_btn);
        //Listening to button event
        btnAnimate.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                //Starting animation
            	v.setVisibility(View.INVISIBLE);
            	startAnimation();   
            }
        });
	}
	
	private void startAnimation() {
		ArrayList<tile> imageList = imageMap;
		ArrayList<tile> shuffleList = shuffleMap;
		
		Log.e(TAG, "starting animation stuff..");
		
		//Loop through every view in the grid and start an animation.
		for (int i=0; i<imageList.size(); i++) {
			
			// Get the current tile at position "i"
			ImageView imageView = (ImageView) grid.findViewWithTag(i);
			tile t = imageList.get(i);
			Log.e (TAG, "current tile position= " + i);
			
	        // Get the current co-ordinates of the tile getX() and getY()
			int[] location = new int[2];
			imageView.getLocationOnScreen(location);
			int curr_x = location[0];
			int curr_y = location[1];
	        Log.e(TAG, "curr_x= " + curr_x + "curr_y= " + curr_y);
	        

			// Get new position of the tile from shuffleMap.  	         
	        int new_position = 0;
	        for (int j=0; i<shuffleList.size(); j++) {
	        	tile shuffle_tile = shuffleList.get(j);
	        	if (shuffle_tile.position == t.position) {
	        		new_position = j;
	        		break;
	        	}	
	        }
	        Log.e (TAG, "new tile position= " + new_position);
	        	        
	        //  Get the destination co-ordinates from the new position of the tile. "findView(new position).getX() and getY()"
	        ImageView shuffleView = (ImageView) grid.findViewWithTag(new_position);
	        shuffleView.getLocationOnScreen(location);
	        int shuff_x = location[0];
			int shuff_y = location[1];
	        Log.e(TAG, "shuff_x= " + shuff_x + "shuff_y= " + shuff_y);
	        
	        // Find the change in x and change in y
	        int delta_x = shuff_x - curr_x;
	        int delta_y = shuff_y - curr_y;
	        
	        // Construct translate animation based on the coordinates
	        //TranslateAnimation moveLefttoRight = new TranslateAnimation(0, 50, 0, 0);
	        TranslateAnimation animate = new TranslateAnimation(0, delta_x, 0, delta_y);
	       	animate.setDuration(1000);
	       	animate.setFillAfter(true);
	   	    
	       	Log.e(TAG, "starting animation");
	       	imageView.startAnimation(animate);
	       	
	       	animate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					Log.e(TAG, "onAnimationEnd");
					ImageAdapter tileAdapter = new ImageAdapter(context, shuffleMap);
			        ((GridView) grid).setAdapter(tileAdapter);
			        ((GridView) grid).invalidateViews();
			        Log.e(TAG, "gridView adapter set");
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					Log.e(TAG, "onAnimationRepeat");
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationStart(Animation animation) {
					Log.e(TAG, "onAnimationStart");
					// TODO Auto-generated method stub
					
				}
	       		
	       	});
	        
		}
		
	}
	
}
