package com.studyjun.tucao.bean;

import java.io.Serializable;

/**
 * @author studyjun
 * @ClassName: Segment
 * @Description: TODO 视频段
 * @date 2015-1-18 下午10:36:14
 * @blog http://www.studyjun.com
 */
public class Video implements Serializable{

    private String type; //视频类型
    private String vid; //视频id
    private String title; //视频标题

    private boolean isDown; //是否下载

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean isDown) {
        this.isDown = isDown;
    }
}