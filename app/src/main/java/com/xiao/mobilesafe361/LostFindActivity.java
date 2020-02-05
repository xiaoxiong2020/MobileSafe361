package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LostFindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);

        initView();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/5 9:09
      * @Description:    重新进入设置跳转界面
     */
    private void initView() {
        TextView mAginEnter = findViewById(R.id.lostfind_tv_aginenter);
        //设置重新进入设置向导的点击事件
        mAginEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LostFindActivity.this, SetUp1Activity.class);
                startActivity(intent);
                //结束当前窗口
                finish();
            }
        });
    }
}
