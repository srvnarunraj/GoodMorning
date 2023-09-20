package com.example.goodmorning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText editPhoneNumber;
    private EditText editMessage;
    private Button buttonSave;
    private Button buttonDelete;

    private int contactPosition; // To identify the position of the contact being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editMessage = findViewById(R.id.editMessage);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);

        // Get the contact details and position from the intent
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String message = getIntent().getStringExtra("message");
        contactPosition = getIntent().getIntExtra("position", -1);

        editPhoneNumber.setText(phoneNumber);
        editMessage.setText(message);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the edited contact details
                String editedPhoneNumber = editPhoneNumber.getText().toString();
                String editedMessage = editMessage.getText().toString();

                // Set the edited data as the result to be sent back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedPhoneNumber", editedPhoneNumber);
                resultIntent.putExtra("editedMessage", editedMessage);
                resultIntent.putExtra("position", contactPosition);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the delete action and the position to be deleted as the result
                Intent resultIntent = new Intent();
                resultIntent.putExtra("deletePosition", contactPosition);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
