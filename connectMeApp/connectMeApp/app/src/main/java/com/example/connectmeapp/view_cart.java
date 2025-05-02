package com.example.connectmeapp;

import static com.example.connectmeapp.R.id.b1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class view_cart extends AppCompatActivity implements JsonResponse {
    ListView l1;
    Button b;

    String[] pname, price, qty,img,des,total,amount,product_id,shop_id, value,shopname,om_id;
    public static String total_amt,omid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_cart);

        l1 = findViewById(R.id.l1);
        b = findViewById(R.id.buy_button);



        JsonReq JR = new JsonReq();
        JR.json_response = view_cart.this;
        String q = "/view_cart?id="+Login.lid;
        JR.execute(q);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                total_amt=total[position];
                omid=om_id[position];
                startActivity(new Intent(getApplicationContext(),make_payment.class));

            }
        });


    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            String method = jo.getString("method");

            if(method.equalsIgnoreCase("view")){

                if(status.equalsIgnoreCase("status"));
                {
                    JSONArray ja1 = jo.getJSONArray("data");

                    int length = ja1.length();

                    pname = new String[length];
                    price = new String[length];
                    qty = new String[length];
                    img = new String[length];
                    des = new String[length];
                    product_id = new String[length];
                    shop_id = new String[length];
                    value = new String[length];
                    shopname = new String[length];
                    total= new String[length];
                    amount= new String[length];
                    om_id= new String[length];

                    for(int i=0;i<length;i++){

                        pname[i] = ja1.getJSONObject(i).getString("product_name");
                        price[i] = ja1.getJSONObject(i).getString("price");
                        qty[i] = ja1.getJSONObject(i).getString("oquantity");
                        img[i] = ja1.getJSONObject(i).getString("image");
                        des[i] = ja1.getJSONObject(i).getString("description");
                        product_id[i] = ja1.getJSONObject(i).getString("product_id");
                        shop_id[i] = ja1.getJSONObject(i).getString("shop_id");
                        shopname[i] = ja1.getJSONObject(i).getString("shop_name");
                        total[i] = ja1.getJSONObject(i).getString("total");
                        amount[i] = ja1.getJSONObject(i).getString("amount");
                        om_id[i] = ja1.getJSONObject(i).getString("om_id");




                        value[i] = "shop name:" + shopname[i]+ "\nProduct name:" + pname[i]+ "\nPrice:" + price[i]  + "\nquantity:" + qty[i] + "\nimage:" + img[i] + "\ndescription:" + des[i]+ "\ntotal:" + total[i]+ "\namount:" + amount[i] ;


                    }

                }

//                ArrayAdapter<String> ar=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
//                l1.setAdapter(ar);

                Custom_cart ar=new Custom_cart(view_cart.this,pname,shopname,price,qty,img,des);
                l1.setAdapter(ar);

            }
        } catch (Exception e){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
