package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SetUp3Activity extends BaseActivity {

    private Button btn_safeNo;
    private TextView et_safeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up3);

        initView();
    }

    private void initView() {
        btn_safeNo = findViewById(R.id.up3_btn_safeNo);
        et_safeNo = findViewById(R.id.up3_et_safeNo);
        btn_safeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //接收ContactActivity传递过来的数据
        if(resultCode == Activity.RESULT_OK){
            if(data != null){
                String number = data.getStringExtra("number");
                //将安全码更新为选择的联系人电话
                et_safeNo.setText(number);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的上一步方法
     */
    @Override
    public void pre_activity() {
        Intent intent = new Intent(this, SetUp2Activity.class);
        startActivity(intent);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的下一步方法
     */
    @Override
    public void next_activity() {
        Intent intent = new Intent(this, SetUp4Activity.class);
        startActivity(intent);
    }
}
