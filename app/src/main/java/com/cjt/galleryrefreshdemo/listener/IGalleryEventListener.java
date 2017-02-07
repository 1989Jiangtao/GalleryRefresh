package com.cjt.galleryrefreshdemo.listener;

/**
 * Created by CaoJiangtao on 2017/2/6.
 *  监听Gallery滑动事件的接口
 */

public interface IGalleryEventListener {
    public void onLoadMore(); // 加载更多的回调函数
    public void onRefresh(); // 刷新的回调函数
}
