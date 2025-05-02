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


public class Custom_view_complaints extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] comp,reply,date;




    public Custom_view_complaints(Activity context, String[] comp, String[] reply, String[] date) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_view_complaints, comp);
        this.context = context;
        this.comp=comp;
        this.reply=reply;
        this.date=date;





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_view_complaints, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1=(TextView)listViewItem.findViewById(R.id.complaint);
        TextView t2=(TextView)listViewItem.findViewById(R.id.reply);
        TextView t3=(TextView)listViewItem.findViewById(R.id.date);






        t1.setText(comp[position]);
        t2.setText(reply[position]);
        t3.setText(date[position]);



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