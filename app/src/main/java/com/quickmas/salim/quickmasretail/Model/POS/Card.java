package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 01/06/2018.
 */

public class Card {
    int id;
    String cardName;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_card_id, this.getId());
        insertValues.put(db.COLUMN_card_name, this.getCardName());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_card,db.COLUMN_card_id+"="+this.getId());
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_card,db.COLUMN_card_id+"="+this.getId());
    }
    public static ArrayList<Card> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_card,con,"");
        ArrayList<Card> cards = new ArrayList<Card>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Card card = new Card();
                    card.setId(c.getInt(c.getColumnIndex(db.COLUMN_card_id)));
                    card.setCardName(c.getString(c.getColumnIndex(db.COLUMN_card_name)));
                    cards.add(card);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return cards;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getId() {
        return id;
    }

    public String getCardName() {
        return cardName;
    }
}
