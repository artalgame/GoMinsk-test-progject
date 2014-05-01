package com.flaxtreme.gominsktestapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.flaxtreme.gominsktestapp.db.CategoryDBAdapter;
import com.flaxtreme.gominsktestapp.db.GeoObjectCategoryDBAdapter;
import com.flaxtreme.gominsktestapp.db.GeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.db.WalkObjectDBAdapter;
import com.flaxtreme.gominsktestapp.db.WalkObjectGeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.entity.CategoryObject;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.entity.GeoObjectCategory;
import com.flaxtreme.gominsktestapp.entity.WalkObject;
import com.flaxtreme.gominsktestapp.entity.WalkObjectGeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IDBAdapter;
import com.flaxtreme.gominsktestapp.interfaces.IProgressCallback;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class APIHelper {

	//URLs
	public final static String ALL_CATEGORIES_URL = "http://minskguide.info/rest/category/all";
	public final static String ALL_GEO_OBJECTS_URL = "http://minskguide.info/rest/point/all";
	public final static String ALL_WALKS_URL = "http://minskguide.info/rest/route/all";
	public final static String DIFF_URL = "http://minskguide.info/rest/diff/%s";
	
	//Common keys
	public final static String DATA_KEY = "data";
	
	//Category keys
	public final static String CATEGORY_ID = "id";
	public final static String CATEGORY_NAME_RU = "name";
	public final static String CATEGORY_NAME_BY = "name_by";
	public final static String CATEGORY_NAME_EN = "name_en";
	
	public final static String CATEGORY_ICON = "icon";
	public final static String CATEGORY_COLOR_STYLE_STRING = "style";
	public final static String CATEGORY_PARENT_ID = "parent_id";
	public final static String CATEGORY_SORT = "sort";
	public final static String CATEGORY_POINTS = "points";

	//GeoObject keys
	public final static String GEO_OBJECT_ID="id";
	public final static String GEO_OBJECT_LATITUDE="lat";
	public final static String GEO_OBJECT_LONGITUDE="lon";
	
	public final static String GEO_OBJECT_NAME_RU="name";
	public final static String GEO_OBJECT_NAME_BY="name_by";
	public final static String GEO_OBJECT_NAME_EN="name_en";
	
	public final static String GEO_OBJECT_ADDRESS_RU="address";
	public final static String GEO_OBJECT_ADDRESS_BY="address_by";
	public final static String GEO_OBJECT_ADDRESS_EN="address_en";
	
	public final static String GEO_OBJECT_DESCRIPTION_RU="description";
	public final static String GEO_OBJECT_DESCRIPTION_BY="description_by";
	public final static String GEO_OBJECT_DESCRIPTION_EN="description_en";
	
	public final static String GEO_OBJECT_INFO="info";
	public final static String GEO_OBJECT_WORKTIME="worktime";
	
	public final static String GEO_OBJECT_MARKER_ID="marker_id";
	
	public final static String GEO_OBJECT_IMAGES="images";
	public final static String GEO_OBJECT_IMAGE_PATH="path";
	public final static String GEO_OBJECT_IMAGE_INFO="info";
	
	//Walks keys
	public final static String WALK_OBJECT_ID = "id";
	
	public final static String WALK_OBJECT_NAME_RU = "name";
	public final static String WALK_OBJECT_NAME_BY = "name_by";
	public final static String WALK_OBJECT_NAME_EN = "name_en";
	
	public final static String WALK_OBJECT_DESCRIPTION_RU = "description";
	public final static String WALK_OBJECT_DESCRIPTION_BY = "description_by";
	public final static String WALK_OBJECT_DESCRIPTION_EN = "description_en";
	
	public final static String WALK_OBJECT_COLOR_STRING = "style";
	public final static String WALK_OBJECT_SORT = "sort";
	public final static String WALK_OBJECT_POINTS = "points";
	
	public final static String WALK_OBJECT_TIME = "time";
	public final static String WALK_OBJECT_DISTANCE = "distance";
	
	
	private Context context;
	
	//Adapters
	IDBAdapter<CategoryObject> categoryDBAdapter =  null;
	
	IDBAdapter<GeoObjectCategory> geoObjectCategoryDBAdapter = null;
	
	IDBAdapter<GeoObject> geoObjectDBAdapter = null;
	
	IDBAdapter<WalkObject> walkDBAdapter = null;
	IDBAdapter<WalkObjectGeoObject> walkGeoObjectDBAdapter = null;
	
	public APIHelper(Context context){
		this.context = context;
	}
	
	public void closeDBAdapters(){
		if(geoObjectCategoryDBAdapter!=null)
			geoObjectCategoryDBAdapter.close();
		
		if(geoObjectDBAdapter!=null)
			geoObjectDBAdapter.close();
		
		if(categoryDBAdapter!=null)
			categoryDBAdapter.close();
		
		if(walkDBAdapter!=null){
			walkDBAdapter.close();
		}
		
		if(walkGeoObjectDBAdapter!=null){
			walkGeoObjectDBAdapter.close();
		}
	}
	
	public static String readStringFromUrl(String url) {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	        Log.e(APIHelper.class.toString(), "Failed to download string from URL");
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return builder.toString();
  }
	
	public void LoadAndSaveToDatabaseAllCategories(IProgressCallback progressCallback)
	{
		String categoriesJSON = readStringFromUrl(ALL_CATEGORIES_URL);
		
		JSONObject object;
		try {
			object = new JSONObject(categoriesJSON);
			
			JSONArray data = object.getJSONArray(DATA_KEY);
			
			if(categoryDBAdapter == null)
				categoryDBAdapter = new CategoryDBAdapter(context);
			
			for(int i=0; i<data.length(); i++){
				JSONObject categoryJson = data.getJSONObject(i);
				CategoryObject category= CategoryFromJSONObject(categoryJson);
				categoryDBAdapter.insertObject(category);
				progressCallback.addToProgress(1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public CategoryObject CategoryFromJSONObject(JSONObject categoryJSON){
			try {
				long id = categoryJSON.getLong(CATEGORY_ID);
				String name_ru = categoryJSON.getString(CATEGORY_NAME_RU);
				String name_by = categoryJSON.getString(CATEGORY_NAME_BY);
				String name_en = categoryJSON.getString(CATEGORY_NAME_EN);
				
				
				//TODO parse Icon
				String colorStyleString = categoryJSON.getString(CATEGORY_COLOR_STYLE_STRING);
				long parent_id = categoryJSON.getLong(CATEGORY_PARENT_ID);
				int sort = categoryJSON.getInt(CATEGORY_SORT);
				
				JSONArray geoObjects = categoryJSON.getJSONArray(CATEGORY_POINTS);
			    
				deleteAllGeoObjectCategoryObjects(id);
				
				for(int i=0; i<geoObjects.length(); i++){
					
					long geoObjectId = geoObjects.getLong(i);
					GeoObjectCategory newGeoObjectCategory = new GeoObjectCategory(geoObjectId, id, i);
					saveGeoObjectCategoryToDB(newGeoObjectCategory);
			    }
				
				return new CategoryObject(id, name_ru, name_by, name_en, null, colorStyleString, parent_id, sort);
				
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
				return null;
			}
	}
	
	private void deleteAllGeoObjectCategoryObjects(long categoryId){
		if(geoObjectCategoryDBAdapter == null)
		{
			geoObjectCategoryDBAdapter = new GeoObjectCategoryDBAdapter(context);
		}
		
		((GeoObjectCategoryDBAdapter)geoObjectCategoryDBAdapter).removeAllObjectsForCategoryId(categoryId);
		
	}
	
	private void saveGeoObjectCategoryToDB(
			GeoObjectCategory newGeoObjectCategory) {
		if(geoObjectCategoryDBAdapter == null)
		{
			geoObjectCategoryDBAdapter = new GeoObjectCategoryDBAdapter(context);
		}
		geoObjectCategoryDBAdapter.insertObject(newGeoObjectCategory);
		
	}

	public void loadAndSaveAllWalksObjects(IProgressCallback progressCallback){
		String walksJSON = readStringFromUrl(ALL_WALKS_URL);
		
		JSONObject object;
		try{
			object = new JSONObject(walksJSON);
			
			JSONArray data = object.getJSONArray(DATA_KEY);
			
			if(walkDBAdapter == null){
				walkDBAdapter = new WalkObjectDBAdapter(context);
				
				for(int i=0; i<data.length(); i++){
					JSONObject walkJson = data.getJSONObject(i);
					WalkObject walkObject = WalkObjectFromJSONObject(walkJson);
					walkDBAdapter.insertObject(walkObject);
					progressCallback.addToProgress(1);
				}
			}
		}catch(JSONException e) {
			e.printStackTrace();
		}
	}
	
	private WalkObject WalkObjectFromJSONObject(JSONObject walkJson) {
		try{
			long id =  walkJson.getLong(WALK_OBJECT_ID);
			
			String name_ru = walkJson.getString(WALK_OBJECT_NAME_RU);
			String name_by = walkJson.getString(WALK_OBJECT_NAME_BY);
			String name_en = walkJson.getString(WALK_OBJECT_NAME_EN);
			
			String description_ru = walkJson.getString(WALK_OBJECT_DESCRIPTION_RU);
			String description_by = walkJson.getString(WALK_OBJECT_DESCRIPTION_BY);
			String description_en = walkJson.getString(WALK_OBJECT_DESCRIPTION_EN);
			
			
			int time = 90; 
			try{
				time = walkJson.getInt(WALK_OBJECT_TIME);
			}catch(Exception ex){
				
			}
			
			int distance = 1500; 
			try{
				distance=walkJson.getInt(WALK_OBJECT_DISTANCE);
			}catch(Exception ex){
				
			}
			
			String colorStyleString = walkJson.getString(WALK_OBJECT_COLOR_STRING);
			int sort = walkJson.getInt(WALK_OBJECT_SORT);
			
			deleteAllWalkObjectGeoObjectObjectsWithWalkId(id);
			
			JSONArray geoObjects = walkJson.getJSONArray(WALK_OBJECT_POINTS);
			for(int i=0; i<geoObjects.length(); i++){
				long geoObjectId = geoObjects.getLong(i);
				WalkObjectGeoObject newWalkObjectGeoObject = new WalkObjectGeoObject(id, geoObjectId, sort);
				saveWalkObjectGeoObjectToDB(newWalkObjectGeoObject);
			}
			return new WalkObject(id, name_ru, name_by, name_en, description_ru, description_by, description_en, colorStyleString, sort, time, distance );
			
		}catch (JSONException e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	private void deleteAllWalkObjectGeoObjectObjectsWithWalkId(long walkId) {
		if(walkGeoObjectDBAdapter == null)
		{
			walkGeoObjectDBAdapter = new WalkObjectGeoObjectDBAdapter(context);
		}
		
		((WalkObjectGeoObjectDBAdapter)walkGeoObjectDBAdapter).deleteAllObjectsForWalkId(walkId);
		
	}

	private void saveWalkObjectGeoObjectToDB(
			WalkObjectGeoObject newWalkObjectGeoObject) {
		if(walkGeoObjectDBAdapter == null){
			walkGeoObjectDBAdapter = new WalkObjectGeoObjectDBAdapter(context);
		}
		walkGeoObjectDBAdapter.insertObject(newWalkObjectGeoObject);
	}

	public void LoadAndSaveAllGeoObjects(IProgressCallback progressCallback){
		String geoObjectsJSON = readStringFromUrl(ALL_GEO_OBJECTS_URL);
		JSONObject object;
		try {
			object = new JSONObject(geoObjectsJSON);
			JSONArray data = object.getJSONArray(DATA_KEY);
			
			if(geoObjectDBAdapter == null)
				geoObjectDBAdapter = new GeoObjectDBAdapter(context);
			
			for(int i=0; i<data.length(); i++){
				JSONObject geoObjectJson = data.getJSONObject(i);
				GeoObject geoObject = GeoObjectFromJSONObject(geoObjectJson);
				geoObjectDBAdapter.insertObject(geoObject);
				progressCallback.addToProgress(1);
			}
	}catch(Exception ex){
		ex.printStackTrace();
	}
}

	private GeoObject GeoObjectFromJSONObject(JSONObject geoObjectJson) {
		try {
			int id = geoObjectJson.getInt(GEO_OBJECT_ID);
		
			double longitude = geoObjectJson.getDouble(GEO_OBJECT_LONGITUDE);
			double latitude = geoObjectJson.getDouble(GEO_OBJECT_LATITUDE);
			
			String name_ru = geoObjectJson.getString(GEO_OBJECT_NAME_RU);
			String name_by = geoObjectJson.getString(GEO_OBJECT_NAME_BY);
			String name_en = geoObjectJson.getString(GEO_OBJECT_NAME_EN);
			
			String address_ru = geoObjectJson.getString(GEO_OBJECT_ADDRESS_RU);
			String address_by = geoObjectJson.getString(GEO_OBJECT_ADDRESS_BY);
			String address_en = geoObjectJson.getString(GEO_OBJECT_ADDRESS_EN);
			
			String description_ru = geoObjectJson.getString(GEO_OBJECT_DESCRIPTION_RU);
			String description_by = geoObjectJson.getString(GEO_OBJECT_DESCRIPTION_BY);
			String description_en = geoObjectJson.getString(GEO_OBJECT_DESCRIPTION_EN);
			
			String info = geoObjectJson.getString(GEO_OBJECT_INFO);
			String worktime = geoObjectJson.getString(GEO_OBJECT_WORKTIME);
			long markerId = geoObjectJson.getLong(GEO_OBJECT_MARKER_ID);
			
			JSONArray imagesJSON = geoObjectJson.getJSONArray(GEO_OBJECT_IMAGES);
			String imageInfo = null;
			Uri imageUri = null;
			
			if(imagesJSON.length()>0){
				JSONObject imageJson = imagesJSON.getJSONObject(0);
				
				imageInfo = imageJson.getString(GEO_OBJECT_IMAGE_INFO);
				String imageUrlPath = imageJson.getString(GEO_OBJECT_IMAGE_PATH);
				imageUri = LoadAndSaveImage(imageUrlPath);
			}
			
			return new GeoObject(
					id, 
					name_ru, 
					name_en, 
					name_by,
					address_ru,
					address_en,
					address_by,
					latitude, 
					longitude, 
					description_ru, 
					description_en, 
					description_by,
					info,
					worktime,
					markerId,
					imageInfo,
					imageUri);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	private Uri LoadAndSaveImage(String imageUrlPath) {
		//Load image
		
		return Uri.parse(imageUrlPath+ "&xs");
		/*try
		{   
		  URL url = new URL(imageUrlPath);
		  //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		  URL xsUrl = new URL(imageUrlPath + "&xs");
		  HttpURLConnection urlConnection = (HttpURLConnection) xsUrl.openConnection();
		  
		  try{
		 	urlConnection.setRequestMethod("GET");
		 	urlConnection.setDoOutput(true);                   
		 	urlConnection.connect();     
		  }catch(Exception ex){
			  Log.w("GoMinsk", "HttpUrl is connected");
		  }
		  
		  File SDCardRoot = context.getExternalFilesDir(null).getAbsoluteFile();
		  
		  String filePathFromUrl=url.getFile();
		  int lastSlash = filePathFromUrl.lastIndexOf("/", filePathFromUrl.length());
		  String directoryPath = filePathFromUrl.substring(0, lastSlash);
		  String fileName = filePathFromUrl.substring(lastSlash+1, filePathFromUrl.length());
		  
		  
		  File directory = new File(SDCardRoot.getAbsolutePath(), directoryPath);
		  directory.mkdirs();
		  
		  File file = new File(directory.getAbsolutePath(),fileName);
		  
		  if(!file.createNewFile())
		  {
		    Log.w("Save image", file.getAbsolutePath() + " exists.");
		  }
		  
		  FileOutputStream fileOutput = new FileOutputStream(file);
		  InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
		  int totalSize = urlConnection.getContentLength();
		  int downloadedSize = 0;   
		  byte[] buffer = new byte[1024];
		  int bufferLength = 0;
		  while ( (bufferLength = inputStream.read(buffer)) > 0 ) 
		  {                 
		    fileOutput.write(buffer, 0, bufferLength);                  
		    downloadedSize += bufferLength;                 
		 
		  }             
		  fileOutput.close();
		  return Uri.fromFile(file);    
		} 
		catch (MalformedURLException e) 
		{
		  e.printStackTrace();
		} 
		catch (IOException e)
		{

		  e.printStackTrace();
		}
		return null;*/
				
	}

	public void firstDBLoad (IProgressCallback progressCallback) {
		LoadAndSaveToDatabaseAllCategories(progressCallback);
    	LoadAndSaveAllGeoObjects(progressCallback);
    	loadAndSaveAllWalksObjects(progressCallback);
    	closeDBAdapters();
		
	}

	public void updateDB(IProgressCallback progressCallback,String dateUpdate) {
		String stringJSON = readStringFromUrl(String.format(DIFF_URL, dateUpdate));
		
		JSONObject object;
		try {
			object = new JSONObject(stringJSON);
			JSONObject data = object.getJSONObject(DATA_KEY);
			JSONArray categories = data.getJSONArray("categories");
			
			if(categories.length()!=0){
				updateCategories(categories,progressCallback);
			}
			
			JSONArray routes = data.getJSONArray("routes");
			if(routes.length()!=0){
				updateRoutes(routes,progressCallback);
			}
			
			JSONArray points = data.getJSONArray("points");
			if(points.length()!=0){
				updatePoints(points,progressCallback);
			}
		}catch(Exception ex){
			
		}		
	}

	private void updatePoints(JSONArray points, IProgressCallback progressCallback) {
		try {
			long[] updPointsId = new long[points.length()];
			
			for(int i=0; i< points.length(); i++){
				updPointsId[i] = points.getLong(i);
			}
		
		String pointsJSON = readStringFromUrl(ALL_GEO_OBJECTS_URL);
		JSONObject object;
			object = new JSONObject(pointsJSON);
			JSONArray data = object.getJSONArray(DATA_KEY);
			
			if(geoObjectDBAdapter == null)
				geoObjectDBAdapter = new GeoObjectDBAdapter(context);
			
			for(int i=0; i<data.length(); i++){
				JSONObject pointJSON = data.getJSONObject(i);
				long pointId = pointJSON.getLong(GEO_OBJECT_ID);
				boolean shouldUpdate = false;
			
				for(long id: updPointsId){
					if(id == pointId ){
						shouldUpdate = true;
						break;
					}
				}
				
				if(shouldUpdate){
					GeoObject point = GeoObjectFromJSONObject(pointJSON);
					geoObjectDBAdapter.removeObject(point.getId());
					geoObjectDBAdapter.insertObject(point);
					progressCallback.addToProgress(1);
				}		
			}
		}catch(Exception ex){
			
		}
	}

	private void updateRoutes(JSONArray updatingRoutesJSON, IProgressCallback progressCallback) {
		try {
			long[] updRoutesId = new long[updatingRoutesJSON.length()];
			
			for(int i=0; i< updatingRoutesJSON.length(); i++){
				updRoutesId[i] = updatingRoutesJSON.getLong(i);
			}
		
		String routesJSON = readStringFromUrl(ALL_WALKS_URL);
		JSONObject object;
			object = new JSONObject(routesJSON);
			JSONArray data = object.getJSONArray(DATA_KEY);
			
			if(walkDBAdapter == null)
				walkDBAdapter = new WalkObjectDBAdapter(context);
			
			for(int i=0; i<data.length(); i++){
				JSONObject walkJSON = data.getJSONObject(i);
				long currentId = walkJSON.getLong(WALK_OBJECT_ID);
				boolean shouldUpdate = false;
			
				for(long id: updRoutesId){
					if(id == currentId ){
						shouldUpdate = true;
						break;
					}
				}
				
				if(shouldUpdate){
					WalkObject walk = WalkObjectFromJSONObject(walkJSON);
					walkDBAdapter.removeObject(walk.getId());
					walkDBAdapter.insertObject(walk);
					progressCallback.addToProgress(1);
				}		
			}
		}catch(Exception ex){
			
		}
	}

	private void updateCategories(JSONArray updatingCategoriesJSON, IProgressCallback progressCallback) {
		
		try {
			
			long[] updCategoriesId = new long[updatingCategoriesJSON.length()];
			
			for(int i=0; i< updatingCategoriesJSON.length(); i++){
				updCategoriesId[i] = updatingCategoriesJSON.getLong(i);
			}
		
		String categoriesJSON = readStringFromUrl(ALL_CATEGORIES_URL);
		JSONObject object;
			object = new JSONObject(categoriesJSON);
			JSONArray data = object.getJSONArray(DATA_KEY);
			
			if(categoryDBAdapter == null)
				categoryDBAdapter = new CategoryDBAdapter(context);
			
			for(int i=0; i<data.length(); i++){
				JSONObject categoryJson = data.getJSONObject(i);
				long currentId = categoryJson.getLong(CATEGORY_ID);
				boolean shouldUpdate = false;
			
				for(long id: updCategoriesId){
					if(id == currentId ){
						shouldUpdate = true;
						break;
					}
				}
				
				if(shouldUpdate){
					CategoryObject category = CategoryFromJSONObject(categoryJson);
					categoryDBAdapter.removeObject(category.getId());
					categoryDBAdapter.insertObject(category);
					progressCallback.addToProgress(1);
				}		
			}
		}catch(Exception ex){
			
		}
	}

	public static Integer checkIfNeedUpdate(Context context, String dateUpdate) {
		if(NetworkConnectionDetector.isConnectionToInternet(context))
		{
			
			if(dateUpdate == null){
				dateUpdate = "1900-01-01";
			}
			String stringJSON = readStringFromUrl(String.format(DIFF_URL, dateUpdate));
			
			JSONObject object;
			try {
				object = new JSONObject(stringJSON);
				JSONObject data = object.getJSONObject(DATA_KEY);
				JSONArray categories = data.getJSONArray("categories");
				JSONArray routes = data.getJSONArray("routes");
				JSONArray points = data.getJSONArray("points");
				
			return	categories.length()+routes.length()+points.length();
		}catch(Exception ex){
			Log.e("APIHelper.checkIfNeedUpdate", "Could not upate " + ex.getMessage());
			return 0;
		}
		}else{
			return 0;
		}
	}
}
