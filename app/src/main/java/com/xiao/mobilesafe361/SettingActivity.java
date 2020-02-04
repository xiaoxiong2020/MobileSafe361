package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.xiao.mobilesafe361.utils.SharedPreferencesUtil;
import com.xiao.mobilesafe361.view.SettingView;

import static com.xiao.mobilesafe361.interfacre.Constants.ISPUDATE;

public class SettingActivity extends AppCompatActivity {

    private SettingView mUpdate;

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

        //设置条目的点击事件
        update();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 0:08
      * @Description:    设置条目的点击事件
     */
    private void update() {
        //再次进入界面的时候，获取保存的开关状态，根据保存的开关状态，设置界面开关操作
        boolean b = SharedPreferencesUtil.getBoolean(getApplicationContext(), ISPUDATE, true);
        mUpdate.setToggleOnOrOff(b);
        //给条目添加点击按钮监听,匿名内部类的方式实现
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击条目后更新开关图片状态，优化第一步
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
}
