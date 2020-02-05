package com.xiao.mobilesafe361;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.xiao.mobilesafe361.interfacre.Constants;
import com.xiao.mobilesafe361.utils.SharedPreferencesUtil;

/**
 * @Description:
 * @Author: TimXiao
 * @CreateDate: 2020/2/5 12:25
 */

public abstract class BaseActivity extends Activity {

    private GestureDetector detector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取手势识别器
        detector = new GestureDetector(this, new MyGestureDetectorListener());
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/5 13:50
      * @Description:    手势识别器监听类
     */
    private class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        /*
        /滑动事件
		//e1 : 按下事件，保存有按下的坐标
		//e2 : 抬起的事件，保存有抬起的坐标
		//velocity : 滑动的速率，速度
        * */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //获取按下和抬起的x坐标
            float xDown = e1.getRawX();
            float xUp = e2.getRawX();

            //获取按下和抬起的y坐标
            float yDown = e1.getRawY();
            float yUp = e2.getRawY();

            //屏蔽斜滑操作
            if (Math.abs(yDown - yUp) > 50) {
                Toast.makeText(getApplicationContext(), "你小子又乱滑了", 0).show();
                return true;
            }

            //下一步
            if((xDown-xUp)>100){
                doNext();
            }

            //上一步
            if((xUp-xDown)>100){
                doPre();
            }

            //true if the event is consumed, else false
            //如果想要执行事件，返回true,不想执行事件，返回false
            return true;
        }
    }

    //2.需要让手势识别器和界面的触摸事件发送关系
    //界面的触摸事件,每个界面都有触摸事件
    //MotionEvent : 触摸事件

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    //因为每个界面都有上一步和下一步的按钮的点击事件，所有相同操作，抽取到父类，这样可以不用每个类都
    //加载两个按钮，父类加载完成了，子类直接使用即可
    //本质：抽取按钮点击事件的

    //按钮的点击事件

    /**
     * @Author: TimXiao
     * @CreateDate: 2020/2/4 23:52
     * @Description: 上一步按钮点击事件
     */
    public void pre(View view) {
        doPre();
    }

    /**
     * @Author: TimXiao
     * @CreateDate: 2020/2/5 12:30
     * @Description: 上一步按钮的具体操作，此处只写能抽取的出来的公共部分代码，比如隐藏对话框，跳转
     * 动画等，其他具体的调整目标activity由抽象方法的实现类实现
     */
    private void doPre() {
        /*Intent intent = new Intent(this, SetUp4Activity.class);
        startActivity(intent);*/
        //抽象方法，由具体继承类来实现具体跳转的目标界面
        pre_activity();
        finish();
        //跳转动画代码，未实现！
    }



    /**
     * @Author: TimXiao
     * @CreateDate: 2020/2/4 23:52
     * @Description: 下一步按钮点击事件
     */
    public void next(View view) {


        /*Intent intent = new Intent(this, LostFindActivity.class);
        startActivity(intent);*/
        doNext();
    }

    private void doNext() {
        //抽象方法，由具体继承类来实现具体跳转的目标界面
        next_activity();
        finish();
        //跳转动画代码，未实现！
    }

    /*
    * 抽象方法，用来完成具体跳转目标activity的操作*/
    public abstract void pre_activity() ;
    public abstract void next_activity() ;


    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/5 12:52
      * @Description:    返回键响应上一步动作
     */
    @Override
    public void onBackPressed() {
        doPre();
        super.onBackPressed();
    }


}
