package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetUp1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up1);
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 23:52
      * @Description:    下一步按钮点击事件
     */
    public void next(View view) {
        Intent intent = new Intent(this, SetUp2Activity.class);
        startActivity(intent);
    }
}
