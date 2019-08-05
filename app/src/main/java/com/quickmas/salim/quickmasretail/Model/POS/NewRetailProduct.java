package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_invoiceID;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_PosInvoice;

/**
 * Created by Forhad on 27/09/2018.
 */

public class NewRetailProduct {
    public int id;
    public int entry_id;
    public String category;
    public String sub_category;
    public String sku;
    public String title;
    public String description;
    public String weight;
    public String dimension;
    public String accessories;
    public String warrenty;
    public String image_group;
    public String images;
    public String manufacturer;
    public String brand;
    public String market_place;
    public double approximate_cost;
    public double whole_sale_price;
    public double retail_price;
    public String purchase_tax_group;
    public String sales_tax_group;
    public String entry_by;
    public String entry_date_time;
    public int status;



    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        if(this.getId()>0)
        {
            insertValues.put(db.COLUMN_new_retail_product_id, this.getId());
        }
        insertValues.put(db.COLUMN_new_retail_product_entry_id, this.getEntry_id());
        insertValues.put(db.COLUMN_new_retail_product_category, this.getCategory());
        insertValues.put(db.COLUMN_new_retail_product_sub_category, this.getSub_category());
        insertValues.put(db.COLUMN_new_retail_product_sku, this.getSku());
        insertValues.put(db.COLUMN_new_retail_product_title, this.getTitle());
        insertValues.put(db.COLUMN_new_retail_product_description, this.getDescription());
        insertValues.put(db.COLUMN_new_retail_product_weight, this.getWeight());
        insertValues.put(db.COLUMN_new_retail_product_dimension, this.getDimension());
        insertValues.put(db.COLUMN_new_retail_product_accessories, this.getAccessories());
        insertValues.put(db.COLUMN_new_retail_product_warrenty, this.getWarrenty());
        insertValues.put(db.COLUMN_new_retail_product_image_group, this.getImage_group());
        insertValues.put(db.COLUMN_new_retail_product_images, this.getImages());
        insertValues.put(db.COLUMN_new_retail_product_manufacturer, this.getManufacturer());
        insertValues.put(db.COLUMN_new_retail_product_brand, this.getBrand());
        insertValues.put(db.COLUMN_new_retail_product_market_place, this.getMarket_place());
        insertValues.put(db.COLUMN_new_retail_product_approximate_cost, this.getApproximate_cost());
        insertValues.put(db.COLUMN_new_retail_product_whole_sale_price, this.getWhole_sale_price());
        insertValues.put(db.COLUMN_new_retail_product_retail_price, this.getRetail_price());
        insertValues.put(db.COLUMN_new_retail_product_purchase_tax_group, this.getPurchase_tax_group());
        insertValues.put(db.COLUMN_new_retail_product_sales_tax_group, this.getSales_tax_group());
        insertValues.put(db.COLUMN_new_retail_product_entry_by, this.getEntry_by());
        insertValues.put(db.COLUMN_new_retail_product_entry_date_time, this.getEntry_date_time());
        insertValues.put(db.COLUMN_new_retail_product_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_new_retail_product,db.COLUMN_new_retail_product_sku+"='"+this.getSku()+"'");
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_new_retail_product,db.COLUMN_new_retail_product_sku+"='"+this.getSku()+"'");
    }
    public static ArrayList<NewRetailProduct> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_new_retail_product,con,"");
        ArrayList<NewRetailProduct> newRetailProducts = new ArrayList<NewRetailProduct>();
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    NewRetailProduct newRetailProduct = new NewRetailProduct();
                    newRetailProduct.setId(c.getInt(c.getColumnIndex(db.COLUMN_new_retail_product_id)));
                    newRetailProduct.setEntry_id(c.getInt(c.getColumnIndex(db.COLUMN_new_retail_product_entry_id)));
                    newRetailProduct.setCategory(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_category)));
                    newRetailProduct.setSub_category(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_sub_category)));
                    newRetailProduct.setSku(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_sku)));
                    newRetailProduct.setTitle(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_title)));
                    newRetailProduct.setDescription(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_description)));
                    newRetailProduct.setWeight(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_weight)));
                    newRetailProduct.setDimension(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_dimension)));
                    newRetailProduct.setAccessories(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_accessories)));
                    newRetailProduct.setWarrenty(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_warrenty)));
                    newRetailProduct.setImage_group(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_image_group)));
                    newRetailProduct.setImages(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_images)));
                    newRetailProduct.setManufacturer(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_manufacturer)));
                    newRetailProduct.setBrand(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_brand)));
                    newRetailProduct.setMarket_place(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_market_place)));
                    newRetailProduct.setApproximate_cost(c.getDouble(c.getColumnIndex(db.COLUMN_new_retail_product_approximate_cost)));
                    newRetailProduct.setWhole_sale_price(c.getDouble(c.getColumnIndex(db.COLUMN_new_retail_product_whole_sale_price)));
                    newRetailProduct.setRetail_price(c.getDouble(c.getColumnIndex(db.COLUMN_new_retail_product_retail_price)));
                    newRetailProduct.setPurchase_tax_group(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_purchase_tax_group)));
                    newRetailProduct.setSales_tax_group(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_sales_tax_group)));
                    newRetailProduct.setEntry_by(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_entry_by)));
                    newRetailProduct.setEntry_date_time(c.getString(c.getColumnIndex(db.COLUMN_new_retail_product_entry_date_time)));
                    newRetailProduct.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_new_retail_product_status)));
                    newRetailProducts.add(newRetailProduct);
                } while (c.moveToNext());
            }
        }catch (Exception e){}
        return newRetailProducts;
    }
    public static int getSkuCount(DBInitialization db,String sku)
    {
        return NewRetailProduct.select(db,db.COLUMN_new_retail_product_sku+"='"+sku+"'").size();
    }
    public static void updateRetailPrice(DBInitialization db,String SKU,double price)
    {
        db.updateData(db.COLUMN_new_retail_product_approximate_cost+"="+String.valueOf(price),db.COLUMN_new_retail_product_sku+"='"+String.valueOf(SKU)+"'", db.TABLE_new_retail_product);
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public void setImage_group(String image_group) {
        this.image_group = image_group;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setApproximate_cost(Double approximate_cost) {
        this.approximate_cost = approximate_cost;
    }

    public void setWhole_sale_price(Double whole_sale_price) {
        this.whole_sale_price = whole_sale_price;
    }

    public void setRetail_price(Double retail_price) {
        this.retail_price = retail_price;
    }

    public void setPurchase_tax_group(String purchase_tax_group) {
        this.purchase_tax_group = purchase_tax_group;
    }

    public void setSales_tax_group(String sales_tax_group) {
        this.sales_tax_group = sales_tax_group;
    }

    public void setMarket_place(String market_place) {
        this.market_place = market_place;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    public void setEntry_by(String entry_by) {
        this.entry_by = entry_by;
    }

    public void setEntry_date_time(String entry_date_time) {
        this.entry_date_time = entry_date_time;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getEntry_by() {
        return entry_by;
    }

    public String getEntry_date_time() {
        return entry_date_time;
    }

    public int getEntry_id() {
        return entry_id;
    }

    public String getMarket_place() {
        return market_place;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public String getSku() {
        return sku;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getWeight() {
        return weight;
    }

    public String getDimension() {
        return dimension;
    }

    public String getAccessories() {
        return accessories;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public String getImage_group() {
        return image_group;
    }

    public String getImages() {
        return images;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public Double getApproximate_cost() {
        return approximate_cost;
    }

    public Double getWhole_sale_price() {
        return whole_sale_price;
    }

    public Double getRetail_price() {
        return retail_price;
    }

    public String getPurchase_tax_group() {
        return purchase_tax_group;
    }

    public String getSales_tax_group() {
        return sales_tax_group;
    }


}
