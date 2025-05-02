package com.example.connectmeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

//import com.squareup.picasso.Picasso;


public class Custom_product extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] pname, shopname, price, qty, img, des;
    ImageView img1;




    public Custom_product(Activity context, String[] pname, String[] shopname, String[] price, String[] qty, String[] img, String[] des) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_product, pname);
        this.context = context;
        this.pname=pname;
        this.shopname=shopname;
        this.price=price;
        this.qty=qty;
        this.img=img;
        this.des=des;





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_product, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1=(TextView)listViewItem.findViewById(R.id.pname);
        TextView t2=(TextView)listViewItem.findViewById(R.id.shopname);
        ImageView t3=(ImageView) listViewItem.findViewById(R.id.img);
        TextView t4=(TextView)listViewItem.findViewById(R.id.des);
        TextView t5=(TextView)listViewItem.findViewById(R.id.price);
        TextView t6=(TextView)listViewItem.findViewById(R.id.qty);


        t1.setText(pname[position]);
        t2.setText(shopname[position]);

        t4.setText(des[position]);
        t5.setText(price[position]);
        t6.setText(qty[position]);



        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+img[position];
        pth = pth.replace("~", "");
        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(t3);
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