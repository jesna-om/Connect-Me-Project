package com.example.connectmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class complaint extends AppCompatActivity implements JsonResponse {
    EditText e1;
    ListView l1;

    Button b;
    String[] date,reply,comp,value;
    String complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_complaint);

        e1=findViewById(R.id.mt);
        b=findViewById(R.id.bt);
        l1=findViewById(R.id.list);

        JsonReq JR=new JsonReq();
        JR.json_response=complaint.this;
        String q="/user_view_complaints?user_l_id="+Login.lid;
        JR.execute(q);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint=e1.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=complaint.this;
                String q="/user_complaint?c="+complaint+"&user_l_id="+Login.lid;
                JR.execute(q);


            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            String status = jo.getString("status");
            if (method.equalsIgnoreCase("send")) {
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(this, "complaint send successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), complaint.class));

                } else {
                    Toast.makeText(this, "complaint send failed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), complaint.class));
                }
            }
            if (method.equalsIgnoreCase("view")){
                if(status.equalsIgnoreCase("status"));
                {
                    JSONArray ja1 = jo.getJSONArray("data");

                    int length = ja1.length();

                    date = new String[length];
                    reply = new String[length];
                    comp = new String[length];
                    value = new String[length];


                    for(int i=0;i<length;i++){

                        date[i] = ja1.getJSONObject(i).getString("date");
                        reply[i] = ja1.getJSONObject(i).getString("reply");
                        comp[i] = ja1.getJSONObject(i).getString("complaint");

                        value[i] = "Date:" + date[i]+ "\nComplaint:" + comp[i]+ "\nReply:" + reply[i] ;


                    }

//                    ArrayAdapter<String> ar=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
//                    l1.setAdapter(ar);

                    Custom_view_complaints ar=new Custom_view_complaints(complaint.this,comp,reply,date);
                    l1.setAdapter(ar);

                }


            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}