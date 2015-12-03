package com.example.johny.contacts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.johny.contacts.R;
import com.example.johny.contacts.database.models.ContactModel;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Johny on 03-Dec-15.
 */
public class ContactAdapter extends RealmBaseAdapter<ContactModel> {

    public ContactAdapter(Context context, RealmResults<ContactModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
        }

        ContactModel contact = realmResults.get(position);
        TextView contactNameTextView = (TextView) convertView.findViewById(R.id.list_item_contact_name);
        contactNameTextView.setText(contact.getFirst_name() + " " + contact.getSurname());

        convertView.setTag(contact);

        return convertView;
    }
}
