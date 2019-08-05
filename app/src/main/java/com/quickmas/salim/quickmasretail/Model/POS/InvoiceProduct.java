package com.quickmas.salim.quickmasretail.Model.POS;

import android.content.ContentValues;
import android.database.Cursor;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;

import java.util.ArrayList;

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
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.TABLE_invoice_product;

public class InvoiceProduct {
    public int id;
    public String uuid="";
    public String group="";
    public String pgroup="";
    public String type="";
    public String ptype="";
    public String pclass="";
    public String pcategory="";
    public String psub_category="";
    public String company="";
    public String brand="";
    public String name="";
    public String names="";
    public String sale_coa="";
    public String barcode="";
    public double wholesale=0;
    public double price=0;
    public String vat="";
    public double discount_amount=0;
    public String discount_type="";
    public String from="";
    public String to="";
    public String from_hr="";
    public String to_hr="";

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_invoice_product_id, this.getId());
        insertValues.put(db.COLUMN_invoice_product_uuid, this.getUuid());
        insertValues.put(db.COLUMN_invoice_product_group, this.getGroup());
        insertValues.put(db.COLUMN_invoice_product_pgroup, this.getPgroup());
        insertValues.put(db.COLUMN_invoice_product_type, this.getType());
        insertValues.put(db.COLUMN_invoice_product_ptype, this.getPtype());
        insertValues.put(db.COLUMN_invoice_product_pclass, this.getPclass());
        insertValues.put(db.COLUMN_invoice_product_pcategory, this.getPcategory());
        insertValues.put(db.COLUMN_invoice_product_psub_category, this.getPsub_category());
        insertValues.put(db.COLUMN_invoice_product_company, this.getCompany());
        insertValues.put(db.COLUMN_invoice_product_brand, this.getBrand());
        insertValues.put(db.COLUMN_invoice_product_name, this.getName());
        insertValues.put(db.COLUMN_invoice_product_names, this.getNames());
        insertValues.put(db.COLUMN_invoice_product_sale_coa, this.getSale_coa());
        insertValues.put(db.COLUMN_invoice_product_barcode, this.getBarcode());
        insertValues.put(db.COLUMN_invoice_product_wholesale, this.getWholesale());
        insertValues.put(db.COLUMN_invoice_product_price, this.getPrice());
        insertValues.put(db.COLUMN_invoice_product_vat, this.getVat());
        insertValues.put(db.COLUMN_invoice_product_discount_amount, this.getDiscount_amount());
        insertValues.put(db.COLUMN_invoice_product_discount_type, this.getDiscount_type());
        insertValues.put(db.COLUMN_invoice_product_from, this.getFrom());
        insertValues.put(db.COLUMN_invoice_product_to, this.getTo());
        insertValues.put(db.COLUMN_invoice_product_from_hr, this.getFrom_hr());
        insertValues.put(db.COLUMN_invoice_product_to_hr, this.getTo_hr());
        return  insertValues;
    }

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),TABLE_invoice_product,db.COLUMN_invoice_product_name+"=\""+this.getName()+"\"");
    }

    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),TABLE_invoice_product,db.COLUMN_invoice_product_name+"=\""+this.getName()+"\"");
    }


    public static ArrayList<InvoiceProduct> select(DBInitialization db, String con)
    {
        Cursor c = db.getData("*",TABLE_invoice_product,con,"");
        ArrayList<InvoiceProduct> products = new ArrayList<InvoiceProduct>();
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                InvoiceProduct cProduct = new InvoiceProduct();
                cProduct.setId(c.getInt(c.getColumnIndex(db.COLUMN_invoice_product_id)));
                cProduct.setUuid(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_uuid)));
                cProduct.setGroup(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_group)));
                cProduct.setPgroup(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_pgroup)));
                cProduct.setType(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_type)));
                cProduct.setPtype(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_ptype)));
                cProduct.setPclass(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_pclass)));
                cProduct.setPcategory(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_pcategory)));
                cProduct.setPsub_category(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_psub_category)));
                cProduct.setCompany(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_company)));
                cProduct.setBrand(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_brand)));
                cProduct.setName(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_name)));
                cProduct.setNames(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_names)));
                cProduct.setSale_coa(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_sale_coa)));
                cProduct.setBarcode(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_barcode)));
                cProduct.setWholesale(c.getDouble(c.getColumnIndex(db.COLUMN_invoice_product_wholesale)));
                cProduct.setPrice(c.getDouble(c.getColumnIndex(db.COLUMN_invoice_product_price)));
                cProduct.setVat(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_vat)));
                cProduct.setDiscount_amount(c.getDouble(c.getColumnIndex(db.COLUMN_invoice_product_discount_amount)));
                cProduct.setDiscount_type(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_discount_type)));
                cProduct.setFrom(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_from)));
                cProduct.setTo(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_to)));
                cProduct.setFrom_hr(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_from_hr)));
                cProduct.setTo_hr(c.getString(c.getColumnIndex(db.COLUMN_invoice_product_to_hr)));
                products.add(cProduct);
            } while (c.moveToNext());
        }
        return products;
    }

    public static double calTax(InvoiceProduct product,int quantity)
    {
        double totalPrice =  product.getPrice() * quantity;
        return (totalPrice * TypeConvertion.parseDouble(product.getVat()))/100;
    }

    public static double calDis(InvoiceProduct product,int quantity)
    {
        if(!DateTimeCalculation.checkValidityDate(product.from,product.to))
        {
            return 0.0;
        }
        if(product.getDiscount_type().toUpperCase().equals("DISCOUNT %"))
        {
            double totalPrice =  product.getPrice() * quantity;
            return (totalPrice * product.getDiscount_amount())/100;
        }
        else if(product.getDiscount_type().toUpperCase().equals("DISCOUNT AMOUNT"))
        {
            return product.getDiscount_amount()*quantity;
        }
        else if(product.getDiscount_type().toUpperCase().equals("FLAT RATE"))
        {
            double totalPrice =  product.getPrice() * quantity;
            return totalPrice - (product.getDiscount_amount()*quantity);
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


    public void setId(int id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setPgroup(String pgroup) {
        this.pgroup = pgroup;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public void setPclass(String pclass) {
        this.pclass = pclass;
    }

    public void setPcategory(String pcategory) {
        this.pcategory = pcategory;
    }

    public void setPsub_category(String psub_category) {
        this.psub_category = psub_category;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setSale_coa(String sale_coa) {
        this.sale_coa = sale_coa;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setWholesale(double wholesale) {
        this.wholesale = wholesale;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom_hr(String from_hr) {
        this.from_hr = from_hr;
    }

    public void setTo_hr(String to_hr) {
        this.to_hr = to_hr;
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getGroup() {
        return group;
    }

    public String getPgroup() {
        return pgroup;
    }

    public String getType() {
        return type;
    }

    public String getPtype() {
        return ptype;
    }

    public String getPclass() {
        return pclass;
    }

    public String getPcategory() {
        return pcategory;
    }

    public String getPsub_category() {
        return psub_category;
    }

    public String getCompany() {
        return company;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getNames() {
        return names;
    }

    public String getSale_coa() {
        return sale_coa;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getWholesale() {
        return wholesale;
    }

    public double getPrice() {
        return price;
    }

    public String getVat() {
        return vat;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFrom_hr() {
        return from_hr;
    }

    public String getTo_hr() {
        return to_hr;
    }
}
