package com.example.goodmorning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private EditText NumberEditText, msgEditText;
    private Button deleteButton;
    private Contact selectedContact;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Spinner contactSpinner;
    private ArrayAdapter<String> contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initWidgets();
        checkForEditContact();

        // Request permission to read contacts if not already granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            // Permission already granted, load contacts
//            loadContacts();
        }
    }

    private void initWidgets() {
        NumberEditText = findViewById(R.id.phNumberEdit);
        msgEditText = findViewById(R.id.phMsgEdit);
        deleteButton = findViewById(R.id.deleteContactButton);
        contactSpinner = findViewById(R.id.contactSpinner);
    }

    private void checkForEditContact() {
        Intent previousIntent = getIntent();
        int passedContactID = previousIntent.getIntExtra(Contact.Contact_EDIT_EXTRA, -1);
        selectedContact = Contact.getContactForID(passedContactID);

        if (selectedContact != null) {
            // Set the Number (phone number) in NumberEditText
            NumberEditText.setText(selectedContact.getNumber());
            msgEditText.setText(selectedContact.getmsg());
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }


    public void saveContact(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String Number = String.valueOf(NumberEditText.getText());
        String msg = String.valueOf(msgEditText.getText());

        if(selectedContact == null)
        {
            int id = Contact.ContactArrayList.size();
            Contact newContact = new Contact(id, Number, msg);
            Contact.ContactArrayList.add(newContact);
            sqLiteManager.addContactToDatabase(newContact);
        }
        else
        {
            selectedContact.setNumber(Number);
            selectedContact.setmsg(msg);
            sqLiteManager.updateContactInDB(selectedContact);
        }

        finish();
    }

    public void deleteContact(View view) {
        selectedContact.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateContactInDB(selectedContact);
        finish();
    }

}
