package com.example.connectmeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class service_n_charges extends AppCompatActivity implements JsonResponse {

    TextView t;
    ListView l;
    String[]  worker_id,service_name, description, amount, value,service_id;
    public static String amt,sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_ncharges);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        t = findViewById(R.id.t7);
        l = findViewById(R.id.l1);

        JsonReq JR = new JsonReq();
        JR.json_response = service_n_charges.this;
        String q = "/service_n_charges?worker_id="+worker.workerid;
        JR.execute(q);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                amt=amount[position];
                sid=service_id[position];

                final  CharSequence[] items ={"Request","Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(service_n_charges.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Request")){
                            startActivity(new Intent(getApplicationContext(),service_request.class));


                        }else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = jo.getJSONArray("data");
                int length = ja1.length();

                worker_id = new String[length];
                service_name = new String[length];
                description = new String[length];
                amount = new String[length];
                service_id = new String[length];
                value = new String[length];

                for (int i = 0; i < length; i++) {
                    worker_id[i] = ja1.getJSONObject(i).getString("worker_id");
                    service_name[i] = ja1.getJSONObject(i).getString("service_name");
                    description[i] = ja1.getJSONObject(i).getString("description");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    service_id[i] = ja1.getJSONObject(i).getString("service_id");

                    value[i] = "Worker ID: " + worker_id[i] + "\nService Name: " + service_name[i] + "\nDescription: " + description[i] + "\nAmount: " + amount[i];
                }

//                ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l.setAdapter(ar);

                Custom_service_charge ar=new Custom_service_charge(service_n_charges.this,service_name, description, amount);
                l.setAdapter(ar);

            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}


