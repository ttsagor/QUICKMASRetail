package com.quickmas.salim.quickmasretail.Model.RTM;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import java.util.ArrayList;

/**
 * Created by Forhad on 28/03/2018.
 */

public class Product {
    public int id;
    public String assign_no;
    public String point;
    public String route;
    public String section;
    public String frequency;
    public String company;
    public String product_type;
    public String gift_for;
    public String product_category;
    public String photo;
    public String brand;
    public String sku;
    public String sub_sku;
    public int sku_qty;
    public double sku_price;
    public int target;
    public String date_from;
    public String date_to;
    public int sold_sku;
    public int return_sku;
    public int total_sku;
    public String accept_date;
    public int status;
    public String order_permission;
    public String payment_permission;
    public String sell_permission;

    public int total_invoice;
    public int sum_quantity;

    public void setTotal_invoice(int total_invoice) {
        this.total_invoice = total_invoice;
    }

    public void setSum_quantity(int sum_quantity) {
        this.sum_quantity = sum_quantity;
    }

    public int getTotal_invoice() {
        return total_invoice;
    }

    public int getSum_quantity() {
        return sum_quantity;
    }

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_product_id, this.getId());
        insertValues.put(db.COLUMN_product_assign_no, this.getAssign_no());
        insertValues.put(db.COLUMN_product_point, this.getPoint());
        insertValues.put(db.COLUMN_product_route, this.getRoute());
        insertValues.put(db.COLUMN_product_section, this.getSection());
        insertValues.put(db.COLUMN_product_frequency, this.getFrequency());
        insertValues.put(db.COLUMN_product_company, this.getCompany());
        insertValues.put(db.COLUMN_product_product_type, this.getProduct_type());
        insertValues.put(db.COLUMN_product_gift_for, this.getGift_for());
        insertValues.put(db.COLUMN_product_product_category, this.getProduct_category());
        insertValues.put(db.COLUMN_product_photo, this.getPhoto());
        insertValues.put(db.COLUMN_product_brand, this.getBrand());
        insertValues.put(db.COLUMN_product_sku, this.getSku());
        insertValues.put(db.COLUMN_product_sub_sku, this.getSub_sku());
        insertValues.put(db.COLUMN_product_sku_qty, this.getSku_qty());
        insertValues.put(db.COLUMN_product_sku_price, this.getSku_price());
        insertValues.put(db.COLUMN_product_target, this.getTarget());
        insertValues.put(db.COLUMN_product_date_from, this.getDate_from());
        insertValues.put(db.COLUMN_product_date_to, this.getDate_to());
        insertValues.put(db.COLUMN_product_sold_sku, this.getSold_sku());
        insertValues.put(db.COLUMN_product_return_sku, this.getReturn_sku());
        insertValues.put(db.COLUMN_product_total_sku, this.getTotal_sku());
        insertValues.put(db.COLUMN_product_accept_date, this.getAccept_date());
        insertValues.put(db.COLUMN_product_status, this.getStatus());
        insertValues.put(db.COLUMN_product_order_permission, this.getOrder_permission());
        insertValues.put(db.COLUMN_product_payment_permission, this.getPayment_permission());
        insertValues.put(db.COLUMN_product_sell_permission, this.getSell_permission());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_PRODUCT,db.COLUMN_product_id+"="+this.getId());
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_PRODUCT,db.COLUMN_product_id+"="+this.getId());
    }


    public static ArrayList<Product> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",db.TABLE_PRODUCT,con,"");
        ArrayList<Product> products = new ArrayList<Product>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                Product cProduct = new Product();
                cProduct.setId(c.getInt(c.getColumnIndex(db.COLUMN_product_id)));
                cProduct.setAssign_no(c.getString(c.getColumnIndex(db.COLUMN_product_assign_no)));
                cProduct.setPoint(c.getString(c.getColumnIndex(db.COLUMN_product_point)));
                cProduct.setRoute(c.getString(c.getColumnIndex(db.COLUMN_product_route)));
                cProduct.setSection(c.getString(c.getColumnIndex(db.COLUMN_product_section)));
                cProduct.setFrequency(c.getString(c.getColumnIndex(db.COLUMN_product_frequency)));
                cProduct.setCompany(c.getString(c.getColumnIndex(db.COLUMN_product_company)));
                cProduct.setProduct_type(c.getString(c.getColumnIndex(db.COLUMN_product_product_type)));
                cProduct.setGift_for(c.getString(c.getColumnIndex(db.COLUMN_product_gift_for)));
                cProduct.setProduct_category(c.getString(c.getColumnIndex(db.COLUMN_product_product_category)));
                cProduct.setPhoto(c.getString(c.getColumnIndex(db.COLUMN_product_photo)));
                cProduct.setBrand(c.getString(c.getColumnIndex(db.COLUMN_product_brand)));
                cProduct.setSku(c.getString(c.getColumnIndex(db.COLUMN_product_sku)));
                cProduct.setSub_sku(c.getString(c.getColumnIndex(db.COLUMN_product_sub_sku)));
                cProduct.setSku_qty(c.getInt(c.getColumnIndex(db.COLUMN_product_sku_qty)));
                cProduct.setSku_price(c.getDouble(c.getColumnIndex(db.COLUMN_product_sku_price)));
                cProduct.setTarget(c.getInt(c.getColumnIndex(db.COLUMN_product_target)));
                cProduct.setDate_from(c.getString(c.getColumnIndex(db.COLUMN_product_date_from)));
                cProduct.setDate_to(c.getString(c.getColumnIndex(db.COLUMN_product_date_to)));
                cProduct.setSold_sku(c.getInt(c.getColumnIndex(db.COLUMN_product_sold_sku)));
                cProduct.setReturn_sku(c.getInt(c.getColumnIndex(db.COLUMN_product_return_sku)));
                cProduct.setTotal_sku(c.getInt(c.getColumnIndex(db.COLUMN_product_total_sku)));
                cProduct.setAccept_date(c.getString(c.getColumnIndex(db.COLUMN_product_accept_date)));
                cProduct.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_product_status)));
                cProduct.setOrder_permission(c.getString(c.getColumnIndex(db.COLUMN_product_order_permission)));
                cProduct.setPayment_permission(c.getString(c.getColumnIndex(db.COLUMN_product_payment_permission)));
                cProduct.setSell_permission(c.getString(c.getColumnIndex(db.COLUMN_product_sell_permission)));
                products.add(cProduct);
            } while (c.moveToNext());
        }
        return products;
    }

    public void setOrder_permission(String order_permission) {
        this.order_permission = order_permission;
    }

    public void setPayment_permission(String payment_permission) {
        this.payment_permission = payment_permission;
    }

    public void setSell_permission(String sell_permission) {
        this.sell_permission = sell_permission;
    }

    public String getOrder_permission() {
        return order_permission;
    }

    public String getPayment_permission() {
        return payment_permission;
    }

    public String getSell_permission() {
        return sell_permission;
    }

    public void setTotal_sku(int total_sku) {
        this.total_sku = total_sku;
    }

    public int getTotal_sku() {
        return total_sku;
    }


    public void setAccept_date(String accept_date) {
        this.accept_date = accept_date;
    }

    public String getAccept_date() {
        return accept_date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public void setSku_price(double sku_price) {
        this.sku_price = sku_price;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAssign_no(String assign_no) {
        this.assign_no = assign_no;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setSku_qty(int sku_qty) {
        this.sku_qty = sku_qty;
    }


    public void setSku_price(int sku_price) {
        this.sku_price = sku_price;
    }


    public void setTarget(int target) {
        this.target = target;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public void setSold_sku(int sold_sku) {
        this.sold_sku = sold_sku;
    }


    public void setReturn_sku(int return_sku) {
        this.return_sku = return_sku;
    }

    public void setGift_for(String gift_for) {
        this.gift_for = gift_for;
    }

    public void setSub_sku(String sub_sku) {
        this.sub_sku = sub_sku;
    }

    public String getSub_sku() {
        return sub_sku;
    }

    public String getGift_for() {
        return gift_for;
    }

    public int getId() {
        return id;
    }

    public String getAssign_no() {
        return assign_no;
    }

    public String getPoint() {
        return point;
    }

    public String getRoute() {
        return route;
    }

    public String getSection() {
        return section;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getCompany() {
        return company;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBrand() {
        return brand;
    }

    public String getSku() {
        return sku;
    }

    public int getSku_qty() {
        return sku_qty;
    }

    public double getSku_price() {
        return sku_price;
    }

    public int getTarget() {
        return target;
    }

    public String getDate_from() {
        return date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public int getSold_sku() {
        return sold_sku;
    }
    public int getReturn_sku() {
        return return_sku;
    }
}
