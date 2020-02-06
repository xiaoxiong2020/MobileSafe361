package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.mobilesafe361.db.dao.AddressDao;

public class AddressActivity extends AppCompatActivity {

    private TextView mNumber;
    private AddressDao addressDao;
    private TextView mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        addressDao = new AddressDao();

        initView();
    }

    private void initView() {
        mNumber = findViewById(R.id.address_et_number);
        mAddress = findViewById(R.id.address_tv_address);
    }

    public void address(View view) {
        String number = mNumber.getText().toString().trim();
        if (!TextUtils.isEmpty(number)) {
            //查询归属地
            String address = addressDao.getAddress(this, number);
            if (!TextUtils.isEmpty(address)) {
                mAddress.setText("归属地:"+address);
            }else {
                Toast.makeText(getApplicationContext(), "未查询到归属地", 0).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "请输入要查询的号码", 0).show();
        }
    }
}
