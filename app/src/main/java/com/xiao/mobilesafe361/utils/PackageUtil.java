package com.xiao.mobilesafe361.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/1/30 22:24
 */

public class PackageUtil {
    /**
     * 获取当前应用程序的版本号
     *@return
     * 2016-10-7 上午10:44:53
     */
    public static int getVersionCode(Context context){
        //1.包的管理者
        PackageManager pm = context.getPackageManager();
        try {
            //2.根据应用程序的报名获取应用程序清单文件中的信息
            //packageName : 包名
            //flags : 获取额外信息的标示，GET_ACTIVITIES：表示除了可以获取基本的信息之外还可以额外的将activity获取出来；0：表示获取基本的信息（包名，版本号，版本名称）
            //getPackageName() : 获取当前应用程序的包名
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 获取当前应用程序的版本名称
     *@return
     * 2016-10-7 上午10:44:53
     */
    public static String getVersionName(Context context){
        //1.包的管理者
        PackageManager pm = context.getPackageManager();
        try {
            //2.根据应用程序的报名获取应用程序清单文件中的信息
            //packageName : 报名
            //flags : 获取额外信息的标示，GET_ACTIVITIES：表示除了可以获取基本的信息之外还可以额外的将activity获取出来；0：表示获取基本的信息（包名，版本号，版本名称）
            //getPackageName() : 获取当前应用程序的报名
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
