package com.studyjun.tucao.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.studyjun.tucao.bean.Video;

import java.util.ArrayList;
import java.util.List;


public class LocalVideoDB {
	
	private static String DB_NAME="localvideo";
	private static String TABLE="video";
	private static int VERSION =1;
	
	private SQLiteOpenHelper sqliteHelper;
	private SQLiteDatabase db;
	public SQLiteOpenHelper getSqliteHelper() {
		return sqliteHelper;
	}
	public void setSqliteHelper(SQLiteOpenHelper sqliteHelper) {
		this.sqliteHelper = sqliteHelper;
		db = sqliteHelper.getWritableDatabase();
	}
	public SQLiteDatabase getDb() {
		return db;
	}
	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}
	public LocalVideoDB(Context context) {
		super();
		sqliteHelper = new LocalVideoDBHelper(context, DB_NAME, null, VERSION);
		
	}
	
	public long insert(Video video,String hid){
        ContentValues values = new ContentValues();;
        values.put("vid", video.getVid());
        values.put("isDwoned", video.isDown()?1:0);
        values.put("hid",hid);
        return db.insert(TABLE, null, values);

    }

    public long insert(String vid ,short isDown,String hid){
        ContentValues values = new ContentValues();;
        values.put("vid", vid);
        values.put("isDwoned", isDown);
        values.put("hid",hid);
        return db.insert(TABLE, null, values);

    }

    public long update(String vid ,short isDown,String hid){
        ContentValues values = new ContentValues();;
        values.put("vid", vid);
        values.put("isDwoned", isDown);
        values.put("hid",hid);
        return db.update(TABLE, values, "vid=?", new String[]{vid});
    }

	public long setIsDowned(String vid,short down){
		ContentValues values = new ContentValues();;
		values.put("isDwoned",down);


		return db.update(TABLE, values, "vid=?", new String[]{vid});
	}
	
	public List<Video> getDownVideo(){
		List<Video> videos = new ArrayList<Video>();
		Video video =null;
		Cursor result = db.query(TABLE, null, "isDwoned=?", new String []{"1"}, null, null, "vid asc");
		if (result!=null) {
			while (result.moveToNext()) {
				video = new Video();
				video.setVid(result.getString(0));
                short down =result.getShort(1);
                if (down>0){
                    video.setDown(true);
                }else {
                    video.setDown(false);
                }

				videos.add(video);
			}
		}
		return videos;
	}
	
//	/**
//	 *
//	 * @param list
//	 * @return
//	 */
//	public void getDownArTheme(List<Video> list){
//
//        Video at =null;
//		Cursor result = db.query(TABLE, null, "isDwoned=?", new String []{"1"}, null, null, "themeId asc");
//		if (result!=null||list!=null) {
//			while (result.moveToNext()) {
//				for (int i = 0; i < list.size(); i++) {
//					if (result.getString(0).equals(list.get(i).getVid())) {
//						list.get(i).setDown(1);
//						if (result.getInt(2)<list.get(i).getVer()) {
//							list.get(i).setHasUpdate(true);
//						}
//						continue;
//					}
//				}
//			}
//		}
//	}

    /**
     //	 *根据hid或许下载的vid有哪些
     //	 * @param list
     //	 * @return
     //	 */
	public List<String> getDownVideoVid(String hid){
        List<String> list = new ArrayList<String>();
        Video at =null;
		Cursor result = db.query(TABLE, null, "isDwoned=? & hid=?", new String []{"1",hid}, null, null, "vid asc");
		if (result!=null) {
			while (result.moveToNext()) {

                list.add(result.getString(0));
			}
		}
        return list;
	}

	public void closeDB(){
		if (db!=null&&db.isOpen()) {
			db.close();
			db = null;
		}
	}
	
	public void openDB(){
		if (db==null) {
			db = sqliteHelper.getWritableDatabase();
		} else if (!db.isOpen()){
			db = sqliteHelper.getWritableDatabase();
		}
		 
	}
	
	/**
	 * 插入
	 * @param
	 */
	public void insertOrUpdate(String vid ,short isDown,String hid){
		if(insert(vid,isDown,hid)<1){
			update(vid,isDown,hid);
		}
	}

    public void delete(String vid){
        db.delete(TABLE,"vid=?",new String[]{vid});
    }
	
}
