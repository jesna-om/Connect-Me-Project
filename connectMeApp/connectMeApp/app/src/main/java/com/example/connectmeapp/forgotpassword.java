package com.example.connectmeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
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

public class forgotpassword extends AppCompatActivity implements JsonResponse {
    EditText e1;
    Button b1;
    String email;
    TextView t1;
    public static String lid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgotpassword);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        e1=findViewById(R.id.fgt_email);
        b1=findViewById(R.id.forgot_btn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=e1.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=forgotpassword.this;
                String q="/forgot_password?email="+email;
                JR.execute(q);

            }
        });
        t1=findViewById(R.id.back_to_login);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1=(JSONArray) jo.getJSONArray("data");
                lid=ja1.getJSONObject(0).getString("login_id");


                startActivity(new Intent(getApplicationContext(),new_password.class));
            }
            else if(status.equalsIgnoreCase("failed")){
                Toast.makeText(this, "edit failed", Toast.LENGTH_SHORT).show();

            }

        }
        catch(Exception e){

        }
    }
}





