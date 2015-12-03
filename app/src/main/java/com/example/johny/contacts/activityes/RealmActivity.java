package com.example.johny.contacts.activityes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

/**
 * Created by Johny on 03-Dec-15.
 *
 * This class will be extended by the activities that will use a realm instance.
 */
public class RealmActivity extends AppCompatActivity {

    protected Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.realm = Realm.getInstance(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
