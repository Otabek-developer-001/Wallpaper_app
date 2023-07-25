package com.example.mywallpaper.activities;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mywallpaper.databinding.ActivityWallPaperBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
public class WallPaperActivity extends AppCompatActivity {
    private ActivityWallPaperBinding binding;
    private String url;
    private WallpaperManager wallpaperManager;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWallPaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        url = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(url).into(binding.image);
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        binding.idBtnSetWallpaper.setOnClickListener(v -> Glide.with(WallPaperActivity.this)
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Toast.makeText(WallPaperActivity.this, "Fail to load image...", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    wallpaperManager.setBitmap(resource);
                    Toast.makeText(WallPaperActivity.this, "Fail to set wallpaper...", Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    e.printStackTrace();
                }
                return false;
            }
        }).submit());
        FancyToast.makeText(WallPaperActivity.this,"Wallpaper to set home screen...",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
    }
}