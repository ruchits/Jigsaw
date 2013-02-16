package com.example.myview;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.myview.ImageActivity.tile;


@TargetApi(11)
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private static final String TAG = "com.example.myView.ImageAdapter";
	private int MAX_WIDTH, MAX_HEIGHT;

	private ArrayList<tile> imageList;

	public ImageAdapter(Context c, ArrayList<tile> imageList) {
        mContext = c;
        this.imageList = imageList;
        Log.e(TAG, "ImageMap size = " + imageList.size());
        MAX_WIDTH = ImageActivity.MAX_WIDTH;
        MAX_HEIGHT = ImageActivity.MAX_HEIGHT;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(MAX_WIDTH, MAX_HEIGHT));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setPadding(1, 1, 1, 1);
            imageView.setTag(position);
        } else {
            imageView = (ImageView) convertView;
        }

        //Log.e(TAG, "Setting the image view inside the grid- position= " + position);

        //imageView.setImageBitmap((Bitmap)imageList.get(position));
        tile t = imageList.get(position);
        Bitmap bm = t.bitmap;
        //Log.e(TAG, "position= " + t.position);
        imageView.setImageBitmap(bm);
        
        // Creates a new drag event listener
        myDragEventListener mDragListen = new myDragEventListener();
        // Sets the drag event listener for the View
        imageView.setOnDragListener(mDragListen);
        
        return imageView;
	}

}
