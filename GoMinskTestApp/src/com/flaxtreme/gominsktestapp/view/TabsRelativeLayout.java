package com.flaxtreme.gominsktestapp.view;

import com.flaxtreme.gominsktestapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


public class TabsRelativeLayout extends RelativeLayout {

	public TabsRelativeLayout(Context context) {
		super(context);
		initView(context);
	}

	public TabsRelativeLayout(Context context, AttributeSet attrs) {
		super(context);
		initView(context);
	}
	
	public TabsRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	
	private void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bar_layout, null);
        addView(view);
	}

}
