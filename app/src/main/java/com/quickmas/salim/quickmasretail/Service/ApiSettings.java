package com.quickmas.salim.quickmasretail.Service;

import android.net.Uri;

import com.quickmas.salim.quickmasretail.Model.System.User;

/**
 * Created by Forhad on 17/03/2018.
 */

public class ApiSettings {
    //------------------URL
    public static String url_login = "https://quickmas.com/api-pos/login-api.php?";
    public static String url_text_download = "https://quickmas.com/api-pos/text-download.php?";
    public static String url_menu_download = "https://quickmas.com/api-pos/image-download.php?";
    public static String url_sub_menu= "https://quickmas.com/api-pos/sub-menu-download.php?";
    public static String url_set_language= "https://quickmas.com/api-pos/change-language.php?";
    public static String url_stock_assignment = "https://quickmas.com/api-pos/stock-assignment.php?";
    public static String url_outlook = "https://quickmas.com/api-pos/outlet-download.php?";
    public static String url_dataSync_download = "https://quickmas.com/api-pos/datasync-download.php?";
    public static String url_bank_list_download = "https://quickmas.com/api-pos/bank-download.php?";
    public static String url_card_list_download = "https://quickmas.com/api-pos/card-download.php?";
    public static String url_warehouse_list_download = "https://quickmas.com/api-pos/warehouse-download.php?";
    public static String url_product_add_category = "https://quickmas.com/api-pos/product-category-download.php?";

    public static String url_company_logo = "https://quickmas.com/upload/logo/";
    public static String url_product_logo = "https://quickmas.com/upload/gallery/";
    public static String url_pos_sales_person = "https://quickmas.com/api-pos/sales-person-download.php?";
    public static String url_pos_customer = "https://quickmas.com/api-pos/pos-customer-download.php?";
    public static String url_pos_invoice_verification_user = "https://quickmas.com/api-pos/user-list-download.php?";
    public static String url_rtm_invoice_upload = "https://quickmas.com/api-pos/invoice-upload.php?";
    public static String url_rtm_invoice_final_upload = "https://quickmas.com/api-pos/final_upload.php?";
    public static String url_pos_invoice_upload = "https://quickmas.com/api-pos/pos-invoice-upload.php?";
    public static String url_pos_invoice_details_upload = "https://quickmas.com/api-pos/pos-invoice-details-upload.php?";
    public static String url_pos_invoice_co_ledger_upload = "https://quickmas.com/api-pos/pos-invoice-co-ledger-upload.php?";
    public static String url_pos_customer_upload = "https://quickmas.com/api-pos/pos-invoice-details-upload.php?";
    public static String url_pos_invoice_download = "https://quickmas.com/api-pos/pos-invoice-download.php?";
    public static String url_pos_invoice_details_download = "https://quickmas.com/api-pos/pos-invoice-details-download.php?";
    public static String url_co_account= "https://quickmas.com/api-pos/co-account-download.php?";
    public static String url_cash_account= "https://quickmas.com/api-pos/cash-account-download.php?";
    public static String url_retail_brand_download = "https://quickmas.com/api-pos/retail-brand-download.php?";
    public static String url_retail_manufacture_download = "https://quickmas.com/api-pos/retail-manufacture-download.php?";
    public static String url_retail_purchase_tax_group_download = "https://quickmas.com/api-pos/retail-purchase-tax-group-download.php?";
    public static String url_retail_sales_tax_group_download = "https://quickmas.com/api-pos/retail-sales-tax-group-download.php?";
    public static String url_retail_image_group_download = "https://quickmas.com/api-pos/retail-image-group-download.php?";
    public static String url_retail_market_place_download = "https://quickmas.com/api-pos/marketplace-download.php?";
    public static String url_retail_supplier_download = "https://quickmas.com/api-pos/supplier-download.php?";
    public static String url_co_exp_account = "https://quickmas.com/api-pos/co-exp-account-download.php?";
    public static String url_assignment_list = "https://quickmas.com/api-pos/assignment-list.php?";
    public static String url_retail_product_download = "https://quickmas.com/api-pos/retail-product-download.php?";
    public static String url_retail_product_upload = "https://quickmas.com/api-pos/retail-product-upload.php?";
    public static String url_retail_product_download_image = "https://quickmas.com/upload/gallery/";
    public static String url_purchase_product_invoice = "https://quickmas.com/api-pos/bills-goods-download.php?";
    public static String url_purchase_product_invoice_details = "https://quickmas.com/api-pos/bills-goods-download-details.php?";
    public static String url_payment_download = "https://quickmas.com/api-pos/payment-download.php?";
    public static String url_payment_download_details = "https://quickmas.com/api-pos/payment-download-details.php?";
    public static String url_receive_download = "https://quickmas.com/api-pos/receive-download.php?";
    public static String url_receive_download_details = "https://quickmas.com/api-pos/receive-download-details.php?";
    public static String url_cash_transfer_download = "https://quickmas.com/api-pos/cash-transfer-download.php?";
    public static String url_goods_transfer_download = "https://quickmas.com/api-pos/goods-transfer-download.php?";
    public static String url_purchase_log_invoice_upload = "https://quickmas.com/api-pos/pos-po-upload.php?";
    public static String url_purchase_log_upload_details = "https://quickmas.com/api-pos/pos-po-details-upload.php?";
    public static String url_payment_upload = "https://quickmas.com/api-pos/payment-upload.php?";
    public static String url_payment_details_upload = "https://quickmas.com/api-pos/payment-upload-details.php?";
    public static String url_cash_receive_upload = "https://quickmas.com/api-pos/receive-upload.php?";
    public static String url_cash_receive_details_upload = "https://quickmas.com/api-pos/receive-upload-details.php?";
    public static String url_cash_transfer_upload = "https://quickmas.com/api-pos/cash-transfer-upload.php?";
    public static String url_cash_transfer_delete = "https://quickmas.com/api-pos/cash-transfer-delete.php?";
    public static String url_cash_transfer_receive = "https://quickmas.com/api-pos/cash-transfer-receive.php?";
    public static String url_stock_transfer_upload = "https://quickmas.com/api-pos/stock-transfer-upload.php?";
    public static String url_cash_cu_balance = "https://quickmas.com/api-pos/cash-balance-download.php?";
    public static String url_cash_statment_download = "https://quickmas.com/api-pos/cash-statment.php?";
    public static String url_active_pos_check = "https://quickmas.com/api-pos/pos-permission.php?";
    public static String url_active_pos_check_update = "https://quickmas.com/api-pos/pos-log-update.php?";
    public static String url_discount_list = "https://quickmas.com/api-pos/discount-download.php?";
    public static String url_invoice_product_download = "https://quickmas.com/api-pos/invoice-product-download.php?";

    ////--------------------Column Head text_download
    public static String url_text_download_id = "id";
    public static String url_text_download_varname = "var_name";
    public static String url_text_download_text = "bn";

    ////--------------------Column Head menu_download
    public static String url_menu_download_id = "id";
    public static String url_menu_download_varname = "var_name";
    public static String url_menu_download_image = "image";
    public static String url_menu_download_text = "bn";
    public static String url_menu_download_category = "category";

    ////--------------------Column Head menu_download (Product)
    public static String url_stock_assignment_id ="id";
    public static String url_stock_assignment_assign_no ="assign_no";
    public static String url_stock_assignment_order_sr ="order_sr";
    public static String url_stock_assignment_payment_sr ="payment_sr";
    public static String url_stock_assignment_delivery_sr ="delivery_sr";
    public static String url_stock_assignment_point ="point";
    public static String url_stock_assignment_route ="route";
    public static String url_stock_assignment_section="section";
    public static String url_stock_assignment_frequency ="frequency";
    public static String url_stock_assignment_company ="company";
    public static String url_stock_assignment_product_type ="product_type";
    public static String url_stock_assignment_product_category ="product_category";
    public static String url_stock_assignment_photo ="photo";
    public static String url_stock_assignment_brand ="brand";
    public static String url_stock_assignment_sku ="sku";
    public static String url_stock_assignment_sub_sku ="sub_sku";
    public static String url_stock_assignment_sku_qty ="sku_qty";
    public static String url_stock_assignment_sku_price ="sku_price";
    public static String url_stock_assignment_target ="target";
    public static String url_stock_assignment_date_from ="date_from";
    public static String url_stock_assignment_date_to ="date_to";
    public static String url_stock_assignment_order_sku ="order_sku";
    public static String url_stock_assignment_sold_sku ="sold_sku";
    public static String url_stock_assignment_return_sku ="return_sku";
    public static String url_stock_assignment_target_status ="target_status";
    public static String url_stock_assignment_accepted_time ="accepted_time";
    public static String url_stock_assignment_gift_for ="gift_for";
    public static String url_stock_assignment_downloadable_status ="downloadable_status";


    ////--------------------Column Head menu_download (Product)
    public static String url_putlook_id ="id";
    public static String url_outlook_outlet_id ="name";
    public static String url_outlook_outletname = "full_name";
    public static String url_outlook_owner = "representative1";
    public static String url_outlook_routr ="route";
    public static String url_outlook_sction ="section";
    public static String url_outlook_cluster = "claster";
    public static String url_outlook_address ="address";
    public static String url_outlook_phone ="phone";
    public static String url_outlook_outlet_type ="outlet_type";
    public static String url_outlook_sales_for ="sales_for";
    public static String url_outlook_outlet_for ="outlet_for";

    ////--------------------Column Head menu_download (Product)
    public static String url_dataSync_download_id ="id";
    public static String url_dataSync_download_product_name ="product_name";
    public static String url_dataSync_download_product_title ="names";
    public static String url_dataSync_download_product_color ="color";
    public static String url_dataSync_download_type ="pclass";
    public static String url_dataSync_download_sub_type ="pcategory";
    public static String url_dataSync_download_features ="features";
    public static String url_dataSync_download_weight ="weight";
    public static String url_dataSync_download_dimensions ="dimensions";
    public static String url_dataSync_download_includes ="includes";
    public static String url_dataSync_download_guarantee ="guarantee";
    public static String url_dataSync_download_location ="location";
    public static String url_dataSync_download_sub_location ="sub_location";
    public static String url_dataSync_download_location_balance= "location_balance";
    public static String url_dataSync_download_sub_location_balance= "sub_location_balance";
    public static String url_dataSync_download_tax= "rate_sales";
    public static String url_dataSync_download_discount= "discount_amount";
    public static String url_dataSync_download_discount_type= "discount_type";
    public static String url_dataSync_download_date_from = "from";
    public static String url_dataSync_download_date_to = "to";
    public static String url_dataSync_download_hour_from= "from_hr";
    public static String url_dataSync_download_hour_to= "to_hr";
    public static String url_dataSync_download_price ="price";
    public static String url_dataSync_download_wholesale ="wholesale";
    public static String url_dataSync_download_photo ="photo";
    public static String url_dataSync_download_brand ="brand";
    public static String url_dataSync_quantity_left ="sub_location_balance";
    public static String url_dataSync_status ="status";

    ////--------------------Column Head pos sales person
    public static String url_pos_sales_person_id ="id";
    public static String url_pos_sales_person_name ="name";
    public static String url_pos_sales_person_full_name = "f_name";
    public static String url_pos_sales_person_is_selected = "is_selected";

    ////--------------------Column Head pos customer
    public static String url_pos_customer_id ="id";
    public static String url_pos_customer_name ="name";
    public static String url_pos_customer_full_name = "full_name";
    public static String url_pos_customer_phone = "mobile";
    public static String url_pos_customer_balance = "deb_cu_balance";

    ////--------------------Column Head Bank
    public static String url_bank_id ="id";
    public static String url_bank_name ="cash_bank";

    ////--------------------Column Head Card
    public static String url_card_id ="id";
    public static String url_card_name ="name";

    ////--------------------Column Pos Verification user
    public static String url_user_name ="user_name";
    public static String url_card_password ="password";


    ////------------------Column Pos Invoice Head
    public static String url_pos_invoice_head_invoice_id="po";
    public static String url_pos_invoice_head_Total_amount_full ="sub_total";
    public static String url_pos_invoice_head_Total_amount ="total";
    public static String url_pos_invoice_head_Total_quantity = "total_qty";
    public static String url_pos_invoice_head_Total_tax = "tax_amount";
    public static String url_pos_invoice_head_Iteam_discount = "discount_amount";
    public static String url_pos_invoice_head_Additional_discount = "additional_discount_amount";
    public static String url_pos_invoice_head_Delivery_expense = "additional_charge_amount";
    public static String url_pos_invoice_head_Sales_person = "sales_person";
    public static String url_pos_invoice_head_Customer = "customer";
    public static String url_pos_invoice_head_Cash_amount = "cash_amount";
    public static String url_pos_invoice_head_Note_given = "note_given";
    public static String url_pos_invoice_Card_amount = "card_amount";
    public static String url_pos_invoice_Cheque_no = "cheque_no";
    public static String url_pos_invoice_Bank = "card";
    public static String url_pos_invoice_Pay_later_amount = "credit_amount";
    public static String url_pos_invoice_Verify_user = "approved_by";
    public static String url_pos_invoice_Invoice_generate_by = "entry_by";
    public static String url_pos_invoice_Invoice_date = "entry_time";
    public static String url_pos_invoice_Card_type = "card_type";
    public static String url_pos_invoice_Sale_type = "invoice_type";
    public static String url_pos_invoice_total_paid_amount = "paid";
    ////------------------Column Pos Invoice deatails

    public static String url_pos_invoice_details_invoice_id="inv_no";
    public static String url_pos_invoice_details_product_name="product";
    public static String url_pos_invoice_details_unit_price="unit_price";
    public static String url_pos_invoice_details_quantity="quantity";
    public static String url_pos_invoice_details_tax="vat";
    public static String url_pos_invoice_details_discount="discount";


    //--------------------column co Account
    public static String url_co_account_name="sub_coa";
    public static String url_co_account_type="chart_of_accounts";

    //--------------------column cash Account
    public static String url_cash_account_id="id";
    public static String url_cash_account_name="cash_bank";


    //------------column sub menu
    public static String url_sub_menu_id="id";
    public static String url_sub_menu_varname="var_name";
    public static String url_sub_menu_text="bn";
    public static String url_sub_menu_new_var="reference";

    //------------column warehouse list
    public static String url_warehouse_list_download_id="id";
    public static String url_warehouse_list_download_name="name";


    //------------column warehouse list
    public static String url_product_add_category_name="name";
    public static String url_product_add_category_category="category";
    public static String url_product_add_category_product_class="product_class";

    //-----------column retail brand
    public static String url_retail_brand_download_name="name";

    //-----------column Manufacture
    public static String url_retail_manufacture_download_name="name";

    //-----------column purchase_tax_group
    public static String url_retail_purchase_tax_group_download_name="name";
    public static String url_retail_purchase_tax_group_download_percentage="rate_purchase";

    //-----------column sales_tax_group
    public static String url_retail_sales_tax_group_download_name="name";


//-----------column image group
    public static String url_retail_image_group_download_name="name";
    public static String url_retail_image_group_download_height="min_height";
    public static String url_retail_image_group_download_width="min_width";

    //-----------column sales_tax_group
    public static String url_retail_market_place_download_name="name";

    //-----------column image group
    public static String url_retail_supplier_download_name="name";
    public static String url_retail_supplier_download_address1="address";
    public static String url_retail_supplier_download_address2="address2";
    public static String url_retail_supplier_download_address3="address3";
    public static String url_retail_supplier_download_mobile="mobile";
    public static String url_retail_supplier_download_email="email";


    //--------------------column co Account
    public static String url_co_exp_account_name="sub_coa";
    public static String url_co_exp_account_type="chart_of_accounts";

    //--------------------column url assignment
    public static String url_assignment_list_assign_no = "assign_no";
    public static String url_assignment_list_date_from = "date_from";
    public static String url_assignment_list_date_to = "date_to";
    public static String url_assignment_list_quantity = "quantity";
    public static String url_assignment_list_status = "status";


    public static String url_retail_product_category = "pclass";
    public static String url_retail_product_sub_category = "pcategory";
    public static String url_retail_product_title = "names";
    public static String url_retail_product_sku = "name";
    public static String url_retail_product_description = "features";
    public static String url_retail_product_weight = "weight";
    public static String url_retail_product_dimensions = "dimensions";
    public static String url_retail_product_accessories = "includes";
    public static String url_retail_product_warrenty = "guarantee"            ;
    public static String url_retail_product_images = "images";
    public static String url_retail_product_manufacturer = "company";
    public static String url_retail_product_brand = "brand";
    public static String url_retail_product_marketplace = "marketplace";
    public static String url_retail_product_approximate_cost = "cost_price";
    public static String url_retail_product_whole_sale_price = "wholesale";
    public static String url_retail_product_retail_price = "price";
    public static String url_retail_product_purchase_tax_group = "p_vat";
    public static String url_retail_product_sales_tax_group = "vat";
    public static String url_retail_product_entry_by = "entry_by";
    public static String url_retail_product_entry_time = "entry_time";

    //----purchase download
    public static String url_purchase_product_invoice_po = "po";
    public static String url_purchase_product_invoice_supplier = "vendor";
    public static String url_purchase_product_invoice_total_quantity = "total_qty";
    public static String url_purchase_product_invoice_total_amount = "sub_total";
    public static String url_purchase_product_invoice_total_tax = "tax_amount";
    public static String url_purchase_product_invoice_total_discount = "discount_amount";
    public static String url_purchase_product_invoice_net_payable = "total";
    public static String url_purchase_product_invoice_amount_paid = "paid";
    public static String url_purchase_product_invoice_entry_by = "entry_by";
    public static String url_purchase_product_invoice_entry_date = "entry_time";
    public static String url_purchase_product_invoice_status = "status";

    //----purchase download
    public static String url_purchase_product_invoice_details_entry_id = "entry_id";
    public static String url_purchase_product_invoice_details_supplier = "vendor";
    public static String url_purchase_product_invoice_details_sku = "product";
    public static String url_purchase_product_invoice_details_quantity = "quantity";
    public static String url_purchase_product_invoice_details_unit_price = "unit_price";
    public static String url_purchase_product_invoice_details_amount = "amount";
    public static String url_purchase_product_invoice_details_entry_by = "entry_by";
    public static String url_purchase_product_invoice_details_entry_date = "entry_time";


    //-----payment download
    public static String url_payment_download_amount = "total";
    public static String url_payment_download_payment_type = "payment_type";
    public static String url_payment_download_type = "type";
    public static String url_payment_download_supplier = "vendor";
    public static String url_payment_download_entry_by = "entry_by";
    public static String url_payment_download_entry_time = "entry_time";
    public static String url_payment_download_bank = "payment_mode";
    public static String url_payment_cheque = "cheque";
    public static String url_payment_uuid = "uuid";


    //----payment download details
    public static String url_payment_download_details_acc_name = "sub_chart_of_accounts";
    public static String url_payment_download_details_amount = "amount";
    public static String url_payment_download_details_remarks = "description";
    public static String url_payment_download_details_po_uuid = "po_uuid";
    public static String url_payment_download_details_uuid = "uuid";


    //-----Receive download
    public static String url_receive_download_amount = "total";
    public static String url_receive_download_payment_type = "payment_type";
    public static String url_receive_download_type = "payment_mode";
    public static String url_receive_download_supplier = "customer";
    public static String url_receive_download_entry_by = "entry_by";
    public static String url_receive_download_entry_time = "entry_time";
    public static String url_receive_download_bank = "payment_mode";
    public static String url_receive_cheque = "cheque";
    public static String url_receive_uuid = "uuid";


    //-----Cash transfer download
    public static String url_cash_transfer_download_account = "to_cashbank";
    public static String url_cash_transfer_download_receive_from = "from_cashbank";
    public static String url_cash_transfer_download_remarks = "description";
    public static String url_cash_transfer_download_amount = "amount";
    public static String url_cash_transfer_download_receive_type = "contra_type";
    public static String url_cash_transfer_download_receive_by = "entry_by";
    public static String url_cash_transfer_download_receive_date = "entry_time";
    public static String url_cash_transfer_download_approve_by = "approved_by";



    public static String getUserDetailsPar(User cSystemInfo)
    {
        String username = Uri.encode(cSystemInfo.getUser_name());
        String password = Uri.encode(cSystemInfo.getPassword());
        String company = Uri.encode(cSystemInfo.getCompany_id());
        String location = Uri.encode(cSystemInfo.getSelected_location());
        String pos = Uri.encode(cSystemInfo.getSelected_pos());
        return  "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
    }

}
