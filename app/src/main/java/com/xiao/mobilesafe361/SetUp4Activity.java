package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetUp4Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up4);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的上一步方法
     */
    @Override
    public void pre_activity() {
        Intent intent = new Intent(this, SetUp3Activity.class);
        startActivity(intent);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的下一步方法
     */
    @Override
    public void next_activity() {
        Intent intent = new Intent(this, SetUp5Activity.class);
        startActivity(intent);
    }
}
