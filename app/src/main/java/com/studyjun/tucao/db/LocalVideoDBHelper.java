package com.studyjun.tucao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @ClassName: ThemeDB
 * @Description: TODO 主题数据库，用于记录本地视频状态
 * @author studyjun
 * @date 2014-12-24 上午9:16:13
 *
 */
public class LocalVideoDBHelper extends SQLiteOpenHelper{

    private static String DB_NAME="video.db";
    private static int VERSION = 2;


    public LocalVideoDBHelper(Context context, String name, CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table video(vid varchar(20) not null PRIMARY KEY , isDwoned int(1),hid varchar(20));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
