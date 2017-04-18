package com.example.administrator.retrofit2;

/**
 * Created by xiaxiao on 2017/4/18.
 */

public class Article {
    String name;
    String url;
    String publishTime;


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

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
