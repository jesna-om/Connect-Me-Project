package com.example.connectmeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

//import com.squareup.picasso.Picasso;


public class Custom_service_charge extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] service_name, description, amount;




    public Custom_service_charge(Activity context, String[] service_name, String[] description, String[] amount) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_service_charge, service_name);
        this.context = context;
        this.service_name=service_name;
        this.description=description;
        this.amount=amount;





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_service_charge, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1=(TextView)listViewItem.findViewById(R.id.service);
        TextView t2=(TextView)listViewItem.findViewById(R.id.des);
        TextView t3=(TextView)listViewItem.findViewById(R.id.chrg);






        t1.setText(service_name[position]);
        t2.setText(description[position]);
        t3.setText(amount[position]);



        sh=PreferenceManager.getDefaultSharedPreferences(getContext());


//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
//        Picasso.with(context)
//                .load(pth)
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//                .into(img1);




        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }





    private void startActivity(Intent intent) {
    }


    @Override
    public void response(JSONObject jo) {

    }
}