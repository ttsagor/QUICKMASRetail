package com.quickmas.salim.quickmasretail.Model.System;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;

import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_user_selected_Location;
import static com.quickmas.salim.quickmasretail.Database.DBInitialization.COLUMN_user_selected_pos;

/**
 * Created by Forhad on 17/03/2018.
 */

public class User {
    public int id;
    public String user_name;
    public String company_id;
    public String password;
    public String user_full_name;
    public String company_name;
    public String address1;
    public String address2;
    public String address3;
    public String country;
    public String phone;
    public String logo;
    public String email;
    public String image;
    public String location;
    public String selected_location;
    public String pos;
    public String selected_pos;
    public int user_order;
    public int user_payment;
    public int user_delivery;
    public int rtm_photo_limit;

    public String app_icon;
    public String loading_color;
    public String loading_background;
    public String blank_img;
    public double cash_cu_balance;
    public String print_company_name;
    public String print_web_name;
    public String print_message;
    public String loading_txt;

    public String choose_color;
    public String choose_size;


    public int status;
    public int active_online;

    public void insert(DBInitialization db)
    {
        db.insertData(getContectValue(db),db.TABLE_USER,db.COLUMN_user_id+"="+this.getId());
    }

    public User select(DBInitialization db)
    {
        return select(db,db.COLUMN_user_id+"="+this.getId());
    }
    public User select(DBInitialization db,String con)
    {
        Cursor c = db.getData("*",db.TABLE_USER,con,"");
        if (c !=null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                this.setId(c.getInt(c.getColumnIndex(db.COLUMN_user_id)));
                this.setUser_name(c.getString(c.getColumnIndex(db.COLUMN_user_name)));
                this.setCompany_id(c.getString(c.getColumnIndex(db.COLUMN_user_company_id)));
                this.setPassword(c.getString(c.getColumnIndex(db.COLUMN_user_password)));
                this.setUser_full_name(c.getString(c.getColumnIndex(db.COLUMN_user_full_name)));
                this.setCompany_name(c.getString(c.getColumnIndex(db.COLUMN_user_company_name)));
                this.setAddress1(c.getString(c.getColumnIndex(db.COLUMN_user_address1)));
                this.setAddress2(c.getString(c.getColumnIndex(db.COLUMN_user_address2)));
                this.setAddress3(c.getString(c.getColumnIndex(db.COLUMN_user_address3)));
                this.setCountry(c.getString(c.getColumnIndex(db.COLUMN_user_country)));
                this.setPhone(c.getString(c.getColumnIndex(db.COLUMN_user_phone)));
                this.setEmail(c.getString(c.getColumnIndex(db.COLUMN_user_email)));
                this.setLogo(c.getString(c.getColumnIndex(db.COLUMN_user_logo)));
                this.setImage(c.getString(c.getColumnIndex(db.COLUMN_user_image)));
                this.setLocation(c.getString(c.getColumnIndex(db.COLUMN_user_location)));
                this.setSelected_location(c.getString(c.getColumnIndex(db.COLUMN_user_selected_Location)));
                this.setPos(c.getString(c.getColumnIndex(db.COLUMN_user_pos)));
                this.setSelected_pos(c.getString(c.getColumnIndex(db.COLUMN_user_selected_pos)));
                this.setUser_order(c.getInt(c.getColumnIndex(db.COLUMN_user_order)));
                this.setUser_payment(c.getInt(c.getColumnIndex(db.COLUMN_user_payment)));
                this.setUser_delivery(c.getInt(c.getColumnIndex(db.COLUMN_user_delivery)));
                this.setRtm_photo_limit(c.getInt(c.getColumnIndex(db.COLUMN_user_photo_limit)));
                this.setStatus(c.getInt(c.getColumnIndex(db.COLUMN_user_status)));
                this.setApp_icon(c.getString(c.getColumnIndex(db.COLUMN_user_app_icon)));
                this.setLoading_color(c.getString(c.getColumnIndex(db.COLUMN_user_loading_color)));
                this.setLoading_background(c.getString(c.getColumnIndex(db.COLUMN_user_loading_background)));
                this.setBlank_img(c.getString(c.getColumnIndex(db.COLUMN_user_blank_img)));
                this.setCash_cu_balance(c.getDouble(c.getColumnIndex(db.COLUMN_user_cash_cu_balance)));
                this.setPrint_company_name(c.getString(c.getColumnIndex(db.COLUMN_user_print_company)));
                this.setPrint_web_name(c.getString(c.getColumnIndex(db.COLUMN_user_print_website)));
                this.setPrint_message(c.getString(c.getColumnIndex(db.COLUMN_user_print_message)));
                this.setActive_online(c.getInt(c.getColumnIndex(db.COLUMN_user_active_online)));
                this.setLoading_txt(c.getString(c.getColumnIndex(db.COLUMN_user_loading_text)));
                this.setChoose_color(c.getString(c.getColumnIndex(db.COLUMN_user_choose_color)));
                this.setChoose_size(c.getString(c.getColumnIndex(db.COLUMN_user_choose_size)));
            } while (c.moveToNext());
        }
        return this;
    }
    public void update(DBInitialization db,String con)
    {
        db.updateData(getContectValue(db),db.TABLE_USER,con);
    }
    public void update(DBInitialization db)
    {
        db.updateData(getContectValue(db),db.TABLE_USER,db.COLUMN_user_id+"="+this.getId());
    }

    ContentValues getContectValue(DBInitialization db)
    {
        ContentValues insertValues = new ContentValues();
        insertValues.put(db.COLUMN_user_id, this.getId());
        insertValues.put(db.COLUMN_user_name, this.getUser_name());
        insertValues.put(db.COLUMN_user_company_id, this.getCompany_id());
        insertValues.put(db.COLUMN_user_password, this.getPassword());
        insertValues.put(db.COLUMN_user_full_name, this.getUser_full_name());
        insertValues.put(db.COLUMN_user_email, this.getEmail());
        insertValues.put(db.COLUMN_user_company_name, this.getCompany_name());
        insertValues.put(db.COLUMN_user_address1, this.getAddress1());
        insertValues.put(db.COLUMN_user_address2, this.getAddress2());
        insertValues.put(db.COLUMN_user_address3, this.getAddress3());
        insertValues.put(db.COLUMN_user_country, this.getCountry());
        insertValues.put(db.COLUMN_user_phone, this.getPhone());
        insertValues.put(db.COLUMN_user_logo, this.getLogo());
        insertValues.put(db.COLUMN_user_image, this.getImage());
        insertValues.put(db.COLUMN_user_location, this.getLocation());
        insertValues.put(db.COLUMN_user_selected_Location, this.getSelected_location());
        insertValues.put(db.COLUMN_user_pos, this.getPos());
        insertValues.put(db.COLUMN_user_selected_pos, this.getSelected_pos());
        insertValues.put(db.COLUMN_user_order, this.getUser_order());
        insertValues.put(db.COLUMN_user_payment, this.getUser_payment());
        insertValues.put(db.COLUMN_user_delivery, this.getUser_delivery());
        insertValues.put(db.COLUMN_user_photo_limit, this.getRtm_photo_limit());
        insertValues.put(db.COLUMN_user_status, this.getStatus());
        insertValues.put(db.COLUMN_user_app_icon, this.getApp_icon());
        insertValues.put(db.COLUMN_user_loading_color, this.getLoading_color());
        insertValues.put(db.COLUMN_user_loading_background, this.getLoading_background());
        insertValues.put(db.COLUMN_user_blank_img, this.getBlank_img());
        insertValues.put(db.COLUMN_user_cash_cu_balance, this.getCash_cu_balance());
        insertValues.put(db.COLUMN_user_print_company, this.getPrint_company_name());
        insertValues.put(db.COLUMN_user_print_website, this.getPrint_web_name());
        insertValues.put(db.COLUMN_user_print_message, this.getPrint_message());
        insertValues.put(db.COLUMN_user_active_online, this.getActive_online());
        insertValues.put(db.COLUMN_user_loading_text, this.getLoading_txt());
        insertValues.put(db.COLUMN_user_choose_color, this.getChoose_color());
        insertValues.put(db.COLUMN_user_choose_size, this.getChoose_size());
        return  insertValues;
    }

    public void setPrint_company_name(String print_company_name) {
        this.print_company_name = print_company_name;
    }

    public static String getUserDetails(User cUser)
    {
        String username = Uri.encode(cUser.getUser_name());
        String password = Uri.encode(cUser.getPassword());
        String company = Uri.encode(cUser.getCompany_id());
        String location = Uri.encode(cUser.getSelected_location());
        String pos = Uri.encode(cUser.getSelected_pos());
        return  "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
    }

    public void setPrint_web_name(String print_web_name) {
        this.print_web_name = print_web_name;
    }

    public String getPrint_company_name() {
        return print_company_name;
    }

    public String getPrint_web_name() {
        return print_web_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUser_order(int user_order) {
        this.user_order = user_order;
    }

    public void setUser_payment(int user_payment) {
        this.user_payment = user_payment;
    }

    public void setUser_delivery(int user_delivery) {
        this.user_delivery = user_delivery;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSelected_pos(String selected_pos) {
        this.selected_pos = selected_pos;
    }

    public void setSelected_location(String selected_location) {
        this.selected_location = selected_location;
    }

    public void setRtm_photo_limit(int rtm_photo_limit) {
        this.rtm_photo_limit = rtm_photo_limit;
    }

    public void setApp_icon(String app_icon) {
        this.app_icon = app_icon;
    }

    public void setLoading_color(String loading_color) {
        this.loading_color = loading_color;
    }

    public void setLoading_background(String loading_background) {
        this.loading_background = loading_background;
    }

    public void setBlank_img(String blank_img) {
        this.blank_img = blank_img;
    }

    public int getRtm_photo_limit() {
        return rtm_photo_limit;
    }

    public String getApp_icon() {
        return app_icon;
    }

    public String getLoading_color() {
        return loading_color;
    }

    public String getLoading_background() {
        return loading_background;
    }

    public String getBlank_img() {
        return blank_img;
    }

    public String getSelected_location() {
        return selected_location;
    }

    public String getSelected_pos() {
        return selected_pos;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getLogo() {
        return logo;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public int getUser_order() {
        return user_order;
    }

    public int getUser_payment() {
        return user_payment;
    }

    public int getUser_delivery() {
        return user_delivery;
    }

    public int getStatus() {
        return status;
    }

    public void setCash_cu_balance(double cash_cu_balance) {
        this.cash_cu_balance = cash_cu_balance;
    }

    public double getCash_cu_balance() {
        return cash_cu_balance;
    }

    public void setPrint_message(String print_message) {
        this.print_message = print_message;
    }

    public String getPrint_message() {
        return print_message;
    }

    public void setActive_online(int active_online) {
        this.active_online = active_online;
    }

    public int getActive_online() {
        return active_online;
    }

    public void setLoading_txt(String loading_txt) {
        this.loading_txt = loading_txt;
    }

    public String getLoading_txt() {
        return loading_txt;
    }

    public void setChoose_color(String choose_color) {
        this.choose_color = choose_color;
    }

    public void setChoose_size(String choose_size) {
        this.choose_size = choose_size;
    }

    public String getChoose_color() {
        return choose_color;
    }

    public String getChoose_size() {
        return choose_size;
    }
}
