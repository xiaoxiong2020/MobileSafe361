package com.xiao.mobilesafe361.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Description:保存当前app的属性设置
 * @Author: TimXiao
 * @CreateDate: 2020/2/4 11:15
 */

public class SharedPreferencesUtil {

    private static SharedPreferences sp_config;

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 11:37
      * @Description:    保存boolean类型的属性值
     */
    public static void saveBoolean(Context context, String key, boolean value){
        if(sp_config == null){
            /*1.name:SharedPreferences（xml）文件的名字
             * 2.mode:该文件的权限模式*/
            sp_config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        /*
        * 使用sp文件保存属性值
        * 1. key，保存熟悉的名字
        * 2. value，保存属性的值
        * */
        sp_config.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue){
        if(sp_config == null){
            /*1.name:SharedPreferences（xml）文件的名字
             * 2.mode:该文件的权限模式*/
            sp_config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp_config.getBoolean(key, defValue);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/4 11:37
     * @Description:    保存boolean类型的属性值
     */
    public static void saveString(Context context, String key, String value){
        if(sp_config == null){
            /*1.name:SharedPreferences（xml）文件的名字
             * 2.mode:该文件的权限模式*/
            sp_config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        /*
         * 使用sp文件保存属性值
         * 1. key，保存熟悉的名字
         * 2. value，保存属性的值
         * */
        sp_config.edit().putString(key,value).commit();
    }

    public static String getString(Context context, String key, String defValue){
        if(sp_config == null){
            /*1.name:SharedPreferences（xml）文件的名字
             * 2.mode:该文件的权限模式*/
            sp_config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp_config.getString(key, defValue);
    }
}
