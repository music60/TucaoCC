package com.studyjun.tucao.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hn on 2015/1/31.
 *
 * @TODO
 */
public class StringUtil {

    public static String inputStream2String(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        try {
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result=   baos.toString();
        try {
            baos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
