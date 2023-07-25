package com.example.mywallpaper.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mywallpaper.adapter.CategoryAdapter;
import com.example.mywallpaper.adapter.WallPaperAdapter;
import com.example.mywallpaper.databinding.ActivityMainBinding;
import com.example.mywallpaper.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface{
    private ActivityMainBinding binding;
    private List<CategoryModel> categoryModelList;
    private ArrayList<String> stringList;
    private CategoryAdapter categoryAdapter;
    private WallPaperAdapter wallPaperAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        stringList = new ArrayList<>();
        categoryModelList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL,false);
        binding.idRlvCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(categoryModelList,this,this::onCategoryClick);
        binding.idRlvCategory.setAdapter(categoryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.idRlv2Category.setLayoutManager(gridLayoutManager);
        wallPaperAdapter = new WallPaperAdapter(stringList,this);
        binding.idRlv2Category.setAdapter(wallPaperAdapter);
        getCategories();
        getWallPapers();
        binding.idImgSearch.setOnClickListener(v -> {
            String  searchStr = binding.idEdtSearch.getText().toString();
            if (searchStr.isEmpty()){
                Toast.makeText(this, "Please enter something to search", Toast.LENGTH_SHORT).show();
            }else {
                getWallPapersByCategory(searchStr);
            }
        });
    }
    private void getWallPapersByCategory(String category) {
        stringList.clear();
        binding.ProgressBar.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query="+category+"&per_page=30&page=1";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    binding.ProgressBar.setVisibility(View.GONE);
                    JSONArray photos = response.getJSONArray("photos");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoObj = photos.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        stringList.add(imgUrl);
                    }
                    wallPaperAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "RKedO2ZsOaVbLlQPPqWoclqGonS99hGe3RyOYUg2WcBMHjJFqb3a1lXW");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    private void getWallPapers() {
        stringList.clear();
        binding.ProgressBar.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?/per_page=30&page=1";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                binding.ProgressBar.setVisibility(View.GONE);
                try {
                    JSONArray photos = response.getJSONArray("photos");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoObj = photos.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        stringList.add(imgUrl);
                    }
                    wallPaperAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get data...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "RKedO2ZsOaVbLlQPPqWoclqGonS99hGe3RyOYUg2WcBMHjJFqb3a1lXW");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    private void getCategories() {
        categoryModelList.add(new CategoryModel("Technology", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryModelList.add(new CategoryModel("Programming", "https://images.unsplash.com/photo-1542831371-29b0f74f9713?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHJvZ3JhbW1pbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryModelList.add(new CategoryModel("Nature", "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModelList.add(new CategoryModel("Travel", "https://images.pexels.com/photos/672358/pexels-photo-672358.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModelList.add(new CategoryModel("Architecture", "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModelList.add(new CategoryModel("Arts", "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModelList.add(new CategoryModel("Music", "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModelList.add(new CategoryModel("Abstract", "https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModelList.add(new CategoryModel("Cars", "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModelList.add(new CategoryModel("Flowers", "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
    }
    @Override
    public void onCategoryClick(int position) {
        String category = categoryModelList.get(position).getCategory();
        getWallPapersByCategory(category);
    }
}