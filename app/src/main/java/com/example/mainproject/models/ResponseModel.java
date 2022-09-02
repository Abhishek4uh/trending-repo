package com.example.mainproject.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseModel{

	@SerializedName("msg")
	private String msg;

	@SerializedName("count")
	private int count;

	@SerializedName("items")
	private List<ItemsItem> items;

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setItems(List<ItemsItem> items) {
		this.items = items;
	}

	public String getMsg(){
		return msg;
	}

	public int getCount(){
		return count;
	}

	public List<ItemsItem> getItems(){
		return items;
	}
}