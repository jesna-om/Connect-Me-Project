package com.example.connectmeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class service_request extends AppCompatActivity implements JsonResponse {

    Button b;
    EditText e1,e2;
    String  title, date;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_request);

        b=findViewById(R.id.request_btn);
        e1=findViewById(R.id.titlereq);
        e2=findViewById(R.id.t1date);


        b=findViewById(R.id.request_btn);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title=e1.getText().toString();
                date=e2.getText().toString();

                if(title.equalsIgnoreCase("")) {
                    e1.setError("Enter the Title");
                    e1.setFocusable(true);
                }else if  (date.equalsIgnoreCase("") || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        e2.setFocusable(true);
                        e2.setError("Enter a valid Date (YYYY-MM-DD)");

                }else {
                    JsonReq JR=new JsonReq();
                    JR.json_response=service_request.this;
                    String q="/service_request?sid=" + service_n_charges.sid + "&uid=" + Login.lid + "&title=" + title + "&amount=" + service_n_charges.amt + "&date=" + date;
                    JR.execute(q);

                }





            }
        });

    }


    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(this, "Request Send Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),User_Home.class));

            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

