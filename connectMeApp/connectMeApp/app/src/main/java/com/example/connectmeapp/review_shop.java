package com.example.connectmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class review_shop extends AppCompatActivity implements JsonResponse {
    TextView t;
    RatingBar r;
    EditText e;
    Button b;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_shop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        t=findViewById(R.id.t1);
        r=findViewById(R.id.r1);
        e=findViewById(R.id.e1);
        b=findViewById(R.id.b1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonReq JR = new JsonReq();
                JR.json_response = review_shop.this;

                // Get the review text and rating
                String reviewText = e.getText().toString();
                float ratingValue = r.getRating();

                // Construct the query with the review and rating
                String q = "/review_shop?pid=" + product.productid + "&review=" + reviewText + "&rating=" + ratingValue+"&uid="+Login.uid;

                JR.execute(q);
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(this, "review submited", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),User_Home.class));
            }
            else if(status.equalsIgnoreCase("failed")){
                Toast.makeText(this, "submission failed", Toast.LENGTH_SHORT).show();

            }

        }
        catch(Exception e){

        }
    }
}


