package com.xiao.mobilesafe361.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.xiao.mobilesafe361.bean.BlackNumberInfo;
import com.xiao.mobilesafe361.db.BlackNumberOpenHelper;
import com.xiao.mobilesafe361.interfacre.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/2/5 21:23
 */

public class BlackNumberDao {

    private final BlackNumberOpenHelper blackNumberOpenHelper;

    public BlackNumberDao(Context context){
        blackNumberOpenHelper = new BlackNumberOpenHelper(context);
    }

    // 增删改查
    /**
     * 添加数据的操作
     *
     * @param blacknumber
     *            ： 号码
     * @param mode
     *            ： 拦截类型 2016-10-14 上午9:32:23
     */
    public boolean add(String blacknumber, int mode) {
        SQLiteDatabase database = blackNumberOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // key:数据库表中的字段名
        // values:添加的数据
        values.put(Constants.BLACKNUMBER, blacknumber);
        values.put(Constants.MODE, mode);
        // 参数1：表名
        // 参数2：Sqlite数据库不能直接添加null操作，如果添加数据是null，sqlite数据库会在添加数据相应的列中设置为null
        // 参数3：添加的数据
        long insert = database.insert(Constants.TABLE_NAME, null,
                values);

        // 判断是否添加成功的操作
        return insert != -1;
    }

    /**
     * 根据号码删除表中对应的记录
     *
     * @param blackNumber
     *            2016-10-14 上午9:41:32
     */
    public boolean delete(String blackNumber) {
        SQLiteDatabase database = blackNumberOpenHelper.getWritableDatabase();
        // whereClause : 查询条件
        // whereArgs : 查询条件的参数（具体的值）
        int delete = database.delete(Constants.TABLE_NAME,
                Constants.BLACKNUMBER + "=?",
                new String[] { blackNumber });

        return delete != 0;
    }

    /**
     * 根据号码更新拦截类型
     *
     * @param blacknumber
     *            ： 号码
     * @param mode
     *            ： 更新的拦截类型 2016-10-14 上午9:46:08
     */
    public boolean update(String blacknumber, int mode) {
        SQLiteDatabase database = blackNumberOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.MODE, mode);
        // values : 要更新的数据
        // whereClause : 查询条件
        // whereArgs : 查询条件的参数（具体的值）
        int update = database.update(Constants.TABLE_NAME, values,
                Constants.BLACKNUMBER + "=?",
                new String[] { blacknumber });
        // 如果update是0标示更新失败
        return update != 0;
    }

    // 查询单个数据
    /**
     * 根据黑名单号码查询拦截类型
     *
     * @param blackNumber
     * @return 2016-10-14 上午9:52:14
     */
    public int queryMode(String blackNumber) {

        int mode = -1;//设置初始的默认值

        SQLiteDatabase database = blackNumberOpenHelper.getReadableDatabase();
        // columns :设置查询哪一列的数据
        // selection : 查询条件
        // selectionArgs : 查询条件的参数
        // groupBy : 分组
        // having : 去重
        // orderBy : 排序
        Cursor cursor = database.query(Constants.TABLE_NAME,
                new String[] { Constants.MODE },
                Constants.BLACKNUMBER + "=?",
                new String[] { blackNumber }, null, null, null);
        //因为是根据黑名单号码查询拦截类型，一个黑名单号码对应的是一个拦截类型
        if(cursor.moveToNext()){
            mode = cursor.getInt(0);
        }
        //关闭
        cursor.close();
        database.close();
        return mode;
    }

    /**
     * 查询全部数据的操作
     *
     * 2016-10-14 上午10:21:12
     */
    public List<BlackNumberInfo> queryAll() {
        //模拟系统查询巨量数据的时间情况。
        //SystemClock.sleep(2000);

        List<BlackNumberInfo> list = new ArrayList<BlackNumberInfo>();

        SQLiteDatabase database = blackNumberOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(Constants.TABLE_NAME, new String[] {
                        Constants.BLACKNUMBER, Constants.MODE },
                null, null, null, null, "_id desc");
        while(cursor.moveToNext()){
            String blacknumber = cursor.getString(0);
            int mode = cursor.getInt(1);

            //将黑名单号码和拦截类型保存到bean类中，方便后面使用
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo(blacknumber, mode);

            //将bean类存放到集合中，方便listview显示
            list.add(blackNumberInfo);
        }
        //关闭数据库
        cursor.close();
        database.close();
        return list;
    }

}
