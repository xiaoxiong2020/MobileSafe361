package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiao.mobilesafe361.view.SettingView;

public class CommonToolActivity extends AppCompatActivity implements View.OnClickListener {

    private SettingView mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tool);

        initView();
    }

    private void initView() {
        mAddress = (SettingView) findViewById(R.id.commontool_sv_address);

        //设置点击事件
        mAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commontool_sv_address:
                //跳转到号码归属地查询界面
                Intent intent = new Intent(CommonToolActivity.this,AddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
