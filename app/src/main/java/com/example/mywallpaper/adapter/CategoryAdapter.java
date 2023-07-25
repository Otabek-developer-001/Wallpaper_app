package com.example.mywallpaper.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mywallpaper.R;
import com.example.mywallpaper.databinding.CategoryItemBinding;
import com.example.mywallpaper.model.CategoryModel;

import java.util.List;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH>{
    private List<CategoryModel> list;
    private Context context;
    private CategoryClickInterface categoryClickInterface;
    public CategoryAdapter(List<CategoryModel> list, Context context, CategoryClickInterface categoryClickInterface) {
        this.list = list;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CategoryModel model = list.get(position);
        holder.binding.idTVCategory.setText(model.getCategory());
        Glide.with(context).load(model.getImageUrl()).into(holder.binding.idIVCategory);
        holder.itemView.setOnClickListener(v -> categoryClickInterface.onCategoryClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }
    public class VH extends RecyclerView.ViewHolder{
        private CategoryItemBinding binding;
        public VH(@NonNull CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
