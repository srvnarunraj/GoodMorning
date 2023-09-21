
package com.example.goodmorning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact>
{
    public ContactAdapter(Context context, List<Contact> Contacts)
    {
        super(context, 0, Contacts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Contact Contact = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        TextView Number = convertView.findViewById(R.id.phNumber);
        TextView desc = convertView.findViewById(R.id.phMsg);

        Number.setText(Contact.getNumber());
        desc.setText(Contact.getmsg());

        return convertView;
    }
}

