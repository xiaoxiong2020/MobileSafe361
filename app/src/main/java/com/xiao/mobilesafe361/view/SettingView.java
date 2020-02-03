package com.xiao.mobilesafe361.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xiao.mobilesafe361.R;

/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/2/3 20:10
 */

/**
  * @Author:         TimXiao
  * @CreateDate:     2020/2/3 20:12
  * @Description:    抽取设置中心的自定义控件
 */
@SuppressLint("AppCompatCustomView")
public class SettingView extends RelativeLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.xiao.mobilesafe361";
    private TextView mTitle;
    private ImageView mIcon;

    public SettingView(Context context) {
        //super(context);
        this(context, null);
    }

    public SettingView(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        this(context, attrs, -1);
    }

    public SettingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
        * 将含有TextView和ImageView的控件添加到自定义控件中
        * */
        initView();

        /*
        * 目的：将自定义控件中的自定义属性值取出来，赋值给自定义控件中的xml文件中的组件，因为最终是这些组件
        * 在activity中显示
        * 做法：因为在AttributeSet中保存有该控件的所有属性，所以可以通过该参数来取值 。
        * attrs.getAttributeValue通过命名空间+属性名，来取值
        * */
        String title = attrs.getAttributeValue(NAMESPACE, "title");
        mTitle.setText(title);
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/3 20:28
      * @Description:    将含有TextView和ImageView的控件添加到自定义控件中
     */
    private void initView() {
        View view = View.inflate(getContext(), R.layout.settingview, null);
        //将参数中的控件view加入到当前控件SettingView中
        this.addView(view);

        //初始化控件，即拿到该控件在代码中使用
        mTitle = view.findViewById(R.id.setting_tv_title);
        mIcon = view.findViewById(R.id.setting_iv_icon);
    }
}
