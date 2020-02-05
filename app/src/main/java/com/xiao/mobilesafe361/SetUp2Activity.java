package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiao.mobilesafe361.interfacre.Constants;
import com.xiao.mobilesafe361.utils.SharedPreferencesUtil;

public class SetUp2Activity extends BaseActivity {

    private RelativeLayout mRelSIM;
    private ImageView mIsLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up2);

        initView();
    }

    /**
     * @Author: TimXiao
     * @CreateDate: 2020/2/5 14:30
     * @Description:
     */
    private void initView() {
        mRelSIM = findViewById(R.id.setup2_rel_sim);
        mIsLock = findViewById(R.id.setup2_iv_islock);

        //2.再次进入界面，回显是否绑定的操作
        String sp_sim = SharedPreferencesUtil.getString(getApplicationContext(), Constants.SIM, "");
        if (TextUtils.isEmpty(sp_sim)) {
            mIsLock.setImageResource(R.drawable.unlock);
        }else{
            mIsLock.setImageResource(R.drawable.lock);
        }

        //1.点击绑定SIM卡
        // 设置按钮的点击事件
        mRelSIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定/解绑SIM卡

                //绑定 -> 点击解绑
                //未绑定 -> 点击绑定
                //判断是否绑定,默认未绑定，即取不到IP
                String sp_sim = SharedPreferencesUtil.getString(getApplicationContext(), Constants.SIM, "");
                if (TextUtils.isEmpty(sp_sim)) {
                    //绑定流程
                    //未绑定 -> 点击绑定
                    //绑定SIM卡：本质保存SIM卡的序列号
                    //获取电话的管理者
                    TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    @SuppressLint("MissingPermission") String sim = manager.getSimSerialNumber();
                    Log.d("xiong", sim);
                    //将序列号存入SP文件中
                    SharedPreferencesUtil.saveString(getApplicationContext(), Constants.SIM, sim);
                    //修改图片
                    mIsLock.setImageResource(R.drawable.lock);
                }else {
                    //解绑流程,置空SP文件的SIM属性值即可
                    SharedPreferencesUtil.saveString(getApplicationContext(),Constants.SIM, "");
                    //修改图片
                    mIsLock.setImageResource(R.drawable.unlock);
                }

            }
        });
    }


    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的上一步方法
     */
    @Override
    public void pre_activity() {
        Intent intent = new Intent(this, SetUp1Activity.class);
        startActivity(intent);
    }

    /**
     * @Author:         TimXiao
     * @CreateDate:     2020/2/5 12:38
     * @Description:    继承父类实现的下一步方法
     */
    @Override
    public void next_activity() {
        //判断是否绑定sim卡，如果绑定跳转，如果没有绑定禁止跳转
        String sp_sim = SharedPreferencesUtil.getString(getApplicationContext(), Constants.SIM, "");
        if (TextUtils.isEmpty(sp_sim)) {
            Toast.makeText(getApplicationContext(), "请先绑定sim卡...", 0).show();
        }


        Intent intent = new Intent(this, SetUp3Activity.class);
        startActivity(intent);
    }
}
