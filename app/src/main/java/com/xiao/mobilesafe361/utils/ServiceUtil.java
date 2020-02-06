package com.xiao.mobilesafe361.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/2/6 14:06
 */

public class ServiceUtil {
    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 14:07
      * @Description:    动态获取服务是否开启的操作
     */
    public static boolean isRunningService(Context context, String className){
        //1.进程的管理者（活动的管理者）
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //2.获取进程中正在运行的服务的集合
        //参数：获取的服务的个数的上限，没有1000,有多少返回多少，超过1000，只返回1000个
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = manager.getRunningServices(1000);
        //3.遍历正在运行的服务的集合中是否有我们的服务
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos){
            //获取正在运行的服务的组件标示
            ComponentName service = runningServiceInfo.service;
            //获取正在运行的服务的全类名
            String clsName = service.getClassName();
            //判断正在运行的服务的全类名跟我们的服务的全类名是否一致
            if(className.equals(clsName)){
                return true;
            }
        }
        return false;
    }
}
