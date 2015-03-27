package com.studyjun.tucao.manager;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

/**
 * Created by hn on 2015/3/20.
 * 设备管理
 *
 * @TODO
 */
public class DeviceManager {

    /**
     * 获取userAgent
     * @return
     */
    public static String getCurrentUserAgent() {

        StringBuffer buffer = new StringBuffer();
        // Add version
        final String version = Build.VERSION.RELEASE;
        if (version.length() > 0) {
            buffer.append(version);
        } else {
            // default to "1.0"
            buffer.append("1.0");
        }
        buffer.append("; ");
        final String language = "zh-CH";

        // add the model for the release build
        if ("REL".equals(Build.VERSION.CODENAME)) {
            final String model = Build.MODEL;
            if (model.length() > 0) {
                buffer.append("; ");
                buffer.append(model);
            }
        }
        final String id = Build.ID;
        if (id.length() > 0) {
            buffer.append(" Build/");
            buffer.append(id);
        }
        final String base = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
        return String.format(base, buffer);
    }

    public static String getVersion() {
        return Build.VERSION.RELEASE;
    }

}
