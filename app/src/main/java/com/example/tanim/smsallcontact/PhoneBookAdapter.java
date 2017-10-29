package com.example.tanim.smsallcontact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tanim on 9/4/2017.
 */

public class PhoneBookAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;

    private List<PhoneBook> listPhonebook;

    public PhoneBookAdapter(Phone context, List<PhoneBook> listPhonebook) {
        this.context = context;
        this.listPhonebook = listPhonebook;
    }

    public int getCount() {
        return listPhonebook.size();
    }

    public Object getItem(int position) {
        return listPhonebook.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        PhoneBook entry = listPhonebook.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contact, null);
        }
        TextView tvContact = (TextView) convertView.findViewById(R.id.tvContact);
        tvContact.setText(entry.getName());

        TextView tvPhone = (TextView) convertView.findViewById(R.id.tvMobile);
        tvPhone.setText(entry.getPhone());


        // Set the onClick Listener on this button
        //Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
        //btnRemove.setFocusableInTouchMode(false);
        //btnRemove.setFocusable(false);
        //btnRemove.setOnClickListener(this);
        // Set the entry, so that you can capture which item was clicked and
        // then remove it
        // As an alternative, you can use the id/position of the item to capture
        // the item
        // that was clicked.
        //btnRemove.setTag(entry);

        // btnRemove.setId(position);


        return convertView;
    }

    @Override
    public void onClick(View view) {
        PhoneBook entry = (PhoneBook) view.getTag();
        listPhonebook.remove(entry);
        // listPhonebook.remove(view.getId());
        notifyDataSetChanged();

    }

    private void showDialog(PhoneBook entry) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }
}
