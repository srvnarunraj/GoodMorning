package com.example.goodmorning;

import java.util.ArrayList;
import java.util.Date;

public class Contact
{
    public static ArrayList<Contact> ContactArrayList = new ArrayList<>();

    public static String Contact_EDIT_EXTRA =  "contactEdit";


    private int id;
    private String Number;
    private String msg;
    private Date deleted;

    public Contact(int id, String Number, String msg, Date deleted)
    {
        this.id = id;
        this.Number = Number;
        this.msg = msg;
        this.deleted = deleted;
    }

    public Contact(int id, String Number, String msg)
    {
        this.id = id;
        this.Number = Number;
        this.msg = msg;
        deleted = null;
    }

    public static Contact getContactForID(int passedContactID)
    {
        for (Contact Contact : ContactArrayList)
        {
            if(Contact.getId() == passedContactID)
                return Contact;
        }

        return null;
    }

    public static ArrayList<Contact> nonDeletedContacts()
    {
        ArrayList<Contact> nonDeleted = new ArrayList<>();
        for(Contact Contact : ContactArrayList)
        {
            if(Contact.getDeleted() == null)
                nonDeleted.add(Contact);
        }

        return nonDeleted;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNumber()
    {
        return Number;
    }

    public void setNumber(String Number)
    {
        this.Number = Number;
    }

    public String getmsg()
    {
        return msg;
    }

    public void setmsg(String msg)
    {
        this.msg = msg;
    }

    public Date getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Date deleted)
    {
        this.deleted = deleted;
    }
}

