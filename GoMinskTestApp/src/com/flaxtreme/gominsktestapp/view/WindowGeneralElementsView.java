package com.flaxtreme.gominsktestapp.view;

import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.AppInfoActivity;
import com.flaxtreme.gominsktestapp.activity.MinskInfoActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class WindowGeneralElementsView extends FrameLayout {

	private ImageButton drawerButton;
	private ListView drawer;
	private TextView windowTitle;
	private FrameLayout actionBarCustomLayout;

	public WindowGeneralElementsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode()){
        	initView(context);
        }
    }

    public WindowGeneralElementsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WindowGeneralElementsView(Context context) {
        super(context);
        initView(context);
    }

    protected void initView(Context context) {
    	inflateLayout(context, R.layout.window_general_elments_layout);
    }
    
    protected void inflateLayout(Context context, int windowGeneralElmentsLayout) {
    	View view = LayoutInflater.from(context).inflate(windowGeneralElmentsLayout, null);
        addView(view);
        initializeElements();
	}

	private void initializeElements() {
    	initializeDrawer();
 
 		windowTitle = (TextView)findViewById(R.id.windowTitle);
 		windowTitle.setText(R.string.app_name);
 		
 		actionBarCustomLayout = (FrameLayout)findViewById(R.id.actionBarCustomLayout);		
	}

	private void initializeDrawerButton() {
    	 drawerButton = (ImageButton)findViewById(R.id.drawerButton);
    	 
    	 drawerButton.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				if(drawer.getVisibility() == View.INVISIBLE ){
 					drawer.setVisibility(View.VISIBLE);
 				}else{
 					drawer.setVisibility(View.INVISIBLE);
 				}
 			}
 		});
	}

	private void initializeDrawer() {
		initializeDrawerButton();
    	
		drawer = (ListView)findViewById(R.id.drawer);
    	setDrawerListViewItems();
    	drawer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch(position){
					case 0:
						getContext().startActivity(new Intent(getContext(), AppInfoActivity.class));
						break;
					case 1:{
						getContext().startActivity(new Intent(getContext(), MinskInfoActivity.class));
						break;
					}
					default:
						break;
				}
			}
    	});
	}

	private void setDrawerListViewItems() {
		String[] settingsTitles = getResources().getStringArray(R.array.drawer_points);

        if(drawer!=null){
        drawer.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, settingsTitles));
        }
		
	}

	public void setTitle(CharSequence title) {
		if(windowTitle!=null){
			windowTitle.setText(title);
		}
		
	}

	public CharSequence getTitle() {
		if(windowTitle!=null){
			return windowTitle.getText();
		}
		return null;
	}
	
	

	@SuppressLint("NewApi")
	public void setBackgroundDrawable(Drawable background){
		if(actionBarCustomLayout != null){
			int currentapiVersion = android.os.Build.VERSION.SDK_INT;
			
			if(currentapiVersion < Build.VERSION_CODES.JELLY_BEAN){
				actionBarCustomLayout.setBackgroundDrawable(background);	
			}else{
				actionBarCustomLayout.setBackground(background);
			}
		}
	}

	public void setDefaultDrawerState() {
		if(drawer!=null){
			drawer.setVisibility(View.INVISIBLE);
		}
		
	}
}
