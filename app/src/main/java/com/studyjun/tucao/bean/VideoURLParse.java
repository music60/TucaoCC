package com.studyjun.tucao.bean;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by hn on 2015/1/24.
 *
 * @TODO
 */
public class VideoURLParse {

    public static String TAG="VideoURLParse";

    public VideoPlay videoPlay;

    public  VideoPlay parse(InputStream inputStream){
        VideoPlay videoPlay = new VideoPlay();
        List<Segment> urls = new ArrayList<Segment>();
        videoPlay.setDurls(urls);
        DocumentBuilder documentBuilder;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {

            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();
            NodeList length_Node = element.getElementsByTagName("timelength");
            videoPlay.setTimelength(Integer.parseInt(length_Node.item(0).getFirstChild().getNodeValue()));
            NodeList durl_Node = element.getElementsByTagName("durl");

            for (int i=0;i<durl_Node.getLength();i++){
                Segment url = new Segment();
                Element ele = (Element) durl_Node.item(i);
                String length =ele.getElementsByTagName("length").item(0).getFirstChild().getNodeValue();
                String order =ele.getElementsByTagName("order").item(0).getFirstChild().getNodeValue();
                String strurls =ele.getElementsByTagName("url").item(0).getFirstChild().getNodeValue();
//                url.setLength(Integer.parseInt(ele.getAttributeNode("length").getNodeValue()));
//                url.setOrder(Integer.parseInt(ele.getAttributeNode("order").getNodeValue()));
                url.setUrl(length+"="+order+"="+strurls);
                urls.add(url);
            }

        } catch (ParserConfigurationException e) {
            Log.e(TAG, "create DocumentBuilder error");
            e.printStackTrace();
        } catch (SAXException e) {
            Log.e(TAG, "parse video url error with SAXException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "parse video url error with IOException");
            e.printStackTrace();
        } catch (Exception e){
            Log.e(TAG, "parse video url error");
            e.printStackTrace();
        }


        return videoPlay;
    }

    public VideoPlay parse(String content){
        InputStream   inputStream   =   new ByteArrayInputStream(content.getBytes());
        return parse(inputStream);
    }
}
