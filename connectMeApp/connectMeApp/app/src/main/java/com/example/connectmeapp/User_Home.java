package com.example.connectmeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class User_Home extends AppCompatActivity {


    Button b1, b2, b3, b4, b5, b6, b7,b8,b9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1 = findViewById(R.id.button2);
        b2 = findViewById(R.id.button3);
        b3 = findViewById(R.id.button4);
        b4 = findViewById(R.id.button5);
        b5 = findViewById(R.id.button6);
        b6 = findViewById(R.id.b7);
        b7 = findViewById(R.id.b8);
        b8 = findViewById(R.id.b9);
        b9 = findViewById(R.id.btnLogout);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), shop.class));


            }

        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), product.class));


            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), view_cart.class));


            }

        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), worker.class));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), view_user_request.class));
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), order_history.class));
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), complaint.class));
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }


    public boolean dispatchKeyEvent(KeyEvent event) {

        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode) {


            case KeyEvent.KEYCODE_VOLUME_DOWN:

                if (action == KeyEvent.ACTION_UP) {

                    if (event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout()) {
                        try {

//                            JsonReq JR = new JsonReq();
//                            JR.json_response = (JsonResponse) User_Home.this;
//                            String q = "/emergency?login_id="+sh.getString("log_id","") + "&lati=" + LocationService.lati + "&long=" + LocationService.logi;
//                            q = q.replace(" ", "%20");
//                            JR.execute(q);

//                            SmsManager sms = SmsManager.getDefault();
//                            sms.sendTextMessage(sh.getString("emergency", "121"), null, "I'm in a trouble, http://www.google.com/maps?q=" + LocationService.lati + "," + LocationService.logi, null, null);
//                            startActivity(new Intent(getApplicationContext(), User_Home.class));

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + "7356857440"));
                            startActivity(callIntent);

//                            startService(new Intent(getApplicationContext(),CameraService.class));
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }//TODO long click action
                        Toast.makeText(this, "Send Emergency Alert Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        //TODO click action

                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}





