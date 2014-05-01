package com.flaxtreme.gominsktestapp.entity;

public class WalkObjectGeoObject {

	private long id;
	
	private long walkObjectId;

	private long geoObjectId;
	
	private int sort;
	
	public WalkObjectGeoObject(long walkObjectId, long geoObjectId, int sort) {
		this.setWalkObjectId(walkObjectId);
		this.setGeoObjectId(geoObjectId);
		this.setSort(sort);
	}
	
	public WalkObjectGeoObject(long id, long walkObjectId, long geoObjectId, int sort){
		this(walkObjectId, geoObjectId, sort);
		this.setId(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWalkObjectId() {
		return walkObjectId;
	}

	public void setWalkObjectId(long walkObjectId) {
		this.walkObjectId = walkObjectId;
	}

	public long getGeoObjectId() {
		return geoObjectId;
	}

	public void setGeoObjectId(long geoObjectId) {
		this.geoObjectId = geoObjectId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
