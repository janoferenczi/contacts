package com.example.johny.contacts.database;


import com.example.johny.contacts.database.models.ContactModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Johny on 03-Dec-15.
 */
public class DBMain{


    /**
     * Method to save or update contactList given as parameter.
     * I do it in main thread because Realm works really fast and there is no need to do it on a secondary thread.
     * @param realm
     * @param contactList
     */
    public static void saveUpdateContactList(Realm realm, ArrayList<ContactModel> contactList){
        realm.beginTransaction();
        for(ContactModel contact:contactList){
            ContactModel contactFromDB = realm.where(ContactModel.class).equalTo("id",contact.getId()).findFirst();
            if(contactFromDB == null){
                realm.copyToRealm(contact);
            }else{
                contactFromDB.setFirst_name(contact.getFirst_name());
                contactFromDB.setSurname(contact.getSurname());
                contactFromDB.setAddress(contact.getAddress());
                contactFromDB.setPhone_number(contact.getPhone_number());
                contactFromDB.setEmail(contact.getEmail());
                contactFromDB.setCreatedAt(contact.getCreatedAt());
                contactFromDB.setUpdatedAt(contact.getUpdatedAt());
            }
        }

        realm.commitTransaction();
    }

    /**
     * Method to receive all the Contacts.
     * I do this too on main thread because the Realm is really fast and it`s not allowed to pass a RealmList between threads.
     * @param realm
     * @return
     */
    public static RealmResults<ContactModel> getAllContacts(Realm realm){
        return realm.where(ContactModel.class).findAll();
    }

}
