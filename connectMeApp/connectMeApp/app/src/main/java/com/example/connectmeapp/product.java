package com.example.connectmeapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import org.json.JSONArray;
import org.json.JSONObject;

public class product extends AppCompatActivity implements JsonResponse {
    ListView l1;
    TextView t1,t2;
    SearchView s1;
    ImageView im1;
    String[] pname, price, qty, img, des,product_id,shop_id, value,shopname;
    Button b1;

    public  static  String productid,shopid,prod_quantity,prod_price;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        l1 = findViewById(R.id.view_product);
        s1 = findViewById(R.id.search_view);

        t1=findViewById(R.id.products_text);
        t2=findViewById(R.id.notfound);
        im1=findViewById(R.id.pro_not_ic);
        b1=findViewById(R.id.pro_rec);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        t2.setVisibility(View.GONE);
        im1.setVisibility(View.GONE);

        JsonReq JR = new JsonReq();
        JR.json_response = product.this;
        String q = "/user_view_product";
        JR.execute(q);

        s1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // You can handle the search when the user presses 'Enter' (optional)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Every time the query text changes, make an API request
                if (!newText.isEmpty()) {
                    sendSearchRequest(newText); // Call the method to send the request
                }
                return true;
            }
        });

        s1.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                triggerDefaultRequest();
                return false; // Returning false allows the default close action to occur
            }
        });



        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productid=product_id[position];
                shopid=shop_id[position];
                prod_quantity=qty[position];
                prod_price = price[position];
                final  CharSequence[] items ={"Add to Cart","Add Review","Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(product.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Add to Cart")){
                            Intent il=new Intent(getApplicationContext(), cart.class);
                            startActivity(il);

                        }
                        else if (items[item].equals("Add Review")){
                            Intent il=new Intent(getApplicationContext(), review_shop.class);
                            startActivity(il);

                        }else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

         }
        });

    }

    private void sendSearchRequest(String query) {
        // Initialize your JsonReq object
        JsonReq JR = new JsonReq();
        JR.json_response = product.this;

        // Add the query parameter to the route (make sure to encode the query if necessary)
        String q = "/user_search_products?query=" + query; // Adjust based on your API

        // Execute the request asynchronously
        JR.execute(q);
    }

    private void triggerDefaultRequest() {
        JsonReq JR = new JsonReq();
        JR.json_response = product.this;
        String q = "/user_view_product";  // Default route when search is cleared
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            String method = jo.getString("method");

            if(method.equalsIgnoreCase("view")){

                if(status.equalsIgnoreCase("success"))
                {
                    Toast.makeText(this, "hhhhhhhhhhh", Toast.LENGTH_SHORT).show();

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

                    for(int i=0;i<length;i++){

                        pname[i] = ja1.getJSONObject(i).getString("product_name");
                        price[i] = ja1.getJSONObject(i).getString("price");
                        qty[i] = ja1.getJSONObject(i).getString("quantity");
                        img[i] = ja1.getJSONObject(i).getString("image");
                        des[i] = ja1.getJSONObject(i).getString("description");
                        product_id[i] = ja1.getJSONObject(i).getString("product_id");
                        shop_id[i] = ja1.getJSONObject(i).getString("shop_id");
                        shopname[i] = ja1.getJSONObject(i).getString("shop_name");




                        value[i] = "shop name:" + shopname[i]+ "\nProduct name:" + pname[i]+ "\nPrice:" + price[i]  + "\nquantity:" + qty[i] + "\nimage:" + img[i] + "\ndescription:" + des[i] ;


                    }
//
//                    ArrayAdapter<String> ar=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
//                    l1.setAdapter(ar);

                    Custom_product ar=new Custom_product(product.this,pname,shopname, price, qty, img, des);
                    l1.setAdapter(ar);

                } else {
                    l1.setVisibility(View.GONE);
                    s1.setVisibility(View.GONE);

                    t1.setVisibility(View.GONE);
                    t2.setVisibility(View.VISIBLE);
                    im1.setVisibility(View.VISIBLE);

                }

            }
        } catch (Exception e){
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}