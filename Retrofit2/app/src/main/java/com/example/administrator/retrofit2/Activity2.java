package com.example.administrator.retrofit2;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.retrofit2.bean.Article;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobListener;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobServer;
import com.example.administrator.retrofit2.thirdframework.glide.GlideHelper;
import com.example.administrator.retrofit2.thirdframework.glide.OnGlideListener;

import cn.bmob.v3.exception.BmobException;

public class Activity2 extends AppCompatActivity {

    public static final int TYPE_GIF=0;
    public static final int TYPE_JPEG=1;
    public static final int TYPE_JPG=2;
    public static final int TYPE_PNG=3;
    ImageView img;
    LinearLayout root;
    BmobServer bmobServer;
    String tempUrl="http://mmbiz.qpic.cn/mmbiz/8TF87KCnib2324aMZgicYnjReiakkCiaVZ4CicMa46xNmkN36fHiaXGWqIo8WNnCkvqSibyMv3zpLoED1jH2Y9w1tXVsQ/0?wx_fmt=jpeg";
    String gifUrl="http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=http://mmbiz.qpic.cn/mmbiz/8TF87KCnib21yqzNnvzicfVI3VHKQPmjChJYL3yhCaFiaw2N49nRbb7ibEYrcofW0Tq9oYfQib60hlExFobaBzVmfoQ/640?wx_fmt=gif&wxfrom=5&wx_lazy=1&tp=webp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        img = (ImageView) findViewById(R.id.imgg);
        root = (LinearLayout) findViewById(R.id.root);
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.mipmap.ic_launcher);
        root.addView(imageView);
        bmobServer = new BmobServer.Builder(this).enableDialog(false).build();
        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmobServer.getArticle("168314e306", new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        loadPics((Article)object);
                    }

                    @Override
                    public void onError(BmobException e) {

                    }
                });
            }
        });
    }


    public void loadPics(Article article) {
        String[] items = article.getContents().split("####");
        for (int i=0;i< items.length;i++) {
            if (items[i].startsWith("http:")) {
                addImageView(i, items[i]);
            } else {
                addTextView(i,items[i]);
            }
        }
    }

    public void loadPicWithGlide(int index, String url, final ImageView img) {
        if (checkPicType(url) == TYPE_GIF) {
            GlideHelper.loadGifImage(this, url, img);
        } else {
            GlideHelper.loadImageAsBitmap(this, url, new OnGlideListener() {
                @Override
                public void onResourceReady(Bitmap resource, String model, Target<Bitmap> target,
                                            boolean isFromMemoryCache, boolean isFirstResource) {
                    img.setImageBitmap(resource);
                    message("loading pic done");
                }

                @Override
                public void onFailed(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                    message("load error:"+e.getMessage());
                }
            });
        }

        /*GlideHelper.loadImageAsBitmap(this, url, new OnGlideListener() {

            @Override
            public void onResourceReady(Bitmap resource, String model, Target<Bitmap> target,
                                        boolean isFromMemoryCache, boolean isFirstResource) {
                img.setImageBitmap(resource);
                message("ok ?:"+resource.getByteCount());
            }

            @Override
            public void onFailed(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                message(e.getMessage());
            }
        });*/
        /*Glide.with(this)
                .load(gifUrl)
                .asGif()
                .toBytes().listener(new RequestListener<String, byte[]>() {
            @Override
            public boolean onException(Exception e, String model, Target<byte[]> target, boolean
                    isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(byte[] resource, String model, Target<byte[]> target,
                                           boolean isFromMemoryCache, boolean isFirstResource) {
                message("okhdahdsdhasdasddsasad adsda  asda da ad a"+resource.length);
                return false;
            }
        })
                .into(img);*/
        message("okhdahdsdhasdasddsasad adsda  asda da ad a");
        /*GlideHelper.loadImage(this,
                "http://mmbiz.qpic.cn/mmbiz/8TF87KCnib2324aMZgicYnjReiakkCiaVZ4CicMa46xNmkN36fHiaXGWqIo8WNnCkvqSibyMv3zpLoED1jH2Y9w1tXVsQ/0?wx_fmt=jpeg",
                img);*/
    }

    public void message(String mes) {
        Log.i("xx",mes);
    }

    public int checkPicType(String url) {
        int i = url.indexOf("?wx_fmt=");
        String type = url.substring(i + 8, url.indexOf("#"));
        if (type.equals("gif")) {
            return TYPE_GIF;
        }
        else {
            return TYPE_PNG;
        }

    }

    public void addTextView(int index, String text) {
        message(index+text);
        TextView t = new TextView(this);
        t.setText(text);
        t.setTag(index);
        root.addView(t);
    }

    public void addImageView(int index, String url) {
        message(index+url);
        ImageView imageView = new ImageView(this);
        loadPicWithGlide(index,url,imageView);
        imageView.setTag(index);
        root.addView(imageView);
    }
}
