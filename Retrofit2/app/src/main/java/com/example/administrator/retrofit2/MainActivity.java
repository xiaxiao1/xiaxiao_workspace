package com.example.administrator.retrofit2;

import android.Manifest;
import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.retrofit2.bean.Article;
import com.example.administrator.retrofit2.bean.ArticleInfo;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobIniter;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobListener;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobServer;
import com.example.administrator.retrofit2.thirdframework.retrofit.gitapi;
import com.example.administrator.retrofit2.util.RuntimePermissionsManager;
import com.squareup.okhttp.ResponseBody;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    public RuntimePermissionsManager runtimePermissionsManager;
    List<Integer> errorPageIndex= new CopyOnWriteArrayList<>();
    List<Article> errorArticles= new CopyOnWriteArrayList<>();
    String API = "https://api.github.com";  // BASE URL
    String realUrl = "http://chuansong.me/account/daaimaomikong?start=";
    TextView tv;
    List<Article> articles = new CopyOnWriteArrayList<>();
    int totalPage=9;
    int currentPage=0;
    int ListSize=0;
    int currentListSize=0;
    int currentArticleIndex=0;
    BmobServer bmobServer;
    Uri notification ;
    Uri notification2 ;
    Ringtone r ;
    Ringtone r2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification2= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r2 = RingtoneManager.getRingtone(getApplicationContext(), notification2);
//        BmobIniter.init(this);

        Bmob.initialize(getApplicationContext(), "2f0b843f4fa2d170216ff309cc123300");
        bmobServer = new BmobServer.Builder(getApplicationContext()).enableDialog(false).build();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage=Integer.parseInt(((EditText)findViewById(R.id.tv_start)).getText().toString().trim());
                totalPage=Integer.parseInt(((EditText)findViewById(R.id.tv_end)).getText().toString().trim());
                getRuntimePermissionManager(MainActivity.this).requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                ring(true);
//                work(currentPage);
//                parseArticlepage("http://chuansong.me/n/1775574245332");
               /* Article a = new Article();
                a.setName("xiaxiao");
                a.setUrl("url http");
                bmobServer.addArticle(a, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        message("kakakakaakak   bmob");
                    }

                    @Override
                    public void onError(BmobException e) {

                    }
                });*/
                queryArticles();
            }
        });




    }

    public void work(final int  pageIndex) {
        message("--------------------------------------------------------------------正在处理第 "+pageIndex+"/"+totalPage+" 个列表页");
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitapi service = retrofit.create(gitapi.class);
        Call<ResponseBody> model = service.profilePicture(realUrl+(pageIndex*12));
        model.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    if (response==null||response.body()==null) {
                        message("----------------------------------------------------第 "+pageIndex+" 个列表页出错");
                        message("error: "+response.code());
                        errorPageIndex.add(pageIndex + 1);
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

                } catch (IOException e) {
                    e.printStackTrace();
                    checkListSize();

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("xx",""+t.getMessage());
                currentListSize=ListSize-1;
                errorPageIndex.add(pageIndex + 1);
            }
        });
    }

    public void parseArticlepage(final Article article) {
        final String pageUrl=article.getUrl();
        message("-------------------------------------------------------------------------------正在处理 "+pageUrl);
        Retrofit retrofit =new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create()).build();
        gitapi service = retrofit.create(gitapi.class);
        Call<ResponseBody> model = service.profilePicture(pageUrl);
        model.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    if (response==null||response.body()==null) {
                        message("------------------------------------------------------------------"+pageUrl+" 页出错");
                        errorArticles.add(article);
//                        checkArticleList();
                        return;
                    }
                    String html=new String(response.body().bytes());
//                    tv.setText(html);
                    ArticleInfo articleInfo = new ArticleInfo();
                    articleInfo.setArticle(article);
                    articleInfo.setUrl(pageUrl);
                    Document doc = Jsoup.parse(html);
                    Elements elements=doc.select("div.rich_media_content").first().getElementsByAttributeValue("style","text-align: center;");
                    if (elements.size()==0) {
                        elements=doc.select("div.rich_media_content").first().getElementsByTag("p");
                    }

                    for (Element e:elements) { //e 是p标签
                        Elements imgs=e.getElementsByTag("img");
                        Elements texts=e.getElementsByTag("span");
                        for (Element text:texts) {
                            if (text.text()!=null&&text.text().trim().length()!=0) {
                                articleInfo.addInfoItem(text.text());
                            }
                        }
                        if (!imgs.isEmpty()) {
                            for (Element img:imgs) {
                                articleInfo.addInfoItem(img.attr("src"));
                            }
                        }

                    }
                    List l = articleInfo.getDatas();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i=0;i<l.size();i++) {
                        String item=(String)l.get(i);
                        stringBuffer.append(item);
                        stringBuffer.append("####");
                        message(item);
                    }
                    articleInfo.setContents(stringBuffer.toString());
                    bmobServer.addArticleInfo(articleInfo, new BmobListener() {
                        @Override
                        public void onSuccess(Object object) {
                            message("save 2 bmob article info OK!");
                        }

                        @Override
                        public void onError(BmobException e) {

                        }
                    });
//                    checkArticleList();
                } catch (IOException e) {
                    e.printStackTrace();
//                    checkArticleList();

                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("xx",""+t.getMessage());
//                currentListSize=ListSize-1;
//                checkArticleList();
                errorArticles.add(article);
            }
        });
    }
    int num=0;
    public void checkListSize() {
        synchronized (this) {
            currentListSize++;
            if (currentListSize>=ListSize) {
                currentPage++;
                if (currentPage <= totalPage) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    work(currentPage);
                } else {
                    message("total over.");
                    message("开始上传文章标题到bmob");

                    /*final List<BmobObject> temps = new CopyOnWriteArrayList<>();
                    while (articles.size()>0) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (articles.size() > 40) {
                            for (int i = 0; i < 40; i++) {
                                temps.add(articles.get(i));
//                                articles.remove(i);
                            }
                        } else {
                            for (int i = 0; i <articles.size(); i++) {
                                temps.add(articles.get(i));
//                                articles.remove(i);
                            }
                        }
                        for (int i = 0; i <temps.size(); i++) {
                                articles.remove(i);
                        }
                        bmobServer.addArticles(temps, new BmobListener() {
                            @Override
                            public void onSuccess(Object object) {
                                message("正在上传"+((int)object)+"/"+temps.size());
                                if (((int)object)+1==temps.size()) {
                                    message("本次上传成功");
                                    temps.clear();
                                    ring(true);
                                }
                            }

                            @Override
                            public void onError(BmobException e) {
                                message("error:  "+e.getMessage());
                                temps.clear();
                            }
                        });
                    }
                    message("分批上传全部完成");*/

                    /*for (final Article aaa : articles) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        message("第 "+(num++)+" 个文章"+aaa.toString());
                        bmobServer.addArticle(aaa, new BmobListener() {
                            @Override
                            public void onSuccess(Object object) {
                                aaa.setObjectId((String)object);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv.setText("正在上传"+num+"/"+articles.size());

                                    }
                                });
                                ring(true);

                            }

                            @Override
                            public void onError(BmobException e) {
                                message("error: "+e.toString());
                            }
                        });
                    }*/

                    saveAsCSV(articles);

                   /* message("----------------------------------------------------------------开始解析第 "+currentArticleIndex+" / "+articles.size()+"  个文章");

                    parseArticlepage(articles.get(0));*/
                }
            }

        }
    }

    public void message(String mes) {
        Log.i("xx",mes);
    }

    public void checkArticleList() {
        currentArticleIndex++;
        if (currentArticleIndex < articles.size()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            message("---------------------------------------------------------开始解析第 "+currentArticleIndex+" / "+articles.size()+"  个文章");

            parseArticlepage((Article) articles.get(currentArticleIndex));
        } else {
            message(" ----------------------------------------------------------------------quan bu OKOKOK!");
            ring(false);
        }
    }

    /*Runnable a=new Runnable(){
        @Override
        public void run() {

        }
    };*/
    public void ring(final boolean onDoing) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                if (onDoing) {
                    r.play();
                } else {
                    r2.play();
                }
            }
        }).start();

    }


    public void saveAsCSV(List<Article> list) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("name,url\r\n");
        for(Article a:list){
            buffer.append(a.getName()+","+a.getUrl()+"\r\n");
        }
        try {
//              String data =new String(buffer.toString().getBytes("utf-8"), "ansi") ;
            String data = buffer.toString();

            String filename = "用户名单_qwqw.csv";

            /*String path = Environment.getExternalStorageDirectory()+"/Users/";
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }*/
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream out=new FileOutputStream(file);


            out.write(data.getBytes());
            out.close();
            Toast.makeText(getApplicationContext(), "文件导出成功！请到SD卡中查看",Toast.LENGTH_SHORT).show();
            message("文件导出成功！请到SD卡中查看");
            ring(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (runtimePermissionsManager!=null) {
            runtimePermissionsManager.handle(requestCode, permissions, grantResults);
        }
    }
    public RuntimePermissionsManager getRuntimePermissionManager(Activity activity) {
        if (runtimePermissionsManager==null) {
            runtimePermissionsManager = new RuntimePermissionsManager(activity);
        }
        return runtimePermissionsManager;
    }

    public void queryArticles() {
        bmobServer.getArticles(new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                List<Article> list=(List<Article>)object;
                for (int i=0;i<list.size();i++) {
//                    message(list.get(i).toString());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    parseArticlepage(list.get(i));
                }
            }

            @Override
            public void onError(BmobException e) {

            }
        });
    }
}
