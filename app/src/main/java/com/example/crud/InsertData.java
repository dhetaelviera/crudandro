package com.example.crud;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.crud.Util.AppControler;
import com.example.crud.Util.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class InsertData extends AppCompatActivity {

    EditText username, nama, email, password;
    Button btncancel, btnsave;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        //get data

        Intent data= getIntent();
       final int update= data.getIntExtra("update",0);
        String iusername=data.getStringExtra("username");
        String inama=data.getStringExtra("nama");
        String iemail=data.getStringExtra("email");
        String ipassword=data.getStringExtra("password");

        //end of get data

        username=(EditText) findViewById(R.id.imp_username);
        email=(EditText) findViewById(R.id.imp_email);
        nama=(EditText) findViewById(R.id.imp_nama);
        password=(EditText) findViewById(R.id.imp_password);
        btncancel=(Button) findViewById(R.id.btn_cancel);
        btnsave=(Button) findViewById(R.id.btn_save);
        progressDialog=new ProgressDialog(InsertData.this);

        //kondisi update atau insert
        System.out.println("kondisi update = " + update);
        if (update==1){
            btnsave.setText("Update data");
           // btncancel.setText("Hapus data");
            username.setText(iusername);
            username.setVisibility(View.GONE);
            nama.setText(inama);
            email.setText(iemail);
            password.setText(ipassword);
        }


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update==1){
                    updateData();
                } else{
                simpanData();
              }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent batal=new Intent(InsertData.this,MainActivity.class);
                startActivity(batal);
            }
        });

    }

    private void updateData(){
        progressDialog.setMessage("Memperbarui data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest updateReq=new StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        try {
                            System.out.println("ini responsenya: "+response);
                            JSONObject res=new JSONObject(response);
                            Toast.makeText(InsertData.this,"pesan :" +  res.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(InsertData.this, MainActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(InsertData.this,"pesan : Gagal update",Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("email", email.getText().toString());
                params.put("nama", nama.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        AppControler.getInstance().addToRequestQueue(updateReq);
    }

    private void simpanData(){
        progressDialog.setMessage("Nyimpen data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest sendData=new StringRequest(Request.Method.POST, ServerAPI.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        try {
                            JSONObject res=new JSONObject(response);
                            Toast.makeText(InsertData.this,"pesan :" +  res.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(InsertData.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Toast.makeText(InsertData.this,"pesan : Gagal insert",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("email", email.getText().toString());
                params.put("nama", nama.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };

        AppControler.getInstance().addToRequestQueue(sendData);

    }
}
