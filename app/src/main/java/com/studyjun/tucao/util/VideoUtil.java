package com.studyjun.tucao.util;

import android.content.Context;
import android.os.Environment;

import com.studyjun.tucao.bean.VideoPlay;
import com.studyjun.tucao.bean.Segment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import tv.danmaku.ijk.media.player.pragma.Pragma;

/**
 * Created by hn on 2015/1/26.
 *
 * @TODO
 */
public class VideoUtil {


    /**
     * 解析地址
     * @param xmlcontent
     * @return
     */
    public static VideoPlay parseUrl(String xmlcontent) {
        VideoPlay videoPlay = new VideoPlay();
        List<Segment> urls = new ArrayList<Segment>();
        videoPlay.setDurls(urls);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlcontent.getBytes());
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document dom = builder.parse(inputStream);
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName("durl");
            NodeList timelength = root.getElementsByTagName("timelength");
            videoPlay.setTimelength(Integer.parseInt(timelength.item(0).getFirstChild().getNodeValue()));
            //查找所有durl节点
            for (int i = 0; i < items.getLength(); i++) {

                //得到第一个durl节点
                Element personNode = (Element) items.item(i);
//
                //获取durl节点下的所有子节点(标签之间的空白节点和name/age元素)
                NodeList childsNodes = personNode.getChildNodes();
                Segment  segment = new Segment();
                for (int j = 0; j < childsNodes.getLength(); j++) {


                    Node node = (Node) childsNodes.item(j);
                    //判断是否为元素类型
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element childNode = (Element) node;
                        //判断是否name元素
                        if ("length".equals(childNode.getNodeName())) {
                            segment.setLength(Integer.parseInt(childNode.getFirstChild().getNodeValue()));
                            //获取length元素下Text节点,然后从Text节点获取数据
                        } else if ("url".equals(childNode.getNodeName())) {
                            segment.setUrl(childNode.getFirstChild().getNodeValue());
                        } else if ("order".equals(childNode.getNodeName())) {
                            segment.setOrder(Integer.parseInt(childNode.getFirstChild().getNodeValue()));
                        }
                    }
                }
                urls.add(segment);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoPlay;
    }

    /**
     *
     * @param context
     * @param hid
     * @param position
     * @return 目录绝对路径
     */
    public static String createFile(Context context,String hid,int position){
      StringBuffer path = new StringBuffer();
      path.append(Environment.getExternalStorageDirectory()).append(File.separator).append("Android").append(File.separator).append("data").append(File.separator).append(context.getPackageName()).append(File.separator).append("download").append(File.separator).append(hid).append(File.separator).append(position);
      File file = new File(path.toString());
      if (!file.exists()){
          file.mkdirs();
      }
      return path.toString();
    }
}
