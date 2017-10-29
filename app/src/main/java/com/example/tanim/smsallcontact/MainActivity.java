package com.example.tanim.smsallcontact;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.phoneNumber;



public class MainActivity extends AppCompatActivity {

    Button sendButton;
    TextView messageTextView;
    String message ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String sms = "";
        sendButton = (Button) findViewById(R.id.send_button);
        messageTextView = (EditText) findViewById(R.id.message);



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = messageTextView.getText().toString();
                Intent intent = new Intent(MainActivity.this,Phone.class);
                startActivity(intent);
                //sendMessage();
            }
        });



    }
    protected void sendMessage()
    {
        //SubscriptionInfo localSubscriptionInfo = (SubscriptionInfo)localIterator.next();
        //localSubscriptionInfo.getSubscriptionId();
        SmsManager smsManager = null;

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        int i =0;

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Toast.makeText(getApplicationContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        Log.d(i+"/ " , name + " " + phoneNo );
                        i++;
                        try {
                            smsManager = SmsManager.getDefault();
                            //smsManager.sendTextMessage(phoneNo,null,message,null,null);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                    pCur.close();
                }
            }
        }
    }


}

