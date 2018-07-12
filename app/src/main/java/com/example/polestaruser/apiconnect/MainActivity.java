package com.example.polestaruser.apiconnect;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView name,number;
    user u;
    String student_json_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);

        if (check_connection()) {
            Toast.makeText(this, "You are not Connected to internet", Toast.LENGTH_SHORT).show();
        }
        else
        {  u = new user();

            Gson g = new Gson();
            student_json_object = g.toJson(u);
            connect();
        }



    }



    public boolean check_connection()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo()==null) return true;
        else
            return false;

    }





    public void connect(){
        //  Toast.makeText(this, "connect", Toast.LENGTH_SHORT).show();
        StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.1.85:8081/newApi/index.jsp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String result = s.replaceAll("[\n\r ]", "");
                        if (result.equals(""))
                            Toast.makeText(MainActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();

                        else

                        {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            Gson g = new Gson();
                            //user u = g.fromJson(s, user.class);
                            List<Pair<String,String>> marks = new ArrayList<>();

                            //marks = u.get_marks();
                           // Toast.makeText(MainActivity.this, u.getName(), Toast.LENGTH_SHORT).show();
                            name.setText(s);


                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map =new HashMap<>();
                map.put("student_json_object",student_json_object);
                return map;
            }
        };
        RequestQueue r= Volley.newRequestQueue(this);
        r.add(sr);
    }
}
