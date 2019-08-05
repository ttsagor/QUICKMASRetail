package com.quickmas.salim.quickmasretail.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBInitialization  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rpsdb.db";

    //---TABLE USERDETAILS
    public static final String TABLE_USER = "userDetails";
    public static final String TABLE_DASH_IMG = "dashImg";
    public static final String TABLE_TEXT = "textString";
    public static final String TABLE_PRODUCT = "productDetails";
    public static final String TABLE_OUTLET = "rtm_outlet";

    //---COLUMN USERDETAILS
    public static final String COLUMN_user_id = "id";
    public static final String COLUMN_user_name = "user_name";
    public static final String COLUMN_user_company_id = "company_id";
    public static final String COLUMN_user_password = "password";
    public static final String COLUMN_user_full_name = "user_full_name";
    public static final String COLUMN_user_company_name = "company_name";
    public static final String COLUMN_user_address1 = "address1";
    public static final String COLUMN_user_address2 = "address2";
    public static final String COLUMN_user_address3 = "address3";
    public static final String COLUMN_user_country = "country";
    public static final String COLUMN_user_phone = "phone";
    public static final String COLUMN_user_logo = "logo";
    public static final String COLUMN_user_email = "email";
    public static final String COLUMN_user_image = "image";
    public static final String COLUMN_user_location = "location";
    public static final String COLUMN_user_selected_Location="selected_location";
    public static final String COLUMN_user_pos = "pos";
    public static final String COLUMN_user_selected_pos="selected_pos";
    public static final String COLUMN_user_order = "user_order";
    public static final String COLUMN_user_payment = "user_payment";
    public static final String COLUMN_user_delivery = "user_delivery";
    public static final String COLUMN_user_photo_limit = "rtm_photo_limit";
    public static final String COLUMN_user_status = "status";
    public static final String COLUMN_user_app_icon = "app_icon";
    public static final String COLUMN_user_loading_color = "loading_color";
    public static final String COLUMN_user_loading_background = "loading_background";
    public static final String COLUMN_user_blank_img = "blank_img";
    public static final String COLUMN_user_cash_cu_balance = "cash_cu_balance";
    public static final String COLUMN_user_print_company = "print_company";
    public static final String COLUMN_user_print_website = "print_website";
    public static final String COLUMN_user_print_message = "print_message";
    public static final String COLUMN_user_active_online = "active_online";
    public static final String COLUMN_user_loading_text = "loading_text";
    public static final String COLUMN_user_choose_color = "choose_color";
    public static final String COLUMN_user_choose_size = "choose_size";

    //-----menu
    public static final String COLUMN_dash_image_id = "id";
    public static final String COLUMN_dash_image_varname = "varname";
    public static final String COLUMN_dash_image_image = "image";
    public static final String COLUMN_dash_image_text = "text";
    public static final String COLUMN_dash_image_category = "category";

    //-----sub menu
    public static final String TABLE_DASH_SUB_MENU = "dashSub";
    public static final String COLUMN_dash_sub_image_id = "id";
    public static final String COLUMN_dash_sub_varname = "varname";
    public static final String COLUMN_dash_sub_text = "text";
    public static final String COLUMN_dash_sub_var = "new_var";


    public static final String COLUMN_text_id = "id";
    public static final String COLUMN_text_varname = "varname";
    public static final String COLUMN_text_text = "text";


    //---TABLE product
    public static final String COLUMN_product_id = "id";
    public static final String COLUMN_product_assign_no = "assign_no";
    public static final String COLUMN_product_point = "point";
    public static final String COLUMN_product_route = "route";
    public static final String COLUMN_product_section = "section";
    public static final String COLUMN_product_frequency = "frequency";
    public static final String COLUMN_product_company = "company";
    public static final String COLUMN_product_product_type = "product_type";
    public static final String COLUMN_product_gift_for = "gift_for";
    public static final String COLUMN_product_product_category = "product_category";
    public static final String COLUMN_product_photo = "photo";
    public static final String COLUMN_product_brand = "brand";
    public static final String COLUMN_product_sku = "sku";
    public static final String COLUMN_product_sub_sku = "sub_sku";
    public static final String COLUMN_product_sku_qty = "sku_qty";
    public static final String COLUMN_product_sku_price = "sku_price";
    public static final String COLUMN_product_target = "target";
    public static final String COLUMN_product_date_from = "date_from";
    public static final String COLUMN_product_date_to = "date_to";
    public static final String COLUMN_product_sold_sku = "sold_sku";
    public static final String COLUMN_product_return_sku = "return_sku";
    public static final String COLUMN_product_total_sku = "total_sku";
    public static final String COLUMN_product_accept_date = "accept_date";
    public static final String COLUMN_product_status = "status";
    public static final String COLUMN_product_order_permission = "order_permission";
    public static final String COLUMN_product_payment_permission = "payment_permission";
    public static final String COLUMN_product_sell_permission = "sell_permission";

    //---TABLE outlet
    public static final String COLUMN_o_id = "id";
    public static final String COLUMN_o_outlet_id = "outlet_id";
    public static final String COLUMN_o_outlet_name = "outlet_name";
    public static final String COLUMN_o_owner_name = "owner_name";
    public static final String COLUMN_o_route = "route";
    public static final String COLUMN_o_section = "section";
    public static final String COLUMN_o_claster = "claster";
    public static final String COLUMN_o_address = "address";
    public static final String COLUMN_o_phone = "phone";
    public static final String COLUMN_o_outlet_type = "outlet_type";
    public static final String COLUMN_o_sales_for = "sales_for";
    public static final String COLUMN_o_outlet_for = "outlet_for";

    //----TABLE invoice
    public static final String TABLE_Invoice = "invoice";
    public static final String COLUMN_i_id = "id";
    public static final String COLUMN_i_invoice_id = "invoice_id";
    public static final String COLUMN_i_outlet_id = "outlet_id";
    public static final String COLUMN_i_route = "route";
    public static final String COLUMN_i_product_id = "product_id";
    public static final String COLUMN_i_quantity = "quantity";
    public static final String COLUMN_i_unit_price = "unit_price";
    public static final String COLUMN_i_invoice_date = "invoice_date";
    public static final String COLUMN_i_status = "status";

    //----TABLE free invoice
    public static final String TABLE_free_Invoice = "free_invoice";
    public static final String COLUMN_free_Invoice_id = "id";
    public static final String COLUMN_free_Invoice_invoice_id = "invoice_id";
    public static final String COLUMN_free_Invoice_outlet_id = "outlet_id";
    public static final String COLUMN_free_Invoice_route = "route";
    public static final String COLUMN_free_Invoice_product_id = "product_id";
    public static final String COLUMN_free_Invoice_product_quantity = "product_quantity";
    public static final String COLUMN_free_Invoice_product_remark = "remark";
    public static final String COLUMN_free_Invoice_product_details = "details";
    public static final String COLUMN_free_Invoice_invoice_by = "invoice_by";
    public static final String COLUMN_free_Invoice_invoice_date = "invoice_date";
    public static final String COLUMN_free_Invoice_status = "status";

    //-----------------------------free invoice head
    public static final String TABLE_free_Invoice_head="free_invoice_head";
    public static final String COLUMN_free_Invoice_head_id="id";
    public static final String COLUMN_free_Invoice_head_product_quantity="product_quantity";
    public static final String COLUMN_free_Invoice_head_outlet="outlet";
    public static final String COLUMN_free_Invoice_head_photo="photo";
    public static final String COLUMN_free_Invoice_head_invoice_by="invoice_by";
    public static final String COLUMN_free_Invoice_head_invoice_date="invoice_date";
    public static final String COLUMN_free_Invoice_head_status="status";


    //----TABLE CRM invoice
    public static final String TABLE_crm_Invoice = "crm_invoice";
    public static final String COLUMN_crm_Invoice_id = "id";
    public static final String COLUMN_crm_Invoice_invoice_id = "invoice_id";
    public static final String COLUMN_crm_Invoice_description = "description";
    public static final String COLUMN_crm_Invoice_product_id = "product_id";
    public static final String COLUMN_crm_Invoice_comment = "comment";
    public static final String COLUMN_crm_Invoice_invoice_by = "invoice_by";
    public static final String COLUMN_crm_Invoice_invoice_date = "invoice_date";
    public static final String COLUMN_crm_Invoice_status = "status";

    //-----------------------------CRM invoice head
    public static final String TABLE_crm_Invoice_head="crm_invoice_head";
    public static final String COLUMN_crm_Invoice_head_id="id";
    public static final String COLUMN_crm_Invoice_head_quantity="quantity";
    public static final String COLUMN_crm_Invoice_head_outlet="outlet";
    public static final String COLUMN_crm_Invoice_head_photo="photo";
    public static final String COLUMN_crm_Invoice_head_invoice_by="invoice_by";
    public static final String COLUMN_crm_Invoice_head_invoice_date="invoice_date";
    public static final String COLUMN_crm_Invoice_head_status="status";

    //-----------------------------POSM invoice head
    public static final String TABLE_posm_Invoice_head="posm_invoice_head";
    public static final String COLUMN_posm_Invoice_head_id="id";
    public static final String COLUMN_posm_Invoice_head_quantity="quantity";
    public static final String COLUMN_posm_Invoice_head_outlet="outlet";
    public static final String COLUMN_posm_Invoice_head_photo="photo";
    public static final String COLUMN_posm_Invoice_head_invoice_by="invoice_by";
    public static final String COLUMN_posm_Invoice_head_invoice_date="invoice_date";
    public static final String COLUMN_posm_Invoice_head_status="status";

    //----TABLE free invoice
    public static final String TABLE_posm_Invoice = "posm_invoice";
    public static final String COLUMN_posm_Invoice_id = "id";
    public static final String COLUMN_posm_Invoice_invoice_id = "invoice_id";
    public static final String COLUMN_posm_Invoice_product_id = "product_id";
    public static final String COLUMN_posm_Invoice_product_quantity = "product_quantity";
    public static final String COLUMN_posm_Invoice_description = "description";
    public static final String COLUMN_posm_Invoice_comment = "comment";
    public static final String COLUMN_posm_Invoice_invoice_by = "invoice_by";
    public static final String COLUMN_posm_Invoice_invoice_date = "invoice_date";
    public static final String COLUMN_posm_Invoice_status = "status";



    //----TABLE MI invoice
    public static final String TABLE_mi_Invoice = "mi_invoice";
    public static final String COLUMN_mi_Invoice_id = "id";
    public static final String COLUMN_mi_Invoice_invoice_id = "invoice_id";
    public static final String COLUMN_mi_Invoice_description = "description";
    public static final String COLUMN_mi_Invoice_product_id = "product_id";
    public static final String COLUMN_mi_Invoice_comment = "comment";
    public static final String COLUMN_mi_Invoice_invoice_by = "invoice_by";
    public static final String COLUMN_mi_Invoice_invoice_date = "invoice_date";
    public static final String COLUMN_mi_Invoice_status = "status";

    //-----------------------------MI invoice head
    public static final String TABLE_mi_Invoice_head="mi_invoice_head";
    public static final String COLUMN_mi_Invoice_head_id="id";
    public static final String COLUMN_mi_Invoice_head_quantity="quantity";
    public static final String COLUMN_mi_Invoice_head_outlet="outlet";
    public static final String COLUMN_mi_Invoice_head_photo="photo";
    public static final String COLUMN_mi_Invoice_head_invoice_by="invoice_by";
    public static final String COLUMN_mi_Invoice_head_invoice_date="invoice_date";
    public static final String COLUMN_mi_Invoice_head_status="status";

    //----TABLE Pos Product
    public static final String TABLE_Pos_Product = "pos_product";
    public static final String COLUMN_pos_product_id = "id";
    public static final String COLUMN_pos_product_name = "name";
    public static final String COLUMN_pos_product_title = "title";
    public static final String COLUMN_pos_product_color = "product_color";
    public static final String COLUMN_pos_product_type = "type";
    public static final String COLUMN_pos_product_sub_type = "sub_type";
    public static final String COLUMN_pos_product_location = "location";
    public static final String COLUMN_pos_product_sub_location = "sub_location";
    public static final String COLUMN_pos_product_features = "features";
    public static final String COLUMN_pos_product_weight = "weight";
    public static final String COLUMN_pos_product_dimensions = "dimensions";
    public static final String COLUMN_pos_product_includes = "includes";
    public static final String COLUMN_pos_product_guarantee= "guarantee";
    public static final String COLUMN_pos_product_quantity = "quantity";
    public static final String COLUMN_pos_product_sub_quantity = "sub_quantity";
    public static final String COLUMN_pos_product_price = "price";
    public static final String COLUMN_pos_product_whole_sale = "whole_sale";
    public static final String COLUMN_pos_product_tax = "tax";
    public static final String COLUMN_pos_product_discount = "discount";
    public static final String COLUMN_pos_product_date_from = "date_from";
    public static final String COLUMN_pos_product_date_to = "date_to";
    public static final String COLUMN_pos_product_hour_from = "hour_from";
    public static final String COLUMN_pos_product_hour_to = "hour_to";
    public static final String COLUMN_pos_product_discount_type = "discount_type";
    public static final String COLUMN_pos_product_photo = "photo";
    public static final String COLUMN_pos_product_sold_quantity = "sold_quantity";
    public static final String COLUMN_pos_product_brand = "brand";
    public static final String COLUMN_pos_product_quantity_left = "quantity_left";
    public static final String COLUMN_pos_product_category_status = "category_status";
    public static final String COLUMN_pos_product_status = "status";


    //----TABLE pos invoice
    public static final String TABLE_PosInvoice = "PosInvoice";
    public static final String TABLE_PosInvoice_regular = "PosInvoice_regular";
    public static final String COLUMN_pos_invoice_id = "id";
    public static final String COLUMN_pos_invoice_invoiceID = "invoiceID";
    public static final String COLUMN_pos_invoice_saleType = "saleType";
    public static final String COLUMN_pos_invoice_productId = "productId";
    public static final String COLUMN_pos_invoice_productSKU = "productSKU";
    public static final String COLUMN_pos_invoice_quantity = "quantity";
    public static final String COLUMN_pos_invoice_exchange = "exchange";
    public static final String COLUMN_pos_invoice_unitPrice = "unitPrice";
    public static final String COLUMN_pos_invoice_tax = "tax";
    public static final String COLUMN_pos_invoice_additionalExpenses = "additionalExpenses";
    public static final String COLUMN_pos_invoice_discount = "discount";
    public static final String COLUMN_pos_invoice_taxIncluded = "taxIncluded";
    public static final String COLUMN_pos_invoice_salesPerson = "salesPerson";
    public static final String COLUMN_pos_invoice_CustomerName = "CustomerName";
    public static final String COLUMN_pos_invoice_cardNumber = "cardNumber";
    public static final String COLUMN_pos_invoice_bankName = "bankName";
    public static final String COLUMN_pos_invoice_cashPayment = "cashPayment";
    public static final String COLUMN_pos_invoice_cardPayment = "cardPayment";
    public static final String COLUMN_pos_invoice_payLaterAmount = "payLaterAmount";
    public static final String COLUMN_pos_invoice_status = "status";
    public static final String COLUMN_pos_invoice_if_synced = "if_synced";


    //----TABLE pos invoice
    public static final String TABLE_PosInvoice_web = "PosInvoice_web";
    public static final String COLUMN_pos_invoice_id_web = "id";
    public static final String COLUMN_pos_invoice_invoiceID_web = "invoiceID";
    public static final String COLUMN_pos_invoice_saleType_web = "saleType";
    public static final String COLUMN_pos_invoice_productId_web = "productId";
    public static final String COLUMN_pos_invoice_productSKU_web = "productSKU";
    public static final String COLUMN_pos_invoice_quantity_web = "quantity";
    public static final String COLUMN_pos_invoice_exchange_web = "exchange";
    public static final String COLUMN_pos_invoice_unitPrice_web = "unitPrice";
    public static final String COLUMN_pos_invoice_tax_web = "tax";
    public static final String COLUMN_pos_invoice_additionalExpenses_web = "additionalExpenses";
    public static final String COLUMN_pos_invoice_discount_web = "discount";
    public static final String COLUMN_pos_invoice_taxIncluded_web = "taxIncluded";
    public static final String COLUMN_pos_invoice_salesPerson_web = "salesPerson";
    public static final String COLUMN_pos_invoice_CustomerName_web = "CustomerName";
    public static final String COLUMN_pos_invoice_cardNumber_web = "cardNumber";
    public static final String COLUMN_pos_invoice_bankName_web = "bankName";
    public static final String COLUMN_pos_invoice_cashPayment_web = "cashPayment";
    public static final String COLUMN_pos_invoice_cardPayment_web = "cardPayment";
    public static final String COLUMN_pos_invoice_payLaterAmount_web = "payLaterAmount";
    public static final String COLUMN_pos_invoice_status_web = "status";



    public static final String TABLE_PosInvoice_regular_web = "PosInvoice_web_regular";


    //----TABLE pos-sales-person
    public static final String TABLE_PosSalesPerson = "PosSalesPerson";
    public static final String COLUMN_pos_sales_person_id = "id";
    public static final String COLUMN_pos_sales_person_name = "pname";
    public static final String COLUMN_pos_sales_person_full_name = "pfull_name";
    public static final String COLUMN_pos_sales_person_isSelected = "isSelected";

    //----TABLE pos-customer
    public static final String TABLE_PosCustomer = "PosCustomer";
    public static final String COLUMN_pos_customer_id = "id";
    public static final String COLUMN_pos_customer_name = "name";
    public static final String COLUMN_pos_customer_full_name = "full_name";
    public static final String COLUMN_pos_customer_phone = "phone";
    public static final String COLUMN_pos_customer_balance = "pre_balance";

    //-------------Bank Information
    public static final String TABLE_bank = "bank_list";
    public static final String COLUMN_bank_id = "id";
    public static final String COLUMN_bank_name = "bank_name";

    //-------------Bank Information
    public static final String TABLE_card = "card_list";
    public static final String COLUMN_card_id = "id";
    public static final String COLUMN_card_name = "card_name";

    //-------------pos invoice head
    public static final String TABLE_pos_invoice_head = "pos_invoice_head";
    public static final String TABLE_pos_invoice_head_regular = "pos_invoice_head_regular";
    public static final String COLUMN_pos_invoice_head_id = "id";
    public static final String COLUMN_pos_invoice_head_invoice_id = "invoice_id";
    public static final String COLUMN_pos_invoice_head_previous_invoice_id = "previous_invoice_id";
    public static final String COLUMN_pos_invoice_head_total_amount_full = "total_amount_full";
    public static final String COLUMN_pos_invoice_head_total_amount="total_amount";
    public static final String COLUMN_pos_invoice_head_total_quantity="total_quantity";
    public static final String COLUMN_pos_invoice_head_total_tax="total_tax";
    public static final String COLUMN_pos_invoice_head_iteam_discount="iteam_discount";
    public static final String COLUMN_pos_invoice_head_additional_discount="additional_discount";
    public static final String COLUMN_pos_invoice_head_additional_discount_percent="additional_discount_percent";
    public static final String COLUMN_pos_invoice_head_delivery_expense="delivery_expense";
    public static final String COLUMN_pos_invoice_head_sales_person="sales_person";
    public static final String COLUMN_pos_invoice_head_customer="customer";
    public static final String COLUMN_pos_invoice_head_payment_mode="payment_mode";
    public static final String COLUMN_pos_invoice_head_cash_amount="cash_amount";
    public static final String COLUMN_pos_invoice_head_note_given="note_given";
    public static final String COLUMN_pos_invoice_head_change="change";
    public static final String COLUMN_pos_invoice_head_card_amount="card_amount";
    public static final String COLUMN_pos_invoice_head_card_type="card_type";
    public static final String COLUMN_pos_invoice_head_cheque_no="cheque_no";
    public static final String COLUMN_pos_invoice_head_bank="bank";
    public static final String COLUMN_pos_invoice_head_pay_later_amount="pay_later_amount";
    public static final String COLUMN_pos_invoice_head_verify_user="verify_user";
    public static final String COLUMN_pos_invoice_head_sale_type="sale_type";
    public static final String COLUMN_pos_invoice_head_total_paid_amount="total_paid_amoount";
    public static final String COLUMN_pos_invoice_head_total_return_amount="return_amount";
    public static final String COLUMN_pos_invoice_head_total_paid_after_return="paid_after_return";
    public static final String COLUMN_pos_invoice_head_invoice_generate_by="invoice_generate_by";
    public static final String COLUMN_pos_invoice_head_invoice_date="invoice_date";
    public static final String COLUMN_pos_invoice_head_status="status";
    public static final String COLUMN_pos_invoice_head_if_synced="if_synced";
    public static final String COLUMN_pos_invoice_head_voucher_no="voucher_no";

    //-------------pos invoice head
    public static final String TABLE_pos_invoice_head_web = "pos_invoice_head_web";
    public static final String COLUMN_pos_invoice_head_id_web = "id";
    public static final String COLUMN_pos_invoice_head_invoice_id_web = "invoice_id";
    public static final String COLUMN_pos_invoice_head_previous_invoice_id_web = "previous_invoice_id";
    public static final String COLUMN_pos_invoice_head_total_amount_full_web = "total_amount_full";
    public static final String COLUMN_pos_invoice_head_total_amount_web="total_amount";
    public static final String COLUMN_pos_invoice_head_total_quantity_web="total_quantity";
    public static final String COLUMN_pos_invoice_head_total_tax_web="total_tax";
    public static final String COLUMN_pos_invoice_head_iteam_discount_web="iteam_discount";
    public static final String COLUMN_pos_invoice_head_additional_discount_web="additional_discount";
    public static final String COLUMN_pos_invoice_head_additional_discount_percent_web="additional_discount_percent";
    public static final String COLUMN_pos_invoice_head_delivery_expense_web="delivery_expense";
    public static final String COLUMN_pos_invoice_head_sales_person_web="sales_person";
    public static final String COLUMN_pos_invoice_head_customer_web="customer";
    public static final String COLUMN_pos_invoice_head_payment_mode_web="payment_mode";
    public static final String COLUMN_pos_invoice_head_cash_amount_web="cash_amount";
    public static final String COLUMN_pos_invoice_head_note_given_web="note_given";
    public static final String COLUMN_pos_invoice_head_change_web="change";
    public static final String COLUMN_pos_invoice_head_card_amount_web="card_amount";
    public static final String COLUMN_pos_invoice_head_card_type_web="card_type";
    public static final String COLUMN_pos_invoice_head_cheque_no_web="cheque_no";
    public static final String COLUMN_pos_invoice_head_bank_web="bank";
    public static final String COLUMN_pos_invoice_head_pay_later_amount_web="pay_later_amount";
    public static final String COLUMN_pos_invoice_head_verify_user_web="verify_user";
    public static final String COLUMN_pos_invoice_head_sale_type_web="sale_type";
    public static final String COLUMN_pos_invoice_head_total_paid_amount_web="total_paid_amoount";
    public static final String COLUMN_pos_invoice_head_new_paid_amount_web="new_paid_amoount";
    public static final String COLUMN_pos_invoice_head_total_return_amount_web="return_amount";
    public static final String COLUMN_pos_invoice_head_total_paid_after_return_web="paid_after_return";
    public static final String COLUMN_pos_invoice_head_invoice_generate_by_web="invoice_generate_by";
    public static final String COLUMN_pos_invoice_head_voucher_no_web="voucher_no";
    public static final String COLUMN_pos_invoice_head_invoice_date_web="invoice_date";
    public static final String COLUMN_pos_invoice_head_status_web="status";

    //-------------pos invoice head
    public static final String TABLE_pos_invoice_regular_head_web = "pos_invoice_regular_head_web";

    //-------------pos verrification users
    public static final String TABLE_pos_ver_user = "pos_verification_user";
    public static final String COLUMN_pos_ver_username = "user_name";
    public static final String COLUMN_pos_ver_password = "password";

    //-------------pos Received payment Information
    public static final String TABLE_pos_received_payment = "pos_received_payment";
    public static final String COLUMN_pos_received_payment_id = "id";
    public static final String COLUMN_pos_received_payment_invoice_id = "invoice_id";
    public static final String COLUMN_pos_received_payment_amount = "amount";
    public static final String COLUMN_pos_received_payment_mode = "payment_mode";
    public static final String COLUMN_pos_received_card_type = "card_type";
    public static final String COLUMN_pos_received_cheque_no = "cheque_no";
    public static final String COLUMN_pos_received_bank = "bank";
    public static final String COLUMN_pos_received_payment_received_by = "received_by";
    public static final String COLUMN_pos_received_payment_received_date = "received_date";

    //-----------------------------CashPaymentReceive
    public static final String TABLE_cash_payment="cash_payment";
    public static final String COLUMN_cash_payemnt_id="id";
    public static final String COLUMN_cash_payemnt_account_name="account_name";
    public static final String COLUMN_cash_payemnt_remark="remark";
    public static final String COLUMN_cash_payemnt_ca_amount="ca_amount";
    public static final String COLUMN_cash_payemnt_cash_amount="cash_amount";
    public static final String COLUMN_cash_payemnt_card_amount="card_amount";
    public static final String COLUMN_cash_payemnt_card_type="card_type";
    public static final String COLUMN_cash_payemnt_bank_account="bank_account";
    public static final String COLUMN_cash_payemnt_bank_name="bank_name";
    public static final String COLUMN_cash_payemnt_customer_name="customer_name";
    public static final String COLUMN_cash_payemnt_cheque_no="cheque_no";
    public static final String COLUMN_cash_payemnt_received_by="received_by";
    public static final String COLUMN_cash_payemnt_date_time="date_time";
    public static final String COLUMN_cash_payemnt_po_id="po_id";
    public static final String COLUMN_cash_payemnt_data_synced="data_synced";
    public static final String COLUMN_cash_payemnt_voucher_no="voucher_no";
    public static final String COLUMN_cash_payemnt_agent="agent";

    //-----------------------------excss_cash
    public static final String TABLE_excss_cash="excss_cash";
    public static final String COLUMN_excss_cash_id="id";
    public static final String COLUMN_excss_cash_customer="customer";
    public static final String COLUMN_excss_cash_amount="amount";
    public static final String COLUMN_excss_cash_receive_id="receive_id";
    public static final String COLUMN_excss_cash_receive_amount="receive_amount";

    //-----------------------------cash_receive_details
    public static final String TABLE_cash_receive_details="cash_receive_details";
    public static final String COLUMN_cash_receive_details_id="id";
    public static final String COLUMN_cash_receive_details_received_id="received_id";
    public static final String COLUMN_cash_receive_details_invoice_id="invoice_id";
    public static final String COLUMN_cash_receive_details_amount="amount";
    public static final String COLUMN_cash_receive_details_date_time="date_time";
    public static final String COLUMN_cash_receive_details_uuid="uuid";
    public static final String COLUMN_cash_receive_details_po_id="po_id";
    public static final String COLUMN_cash_receive_details_voucher_no="voucher_no";

    //-----------------------------coa_details
    public static final String TABLE_cash_receive_coa_details="coa_details";
    public static final String COLUMN_cash_receive_coa_details_id="id";
    public static final String COLUMN_cash_receive_coa_details_received_id="received_id";
    public static final String COLUMN_cash_receive_coa_details_account_name="account_name";
    public static final String COLUMN_cash_receive_coa_details_remarks="remarks";
    public static final String COLUMN_cash_receive_coa_details_amount="amount";
    public static final String COLUMN_cash_receive_coa_details_date_time="date_time";
    public static final String COLUMN_cash_receive_coa_details_uuid="uuid";
    public static final String COLUMN_cash_receive_coa_details_po_uuid="po_uuid";
    public static final String COLUMN_cash_receive_coa_details_if_data_synced="if_data_synced";

    //-----------------------------co_account
    public static final String TABLE_co_account="co_account";
    public static final String COLUMN_co_account_id="id";
    public static final String COLUMN_co_account_name="account_name";
    public static final String COLUMN_co_account_type="account_type";

    //-----------------------------cash_transfer
    public static final String TABLE_cash_transfer="cash_transfer";
    public static final String COLUMN_cash_account_id="id";
    public static final String COLUMN_cash_account_account_name="account_name";
    public static final String COLUMN_cash_account_remarks="remarks";
    public static final String COLUMN_cash_account_amount="amount";
    public static final String COLUMN_cash_account_receive_type="receive_type";
    public static final String COLUMN_cash_account_receive_by="receive_by";
    public static final String COLUMN_cash_account_receive_from="receive_from";
    public static final String COLUMN_cash_account_receive_date="receive_date";
    public static final String COLUMN_cash_account_approve_by="approve_by";
    public static final String COLUMN_cash_account_approve_date="approve_date";
    public static final String COLUMN_cash_account_pos_by="pos_by";
    public static final String COLUMN_cash_account_status="status";


    //-----------------------------cash_account
    public static final String TABLE_cash_account="cash_account";
    public static final String COLUMN_cash_db_account_id="id";
    public static final String COLUMN_cash_db_account_account_name="account_name";


    //-----------------------------cash_transfer
    public static final String TABLE_exchange_details="exchange_details";
    public static final String COLUMN_exchange_details_id="id";
    public static final String COLUMN_exchange_details_previous_inv_id="previous_inv_id";
    public static final String COLUMN_exchange_details_new_inv_id="new_inv_id";
    public static final String COLUMN_exchange_details_product_id="product_id";
    public static final String COLUMN_exchange_details_product_name="product_name";
    public static final String COLUMN_exchange_details_product_quantity="product_quantity";
    public static final String COLUMN_exchange_details_product_amount="product_amount";
    public static final String COLUMN_exchange_details_status="status";
    public static final String COLUMN_exchange_details_date_time="date_time";


    //-------------warehouse Information
    public static final String TABLE_warehouse = "warehouse_list";
    public static final String COLUMN_warehouse_id = "id";
    public static final String COLUMN_warehouse_name = "warehouse_name";

    //-------------stock transfer details
    public static final String TABLE_stock_transfer_details = "stock_transfer_details";
    public static final String COLUMN_stock_transfer_details_id = "id";
    public static final String COLUMN_stock_transfer_details_transfer_id = "transfer_id";
    public static final String COLUMN_stock_transfer_details_product_id = "product_id";
    public static final String COLUMN_stock_transfer_details_product_name = "product_name";
    public static final String COLUMN_stock_transfer_details_product_quantity = "product_quantity";
    public static final String COLUMN_stock_transfer_details_product_unit_price = "product_unit_price";
    public static final String COLUMN_stock_transfer_details_received_qty = "received_qty";
    public static final String COLUMN_stock_transfer_details_delivered_qty = "delivered_qty";
    public static final String COLUMN_stock_transfer_details_status = "status";
    public static final String COLUMN_stock_transfer_details_product_date_time = "date_time";

    //-------------stock transfer head
    public static final String TABLE_stock_transfer = "stock_transfer";
    public static final String COLUMN_stock_transfer_id = "id";
    public static final String COLUMN_stock_transfer_product_quantity = "product_quantity";
    public static final String COLUMN_stock_transfer_product_price = "product_price";
    public static final String COLUMN_stock_transfer_transfer_by = "transfer_by";
    public static final String COLUMN_stock_transfer_status = "status";
    public static final String COLUMN_stock_transfer_product_date_time = "date_time";
    public static final String COLUMN_stock_transfer_product_transfer_to = "transfer_to";
    public static final String COLUMN_stock_transfer_product_entry_by = "entry_by";
    public static final String COLUMN_stock_transfer_product_total_delivered_qty = "total_delivered_qty";
    public static final String COLUMN_stock_transfer_product_total_received_qty = "total_received_qty";
    public static final String COLUMN_stock_transfer_product_pos_by = "pos_by";

    //-------------Product Add Category
    public static final String TABLE_product_add_category = "retail_product_add_category";
    public static final String COLUMN_product_add_category_id = "id";
    public static final String COLUMN_product_add_category_name = "name";
    public static final String COLUMN_product_add_category_category = "category";
    public static final String COLUMN_product_add_category_product_class = "product_class";
    public static final String COLUMN_product_add_category_entry_by = "entry_by";
    public static final String COLUMN_product_add_category_date_time = "date_time";

    //-------------Product Add Category Image Group
    public static final String TABLE_product_add_retail_image_group = "retail_product_image_group";
    public static final String COLUMN_product_add_retail_image_group_id = "id";
    public static final String COLUMN_product_add_retail_image_group_name = "name";
    public static final String COLUMN_product_add_retail_image_group_height = "img_height";
    public static final String COLUMN_product_add_retail_image_group_width = "img_width";
    public static final String COLUMN_product_add_retail_image_group_entry_by = "entry_by";
    public static final String COLUMN_product_add_retail_image_group_date_time = "date_time";


    //--------------Product Add Category Manufacture
    public static final String TABLE_retail_product_manufacture = "manufacture_list";
    public static final String COLUMN_retail_product_manufacture_id = "id";
    public static final String COLUMN_retail_product_manufacture_name = "manufacture_name";

    //--------------Product Add Category Manufacture
    public static final String TABLE_retail_product_brand = "brand_list";
    public static final String COLUMN_retail_product_brand_id = "id";
    public static final String COLUMN_retail_product_brand_name = "brand_name";

    //--------------Product Add Category purchase tax group
    public static final String TABLE_retail_purchase_tax_group = "retail_purchase_tax_group";
    public static final String COLUMN_retail_purchase_tax_group_id = "id";
    public static final String COLUMN_retail_purchase_tax_group_name = "tax_group_name";
    public static final String COLUMN_retail_purchase_tax_group_percentage = "tax_group_percentage";


    //--------------Product Add Category sale tax group
    public static final String TABLE_retail_sales_tax_group = "retail_sales_tax_group";
    public static final String COLUMN_retail_sales_tax_group_id = "id";
    public static final String COLUMN_retail_sales_tax_group_name = "tax_group_name";

    //--------------Market Place
    public static final String TABLE_market_place = "market_place";
    public static final String COLUMN_market_place_id = "id";
    public static final String COLUMN_market_place_name = "market_place_name";

    //--------------New Retail Product
    public static final String TABLE_new_retail_product = "new_retail_product";
    public static final String COLUMN_new_retail_product_id = "id";
    public static final String COLUMN_new_retail_product_entry_id = "entry_id";
    public static final String COLUMN_new_retail_product_category = "category";
    public static final String COLUMN_new_retail_product_sub_category = "sub_category";
    public static final String COLUMN_new_retail_product_sku = "sku";
    public static final String COLUMN_new_retail_product_title = "title";
    public static final String COLUMN_new_retail_product_description = "description";
    public static final String COLUMN_new_retail_product_weight = "weight";
    public static final String COLUMN_new_retail_product_dimension = "dimension";
    public static final String COLUMN_new_retail_product_accessories = "accessories";
    public static final String COLUMN_new_retail_product_warrenty = "warrenty";
    public static final String COLUMN_new_retail_product_image_group = "image_group";
    public static final String COLUMN_new_retail_product_images = "images";
    public static final String COLUMN_new_retail_product_manufacturer = "manufacturer";
    public static final String COLUMN_new_retail_product_brand = "brand";
    public static final String COLUMN_new_retail_product_market_place = "market_place";
    public static final String COLUMN_new_retail_product_approximate_cost = "approximate_cost";
    public static final String COLUMN_new_retail_product_whole_sale_price = "whole_sale_price";
    public static final String COLUMN_new_retail_product_retail_price = "retail_price";
    public static final String COLUMN_new_retail_product_purchase_tax_group = "purchase_tax_group";
    public static final String COLUMN_new_retail_product_sales_tax_group = "sales_tax_group";
    public static final String COLUMN_new_retail_product_entry_by = "entry_by";
    public static final String COLUMN_new_retail_product_entry_date_time = "entry_date_time";
    public static final String COLUMN_new_retail_product_status = "status";


    public static final String TABLE_new_retail_product_head = "new_retail_product_head";
    public static final String COLUMN_new_retail_product_head_id = "id";
    public static final String COLUMN_new_retail_product_head_sku = "sku";
    public static final String COLUMN_new_retail_product_head_quantity = "quantity";
    public static final String COLUMN_new_retail_product_head_sub_photoQuantity = "photoQuantity";
    public static final String COLUMN_new_retail_product_head_entry_by = "entry_by";
    public static final String COLUMN_new_retail_product_head_entry_date = "entry_date";
    public static final String COLUMN_new_retail_product_head_status = "status";


    //------supplier
    public static final String TABLE_supplier= "supplier";
    public static final String COLUMN_supplier_id = "id";
    public static final String COLUMN_supplier_name = "name";
    public static final String COLUMN_supplier_address = "address";
    public static final String COLUMN_supplier_address2 = "address2";
    public static final String COLUMN_supplier_address3 = "address3";
    public static final String COLUMN_supplier_mobile = "mobile";
    public static final String COLUMN_supplier_email = "email";

    //------Purchase Log
    public static final String TABLE_purchase_log= "purchase_log";
    public static final String COLUMN_purchase_log_id = "id";
    public static final String COLUMN_purchase_log_entry_id = "entry_id";
    public static final String COLUMN_purchase_log_supplier = "supplier";
    public static final String COLUMN_purchase_log_sku = "sku";
    public static final String COLUMN_purchase_log_quantity = "quantity";
    public static final String COLUMN_purchase_log_unit_price = "unit_price";
    public static final String COLUMN_purchase_log_amount = "amount";
    public static final String COLUMN_purchase_log_entry_by = "entry_by";
    public static final String COLUMN_purchase_log_entry_date = "entry_date";
    public static final String COLUMN_purchase_log_status = "status";
    public static final String COLUMN_purchase_if_data_sycned = "if_data_sycned";

    //------Purchase Log Invoice
    public static final String TABLE_purchase_log_invoice= "purchase_log_invoice";
    public static final String COLUMN_purchase_log_invoice_id = "id";
    public static final String COLUMN_purchase_log_invoice_suppler = "suppler";
    public static final String COLUMN_purchase_log_invoice_sku_quantity = "sku_quantity";
    public static final String COLUMN_purchase_log_invoice_total_quantity = "total_quantity";
    public static final String COLUMN_purchase_log_invoice_total_amount = "total_amount";
    public static final String COLUMN_purchase_log_invoice_unit_total_tax = "total_tax";
    public static final String COLUMN_purchase_log_invoice_total_discount = "total_discount";
    public static final String COLUMN_purchase_log_invoice_net_payable = "net_payable";
    public static final String COLUMN_purchase_log_invoice_amount_paid = "amount_paid";
    public static final String COLUMN_purchase_log_invoice_entry_by = "entry_by";
    public static final String COLUMN_purchase_log_invoice_entry_date = "entry_date";
    public static final String COLUMN_purchase_log_invoice_status = "status";
    public static final String COLUMN_purchase_log_if_data_synced = "if_data_synced";



    //-----------------------------CashPaymentReceive
    public static final String TABLE_supplier_cash_payment="supplier_cash_payment";
    public static final String COLUMN_supplier_cash_payemnt_id="id";
    public static final String COLUMN_supplier_cash_payemnt_account_name="account_name";
    public static final String COLUMN_supplier_cash_payemnt_remark="remark";
    public static final String COLUMN_supplier_cash_payemnt_ca_amount="ca_amount";
    public static final String COLUMN_supplier_cash_payemnt_cash_amount="cash_amount";
    public static final String COLUMN_supplier_cash_payemnt_card_amount="card_amount";
    public static final String COLUMN_supplier_cash_payemnt_card_type="card_type";
    public static final String COLUMN_supplier_cash_payemnt_bank_account="bank_account";
    public static final String COLUMN_supplier_cash_payemnt_po_id="po_id";
    public static final String COLUMN_supplier_cash_payemnt_bank_name="bank_name";
    public static final String COLUMN_supplier_cash_payemnt_customer_name="customer_name";
    public static final String COLUMN_supplier_cash_payemnt_cheque_no="cheque_no";
    public static final String COLUMN_supplier_cash_payemnt_received_by="received_by";
    public static final String COLUMN_supplier_cash_payemnt_date_time="date_time";
    public static final String COLUMN_supplier_cash_payemnt_if_synced="if_synced";

    //-----------------------------excss_cash
    public static final String TABLE_supplier_excss_cash="supplier_excss_cash";
    public static final String COLUMN_supplier_excss_cash_id="id";
    public static final String COLUMN_supplier_excss_cash_customer="customer";
    public static final String COLUMN_supplier_excss_cash_amount="amount";
    public static final String COLUMN_supplier_excss_cash_receive_id="receive_id";
    public static final String COLUMN_supplier_excss_cash_receive_amount="receive_amount";

    //-----------------------------cash_receive_details
    public static final String TABLE_supplier_cash_receive_details="supplier_cash_receive_details";
    public static final String COLUMN_supplier_cash_receive_details_id="id";
    public static final String COLUMN_supplier_cash_receive_details_received_id="received_id";
    public static final String COLUMN_supplier_cash_receive_details_invoice_id="invoice_id";
    public static final String COLUMN_supplier_cash_receive_details_amount="amount";
    public static final String COLUMN_supplier_cash_receive_details_date_time="date_time";



    //-------------Purchase Received payment Information
    public static final String TABLE_purchase_received_payment = "purchase_received_payment";
    public static final String COLUMN_purchase_received_payment_id = "id";
    public static final String COLUMN_purchase_received_payment_invoice_id = "invoice_id";
    public static final String COLUMN_purchase_received_payment_amount = "amount";
    public static final String COLUMN_purchase_received_payment_mode = "payment_mode";
    public static final String COLUMN_purchase_received_card_type = "card_type";
    public static final String COLUMN_purchase_received_cheque_no = "cheque_no";
    public static final String COLUMN_purchase_received_bank = "bank";
    public static final String COLUMN_purchase_received_payment_received_by = "received_by";
    public static final String COLUMN_purchase_received_payment_received_date = "received_date";

    //-----------------------------co_account expense
    public static final String TABLE_co_exp_account="co_exp_account";
    public static final String COLUMN_co_exp_account_id="id";
    public static final String COLUMN_co_exp_account_name="account_name";
    public static final String COLUMN_co_exp_account_type="account_type";

    //-----------------------------coa_exp_details
    public static final String TABLE_cash_receive_coa_exp_details="coa_exp_details";
    public static final String COLUMN_cash_receive_coa_exp_details_id="id";
    public static final String COLUMN_cash_receive_coa_exp_details_received_id="received_id";
    public static final String COLUMN_cash_receive_coa_exp_details_account_name="account_name";
    public static final String COLUMN_cash_receive_coa_exp_details_remarks="remarks";
    public static final String COLUMN_cash_receive_coa_exp_details_amount="amount";
    public static final String COLUMN_cash_receive_coa_exp_details_uuid="uuid";
    public static final String COLUMN_cash_receive_coa_exp_details_po_id="po_id";
    public static final String COLUMN_cash_receive_coa_exp_details_if_data_synced="if_data_synced";


    //-----------------------------cash statment
    public static final String TABLE_cash_statment="cash_statment";
    public static final String COLUMN_cash_statment_id="id";
    public static final String COLUMN_cash_statmen_entry_time="entry_time";
    public static final String COLUMN_cash_statment_voucher="voucher";
    public static final String COLUMN_cash_statment_inv_coa="inv_coa";
    public static final String COLUMN_cash_statment_debit_amount="debit_amount";
    public static final String COLUMN_cash_statment_credit_amount="credit_amount";
    public static final String COLUMN_cash_statment_cash_cu_balance="cash_cu_balance";

    //-----------------------------discount
    public static final String TABLE_discount_list="discount_list";
    public static final String COLUMN_discount_list_name="discount_list_name";
    public static final String COLUMN_discount_list_discount="discount_list_discount";
    public static final String COLUMN_discount_valid_from="valid_from";
    public static final String COLUMN_discount_valid_to="valid_to";


    //-----------------------------invoice product
    public static final String TABLE_invoice_product="invoice_product";
    public static final String COLUMN_invoice_product_id="id";
    public static final String COLUMN_invoice_product_uuid="uuid";
    public static final String COLUMN_invoice_product_group="product_group";
    public static final String COLUMN_invoice_product_pgroup = "product_pgroup";
    public static final String COLUMN_invoice_product_type = "product_type";
    public static final String COLUMN_invoice_product_ptype = "product_ptype";
    public static final String COLUMN_invoice_product_pclass = "product_pclass";
    public static final String COLUMN_invoice_product_pcategory = "product_pcategory";
    public static final String COLUMN_invoice_product_psub_category = "psub_category";
    public static final String COLUMN_invoice_product_company = "product_company";
    public static final String COLUMN_invoice_product_brand = "product_brand";
    public static final String COLUMN_invoice_product_name = "product_name";
    public static final String COLUMN_invoice_product_names = "product_names";
    public static final String COLUMN_invoice_product_sale_coa = "product_sale_coa";
    public static final String COLUMN_invoice_product_barcode = "product_barcode";
    public static final String COLUMN_invoice_product_wholesale = "product_wholesale";
    public static final String COLUMN_invoice_product_price = "product_price";
    public static final String COLUMN_invoice_product_vat = "product_vat";
    public static final String COLUMN_invoice_product_discount_amount = "product_discount_amount";
    public static final String COLUMN_invoice_product_discount_type = "discount_type";
    public static final String COLUMN_invoice_product_from = "product_from";
    public static final String COLUMN_invoice_product_to = "product_to";
    public static final String COLUMN_invoice_product_from_hr = "product_from_hr";
    public static final String COLUMN_invoice_product_to_hr = "product_toto_hr";



    //-----------------------------invoice product
    public static final String TABLE_payment_rece_vou="payment_rece_vou";
    public static final String COLUMN_payment_rece_vou_id="id";
    public static final String COLUMN_payment_rece_vou_sl_id="sl_id";
    public static final String COLUMN_payment_rece_vou_inv_id="inv_id";
    public static final String COLUMN_payment_rece_vou_category="category";
    public static final String COLUMN_payment_rece_vou_customer = "customer";
    public static final String COLUMN_payment_rece_vou_payment_mode = "payment_mode";
    public static final String COLUMN_payment_rece_vou_print_by = "print_by";
    public static final String COLUMN_payment_rece_vou_print_date = "print_date";
    public static final String COLUMN_payment_rece_vou_amount = "amount";
    public static final String COLUMN_payment_rece_vou_card_info = "card_info";
    public static final String COLUMN_payment_rece_vou_card_no = "card_no";
    public static final String COLUMN_payment_rece_vou_remark = "remark";


    public DBInitialization(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DASH_IMG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DASH_SUB_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEXT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTLET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Invoice);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Pos_Product);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PosInvoice);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PosInvoice_regular);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PosInvoice_web);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PosSalesPerson);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PosCustomer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_bank);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_card);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pos_invoice_head);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pos_invoice_head_regular);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pos_invoice_head_web);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pos_ver_user);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pos_received_payment);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cash_payment);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_excss_cash);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cash_receive_details);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_co_account);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cash_receive_coa_details);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cash_transfer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cash_account);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_exchange_details);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_warehouse);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_stock_transfer_details);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_stock_transfer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_product_add_category);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_product_add_retail_image_group);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_retail_product_manufacture);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_retail_product_brand);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_retail_purchase_tax_group);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_retail_sales_tax_group);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_market_place);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_new_retail_product);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_new_retail_product_head);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_supplier);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_purchase_log);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_purchase_log_invoice);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_supplier_cash_payment);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_supplier_excss_cash);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_supplier_cash_receive_details);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_purchase_received_payment);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_co_exp_account);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cash_receive_coa_exp_details);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_free_Invoice);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_free_Invoice_head);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_crm_Invoice);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_crm_Invoice_head);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_mi_Invoice);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_mi_Invoice_head);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_posm_Invoice_head);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_posm_Invoice);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cash_statment);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_discount_list);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_pos_invoice_regular_head_web);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PosInvoice_regular_web);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_invoice_product);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_payment_rece_vou);
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase db) {
        //----USERDETAILS TABLE
        String query = "CREATE TABLE " + TABLE_USER + " ( " +
                COLUMN_user_id + " INTEGER PRIMARY KEY, " +
                COLUMN_user_name + " TEXT , " +
                COLUMN_user_company_id + " TEXT , " +
                COLUMN_user_password + " TEXT , " +
                COLUMN_user_full_name + " TEXT , " +
                COLUMN_user_company_name + " TEXT , " +
                COLUMN_user_address1 + " TEXT , " +
                COLUMN_user_address2 + " TEXT , " +
                COLUMN_user_address3 + " TEXT , " +
                COLUMN_user_country+ " TEXT , " +
                COLUMN_user_phone + " TEXT , " +
                COLUMN_user_logo + " TEXT , " +
                COLUMN_user_email + " TEXT , " +
                COLUMN_user_image + " TEXT , " +
                COLUMN_user_location + " TEXT , " +
                COLUMN_user_selected_Location + " TEXT , " +
                COLUMN_user_pos + " TEXT , " +
                COLUMN_user_selected_pos + " TEXT , " +
                COLUMN_user_order + " TEXT , " +
                COLUMN_user_payment + " TEXT , " +
                COLUMN_user_delivery + " TEXT , " +
                COLUMN_user_app_icon + " TEXT , " +
                COLUMN_user_loading_color + " TEXT , " +
                COLUMN_user_loading_background + " TEXT , " +
                COLUMN_user_blank_img + " TEXT , " +
                COLUMN_user_photo_limit + " INTEGER , " +
                COLUMN_user_cash_cu_balance + " REAL , " +
                COLUMN_user_print_company + " TEXT , " +
                COLUMN_user_print_website + " TEXT , " +
                COLUMN_user_print_message + " TEXT , " +
                COLUMN_user_loading_text + " TEXT , " +
                COLUMN_user_choose_color + " TEXT , " +
                COLUMN_user_choose_size + " TEXT , " +
                COLUMN_user_active_online + " INTEGER , " +
                COLUMN_user_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        //----DASHIMAGE TABLE
        query = "CREATE TABLE " + TABLE_DASH_IMG + " ( " +
                COLUMN_dash_image_id + " INTEGER PRIMARY KEY, " +
                COLUMN_dash_image_varname + " TEXT, " +
                COLUMN_dash_image_image + " TEXT , " +
                COLUMN_dash_image_category + " TEXT , " +
                COLUMN_dash_image_text + " TEXT );";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        //----DASH SUB TABLE
        query = "CREATE TABLE " + TABLE_DASH_SUB_MENU + " ( " +
                COLUMN_dash_sub_image_id + " INTEGER PRIMARY KEY, " +
                COLUMN_dash_sub_varname + " TEXT, " +
                COLUMN_dash_sub_var + " TEXT, " +
                COLUMN_dash_sub_text + " TEXT );";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        //----TEXT TABLE
        query = "CREATE TABLE " + TABLE_TEXT + " ( " +
                COLUMN_text_id + " INTEGER PRIMARY KEY, " +
                COLUMN_text_varname + " TEXT, " +
                COLUMN_text_text + " TEXT );";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //----productDetails TABLE
        query = "CREATE TABLE " + TABLE_PRODUCT + " ( " +
                COLUMN_product_id + " INTEGER PRIMARY KEY, " +
                COLUMN_product_assign_no + " TEXT , " +
                COLUMN_product_point + " TEXT , " +
                COLUMN_product_route + " TEXT , " +
                COLUMN_product_section + " TEXT , " +
                COLUMN_product_frequency + " TEXT , " +
                COLUMN_product_company + " TEXT , " +
                COLUMN_product_product_type + " TEXT , " +
                COLUMN_product_gift_for + " TEXT , " +
                COLUMN_product_product_category + " TEXT , " +
                COLUMN_product_photo + " TEXT , " +
                COLUMN_product_brand + " TEXT , " +
                COLUMN_product_sku + " TEXT , " +
                COLUMN_product_sub_sku + " TEXT , " +
                COLUMN_product_sku_qty + " INTEGER , " +
                COLUMN_product_sku_price + " REAL , " +
                COLUMN_product_target + " INTEGER , " +
                COLUMN_product_date_from + " TEXT , " +
                COLUMN_product_date_to + " TEXT , " +
                COLUMN_product_sold_sku + " INTEGER , " +
                COLUMN_product_return_sku + " INTEGER , " +
                COLUMN_product_total_sku + " INTEGER , " +
                COLUMN_product_accept_date + " TEXT , " +
                COLUMN_product_status + " INTEGER , " +
                COLUMN_product_order_permission + " TEXT , " +
                COLUMN_product_payment_permission + " TEXT , " +
                COLUMN_product_sell_permission + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_OUTLET + " ( " +
                COLUMN_o_id + " INTEGER PRIMARY KEY, " +
                COLUMN_o_outlet_id + " TEXT , " +
                COLUMN_o_outlet_name + " TEXT , " +
                COLUMN_o_owner_name + " TEXT , " +
                COLUMN_o_route + " TEXT , " +
                COLUMN_o_section + " TEXT , " +
                COLUMN_o_claster + " TEXT , " +
                COLUMN_o_address + " TEXT , " +
                COLUMN_o_phone + " TEXT , " +
                COLUMN_o_outlet_type + " TEXT , " +
                COLUMN_o_sales_for + " TEXT , " +
                COLUMN_o_outlet_for + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_Invoice + " ( " +
                COLUMN_i_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_i_invoice_id + " INTEGER , " +
                COLUMN_i_outlet_id + " INTEGER , " +
                COLUMN_i_route + " TEXT , " +
                COLUMN_i_product_id + " INTEGER , " +
                COLUMN_i_quantity + " INTEGER , " +
                COLUMN_i_unit_price + " REAL , " +
                COLUMN_i_invoice_date + " TEXT , " +
                COLUMN_i_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_Pos_Product + " ( " +
                COLUMN_pos_product_id + " INTEGER PRIMARY KEY, " +
                COLUMN_pos_product_name + " TEXT , " +
                COLUMN_pos_product_title + " TEXT , " +
                COLUMN_pos_product_color + " TEXT , " +
                COLUMN_pos_product_type + " TEXT , " +
                COLUMN_pos_product_sub_type + " TEXT , " +
                COLUMN_pos_product_location + " TEXT , " +
                COLUMN_pos_product_sub_location + " TEXT , " +
                COLUMN_pos_product_features + " TEXT , " +
                COLUMN_pos_product_weight  + " TEXT , " +
                COLUMN_pos_product_dimensions + " TEXT , " +
                COLUMN_pos_product_includes  + " TEXT , " +
                COLUMN_pos_product_guarantee  + " TEXT , " +
                COLUMN_pos_product_quantity + " INTEGER , " +
                COLUMN_pos_product_sub_quantity + " INTEGER , " +
                COLUMN_pos_product_price + " REAL , " +
                COLUMN_pos_product_whole_sale + " REAL , " +
                COLUMN_pos_product_tax + " REAL , " +
                COLUMN_pos_product_discount + " REAL , "+
                COLUMN_pos_product_discount_type + " TEXT , "+
                COLUMN_pos_product_date_from + " TEXT , "+
                COLUMN_pos_product_date_to  + " TEXT , "+
                COLUMN_pos_product_hour_from + " TEXT , "+
                COLUMN_pos_product_hour_to + " TEXT , "+
                COLUMN_pos_product_photo + " TEXT , " +
                COLUMN_pos_product_brand + " TEXT , " +
                COLUMN_pos_product_quantity_left + " INTEGER , " +
                COLUMN_pos_product_sold_quantity + " INTEGER , " +
                COLUMN_pos_product_category_status + " TEXT , " +
                COLUMN_pos_product_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_PosInvoice + " ( " +
                COLUMN_pos_invoice_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_pos_invoice_invoiceID + " INTEGER , " +
                COLUMN_pos_invoice_saleType + " TEXT , " +
                COLUMN_pos_invoice_productId + " INTEGER , " +
                COLUMN_pos_invoice_productSKU + " TEXT , " +
                COLUMN_pos_invoice_quantity + " INTEGER , " +
                COLUMN_pos_invoice_exchange + " INTEGER , " +
                COLUMN_pos_invoice_unitPrice + " REAL , " +
                COLUMN_pos_invoice_tax + " REAL , " +
                COLUMN_pos_invoice_additionalExpenses  + " REAL , " +
                COLUMN_pos_invoice_discount + " REAL , " +
                COLUMN_pos_invoice_taxIncluded  + " INTEGER , " +
                COLUMN_pos_invoice_salesPerson  + " TEXT , " +
                COLUMN_pos_invoice_CustomerName + " TEXT , " +
                COLUMN_pos_invoice_cardNumber + " TEXT , " +
                COLUMN_pos_invoice_bankName + " REAL , " +
                COLUMN_pos_invoice_cashPayment + " REAL , " +
                COLUMN_pos_invoice_cardPayment + " REAL , " +
                COLUMN_pos_invoice_payLaterAmount + " REAL , " +
                COLUMN_pos_invoice_if_synced + " INTEGER , " +
                COLUMN_pos_invoice_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

         query = "CREATE TABLE " + TABLE_PosInvoice_regular + " ( " +
                COLUMN_pos_invoice_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_pos_invoice_invoiceID + " INTEGER , " +
                COLUMN_pos_invoice_saleType + " TEXT , " +
                COLUMN_pos_invoice_productId + " INTEGER , " +
                COLUMN_pos_invoice_productSKU + " TEXT , " +
                COLUMN_pos_invoice_quantity + " INTEGER , " +
                COLUMN_pos_invoice_exchange + " INTEGER , " +
                COLUMN_pos_invoice_unitPrice + " REAL , " +
                COLUMN_pos_invoice_tax + " REAL , " +
                COLUMN_pos_invoice_additionalExpenses  + " REAL , " +
                COLUMN_pos_invoice_discount + " REAL , " +
                COLUMN_pos_invoice_taxIncluded  + " INTEGER , " +
                COLUMN_pos_invoice_salesPerson  + " TEXT , " +
                COLUMN_pos_invoice_CustomerName + " TEXT , " +
                COLUMN_pos_invoice_cardNumber + " TEXT , " +
                COLUMN_pos_invoice_bankName + " REAL , " +
                COLUMN_pos_invoice_cashPayment + " REAL , " +
                COLUMN_pos_invoice_cardPayment + " REAL , " +
                COLUMN_pos_invoice_payLaterAmount + " REAL , " +
                COLUMN_pos_invoice_if_synced + " INTEGER , " +
                COLUMN_pos_invoice_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_PosInvoice_web + " ( " +
                COLUMN_pos_invoice_id_web + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_pos_invoice_invoiceID_web + " INTEGER , " +
                COLUMN_pos_invoice_saleType_web + " TEXT , " +
                COLUMN_pos_invoice_productId_web + " INTEGER , " +
                COLUMN_pos_invoice_productSKU_web + " TEXT , " +
                COLUMN_pos_invoice_quantity_web + " INTEGER , " +
                COLUMN_pos_invoice_exchange_web + " INTEGER , " +
                COLUMN_pos_invoice_unitPrice_web + " REAL , " +
                COLUMN_pos_invoice_tax_web + " REAL , " +
                COLUMN_pos_invoice_additionalExpenses_web  + " REAL , " +
                COLUMN_pos_invoice_discount_web + " REAL , " +
                COLUMN_pos_invoice_taxIncluded_web  + " INTEGER , " +
                COLUMN_pos_invoice_salesPerson_web  + " TEXT , " +
                COLUMN_pos_invoice_CustomerName_web + " TEXT , " +
                COLUMN_pos_invoice_cardNumber_web + " TEXT , " +
                COLUMN_pos_invoice_bankName_web + " REAL , " +
                COLUMN_pos_invoice_cashPayment_web + " REAL , " +
                COLUMN_pos_invoice_cardPayment_web + " REAL , " +
                COLUMN_pos_invoice_payLaterAmount_web + " REAL , " +
                COLUMN_pos_invoice_status_web + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_PosInvoice_regular_web + " ( " +
                COLUMN_pos_invoice_id_web + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_pos_invoice_invoiceID_web + " INTEGER , " +
                COLUMN_pos_invoice_saleType_web + " TEXT , " +
                COLUMN_pos_invoice_productId_web + " INTEGER , " +
                COLUMN_pos_invoice_productSKU_web + " TEXT , " +
                COLUMN_pos_invoice_quantity_web + " INTEGER , " +
                COLUMN_pos_invoice_exchange_web + " INTEGER , " +
                COLUMN_pos_invoice_unitPrice_web + " REAL , " +
                COLUMN_pos_invoice_tax_web + " REAL , " +
                COLUMN_pos_invoice_additionalExpenses_web  + " REAL , " +
                COLUMN_pos_invoice_discount_web + " REAL , " +
                COLUMN_pos_invoice_taxIncluded_web  + " INTEGER , " +
                COLUMN_pos_invoice_salesPerson_web  + " TEXT , " +
                COLUMN_pos_invoice_CustomerName_web + " TEXT , " +
                COLUMN_pos_invoice_cardNumber_web + " TEXT , " +
                COLUMN_pos_invoice_bankName_web + " REAL , " +
                COLUMN_pos_invoice_cashPayment_web + " REAL , " +
                COLUMN_pos_invoice_cardPayment_web + " REAL , " +
                COLUMN_pos_invoice_payLaterAmount_web + " REAL , " +
                COLUMN_pos_invoice_status_web + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_PosSalesPerson + " ( " +
                COLUMN_pos_sales_person_id + " INTEGER PRIMARY KEY, " +
                COLUMN_pos_sales_person_name + " TEXT  , " +
                COLUMN_pos_sales_person_isSelected + " INTEGER  , " +
                COLUMN_pos_sales_person_full_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_PosCustomer + " ( " +
                COLUMN_pos_customer_id + " INTEGER PRIMARY KEY, " +
                COLUMN_pos_customer_name + " TEXT  , " +
                COLUMN_pos_customer_full_name + " TEXT  , " +
                COLUMN_pos_customer_balance + " REAL  , " +
                COLUMN_pos_customer_phone + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_bank + " ( " +
                COLUMN_bank_id + " INTEGER PRIMARY KEY, " +
                COLUMN_bank_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_card + " ( " +
                COLUMN_card_id + " INTEGER PRIMARY KEY, " +
                COLUMN_card_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_pos_invoice_head+ " ( " +
                COLUMN_pos_invoice_head_id + " INTEGER PRIMARY KEY, " +
                COLUMN_pos_invoice_head_invoice_id + " TEXT , " +
                COLUMN_pos_invoice_head_previous_invoice_id + " TEXT , " +
                COLUMN_pos_invoice_head_total_amount_full + " REAL , " +
                COLUMN_pos_invoice_head_total_amount + " REAL , " +
                COLUMN_pos_invoice_head_total_quantity + " INTEGER , " +
                COLUMN_pos_invoice_head_total_tax + " REAL , " +
                COLUMN_pos_invoice_head_iteam_discount + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount_percent + " REAL , " +
                COLUMN_pos_invoice_head_delivery_expense + " REAL , " +
                COLUMN_pos_invoice_head_sales_person + " TEXT , " +
                COLUMN_pos_invoice_head_customer + " TEXT , " +
                COLUMN_pos_invoice_head_payment_mode  + " TEXT , " +
                COLUMN_pos_invoice_head_cash_amount + " REAL , " +
                COLUMN_pos_invoice_head_note_given  + " REAL , " +
                COLUMN_pos_invoice_head_change  + " REAL , " +
                COLUMN_pos_invoice_head_card_amount + " REAL , " +
                COLUMN_pos_invoice_head_card_type + " TEXT , " +
                COLUMN_pos_invoice_head_cheque_no + " TEXT , " +
                COLUMN_pos_invoice_head_bank + " TEXT , " +
                COLUMN_pos_invoice_head_pay_later_amount + " REAL , " +
                COLUMN_pos_invoice_head_verify_user + " TEXT , " +
                COLUMN_pos_invoice_head_sale_type + " TEXT , " +
                COLUMN_pos_invoice_head_total_paid_amount + " REAL , " +
                COLUMN_pos_invoice_head_total_return_amount + " REAL , " +
                COLUMN_pos_invoice_head_total_paid_after_return + " REAL , " +
                COLUMN_pos_invoice_head_invoice_generate_by + " TEXT , " +
                COLUMN_pos_invoice_head_invoice_date + " TEXT , " +
                COLUMN_pos_invoice_head_if_synced + " INTEGER , " +
                COLUMN_pos_invoice_head_voucher_no + " TEXT , " +
                COLUMN_pos_invoice_head_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_pos_invoice_head_regular+ " ( " +
                COLUMN_pos_invoice_head_id + " INTEGER PRIMARY KEY, " +
                COLUMN_pos_invoice_head_invoice_id + " TEXT , " +
                COLUMN_pos_invoice_head_previous_invoice_id + " TEXT , " +
                COLUMN_pos_invoice_head_total_amount_full + " REAL , " +
                COLUMN_pos_invoice_head_total_amount + " REAL , " +
                COLUMN_pos_invoice_head_total_quantity + " INTEGER , " +
                COLUMN_pos_invoice_head_total_tax + " REAL , " +
                COLUMN_pos_invoice_head_iteam_discount + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount_percent + " REAL , " +
                COLUMN_pos_invoice_head_delivery_expense + " REAL , " +
                COLUMN_pos_invoice_head_sales_person + " TEXT , " +
                COLUMN_pos_invoice_head_customer + " TEXT , " +
                COLUMN_pos_invoice_head_payment_mode  + " TEXT , " +
                COLUMN_pos_invoice_head_cash_amount + " REAL , " +
                COLUMN_pos_invoice_head_note_given  + " REAL , " +
                COLUMN_pos_invoice_head_change  + " REAL , " +
                COLUMN_pos_invoice_head_card_amount + " REAL , " +
                COLUMN_pos_invoice_head_card_type + " TEXT , " +
                COLUMN_pos_invoice_head_cheque_no + " TEXT , " +
                COLUMN_pos_invoice_head_bank + " TEXT , " +
                COLUMN_pos_invoice_head_pay_later_amount + " REAL , " +
                COLUMN_pos_invoice_head_verify_user + " TEXT , " +
                COLUMN_pos_invoice_head_sale_type + " TEXT , " +
                COLUMN_pos_invoice_head_total_paid_amount + " REAL , " +
                COLUMN_pos_invoice_head_total_return_amount + " REAL , " +
                COLUMN_pos_invoice_head_total_paid_after_return + " REAL , " +
                COLUMN_pos_invoice_head_invoice_generate_by + " TEXT , " +
                COLUMN_pos_invoice_head_voucher_no + " TEXT , " +
                COLUMN_pos_invoice_head_invoice_date + " TEXT , " +
                COLUMN_pos_invoice_head_if_synced + " INTEGER , " +
                COLUMN_pos_invoice_head_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_pos_invoice_head_web+ " ( " +
                COLUMN_pos_invoice_head_id_web + " INTEGER PRIMARY KEY, " +
                COLUMN_pos_invoice_head_invoice_id_web + " TEXT , " +
                COLUMN_pos_invoice_head_previous_invoice_id_web + " TEXT , " +
                COLUMN_pos_invoice_head_total_amount_full_web + " REAL , " +
                COLUMN_pos_invoice_head_total_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_total_quantity_web + " INTEGER , " +
                COLUMN_pos_invoice_head_total_tax_web + " REAL , " +
                COLUMN_pos_invoice_head_iteam_discount_web + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount_web + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount_percent_web + " REAL , " +
                COLUMN_pos_invoice_head_delivery_expense_web + " REAL , " +
                COLUMN_pos_invoice_head_sales_person_web + " TEXT , " +
                COLUMN_pos_invoice_head_customer_web + " TEXT , " +
                COLUMN_pos_invoice_head_payment_mode_web  + " TEXT , " +
                COLUMN_pos_invoice_head_cash_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_note_given_web  + " REAL , " +
                COLUMN_pos_invoice_head_change_web  + " REAL , " +
                COLUMN_pos_invoice_head_card_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_card_type_web + " TEXT , " +
                COLUMN_pos_invoice_head_cheque_no_web + " TEXT , " +
                COLUMN_pos_invoice_head_bank_web + " TEXT , " +
                COLUMN_pos_invoice_head_pay_later_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_verify_user_web + " TEXT , " +
                COLUMN_pos_invoice_head_sale_type_web + " TEXT , " +
                COLUMN_pos_invoice_head_total_paid_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_new_paid_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_total_return_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_total_paid_after_return_web + " REAL , " +
                COLUMN_pos_invoice_head_invoice_generate_by_web + " TEXT , " +
                COLUMN_pos_invoice_head_voucher_no_web + " TEXT , " +
                COLUMN_pos_invoice_head_invoice_date_web + " TEXT , " +
                COLUMN_pos_invoice_head_status_web + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_pos_invoice_regular_head_web+ " ( " +
                COLUMN_pos_invoice_head_id_web + " INTEGER PRIMARY KEY, " +
                COLUMN_pos_invoice_head_invoice_id_web + " TEXT , " +
                COLUMN_pos_invoice_head_previous_invoice_id_web + " TEXT , " +
                COLUMN_pos_invoice_head_total_amount_full_web + " REAL , " +
                COLUMN_pos_invoice_head_total_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_total_quantity_web + " INTEGER , " +
                COLUMN_pos_invoice_head_total_tax_web + " REAL , " +
                COLUMN_pos_invoice_head_iteam_discount_web + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount_web + " REAL , " +
                COLUMN_pos_invoice_head_additional_discount_percent_web + " REAL , " +
                COLUMN_pos_invoice_head_delivery_expense_web + " REAL , " +
                COLUMN_pos_invoice_head_sales_person_web + " TEXT , " +
                COLUMN_pos_invoice_head_customer_web + " TEXT , " +
                COLUMN_pos_invoice_head_payment_mode_web  + " TEXT , " +
                COLUMN_pos_invoice_head_cash_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_note_given_web  + " REAL , " +
                COLUMN_pos_invoice_head_change_web  + " REAL , " +
                COLUMN_pos_invoice_head_card_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_card_type_web + " TEXT , " +
                COLUMN_pos_invoice_head_cheque_no_web + " TEXT , " +
                COLUMN_pos_invoice_head_bank_web + " TEXT , " +
                COLUMN_pos_invoice_head_pay_later_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_verify_user_web + " TEXT , " +
                COLUMN_pos_invoice_head_sale_type_web + " TEXT , " +
                COLUMN_pos_invoice_head_total_paid_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_new_paid_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_total_return_amount_web + " REAL , " +
                COLUMN_pos_invoice_head_total_paid_after_return_web + " REAL , " +
                COLUMN_pos_invoice_head_voucher_no_web + " TEXT , " +
                COLUMN_pos_invoice_head_invoice_generate_by_web + " TEXT , " +
                COLUMN_pos_invoice_head_invoice_date_web + " TEXT , " +
                COLUMN_pos_invoice_head_status_web + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_pos_ver_user + " ( " +
                COLUMN_pos_ver_username + " TEXT PRIMARY KEY, " +
                COLUMN_pos_ver_password + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_pos_received_payment + " ( " +
                COLUMN_pos_received_payment_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_pos_received_payment_invoice_id + " INTEGER  , " +
                COLUMN_pos_received_payment_amount + " REAL  , " +
                COLUMN_pos_received_payment_mode+ " TEXT  , " +
                COLUMN_pos_received_card_type + " TEXT  , " +
                COLUMN_pos_received_cheque_no + " TEXT  , " +
                COLUMN_pos_received_bank + " TEXT  , " +
                COLUMN_pos_received_payment_received_by + " TEXT  , " +
                COLUMN_pos_received_payment_received_date + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_cash_payment + " ( " +
                COLUMN_cash_payemnt_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_cash_payemnt_account_name + " TEXT  , " +
                COLUMN_cash_payemnt_remark + " TEXT  , " +
                COLUMN_cash_payemnt_ca_amount+ " REAL  , " +
                COLUMN_cash_payemnt_cash_amount + " REAL  , " +
                COLUMN_cash_payemnt_card_amount + " REAL  , " +
                COLUMN_cash_payemnt_card_type + " TEXT  , " +
                COLUMN_cash_payemnt_bank_account + " TEXT  , " +
                COLUMN_cash_payemnt_bank_name + " TEXT  , " +
                COLUMN_cash_payemnt_cheque_no + " TEXT  , " +
                COLUMN_cash_payemnt_customer_name + " TEXT  , " +
                COLUMN_cash_payemnt_received_by + " TEXT  , " +
                COLUMN_cash_payemnt_po_id + " TEXT  , " +
                COLUMN_cash_payemnt_data_synced + " INTEGER  , " +
                COLUMN_cash_payemnt_voucher_no + " TEXT  , " +
                COLUMN_cash_payemnt_agent + " TEXT  , " +
                COLUMN_cash_payemnt_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_excss_cash + " ( " +
                COLUMN_excss_cash_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_excss_cash_customer + " TEXT, " +
                COLUMN_excss_cash_amount  + " REAL, " +
                COLUMN_excss_cash_receive_id +  " INTEGER, "+
                COLUMN_excss_cash_receive_amount+ " REAL);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }



        query = "CREATE TABLE " + TABLE_cash_receive_details + " ( " +
                COLUMN_cash_receive_details_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_cash_receive_details_received_id + " INTEGER  , " +
                COLUMN_cash_receive_details_invoice_id + " INTEGER  , " +
                COLUMN_cash_receive_details_amount + " REAL  , " +
                COLUMN_cash_receive_details_uuid + " TEXT  , " +
                COLUMN_cash_receive_details_po_id + " TEXT  , " +
                COLUMN_cash_receive_details_voucher_no + " TEXT  , " +
                COLUMN_cash_receive_details_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_co_account + " ( " +
                COLUMN_co_account_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_co_account_type + " TEXT, " +
                COLUMN_co_account_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_cash_receive_coa_details + " ( " +
                COLUMN_cash_receive_coa_details_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_cash_receive_coa_details_received_id + " TEXT, " +
                COLUMN_cash_receive_coa_details_account_name + " TEXT, " +
                COLUMN_cash_receive_coa_details_remarks + " TEXT, " +
                COLUMN_cash_receive_coa_details_amount + " REAL, " +
                COLUMN_cash_receive_coa_details_uuid + " TEXT, " +
                COLUMN_cash_receive_coa_details_po_uuid + " TEXT, " +
                COLUMN_cash_receive_coa_details_if_data_synced + " INTEGER, " +
                COLUMN_cash_receive_coa_details_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_cash_transfer + " ( " +
                COLUMN_cash_account_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_cash_account_account_name + " TEXT, " +
                COLUMN_cash_account_remarks + " TEXT, " +
                COLUMN_cash_account_amount + " REAL, " +
                COLUMN_cash_account_receive_type + " TEXT, " +
                COLUMN_cash_account_receive_by + " TEXT, " +
                COLUMN_cash_account_receive_from + " TEXT, " +
                COLUMN_cash_account_receive_date + " TEXT, " +
                COLUMN_cash_account_approve_by + " TEXT, " +
                COLUMN_cash_account_approve_date + " TEXT, " +
                COLUMN_cash_account_pos_by + " TEXT, " +
                COLUMN_cash_account_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_cash_account + " ( " +
                COLUMN_cash_db_account_id + " INTEGER PRIMARY KEY , " +
                COLUMN_cash_db_account_account_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_exchange_details + " ( " +
                COLUMN_exchange_details_id + " INTEGER PRIMARY KEY , " +
                COLUMN_exchange_details_previous_inv_id + " TEXT , " +
                COLUMN_exchange_details_new_inv_id + " TEXT , " +
                COLUMN_exchange_details_product_id + " TEXT , " +
                COLUMN_exchange_details_product_name + " TEXT , " +
                COLUMN_exchange_details_product_quantity + " INTEGER , " +
                COLUMN_exchange_details_product_amount + " REAL , " +
                COLUMN_exchange_details_status + " INTEGER , " +
                COLUMN_exchange_details_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_warehouse + " ( " +
                COLUMN_warehouse_id + " INTEGER PRIMARY KEY , " +
                COLUMN_warehouse_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_stock_transfer_details + " ( " +
                COLUMN_stock_transfer_details_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_stock_transfer_details_transfer_id + " INTEGER , " +
                COLUMN_stock_transfer_details_product_id + " INTEGER , " +
                COLUMN_stock_transfer_details_product_name + " TEXT , " +
                COLUMN_stock_transfer_details_product_quantity + " INTEGER , " +
                COLUMN_stock_transfer_details_product_unit_price + " REAL , " +
                COLUMN_stock_transfer_details_received_qty  + " INTEGER , " +
                COLUMN_stock_transfer_details_delivered_qty + " INTEGER , " +
                COLUMN_stock_transfer_details_status + " INTEGER , " +
                COLUMN_stock_transfer_details_product_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_stock_transfer + " ( " +
                COLUMN_stock_transfer_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_stock_transfer_product_quantity + " INTEGER , " +
                COLUMN_stock_transfer_product_total_delivered_qty + " INTEGER , " +
                COLUMN_stock_transfer_product_total_received_qty  + " INTEGER , " +
                COLUMN_stock_transfer_product_price + " REAL , " +
                COLUMN_stock_transfer_transfer_by + " TEXT , " +
                COLUMN_stock_transfer_status + " INTEGER , " +
                COLUMN_stock_transfer_product_transfer_to + " TEXT , " +
                COLUMN_stock_transfer_product_entry_by + " TEXT , " +
                COLUMN_stock_transfer_product_pos_by + " TEXT , " +
                COLUMN_stock_transfer_product_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_product_add_category + " ( " +
                COLUMN_product_add_category_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_product_add_category_name + " TEXT , " +
                COLUMN_product_add_category_category + " TEXT , " +
                COLUMN_product_add_category_product_class + " TEXT , " +
                COLUMN_product_add_category_entry_by + " TEXT , " +
                COLUMN_product_add_category_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_product_add_retail_image_group + " ( " +
                COLUMN_product_add_retail_image_group_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_product_add_retail_image_group_name + " TEXT , " +
                COLUMN_product_add_retail_image_group_height + " INTEGER , " +
                COLUMN_product_add_retail_image_group_width + " INTEGER , " +
                COLUMN_product_add_retail_image_group_entry_by + " TEXT , " +
                COLUMN_product_add_retail_image_group_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_retail_product_manufacture + " ( " +
                COLUMN_retail_product_manufacture_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_retail_product_manufacture_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_retail_product_brand + " ( " +
                COLUMN_retail_product_brand_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_retail_product_brand_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_retail_purchase_tax_group + " ( " +
                COLUMN_retail_purchase_tax_group_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_retail_purchase_tax_group_percentage + " REAL , " +
                COLUMN_retail_purchase_tax_group_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_retail_sales_tax_group + " ( " +
                COLUMN_retail_sales_tax_group_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_retail_sales_tax_group_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_market_place + " ( " +
                COLUMN_market_place_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_market_place_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_new_retail_product + " ( " +
                COLUMN_new_retail_product_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_new_retail_product_entry_id + " INTEGER , " +
                COLUMN_new_retail_product_category + " TEXT , " +
                COLUMN_new_retail_product_sub_category + " TEXT , " +
                COLUMN_new_retail_product_sku + " TEXT , " +
                COLUMN_new_retail_product_title + " TEXT , " +
                COLUMN_new_retail_product_description + " TEXT , " +
                COLUMN_new_retail_product_weight + " TEXT , " +
                COLUMN_new_retail_product_dimension + " TEXT , " +
                COLUMN_new_retail_product_accessories + " TEXT , " +
                COLUMN_new_retail_product_warrenty + " TEXT , " +
                COLUMN_new_retail_product_image_group + " TEXT , " +
                COLUMN_new_retail_product_images + " TEXT , " +
                COLUMN_new_retail_product_manufacturer + " TEXT , " +
                COLUMN_new_retail_product_brand + " TEXT , " +
                COLUMN_new_retail_product_market_place + " TEXT , " +
                COLUMN_new_retail_product_approximate_cost + " REAL , " +
                COLUMN_new_retail_product_whole_sale_price + " REAL , " +
                COLUMN_new_retail_product_retail_price + " REAL , " +
                COLUMN_new_retail_product_purchase_tax_group + " TEXT , " +
                COLUMN_new_retail_product_sales_tax_group + " TEXT , " +
                COLUMN_new_retail_product_entry_by + " TEXT , " +
                COLUMN_new_retail_product_entry_date_time + " TEXT , " +
                COLUMN_new_retail_product_status+ " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_new_retail_product_head + " ( " +
                COLUMN_new_retail_product_head_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_new_retail_product_head_sku + " TEXT , " +
                COLUMN_new_retail_product_head_quantity + " INTEGER , " +
                COLUMN_new_retail_product_head_sub_photoQuantity + " INTEGER , " +
                COLUMN_new_retail_product_head_entry_by + " TEXT , " +
                COLUMN_new_retail_product_head_entry_date + " TEXT , " +
                COLUMN_new_retail_product_head_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_supplier + " ( " +
                COLUMN_supplier_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_supplier_name + " TEXT , " +
                COLUMN_supplier_address + " TEXT , " +
                COLUMN_supplier_address2 + " TEXT , " +
                COLUMN_supplier_address3 + " TEXT , " +
                COLUMN_supplier_mobile + " TEXT , " +
                COLUMN_supplier_email + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_purchase_log + " ( " +
                COLUMN_purchase_log_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_purchase_log_entry_id + " INTEGER , " +
                COLUMN_purchase_log_supplier + " TEXT , " +
                COLUMN_purchase_log_sku + " TEXT , " +
                COLUMN_purchase_log_quantity + " INTEGER , " +
                COLUMN_purchase_log_unit_price + " REAL , " +
                COLUMN_purchase_log_amount + " REAL , " +
                COLUMN_purchase_log_entry_by + " TEXT , " +
                COLUMN_purchase_log_entry_date + " TEXT , " +
                COLUMN_purchase_if_data_sycned + " INTEGER , " +
                COLUMN_purchase_log_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_purchase_log_invoice + " ( " +
                COLUMN_purchase_log_invoice_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_purchase_log_invoice_suppler + " TEXT , " +
                COLUMN_purchase_log_invoice_sku_quantity + " INTEGER , " +
                COLUMN_purchase_log_invoice_total_quantity + " INTEGER , " +
                COLUMN_purchase_log_invoice_total_amount + " REAL , " +
                COLUMN_purchase_log_invoice_unit_total_tax + " REAL , " +
                COLUMN_purchase_log_invoice_total_discount + " REAL , " +
                COLUMN_purchase_log_invoice_net_payable + " REAL , " +
                COLUMN_purchase_log_invoice_amount_paid + " REAL , " +
                COLUMN_purchase_log_invoice_entry_by + " TEXT , " +
                COLUMN_purchase_log_invoice_entry_date + " TEXT , " +
                COLUMN_purchase_log_if_data_synced + " INTEGER , " +
                COLUMN_purchase_log_invoice_status + " INTEGER);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_supplier_cash_payment + " ( " +
                COLUMN_supplier_cash_payemnt_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_supplier_cash_payemnt_account_name + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_remark + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_ca_amount+ " REAL  , " +
                COLUMN_supplier_cash_payemnt_cash_amount + " REAL  , " +
                COLUMN_supplier_cash_payemnt_card_amount + " REAL  , " +
                COLUMN_supplier_cash_payemnt_card_type + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_bank_account + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_po_id + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_bank_name + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_cheque_no + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_customer_name + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_received_by + " TEXT  , " +
                COLUMN_supplier_cash_payemnt_if_synced + " INTEGER  , " +
                COLUMN_supplier_cash_payemnt_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_supplier_excss_cash + " ( " +
                COLUMN_supplier_excss_cash_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_supplier_excss_cash_customer + " TEXT, " +
                COLUMN_supplier_excss_cash_amount  + " REAL, " +
                COLUMN_supplier_excss_cash_receive_id +  " INTEGER, "+
                COLUMN_supplier_excss_cash_receive_amount+ " REAL);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }



        query = "CREATE TABLE " + TABLE_supplier_cash_receive_details + " ( " +
                COLUMN_supplier_cash_receive_details_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_supplier_cash_receive_details_received_id + " INTEGER  , " +
                COLUMN_supplier_cash_receive_details_invoice_id + " INTEGER  , " +
                COLUMN_supplier_cash_receive_details_amount + " REAL  , " +
                COLUMN_supplier_cash_receive_details_date_time + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_purchase_received_payment + " ( " +
                COLUMN_purchase_received_payment_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_purchase_received_payment_invoice_id + " INTEGER  , " +
                COLUMN_purchase_received_payment_amount + " REAL  , " +
                COLUMN_purchase_received_payment_mode+ " TEXT  , " +
                COLUMN_purchase_received_card_type + " TEXT  , " +
                COLUMN_purchase_received_cheque_no + " TEXT  , " +
                COLUMN_purchase_received_bank + " TEXT  , " +
                COLUMN_purchase_received_payment_received_by + " TEXT  , " +
                COLUMN_purchase_received_payment_received_date + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_co_exp_account + " ( " +
                COLUMN_co_exp_account_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_co_exp_account_type + " TEXT, " +
                COLUMN_co_exp_account_name + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_cash_receive_coa_exp_details + " ( " +
                COLUMN_cash_receive_coa_exp_details_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_cash_receive_coa_exp_details_received_id + " TEXT, " +
                COLUMN_cash_receive_coa_exp_details_account_name + " TEXT, " +
                COLUMN_cash_receive_coa_exp_details_remarks + " TEXT, " +
                COLUMN_cash_receive_coa_exp_details_amount + " REAL, " +
                COLUMN_cash_receive_coa_exp_details_po_id + " TEXT, " +
                COLUMN_cash_receive_coa_exp_details_if_data_synced + " INTEGER, " +
                COLUMN_cash_receive_coa_exp_details_uuid + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_free_Invoice + " ( " +
                COLUMN_free_Invoice_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_free_Invoice_invoice_id + " INTEGER, " +
                COLUMN_free_Invoice_outlet_id + " INTEGER, " +
                COLUMN_free_Invoice_route + " TEXT, " +
                COLUMN_free_Invoice_product_id + " INTEGER, " +
                COLUMN_free_Invoice_product_quantity + " INTEGER, " +
                COLUMN_free_Invoice_product_remark  + " TEXT, " +
                COLUMN_free_Invoice_product_details + " TEXT, " +
                COLUMN_free_Invoice_invoice_by + " TEXT, " +
                COLUMN_free_Invoice_invoice_date + " TEXT, " +
                COLUMN_free_Invoice_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_free_Invoice_head + " ( " +
                COLUMN_free_Invoice_head_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_free_Invoice_head_product_quantity + " INTEGER, " +
                COLUMN_free_Invoice_head_outlet + " TEXT, " +
                COLUMN_free_Invoice_head_photo + " TEXT, " +
                COLUMN_free_Invoice_head_invoice_by + " TEXT, " +
                COLUMN_free_Invoice_head_invoice_date + " TEXT, " +
                COLUMN_free_Invoice_head_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_crm_Invoice + " ( " +
                COLUMN_crm_Invoice_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_crm_Invoice_invoice_id + " INTEGER, " +
                COLUMN_crm_Invoice_product_id + " INTEGER, " +
                COLUMN_crm_Invoice_description + " TEXT, " +
                COLUMN_crm_Invoice_comment + " TEXT, " +
                COLUMN_crm_Invoice_invoice_by + " TEXT, " +
                COLUMN_crm_Invoice_invoice_date + " TEXT, " +
                COLUMN_crm_Invoice_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_crm_Invoice_head + " ( " +
                COLUMN_crm_Invoice_head_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_crm_Invoice_head_quantity + " INTEGER, " +
                COLUMN_crm_Invoice_head_outlet + " TEXT, " +
                COLUMN_crm_Invoice_head_photo + " TEXT, " +
                COLUMN_crm_Invoice_head_invoice_by + " TEXT, " +
                COLUMN_crm_Invoice_head_invoice_date + " TEXT, " +
                COLUMN_crm_Invoice_head_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_mi_Invoice + " ( " +
                COLUMN_mi_Invoice_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_mi_Invoice_invoice_id + " INTEGER, " +
                COLUMN_mi_Invoice_product_id + " INTEGER, " +
                COLUMN_mi_Invoice_description + " TEXT, " +
                COLUMN_mi_Invoice_comment + " TEXT, " +
                COLUMN_mi_Invoice_invoice_by + " TEXT, " +
                COLUMN_mi_Invoice_invoice_date + " TEXT, " +
                COLUMN_mi_Invoice_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_mi_Invoice_head + " ( " +
                COLUMN_mi_Invoice_head_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_mi_Invoice_head_quantity + " INTEGER, " +
                COLUMN_mi_Invoice_head_outlet + " TEXT, " +
                COLUMN_mi_Invoice_head_photo + " TEXT, " +
                COLUMN_mi_Invoice_head_invoice_by + " TEXT, " +
                COLUMN_mi_Invoice_head_invoice_date + " TEXT, " +
                COLUMN_mi_Invoice_head_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_posm_Invoice_head + " ( " +
                COLUMN_posm_Invoice_head_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_posm_Invoice_head_quantity + " INTEGER, " +
                COLUMN_posm_Invoice_head_outlet + " TEXT, " +
                COLUMN_posm_Invoice_head_photo + " TEXT, " +
                COLUMN_posm_Invoice_head_invoice_by + " TEXT, " +
                COLUMN_posm_Invoice_head_invoice_date + " TEXT, " +
                COLUMN_posm_Invoice_head_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_posm_Invoice + " ( " +
                COLUMN_posm_Invoice_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_posm_Invoice_invoice_id + " INTEGER, " +
                COLUMN_posm_Invoice_product_id + " INTEGER, " +
                COLUMN_posm_Invoice_product_quantity + " INTEGER, " +
                COLUMN_posm_Invoice_description + " TEXT, " +
                COLUMN_posm_Invoice_comment + " TEXT, " +
                COLUMN_posm_Invoice_invoice_by + " TEXT, " +
                COLUMN_posm_Invoice_invoice_date + " TEXT, " +
                COLUMN_posm_Invoice_status + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        query = "CREATE TABLE " + TABLE_cash_statment + " ( " +
                COLUMN_cash_statment_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_cash_statmen_entry_time + " TEXT, " +
                COLUMN_cash_statment_voucher + " TEXT, " +
                COLUMN_cash_statment_inv_coa + " TEXT, " +
                COLUMN_cash_statment_debit_amount + " REAL, " +
                COLUMN_cash_statment_credit_amount + " REAL, " +
                COLUMN_cash_statment_cash_cu_balance + " REAL);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_discount_list + " ( " +
                COLUMN_discount_list_name + " TEXT, " +
                COLUMN_discount_valid_from + " TEXT, " +
                COLUMN_discount_valid_to + " TEXT, " +
                COLUMN_discount_list_discount + " REAL);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }



        query = "CREATE TABLE " + TABLE_invoice_product+ " ( " +
                COLUMN_invoice_product_id + " INTEGER PRIMARY KEY, " +
                COLUMN_invoice_product_uuid + " TEXT , " +
                COLUMN_invoice_product_group + " TEXT , " +
                COLUMN_invoice_product_pgroup + " REAL , " +
                COLUMN_invoice_product_type + " TEXT , " +
                COLUMN_invoice_product_ptype + " TEXT , " +
                COLUMN_invoice_product_pclass + " TEXT , " +
                COLUMN_invoice_product_pcategory + " TEXT , " +
                COLUMN_invoice_product_psub_category + " TEXT , " +
                COLUMN_invoice_product_company + " TEXT , " +
                COLUMN_invoice_product_brand + " TEXT , " +
                COLUMN_invoice_product_name + " TEXT , " +
                COLUMN_invoice_product_names + " TEXT , " +
                COLUMN_invoice_product_sale_coa  + " TEXT , " +
                COLUMN_invoice_product_barcode + " REAL , " +
                COLUMN_invoice_product_wholesale  + " REAL , " +
                COLUMN_invoice_product_price  + " REAL , " +
                COLUMN_invoice_product_vat + " TEXT , " +
                COLUMN_invoice_product_discount_amount + " TEXT , " +
                COLUMN_invoice_product_discount_type + " TEXT , " +
                COLUMN_invoice_product_from + " TEXT , " +
                COLUMN_invoice_product_to + " TEXT , " +
                COLUMN_invoice_product_from_hr + " TEXT , " +
                COLUMN_invoice_product_to_hr + " TEXT);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


        query = "CREATE TABLE " + TABLE_payment_rece_vou+ " ( " +
                COLUMN_payment_rece_vou_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_payment_rece_vou_sl_id + " INTEGER , " +
                COLUMN_payment_rece_vou_inv_id + " TEXT , " +
                COLUMN_payment_rece_vou_category + " TEXT , " +
                COLUMN_payment_rece_vou_customer + " REAL , " +
                COLUMN_payment_rece_vou_payment_mode + " TEXT , " +
                COLUMN_payment_rece_vou_print_by + " TEXT , " +
                COLUMN_payment_rece_vou_print_date + " TEXT , " +
                COLUMN_payment_rece_vou_card_info + " TEXT , " +
                COLUMN_payment_rece_vou_card_no + " TEXT , " +
                COLUMN_payment_rece_vou_remark + " TEXT , " +
                COLUMN_payment_rece_vou_amount + " REAL);";
        try {
            db.execSQL(query);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    //------generic Methods


    public Cursor getData(String sel,String table,String where,String groupby)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="";
        if(groupby.length()>0)
        {
            query = "SELECT "+sel+" FROM " + table + " WHERE " + where+" GROUP BY "+groupby;
        }
        else
        {
            query = "SELECT "+sel+" FROM " + table + " WHERE " + where;
        }
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        return c;
    }

    public void insertData(ContentValues insertValues, String table_name,String con) {

        int size = getDataCount(table_name, con);
        SQLiteDatabase db = getWritableDatabase();
        if(size>0)
        {
            try
            {
                db.update(table_name, insertValues, con, null);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        else
        {
            try
            {
                db.insert(table_name, null, insertValues);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        //db.close();
    }

    public void updateData(ContentValues insertValues, String table_name,String con) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.update(table_name, insertValues, con, null);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
    }
    public void updateData(String data, String con, String TableName) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String qu = "UPDATE " + TableName + " SET " + data + " WHERE " + con + ";";
            db.execSQL(qu);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
       // db.close();
    }

    public void deleteData( String TableName , String con) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String qu = "DELETE FROM " + TableName + " WHERE " + con + ";";
            db.execSQL(qu);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
    }
    public int getDataCount(String TABLE_NAME, String con) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + con;
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
        int count = c.getCount();
        c.close();
        return count;
    }

    public int sumData(String data, String TABLE_NAME, String con) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM("+data+") FROM " + TABLE_NAME + " WHERE " + con;
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    return c.getInt(0);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
        return 0;
    }

    public double sumDataDouble(String data, String TABLE_NAME, String con) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM("+data+") FROM " + TABLE_NAME + " WHERE " + con;
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    return c.getDouble(0);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        //db.close();
        return 0;
    }

    public int getMax(String TABLE_NAME,String col, String con) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX("+col+") FROM " + TABLE_NAME + " WHERE " + con;
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                return c.getInt(0);
            } while (c.moveToNext());
        }
        return 0;
    }

    public void runSQL(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            db.execSQL(query, null);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }
}
