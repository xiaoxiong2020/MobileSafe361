package com.xiao.mobilesafe361.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lidroid.xutils.view.annotation.event.OnFocusChange;


/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/2/3 14:38
 */

@SuppressLint("AppCompatCustomView")
public class HomeTextView extends TextView {
    /*
    * 在代码中调用的时候使用该方法*/
    public HomeTextView(Context context) {
        //super(context);
        //HomeTextView homeTextView = new HomeTextView(context);
        this(context, null);
    }

    /*
    * 在布局文件中使用调用的方法
    * 布局文件中的空间都会通过反射的方式，转换成代码。即在转换的时候new的时候调用的方法
    * 空间中所有的属性都会保存到AttributeSet中*/
    public HomeTextView(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        //-1表示使用默认的样式
        this(context, attrs, -1);
    }

    /*
    * 在控件的内部让两个参数的构造函数调用，所有的实现方法都写在三个函数的方法中，这是通用做法*/
    public HomeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         *
         android:singleLine="true"
         android:ellipsize="marquee"
         android:focusableInTouchMode="true"
         android:marqueeRepeatLimit="marquee_forever"
         */
        setSingleLine();//使用代码设置单行显示
        setEllipsize(TextUtils.TruncateAt.MARQUEE);//使用代码设置滚动操作
        setFocusableInTouchMode(true);//使用代码设置触摸获取焦点
        setMarqueeRepeatLimit(-1);//设置滚动次数
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/3 14:57
      * @Description:    是否允许自定义控件获取焦点
     */
    @Override
    public boolean isFocused() {
        return true;
    }

    //焦点切换调用的方法
    //focused : 焦点是否释放
    //direction : 焦点移动的方向
    //previouslyFocusedRect : 焦点从哪个控件过来
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        //当焦点被抢夺的时候，不能抢夺textview的焦点
        //如果焦点没有被抢夺，调用系统的方法，帮我们保留焦点
        //如果焦点被抢夺了，禁止调用系统的方法，禁止系统移除焦点
        if (focused){
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }



}
