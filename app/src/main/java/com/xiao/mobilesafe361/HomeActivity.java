package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private final String[] TITLES = new String[] { "手机防盗", "骚扰拦截", "软件管家",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具" };
    private final String[] DESCS = new String[] { "远程定位手机", "全面拦截骚扰", "管理您的软件",
            "管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };
    private final int[] ICONS = new int[] { R.drawable.sjfd, R.drawable.srlj,
            R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
            R.drawable.hcql, R.drawable.cygj };

    private ImageView mLogo;
    private GridView mGridView;

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
