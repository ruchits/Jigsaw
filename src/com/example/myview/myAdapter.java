package com.example.myview;


import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.opengl.GLES20;

public class myAdapter extends BaseAdapter{
	private Activity activity;
    private static LayoutInflater inflater=null; 
    private HashMap<Integer, Object> imageList;
    private Context context;
    private static final String TAG = "com.example.myview.myAdapter";
    
    public myAdapter(Activity a, Context c, HashMap<Integer, Object> imageList) {
    	this.activity = a;
    	this.imageList = imageList;
    	this.context = c;
    	inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return imageList.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
    	View vi=convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.rowlayout, null);
        }
  
        int[] maxTextureSize = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        Log.e(TAG, "texture maxSize= " + maxTextureSize[0]);
        
        // Get the image resource and decode into a bitmap
        Integer resId =  (Integer) imageList.get(position);
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        // Decode bitmap with inSampleSize set
        options.inSampleSize = 1;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, options);
       
        // Set Image View
        ImageView image = (ImageView)vi.findViewById(R.id.image);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setImageBitmap(bm);
        
       /*
        	final TranslateAnimation moveLefttoRight = new TranslateAnimation(0, 200, 0, 0);
        	moveLefttoRight.setDuration(1000);
        	moveLefttoRight.setFillAfter(true);
        	
        	//vi.setOnClickListener(new OnClickListener() {
        	//public void onClick(View v) {
        		Log.e(TAG, "starting animation");
                vi.startAnimation(moveLefttoRight);
            //}
        	//});
       */
        
        return vi;
    }
    
}