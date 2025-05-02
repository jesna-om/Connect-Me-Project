package com.example.connectmeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class view_user_request extends AppCompatActivity implements JsonResponse {
    TextView t;
    ListView l1;

    String[]  title,amount,sts,date,result,request_id;

    public static String reqid,amot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_user_request);

        t = findViewById(R.id.textView5);

        l1=findViewById(R.id.requestview);


        JsonReq JR=new JsonReq();
        JR.json_response=view_user_request.this;
        String q="/user_view_request?id=" + Login.lid;
        JR.execute(q);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                amot=amount[position];
                reqid=request_id[position];

                final  CharSequence[] items ={"pay","Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view_user_request.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("pay")){
                            Intent il=new Intent(getApplicationContext(), service_payment.class);
                            startActivity(il);

                        }
                        else if (items[item].equals("cancel")){
                            Intent il=new Intent(getApplicationContext(), view_user_request.class);
                            startActivity(il);

                        }
                    }
                });
                builder.show();

            }
        });




    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");

            if(status.equalsIgnoreCase("Success")){
                JSONArray ja1=(JSONArray) jo.getJSONArray("data");
                title=new String[ja1.length()];
                amount=new String[ja1.length()];
                sts=new String[ja1.length()];
                date=new String[ja1.length()];
                request_id=new String[ja1.length()];
                result=new String[ja1.length()];

                for(int i=0;i<ja1.length();i++){
                    title[i]=ja1.getJSONObject(i).getString("title");
                    amount[i]=ja1.getJSONObject(i).getString("amount");
                    sts[i]=ja1.getJSONObject(i).getString("status");
                    date[i]=ja1.getJSONObject(i).getString("date");
                    request_id[i]=ja1.getJSONObject(i).getString("request_id");

                    result[i]="Title : "+title[i]+"\namount : "+amount[i]+"\nStatus : "+sts[i]+"\nDate : "+date[i];

                }

//                ArrayAdapter<String> ar=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,result);
//                l1.setAdapter(ar);

                Custom_request ar=new Custom_request(view_user_request.this,title,amount,sts,date);
                l1.setAdapter(ar);

            }else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }


        }
        catch(Exception e){

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
}
