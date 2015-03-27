package com.studyjun.tucao.widget;

import android.os.Environment;


import java.io.File;
import java.io.FileWriter;

/**
 * Created by hn on 2015/3/17.
 * 日志
 * @TODO
 */
public class Log {

    public static String PATH= Environment.getExternalStorageDirectory()+ File.separator+"tucao"+File.separator+"log";
    public static String NAME= "data.log";
    public static String APP_NAME="tucao";

    public static void log(String tag,String text){
        android.util.Log.d(tag, text);
    }


    public static void i(String text){
        android.util.Log.i(APP_NAME, text);
    }

    public static void w(String text){
        android.util.Log.w(APP_NAME, text);
    }

    public static void e(String text){
        android.util.Log.e(APP_NAME, text);
    }

    public static void d(String tag,String text){
        android.util.Log.d(tag, text);
    }

    public static void i(String tag,String text){
        android.util.Log.i(tag, text);
    }

    public static void w(String tag,String text){
        android.util.Log.w(tag, text);
    }

    public static void e(String tag,String text){
        android.util.Log.e(tag, text);
    }

    public static void d(String text){
        android.util.Log.d(APP_NAME, text);
    }
    public static void v(String tag,String text){
        android.util.Log.v(tag, text);
    }

    public static void v(String text){
        android.util.Log.v(APP_NAME, text);
    }
    public static void logDisk(String tag,String text){
        android.util.Log.d(tag, text);
        try {
            File filePath  = new File(PATH);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File file = new File(filePath,NAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file,true);
            fw.flush();
            fw.write(text);
            fw.write("|@");
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
