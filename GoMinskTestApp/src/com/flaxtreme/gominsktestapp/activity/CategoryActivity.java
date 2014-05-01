package com.flaxtreme.gominsktestapp.activity;

import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.db.CategoryDBAdapter;
import com.flaxtreme.gominsktestapp.entity.CategoryObject;
import com.flaxtreme.gominsktestapp.fragment.CategoryFragment;
import com.flaxtreme.gominsktestapp.interfaces.IDBAdapter;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class CategoryActivity extends BaseBackActivity {

	public static CategoryObject categoryObject;
	public static long categoryId;
	public static Fragment categoryFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		categoryId = getIntent().getLongExtra(GoMinskConstants.INTENT_CATEGORY_ID_KEY, 0);
		
		IDBAdapter<CategoryObject> dbAdapter = new CategoryDBAdapter(this);
		CategoryObject category = dbAdapter.getObjectItem(categoryId);
		
		Bundle fragmentBundle = new Bundle();
		fragmentBundle.putParcelable(GoMinskConstants.BUNDLE_CATEGORY_PARCELABLE, category);
		
		categoryFragment = new CategoryFragment();
		categoryFragment.setArguments(fragmentBundle);		
		setContentFragment(categoryFragment);
		
		setWindowTitle(category.getTitle());
		setActionBarBackgroundDrawable(new ColorDrawable(category.getColor()));
		//new LoadCategoryToCategoryActivityAsyncTask(this).execute();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	};
	
	@Override
	protected void onRestart() {
		super.onRestart();
	};
	
	@Override
	protected void onResume() {
		super.onResume();
	};
	
	public static class LoadCategoryToCategoryActivityAsyncTask extends AsyncTask<Void, Void, CategoryObject>{

		public BaseActivity context;
		public LoadCategoryToCategoryActivityAsyncTask(BaseActivity context){
			this.context = context;
		}
		
		@Override
		protected CategoryObject doInBackground(Void...voids) {
			IDBAdapter<CategoryObject> dbAdapter = new CategoryDBAdapter(context);
			CategoryObject category = dbAdapter.getObjectItem(categoryId);
			return category;
		}
		
		@Override
		 protected void onPostExecute(CategoryObject result) {
			
			if(result==null){
				Toast.makeText(context.getApplicationContext(),context.getString(R.string.update_database_toast), Toast.LENGTH_LONG).show();
				context.finish();
				return;
			}
			
			Bundle fragmentBundle = new Bundle();
			fragmentBundle.putParcelable(GoMinskConstants.BUNDLE_CATEGORY_PARCELABLE, result);
			
			categoryFragment = new CategoryFragment();
			categoryFragment.setArguments(fragmentBundle);
			
			context.setWindowTitle(result.getTitle());
			context.setActionBarBackgroundDrawable(new ColorDrawable(result.getColor()));
			
			context.setContentFragment(categoryFragment);
	     }
		
	}
}
