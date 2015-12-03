package com.example.johny.contacts.webservice.utils;

import com.example.johny.contacts.database.models.ContactModel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Johny on 03-Dec-15.
 */
public class ContactsDeserializer implements JsonDeserializer<ContactModel> {

    /**
     * Function to deserialize the json received from the server.
     * I have to do it manually because ContactModel extends RealmObject and the app freezes if I do it with "Gson.fromJson(json,Contact.class)"
     *
     * @param json
     * @param typeOfT
     * @param context
     * @return Returns a ContactModel.
     * @throws JsonParseException
     */
    @Override
    public ContactModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ContactModel contactModel = new ContactModel();

        JsonObject object = json.getAsJsonObject();

        contactModel.setFirst_name(chechkIfStringNull("first_name", object));
        contactModel.setSurname(chechkIfStringNull("surname", object));
        contactModel.setAddress(chechkIfStringNull("address", object));
        contactModel.setEmail(chechkIfStringNull("email", object));
        contactModel.setId(object.get("id").getAsInt());
        contactModel.setPhone_number(chechkIfStringNull("phone_number", object));
        contactModel.setCreatedAt(chechkIfStringNull("createdAt", object));
        contactModel.setUpdatedAt(chechkIfStringNull("updatedAt", object));

        return contactModel;
    }

    private String chechkIfStringNull(String fieldName, JsonObject jsonObject){
        if(jsonObject.get(fieldName).isJsonNull()){
            return  null;
        }
        return jsonObject.get(fieldName).getAsString();
    }


}
