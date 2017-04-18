package com.example.administrator.retrofit2;

import android.util.Log;
import android.widget.TextView;

import com.squareup.okhttp.ResponseBody;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    String API = "https://api.github.com";  // BASE URL
    String realUrl = "http://chuansong.me/account/daaimaomikong?start=";
    List<Article> articles = new CopyOnWriteArrayList<>();

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        for (int page=9;page<10;page++) {
            System.out.print("第"+page+"个列表页。。");
            work(realUrl+(page*12));
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void work(String articleListUrl) {
        Retrofit retrofit =new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create()).build();
        gitapi service = retrofit.create(gitapi.class);
        Call<ResponseBody> model = service.profilePicture(articleListUrl);
        model.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String html=new String(response.body().bytes());
                    Document doc = Jsoup.parse(html);
                    Elements elements=doc.select("div.feed_body").first().select("div.pagedlist_item");
                    for (int ii=0;ii<elements.size();ii++) {
                        Element e=elements.get(ii);
                        System.out.println("正在处理第 "+ii+" /"+elements.size());
                        Article a = new Article();
                        Element eName = e.select("a.question_link").first();
                        Element eUrl = e.select("a.tb").first();
                        a.setName(eName.text());
                        String dtr=eUrl.attr("onclick");
                        dtr = dtr.substring(dtr.indexOf("?url=") + 5, dtr.indexOf("&"));
                        a.setUrl(dtr);
                        articles.add(a);
                        Log.i("xx","item value:"+a.toString());
                    }
                    /*for (int i=0;i<articles.size();i++) {
                        if (articles.get(i)!=null) {

                        }
                    }*/
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("xx",""+response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("xx",""+t.getMessage());
            }
        });
    }
}