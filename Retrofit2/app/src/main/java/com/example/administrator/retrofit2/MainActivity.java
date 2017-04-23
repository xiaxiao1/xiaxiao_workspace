package com.example.administrator.retrofit2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.retrofit2.bean.Article;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobListener;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobServer;
import com.example.administrator.retrofit2.thirdframework.retrofit.gitapi;
import com.example.administrator.retrofit2.util.RuntimePermissionsManager;

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
import cn.bmob.v3.exception.BmobException;
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<Article> saveArticles= new CopyOnWriteArrayList<>();
    public int pieceSize;
    int totalitems;
    public int pieceNum=0;
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
//    OkHttpClient client;
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
//        addHeader();
     /*   t_retrofit =new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(addHeader())
                .build();
        t_service = t_retrofit.create(gitapi.class);*/
        bmobServer = new BmobServer.Builder(getApplicationContext()).enableDialog(false).build();
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Activity2.class));
            }
        });
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



           /*     new Thread(new Runnable() {
                    @Override
                    public void run() {
                        queryArticles();
                    }
                }).start();*/

//                editArticles();
            }
        });




    }
/*

    public void editArticles() {
        bmobServer.getArticles(pieceNum,new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                alist=(List<Article>)object;
                for (Article f:alist) {
                    if (f.getContents() .length()>1) {
                        f.setHavecontent(1);
                    } else {
                        f.setHavecontent(0);
                    }
                    saveArticles.add(f);
                }
                if (alist.size() > 0) {
                    pieceNum++;
                    editArticles();
                } else {
//                    saveAsCSV(saveArticles);
                    updateArticles(saveArticles);
                }


                message("---------------------------------------------------------------请求成功，开始解析文章内容");

            }

            @Override
            public void onError(BmobException e) {
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
            */
/*@Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }*//*


           */
/* @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }*//*


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("xx",""+t.getMessage());
                currentListSize=ListSize-1;
                errorPageIndex.add(pageIndex + 1);
            }
        });
    }

    Retrofit t_retrofit;
    gitapi t_service ;
    public void parseArticlepage(final Article article) {
        final String pageUrl=article.getUrl();
        Call<ResponseBody> model = t_service.profilePicture(pageUrl);

        model.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (response==null||response.body()==null) {
                        message("------------------------------------------------------------------"+pageUrl+" 页出错  "+response.errorBody().string()+response.code());
//                        errorArticles.add(article);
//                        checkArticleList();
                        checkPieceSize();
                        return;
                    }
                    String html=new String(response.body().bytes());
//                    tv.setText(html);
                    */
/*ArticleInfo articleInfo = new ArticleInfo();
                    articleInfo.setArticle(article);
                    articleInfo.setUrl(pageUrl);*//*

                    Document doc = Jsoup.parse(html);
                    Elements elements=doc.select("div.rich_media_content").first().getElementsByAttributeValue("style","text-align: center;");
                    if (elements.size()==0) {
                        elements=doc.select("div.rich_media_content").first().getElementsByTag("p");
                    }

                    StringBuffer stringBuffer = new StringBuffer();
                    for (Element e:elements) { //e 是p标签
                        Elements imgs=e.getElementsByTag("img");
                        Elements texts=e.getElementsByTag("span");
                        for (Element text:texts) {
                            if (text.text()!=null&&text.text().trim().length()!=0) {
//                                articleInfo.addInfoItem(text.text());
                                stringBuffer.append(text.text());
                                stringBuffer.append("####");
                            }
                        }
                        if (!imgs.isEmpty()) {
                            for (Element img:imgs) {
//                                articleInfo.addInfoItem(img.attr("src"));
                                stringBuffer.append(img.attr("src"));
                                stringBuffer.append("####");
                            }
                        }

                    }
//                    List l = articleInfo.getDatas();
//
//                    for (int i=0;i<l.size();i++) {
//                        String item=(String)l.get(i);
//                        stringBuffer.append(item);
//                        stringBuffer.append("####");
////                        message(item);
//                    }
//                    articleInfo.setContents(stringBuffer.toString());
                    message("解析文章内容完成，开始存入bmob");
                    article.setContents(stringBuffer.toString());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bmobServer.updateArticle(article, new BmobListener() {
                        @Override
                        public void onSuccess(Object object) {
                            message("文章存入bmob成功，");
                            checkPieceSize();
                        }

                        @Override
                        public void onError(BmobException e) {
                            checkPieceSize();
                        }
                    });
//                    checkArticleList();
                } catch (Exception e) {
                    e.printStackTrace();
//                    checkArticleList();
                    checkPieceSize();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("xx",""+t.getMessage());
//                currentListSize=ListSize-1;
//                checkArticleList();
//                errorArticles.add(article);
                checkPieceSize();
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

                    */
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
                    message("分批上传全部完成");*//*


                    */
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
                    }*//*


                    saveAsCSV(articles);

                   */
/* message("----------------------------------------------------------------开始解析第 "+currentArticleIndex+" / "+articles.size()+"  个文章");

                    parseArticlepage(articles.get(0));*//*

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

    */
/*Runnable a=new Runnable(){
        @Override
        public void run() {

        }
    };*//*

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


    public void updateArticles(List<Article> list) {
        for (Article aa:list) {
            bmobServer.updateArticle(aa);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void saveAsCSV(List<Article> list) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("name,url,contents,havecontent\r\n");
        for(Article a:list){
            buffer.append(a.getName()+","+a.getUrl()+a.getContents()+a.getHavecontent()+"\r\n");
        }
        try {
              String data =new String(buffer.toString().getBytes("utf-8"), "utf-8") ;
//            String data = buffer.toString();

            String filename = "用户名单_qwqw.csv";

            */
/*String path = Environment.getExternalStorageDirectory()+"/Users/";
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }*//*

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


    List<Article> alist;
    public void queryArticles() {
        message("----------------------------------------------------------------------请求文章列表 "+pieceNum);
        bmobServer.getArticles(pieceNum,new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                alist=(List<Article>)object;
                List<Article> tempList=(List<Article>)object;
                */
/*for (Article f:alist) {
                    if (f.getContents()!=null) {
                        tempList.add(f);
                    }
                }
                alist=tempList;*//*

                pieceSize=alist.size();
                if (alist.size()==0) {
                    message("---------------------------------------------------------------本次结束 ，共操作条数"+totalitems);
                    ring(true);
                    return;
                }
                totalitems=totalitems+pieceSize;
                message("---------------------------------------------------------------请求成功，开始解析文章内容 "+pieceNum);
                for (int i=0;i<alist.size();i++) {
//                    message(list.get(i).toString());

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    message("-------------------------------------------------------------------------------正在处理 "+alist.get(i).getUrl()+"  "+i+" : "+pieceNum);
                        parseArticlepage(alist.get(i));
                }
            }

            @Override
            public void onError(BmobException e) {
                queryArticles();
            }
        });
    }

    public void checkPieceSize() {
        synchronized (this) {
            pieceSize--;
            if (pieceSize==0) {
                message("本批次全部完成，启动下一轮查询");
                alist.clear();
                pieceNum++;
                queryArticles();
            }
        }
    }


    public OkHttpClient addHeader() {


               OkHttpClient httpClient = new OkHttpClient.Builder()

                      .addInterceptor(new Interceptor() {

                              @Override

                              public okhttp3.Response intercept(Chain chain) throws IOException {

                                      Request request = chain.request()

                                               .newBuilder()

                                              .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")

                                              .addHeader("Accept-Encoding", "gzip, deflate")

                                              .addHeader("Connection", "keep-alive")

                                              .addHeader("Accept", "*
/*")

                                              .addHeader("Cookie", "add cookies here")

                                              .build();

                                      return chain.proceed(request);

                                  }



                                })

                         .build();

                 return httpClient;

                 }


*/



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
}
