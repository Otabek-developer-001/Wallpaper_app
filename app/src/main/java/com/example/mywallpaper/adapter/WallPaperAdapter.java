package com.example.mywallpaper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mywallpaper.activities.WallPaperActivity;
import com.example.mywallpaper.databinding.WallpaperItemBinding;

import java.util.List;

public class WallPaperAdapter extends RecyclerView.Adapter<WallPaperAdapter.VH>{
    private List<String> stringList;
    private Context context;

    public WallPaperAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(WallpaperItemBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(stringList.get(position)).into(holder.binding.idIVWallpaper);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WallPaperActivity.class);
            intent.putExtra("imgUrl",stringList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
    public static class VH extends RecyclerView.ViewHolder{
        private final WallpaperItemBinding binding;
        public VH(@NonNull WallpaperItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
