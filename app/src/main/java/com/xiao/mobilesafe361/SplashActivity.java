package com.xiao.mobilesafe361;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiao.mobilesafe361.utils.PackageUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class SplashActivity extends AppCompatActivity {

    private static final String CONNECTURL = "http://10.0.2.2:8080/update.html";
    private TextView mVersion;
    private int mNewCode;
    private String mNewApkurl;
    private String mNewMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        update();
    }

    private void update() {
        //1.链接服务器，获取服务器数据，判断是否有最新版本
        //1.1.链接服务器   a.联网操作，耗时操作，子线程中 ；b.权限    c.httpURlConnection  httpclient  xutils  volly  okhttp
        //connTimeout : 链接超时时间
        HttpUtils httpUtils = new HttpUtils(2000);
        //链接请求服务器
        //①method : 请求方式
        //②url ： 请求路径
        //③params : 请求参数   http://baidu.com/?name=ls&psw=123456
        //④RequestCallBack : 请求的回调监听
        httpUtils.send(HttpRequest.HttpMethod.GET, CONNECTURL, null, new RequestCallBack<String>() {
            //请求成功调用的方法
            //responseInfo : 保存服务器返回的数据
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //1.2.链接成功，获取服务器返回的数据，问题：服务会返回那些数据，code:新版本的版本号   apkurl:新版本的下载路径    msg:更新版本的描述信息，更新内容的操作
                //问题：如何封装数据   xml  json
                String json = responseInfo.result;
                Log.d("xiong", json);
                processJson(json);
            }

            /**
              * @ClassName:      
              * @Description:    请求失败跳转首页
              * @Author:         TimXiao
              * @CreateDate:     2020/2/2 21:58
             */
            @Override
            public void onFailure(HttpException e, String s) {
                enterHome();
            }
        });
    }

    /**
     * @description 1.2.1.解析json数据
     * @date: 2020/1/30 23:44
     * @author: TimXiao
     * @param
     * @return
     */
    protected void processJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            mNewCode = jsonObject.getInt("code");
            mNewApkurl = jsonObject.getString("apkurl");
            mNewMsg = jsonObject.getString("msg");
            Log.d("xiong", mNewCode+mNewApkurl+mNewMsg);
            //1.3.判断是否有最新版本
            //判断最新版本的apk的版本号是否和当前应用程序的版本号一致，如果一致，没有最新版本，如果不一致，有最新版本
            int currentVersionCode = PackageUtil.getVersionCode(this);
            if (currentVersionCode == mNewCode){
                //如果一致，表示没有新版本，跳转到首页
                enterHome();
            } else {
                //如果不一致表示有新版本，弹出对话框，选择是否更新
                showUpdateDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
      * @Description:    更新程序，生成一个选择对话框
      * @Author:         TimXiao
      * @CreateDate:     2020/2/2 22:03
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置标题
        builder.setTitle("最新版本："+ mNewCode + ".0");
        //设置图标
        builder.setIcon(R.drawable.ic_launcher);
        //设置描述信息
        builder.setMessage(mNewMsg);
        //设置两个按钮
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //显示对话框
        builder.show();
    }

    private void enterHome() {
        //用显示意图打开首页activity
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * @description 初始化界面
     * @date: 2020/1/30 22:43
     * @author: TimXiao
     * @param
     * @return
     */
    public void initView(){
        mVersion = (TextView) findViewById(R.id.splash_tv_version);

        //获取当前应用程序的版本名称，设置给textview展示
        mVersion.setText("版本:"+ PackageUtil.getVersionName(this));
    }
}
