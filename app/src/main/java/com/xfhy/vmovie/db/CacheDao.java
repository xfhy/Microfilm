package com.xfhy.vmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.xfhy.vmovie.db.CacheDataOpenHelper.*;


/**
 * Created by xfhy on 2017/6/19.
 * 操作缓存数据库的dao,比较方便
 */

public class CacheDao {

    private Context context;
    private CacheDataOpenHelper mOpenHelper;
    private static CacheDao mDao;
    private SQLiteDatabase db;

    /**
     * 私有化构造方法
     *
     * @param context
     */
    private CacheDao(Context context) {
        this.context = context;
        mOpenHelper = new CacheDataOpenHelper(context);
        db = mOpenHelper.getWritableDatabase(); // 获得数据库 可写入的方式
    }

    /**
     * 获取实例
     * synchronized : 这里是同步的一个时间只能有一个线程得到执行
     *
     * @return
     */
    public synchronized static CacheDao getInstance(Context context) {
        if (mDao == null) {
            mDao = new CacheDao(context);
        }
        return mDao;
    }

    /**
     * 设置缓存
     *
     * @param type
     * @param json
     */
    public void setCache(int type, String json) {
        //有缓存则更新,没有缓存则插入缓存
        if (haveCache(type)) {
            updateCache(type, json);
        } else {
            insertCache(type, json);
        }
    }

    /**
     * 获取缓存
     *
     * @param type 根据类型获取缓存
     * @return
     */
    public String getCache(int type) {
        return queryCache(type);
    }

    /**
     * 判断是否有缓存
     *
     * @param type
     * @return
     */
    public boolean haveCache(int type) {
        Cursor cursor = db.query(CACHE_TABLE_NAME, new String[]{CACHE_TABLE_TYPE},
                CACHE_TABLE_TYPE + "=?", new String[]{type + ""}, null,
                null, null);
        if (cursor != null && cursor.moveToFirst()) {
            //如果有值 说明有缓存
            return true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    /**
     * 插入缓存
     *
     * @param type
     * @param json
     */
    public void insertCache(int type, String json) {
        ContentValues values = new ContentValues();
        values.put(CACHE_TABLE_TYPE, type);
        values.put(CACHE_TABLE_RESPONSE, json);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentTime = formatter.format(new Date());
        values.put(CACHE_TABLE_DATE, currentTime);
        db.insert(CACHE_TABLE_NAME, null, values);
    }

    /**
     * 删除缓存
     *
     * @param type
     */
    public void deleteCache(int type) {
        db.delete(CACHE_TABLE_NAME, CACHE_TABLE_TYPE + " = ?", new String[]{type + ""});
    }

    /**
     * 查询缓存
     *
     * @param type
     * @return
     */
    public String queryCache(int type) {
        Cursor cursor = db.query(CACHE_TABLE_NAME, new String[]{CACHE_TABLE_RESPONSE},
                CACHE_TABLE_TYPE + "=?",
                new String[]{type + ""}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String json = cursor.getString(cursor.getColumnIndex(CACHE_TABLE_RESPONSE));
            return json;
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    /**
     * 更新缓存
     *
     * @param type
     * @param json
     */
    public void updateCache(int type, String json) {
        ContentValues values = new ContentValues();
        values.put(CACHE_TABLE_TYPE, type);
        values.put(CACHE_TABLE_RESPONSE, json);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentTime = formatter.format(new Date());
        values.put(CACHE_TABLE_DATE, currentTime);

        db.update(CACHE_TABLE_NAME, values, CACHE_TABLE_TYPE + "=?", new String[]{type + ""});
    }

}
