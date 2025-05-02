package com.example.connectmeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class shop extends AppCompatActivity implements JsonResponse {

    ListView l1;

    String[] sname,place,pin,email,phone,result,latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop);


        startService(new Intent(getApplicationContext(), LocationService.class));

        l1=findViewById(R.id.view_shop);

        JsonReq JR=new JsonReq();
        JR.json_response=shop.this;
        String q = "/user_shop?lati=" + LocationService.lati+ "&logi="+ LocationService.logi;
        JR.execute(q);


//        JsonReq JR = new JsonReq();
//        JR.json_response = (JsonResponse) shop.this;
//        String q = "/user_shop?lati=" + LocationService.lati+ "&logi="+ LocationService.logi;
//        q = q.replace(" ", "%20");
//        JR.execute(q);
//

        }

    @Override
    public void response(JSONObject jo) {

        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")){
                JSONArray ja1=(JSONArray) jo.getJSONArray("data");
                sname=new String[ja1.length()];
                place=new String[ja1.length()];
                pin=new String[ja1.length()];
                email=new String[ja1.length()];
                phone=new String[ja1.length()];
                latitude=new String[ja1.length()];
                longitude=new String[ja1.length()];
                result=new String[ja1.length()];

                for(int i=0;i<ja1.length();i++){
                    sname[i]=ja1.getJSONObject(i).getString("shop_name");
                    place[i]=ja1.getJSONObject(i).getString("place");
                    pin[i]=ja1.getJSONObject(i).getString("pin");
                    email[i]=ja1.getJSONObject(i).getString("email");
                    phone[i]=ja1.getJSONObject(i).getString("phone");
                    latitude[i]=ja1.getJSONObject(i).getString("latitude");
                    longitude[i]=ja1.getJSONObject(i).getString("longitude");
                    result[i]="Shop Name : "+sname[i]+"\nPlace : "+place[i]+"\nPin : "+pin[i]+"\nemail : "+email[i]+"\nPhone : "+phone[i];

                }

//                l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,result));

                Custom_shop ar=new Custom_shop(shop.this,sname,place,pin,email,phone);
                l1.setAdapter(ar);



            }


        }
        catch(Exception e){

        }

    }
}