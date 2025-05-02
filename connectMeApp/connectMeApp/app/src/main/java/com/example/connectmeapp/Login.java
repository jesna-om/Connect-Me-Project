package com.example.connectmeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements JsonResponse {
    EditText a,b;
    TextView f;
    Button c,d;

    String username,password;
    public static String utype,lid,uid;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        startService(new Intent(getApplicationContext(), LocationService.class));


        a=findViewById(R.id.t1);
        b=findViewById(R.id.t2);
        c=findViewById(R.id.b1);
        d=findViewById(R.id.b2);
        f=findViewById(R.id.forgot_password);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              username=a.getText().toString();
              password=b.getText().toString();

              JsonReq JR=new JsonReq();
              JR.json_response=Login.this;
              String q="/user_login?u="+username+"&p="+password;
              JR.execute(q);


            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });

        TextView forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to handle the forgot password click event
                Intent intent = new Intent(Login.this, forgotpassword.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            Log.d("pearl",status);
            if(status.equalsIgnoreCase("success")){
//                Toast.makeText(this, "success0", Toast.LENGTH_SHORT).show();

                JSONArray ja1=(JSONArray) jo.getJSONArray("data");
                lid=ja1.getJSONObject(0).getString("login_id");
                utype=ja1.getJSONObject(0).getString("user_type");
                uid=ja1.getJSONObject(0).getString("user_id");


                SharedPreferences.Editor e=sh.edit();
                e.putString("lid",lid);
                e.commit();
                Toast.makeText(this, "success1", Toast.LENGTH_SHORT).show();

                if(utype.equals("user")){
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),User_Home.class));
                }
//                else if (utype.equals("volunteer")) {
//                    Toast.makeText(getApplicationContext(), "Login Successful as Volunteer", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(), Volunteer_home.class));
//                }else if (utype.equals("ambulance")) {
//                    Toast.makeText(getApplicationContext(), "Login Successful as Ambulance", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(), Ambulance_home.class));
//                }
                else if(status.equalsIgnoreCase("failed")){
                    Toast.makeText(getApplicationContext(), "Invalid Username & Password", Toast.LENGTH_LONG).show();
                }
            }

        }
        catch (Exception e){
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

    }
}