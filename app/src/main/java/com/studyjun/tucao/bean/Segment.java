package com.studyjun.tucao.bean;

/**
 * Created by hn on 2015/1/24.
 *
 * @TODO
 */
public class Segment {
//    <order>0</order>
//    <length>381000</length>
//    <url>
//    <![CDATA[
//    http://f.youku.com/player/getFlvPath/sid/142207176513959146528_00/st/mp4/fileid/030008020054AF7A13FD8305017057602256BD-B2B7-77BB-CB2A-9B83A1203518?K=a4da5a83ceea4c4f24121523
//            ]]>
//    </url>
//    </durl>

    public int order;
    public int length;
    public String url;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
