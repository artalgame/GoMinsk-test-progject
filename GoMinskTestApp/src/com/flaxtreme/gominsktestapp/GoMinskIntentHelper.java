package com.flaxtreme.gominsktestapp;

import android.content.Context;
import android.content.Intent;

import com.flaxtreme.gominsktestapp.activity.CategoryActivity;

public class GoMinskIntentHelper {
	public static void StartCategoryActivity(Context context, long categoryId){
		Intent categoryActivityIntent = new Intent(context, CategoryActivity.class);
		categoryActivityIntent.putExtra(GoMinskConstants.INTENT_CATEGORY_ID_KEY, categoryId);
		context.startActivity(categoryActivityIntent);
	}
}
