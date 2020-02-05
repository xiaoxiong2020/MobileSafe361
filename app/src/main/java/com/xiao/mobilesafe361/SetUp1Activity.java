package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetUp1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up1);
    }


    @Override
    public void pre_activity() {
        /*
        * 第一个界面没有上一步，不用具体实现*/
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的下一步方法
     */
    @Override
    public void next_activity() {
        Intent intent = new Intent(this, SetUp2Activity.class);
        startActivity(intent);
    }
}
