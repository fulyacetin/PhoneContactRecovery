package com.fulya.phonecontactrecovery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fulya on 10.04.2016.
 */
public class MyAdapter extends BaseAdapter {

    private List<ContactInformation> contactList;
    private int numberOfContacts;
    private LayoutInflater myInflater;

    public MyAdapter(Activity activity, int numberOfContacts,List<ContactInformation> contactList) {
        myInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contactList = contactList;
        this.numberOfContacts=numberOfContacts;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        rowView = myInflater.inflate(R.layout.listview_item, null);

        ImageView image =(ImageView) rowView.findViewById(R.id.contactImage);
        TextView name =(TextView) rowView.findViewById(R.id.contactName);
        TextView phone= (TextView) rowView.findViewById(R.id.contactPhoneNumber);

        ContactInformation person = contactList.get(position);
        image.setImageResource(R.drawable.abc);
        name.setText(person.getContactName());
        phone.setText(person.getContactPhoneNumber());

        return rowView;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
