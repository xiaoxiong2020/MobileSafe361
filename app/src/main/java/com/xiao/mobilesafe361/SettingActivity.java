package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiao.mobilesafe361.service.BlackNumberService;
import com.xiao.mobilesafe361.utils.ServiceUtil;
import com.xiao.mobilesafe361.utils.SharedPreferencesUtil;
import com.xiao.mobilesafe361.view.SettingView;

import static com.xiao.mobilesafe361.interfacre.Constants.ISPUDATE;

public class SettingActivity extends AppCompatActivity {

    private SettingView mUpdate;
    private SettingView mBlackNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //初始化控件
        initView();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 0:05
      * @Description:    初始化控件
     */
    private void initView() {
        mUpdate = findViewById(R.id.setting_sv_update);
        mBlackNumber = findViewById(R.id.setting_sv_blacknumber);

        //设置条目的点击事件
        //自动更新点击事件
        update();

        //骚扰拦截击事件
        blacknumber();
    }



    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 0:08
      * @Description:    设置条目的点击事件-自动更新
     */
    private void update() {
        //再次进入界面的时候，获取保存的开关状态，根据保存的开关状态，设置界面开关操作
        boolean b = SharedPreferencesUtil.getBoolean(getApplicationContext(), ISPUDATE, true);
        mUpdate.setToggleOnOrOff(b);
        //给条目添加点击按钮监听,匿名内部类的方式实现
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击条目后更改开关状态，优化第一步
                mUpdate.toggle();
                //开启关闭成功，保存开关状态
                SharedPreferencesUtil.saveBoolean(getApplicationContext(), ISPUDATE,  mUpdate.getToggle());
                /*目的：获取开关的当前状态
                * 问题：点击事件是在当前activity中实现的，但是开关状态是在自定义控件中的
                * 解决办法，在自定义控件中新增一个获得开关状态的方法，暴漏给当前activity**/
                /*
                boolean isToggleOn = mUpdate.getToggle();
                if (isToggleOn){
                    *//*
                     * 目的：通过点击事件控制开启和关闭图标
                     * 问题：点击事件是在当前activity中实现的，但是修改的图片是在自定义控件中的
                     * 解决办法，在自定义控件中新增一个修改图标的方法，暴漏给当前activity*//*
                    mUpdate.setToggleOnOrOff(false);
                }else {
                    mUpdate.setToggleOnOrOff(true);

                }*/

            }
        });
    }


    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 14:00
      * @Description:    骚扰拦截点击事件
     */
    private void blacknumber() {
        //添加条目的点击事件
        mBlackNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.开启/关闭服务
                // 关闭 -> 点击开启服务
                // 开启 -> 点击关闭服务
                // 需要知道服务是否开启
                // 不能SharedPreferences的原因：因为在系统的设置操作可以，可以手动的停止服务，手动停止服务因为是在系统的设置界面中的，所以不好更改SharedPreferences保存的值
                // 动态的获取服务是否开启BlackNumberService
                boolean blacknumberServiceIsRunning = ServiceUtil.isRunningService(SettingActivity.this, "com.xiao.mobilesafe361.service.BlackNumberService");
                Intent intent = new Intent(SettingActivity.this, BlackNumberService.class);
                if(blacknumberServiceIsRunning){
                    //如果是开启的，就关闭服务
                    stopService(intent);

                }else {
                    //否则开启服务
                    startService(intent);
                }
                // 2. 更新图片
                mBlackNumber.toggle();
                //3. 回显图片
            }
        });

    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 14:26
      * @Description:    根据服务是否开启，显示条目按钮状态
     * 原因：回显操作写在blacknumber方法中的，而blacknumber方法是在oncreate方法中调用的，最小化在打开界面是不会调用oncreate方法，所以不会调用回显操作
     * 	解决：将回显操作写到onstart方法中
     */
    @Override
    protected void onStart() {
        // 2.再次进入的时候，判断服务是否开启，设置开关状态
        boolean b = ServiceUtil.isRunningService(this,
                "com.xiao.mobilesafe361.service.BlackNumberService");
        mBlackNumber.setToggleOnOrOff(b);
        super.onStart();
    }
}
