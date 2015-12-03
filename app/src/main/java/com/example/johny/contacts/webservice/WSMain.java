package com.example.johny.contacts.webservice;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.johny.contacts.database.models.ContactModel;
import com.example.johny.contacts.webservice.utils.ContactsDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Johny on 03-Dec-15.
 */
public class WSMain {

    private final String contactsURL = "http://fast-gorge.herokuapp.com/contacts";
    private final String imageURL = "http://api.adorable.io/avatars";


    /**
     * Method to fetch the contacts from the WS
     * @param context
     * @param contactsListener
     */
    public void requestContacts(Context context, final WSContactsListener contactsListener){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, contactsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().registerTypeAdapter(ContactModel.class, new ContactsDeserializer()).create();
                Type listType = new TypeToken<List<ContactModel>>(){}.getType();
                ArrayList<ContactModel> contactList =  gson.fromJson(response, listType);
                if( contactsListener!= null ){
                    contactsListener.onContactsReceived(contactList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (contactsListener != null){
                    contactsListener.onContactReceivedError(error);
                }
            }
        });

        queue.add(request);
        queue.start();
    }


    /**
     * Method to download image for email adress given
     * @param context
     * @param email
     * @param imageSize
     * @param imageListener
     */
    public void requestImage(Context context, String email, int imageSize, final WSImageListener imageListener){

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = imageURL + "/" + imageSize + "/" + email;

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if (imageListener != null) {
                    imageListener.onImageReceived(response);
                }
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.toString();
            }
        });

        queue.add(imageRequest);
        queue.start();
    }


    public interface WSContactsListener{

        void onContactsReceived(ArrayList<ContactModel> contactModels);

        void onContactReceivedError(VolleyError error);
    }

    public interface WSImageListener {

        void onImageReceived(Bitmap bitmap);

    }

}
