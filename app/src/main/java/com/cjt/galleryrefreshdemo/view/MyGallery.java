package com.cjt.galleryrefreshdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

import com.cjt.galleryrefreshdemo.listener.IGalleryEventListener;

/**
 * Created by CaoJiangtao on 2017/2/6.
 * 自定义的Gallery控件，集成了刷新和加载的功能
 */

public class MyGallery extends Gallery implements android.widget.AdapterView.OnItemSelectedListener{

    private IGalleryEventListener mIGalleryEventListener; // 监听Gallery滑动
    private View refreshView ; // 刷新的缓冲界面
    private View loadMoreView ; // 加载更多缓冲的界面

    private int startIndex = 0 ; // 记录首项的索引
    private int endIndex = 0 ; // 记录最后一项的索引

    public MyGallery(Context context) {
        super(context);
        this.setOnItemSelectedListener(this); // 设置监听器
    }

    public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnItemSelectedListener(this); // 设置监听器
    }

    public MyGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnItemSelectedListener(this); // 设置监听器
    }

    /**
     * 对外提供设置监听器的方法
     * @param iGalleryEventListener Gallery的滑动监听器
     */
    public void setGalleryEventListerner(IGalleryEventListener iGalleryEventListener){
        this.mIGalleryEventListener = iGalleryEventListener;
    }

    /**
     * 刷新或加载结束的回调方法
     */
    public void onCompleted(){
        if(refreshView != null && refreshView.isShown()){
            refreshView.setVisibility(View.GONE); // 刷新页面不可见
            this.setSelection(startIndex+1); // 选中刷新页面的后一项
        }

        if(loadMoreView != null && loadMoreView.isShown()){
            loadMoreView.setVisibility(View.GONE); // 加载更多页面不可见
            if(this.getChildAt(endIndex) != null ) this.setSelection(endIndex) ; // 有数据就选中刚刚加载的一项
            else this.setSelection(endIndex-1); // 否则就选中加载更多页面的前面一项
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        startIndex = 0 ; // 首项
        endIndex = adapterView.getCount() - 1 ; // 最后一项

        if(position == startIndex ){ // 表示滑动到了最前面一页，刷新操作
            refreshView = view ; // 刷新页面保存
            if(!refreshView.isShown()) refreshView.setVisibility(View.VISIBLE);
            mIGalleryEventListener.onRefresh(); // 回调刷新的方法
        }

        if(position == endIndex){ // 表示滑动到了最后一页，加载操作
            loadMoreView = view ;
            if(!loadMoreView.isShown()) loadMoreView.setVisibility(View.VISIBLE);
            mIGalleryEventListener.onLoadMore(); // 回调加载的方法
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
