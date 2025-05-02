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

public class edit_profile extends AppCompatActivity implements JsonResponse {

    EditText a,b,c,d,e;
    Button j;

    String fname,lname,place,email,phone,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        a=findViewById(R.id.et1);
        b=findViewById(R.id.et2);
        c=findViewById(R.id.et3);
        d=findViewById(R.id.et4);
        e=findViewById(R.id.et5);
        j=findViewById(R.id.bt1);

        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=a.getText().toString();
                lname=b.getText().toString();
                place=c.getText().toString();
                email=d.getText().toString();
                phone=e.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=edit_profile.this;
                String q="/edit_profile?f="+fname+"&l="+lname+"&pl="+place+"&em="+email+"&ph="+phone+"&lid="+Login.lid;
                JR.execute(q);


            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(this, "edited Sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
            else if(status.equalsIgnoreCase("failed")){
                Toast.makeText(this, "edit failed", Toast.LENGTH_SHORT).show();

            }

        }
        catch(Exception e){

        }
    }
}


