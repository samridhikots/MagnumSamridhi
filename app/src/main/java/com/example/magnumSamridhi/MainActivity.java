package com.example.magnumSamridhi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String URL= "https://api.github.com/search/users?q=";
    private static String URL2 = "&page=";
    private static String pageNo = "0";
    private static String term = "";
    private int page = 0;
    RecyclerView userList;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this);
    boolean isScrolling = false;
    int scrolledOut,total,visible;
    ArrayList<User> users;
    ProgrammingAdapter adapter;
    private ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        userList = findViewById (R.id.UserList);
        progressBar = findViewById (R.id.progressBar);

        Intent i = getIntent ();
        term = i.getStringExtra ("term");

        users = new ArrayList<> ();
        adapter = new ProgrammingAdapter (getApplicationContext (), users);
        userList.setAdapter (adapter);
        userList.setLayoutManager (linearLayoutManager);
        userList.setOnScrollChangeListener (new View.OnScrollChangeListener () {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                visible = linearLayoutManager.getChildCount();
                total = linearLayoutManager.getItemCount();
                scrolledOut = linearLayoutManager.findFirstVisibleItemPosition();

                if(visible + scrolledOut == total){
                    progressBar.setVisibility (View.VISIBLE);
                    getData ();
                }

            }
        });
        getData ();

    }

    private void getData() {
        page++;
        pageNo = String.valueOf (page);
        final String sam = URL + term +  URL2 +pageNo;




                final StringRequest request = new StringRequest (sam, new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        Log.d ("CODE", response);

                        GsonBuilder gsonBuilder = new GsonBuilder ();
                        Gson gson = gsonBuilder.create ();
                        //users = gson.fromJson (response, User[].class);
                        try {
                            //JSONArray qq = new JSONArray (response);
                            JSONObject qq1 = new JSONObject (response);
                            JSONArray jsonArray = qq1.getJSONArray ("items");
                            Log.e ("samridhi", jsonArray.toString ());
                            for (int i = 0; i < jsonArray.length (); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject (i);

                                User user = gson.fromJson (jsonObject.toString (), User.class);
                                users.add (user);
                            }
                        } catch (JSONException e) {
                            Log.e ("json", e.getMessage ());
                            e.printStackTrace ();
                        }
                        adapter.notifyDataSetChanged ();
                        progressBar.setVisibility (View.GONE);
                    }
                }, new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText (MainActivity.this, "API rate limit exceeded", Toast.LENGTH_SHORT).show ();
                        progressBar.setVisibility (View.GONE);
                    }
                });

                RequestQueue queue = Volley.newRequestQueue (MainActivity.this);
                queue.add (request);


            }




}