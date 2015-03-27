package com.studyjun.tucao.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by hn on 2015/1/21.
 * ui辅助工具类
 *
 * @TODO
 */
public class UI {

    public static void toast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
    public static void toastLongTime(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
    public static void gotoActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
