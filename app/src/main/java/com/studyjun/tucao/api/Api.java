package com.studyjun.tucao.api;

/**
 * Created by studyjun on 2015/1/19.
 * api,接口
 * @TODO
 */
public class Api {

    public static String APP_KEY="25tids8f1ew1821ed";

    public static String TYPE="json";

    
    /**
     * 获得视频相关信息:
     * hid	视频id<br/>
     */
    public static String VIDEO_INFO="http://www.tucao.cc/api_v2/view.php";

    /**
     * 获得分区内的视频列表
     * tid		分类id<br/>
     * page  	分页选择（默认第一页）<br/>
     * pagesize	返回记录数 （默认10）<br/>
     * order  		排序内容(默认date(发布时间), 可选mukio(弹幕数)/views（播放数）)<br/>
     *
     */
    public static String REGION_VIDEO_LIST="http://www.tucao.cc/api_v2/list.php?type=json&apikey=25tids8f1ew1821ed&tid=%S&page=%S&pagesize=%S";


    /**
     * 获取排行列表：
     * tid		分类id  不支持子分类<br/>
     * date		0（每日）1（本周）2（本月）<br/>
     */
    public static String RANK_LIST="http://www.tucao.cc/api_v2/rank.php?type=json&tid=%s&date=%s&apikey="+APP_KEY;

    /**
     *搜索：
     * tid		分类id（可选）<br/>
     * page  		分页选择（默认第一页）<br/>
     *pagesize	返回记录数 （默认10）<br/>
     *order  		排序内容(默认date(发布时间), 可选mukio(弹幕数)/views（播放数）)<br/>
     * q		关键词<br/>
     */
    public static String SEARCH="http://www.tucao.cc/api_v2/search.php?q=%s&page=%s&pagesize=%s&apikey="+APP_KEY;


    /**
     * 弹幕信息获得地址<xml>:
     *
     *
     */
    public static String BARRAGE="http://www.tucao.cc/index.php?m=mukio&c=index&a=init&playerID=11-%s-1-%s";


    /**
     * 视频地址<xml>:
     *
     *
     */
    public static String PLAY_URL_DEFAULT ="http://www.tucao.cc/api_v2/playurl.php?type=%s&vid=%s&apikey="+APP_KEY;


    /**
     * 视频地址<xml>:
     *
     *
     */
    public static String PLAY_URL_OTHER="http://www.tucao.cc/api/playurl.php?type=%s&vid=%s&apikey="+APP_KEY;
}
