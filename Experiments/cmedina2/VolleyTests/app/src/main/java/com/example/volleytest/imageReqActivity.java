package com.example.volleytest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.volleytest.app.AppController;
import com.example.volleytest.utils.Const;

import java.io.UnsupportedEncodingException;

public class imageReqActivity extends AppCompatActivity {
    private static final String TAG = imageReqActivity.class.getSimpleName();
    private Button btnImageReq;
    private NetworkImageView imgNetWorkView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_req);

        btnImageReq = (Button) findViewById(R.id.imageReqBtn);
        imgNetWorkView = (NetworkImageView) findViewById(R.id.imageNetwork);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnImageReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                makeImageRequest();
            }
        });
    }
    private void makeImageRequest(){
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imgNetWorkView.setImageUrl(Const.URL_IMAGE, imageLoader);
        imageLoader.get(Const.URL_IMAGE, ImageLoader.getImageListener(
                imageView, R.drawable.ig_test1, R.drawable.ig_test1));

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Const.URL_IMAGE);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }else{
            // catch response doesn't exist. Make network call here
        }
    }
}