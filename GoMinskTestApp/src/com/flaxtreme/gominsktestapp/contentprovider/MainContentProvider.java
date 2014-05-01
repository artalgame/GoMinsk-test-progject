package com.flaxtreme.gominsktestapp.contentprovider;


import com.flaxtreme.gominsktestapp.GoMinskDBAdapter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MainContentProvider extends ContentProvider {
	private static final String providerURIString ="content://com.flaxtreme.provider.gominsk/categories";
	public static final Uri CONTENT_URI = Uri.parse(providerURIString);
	
	public static final String AUTHORITIES = "com.flaxtreme.provider.gominsk";
	public static final String PATH_CATEGORY_ALLROWS = "categories";
	public static final String PATH_CATEGORY_SINGLE_ROW = "categories/#";
	public static final String PATH_WALKS_ALLROWS = "walks";
	
	private static final int CODE_ALLROWS = 1;
	private static final int CODE_SINGLE_ROW=2;
	
	public static final String MIME_TYPE_DIR = "com.flaxtreme.cursor.dir/providercontent";
	public static final String MIME_TYPE_ITEM = "com.flaxtreme.cursor.item/providercontent";
	
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITIES, PATH_CATEGORY_ALLROWS, CODE_ALLROWS);
		uriMatcher.addURI(AUTHORITIES, PATH_CATEGORY_SINGLE_ROW, CODE_SINGLE_ROW);
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)){
		case CODE_ALLROWS: return MIME_TYPE_DIR;
		case CODE_SINGLE_ROW: return MIME_TYPE_ITEM;
		default: throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		new GoMinskDBAdapter(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
