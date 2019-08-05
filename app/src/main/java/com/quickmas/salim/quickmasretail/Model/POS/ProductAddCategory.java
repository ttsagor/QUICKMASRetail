package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_stock_transfer_details_product_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_stock_transfer_details_transfer_id;

/**
 * Created by Forhad on 25/09/2018.
 */

public class ProductAddCategory {
    public int id;
    public String name;
    public String category;
    public String product_class;
    public String entry_by;
    public String dateTime;


    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0) {
            insertValues.put(db.COLUMN_product_add_category_id, this.getId());
        }
        insertValues.put(db.COLUMN_product_add_category_name, this.getName());
        insertValues.put(db.COLUMN_product_add_category_category, this.getCategory());
        insertValues.put(db.COLUMN_product_add_category_product_class, this.getProduct_class());
        insertValues.put(db.COLUMN_product_add_category_entry_by, this.getEntry_by());
        insertValues.put(db.COLUMN_product_add_category_date_time, this.getDateTime());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        String con = db.COLUMN_product_add_category_name+"='"+this.getName()+"' AND "+db.COLUMN_product_add_category_category+"='"+this.getCategory()+"' AND "+db.COLUMN_product_add_category_product_class+"='"+this.getClass()+"'";
        db.insertData(getContectValue(db),db.TABLE_product_add_category,con);
    }
    public void update(DBInitialization db)
    {
        String con = db.COLUMN_product_add_category_name+"='"+this.getName()+"' AND "+db.COLUMN_product_add_category_category+"='"+this.getCategory()+"' AND "+db.COLUMN_product_add_category_product_class+"='"+this.getClass()+"'";
        db.updateData(getContectValue(db),db.TABLE_product_add_category,con);
    }
    public static ArrayList<ProductAddCategory> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_product_add_category,con,"");
        ArrayList<ProductAddCategory> productAddCategories = new ArrayList<ProductAddCategory>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    ProductAddCategory productAddCategory = new ProductAddCategory();
                    productAddCategory.setId(c.getInt(c.getColumnIndex(db.COLUMN_product_add_category_id)));
                    productAddCategory.setName(c.getString(c.getColumnIndex(db.COLUMN_product_add_category_name)));
                    productAddCategory.setCategory(c.getString(c.getColumnIndex(db.COLUMN_product_add_category_category)));
                    productAddCategory.setProduct_class(c.getString(c.getColumnIndex(db.COLUMN_product_add_category_product_class)));
                    productAddCategory.setEntry_by(c.getString(c.getColumnIndex(db.COLUMN_product_add_category_entry_by)));
                    productAddCategory.setDateTime(c.getString(c.getColumnIndex(db.COLUMN_product_add_category_date_time)));
                    productAddCategories.add(productAddCategory);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return productAddCategories;
    }

    public static ArrayList<String> getCategory(DBInitialization db,String upTxt)
    {

        ArrayList<ProductAddCategory> productAddCategories =  select(db, db.COLUMN_product_add_category_category+"='Product Class'");
        ArrayList<String> category = new ArrayList<>();
        if(!upTxt.equals(""))
        {
            category.add(upTxt);
        }
        for(ProductAddCategory productAddCategory : productAddCategories)
        {
            if(!category.contains(productAddCategory.getName()))
            {
                category.add(productAddCategory.getName());
            }
        }
        return category;
    }

    public static ArrayList<String> getSubCategory(DBInitialization db,String subCategory,String upTxt)
    {

        ArrayList<ProductAddCategory> productAddCategories =  select(db, db.COLUMN_product_add_category_category+"='Product Category' AND "+db.COLUMN_product_add_category_product_class+"='"+subCategory+"'");
        ArrayList<String> category = new ArrayList<>();
        if(!upTxt.equals(""))
        {
            category.add(upTxt);
        }
        for(ProductAddCategory productAddCategory : productAddCategories)
        {
            if(!category.contains(productAddCategory.getName()))
            {
                category.add(productAddCategory.getName());
            }
        }
        return category;
    }

    public static ArrayList<String> getListData(DBInitialization db,String con,String upTxt,String endText)
    {

        ArrayList<ProductAddCategory> productAddCategories =  select(db, con);
        ArrayList<String> category = new ArrayList<>();
        if(!upTxt.equals(""))
        {
            category.add(upTxt);
        }
        for(ProductAddCategory productAddCategory : productAddCategories)
        {
            if(!category.contains(productAddCategory.getName()))
            {
                category.add(productAddCategory.getName());
            }
        }
        if(!endText.equals(""))
        {
            category.add(endText);
        }
        return category;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProduct_class(String product_class) {
        this.product_class = product_class;
    }

    public void setEntry_by(String entry_by) {
        this.entry_by = entry_by;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEntry_by() {
        return entry_by;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getProduct_class() {
        return product_class;
    }
}
