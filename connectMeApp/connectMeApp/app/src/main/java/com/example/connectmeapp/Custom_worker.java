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


public class Custom_worker extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] fname,lname,place,phone,email,work;




    public Custom_worker(Activity context, String[] fname, String[] lname, String[] place, String[] phone, String[] email, String[] work) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_worker, fname);
        this.context = context;
        this.fname=fname;
        this.lname=lname;
        this.place=place;
        this.phone=phone;
        this.email=email;
        this.work=work;





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_worker, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1=(TextView)listViewItem.findViewById(R.id.fname);
        TextView t2=(TextView)listViewItem.findViewById(R.id.lname);
        TextView t3=(TextView)listViewItem.findViewById(R.id.work);
        TextView t4=(TextView)listViewItem.findViewById(R.id.phone);
        TextView t5=(TextView)listViewItem.findViewById(R.id.email);
        TextView t6=(TextView)listViewItem.findViewById(R.id.place);






        t1.setText(fname[position]);
        t2.setText(lname[position]);
        t3.setText(work[position]);
        t4.setText(phone[position]);
        t5.setText(email[position]);
        t6.setText(place[position]);



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