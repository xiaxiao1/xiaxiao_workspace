package com.example.administrator.retrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.ResponseBody;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    String API = "https://api.github.com";  // BASE URL
    String realUrl = "http://chuansong.me/account/daaimaomikong?start=";
    TextView tv;
    List<Article> articles = new CopyOnWriteArrayList<>();
    int totalPage=10;
    int currentPage=0;
    int ListSize=0;
    int currentListSize=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                work(0);
                parseArticlepage("http://chuansong.me/n/1745724245630");
            }
        });




    }

    public void work(final int  pageIndex) {
        message("正在处理第 "+pageIndex+" 个列表页");
        Retrofit retrofit =new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create()).build();
        gitapi service = retrofit.create(gitapi.class);
        Call<ResponseBody> model = service.profilePicture(realUrl+(pageIndex*12));
        model.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    if (response==null||response.body()==null) {
                        message("------------------------------------第 "+pageIndex+" 个列表页出错");
                        checkListSize();
                        return;
                    }
                    String html=new String(response.body().bytes());
                    tv.setText("OK");
                    Document doc = Jsoup.parse(html);
                    Elements elements=doc.select("div.feed_body").first().select("div.pagedlist_item");
                    ListSize=elements.size();
                    currentListSize=0;
                    for (Element e:elements) {
                        Article a = new Article();
                        Element eName = e.select("a.question_link").first();
                        Element eUrl = e.select("a.tb").first();
                        a.setName(eName.text());
                        String dtr=eUrl.attr("onclick");
                        dtr = dtr.substring(dtr.indexOf("?url=") + 5, dtr.indexOf("&"));
                        a.setUrl(dtr);
                        articles.add(a);
                        message("完成第 "+currentListSize+" / "+ListSize+"  个");
                        checkListSize();
                    }
                    /*for (int i=0;i<articles.size();i++) {
                        if (articles.get(i)!=null) {
                            Log.i("xx","item value:"+(i+1)+articles.get(i).toString());
                        }
                    }*/
                } catch (IOException e) {
                    e.printStackTrace();
                    checkListSize();

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("xx",""+t.getMessage());
                currentListSize=ListSize-1;
            }
        });
    }

    public void parseArticlepage(final String pageUrl) {
        message("正在处理 "+pageUrl);
        Retrofit retrofit =new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create()).build();
        gitapi service = retrofit.create(gitapi.class);
        Call<ResponseBody> model = service.profilePicture(pageUrl);
        model.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    if (response==null||response.body()==null) {
                        message("------------------------------------"+pageUrl+" 个页出错");
//                        checkListSize();
                        return;
                    }
                    String html=new String(response.body().bytes());
                    tv.setText(html);
                    ArticleInfo articleInfo = new ArticleInfo();
                    articleInfo.setUrl(pageUrl);
                    Document doc = Jsoup.parse(html);
                    Elements elements=doc.select("div.rich_media_content").first().getElementsByTag("span");

                    for (Element e:elements) { //e 是span标签
                        Element img=e.getElementsByTag("img").first();
                        if (img!=null) {
                            articleInfo.addInfoItem(img.attr("src"));
                        } else if (e.text()!=null&&e.text().trim().length()!=0) {
                            articleInfo.addInfoItem(e.text());
                        }

//                        message(e.tagName());
                        /*Article a = new Article();
                        Element eName = e.select("a.question_link").first();
                        Element eUrl = e.select("a.tb").first();
                        a.setName(eName.text());
                        String dtr=eUrl.attr("onclick");
                        dtr = dtr.substring(dtr.indexOf("?url=") + 5, dtr.indexOf("&"));
                        a.setUrl(dtr);
                        articles.add(a);
                        message("完成第 "+currentListSize+" / "+ListSize+"  个");
                        checkListSize();*/
                    }
                    List l = articleInfo.getDatas();
                    for (int i=0;i<l.size();i++) {
                        message(l.get(i).toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    checkListSize();

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("xx",""+t.getMessage());
                currentListSize=ListSize-1;
            }
        });
    }

    public void checkListSize() {
        synchronized (this) {
            currentListSize++;
            if (currentListSize>=ListSize) {
                currentPage++;
                if (currentPage <= totalPage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    work(currentPage);
                } else {
                    message("total over.");
                    for (int i=0;i<articles.size();i++) {
                        if (articles.get(i)!=null) {
                            message((i+1)+articles.get(i).toString());
                        }
                    }
                }
            }

        }
    }

    public void message(String mes) {
        Log.i("xx",mes);
    }
}
