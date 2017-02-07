package com.cjt.galleryrefreshdemo.bean;

/**
 * Created by CaoJiangtao on 2017/2/6.
 *  测试用的实体类，这里包含一张图片和图片的名称
 */

public class PictureBean {
    private int pictureResId ; // 图片资源的ID
    private String pictureName ; // 图片的名称描述

    public int getPictureResId() {
        return pictureResId;
    }

    public void setPictureResId(int pictureResId) {
        this.pictureResId = pictureResId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "pictureResId=" + pictureResId +
                ", pictureName='" + pictureName + '\'' +
                '}';
    }
}
