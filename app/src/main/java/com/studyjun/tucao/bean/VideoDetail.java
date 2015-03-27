package com.studyjun.tucao.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ClassName: VideoDetail
 * @Description: TODO 视频详情
 * @author studyjun
 * @date 2015-1-18 下午10:31:22
 * @blog http://www.studyjun.com
 */
public class VideoDetail implements Serializable{

    private String hid; //视频id
    private String typeid; //分类id
    private long create; //发布时间
    private int mukio; //弹幕数
    private String typename; //分类名
    private String title; //视频名称
    private String play; //播放数
    private String description; //介绍
    private String keywords; //关键词
    private String thumb; //缩略图
    private String user; //发布人
    private String userid; //发布人id
    private int part; //分P数量
    private List<Video> video; //分P视频信息

    public String getHid() {
        return hid;
    }
    public void setHid(String hid) {
        this.hid = hid;
    }
    public String getTypeid() {
        return typeid;
    }
    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }
    public long getCreate() {
        return create;
    }
    public void setCreate(long create) {
        this.create = create;
    }
    public int getMukio() {
        return mukio;
    }
    public void setMukio(int mukio) {
        this.mukio = mukio;
    }
    public String getTypename() {
        return typename;
    }
    public void setTypename(String typename) {
        this.typename = typename;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPlay() {
        return play;
    }
    public void setPlay(String play) {
        this.play = play;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getThumb() {
        return thumb;
    }
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public int getPart() {
        return part;
    }
    public void setPart(int part) {
        this.part = part;
    }
    public List<Video> getVideo() {
        return video;
    }
    public void setVideo(List<Video> video) {
        this.video = video;
    }



}