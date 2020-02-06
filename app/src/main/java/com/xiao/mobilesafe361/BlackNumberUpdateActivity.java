package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xiao.mobilesafe361.db.dao.BlackNumberDao;

public class BlackNumberUpdateActivity extends AppCompatActivity {

    private String number;
    private int mode;
    private int position;
    private EditText mBlackNumber;
    private RadioGroup mModes;
    private BlackNumberDao blackNumberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number_update);

        blackNumberDao = new BlackNumberDao(this);

        //接收其他activity传递过来的数据
        getValue();
        initView();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 12:19
      * @Description:    接收其他activity传递过来的数据
     */
    private void getValue() {
        //获取跳转发起activity传递过来的intent，
        //如果是，跳转发起者要获取返回的数据则，在返回方new Intent来填充数据，一般用data做变量名
        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        mode = intent.getIntExtra("mode", -1);
        position = intent.getIntExtra("position", -1);
    }

    private void initView() {
        mBlackNumber = (EditText) findViewById(R.id.blacknumberupdate_et_blacknumber);
        mModes = (RadioGroup) findViewById(R.id.blacknumberupdate_rg_modes);

        // 将传递过来的数据设置控件显示
        setMsg();
    }

    private void setMsg() {
        mBlackNumber.setText(number);
        // 2.显示类型
        int checkId = -1;// 初始化Radiobutton的id保存变量
        // 因为传递来的是0,1,2,所以需要根据传递过来的数据，设置应该选中Radiobutton
        switch (mode) {
            case 0:
                // 电话的Radiobutton
                checkId = R.id.blacknumberupdate_rbtn_call;
                break;
            case 1:
                // 短信的Radiobutton
                checkId = R.id.blacknumberupdate_rbtn_sms;
                break;
            case 2:
                // 全部的Radiobutton
                checkId = R.id.blacknumberupdate_rbtn_all;
                break;
            default:
                // 如果不是012，设置默认选中全部Raddiobutton
                checkId = R.id.blacknumberupdate_rbtn_all;
                break;
        }
        mModes.check(checkId);// 设置选中哪个Radiobutton，id:Radiobutton的id
    }



    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 12:35
      * @Description:    更新按钮点击事件
     */
    public void update(View view) {
        // 1.获取数据，跟添加界面一样的操作
        // 1.1.获取输入的号码，判断是否为空
        String updateblacknumber = mBlackNumber.getText().toString().trim();
        if (TextUtils.isEmpty(updateblacknumber)) {
            Toast.makeText(getApplicationContext(), "请输入黑名单号码", 0).show();
            // 如果号码为空，不执行其他操作
            return;
        }
        // 1.2.获取拦截类型，判断拦截类型是否选择
        int updatemode = -1;// 初始化拦截类型
        // 选中那个RadioButton，表示选中哪个类型
        int checkedRadioButtonId = mModes.getCheckedRadioButtonId();// 获取被选中的RadioButton的id,返回RadioButton的id
        switch (checkedRadioButtonId) {
            case R.id.blacknumberupdate_rbtn_call:
                // 电话拦截
                updatemode = 0;
                break;
            case R.id.blacknumberupdate_rbtn_sms:
                // 短信拦截
                updatemode = 1;
                break;
            case R.id.blacknumberupdate_rbtn_all:
                // 全部拦截
                updatemode = 2;
                break;
            default:
                Toast.makeText(getApplicationContext(), "请选择拦截类型", 0).show();
                // break;
                return;
        }
        // 2.更新数据库中的数据
        //2.1.更新数据库中的数据
        boolean update = blackNumberDao.update(updateblacknumber, updatemode);
        if (update) {
            Toast.makeText(getApplicationContext(), "更新成功", 0).show();

            //3.更新成功，将更新的数据回传给黑名单管理界面进行界面的更新显示操作
            Intent data = new Intent();
            //因为号码无法更改，所以不需要回传
            data.putExtra("mode", updatemode);
            data.putExtra("position", position);//将更新条目的索引回传给黑名单管理界面
            setResult(Activity.RESULT_OK, data);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "系统繁忙，请稍候再试..", 0).show();
        }
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 12:34
      * @Description:    取消按钮点击事件
     */
    public void cancel(View view) {
        finish();
    }
}
