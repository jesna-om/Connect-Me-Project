package com.example.connectmeapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

//import com.squareup.picasso.Picasso;


public class Custom_profile extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] fname,lname,place,email,phone;




    public Custom_profile(Activity context, String[] fname, String[] lname, String[] place, String[] email, String[] phone) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_profile, fname);
        this.context = context;
        this.fname=fname;
        this.lname=lname;
        this.place=place;
        this.email=email;
        this.phone=phone;





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_profile, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1=(TextView)listViewItem.findViewById(R.id.fname);
        TextView t2=(TextView)listViewItem.findViewById(R.id.lname);
        TextView t3=(TextView)listViewItem.findViewById(R.id.phone);
        TextView t4=(TextView)listViewItem.findViewById(R.id.email);
        TextView t5=(TextView)listViewItem.findViewById(R.id.place);






        t1.setText(fname[position]);
        t2.setText(lname[position]);
        t3.setText(phone[position]);
        t4.setText(email[position]);
        t5.setText(place[position]);



        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

//        String pth = "http://"+sh.getString("ip", "")+"/"+complaints[position];
//        pth = pth.replace("~", "");
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