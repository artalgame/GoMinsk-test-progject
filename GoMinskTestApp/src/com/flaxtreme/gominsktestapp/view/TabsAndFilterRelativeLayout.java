package com.flaxtreme.gominsktestapp.view;

import com.flaxtreme.gominsktestapp.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


public class TabsAndFilterRelativeLayout extends RelativeLayout {

	private View rootView;

	public TabsAndFilterRelativeLayout(Context context) {
		super(context);
		initView(context);
	}

	public TabsAndFilterRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	public TabsAndFilterRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	
	private void initView(Context context) {
		rootView = LayoutInflater.from(context).inflate(R.layout.tabs_and_filter_bar_layout, null);
        addView(rootView);
	}

	public void setBackgroundColor(int color){
		setBackgroundFromDrawable(new ColorDrawable(color));
	}
	
	@SuppressLint("NewApi")
	public void setBackgroundFromDrawable(Drawable background){
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		
		if(currentapiVersion < Build.VERSION_CODES.JELLY_BEAN){
			rootView.setBackgroundDrawable(background);	
		}else{
			rootView.setBackground(background);
		}
		rootView.invalidate();
	}
	
}
