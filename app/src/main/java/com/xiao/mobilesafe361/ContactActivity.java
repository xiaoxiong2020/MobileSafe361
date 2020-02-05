package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void bind56(View view) {
        bindPhone("5556");
    }

    public void bind54(View view) {
        bindPhone("5554");
    }

    private void bindPhone(String s) {
        Intent data = new Intent();
        data.putExtra("number", s);
        //设置结果的方法，将结果返回给调用当前activity的activity
        //参数1：结果码
        //参数2：传递数据的意图
        setResult(Activity.RESULT_OK, data);
        finish();

    }
}
