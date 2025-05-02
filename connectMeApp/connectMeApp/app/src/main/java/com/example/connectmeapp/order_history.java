package com.example.connectmeapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class order_history extends AppCompatActivity implements JsonResponse {
    ListView l1;
    TextView t;

    String[] pname, price, qty,img,des,total,amount,product_id,shop_id, value,shopname,om_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        l1 = findViewById(R.id.history);
        t = findViewById(R.id.textView);



        JsonReq JR = new JsonReq();
        JR.json_response = order_history.this;
        String q = "/order_history?id="+Login.lid;
        JR.execute(q);
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
//
//                ArrayAdapter<String> ar=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
//                l1.setAdapter(ar);

                Custom_history ar=new Custom_history(order_history.this,pname,shopname, price, qty, img, des);
                l1.setAdapter(ar);

            }
        } catch (Exception e){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
