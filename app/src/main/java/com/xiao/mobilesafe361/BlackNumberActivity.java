package com.xiao.mobilesafe361;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

    private LinearLayout mLLLoading;
    private ImageView mEmpty;
    private ListView mListView;
    private BlackNumberDao blackNumberDao;
    private List<BlackNumberInfo> list;

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
                        mListView.setAdapter(new MyAdapter());
                        //如果listview没有数据，就显示的 imageView，如果listview有数据，就隐藏imageView
                        mListView.setEmptyView(mEmpty);
                        //显示出数据就隐藏进度条
                        mLLLoading.setVisibility(View.GONE);
                    }
                });
            }
        }.start();
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
