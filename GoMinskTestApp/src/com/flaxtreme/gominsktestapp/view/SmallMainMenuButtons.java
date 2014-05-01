package com.flaxtreme.gominsktestapp.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.GoMinskIntentHelper;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.MetroActivity;
import com.flurry.android.FlurryAgent;

public class SmallMainMenuButtons  extends FrameLayout implements View.OnClickListener {
		
		Button button1Shops;
		Button button2Eat;
		Button button3Metro;
		Button button4WiFi;
		Button button5Bars;
		
		ImageView mainImage;
		
		public SmallMainMenuButtons(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        if(!isInEditMode()){
	        	initView(context);
	        }
	    }

	    public SmallMainMenuButtons(Context context, AttributeSet attrs) {
	        super(context, attrs);
	     
	        initView(context);
	    }

	    public SmallMainMenuButtons(Context context) {
	        super(context);
	     
	        initView(context);
	    }

	    private void initView(Context context) {
	        View view = LayoutInflater.from(context).inflate(R.layout.main_menu_small_buttons, null);
	        addView(view);
	        button1Shops = (Button)view.findViewById(R.id.button1Shops);
	        button2Eat   = (Button)view.findViewById(R.id.button2Food);
	        button3Metro = (Button)view.findViewById(R.id.button3Metro);
	        button4WiFi  = (Button)view.findViewById(R.id.button4WiFi);
	        button5Bars  = (Button)view.findViewById(R.id.button5Bars);
	        
	        mainImage = (ImageView)view.findViewById(R.id.mainImage);
	        
	        button1Shops.setOnClickListener(this);
	        button2Eat.setOnClickListener(this);
	        button3Metro.setOnClickListener(this);
	        button4WiFi.setOnClickListener(this);
	        button5Bars.setOnClickListener(this);
	    }

		@Override
		public void onClick(View clickedButton) {
			
			int id = clickedButton.getId();
			
			if(id == button1Shops.getId()){
				FlurryAgent.logEvent(FlurryConstants.BUTTON_SHOPPING_CLICKED);
				changeMainImage(R.drawable.main_menu_small_0);
				GoMinskIntentHelper.StartCategoryActivity(getContext(), GoMinskConstants.SHOPS_CATEGORY_ID);
			}else
				if(id == button2Eat.getId()){
					FlurryAgent.logEvent(FlurryConstants.BUTTON_FOOD_CLICKED);
					changeMainImage(R.drawable.main_menu_small_2);
					GoMinskIntentHelper.StartCategoryActivity(getContext(), GoMinskConstants.FOOD_CATEGORY_ID);
				}else
					if(id == button3Metro.getId()){
						FlurryAgent.logEvent(FlurryConstants.BUTTON_METRO_CLICKED);
						changeMainImage(R.drawable.main_menu_small_3);
						getContext().startActivity(new Intent(getContext(), MetroActivity.class));
					}else
						if(id == button4WiFi.getId()){
							FlurryAgent.logEvent(FlurryConstants.BUTTON_WiFi_CLICKED);
							changeMainImage(R.drawable.main_menu_small_0);
							GoMinskIntentHelper.StartCategoryActivity(getContext(), GoMinskConstants.WiFi_CATEGORY_ID);
						}else
							if(id == button5Bars.getId()){
								FlurryAgent.logEvent(FlurryConstants.BUTTON_NIGHT_LIFE_CLICKED);
								changeMainImage(R.drawable.main_menu_small_0);
								GoMinskIntentHelper.StartCategoryActivity(getContext(), GoMinskConstants.NIGHT_LIFE_CATEGORY_ID);
							}
		}

		private void changeMainImage(int imageId) {
			mainImage.setImageResource(imageId);
			mainImage.invalidate();
		}

		public void setDefaultState() {
			changeMainImage(R.drawable.main_menu_small_0);
			
		}
}
