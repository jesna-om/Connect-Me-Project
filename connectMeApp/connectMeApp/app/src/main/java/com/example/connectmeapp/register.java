package com.example.connectmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class register extends AppCompatActivity implements JsonResponse {
    EditText a,b,c,d,e,h,i;
    Button j;

    String fname,lname,place,email,phone,uname,psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);



        startService(new Intent(getApplicationContext(), LocationService.class));


        a=findViewById(R.id.t1);
        b=findViewById(R.id.t2);
        c=findViewById(R.id.t3);
        d=findViewById(R.id.t4);
        e=findViewById(R.id.t5);
        h=findViewById(R.id.t8);
        i=findViewById(R.id.t9);
        j=findViewById(R.id.b1);

        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=a.getText().toString();
                lname=b.getText().toString();
                place=c.getText().toString();
                email=d.getText().toString();
                phone=e.getText().toString();
                uname=h.getText().toString();
                psw=i.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=register.this;
                String q="/user_register?f="+fname+"&l="+lname+"&pl="+place+"&em="+email+"&ph="+phone+"&u="+uname+"&p="+psw + "&lati=" + LocationService.lati+ "&logi="+ LocationService.logi;
                JR.execute(q);


            }
        });
    }

    @Override
    public void response(JSONObject jo) {
    try{
        String status=jo.getString("status");
        if(status.equalsIgnoreCase("success")){
            Toast.makeText(this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
        else if(status.equalsIgnoreCase("failed")){
            Toast.makeText(this, "Registered failed", Toast.LENGTH_SHORT).show();

        }
//        else if(status.equalsIgnoreCase("username")){
//            Toast.makeText(this, "Username already Exist", Toast.LENGTH_SHORT).show();
//
//        }

    }
    catch(Exception e){

    }
    }
}
