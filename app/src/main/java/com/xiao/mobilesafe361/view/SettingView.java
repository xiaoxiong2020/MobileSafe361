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
    //开关的状态，默认状态为关
    private boolean mToggleOn = true;

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
        * 目的：获取自定义控件属性title的属性值。
        * 所以要将自定义控件中的自定义属性值取出来，赋值给自定义控件中的xml文件中的组件，因为最终是这些组件
        * 在activity中显示
        * 做法：因为在AttributeSet中保存有该控件的所有属性，所以可以通过该参数来取值 。
        * attrs.getAttributeValue通过命名空间+属性名，来取值
        * */
        String title = attrs.getAttributeValue(NAMESPACE, "title");
        mTitle.setText(title);

        //获取自定义属性（是否显示图片isloggle），对应的值
        boolean isloggle = attrs.getAttributeBooleanValue(NAMESPACE, "isloggle", true);
        //根据属性的值设置图片(xml文件中)的隐藏和显示了
        mIcon.setVisibility(isloggle ? View.VISIBLE : View.GONE);
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

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 0:19
      * @Description:    提供给activity调用的方法，根据传递过来的开关状态，设置自定义控件的显示图标
     * isToggleOn开关状态。true为打开，false为关闭
     */
    public void setToggleOnOrOff(boolean isToggleOn) {
        mToggleOn = isToggleOn;//拿到开光状态后将其值保存到成员变量中供其他方法使用，比如get方法。
        if(isToggleOn){
            mIcon.setImageResource(R.drawable.on);
        }else {
            mIcon.setImageResource(R.drawable.off);
        }
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 0:30
      * @Description:    提供给activity调用的方法，返回当前开关状态
     */
    public boolean getToggle() {
        return mToggleOn;
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/4 11:08
      * @Description:    根据当前开光状态来更新点击后的开关图片
     */
    public void toggle() {
        /*目的：获取开关的当前状态
         * 问题：点击事件是在当前activity中实现的，但是开关状态是在自定义控件中的
         * 解决办法，在自定义控件中新增一个获得开关状态的方法，暴漏给当前activity**/
//        boolean isToggleOn = getToggle();
//        if (isToggleOn){
//            /*
//             * 目的：通过点击事件控制开启和关闭图标
//             * 问题：点击事件是在当前activity中实现的，但是修改的图片是在自定义控件中的
//             * 解决办法，在自定义控件中新增一个修改图标的方法，暴漏给当前activity*/
//            setToggleOnOrOff(false);
//        }else {
//            setToggleOnOrOff(true);
//        }

        //第二步优化
			/*if (mIsToggle) {
				setToggleOn(!mIsToggle);
			}else{
				setToggleOn(!mIsToggle);
			}*/
        //第三步优化
        setToggleOnOrOff(!mToggleOn);

    }
}
