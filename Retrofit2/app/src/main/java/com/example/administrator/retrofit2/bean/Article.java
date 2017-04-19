package com.example.administrator.retrofit2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxiao on 2017/4/18.
 */

public class Article extends BmobObject{
    String name;
    String url;
    String publishTime;
    MyUser writer;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyUser getWriter() {
        return writer;
    }

    public void setWriter(MyUser writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}