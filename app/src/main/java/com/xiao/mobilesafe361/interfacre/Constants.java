package com.xiao.mobilesafe361.interfacre;

/**
 * @Description:存放常量的接口
 * @Author: TimXiao
 * @CreateDate: 2020/2/4 16:05
 */

public interface Constants {
    //自动更新功能在SharedPreferences文件中的属性名称
    public static final String ISPUDATE = "isupdate";
    //手机防盗密码属性名称在SharedPreferences文件中的属性名称
    public static final String SJFDPWD = "sjfdpwd";
    //手机防盗设置完成标识属性在SharedPreferences文件中的属性名称
    public static final String SJFDSZWC = "sjfdszwc";
    public static final String SIM = "sim";


    /** 数据库的名称 **/
    public static final String DB_NAME = "blacknumber.db";

    /** 数据库的版本 **/
    public static final int DB_VERSION = 1;

    // 为了方便后期修改表的名称和字段的名称，把表名和字段名也抽取出来
    /** 表名 **/
    public static final String TABLE_NAME = "info";

    /** _id字段的名称 **/
    public static final String ID = "_id";
    /** 号码的字段的名称 **/
    public static final String BLACKNUMBER = "blacknumber";
    /** 类型的字段的名称 **/
    public static final String MODE = "mode";

    /** 创建表的sql语句 **/
    public static final String DB_SQL = "create table " + TABLE_NAME + "(" + ID
            + " integer primary key autoincrement," + BLACKNUMBER
            + " varchar(20)," + MODE + " varchar(2))";
}
