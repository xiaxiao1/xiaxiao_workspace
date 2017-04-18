package com.example.administrator.retrofit2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by xiaxiao on 2017/4/18.
 */

public class ArticleInfo {
    String url;
    String title;
    List<Object> datas;

    public ArticleInfo() {
        datas = new CopyOnWriteArrayList<>();
    }
    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void addInfoItem(Object object) {
        this.datas.add(object);
    }
    @Override
    public String toString() {
        return "ArticleInfo{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", datas=" + datas +
                '}';
    }
}
