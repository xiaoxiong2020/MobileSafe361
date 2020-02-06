package com.xiao.mobilesafe361;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiao.mobilesafe361.bean.BlackNumberInfo;
import com.xiao.mobilesafe361.db.dao.BlackNumberDao;

import java.util.List;

public class BlackNumberActivity extends AppCompatActivity {

    //添加黑名单数据后返回数据的请求码
    private static final int REQUEST_ADD_CODE = 100;
    //更新黑名单数据后返回数据的请求码
    private static final int REQUEST_UPDATE_CODE = 101;


    private LinearLayout mLLLoading;
    private ImageView mEmpty;
    private ListView mListView;
    private BlackNumberDao blackNumberDao;
    private List<BlackNumberInfo> list;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);

        blackNumberDao = new BlackNumberDao(this);
        initView();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/5 20:35
      * @Description:    初始化控件
     */
    private void initView() {
        mEmpty = findViewById(R.id.blacknumber_iv_empty);
        mListView = findViewById(R.id.blacknumber_lv_listview);
        mLLLoading = findViewById(R.id.bn_ll_loading);

        //给list条目添加点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BlackNumberActivity.this, BlackNumberUpdateActivity.class);
                //设置要专递的数据
                intent.putExtra("number", list.get(position).blacknumber);
                intent.putExtra("mode", list.get(position).mode);
                //需要将更新那个条目的索引告诉更新界面
                intent.putExtra("position", position);
                //startActivity(intent);
                startActivityForResult(intent,REQUEST_UPDATE_CODE);
            }
        });


        //addData();

        //初始化数据，从数据库读取数据。
        initData();
    }

    private void initData() {
        //因为是查询数据库操作，属于耗时操作，需要隐藏显示进度条
        //加载数据之前，显示进度条
        mLLLoading.setVisibility(View.VISIBLE);
        //耗时操作需要启动新线程
        new Thread(){

            public void run(){
                list = blackNumberDao.queryAll();
                //查询数据库完成，展示数据,由于展示数据也是一个耗时操作，开启runOnUiThread线程处理
                runOnUiThread(new Runnable() {


                    @Override
                    public void run() {
                        myAdapter = new MyAdapter();
                        mListView.setAdapter(myAdapter);
                        //如果listview没有数据，就显示的 imageView，如果listview有数据，就隐藏imageView
                        mListView.setEmptyView(mEmpty);
                        //显示出数据就隐藏进度条
                        mLLLoading.setVisibility(View.GONE);
                    }
                });
            }
        }.start();
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 11:07
      * @Description:    enteradd按钮的点击事件
     */
    public void enteradd(View view) {
        Intent intent = new Intent(this,BlackNumberAddActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, REQUEST_ADD_CODE);
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/6 11:14
      * @Description:    跳转其他activity后，返回当前activityd会调用的方法，主要用来接收数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //接受传递过来的数据
        //因为添加界面和更新界面都会传递数据过来，需要判断数据是谁传递过来
        //添加黑名单后返回的数据
        if(requestCode == REQUEST_ADD_CODE){
            if(data != null){
                //解析传回的数据
                String number = data.getStringExtra("number");
                //defaultValue : 默认的值，没有传递数据过来的时候，使用的值
                int mode = data.getIntExtra("mode", -1);

                //将数据存放到bean类
                BlackNumberInfo blackNumberInfo = new BlackNumberInfo(number, mode);
                //将数据添加到集合哪个位置，index：位置；element：添加的数据
                list.add(0, blackNumberInfo);
                //更新界面，将新的数据刷新出来
                myAdapter.notifyDataSetChanged();
            }
        }else if(requestCode == REQUEST_UPDATE_CODE){
            if(data != null){
                //解析传回的数据
                int updatemode = data.getIntExtra("mode", -1);
                //将更新的拦截类型设置给更新的条目，重新刷新界面
                int position = data.getIntExtra("position", -1);
                list.get(position).mode = updatemode;
                //更新界面
                myAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(getApplicationContext(), R.layout.blacknumber_listview_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mNumber = convertView.findViewById(R.id.item_tv_number);
                viewHolder.mMode = convertView.findViewById(R.id.item_tv_mode);
                viewHolder.mDelete = convertView.findViewById(R.id.item_iv_delete);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //把已经从数据库获取的数据list展示在viewHolder所包含的三种控件中
            BlackNumberInfo blackNumberInfo = list.get(position);
            viewHolder.mNumber.setText(blackNumberInfo.blacknumber);
            //因为数据库中的拦截类型是0,1,2
            switch (blackNumberInfo.mode){
                case 0:
                    //电话拦截
                    viewHolder.mMode.setText("电话拦截");
                    break;
                case 1:
                    //短信拦截
                    viewHolder.mMode.setText("短信拦截");
                    break;
                case 2:
                    //全部拦截
                    viewHolder.mMode.setText("全部拦截");
                    break;
            }
            return convertView;
        }

    }
    static class ViewHolder{
        TextView mNumber,mMode;
        ImageView mDelete;
    }

    /**
      * @Author:         TimXiao
      * @CreateDate:     2020/2/5 22:33
      * @Description:    添加测试数据到数据库
     */
    private void addData(){
        blackNumberDao.add("138001", 1);
        blackNumberDao.add("138002", 2);
        blackNumberDao.add("138003", 3);
    }
}
