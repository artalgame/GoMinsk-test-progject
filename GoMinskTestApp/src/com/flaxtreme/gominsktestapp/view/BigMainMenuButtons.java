package com.flaxtreme.gominsktestapp.view;

import java.util.ArrayList;

import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.GoMinskIntentHelper;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.CityMapActivity;
import com.flaxtreme.gominsktestapp.activity.SuggestedWalksActivity;
import com.flurry.android.FlurryAgent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class BigMainMenuButtons extends FrameLayout implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {
	
	private ImageView mainImageView;
	private Button button1Sight;
	private Button button2Map;
	private Button button3Walks;
	private Rect buttonRect;
	private ArrayList<Float> userTouchMoveArray;
	
	public BigMainMenuButtons(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode()){
        	initView(context);
        }
    }

    public BigMainMenuButtons(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BigMainMenuButtons(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        
    	View view = LayoutInflater.from(context).inflate(R.layout.main_menu_big_buttons, null);
        addView(view);
        
        mainImageView = (ImageView)view.findViewById(R.id.mainImage);
       
        button1Sight = (Button)view.findViewById(R.id.button1Sights);
        button2Map = (Button)view.findViewById(R.id.button2CityMap);
        button3Walks = (Button)view.findViewById(R.id.button3Walks);
        
        if(button1Sight!=null){
        	button1Sight.setOnClickListener(this);
        	button1Sight.setOnLongClickListener(this);
        	button1Sight.setOnTouchListener(this);
        }
        
        if(button2Map!=null){
        	button2Map.setOnClickListener(this);
        	button2Map.setOnLongClickListener(this);
        	button2Map.setOnTouchListener(this);
        }
        
        if(button3Walks!=null){
        	button3Walks.setOnClickListener(this); 
        	button3Walks.setOnLongClickListener(this);
        	button3Walks.setOnTouchListener(this);
        }      
    }

	@Override
	public void onClick(View button) {
		if(button.getId() == R.id.button1Sights)
		{
			FlurryAgent.logEvent(FlurryConstants.BUTTON_SIGHT_AND_CULTURE_CLICKED);
			button1Sight.setSelected(true);
			changeMainPicture(R.drawable.main_menu_big_1);
			GoMinskIntentHelper.StartCategoryActivity(getContext(), GoMinskConstants.SIGHT_CATEGORY_ID);
		}
		else
			if(button.getId() == R.id.button2CityMap){
				FlurryAgent.logEvent(FlurryConstants.BUTTON_CITY_MAP_CLICKED);
				button2Map.setSelected(true);
				changeMainPicture(R.drawable.main_menu_big_2);
				getContext().startActivity(new Intent(getContext(), CityMapActivity.class));
			}
			else
				if(button.getId() == R.id.button3Walks){
					FlurryAgent.logEvent(FlurryConstants.BUTTON_SUGGESTED_WALKS_CLICKED);
					button3Walks.setSelected(true);
					changeMainPicture(R.drawable.main_menu_big_3);
					getContext().startActivity(new Intent(getContext(), SuggestedWalksActivity.class));
				}
	}

	private void changeMainPicture(int mainMenuImageID) {
		mainImageView.setImageResource(mainMenuImageID);
		mainImageView.invalidate();
	}

	@Override
	public boolean onTouch(View button, MotionEvent event) {

		if(event.getAction() == MotionEvent.ACTION_MOVE )
		{
			buttonRect = new Rect(button.getLeft(), button.getTop(), button.getRight(), button.getBottom());
			float currentX = event.getX();
	        this.userTouchMoveArray.add(currentX);

	        boolean touchIsMoved;
			if (this.userTouchMoveArray.size() == 1) {
	            touchIsMoved = false;
	        } else {
	            float oldestX = userTouchMoveArray.get(0);
	            if (Math.abs(currentX - oldestX) > 2.0f) {
	                touchIsMoved = true;
	            } else {
	                touchIsMoved = false;
	            }
	        }
			if(touchIsMoved && !buttonRect.contains((int)event.getX(), (int)event.getY()))
			{
				changeMainPicture(R.drawable.main_menu_big_0);
				return false;
			}
		}
		
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN)
		{
			if(button.getId() == R.id.button1Sights)
				changeMainPicture(R.drawable.main_menu_big_1);
				
			if(button.getId() == R.id.button2CityMap)
				changeMainPicture(R.drawable.main_menu_big_2);
					
			if(button.getId() == R.id.button3Walks)
				changeMainPicture(R.drawable.main_menu_big_3);
			
			
			return false;
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP)
		{
			userTouchMoveArray = new ArrayList<Float>();
			changeMainPicture(R.drawable.main_menu_big_0);
			return false;
		}
		return false;
	}

	public void setDefaultState() {
		changeMainPicture(R.drawable.main_menu_big_0);
		button1Sight.setSelected(false);
		button2Map.setSelected(false);
		button3Walks.setSelected(false);
		userTouchMoveArray = new ArrayList<Float>();
	}

	@Override
	public boolean onLongClick(View button) {
		if(button.getId() == R.id.button1Sights){
			changeMainPicture(R.drawable.main_menu_big_1);
		}
		else
			if(button.getId() == R.id.button2CityMap){
				changeMainPicture(R.drawable.main_menu_big_2);
			}
			else
				if(button.getId() == R.id.button3Walks){
					changeMainPicture(R.drawable.main_menu_big_3);
				}
		return false;
	}
}
