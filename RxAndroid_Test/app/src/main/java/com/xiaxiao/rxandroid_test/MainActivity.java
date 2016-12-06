package com.xiaxiao.rxandroid_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    int f3=10;
    TextView tv;
    Button btn;
    //    超时时间
    private static long TIME_OUT = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "lalala", Toast.LENGTH_SHORT).show();
                function8();
            }
        });
//        function3();


    }
    public void function8() {
        int[] i=new int[]{1,2,3,4,5,6,7,8,9};
        Observable.just(i)
                .map(new Func1<int[], int[]>() {
                    @Override
                    public int[] call(int[] ints) {
                        for (int u:ints) {
                            if (u%2==0) {
                                u=0;
                            }
                        }
                        return ints;
                    }

                })
                .map(new Func1<int[], String>() {
                    @Override
                    public String call(int[] ints) {
                        String s="";
                        for (int t:ints) {
                            s=s+t;
                        }
                        return s;
                    }

                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("xx",s);
                        tv.setText(s);

                    }
                });
    }

    public void function7(){
        new HttpUtil.Builder()
                .url("https://apis.lis99.com/activity/order_detail/")
                .params("453143",19165)
                .build()
                .get()
                //线程切换也封装起来了
                .subscribe(new Subscriber<LSMyActivity>(){
                    @Override
                    public void onCompleted() {
                        Log.i("xx","compelte");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("xx",e.toString());
                    }

                    @Override
                    public void onNext(LSMyActivity lsMyActivity) {
                        Log.i("xx",lsMyActivity.toString());
                    }
                });
        /*new Action1<LSMyActivity>() {
                    @Override
                    public void call(LSMyActivity lsMyActivity) {
                        Log.i("xx",lsMyActivity.toString());
                    }
                }*/
    }

    public void function6() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.tngou.net")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RetrofitService retrofitHelper = retrofit.create(RetrofitService.class);
        retrofitHelper.getClassifyBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose()
                .subscribe(new Subscriber<ClassifyBean>() {
                    @Override
                    public void onCompleted() {
                        Log.i("xx","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("xx",e.toString());

                    }

                    @Override
                    public void onNext(ClassifyBean s) {
                        Log.i("xx",s.toString());
                        tv.setText(s.toString());
                    }
                });
    }

    public void function5() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apis.lis99.com")
                .addConverterFactory(GsonConverterFactory.create())
                //retrofit和rxandroid之间存在版本匹配问题  一定要加下面这句
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RetrofitService retrofitHelper = retrofit.create(RetrofitService.class);
       final Call<String> call= retrofitHelper.getResult2("453143", 19165);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<String> s=call.execute();
                    tv.setText(s.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }){}.start();


    }
    public void function4() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apis.lis99.com")
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                //retrofit和rxandroid之间存在版本匹配问题  一定要加下面这句
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RetrofitService retrofitHelper = retrofit.create(RetrofitService.class);
        retrofitHelper.getResult("453143",19165)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(/*new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("xx",s);
                        tv.setText(s);
                    }
                }*/
                        new Subscriber<LSMyActivity>() {
                            @Override
                            public void onCompleted() {
                                Log.i("xx","onCompleted");
//                                tv.setText(s);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("xx","onError"+e.getMessage());
                            }

                            @Override
                            public void onNext(LSMyActivity s) {
                                Log.i("xx",s.toString());
                                tv.setText(s.toString());
                            }
                        }
                );
    }
    public void function3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (f3>0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(f3+"");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    f3--;
                }

            }
        }){}.start();
    }
    public String getString() {
        return System.currentTimeMillis() + "";
    }

    public void function2() {
        String s[]=new String[]{"a","a2","a3","a4","a5","a6","a7"};
        Observable.from(s)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("xx",s);
                    }
                });

    }

    public void function1() {
         /*Full<String> f = new Full<String>();
        f.requset(12121);*/

        //创建被观察者
        Observable.OnSubscribe<String> onSubscribe1=new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i("xx", "call");
                subscriber.onNext(getString());
                subscriber.onNext("world");
                subscriber.onNext(" come on!");
                subscriber.onCompleted();
            }

        };

        //创建订阅者
        Subscriber<String> subscriber2 = new Subscriber<String>(){
            @Override
            public void onCompleted(){
                Log.i("xx", "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.i("xx", "onError");

            }

            @Override
            public void onNext(String s) {
                Log.i("xx", "onNext :"+s);
            }
        };
//        subscriber2.isUnsubscribed()

        Observable.create(onSubscribe1)
                .map(new Func1<String,String>(){
                    @Override
                    public String call(String s) {
                        return s + " this is map";
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber2);
    }


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
            builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);

//            builder.addInterceptor(getLog());

            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*private static HttpLoggingInterceptor getLog()
    {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return loggingInterceptor;
    }*/
}
