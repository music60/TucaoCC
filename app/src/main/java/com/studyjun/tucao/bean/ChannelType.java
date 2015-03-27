package com.studyjun.tucao.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChannelType implements Serializable{
	
	private int tid;
	private String name;
	private List<ChannelTypeItem> items;
	
	
	public ChannelType(int tid, String name) {
		this.tid = tid;
		this.name =name;
		this.items = new ArrayList<ChannelTypeItem>();
	}
	
	public List<ChannelTypeItem> getItems() {
		return items;
	}
	public void setItems(List<ChannelTypeItem> items) {
		this.items = items;
	}
	public ChannelType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
