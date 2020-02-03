package com.xiao.mobilesafe361;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiao.mobilesafe361.utils.PackageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


public class SplashActivity extends AppCompatActivity {
    //服务器更新页面地址
    private static final String CONNECTURL = "http://10.0.2.2:8080/update.html";
    //新版APK下载地址
    private static final String SAVE_URL = "mnt/sdcard/mobileSafe361_2.apk";
    private TextView mVersion;
    private int mNewCode;
    private String mNewApkurl;
    private String mNewMsg;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化界面
        initView();
        /*
        * 延迟一段时间后，再进行更新操作
        * postDelayed()
        * 1. 接受消息执行的操作，即Runnable对象的run方法
        * 2. 延迟的时间*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行更新操作
                update();
            }
        }, 2000);

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
      * @Description:    更新版本对话框，生成一个选择对话框
      * @Author:         TimXiao
      * @CreateDate:     2020/2/2 22:03
     */
    private void showUpdateDialog() {
        //显示下载对话框
        showProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框是否可以消失
        //builder.setCancelable(false);
        //监听对话框消失的操作
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                //隐藏对话框
                dialogInterface.dismiss();
                //对话框消失，跳转到首页
                enterHome();
            }
        });
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
                //隐藏对话框
                dialogInterface.dismiss();
                //下载最新的APK
                downLoadAPK();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //隐藏对话框
                dialogInterface.dismiss();
                //对话框消失，跳转到首页
                enterHome();
            }
        });
        //显示对话框
        builder.show();
    }

    /**
      * @Description:    显示下载进度条对话框
      * @Author:         TimXiao
      * @CreateDate:     2020/2/3 10:12
     */

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        //设置进度条的样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置对话框是否可以消失
        progressDialog.setCancelable(false);
        //显示对话框
        progressDialog.show();
    }

    private void downLoadAPK() {
        //问题：1. 下载路径；2. 写SD卡的权限；3. 判断SD卡是否挂载；4. 生成一个2.0版本的APK。
        //判断SD卡是否挂载,如果有则开始下载动作，如果没有则吐司提示用户
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //链接服务器，下载最新版本
            HttpUtils httpUtils = new HttpUtils();
            //1. url下载路径
            //2. 文件在手机中的保存路径
            //3. 下载的回调方法
            httpUtils.download(mNewApkurl, SAVE_URL, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //下载成功，隐藏对话框
                    progressDialog.dismiss();
                    enterHome();
                    //下载完成后，安装APK
                    installAPK();
                }

                /**
                  * @Description:    获取下载进度情况的方法
                 * 目的设置下载进度条
                 * 1. total: 下载总进度
                 * 2. current：当前进度
                 * 3. isUploading: 是否支持回调上传
                  * @Author:         TimXiao
                  * @CreateDate:     2020/2/3 10:08
                 */
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    //设置总进度
                    progressDialog.setMax((int) total);
                    //设置当前进度
                    progressDialog.setProgress((int) current);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    //下载失败，隐藏对话框
                    progressDialog.dismiss();
                }
            });
        }else {
            //吐司提醒用户没有SD卡
            Toast.makeText(getApplicationContext(), "没有可用的SD卡", Toast.LENGTH_SHORT).show();
        }

    }


    
    
    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/3 11:54
      * @Description:    APK安装的方法
     */
    private void installAPK() {
        /*
        * <activity android:name=".PackageInstallerActivity"
                android:configChanges="orientation|keyboardHidden"
                android:theme="@style/TallTitleBarTheme">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="content" /> //用内容提供者获取APK安装文件
                <data android:scheme="file" />     //用文件获取APK
                <data android:mimeType="application/vnd.android.package-archive" />
            </intent-filter>
        </activity>
        用隐式意图打开PackageInstallerActivity
        */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //1. data数据,这里是uri路径，2. 数据的类型
        intent.setDataAndType(Uri.fromFile(new File(SAVE_URL)), "application/vnd.android.package-archive");
        //直接打开另一个activity
        startActivity(intent);
        //
        // 当跳转到的activity退出时，会调用当前activity的onActivityResult()方法
        //用这个逻辑实现，退出安装界面时返回首页而不是又返回欢迎页面
        // 1. intent 意图。
        // 2. requestCode请求码，被打开activity用该请求码来区分是哪个发起activity来打开自己的。
        // 因为只有一个activity请求打开PackageInstallerActivity，所以就用0即可，不然可以定义一个常量。
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //跳转首页
        enterHome();
    }

    private void enterHome() {
        //用显示意图打开首页activity
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        //防止按下返回键，跳回欢迎页面，需要在跳转首页后，移除欢迎页面
        finish();
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
