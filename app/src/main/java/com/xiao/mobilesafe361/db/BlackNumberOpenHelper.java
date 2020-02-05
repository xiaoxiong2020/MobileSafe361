package com.xiao.mobilesafe361.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.xiao.mobilesafe361.interfacre.Constants;

/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/2/5 20:50
 */

public class BlackNumberOpenHelper extends SQLiteOpenHelper {
    public BlackNumberOpenHelper(@Nullable Context context) {
        //设置数据库的信息，创建数据库文件
        //name : 数据库的名称
        //factory : 游标工厂
        //version : 数据库的版本
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.DB_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
