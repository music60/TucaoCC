package com.studyjun.tucao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hn on 2015/1/24.
 *
 * @TODO
 */
public class VideoPlay implements Serializable{



    public int timelength;
    public String src;
    private List<Segment> durls;

    public void setTimelength(int timelength) {
        this.timelength = timelength;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setDurls(List<Segment> durls) {
        this.durls = durls;
    }

    public int getTimelength() {
        return timelength;
    }

    public String getSrc() {
        return src;
    }

    public List<Segment> getDurls() {
        return durls;
    }
}
