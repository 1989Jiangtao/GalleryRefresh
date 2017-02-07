package com.cjt.galleryrefreshdemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjt.galleryrefreshdemo.R;
import com.cjt.galleryrefreshdemo.bean.PictureBean;

import java.util.List;

/**
 * Created by CaoJiangtao on 2017/2/6.
 *  自定义的Gallery适配器
 */

public class MyGalleryAdapter extends BaseAdapter {

    private Context mContext ; // 运行的上下文
    private List<PictureBean> mPictureList ; // 实体类数据集合
    private LayoutInflater mInfalter ; //  界面渲染器
    private View refreshView, loadMoreView; // 一头一尾

    public MyGalleryAdapter(){
    }

    /**
     * 构造方法
     * @param context 上下文
     * @param pictureList  实体类数据集合
     */
    public MyGalleryAdapter(Context context , List<PictureBean> pictureList){
        this.mContext = context ;
        this.mPictureList = pictureList ;
    }

    @Override
    public int getCount() {
        return mPictureList.size() + 2; // 这里多加了两个是因为我们多加了两个界面，分别是加载和刷新的界面，在数据集合中一头一尾
    }

    @Override
    public PictureBean getItem(int position) {
        if(position==0 || position== getCount()-1) return null;
        return mPictureList.get(position-1) ;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        mInfalter = LayoutInflater.from(mContext);
        if(position == 0 ){ // 第一页
            refreshView = mInfalter.inflate(R.layout.refresh_layout ,null);
            refreshView.setVisibility(View.GONE); // 默认设置刷新页面不可见
            return refreshView; // 返回刷新页面
        }else if(position == this.getCount() - 1){ //  最后一页
            loadMoreView = mInfalter.inflate(R.layout.loadmore_layout ,null);
            loadMoreView.setVisibility(View.GONE); // 默认设置加载更多不可见
            return loadMoreView; // 返回加载更多页面
        }else{
            if(view == null) view = mInfalter.inflate(R.layout.content_layout, null);
            ImageView imageView = ViewHolder.get(view,R.id.image);
            TextView describeTv = ViewHolder.get(view,R.id.describeTv);
            imageView.setImageResource(getItem(position).getPictureResId()); // 设置相片资源ID
            describeTv.setText(getItem(position).getPictureName());  // 设置相片名称
            return view ; // 返回内容页面
        }
    }
}
