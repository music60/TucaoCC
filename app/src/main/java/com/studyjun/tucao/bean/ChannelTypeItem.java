package com.studyjun.tucao.bean;

import java.io.Serializable;

public class ChannelTypeItem implements Serializable{
	

	private int tid;
	private String name;
	
	public ChannelTypeItem(int tid, String name) {
		this.tid = tid;
		this.name =name;
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
