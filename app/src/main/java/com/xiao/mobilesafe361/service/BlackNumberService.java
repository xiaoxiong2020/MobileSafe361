package com.xiao.mobilesafe361.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.xiao.mobilesafe361.db.dao.BlackNumberDao;

public class BlackNumberService extends Service {

    private SmsReceiver smsReceiver;
    private BlackNumberDao blackNumberDao;
    private TelephonyManager tel;
    private MyPhoneStateListener listener;



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        blackNumberDao = new BlackNumberDao(getApplicationContext());

        //1.短信拦截
        //判断发件人的拦截类型
        //代码注册广播接受者
        //1.1.广播接受者
        smsReceiver = new SmsReceiver();
        //1.2.设置接受的广播事件
        //设置意图过滤器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(1000);
        //设置接受的广播事件,意图过滤事件
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED_ACTION");
        //1.3.注册广播接受者
        registerReceiver(smsReceiver, intentFilter);

        //2.电话拦截
        //监听电话的状态
        //2.1.获取电话的管理者
        tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //2.2.监听电话的事件
        listener = new MyPhoneStateListener();
        //listener : 回调监听
        //events : 监听的事件
        tel.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        Log.e("xiong", "服务开启");
    }

    @Override
    public void onDestroy() {
        //1.4.当服务退出，注销广播接受者
        unregisterReceiver(smsReceiver);


        //2.3.服务关闭，停止监听电话状态的操作
        tel.listen(listener, PhoneStateListener.LISTEN_NONE);//设置不在监听任何状态，停止监听操作
        Log.e("xiong", "服务关闭");
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 15:50
      * @Description:    广播接受者，收到广播后会执行的onReceive方法
     */
    private class SmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("xiong", "onReceive");
            //1.5.接受解析短信，获取发件人的拦截类型，进行拦截设置了
            Object[] objs = (Object[]) intent.getExtras().get("pdus");

            for (Object obj : objs) {
                //转化成短信对象
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                //获取发件人的号码
                String sender = smsMessage.getOriginatingAddress();
                //获取短信内容
                String messageBody = smsMessage.getMessageBody();

                //判断发件人类型，来决定是否拦截
                int mode = blackNumberDao.queryMode(sender);
                if (mode == 1 || mode == 2) {
                    //拦截短信
                    abortBroadcast();
                }
            }
        }
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 17:20
      * @Description:    电话的监听操作
     */
    private class MyPhoneStateListener extends PhoneStateListener {
        //监听电话状态的方法
        //state : 电话的状态
        //incomingNumber : 来电号码
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲的状态，挂断的状态

                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃状态
                    //获取来电话的拦截类型，判断是否应该挂断电话
                    Log.d("xiong", "来电话了,挂断电话");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://通话状态，接听状态

                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }
}
