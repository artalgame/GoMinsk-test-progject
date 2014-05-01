package com.flaxtreme.gominsktestapp.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class RobotoMediumTextView extends TextView {
	public RobotoMediumTextView(Context context) {
        super(context);
        if(!isInEditMode())
        	style(context);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        	style(context);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        	style(context);
    }

    private void style(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto/roboto_medium.ttf");
        setTypeface(tf);
    }

}
