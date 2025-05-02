package com.example.connectmeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class Profile extends AppCompatActivity implements JsonResponse {
    ListView l;

    String[] fname,lname,place,email,phone,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        l=findViewById(R.id.view_profile);
        JsonReq JR=new JsonReq();
        JR.json_response=Profile.this;
        String q="/user_profile?lid="+Login.lid;
        JR.execute(q);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Choose option");

                String[] options = {"Edit profile"};

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            startActivity(new Intent(getApplicationContext(),edit_profile.class));
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
    try{
        String status=jo.getString("status");
        if(status.equalsIgnoreCase("success")){
            JSONArray ja1=(JSONArray) jo.getJSONArray("data");
           fname=new String[ja1.length()];
           lname=new String[ja1.length()];
            place=new String[ja1.length()];
            email=new String[ja1.length()];
            phone=new String[ja1.length()];
            result=new String[ja1.length()];

            for(int i=0;i<ja1.length();i++){
                fname[i]=ja1.getJSONObject(i).getString("first_name");
                lname[i]=ja1.getJSONObject(i).getString("last_name");
                place[i]=ja1.getJSONObject(i).getString("place");
                email[i]=ja1.getJSONObject(i).getString("email");
                phone[i]=ja1.getJSONObject(i).getString("phone");
                result[i]="first_name: "+fname[i]+"\nlast_name : "+lname[i]+"\nplace : "+place[i]+"\nemail : "+email[i]+"\nPhone : "+phone[i];

            }

//            l.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,result));

            Custom_profile ar=new Custom_profile(Profile.this,fname,lname,place,email,phone);
            l.setAdapter(ar);



        }


    }
    catch(Exception e){

    }

}


    }
