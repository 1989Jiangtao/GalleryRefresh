package com.cjt.galleryrefreshdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cjt.galleryrefreshdemo.adapter.MyGalleryAdapter;
import com.cjt.galleryrefreshdemo.bean.PictureBean;
import com.cjt.galleryrefreshdemo.listener.IGalleryEventListener;
import com.cjt.galleryrefreshdemo.view.MyGallery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IGalleryEventListener{

    private MyGallery myGallery ; // 自定义Gallery控件
    private MyGalleryAdapter myGalleryAdapter ; // 适配器
    private List<PictureBean> pictureList = new ArrayList<>();  // 数据集合，使用前要先转换为List的子类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGallery = (MyGallery) findViewById(R.id.myGallery); /// 获取界面自定义的Gallery控件
        myGallery.setGalleryEventListerner(this); // 为Gallery设置滑动监听器

        // 准备数据
        PictureBean bean ;
        for (int i = 1; i <= 7 ; i++) {
            bean = new PictureBean();
            int picResId = this.getResources().getIdentifier("pic"+i,"mipmap",getPackageName());
            bean.setPictureResId(picResId);
            bean.setPictureName("照片--"+i);
            pictureList.add(bean);
        }

        // 设置适配器
        myGalleryAdapter = new MyGalleryAdapter(this,pictureList) ;
        myGallery.setAdapter(myGalleryAdapter);
        myGallery.setSelection(1);   // 默认选中第一项，跳过第0项
    }

    @Override
    public void onLoadMore() {
        // 在这里处理加载更多的事件
        taskThread(0);
    }

    @Override
    public void onRefresh() {
       // 在这里处理刷新的事件
        taskThread(1);
    }

    /**
     * 一般新开一个线程用于加载或刷新操作
     * @param type  这里的type用于区分是刷新还是加载操作
     */
    private void taskThread(final int type){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("CJT","这里可以新建加载或刷新的任务");
                getLoad(type); // 这里是模拟加载更多的任务
            }
        }).start();
    }

    // 模拟获取数据的方法，这里一般是网络请求加载数据
    private void getLoad(int type){
        if(type == 0){ // 加载操作
            mHandler.sendEmptyMessage(0x01); // 加载完毕之后，发送消息，更新界面

            // 这里模拟加载更多数据
            // 准备数据
            PictureBean bean ;
            for (int i = 1; i <= 4 ; i++) {
                bean = new PictureBean();
                int picResId = this.getResources().getIdentifier("m"+i,"mipmap",getPackageName());
                bean.setPictureResId(picResId);
                bean.setPictureName("美女--"+i);
                pictureList.add(bean);
            }
        }else{
            mHandler.sendEmptyMessage(0x02); // 刷新完毕之后，发送消息，更新界面
        }
    }

    // 一般使用Handler来更新界面
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x01){
                // 更新界面
                if(myGalleryAdapter != null )
                    myGalleryAdapter.notifyDataSetChanged();
            }else  if(msg.what == 0x02){
                // 更新界面

            }
            myGallery.onCompleted(); // 同时调用加载结束的回调函数，停止转圈圈
        }
    };
}
