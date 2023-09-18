package com.example.goodmorning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSendMessages = findViewById(R.id.buttonSendMessages);

        buttonSendMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] contacts = {
                        "+XXXXXXXXXX",
                        "+XXXXXXXXXX",
                        "+XXXXXXXXXX",
                        "+XXXXXXXXXX",
                };

                String[] messages = {
                        "Good Morning Bro",
                        "Good Morning Mother",
                        "Good Morning Father",
                        "Good Morning Sister",
                };

                for (int i = 0; i < contacts.length; i++) {
                    System.out.println(i);
                    sendWhatsAppMessage(contacts[i], messages[i]);
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

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // The user clicked the send button
            } else {
                // The user did not click the send button
            }
        }
    }
}