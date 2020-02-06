package com.xiao.mobilesafe361;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileUtils;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.mobilesafe361.interfacre.Constants;
import com.xiao.mobilesafe361.utils.SharedPreferencesUtil;

import static com.xiao.mobilesafe361.interfacre.Constants.SJFDPWD;
import static com.xiao.mobilesafe361.interfacre.Constants.SJFDSZWC;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String[] TITLES = new String[] { "手机防盗", "骚扰拦截", "软件管家",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具" };
    private final String[] DESCS = new String[] { "远程定位手机", "全面拦截骚扰", "管理您的软件",
            "管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };
    private final int[] ICONS = new int[] { R.drawable.sjfd, R.drawable.srlj,
            R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
            R.drawable.hcql, R.drawable.cygj };

    private ImageView mLogo;
    private GridView mGridView;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/3 15:36
      * @Description:    初始化界面
     */
    private void initView() {
        mLogo = findViewById(R.id.home_iv_logo);
        mGridView = findViewById(R.id.home_gv_gridview);
        //实现Logo翻转动画效果
        setAnimation();

        //通过gridView显示数据
        mGridView.setAdapter(new MyGridViewAdapter());

        //设置gridview的条目点击事件
        mGridView.setOnItemClickListener(this);

        //TIM
        String confirm = SharedPreferencesUtil.getString(getApplicationContext(), SJFDPWD, "");
        //Log.d("xiong", "SIM:"+confirm);
        //sendSMSTest();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/5 17:42
      * @Description:    发送短信功能测试
     */
    private void sendSMSTest() {
        SmsManager smsManager = SmsManager.getDefault();
        //destinationAddress : 收件人的号码
        //scAddress : 服务中心的地址
        //text : 发送的短信内容
        //sentIntent : 判断是否发送成功
        //deliveryIntent : 判断收件人是否接受成功
        smsManager.sendTextMessage("5554", null, "test message", null, null);

    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 16:44
      * @Description:    grid条目添加点击事件
     * 		 *@param parent 被点击条目的父控件
     * 		 *@param view 被点击条目的view对象
     * 		 *@param position  被点击的条目的索引（位置）
     * 		 *@param id  被点击的条目的id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                //判断是弹出设置密码对话框还是验证密码对话框
                //问题：需要知道密码是否设置成功
                //当设置密码成功，保存密码，再次点击手机防盗条目的时候，获取保存的密码，
                //如果有保存，弹出验证密码对话框，如果没有弹出设置密码对话框
                String sp_pwd = SharedPreferencesUtil.getString(getApplicationContext(), SJFDPWD, "");
                if(TextUtils.isEmpty(sp_pwd)){
                    //密码为空则弹出密码设置对话框
                    showSetPasswordDialog();
                }else {
                    //Toast.makeText(getApplicationContext(), "弹出密码验证对话框", Toast.LENGTH_SHORT).show();
                    showEnterPasswordDialog();
                }
                break;
            case 1://骚扰拦截功能
                Intent intent = new Intent(this, BlackNumberActivity.class);
                startActivity(intent);
            case 7://常用功能
                Intent intent7 = new Intent(this, CommonToolActivity.class);
                startActivity(intent7);
        }
    }

    private void showEnterPasswordDialog(){
        //将布局文件home_setpassword_dialog.xml放到Dialog中显示
        //1. 创建对话框Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //2. 将xml加载为View对象
        View view = View.inflate(getApplicationContext(), R.layout.home_enterpassword_dialog, null);




        //初始化控件
        final EditText mPsw = (EditText) view.findViewById(R.id.dialog_et_psw);
        Button mOk = (Button) view.findViewById(R.id.dialog_ok);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);

        //设置按钮点击事件
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. 获取输入密码值,trim()去除空格
                String psw = mPsw.getText().toString().trim();
                //2. 判断密码不能为空,TextUtils会判断null和""空字符串两种情况
                if (TextUtils.isEmpty(psw)){
                    //假如为空则弹出吐司
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }
                //3. 获取SP文件保存的密码，与录入的密码比对，如果一致则显示密码正确。
                String confirm = SharedPreferencesUtil.getString(getApplicationContext(), SJFDPWD, "");
                //3. 判断两次密码必须为一致
                if(psw.equals(confirm)){
                    Toast.makeText(getApplicationContext(), "密码正确", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                    //跳转到手机防盗设置向导的第一个界面
                    enterLostFind();
                }else {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏对话框
                alertDialog.dismiss();
            }
        });

        //3. 将一个view对象加载到对话框中
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void enterLostFind() {
        /*
         * 根据SP中保存的是否完成向导设置标识，来决定是进入第一个向导界面还是进入，设置完成界面*/
        boolean b = SharedPreferencesUtil.getBoolean(getApplicationContext(), SJFDSZWC, false);
        if(b){
            //跳转到设置完成界面
            Intent intent = new Intent(this, LostFindActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, SetUp1Activity.class);
            startActivity(intent);
        }

    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 16:52
      * @Description:    输入密码对话框
     */
    private void showSetPasswordDialog() {
        //将布局文件home_setpassword_dialog.xml放到Dialog中显示
        //1. 创建对话框Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //2. 将xml加载为View对象
        View view = View.inflate(getApplicationContext(), R.layout.home_setpassword_dialog, null);


        //初始化控件
        final EditText mPsw = (EditText) view.findViewById(R.id.dialog_et_psw);
        final EditText mConfirm = (EditText) view.findViewById(R.id.dialog_et_confirm);
        Button mOk = (Button) view.findViewById(R.id.dialog_ok);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);

        //设置按钮点击事件
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. 获取输入密码值,trim()去除空格
                String psw = mPsw.getText().toString().trim();
                //2. 判断密码不能为空,TextUtils会判断null和""空字符串两种情况
                if (TextUtils.isEmpty(psw)){
                    //假如为空则弹出吐司
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String confirm = mConfirm.getText().toString().trim();
                //3. 判断两次密码必须为一致
                if(psw.equals(confirm)){
                    //保存设置的密码，为再次点击手机防盗条目判断做准备
                    SharedPreferencesUtil.saveString(getApplicationContext(), SJFDPWD, psw);
                    Toast.makeText(getApplicationContext(), "密码保存成功", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏对话框
                alertDialog.dismiss();
            }
        });

        //3. 将一个view对象加载到对话框中
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/3 18:42
      * @Description:    按钮点击事件，进入设置中心界面
     * view 指的是对应控件的View对象
     */
    public void enterSetting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }




    /**
  * @Author:         TimXiao
  * @CreateDate:     2020/2/3 16:32
  * @Description:    gridView适配器
 */
    private class MyGridViewAdapter extends BaseAdapter {
        //获取条目的数量
        @Override
        public int getCount() {
            return ICONS.length;
        }

        //根据条目的位置获取条目的对象数据
        @Override
        public Object getItem(int position) {
            return null;
        }

        //根据条目的位置获取条目的ID
        @Override
        public long getItemId(int position) {
            return 0;
        }

        //设置条目的样式
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
            * 将布局文件转换为View对象
            * 1. 上下文
            * 2. xml对象,在inflate方法中主要加载xml中的格式以及内容。
            * 3. 属于哪个父控件组*/
            View view = View.inflate(getApplicationContext(), R.layout.home_gridview_item, null);
            //初始化控件，将内容加载到xml对象（view）对应的属性值中。
            //获取view中的子控件
            ImageView mIcon = view.findViewById(R.id.item_iv_icon);
            TextView mTitle = view.findViewById(R.id.item_tv_title);
            TextView mDesc = view.findViewById(R.id.item_tv_desc);

            //往子控件中加载内容
            mIcon.setImageResource(ICONS[position]);//根据图片的位置展示相应的图片
            mTitle.setText(TITLES[position]);//根据标题的位置展示相应的标题
            mDesc.setText(DESCS[position]);//根据描述信息的位置展示相应的描述信息
            return view;
        }
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/3 15:38
      * @Description:    实现Logo翻转动画效果
     * 动画的种类
     *
     * 		1.帧动画
     * 			xml -> 多张图片
     * 		2.补间动画
     * 			平移动画，旋转动画，缩放动画，渐变动画
     * 		3.属性动画
     * 			3.0，实现3D效果
     */
    private void setAnimation() {
        //setRotationX : 根据x轴进行旋转  ；setRotationY：根据y轴进行旋转;setRotation:根据z轴进行旋转
        //mLogo.setRotationY(rotationY)
        //参数1：执行动画的控件
        //参数2：执行动画的方法的名称
        //参数3：执行动画所需的参数
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLogo, "rotationY", 0f,90f,270f,360f);
        objectAnimator.setDuration(2000);//设置持续时间
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);//设置动画执行次数，INFINITE：一直执行
        //RESTART : 每次旋转从开始的位置旋转
        //REVERSE : 旋转结束，从结束的位置开始旋转
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);//设置动画执行的类型

        objectAnimator.start();//执行动画
    }
}
