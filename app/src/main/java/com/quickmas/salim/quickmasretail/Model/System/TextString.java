package com.quickmas.salim.quickmasretail.Model.System;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Forhad on 25/03/2018.
 */

public class TextString {
    public int id;
    public String varname;
    public String text;
    public static HashMap<String, String> global_var=new HashMap<String, String>();
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVarname() {
        return varname;
    }

    public String getText() {
        return text;
    }

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_text_id, this.getId());
        insertValues.put(db.COLUMN_text_varname, this.getVarname());
        insertValues.put(db.COLUMN_text_text, this.getText());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_TEXT,db.COLUMN_text_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_TEXT,db.COLUMN_text_id+"="+this.getId());
    }
    public static ArrayList<TextString> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_TEXT,con,"");
        ArrayList<TextString> texts = new ArrayList<TextString>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    TextString cText = new TextString();
                    cText.setId(c.getInt(c.getColumnIndex(db.COLUMN_text_id)));
                    cText.setVarname(c.getString(c.getColumnIndex(db.COLUMN_text_varname)));
                    cText.setText(c.getString(c.getColumnIndex(db.COLUMN_text_text)));
                    texts.add(cText);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return texts;
    }
    public static TextString textSelectByVarname(DBInitialization db,String varname)
    {
        String value = global_var.get(varname);
        TextString t = new TextString();
        if (value != null)
        {
            t.setId(0);
            t.setVarname(varname);
            t.setText(value);
        }
        else {

            t.setId(0);
            t.setVarname(varname);
            t.setText(varname);
        }
        return t;
    }
    public static TextString textSelectByVarnameDb(DBInitialization db,String varname)
    {
        try {
            TextString t = TextString.select(db, db.COLUMN_text_varname + "='" + varname + "'").get(0);
            return t;
        }
        catch (Exception e){
            TextString t = new TextString();
            t.setId(0);
            t.setVarname(varname);
            t.setText(varname);
            return t;
        }
    }
    public static void populateVarMap(DBInitialization db)
    {
        ArrayList<TextString> textStrings =  select(db,"1=1");
        for(TextString textString : textStrings)
        {
            global_var.put(textString.getVarname(),textString.getText());
        }
    }
}
