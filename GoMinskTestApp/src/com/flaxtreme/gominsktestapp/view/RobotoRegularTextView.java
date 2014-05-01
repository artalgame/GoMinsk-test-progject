package com.flaxtreme.gominsktestapp.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class RobotoRegularTextView extends TextView {
	public RobotoRegularTextView(Context context) {
        super(context);
        if(!isInEditMode())
        	style(context);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        	style(context);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        	style(context);
    }

    private void style(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto/roboto_regular.ttf");
        setTypeface(tf);
    }

}
