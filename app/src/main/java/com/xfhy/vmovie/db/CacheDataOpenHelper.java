package com.xfhy.vmovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xfhy on 2017/6/19.
 * 缓存数据库的帮助类
 */

public class CacheDataOpenHelper extends SQLiteOpenHelper {

    /**
     * 缓存数据库名称
     */
    public static final String CACHE_DATABASE_NAME = "cache_data";
    /**
     * 缓存数据库版本
     */
    public static final int CACHE_DATABASE_VERSION = 1;
    /**
     * 缓存数据表表名
     */
    public static final String CACHE_TABLE_NAME = "cache";
    /**
     * 缓存数据表 id
     */
    public static final String CACHE_TABLE_ID = "_id";
    /**
     * 缓存数据表 类型
     */
    public static final String CACHE_TABLE_TYPE = "type";
    /**
     * 缓存数据表 服务器返回的json数据
     */
    public static final String CACHE_TABLE_RESPONSE = "response";
    /**
     * 缓存数据表 缓存的时间
     */
    public static final String CACHE_TABLE_DATE = "date";

    public CacheDataOpenHelper(Context context) {
        this(context, CACHE_DATABASE_NAME, null, CACHE_DATABASE_VERSION);
    }

    public CacheDataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CACHE_TABLE_NAME + "(" +
                CACHE_TABLE_ID + " integer primary key autoincrement," +
                CACHE_TABLE_TYPE + " integer not null," +
                CACHE_TABLE_RESPONSE + " text," +
                CACHE_TABLE_DATE + " text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
