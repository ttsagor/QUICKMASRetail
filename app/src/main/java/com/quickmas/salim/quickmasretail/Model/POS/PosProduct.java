package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;

import java.util.ArrayList;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_invoiceID;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_invoice_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_brand;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_category_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_color;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_date_from;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_date_to;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_dimensions;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_discount;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_discount_type;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_features;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_guarantee;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_hour_from;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_hour_to;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_id;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_includes;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_location;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_name;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_photo;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_price;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_quantity;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_quantity_left;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_sold_quantity;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_status;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_sub_location;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_sub_quantity;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_sub_type;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_tax;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_title;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_type;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_weight;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_pos_product_whole_sale;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_Pos_Product;

/**
 * Created by Forhad on 15/04/2018.
 */

public class PosProduct {
    public int id=0;
    public String product_name;
    public String title;
    public String color;
    public String type;
    public String sub_type;
    public String location;
    public String sub_location;
    public String features;
    public String weight;
    public String dimensions;
    public String includes;
    public String guarantee;
    public double tax;
    public double discount;
    public String discount_type;
    public String date_from;
    public String date_to;
    public String hour_from;
    public String hour_to;
    public int quantity;
    public int sub_quantity;
    public double price;
    public double whole_sale;
    public String photo;
    public String brand;
    public int sold_quantity;
    public int quantity_left;
    public String category_status;
    public int status;

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(COLUMN_pos_product_id, this.getId());
        insertValues.put(COLUMN_pos_product_name, this.getProduct_name());
        insertValues.put(COLUMN_pos_product_title, this.getTitle());
        insertValues.put(COLUMN_pos_product_color, this.getColor());
        insertValues.put(COLUMN_pos_product_type, this.getType());
        insertValues.put(COLUMN_pos_product_sub_type, this.getSub_type());
        insertValues.put(COLUMN_pos_product_location, this.getLocation());
        insertValues.put(COLUMN_pos_product_sub_location, this.getSub_location());
        insertValues.put(COLUMN_pos_product_features, this.getFeatures());
        insertValues.put(COLUMN_pos_product_weight, this.getWeight());
        insertValues.put(COLUMN_pos_product_dimensions, this.getDimensions());
        insertValues.put(COLUMN_pos_product_includes, this.getIncludes());
        insertValues.put(COLUMN_pos_product_guarantee, this.getGuarantee());
        insertValues.put(COLUMN_pos_product_quantity, this.getQuantity());
        insertValues.put(COLUMN_pos_product_sub_quantity, this.getSub_quantity());
        insertValues.put(COLUMN_pos_product_price, this.getPrice());
        insertValues.put(COLUMN_pos_product_whole_sale, this.getWhole_sale());
        insertValues.put(COLUMN_pos_product_tax, this.getTax());
        insertValues.put(COLUMN_pos_product_discount, this.getDiscount());
        insertValues.put(COLUMN_pos_product_discount_type, this.getDiscount_type());
        insertValues.put(COLUMN_pos_product_date_from, this.getDate_from());
        insertValues.put(COLUMN_pos_product_date_to, this.getDate_to());
        insertValues.put(COLUMN_pos_product_hour_from, this.getHour_from());
        insertValues.put(COLUMN_pos_product_hour_to, this.getHour_to());
        insertValues.put(COLUMN_pos_product_photo, this.getPhoto());
        insertValues.put(COLUMN_pos_product_brand, this.getBrand());
        insertValues.put(COLUMN_pos_product_sold_quantity, this.getSold_quantity());
        insertValues.put(COLUMN_pos_product_quantity_left, this.getQuantity_left());
        insertValues.put(COLUMN_pos_product_category_status, this.getCategory_status());
        insertValues.put(COLUMN_pos_product_status, this.getStatus());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_Pos_Product,db.COLUMN_pos_product_id+"="+this.getId());
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_Pos_Product,db.COLUMN_pos_product_id+"="+this.getId());
    }


    public static ArrayList<PosProduct> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_Pos_Product,con,"");
        ArrayList<PosProduct> products = new ArrayList<PosProduct>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                PosProduct cProduct = new PosProduct();
                cProduct.setId(c.getInt(c.getColumnIndex(db.COLUMN_product_id)));
                cProduct.setProduct_name(c.getString(c.getColumnIndex(db.COLUMN_pos_product_name)));
                cProduct.setTitle(c.getString(c.getColumnIndex(db.COLUMN_pos_product_title)));
                cProduct.setColor(c.getString(c.getColumnIndex(db.COLUMN_pos_product_color)));
                cProduct.setType(c.getString(c.getColumnIndex(db.COLUMN_pos_product_type)));
                cProduct.setSub_type(c.getString(c.getColumnIndex(db.COLUMN_pos_product_sub_type)));
                cProduct.setLocation(c.getString(c.getColumnIndex(db.COLUMN_pos_product_location)));
                cProduct.setSub_location(c.getString(c.getColumnIndex(db.COLUMN_pos_product_sub_location)));
                cProduct.setFeatures(c.getString(c.getColumnIndex(db.COLUMN_pos_product_features)));
                cProduct.setWeight(c.getString(c.getColumnIndex(db.COLUMN_pos_product_weight)));
                cProduct.setDimensions(c.getString(c.getColumnIndex(db.COLUMN_pos_product_dimensions)));
                cProduct.setIncludes(c.getString(c.getColumnIndex(db.COLUMN_pos_product_includes)));
                cProduct.setGuarantee(c.getString(c.getColumnIndex(db.COLUMN_pos_product_guarantee)));
                cProduct.setQuantity(c.getInt(c.getColumnIndex(db.COLUMN_pos_product_quantity)));
                cProduct.setSub_quantity(c.getInt(c.getColumnIndex(db.COLUMN_pos_product_sub_quantity)));
                cProduct.setPrice(c.getDouble(c.getColumnIndex(db.COLUMN_pos_product_price)));
                cProduct.setWhole_sale(c.getDouble(c.getColumnIndex(db.COLUMN_pos_product_whole_sale)));
                cProduct.setTax(c.getDouble(c.getColumnIndex(db.COLUMN_pos_product_tax)));
                cProduct.setDiscount(c.getDouble(c.getColumnIndex(db.COLUMN_pos_product_discount)));
                cProduct.setDate_from(c.getString(c.getColumnIndex(db.COLUMN_pos_product_date_from)));
                cProduct.setDate_to(c.getString(c.getColumnIndex(db.COLUMN_pos_product_date_to)));
                cProduct.setHour_from(c.getString(c.getColumnIndex(db.COLUMN_pos_product_hour_from)));
                cProduct.setHour_to(c.getString(c.getColumnIndex(db.COLUMN_pos_product_hour_to)));
                cProduct.setDiscount_type(c.getString(c.getColumnIndex(db.COLUMN_pos_product_discount_type)));
                cProduct.setPhoto(c.getString(c.getColumnIndex(db.COLUMN_pos_product_photo)));
                cProduct.setBrand(c.getString(c.getColumnIndex(db.COLUMN_pos_product_brand)));
                cProduct.setSold_quantity(c.getInt(c.getColumnIndex(db.COLUMN_pos_product_sold_quantity)));
                cProduct.setQuantity_left(c.getInt(c.getColumnIndex(db.COLUMN_pos_product_quantity_left)));
                cProduct.setCategory_status(c.getString(c.getColumnIndex(db.COLUMN_pos_product_category_status)));
                cProduct.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_pos_product_status)));
                products.add(cProduct);
            } while (c.moveToNext());
        }
        return products;
    }

    public static double calTax(PosProduct product,int quantity)
    {
        double totalPrice =  product.getPrice() * quantity;
        return (totalPrice * product.getTax())/100;
    }

    public static double calDis(PosProduct product,int quantity)
    {
        if(!DateTimeCalculation.checkValidityDate(product.date_from,product.date_to))
        {
            return 0.0;
        }
        if(product.getDiscount_type().toUpperCase().equals("DISCOUNT %"))
        {
            double totalPrice =  product.getPrice() * quantity;
            return (totalPrice * product.getDiscount())/100;
        }
        else if(product.getDiscount_type().toUpperCase().equals("DISCOUNT AMOUNT"))
        {
            return product.getDiscount()*quantity;
        }
        else if(product.getDiscount_type().toUpperCase().equals("FLAT RATE"))
        {
            double totalPrice =  product.getPrice() * quantity;
            return totalPrice - (product.getDiscount()*quantity);
        }
        else if(product.getDiscount_type().toUpperCase().equals("PACKAGE"))
        {

        }
        else if(product.getDiscount_type().toUpperCase().equals("BUY & GET"))
        {

        }
        else if(product.getDiscount_type().toUpperCase().equals("HAPPY HOUR"))
        {

        }
        return 0.0;
    }


    public static int sumQuanitityLeftByTitle(DBInitialization db,PosProduct product)
    {
       return db.sumData(COLUMN_pos_product_quantity_left,TABLE_Pos_Product,COLUMN_pos_product_title+"='"+product.getTitle()+"'");
    }

    public String getDate_from() {
        return date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public String getHour_from() {
        return hour_from;
    }

    public String getHour_to() {
        return hour_to;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public void setHour_from(String hour_from) {
        this.hour_from = hour_from;
    }

    public void setHour_to(String hour_to) {
        this.hour_to = hour_to;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setQuantity_left(int quantity_left) {
        this.quantity_left = quantity_left;
    }

    public void setCategory_status(String category_status) {
        this.category_status = category_status;
    }

    public String getCategory_status() {
        return category_status;
    }

    public int getQuantity_left() {
        return quantity_left;
    }

    public String getColor() {
        return color;
    }

    public double getDiscount() {
        return discount;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public String getFeatures() {
        return features;
    }

    public String getWeight() {
        return weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getIncludes() {
        return includes;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWhole_sale(double whole_sale) {
        this.whole_sale = whole_sale;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public double getTax() {
        return tax;
    }

    public int getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getSub_type() {
        return sub_type;
    }

    public String getLocation() {
        return location;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getWhole_sale() {
        return whole_sale;
    }

    public String getPhoto() {
        return photo;
    }

    public int getSold_quantity() {
        return sold_quantity;
    }

    public String getSub_location() {
        return sub_location;
    }

    public int getSub_quantity() {
        return sub_quantity;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setSub_location(String sub_location) {
        this.sub_location = sub_location;
    }

    public void setSub_quantity(int sub_quantity) {
        this.sub_quantity = sub_quantity;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
