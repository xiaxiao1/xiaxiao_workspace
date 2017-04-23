package com.example.administrator.retrofit2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.retrofit2.bean.Article;
import com.example.administrator.retrofit2.bean.Picture;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobListener;
import com.example.administrator.retrofit2.thirdframework.bmob.BmobServer;
import com.example.administrator.retrofit2.thirdframework.glide.GlideHelper;
import com.example.administrator.retrofit2.thirdframework.glide.OnGlideGIFListener;
import com.example.administrator.retrofit2.thirdframework.glide.OnGlidePNGListener;
import com.example.administrator.retrofit2.util.BitmapUtil;
import com.example.administrator.retrofit2.util.UIDialog;
import com.example.administrator.retrofit2.util.Util;
import com.example.administrator.retrofit2.bean.PicFile;

import java.io.BufferedOutputStream;
//import java.io.File;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
//import okhttp3.Headers;

public class Activity2 extends AppCompatActivity  {

    public static final int TYPE_GIF=0;
    public static final int TYPE_JPEG=1;
    public static final int TYPE_JPG=2;
    public static final int TYPE_PNG=3;
    ImageView img;
    LinearLayout root;
    BmobServer bmobServer;
    String tempUrl="http://mmbiz.qpic.cn/mmbiz/8TF87KCnib2324aMZgicYnjReiakkCiaVZ4CicMa46xNmkN36fHiaXGWqIo8WNnCkvqSibyMv3zpLoED1jH2Y9w1tXVsQ/0?wx_fmt=jpeg";
    String gifUrl="http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=http://mmbiz.qpic.cn/mmbiz/8TF87KCnib21yqzNnvzicfVI3VHKQPmjChJYL3yhCaFiaw2N49nRbb7ibEYrcofW0Tq9oYfQib60hlExFobaBzVmfoQ/640?wx_fmt=gif&wxfrom=5&wx_lazy=1&tp=webp";
    String[] results;
    List<Picture> pictureList = new CopyOnWriteArrayList<>();
    int requestTime;
    int picNum;
    Article theArticle;
    Button findBtn;
    Button updateBtn;
    UIDialog uiDialog;
    List<File> files = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        img = (ImageView) findViewById(R.id.imgg);
        root = (LinearLayout) findViewById(R.id.root);
        bmobServer = new BmobServer.Builder(this).enableDialog(false).build();
        updateBtn=((Button)findViewById(R.id.submit));
        findBtn=((Button)findViewById(R.id.btn));
        uiDialog = new UIDialog(this);
        updateBtn.setEnabled(false);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiDialog.showWaitDialog("正在更新文章，，");
                updateBtn.setEnabled(false);
                findBtn.setEnabled(false);
                picNum = pictureList.size();
                prepareArticle2();

            }
        });
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findBtn.setEnabled(false);
                uiDialog.showWaitDialog("正在加载原始文章");
                bmobServer.getArticle( new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        loadPics((Article)object);
//                        loadPicWithGlide(1,tempUrl,img);
                    }

                    @Override
                    public void onError(BmobException e) {
                        message("get a Article error: "+e.getMessage());
                        uiDialog.dismissWaitDialog();
                    }
                });
                /*GlideHelper.loadImageAsBitmap(Activity2.this, tempUrl, new OnGlideListener() {
                    @Override
                    public void onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        img.setImageBitmap(resource);
                    }

                    @Override
                    public void onFailed(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {

                    }
                });*/
//                GlideHelper.loadImage(Activity2.this,tempUrl,img);
            }
        });
    }


    public void loadPics(Article article) {
        theArticle = article;
        String[] items = article.getContents().split("####");
        requestTime=items.length;
        results = new String[items.length];
        for (int i=0;i< items.length;i++) {
            if (items[i].startsWith("http:")) {
                addImageView(i, items[i]);
                message(i+"img");
            } else {
                addTextView(i,items[i]);
                message(i+"text");
            }
        }
        message("add view done");
    }

    public void loadPicWithGlide(final int index, String url, final ImageView img) {
        if (checkPicType(url) == TYPE_GIF) {
            GlideHelper.loadGifImage(this, url, img, new OnGlideGIFListener() {
                @Override
                public void success(File gifFile) {
                    files.add(gifFile);
                    Picture picture = new Picture();
                    picture.setType(Picture.PICTURE_TYPE_GIF);
                    picture.setIndex(index);
                    picture.setGifFile(gifFile);
                    pictureList.add(picture);
                    isQuesetComplete();
                }

                @Override
                public void failed() {
                    img.setImageResource(R.drawable.error);
                    message("glide pload gif  pic error: ");
                    isQuesetComplete();
                }
            });
        } else {

//            GlideHelper.loadImage(this,url,img);
            GlideHelper.loadImageAsBitmap(this, url, img, new OnGlidePNGListener() {
                @Override
                public void success(Bitmap bitmap) {
//                    saveBitmapFile(bitmap);
                    Picture picture = new Picture();
                    picture.setType(Picture.PICTURE_TYPE_PNG);
                    picture.setIndex(index);
                    picture.setPngBitmap(bitmap);
                    pictureList.add(picture);
                    isQuesetComplete();
                }

                @Override
                public void failed() {
                    isQuesetComplete();
                    message("glide pload png pic error: ");
                }
            });
        }

    }

    public void message(String mes) {
        Log.i("xx",mes);
    }

    public int checkPicType(String url) {
        int i = url.indexOf("?wx_fmt=");
        String t_str = url.substring(i);
        String type=null;
        if (t_str.indexOf("&") == -1) {
            type = t_str.substring( 8);
        } else {
            type = t_str.substring( 8, t_str.indexOf("&"));
        }
        if (type.equals("gif")) {
            return TYPE_GIF;
        }
        else {
            return TYPE_PNG;
        }

    }

    public void addTextView(final int index, String text) {
//        message(index+text);
        final TextView t = new TextView(this);
        t.setText(text);
        t.setTag(index);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteText(index);
                root.removeView(t);
            }
        });
        root.addView(t);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)t.getLayoutParams();
        params.setMargins(0,10,0,10);
        t.setLayoutParams(params);
        t.setPadding(0,20,0,20);
        t.setBackgroundResource(R.drawable.text_bg);
        results[index]=text;
        isQuesetComplete();
    }

    public void addImageView(final int index, String url) {
//        message(index+url);
        final ImageView imageView = new ImageView(this);
        loadPicWithGlide(index,url,imageView);
//        imageView.setTag(index);
        root.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePic(index);
                root.removeView(imageView);
            }
        });
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imageView.getLayoutParams();
        params.setMargins(0,20,0,20);
        imageView.setLayoutParams(params);
    }













    public File saveBitmapFile(Bitmap bitmap){
        File file=new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+"ss.png");//将要保存图片的路径

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  file;
    }

    public File saveBitmapFile2(Bitmap bitmap) {
        if (bitmap==null) {
            return null;
        }
        File f = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+"sss.png");
        if (!f.exists()) {
            try {
                f.createNewFile();
                OutputStream out= null;
                out = new FileOutputStream(f);
                out.write(BitmapUtil.Bitmap2Bytes(bitmap));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                Util.L("save file error:"+e.getMessage());
            }
        }

        return f;
    }

    public void isQuesetComplete() {
        synchronized (this) {
            requestTime--;
            if (requestTime==0) {
                message("quest ok !");
                updateBtn.setEnabled(true);
                uiDialog.dismissWaitDialog();
            }
        }
    }


    public void deleteText(int index) {
        results[index]="";
    }

    public void deletePic(int index) {
        for (Picture picture:pictureList) {
            if (picture.getIndex()==index) {
                pictureList.remove(picture);
                return;
            }
        }
    }


    public void prepareArticle() {

        for (Picture picture:pictureList) {
            File f;
            final int index = picture.getIndex();
            if (picture.getType() == Picture.PICTURE_TYPE_GIF) {
                f = picture.getGifFile();
            } else {
                f = saveBitmapFile(picture.getPngBitmap());
                files.add(f);
            }

            bmobServer.upFile(f, new Integer(index),new BmobListener() {
                int ii=index;
                @Override
                public void onSuccess(Object object) {
                    BmobFile bmobFile = (BmobFile) object;
                    results[ii] = bmobFile.getFileUrl();
                    message("上传成功一张图片："+ii+"   url:"+bmobFile.getFileUrl());
                    isPicUploadComplete();
                    message("upload pic ok");
                }

                @Override
                public void onError(BmobException e) {
                    results[index]="";
                    message("upload pic error: "+e.getMessage());
                    isPicUploadComplete();
                }
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void prepareArticle2() {
//        picNum = pictureList.size();
//        for (Picture picture:pictureList) {
        if (pictureList.size()==0) {
            updateArticle();
            return;
        }
        Picture picture = pictureList.get(0);
            File f;
            final int index = picture.getIndex();
            if (picture.getType() == Picture.PICTURE_TYPE_GIF) {
                f = picture.getGifFile();
            } else {
                f = saveBitmapFile(picture.getPngBitmap());
            }

            bmobServer.upFile(f, new Integer(index),new BmobListener() {
                int ii=index;
                @Override
                public void onSuccess(Object object) {
                    BmobFile bmobFile = (BmobFile) object;
                    results[ii] = bmobFile.getFileUrl();
                    message("上传成功一张图片："+ii+"   url:"+bmobFile.getFileUrl());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    isPicUploadComplete();
                    message("upload pic ok");
                }

                @Override
                public void onError(BmobException e) {
                    results[index]="";
                    message("upload pic error: "+e.getMessage());
                    isPicUploadComplete();
                }
            });

//        }
    }

    public void isPicUploadComplete() {
        synchronized (this) {
            picNum--;
            pictureList.remove(0);
            message("check picnum :"+picNum);
            if (picNum == 0) {
                updateArticle();
            } else {
                prepareArticle2();
            }
        }
    }

    public void updateArticle() {

        StringBuffer stringBuffer = new StringBuffer();
        for (String s:results) {
            if (s!=null&&!s.equals("")) {
                stringBuffer.append(s);
                stringBuffer.append("####");
            }
        }
        theArticle.setHavecontent(2);
        if (pictureList.size()<1) {
            theArticle.setHavecontent(3);
        }
        for (File f:files) {
            f.delete();
        }
        files.clear();
        theArticle.setMainContent(stringBuffer.toString());
        message(theArticle.getMainContent());
        bmobServer.updateArticle(theArticle, new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                message("update article ok!");
                uiDialog.dismissWaitDialog();
                Util.toast(Activity2.this,"本条操作完成，下一条");
                updateBtn.setEnabled(false);
                findBtn.setEnabled(true);
                root.removeAllViews();
            }

            @Override
            public void onError(BmobException e) {
                message("update article erro!"+e.getMessage());
                Util.toast(Activity2.this,"本条操作完成，下一条");
                updateBtn.setEnabled(false);
                findBtn.setEnabled(true);
                root.removeAllViews();

            }
        });
    }
}
