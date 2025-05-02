package com.example.connectmeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

public class service_payment extends AppCompatActivity implements JsonResponse {
    Button b;

    TextView t1,t2,t3,t4,t5;
    EditText e1,e2,e3,e4;

    String amount,status;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        t1 = findViewById(R.id.amt);
//        t1.setText("Total Amount : " +service_n_charges.amt);
        b = findViewById(R.id.pay_btn);
        b.setText(("Pay" +view_user_request.amot));


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonReq JR = new JsonReq();
                JR.json_response = service_payment.this;
                String q = "/service_payment?reqid=" + view_user_request.reqid + "&amt=" + view_user_request.amot;
                JR.execute(q);

            }
        });
    }


    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(this, "Payment Sucessfull", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),User_Home.class));
            }
            else if(status.equalsIgnoreCase("failed")){
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();

            }

        }
        catch(Exception e){

        }
    }
}

