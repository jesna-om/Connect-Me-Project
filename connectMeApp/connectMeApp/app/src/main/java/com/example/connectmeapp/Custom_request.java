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


public class Custom_request extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] title,amount,sts,date;




    public Custom_request(Activity context, String[] title, String[] amount, String[] sts, String[] date) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_request, title);
        this.context = context;
        this.title=title;
        this.amount=amount;
        this.sts=sts;
        this.date=date;





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_request, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1=(TextView)listViewItem.findViewById(R.id.title);
        TextView t2=(TextView)listViewItem.findViewById(R.id.amt);
        TextView t3=(TextView)listViewItem.findViewById(R.id.sts);
        TextView t4=(TextView)listViewItem.findViewById(R.id.date);






        t1.setText(title[position]);
        t2.setText(amount[position]);
        t3.setText(sts[position]);
        t4.setText(date[position]);



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