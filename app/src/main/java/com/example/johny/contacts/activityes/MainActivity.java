package com.example.johny.contacts.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.johny.contacts.R;
import com.example.johny.contacts.adapters.ContactAdapter;
import com.example.johny.contacts.database.DBMain;
import com.example.johny.contacts.database.models.ContactModel;
import com.example.johny.contacts.webservice.WSMain;

import java.util.ArrayList;

import io.realm.RealmResults;

public class MainActivity extends RealmActivity implements WSMain.WSContactsListener {

    private ListView contactsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactsListView = (ListView) findViewById(R.id.contacts_list);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactModel contact = (ContactModel) view.getTag();

                Intent contactDetailsIntent = new Intent(MainActivity.this, ContactDetailsActivity.class);
                contactDetailsIntent.putExtra(ContactDetailsActivity.CONTACT_ID, contact.getId());
                startActivity(contactDetailsIntent);
            }
        });
        new WSMain().requestContacts(MainActivity.this, MainActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            new WSMain().requestContacts(this, this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContactsReceived(ArrayList<ContactModel> contactModels) {

        DBMain.saveUpdateContactList(realm, contactModels);
        setContactsOnListView();

    }

    @Override
    public void onContactReceivedError(VolleyError error) {
        setContactsOnListView();
    }

    private void setContactsOnListView(){
        RealmResults<ContactModel> contactsFromDB = DBMain.getAllContacts(realm);
        contactsListView.setAdapter(new ContactAdapter(this, contactsFromDB,true));
    }
}
