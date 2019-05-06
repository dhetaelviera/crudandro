package com.example.crud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class UpdateDelete extends AppCompatActivity {


    EditText username,nama,email,password;
    Button btnDelete, btnUpdate, btnBatal;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        //get data

        Intent data= getIntent();
        int update= data.getIntExtra("update",0);
        final String iusername=data.getStringExtra("username");
        final String inama=data.getStringExtra("nama");
        final String iemail=data.getStringExtra("email");
        final String ipassword=data.getStringExtra("password");

        //end of get data


        username=(EditText) findViewById(R.id.username1);
        email=(EditText) findViewById(R.id.email1);
        nama=(EditText) findViewById(R.id.nama1);
        password=(EditText) findViewById(R.id.password1);

        btnDelete=(Button) findViewById(R.id.btn_del);
        btnUpdate=(Button) findViewById(R.id.btn_update);
        btnBatal=(Button) findViewById(R.id.btn_batal);
        progressDialog=new ProgressDialog(UpdateDelete.this);

        if (update==0){
            username.setText(iusername);
            username.setVisibility(View.GONE);
            nama.setText(inama);
            email.setText(iemail);
            password.setText(ipassword);
        }

        //delete action
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                openDialog();
            }
        });

        //update action
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateData();
            }
        });

        //cancel action
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent batal=new Intent(UpdateDelete.this,MainActivity.class);
                startActivity(batal);
            }
        });



    }
    public void openDialog() {
        Dialog dialog=new Dialog();
        dialog.show(getSupportFragmentManager(),"dialog");
    }

    private void deleteData(){
        progressDialog.setMessage("Deleting...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest deleteReq=new StringRequest(Request.Method.POST, ServerAPI.URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        Log.d("volley","response :"+response.toString());
                        try {
                            JSONObject res=new JSONObject(response);
                            Toast.makeText(UpdateDelete.this,res.getString("message"),Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(UpdateDelete.this, MainActivity.class));
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Log.d("volley","error: "+error.getMessage());
                        Toast.makeText(UpdateDelete.this,"gagal delete",Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                return params;
            }
        };

        AppControler.getInstance().addToRequestQueue(deleteReq);
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
                            Toast.makeText(UpdateDelete.this,"pesan :" +  res.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(UpdateDelete.this, MainActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(UpdateDelete.this,"pesan : Gagal update",Toast.LENGTH_SHORT).show();
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
}
