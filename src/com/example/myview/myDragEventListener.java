package com.example.myview;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.myview.ImageActivity.tile;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

@TargetApi(16)
public class myDragEventListener implements OnDragListener {
	private static final String TAG = "com.example.myView.myDragEventListener";
	private String dragData;
	private Context context = MyApplication.getAppContext();
	final int viewAlpha = 255;
	
    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    public boolean onDrag(View v, DragEvent event) {
    	
    	View dragView = (View) event.getLocalState();
    	GridView owner = (GridView) v.getParent();
        // Defines a variable to store the action type for the incoming event
        final int action = event.getAction();

        // Handles each of the expected events
        switch(action) {
        
        case DragEvent.ACTION_DRAG_STARTED:
        	Log.e(TAG, "ACTION_DRAG_STARTED");
        	// Determines if this View can accept the dragged data
            if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                // returns true to indicate that the View can accept the dragged data.
                return(true);
            } else {
            	// Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return(false);
            }
		case DragEvent.ACTION_DRAG_ENTERED:
			Log.e(TAG, "ACTION_DRAG_ENTERED");

			((ImageView) v).setImageAlpha(128);
            // Invalidate the view to force a redraw in the new tint
            v.invalidate();

            return(true);

		case DragEvent.ACTION_DRAG_LOCATION:
        	// Ignore the event
            return(true);
                
		case DragEvent.ACTION_DRAG_EXITED:
			Log.e(TAG, "ACTION_DRAG_EXITED");

			((ImageView) v).setImageAlpha(viewAlpha);
            // Invalidate the view to force a redraw in the new tint
            v.invalidate();

            return(true);

		case DragEvent.ACTION_DROP:
			Log.e(TAG, "ACTION_DROP");
        	// Gets the item containing the dragged data
            ClipData.Item item = event.getClipData().getItemAt(0);

            // Gets the text data from the item.
            dragData = (String) item.getText();

            // Displays a message containing the dragged data.
            Toast.makeText(context, "Dragged data is " + dragData, Toast.LENGTH_LONG);
            Log.e(TAG, "Dragged data is " + dragData);
            
            // Swap the 2 views
            swapViews(v, dragView);
            owner.invalidateViews();
            
            // Returns true. DragEvent.getResult() will return true.
            return(true);

		case DragEvent.ACTION_DRAG_ENDED:
			Log.e(TAG, "ACTION_DRAG_ENDED");
 
            ((ImageView) v).setImageAlpha(viewAlpha);
            // Invalidates the view to force a redraw
            v.invalidate();

            // Does a getResult(), and displays what happened.
            if (event.getResult()) {
            	Toast.makeText(context, "The drop was handled.", Toast.LENGTH_LONG);

            } else {
            	Toast.makeText(context, "The drop didn't work.", Toast.LENGTH_LONG);

            };

            // returns true; the value is ignored.
            return(true);

		// An unknown action type was received.
        default:
        	Log.e(TAG,"Unknown action type received by OnDragListener.");

            break;
        };
        Log.e(TAG, "NUNNN");
		return false;
    };
    
    // Swap the position of the 2 views
    private void swapViews(View currView, View dragView) {
    	if (dragView == null || currView == null) {
    		Log.e(TAG, "Missing views..");
    	}
    	else {
    		ArrayList<tile> list = ImageActivity.shuffleMap;
    		Log.e(TAG, "list size= " + list.size());
    		ViewGroup owner = (ViewGroup) currView.getParent();
    		int currIndex = owner.indexOfChild(currView);
    		int dragIndex = owner.indexOfChild(dragView);
    		Log.e(TAG, "currIndex= " + currIndex + "  dragIndex= " + dragIndex);
    		
    		tile currTile = list.get(currIndex);
    		tile dragTile = list.get(dragIndex);
    		Log.e(TAG, "currTile - arrayposn= " + currTile.position);
    		Log.e(TAG, "dragTile - arrayposn= " + dragTile.position);
    		
    		list.set(currIndex, dragTile);
    		list.set(dragIndex, currTile);
    	
    		boolean result = checkImageMap(list);
    		Log.e(TAG, "after swap - list size= " + list.size());
    	}
    }
    
    // Check if the imageMap is complete. If yes, then print success.
    public boolean checkImageMap(ArrayList<tile> list) {
    	int i = 0;
    	
    	while(i < list.size()) {
    		tile t = list.get(i);
    		if(t.position != i) {
    			Log.e(TAG, "Failed at position: tile position=  " + t.position + "  index= " + i);
    			return false;
    		}
    		i++;
    	}
    
        Toast.makeText(context, "SUCCESS!", Toast.LENGTH_LONG);
    	Log.e(TAG, "SUCCESS!!");
    	return true;
    }
    
    // Solve the whole puzzle by itself.
    public void solvepuzzle(ArrayList<tile> list) {
    	
    }
};