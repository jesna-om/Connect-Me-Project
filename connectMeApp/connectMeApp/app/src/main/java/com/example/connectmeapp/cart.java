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

import org.json.JSONException;
import org.json.JSONObject;

public class cart extends AppCompatActivity implements JsonResponse {

    EditText a;
    Button b;
    String qty,total,amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        a = findViewById(R.id.t1);
        b = findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qty = a.getText().toString();



                if (Integer.parseInt(qty)<Integer.parseInt(product.prod_quantity)) {
                    float total_amount = Float.parseFloat(product.prod_price) * Float.parseFloat(qty);

                    JsonReq JR = new JsonReq();
                    JR.json_response=cart.this;
                    String q = "/User_add_to_cart?qty="+qty+"&productid="+product.productid+"&shopid="+product.shopid+"&loginid="+Login.lid+"&price="+product.prod_price+"&tot_amt="+total_amount;
                    JR.execute(q);
                    Toast.makeText(cart.this, "On stock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(cart.this, "Out of Stock", Toast.LENGTH_SHORT).show();
                }


            }


        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String  status = jo.getString("status");

            String method = jo.getString("method");

            if (method.equalsIgnoreCase("add_to_cart")){

                if(status.equalsIgnoreCase("success")){
                    Toast.makeText(this, "Add to Cart successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),User_Home.class));

                }else {
                    Toast.makeText(this, "Can't add to cart", Toast.LENGTH_SHORT).show();
                }

            }else {

                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
}