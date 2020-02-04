package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetUp3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up3);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/4 23:52
     * @Description:    下一步按钮点击事件
     */
    public void next(View view) {
        Intent intent = new Intent(this, SetUp4Activity.class);
        startActivity(intent);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/4 23:52
     * @Description:    上一步按钮点击事件
     */
    public void pre(View view) {
        Intent intent = new Intent(this, SetUp2Activity.class);
        startActivity(intent);
    }
}
