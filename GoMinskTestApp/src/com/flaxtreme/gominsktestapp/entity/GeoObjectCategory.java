package com.flaxtreme.gominsktestapp.entity;

public class GeoObjectCategory {
	
	private long id;
	
	private long geoObjectId;

	private long categoryId;
	
	private int sort;

	public GeoObjectCategory(long geoObjectId, long categoryId, int sort){
		this.geoObjectId = geoObjectId;
		this.categoryId = categoryId;
		this.sort = sort;
	}
	
	public GeoObjectCategory(long id, long geoObjectId, long categoryId, int sort){
		this(geoObjectId, categoryId, sort);
		this.id = id;
	}
	
	public long getGeoObjectId() {
		return geoObjectId;
	}

	public void setGeoObjectId(long geoObjectId) {
		this.geoObjectId = geoObjectId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
