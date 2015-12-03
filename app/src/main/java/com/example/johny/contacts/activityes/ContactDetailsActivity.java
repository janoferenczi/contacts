package com.example.johny.contacts.activityes;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.johny.contacts.R;
import com.example.johny.contacts.database.models.ContactModel;
import com.example.johny.contacts.webservice.WSMain;

/**
 * Created by Johny on 03-Dec-15.
 */
public class ContactDetailsActivity extends RealmActivity implements WSMain.WSImageListener {

    public static final String CONTACT_ID = "contactID";

    private TextView firstNameText;
    private TextView surnameText;
    private TextView addressText;
    private TextView phoneNumberText;
    private TextView emailText;
    private TextView createdAtText;
    private TextView updatedAtText;
    private ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstNameText = ((TextView) findViewById(R.id.first_name_text_view));
        surnameText = ((TextView) findViewById(R.id.surname_text_view));
        addressText = ((TextView) findViewById(R.id.address_text_view));
        phoneNumberText = ((TextView) findViewById(R.id.phone_number_text_view));
        emailText = ((TextView) findViewById(R.id.email_text_view));
        createdAtText = ((TextView) findViewById(R.id.created_at_text_view));
        updatedAtText = ((TextView) findViewById(R.id.updated_at_text_view));
        avatarImage = (ImageView) findViewById(R.id.avatar);
        setDetails();

    }

    /**
     * Sets up the views
     */
    private void setDetails() {
        int id = getIntent().getIntExtra(CONTACT_ID, 0);

        ContactModel contact = realm.where(ContactModel.class).equalTo("id", id).findFirst();

        firstNameText.setText(addSpaceInFront(contact.getFirst_name()));
        surnameText.setText(addSpaceInFront(contact.getSurname()));
        addressText.setText(addSpaceInFront(contact.getAddress()));
        phoneNumberText.setText(addSpaceInFront(contact.getPhone_number()));
        emailText.setText(addSpaceInFront(contact.getEmail()));
        createdAtText.setText(addSpaceInFront(contact.getCreatedAt()));
        updatedAtText.setText(addSpaceInFront(contact.getUpdatedAt()));

        new WSMain().requestImage(this, contact.getEmail(), 200, this);

    }

    /**
     * Puts a space in front of the string given and if it's null returns an empty string
     *
     * @param value
     * @return
     */
    private String addSpaceInFront(String value) {
        if (value == null) {
            return "";
        }
        return " " + value;
    }

    @Override
    public void onImageReceived(Bitmap bitmap) {
        avatarImage.setImageBitmap(bitmap);
    }
}
