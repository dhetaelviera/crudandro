package com.example.crud;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.crud.Adapter.AdapterData;
import com.example.crud.Model.ModelData;
import com.example.crud.Util.AppControler;
import com.example.crud.Util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    Button btninsert, btndelete;
    List<ModelData> mItems;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(MainActivity.this);

        mRecyclerview=(RecyclerView) findViewById(R.id.recyclerviewTemp);
        btndelete=(Button) findViewById(R.id.btn_delete);
        btninsert=(Button) findViewById(R.id.btn_insert);
        mItems=new ArrayList<>();


        mManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter=new AdapterData(MainActivity.this, mItems);
        mRecyclerview.setAdapter(mAdapter);
        loadJson();

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, InsertData.class);
                startActivity(intent);
            }
        });

    }

    private void loadJson(){

        progressDialog.setMessage("Ngambil data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonArrayRequest reqData=new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATA,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("ini responsenya: ");
                        System.out.println(response);
                        progressDialog.cancel();
                        Log.d("volley ", "response = " + response.toString());
                        for (int i=0;i<response.length();i++){
                            try {
                                JSONObject data= response.getJSONObject(i);
                                ModelData md= new ModelData();
                                md.setUsername(data.getString("username"));
                                md.setNama(data.getString("nama"));
                                md.setEmail(data.getString("email"));
                                md.setPassword(data.getString("password"));
                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Log.d("volley","error="+ error.getMessage());
                    }
                }
        );
        AppControler.getInstance().addToRequestQueue(reqData);
    }
}

