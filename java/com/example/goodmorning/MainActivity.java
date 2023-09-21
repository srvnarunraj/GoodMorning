package com.example.goodmorning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private ListView ContactListView;
    private Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setContactAdapter();
        setOnClickListener();
    }


    private void initWidgets()
    {
        ContactListView = findViewById(R.id.AutomateListView);
        sendButton = findViewById(R.id.Send);
    }

    private void loadFromDBToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateContactListArray();
    }

    private void setContactAdapter()
    {
        ContactAdapter ContactAdapter = new ContactAdapter(getApplicationContext(), Contact.nonDeletedContacts());
        ContactListView.setAdapter(ContactAdapter);
    }


    private void setOnClickListener()
    {
        ContactListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Contact selectedContact = (Contact) ContactListView.getItemAtPosition(position);
                Intent editContactIntent = new Intent(getApplicationContext(), EditActivity.class);
                editContactIntent.putExtra(Contact.Contact_EDIT_EXTRA, selectedContact.getId());
                startActivity(editContactIntent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to retrieve and display the data
                displayPhoneNumbersAndMessages();
            }
        });
    }


    public void newContact(View view)
    {
        Intent newContactIntent = new Intent(this, EditActivity.class);
        startActivity(newContactIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setContactAdapter();
    }
    private void displayPhoneNumbersAndMessages() {
        for (Contact Contact : Contact.nonDeletedContacts()) {
            String phoneNumber = Contact.getNumber(); // Get the phone number from the title field
            String messageText = Contact.getmsg(); // Get the message from the description field

            // Check if both phone number and message are not null or empty
            if (phoneNumber != null && !phoneNumber.isEmpty() && messageText != null && !messageText.isEmpty()) {
                // Call the sendWhatsAppMessage method with phone number and message
                sendWhatsAppMessage(phoneNumber, messageText);
            }
        }

        // You can choose to display a confirmation message here if needed.
    }

    private void sendWhatsAppMessage(String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://wa.me/" + phoneNumber + "/?text=" + message));
        intent.setPackage("com.whatsapp");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", message);
        int requestCode = 1;
        startActivityForResult(intent, requestCode);
    }

}
