package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiao.mobilesafe361.interfacre.Constants;
import com.xiao.mobilesafe361.utils.SharedPreferencesUtil;

import static com.xiao.mobilesafe361.interfacre.Constants.SJFDSZWC;

public class SetUp5Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up5);
    }



    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的上一步方法
     */
    @Override
    public void pre_activity() {
        //保存设置完成的状态，后续再次进入防盗界面跳过向导前面5步，直接进入完成界面
        SharedPreferencesUtil.saveBoolean(getApplicationContext(), Constants.SJFDSZWC, true);


        Intent intent = new Intent(this, SetUp4Activity.class);
        startActivity(intent);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的下一步方法
     */
    @Override
    public void next_activity() {
        Intent intent = new Intent(this, LostFindActivity.class);
        startActivity(intent);
    }
}
