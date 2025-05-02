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

public class new_password extends AppCompatActivity implements JsonResponse {
    EditText a,b;
    Button c;
    String passw,confirm_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        a=findViewById(R.id.psw2);
        b=findViewById(R.id.psw1);
        c=findViewById(R.id.btn);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passw=a.getText().toString();
                confirm_psw=a.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=new_password.this;
                String q="/new_password?new_pass="+confirm_psw+"&login_id="+forgotpassword.lid;
                JR.execute(q);

            }
        });


    }


    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(this, "password changed succesfully ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
            else if(status.equalsIgnoreCase("failed")){
                Toast.makeText(this, "password change failed", Toast.LENGTH_SHORT).show();

            }

        }
        catch(Exception e){

        }
    }
}

