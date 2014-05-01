package com.flaxtreme.gominsktestapp.view;

import com.flaxtreme.gominsktestapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class WindowGeneralElementsWithBackButtonView extends WindowGeneralElementsView{

	private ImageButton backButton;
	
	public WindowGeneralElementsWithBackButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WindowGeneralElementsWithBackButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WindowGeneralElementsWithBackButtonView(Context context) {
        super(context);
    }
	
	@Override
	protected void initView(Context context) {
    	inflateLayout(context, R.layout.window_general_elements_with_back_button_layout);
    	setBackButton((ImageButton)findViewById(R.id.backImageButton));	
	}
	
	public ImageButton getBackButton() {
		return backButton;
	}

	public void setBackButton(ImageButton backButton) {
		this.backButton = backButton;
	}
}
