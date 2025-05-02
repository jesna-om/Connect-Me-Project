package com.example.connectmeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class worker extends AppCompatActivity implements JsonResponse{

    ListView l1;

    SearchView s1;
    String[] fname,lname,place,phone,email,work,worker_id,result,latitude,longitude;

    public  static  String workerid;

    public static String llati,llongi;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_worker);


        startService(new Intent(getApplicationContext(), LocationService.class));
        l1=findViewById(R.id.view_worker);
        llati=LocationService.lati;
        llongi=LocationService.logi;;
        Toast.makeText(this,llati.toString(), Toast.LENGTH_SHORT).show();


        s1 = findViewById(R.id.search);

        s1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendSearchRequest(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()) {
                    sendDefaultRequest(); // Show all workers when search is cleared
                } else {
                    sendSearchRequest(newText);
                }
                return true;
            }
        });




        JsonReq JR=new JsonReq();
        JR.json_response=worker.this;
            String q="/user_worker?lati=" + llati+ "&user_id="+ Login.uid;
        JR.execute(q);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                workerid=worker_id[position];

                final  CharSequence[] items ={"Service and Charges","Cancel","Add Review"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(worker.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Service and Charges")){
                            Intent il=new Intent(getApplicationContext(), service_n_charges.class);
                            startActivity(il);


                        }else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        } else if (items[item].equals("Add Review")) {
                            startActivity(new Intent(getApplicationContext(),WorkerReview.class));
                        }
                    }
                });
                builder.show();

            }
        });

    }


    private void sendSearchRequest(String query) {
        // Replace spaces with %20 for URL encoding (if necessary)
        String encodedQuery = query.replace(" ", "%20");

        // Construct the request URL
        String q = "/user_search_workers?search=" + encodedQuery;

        // Initialize and execute the request
        JsonReq jr = new JsonReq();
        jr.json_response = worker.this;
        jr.execute(q);
    }

    private void sendDefaultRequest() {
//        JsonReq jr = new JsonReq();
//        jr.json_response = worker.this;
//        String q = "/user_worker";
//        jr.execute(q);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) worker.this;
        String q = "/user_worker?";
        Log.d("qqq",q);
        q = q.replace(" ", "%20");
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            String method = jo.getString("method");

            if (method.equalsIgnoreCase("user_view_workers")) {
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    fname = new String[ja1.length()];
                    lname = new String[ja1.length()];
                    place = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    email = new String[ja1.length()];
                    work = new String[ja1.length()];
                    latitude=new String[ja1.length()];
                    longitude=new String[ja1.length()];
                    worker_id = new String[ja1.length()];
                    result = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        fname[i] = ja1.getJSONObject(i).getString("first_name");
                        lname[i] = ja1.getJSONObject(i).getString("last_name");
                        place[i] = ja1.getJSONObject(i).getString("place");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        work[i] = ja1.getJSONObject(i).getString("work");
                        worker_id[i] = ja1.getJSONObject(i).getString("worker_id");
                        latitude[i]=ja1.getJSONObject(i).getString("latitude");
                        longitude[i]=ja1.getJSONObject(i).getString("longitude");


                        result[i] = "First Name : " + fname[i] + "Last Name : " + lname[i] + "\nPlace : " + place[i] + "\nphone : " + phone[i] + "\nemail : " + email[i] + "\nwork : " + work[i];

                    }

//                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, result));

                    Custom_worker ar=new Custom_worker(worker.this,fname,lname,place,phone,email,work);
                    l1.setAdapter(ar);


                }


            }
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
}


