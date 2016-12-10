package com.xiaxiao.rxandroid_test;

import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/6.
 */
public class HttpUtil {
    private RetrofitService retrofitService;
    private static HttpUtil httpUtil;

    private  HttpUtil(RetrofitService retrofitService) {
       this.retrofitService = retrofitService;
    }



    public static class Builder{
        //    超时时间
        private static long TIME_OUT = 20;
        String url;
        Map<String ,Integer> params;
        String time;

        public Builder url(String url) {
            this.url=url;
            return this;
        }

        public Builder params(Map<String, Integer> params) {
            this.params = params;
            return this;
        }

        public Builder params(String key, Integer value) {
            if (this.params==null) {
                params = new HashMap<>();
            }
            this.params.put(key, value);
            return this;
        }

        public Builder cacheTime(String time) {
            this.time = time;
            return this;
        }

        public Builder build() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(getUnsafeOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    //retrofit和rxandroid之间存在版本匹配问题  一定要加下面这句
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            RetrofitService retrofitHelper = retrofit.create(RetrofitService.class);
            httpUtil=new HttpUtil(retrofitHelper);
            return this;
        }

        public Observable<LSMyActivity> get() {
            return httpUtil.retrofitService.Obget(url,params,time)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

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

    }

}
