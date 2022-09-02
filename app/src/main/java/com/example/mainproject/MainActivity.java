package com.example.mainproject;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mainproject.models.ItemsItem;
import com.example.mainproject.models.ResponseModel;
import com.example.mainproject.retrofit.ApiHelper;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Initialization
    ArrayList<ItemsItem> dataList = new ArrayList<>();
    ConstraintLayout constraintLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    Button retryAgain;
    Adapter adapter ;
    ApiHelper apiHelper;
    View errorLayout;
    RecyclerView recyclerView;


    //Initializing Object of Other Classes
    SortingPerform sortingPerform=new SortingPerform();
    SortingByName sortingByName=new SortingByName();


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init views
        recyclerView = findViewById(R.id.recyclerV1);
        constraintLayout=findViewById(R.id.mainContent);
        shimmerFrameLayout=findViewById(R.id.shimmer);
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        errorLayout=findViewById(R.id.errorLayout);

        setUpRecyclerView();

        setUpRetrofit();

        makeApiCall();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeApiCall();
            }
        });

    }



    private void setUpRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Adapter(dataList);
        recyclerView.setAdapter(adapter);
    }


    //Retrofit client
    private void setUpRetrofit(){
        //Logging interceptor to log the api calls.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();
        String baseUrl = "https://trendings.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiHelper = retrofit.create(ApiHelper.class);
    }


    void makeApiCall(){


        shimmerFrameLayout.startShimmer();
        apiHelper.makeCall().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.setVisibility(View.GONE);

                    if (response.body() != null) {
                        dataList.clear();
                        dataList.addAll(response.body().getItems());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.VISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                errorLayout.setVisibility(View.VISIBLE);
                constraintLayout.setVisibility(View.GONE);
                retryAgain= findViewById(R.id.Retry);
                retryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeApiCall();
                    }
                });
            }
        });
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.resource_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action1:
                 Toast.makeText(getApplicationContext(),"Sort by Name",Toast.LENGTH_SHORT).show();
                 Collections.sort(dataList, sortingByName);
                 adapter.notifyItemRangeChanged(0, dataList.size() - 1);
                 break;
            case R.id.action2:
                 Toast.makeText(getApplicationContext(),"Sort by Stars",Toast.LENGTH_SHORT).show();
                 Collections.sort(dataList, sortingPerform);
                adapter.notifyItemRangeChanged(0, dataList.size() - 1);
                 break;
            case R.id.action3:
                 finish();
            default:
                 break;
        }
        return super.onOptionsItemSelected(item);
    }

}