package com.example.tanim.smsallcontact;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.R.id.list;


/**
 * Created by tanim on 9/4/2017.
 */

public class Phone extends AppCompatActivity {



    ProgressDialog dialog;
    List<Integer> sims;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        new shlowContactList().execute();

        Log.d("List: " , "okok");

        //listOfPhonebook = sendMessage(listOfPhonebook);
        //listOfPhonebook.add(new PhoneBook("Tanim","1234"));
        //listOfPhonebook.add(new PhoneBook("Tanim","1234"));


    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void GetCarriorsInformation(){
        try {

            sims = new ArrayList<Integer>();
            SubscriptionManager mSubscriptionManager = SubscriptionManager.from(getApplication());

            List<SubscriptionInfo> subInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
            //Log.d("SIms  :", "ok");
            for (int i = 0; i < subInfoList.size(); i++) {
                sims.add(subInfoList.get(i).getSubscriptionId());
                Log.d("SIms  :", sims.get(i).toString());
            }
        }
        catch (Exception e)
        {
            Log.e("ok",e.getMessage());

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    protected void sendMessage(final List<PhoneBook> listOfPhonebook)
    {

        GetCarriorsInformation();

        TelephonyManager tManager = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

// Get Mobile No
        String pNumber= tManager.getLine1Number();

// Get carrier name (Network Operator Name)
        String networkOperatorName= tManager.getNetworkOperatorName();

        Log.d("operator " , networkOperatorName );

        //String optName1 = getOutput(getApplicationContext(), "getCarrierName", 0);
        // Sim One
        //SmsManager sm = SmsManager.getSmsManagerForSubscriptionId(sims.get(0));
        //sm.sendTextMessage("01750202379", null, "01521455796", null, null);

        // Sim Two
        //SmsManager sm = SmsManager.getSmsManagerForSubscriptionId(sims.get(1));

        ListView list = (ListView) findViewById(R.id.contact_view);
        list.setClickable(true);

        Collections.sort(listOfPhonebook, new Comparator<PhoneBook>(){
            public int compare(PhoneBook obj1, PhoneBook obj2) {
                // ## Ascending order
                return obj1.getName().compareToIgnoreCase(obj2.getName()); // To compare string values
                // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });

        PhoneBookAdapter adapter = new PhoneBookAdapter(this, listOfPhonebook);

        //final List<PhoneBook> finalListOfPhonebook = listOfPhonebook;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                showToast(listOfPhonebook.get(position).getName());
            }
        });

        list.setAdapter(adapter);
    }

    private class shlowContactList extends AsyncTask<Void, Void,List<PhoneBook> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Phone.this);
            dialog.setTitle("Please Wait");
            dialog.show();
            //List<Phone> listOfPhonebook = new ArrayList<PhoneBook>();

        }

        @Override
        protected List<PhoneBook> doInBackground(Void... params) {

            List<PhoneBook> listOfPhonebook = new ArrayList<PhoneBook>();

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
                            listOfPhonebook.add(new PhoneBook(name,phoneNo));
                            //Toast.makeText(getApplicationContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                            Log.d(i+"/ " , name + " " + phoneNo );
                            i++;
                            try {
                                //smsManager = SmsManager.getDefault();
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
            return listOfPhonebook;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        protected void onPostExecute(List<PhoneBook> listOfPhonebook ) {
            super.onPostExecute(listOfPhonebook);
            dialog.dismiss();

            for (int i=0;i<listOfPhonebook.size();i++)
            {
                Log.d("CHecking: ",listOfPhonebook.get(i).getName());
            }
            sendMessage(listOfPhonebook);

        }
    }
}
