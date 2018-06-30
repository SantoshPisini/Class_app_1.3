package com.santosh.android.class_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class tab2 extends Fragment {
    private static final String URL_DATA = "http://santosh36.ga/class_app_home.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<list_item2> listItems;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        intab2(view);
        return view;
    }

    private void intab2(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listItems = new ArrayList<>();
        loadRecyclerViewData();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.simpleSwipeRefreshLayout2);
        swipeRefreshLayout.setColorSchemeResources(R.color.Green,R.color.Yellow,R.color.Red,R.color.Blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        listItems.clear();
                        if(recyclerView.isDrawingCacheEnabled()){
                            recyclerView.destroyDrawingCache();
                        }
                        loadRecyclerViewData();
                    }
                },3000);
            }
        });
    }
    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data . . .");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = jsonArray.length()-1; i >= 0; i--) {
                                JSONObject o = jsonArray.getJSONObject(i);
                                list_item2 item = new list_item2(
                                        o.getString("date"),
                                        o.getString("title"),
                                        o.getString("description"),
                                        o.getString("img"),
                                        o.getString("source"),
                                        o.getInt("category")
                                );
                                listItems.add(item);
                            }
                            adapter = new MyAdapter2(listItems, getContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Oops! Can't connect to internet", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}

