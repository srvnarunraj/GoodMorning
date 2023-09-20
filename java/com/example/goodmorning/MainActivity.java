package com.example.goodmorning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contacts = new ArrayList<>();
    private static final int EDIT_CONTACT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add contacts with unique IDs
        contacts.add(new Contact("+XXXXXXXXXX", "Good Morning Bro", 1));
        contacts.add(new Contact("+XXXXXXXXXX", "Good Morning Mother", 2));
        contacts.add(new Contact("+XXXXXXXXXX", "Good Morning Father", 3));
        contacts.add(new Contact("+XXXXXXXXXX", "Good Morning Sister", 4));

        contactAdapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(contactAdapter);

        Button buttonSendMessages = findViewById(R.id.buttonSendMessages);
        buttonSendMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Contact contact : contacts) {
                    sendWhatsAppMessage(contact.getPhoneNumber(), contact.getMessage());
                }
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_CONTACT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Check if it's a delete action
                if (data.hasExtra("deletePosition")) {
                    int deletePosition = data.getIntExtra("deletePosition", -1);
                    if (deletePosition != -1) {
                        // Remove the contact from the list and update the adapter
                        int position = deletePosition;
                        contacts.remove(position);
                        contactAdapter.notifyItemRemoved(position);
                    }
                }
            }
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

        private List<Contact> contacts;

        public ContactAdapter(List<Contact> contacts) {
            this.contacts = contacts;
        }

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            Contact contact = contacts.get(position);
            holder.textContact.setText(contact.getPhoneNumber());
            holder.textMessage.setText(contact.getMessage());

            // Set a click listener for the "Edit" button
            holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Start EditActivity to edit the contact
                    Intent editIntent = new Intent(MainActivity.this, EditActivity.class);
                    editIntent.putExtra("phoneNumber", contact.getPhoneNumber());
                    editIntent.putExtra("message", contact.getMessage());
                    editIntent.putExtra("position", holder.getAdapterPosition());
                    startActivityForResult(editIntent, EDIT_CONTACT_REQUEST_CODE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }
    }

    private class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textContact;
        TextView textMessage;
        Button buttonEdit;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textContact = itemView.findViewById(R.id.textContact);
            textMessage = itemView.findViewById(R.id.textMessage);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
}
