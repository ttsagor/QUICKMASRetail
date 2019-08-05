package com.quickmas.salim.quickmasretail.Module.Loading;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Model.POS.CashAccount;
import com.quickmas.salim.quickmasretail.Model.POS.CashAccountTransfer;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.CashPaymentReceivedDetails;
import com.quickmas.salim.quickmasretail.Model.POS.CashStatment;
import com.quickmas.salim.quickmasretail.Model.POS.CoAccount;
import com.quickmas.salim.quickmasretail.Model.POS.CoAccountReceiveDetails;
import com.quickmas.salim.quickmasretail.Model.POS.CoExpAccount;
import com.quickmas.salim.quickmasretail.Model.POS.CoExpAccountReceiveDetails;
import com.quickmas.salim.quickmasretail.Model.POS.Discount;
import com.quickmas.salim.quickmasretail.Model.POS.InvoiceProduct;
import com.quickmas.salim.quickmasretail.Model.POS.MarketPlace;
import com.quickmas.salim.quickmasretail.Model.POS.NewRetailProduct;
import com.quickmas.salim.quickmasretail.Model.POS.NewRetailProductEntryHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceHeadWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceRegularWeb;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoiceWeb;
import com.quickmas.salim.quickmasretail.Model.POS.ProductAddCategory;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLog;
import com.quickmas.salim.quickmasretail.Model.POS.PurchaseLogInvoice;
import com.quickmas.salim.quickmasretail.Model.POS.RetailImageGroup;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductBrand;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductManufacture;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductSalesTaxGroup;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductTaxGroup;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;
import com.quickmas.salim.quickmasretail.Model.POS.Supplier;
import com.quickmas.salim.quickmasretail.Model.POS.SupplierCashPaymentReceive;
import com.quickmas.salim.quickmasretail.Model.POS.Warehouse;
import com.quickmas.salim.quickmasretail.Model.RTM.RtmAssignmentList;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.AddProduct.AddProduct;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.ProductList.ProductList;
import com.quickmas.salim.quickmasretail.Module.Dashboard.FooterActivity.Settings.Settings;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Module.DataSync.AssignmentList.AssignmentList;
import com.quickmas.salim.quickmasretail.Module.DataSync.DataDownload.PendingList;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.Bank;
import com.quickmas.salim.quickmasretail.Model.POS.Card;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.POS.PosCustomer;
import com.quickmas.salim.quickmasretail.Model.POS.PosInvoicePayLaterUser;
import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.POS.SalesPerson;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.ApiSettings;
import com.quickmas.salim.quickmasretail.Service.BaseDataService;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.CashPaymentReceiveAPI;
import com.quickmas.salim.quickmasretail.Structure.GoodsTransferAPI;
import com.quickmas.salim.quickmasretail.Structure.PaymentPaidAPI;
import com.quickmas.salim.quickmasretail.Structure.PhotoPathTarDir;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceCombian;
import com.quickmas.salim.quickmasretail.Structure.PosInvoiceHeadCombian;
import com.quickmas.salim.quickmasretail.Structure.StockTransferAPI;
import com.quickmas.salim.quickmasretail.Utility.CallBack;
import com.quickmas.salim.quickmasretail.Service.DataDownload;
import com.quickmas.salim.quickmasretail.Service.DataloadImage;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Module.DataSync.DataDownload.PendingList.dataDownloadableStatus;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.*;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_co_exp_account_type;
import static com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation.getCurrentDateTime;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setGlobalProgessBarColor;

public class Loading extends AppCompatActivity {
    DBInitialization db;
    public static  DBInitialization db1;
    public static User cSystemInfo = new User();
    String firstLogin="false";
    String dataDownloadPendingList="false";
    String dataDownloadAssignmentList="false";
    String pos_data_sync="false";
    String assign_no="";
    public static TextView wait_text;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_loading);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        context = this;

        db = new DBInitialization(this,null,null,1);
        db1 = db;
        cSystemInfo = cSystemInfo.select(db,"1=1");
        DebugHelper.print(cSystemInfo);
        setGlobalProgessBarColor(context, (ProgressBar) findViewById(R.id.progressBar1), (LinearLayout) findViewById(R.id.background_loading),cSystemInfo.getLoading_color(),cSystemInfo.getLoading_background());
        String txt = TextString.textSelectByVarnameDb(db,"pos_lauding_body_messages").getText();
        txt = txt.replace("\\n",System.getProperty("line.separator"));
        wait_text = FontSettings.setTextFont((TextView) findViewById(R.id.wait_text),this,txt);


        if(b!=null)
        {
            try{
                firstLogin =(String) b.get("firstLogin");
            }catch (Exception e){}
            try{
                dataDownloadPendingList=(String) b.get("dataDownloadPendingList");
            }catch (Exception e){}
            try{
                dataDownloadAssignmentList=(String) b.get("dataDownloadAssignmentList");
            }catch (Exception e){}
            try{
                assign_no=(String) b.get("assign_no");
            }catch (Exception e){}
            try{
                pos_data_sync=(String) b.get("pos_data_sync");
            }catch (Exception e){}
        }

        if(firstLogin!=null && firstLogin.equalsIgnoreCase("true"))
        {
            try
            {
                CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertText");
                DataDownload.downloadData(context,url_text_download,"",cSystemInfo,c);
            }catch (Exception e){
                System.out.println("LOG: "+e.toString());
            }

        }

        if(dataDownloadPendingList!=null &&  dataDownloadPendingList.equalsIgnoreCase("true"))
        {
            try
            {
                CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertProduct");
                DataDownload.downloadData(context,url_stock_assignment,"assign_no="+Uri.encode(assign_no),cSystemInfo,c);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }

        if(pos_data_sync!=null &&  pos_data_sync.equalsIgnoreCase("true"))
        {
            try
            {
                pos_product_upload("",context);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }

        if(dataDownloadAssignmentList!=null &&  dataDownloadAssignmentList.equalsIgnoreCase("true"))
        {
            try
            {
                CallBack c = new CallBack("Module.Loading.Loading", "jsonAssigmentList");
                DataDownload.downloadData(context,url_assignment_list,"",cSystemInfo,c);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public void jsonAssigmentList(final String response,final Context context) {
        new Thread(new Runnable()
        {
            public void run() {
                ArrayList<RtmAssignmentList> products = new ArrayList<RtmAssignmentList>();
                try {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            RtmAssignmentList rtmAssignmentList = new RtmAssignmentList();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            rtmAssignmentList.setAsignNo(jb.get(url_assignment_list_assign_no).toString());
                            rtmAssignmentList.setDateFrom(jb.get(url_assignment_list_date_from).toString());
                            rtmAssignmentList.setDateTo(jb.get(url_assignment_list_date_to).toString());
                            rtmAssignmentList.setQuantity(jb.get(url_assignment_list_quantity).toString());
                            rtmAssignmentList.setStatus(jb.get(url_assignment_list_status).toString());
                            products.add(rtmAssignmentList);
                        }catch (Exception e){}
                    }
                    AssignmentList.pendingRtmAssignmentLists = products;

                }catch (Exception e){
                    Intent intent = new Intent(context, AssignmentList.class);
                    context.startActivity(intent);
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(context, AssignmentList.class);
                        context.startActivity(intent);
                    }
                });
            }
        }).start();
    }

    public void pos_product_upload(final String response,final Context context)
    {
        final UploadData uploadData = new UploadData();
        new Thread(new Runnable()
        {
            public void run() {
                try {
                    uploadData.data = PosInvoiceHeadCombian.getPosInvoiceCombian(PosInvoiceHead.select(db1,"1=1"),PosInvoiceHeadWeb.select(db1,"1=1"));
                }catch (Exception e){
                    Toasty.error(context, "Something went wrong.Please try agian", Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(context, AssignmentList.class);
                    context.startActivity(intent);
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        String username = cSystemInfo.getUser_name();
                        String password = cSystemInfo.getPassword();
                        String company = cSystemInfo.getCompany_id();
                        String location = cSystemInfo.getSelected_location();
                        String pos = cSystemInfo.getSelected_pos();

                        uploadData.url=url_pos_invoice_upload + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
                        DebugHelper.print(uploadData.data);
                        uploadData.uploaddata(context,context,"",context,"pos_product_details_upload");

                    }
                });
            }
        }).start();
    }

    public void pos_product_details_upload(ArrayList<String> data)
    {
        ArrayList<PosInvoiceHead> posInvoices = PosInvoiceHead.select(db1,"1=1");
        for(PosInvoiceHead p : posInvoices)
        {
            p.setIf_synced(1);
            p.update(db);
        }
        UploadData uploadData = new UploadData();
        uploadData.data = PosInvoiceCombian.setPosInvoiceCombian(PosInvoice.select(db,db.COLUMN_pos_invoice_status+"=2"));
        uploadData.map.put("saleType","");
        uploadData.map.put("payLaterAmount","");
        uploadData.map.put("cardPayment","");
        uploadData.map.put("cashPayment","");
        uploadData.map.put("cardNumber","");
        uploadData.map.put("CustomerName","");
        uploadData.map.put("salesPerson","");
        uploadData.map.put("additionalExpenses","");
        uploadData.map.put("id","");
        uploadData.map.put("taxIncluded","");
        uploadData.map.put("posPrefix","");

        uploadData.url=url_pos_invoice_details_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"pos_product_co_ledger_upload");

    }

    public void pos_product_co_ledger_upload(ArrayList<String> data)
    {
        ArrayList<PosInvoice> posInvoices = PosInvoice.select(db,db.COLUMN_pos_invoice_status+"=2");
        for(PosInvoice p : posInvoices)
        {
            p.setIf_synced(1);
            p.update(db);
        }
        UploadData uploadData = new UploadData();
        uploadData.data = PosInvoiceHeadCombian.setPosInvoiceHead(PosInvoiceHead.select(db,"1=1"));
        String username = cSystemInfo.getUser_name();
        String password = cSystemInfo.getPassword();
        String company = cSystemInfo.getCompany_id();
        String location = cSystemInfo.getSelected_location();
        String pos = cSystemInfo.getSelected_pos();

        uploadData.url=url_pos_invoice_co_ledger_upload + "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
        uploadData.uploaddata(this,this,"",this,"purchase_log_invoice_log");

    }

    public void purchase_log_invoice_log(ArrayList<String> data)
    {
        UploadData uploadData = new UploadData();
        uploadData.data = PurchaseLogInvoice.select(db,"1=1");
        uploadData.url=url_purchase_log_invoice_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"purchase_log_details");

    }

    public void purchase_log_details(ArrayList<String> data)
    {
        ArrayList<PurchaseLogInvoice> pli = PurchaseLogInvoice.select(db,"1=1");
        Collections.reverse(data);
        int count=pli.size()-1;
        for(String rs : data)
        {
            try{
                JSONArray jr = new JSONArray(data.get(0));
                if(rs.toString().charAt(0)=='[')
                {
                    jr = new JSONArray(rs.toString());
                }
                for (int i = 0; i < jr.length(); i++) {
                    try{
                        JSONObject jb = (JSONObject) jr.getJSONObject(i);
                        jb.get("po").toString();
                        try {
                            String new_po_id = jb.get("po").toString();
                            String new_id = new_po_id.split("-")[new_po_id.split("-").length - 1];
                            PurchaseLog.update(db, db.COLUMN_purchase_log_entry_id + "=" + new_id, db.COLUMN_purchase_log_entry_id + "=" + pli.get(count).id);
                            PurchaseLogInvoice.update(db, db.COLUMN_purchase_log_invoice_id + "=" + new_id, db.COLUMN_purchase_log_invoice_id + "=" + pli.get(count).id);
                        }catch (Exception e){}

                    }catch (Exception e){}
                }

            }catch (Exception e){}
            count--;
        }
        UploadData uploadData = new UploadData();
        uploadData.data = PurchaseLog.select(db,"1=1");
        uploadData.url=url_purchase_log_upload_details + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"supplier_payment_receive");

    }

    public void supplier_payment_receive(ArrayList<String> data)
    {
        UploadData uploadData = new UploadData();
        uploadData.data = SupplierCashPaymentReceive.select(db,"1=1");
        uploadData.url=url_payment_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"supplier_payment_receive_details");

    }
    public void supplier_payment_receive_details(ArrayList<String> data)
    {
        ArrayList<CoExpAccountReceiveDetails> c = CoExpAccountReceiveDetails.select(db,"1=1");

        ArrayList<PaymentPaidAPI> p = new ArrayList<>();

        for(CoExpAccountReceiveDetails cc : c)
        {
            SupplierCashPaymentReceive cs = SupplierCashPaymentReceive.select(db1,db.COLUMN_supplier_cash_payemnt_id+"='"+cc.receive_id+"'").get(0);
            PaymentPaidAPI pp = new PaymentPaidAPI(cs,cc);
            DebugHelper.print(pp);
            p.add(pp);
        }
        UploadData uploadData = new UploadData();
        uploadData.data = p;
        uploadData.url=url_payment_details_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"cash_receive_upload");

    }
    public void cash_receive_upload(ArrayList<String> data)
    {
        UploadData uploadData = new UploadData();
        uploadData.data = CashPaymentReceive.select(db,"1=1");
        uploadData.url=url_cash_receive_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"cash_receive_details_upload");

    }

    public void cash_receive_details_upload(ArrayList<String> data)
    {
        ArrayList<CoAccountReceiveDetails> c = CoAccountReceiveDetails.select(db,"1=1");
        DebugHelper.print(c);
        ArrayList<CashPaymentReceiveAPI> p = new ArrayList<>();

        for(CoAccountReceiveDetails cc : c)
        {
            CashPaymentReceive cs = CashPaymentReceive.select(db1,db.COLUMN_supplier_cash_payemnt_id+"='"+cc.receive_id+"'").get(0);
            CashPaymentReceiveAPI pp = new CashPaymentReceiveAPI(cs,cc);
            DebugHelper.print(pp);
            p.add(pp);
        }

        UploadData uploadData = new UploadData();
        uploadData.data = p;
        uploadData.url=url_cash_receive_details_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"cash_transfer_upload");

    }


    public void cash_transfer_upload(ArrayList<String> data)
    {
        UploadData uploadData = new UploadData();
        uploadData.data = CashAccountTransfer.select(db,"1=1");
        uploadData.url=url_cash_transfer_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"stock_transfer_upload");

    }
    public void stock_transfer_upload(ArrayList<String> data)
    {
        ArrayList<StockTransferDetails> c = StockTransferDetails.select(db,"1=1");
        ArrayList<GoodsTransferAPI> p = new ArrayList<>();
        for(StockTransferDetails cc : c)
        {
            try {
                StockTransferHead cs = StockTransferHead.select(db1, db.COLUMN_stock_transfer_id + "='" + cc.transfer_id + "'").get(0);
                GoodsTransferAPI pp = new GoodsTransferAPI(cs, cc);
                p.add(pp);
            }catch (Exception e){}
        }
        UploadData uploadData = new UploadData();
        uploadData.data = p;
        uploadData.url=url_stock_transfer_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"new_retail_product_upload");

    }

    public void new_retail_product_upload(ArrayList<String> data)
    {
        ArrayList<NewRetailProduct> p = NewRetailProduct.select(db,"1=1");
        UploadData uploadData = new UploadData();
        uploadData.data = p;
        uploadData.url=url_retail_product_upload + getUserDetails();
        uploadData.uploaddata(this,this,"",this,"pos_product_download");

    }

    public void pos_product_download(ArrayList<String> data)
    {
        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertPosProduct");
        DataDownload.downloadData(this,url_dataSync_download,"",cSystemInfo,c);
    }
    public void jsonInsertPosProduct(final String response,final Context context) {
        final ArrayList<PhotoPathTarDir> paths = new ArrayList<PhotoPathTarDir>();
        new Thread(new Runnable()
        {
            public void run() {
                try {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++) {
                        try{
                        PosProduct cProduct = new PosProduct();
                        JSONObject jb = (JSONObject) jr.getJSONObject(i);
                        cProduct.setId(Integer.parseInt(jb.get(url_dataSync_download_id).toString()));
                        cProduct.setProduct_name(jb.get(url_dataSync_download_product_name).toString());
                        cProduct.setTitle(jb.get(url_dataSync_download_product_title).toString());
                        cProduct.setColor(jb.get(url_dataSync_download_product_color).toString());
                        cProduct.setType(jb.get(url_dataSync_download_type).toString());
                        cProduct.setSub_type(jb.get(url_dataSync_download_sub_type).toString());
                        cProduct.setLocation((jb.get(url_dataSync_download_location).toString()));
                        cProduct.setSub_location(jb.get(url_dataSync_download_sub_location).toString());
                        cProduct.setFeatures(jb.get(url_dataSync_download_features).toString());
                        cProduct.setWeight(jb.get(url_dataSync_download_weight).toString());
                        cProduct.setDimensions(jb.get(url_dataSync_download_dimensions).toString());
                        cProduct.setIncludes(jb.get(url_dataSync_download_includes).toString());
                        cProduct.setGuarantee(jb.get(url_dataSync_download_guarantee) .toString());
                        cProduct.setQuantity(Integer.parseInt(jb.get(url_dataSync_download_location_balance).toString()));
                        cProduct.setSub_quantity((Integer.parseInt(jb.get(url_dataSync_download_sub_location_balance).toString())));
                        cProduct.setPrice(Double.parseDouble(jb.get(url_dataSync_download_price).toString()));
                        cProduct.setWhole_sale(Double.parseDouble(jb.get(url_dataSync_download_wholesale).toString()));
                        try {
                            cProduct.setTax(Double.parseDouble(jb.get(url_dataSync_download_tax).toString()));
                        }catch (Exception E){}
                        try {
                            cProduct.setDiscount(Double.parseDouble(jb.get(url_dataSync_download_discount).toString()));
                        }catch (Exception E){}
                        cProduct.setDiscount_type(jb.get(url_dataSync_download_discount_type).toString());
                        cProduct.setDate_from(jb.get(url_dataSync_download_date_from).toString());
                        cProduct.setDate_to(jb.get(url_dataSync_download_date_to).toString());
                        cProduct.setHour_from(jb.get(url_dataSync_download_hour_from).toString());
                        cProduct.setHour_to(jb.get(url_dataSync_download_hour_to).toString());
                        cProduct.setPhoto(jb.get(url_dataSync_download_photo).toString());
                        cProduct.setSold_quantity(0);
                        cProduct.setQuantity_left((Integer.parseInt(jb.get(url_dataSync_quantity_left).toString())));
                        cProduct.setBrand(jb.get(url_dataSync_download_brand).toString());
                        cProduct.setCategory_status(jb.get(url_dataSync_status).toString());
                        cProduct.setStatus(0);
                        PhotoPathTarDir p = new PhotoPathTarDir();
                        p.setUrl(cProduct.getPhoto());
                        p.setTarget("pos_product_"+cProduct.getId());
                        //DebugHelper.print(p);
                        cProduct.setPhoto("pos_product_"+cProduct.getId()+".png");
                        paths.add(p);
                        //DebugHelper.print(cProduct);
                        cProduct.insert(db1);
                        }catch (Exception e){}
                    }
                }catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        DataloadImage di = new DataloadImage();
                        CallBack c = new CallBack("Module.Loading.Loading", "eachimg");
                        CallBack c1 = new CallBack("Module.Loading.Loading", "purchase_invoice_download");
                        di.downloadData(context,paths,c,c1);
                    }
                });
            }
        }).start();
    }

    public void purchase_invoice_download(final String response,final Context context)
    {
        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertPurchaseInvoice");
        DataDownload.downloadData(context,url_purchase_product_invoice,"",cSystemInfo,c);
    }

    public void jsonInsertPurchaseInvoice(final String response,final Context context) {

        new Thread(new Runnable()
        {
            public void run() {
                try {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++) {
                        try{
                            PurchaseLogInvoice purchaseLogInvoice = new PurchaseLogInvoice();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);

                            String po = jb.get(url_purchase_product_invoice_po).toString();
                            purchaseLogInvoice.setId(TypeConvertion.parseInt(po.split("-")[po.split("-").length-1]));
                            purchaseLogInvoice.setSupplier(jb.get(url_purchase_product_invoice_supplier).toString());
                            purchaseLogInvoice.setTotal_quantity(TypeConvertion.parseInt(jb.get(url_purchase_product_invoice_total_quantity).toString()));
                            purchaseLogInvoice.setTotal_amount(TypeConvertion.parseDouble(jb.get(url_purchase_product_invoice_total_amount).toString()));
                            purchaseLogInvoice.setTotal_tax(TypeConvertion.parseDouble(jb.get(url_purchase_product_invoice_total_tax).toString()));
                            purchaseLogInvoice.setTotal_discount(TypeConvertion.parseDouble(jb.get(url_purchase_product_invoice_total_discount).toString()));
                            purchaseLogInvoice.setNet_payable(TypeConvertion.parseDouble(jb.get(url_purchase_product_invoice_net_payable).toString()));
                            purchaseLogInvoice.setAmount_paid(TypeConvertion.parseDouble(jb.get(url_purchase_product_invoice_amount_paid).toString()));
                            purchaseLogInvoice.setEntry_by(jb.get(url_purchase_product_invoice_entry_by).toString());
                            purchaseLogInvoice.setEntry_date(jb.get(url_purchase_product_invoice_entry_date).toString());
                            purchaseLogInvoice.setIf_data_synced(1);
                            purchaseLogInvoice.insert(db1);
                            DebugHelper.print(purchaseLogInvoice);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    //DebugHelper.print(paths);
                }catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        BaseDataService m = new BaseDataService();
                        m.url = url_purchase_product_invoice_details;
                        PurchaseLog mv = new PurchaseLog();
                        m.outputToModel = mv;
                        m.context = context;
                        m.parameterdataClass = cSystemInfo;
                        m.outputModelVariableMap.put("entry_id",url_purchase_product_invoice_details_entry_id);
                        m.outputModelVariableMap.put("supplier",url_purchase_product_invoice_details_supplier);
                        m.outputModelVariableMap.put("sku",url_purchase_product_invoice_details_sku);
                        m.outputModelVariableMap.put("quantity",url_purchase_product_invoice_details_quantity);
                        m.outputModelVariableMap.put("unit_price",url_purchase_product_invoice_details_unit_price);
                        m.outputModelVariableMap.put("amount",url_purchase_product_invoice_details_amount);
                        m.outputModelVariableMap.put("entry_by",url_purchase_product_invoice_details_entry_by);
                        m.outputModelVariableMap.put("entry_date",url_purchase_product_invoice_details_entry_date);
                        m.getDataFromURLModel(context,"purchase_log_details_download");
                    }
                });
            }
        }).start();
    }

    public void purchase_log_details_download(ArrayList<?> data,Context context)
    {
        for(Object ob : data)
        {
            PurchaseLog purchaseLog = (PurchaseLog) ob;
            purchaseLog.setId(0);
            purchaseLog.insert(db1);
        }
        //CallBack c = new CallBack("Module.Loading.Loading", "payment_paid_download_output");
        //DataDownload.downloadData(context,url_payment_download,"",cSystemInfo,c);

        BaseDataService m = new BaseDataService();
        m.url = url_payment_download;
        SupplierCashPaymentReceive mv = new SupplierCashPaymentReceive();
        m.outputToModel = mv;
        m.context = context;
        m.parameterdataClass = cSystemInfo;
        m.outputModelVariableMap.put("remark",url_payment_download_payment_type);
        m.outputModelVariableMap.put("ca_amount",url_payment_download_amount);
        m.outputModelVariableMap.put("account_name",url_payment_download_type);
        m.outputModelVariableMap.put("customer_name",url_payment_download_supplier);
        m.outputModelVariableMap.put("received_by",url_payment_download_entry_by);
        m.outputModelVariableMap.put("date_time",url_payment_download_entry_time);
        m.outputModelVariableMap.put("bank_name",url_payment_download_bank);
        m.outputModelVariableMap.put("po_id",url_payment_uuid);
        m.outputModelVariableMap.put("cheque_no",url_payment_cheque);
        m.getDataFromURLModel(context,"payment_paid_download_output");

    }

    public void payment_paid_download_output(ArrayList<?> data,Context context)
    {
        for(Object ob : data)
        {
            SupplierCashPaymentReceive supplierCashPaymentReceive = (SupplierCashPaymentReceive) ob;

            if(supplierCashPaymentReceive.account_name!=null && supplierCashPaymentReceive.account_name.equals(""))
            {
                supplierCashPaymentReceive.account_name="Expense Payment";
            }
            if(supplierCashPaymentReceive.remark!=null && supplierCashPaymentReceive.customer_name!=null && !supplierCashPaymentReceive.customer_name.equals(""))
            {
                if(supplierCashPaymentReceive.remark.toUpperCase().equals("CASH"))
                {
                    supplierCashPaymentReceive.cash_amount=supplierCashPaymentReceive.ca_amount;
                    supplierCashPaymentReceive.ca_amount=0;
                }
                else
                {
                    supplierCashPaymentReceive.card_amount=supplierCashPaymentReceive.ca_amount;
                    supplierCashPaymentReceive.ca_amount=0;
                }

            }
            if(supplierCashPaymentReceive.customer_name!=null && supplierCashPaymentReceive.customer_name.equals(""))
            {
                supplierCashPaymentReceive.customer_name="Expense Payment";
            }
            supplierCashPaymentReceive.id=0;
            /////-----check
            supplierCashPaymentReceive.bank_account="";
            //--------------
            try {
                String[] vc = supplierCashPaymentReceive.voucher.split("-");
                supplierCashPaymentReceive.id = TypeConvertion.parseInt(vc[vc.length - 1]);
            }catch (Exception e){}
            supplierCashPaymentReceive.remark="";
            supplierCashPaymentReceive.if_data_synced=1;
            if(supplierCashPaymentReceive.account_name.equals("Expense Payment"))
            {
                supplierCashPaymentReceive.account_name="";
            }
            if(supplierCashPaymentReceive.card_amount==0.0)
            {
                supplierCashPaymentReceive.bank_name="";
            }
            String con = db.COLUMN_supplier_cash_payemnt_account_name+"='"+supplierCashPaymentReceive.account_name+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_remark+"='"+supplierCashPaymentReceive.remark+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_ca_amount+"='"+supplierCashPaymentReceive.ca_amount+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_cash_amount+"='"+supplierCashPaymentReceive.cash_amount+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_card_amount+"='"+supplierCashPaymentReceive.card_amount+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_card_type+"='"+supplierCashPaymentReceive.card_type+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_bank_account+"='"+supplierCashPaymentReceive.bank_account+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_bank_name+"='"+supplierCashPaymentReceive.bank_name+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_customer_name+"='"+supplierCashPaymentReceive.customer_name+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_cheque_no+"='"+supplierCashPaymentReceive.cheque_no+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_received_by+"='"+supplierCashPaymentReceive.received_by+"' AND "+
                    db.COLUMN_supplier_cash_payemnt_date_time+"='"+supplierCashPaymentReceive.date_time+"'";

            supplierCashPaymentReceive.insert(db1,con);
        }

        ArrayList<CoExpAccountReceiveDetails> coExpAccountReceiveDetails = CoExpAccountReceiveDetails.select(db,"1=1");

        for(CoExpAccountReceiveDetails c : coExpAccountReceiveDetails)
        {
            SupplierCashPaymentReceive cs = SupplierCashPaymentReceive.select(db1,db.COLUMN_supplier_cash_payemnt_id+"='"+c.receive_id+"'").get(0);
            c.setPo_id(cs.getPo_id());
            c.update(db1);
        }

        BaseDataService m = new BaseDataService();
        m.url = url_payment_download_details;
        CoExpAccountReceiveDetails mv = new CoExpAccountReceiveDetails();
        m.outputToModel = mv;
        m.context = context;
        m.parameterdataClass = cSystemInfo;
        m.outputModelVariableMap.put("account_name",url_payment_download_details_acc_name);
        m.outputModelVariableMap.put("amount",url_payment_download_details_amount);
        m.outputModelVariableMap.put("remarks",url_payment_download_details_remarks);
        m.outputModelVariableMap.put("po_id",url_payment_download_details_po_uuid);
        m.outputModelVariableMap.put("uuid",url_payment_download_details_uuid);
        m.getDataFromURLModel(context,"payment_paid_details_download_output");

    }



    public void payment_paid_details_download_output(ArrayList<?> data,Context context)
    {
        for(Object ob : data)
        {
            try {
                CoExpAccountReceiveDetails coExpAccountReceiveDetails = (CoExpAccountReceiveDetails) ob;
                SupplierCashPaymentReceive cs = SupplierCashPaymentReceive.select(db1,db.COLUMN_supplier_cash_payemnt_po_id+"='"+coExpAccountReceiveDetails.po_id+"'").get(0);
                coExpAccountReceiveDetails.setReceive_id(cs.getId());
                coExpAccountReceiveDetails.setDate_time(cs.getDate_time());
                coExpAccountReceiveDetails.insert(db1);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        BaseDataService m = new BaseDataService();
        m.url = url_receive_download;
        CashPaymentReceive mv = new CashPaymentReceive();
        m.outputToModel = mv;
        m.context = context;
        m.parameterdataClass = cSystemInfo;
        m.outputModelVariableMap.put("remark",url_receive_download_payment_type);
        m.outputModelVariableMap.put("ca_amount",url_receive_download_amount);
        m.outputModelVariableMap.put("account_name",url_receive_download_type);
        m.outputModelVariableMap.put("customer_name",url_receive_download_supplier);
        m.outputModelVariableMap.put("received_by",url_receive_download_entry_by);
        m.outputModelVariableMap.put("date_time",url_receive_download_entry_time);
        m.outputModelVariableMap.put("bank_name",url_receive_download_bank);
        m.outputModelVariableMap.put("po_id",url_receive_uuid);
        m.outputModelVariableMap.put("cheque_no",url_receive_cheque);
        m.getDataFromURLModel(context,"payment_receive_download_output");
    }

    public void payment_receive_download_output(ArrayList<?> data,Context context)
    {
        for(Object ob: data)
        {
            CashPaymentReceive supplierCashPaymentReceive = (CashPaymentReceive) ob;
            DebugHelper.print(supplierCashPaymentReceive);
            if(supplierCashPaymentReceive.account_name!=null && supplierCashPaymentReceive.account_name.equals(""))
            {
                supplierCashPaymentReceive.account_name="Income Receive";
            }
            if(supplierCashPaymentReceive.remark!=null && supplierCashPaymentReceive.customer_name!=null && !supplierCashPaymentReceive.customer_name.equals(""))
            {
                if(supplierCashPaymentReceive.remark.toUpperCase().equals("CASH"))
                {
                    supplierCashPaymentReceive.cash_amount=supplierCashPaymentReceive.ca_amount;
                    supplierCashPaymentReceive.ca_amount=0;
                }
                else
                {
                    supplierCashPaymentReceive.card_amount=supplierCashPaymentReceive.ca_amount;
                    supplierCashPaymentReceive.ca_amount=0;
                }
                supplierCashPaymentReceive.remark="";
            }
            if(supplierCashPaymentReceive.customer_name!=null && supplierCashPaymentReceive.customer_name.equals(""))
            {
                supplierCashPaymentReceive.customer_name="Income Receive";
            }
            if(supplierCashPaymentReceive.account_name.equals("Income Receive"))
            {
                supplierCashPaymentReceive.account_name="";
            }
            if(supplierCashPaymentReceive.card_amount==0.0)
            {
                supplierCashPaymentReceive.bank_name="";
            }
            try {
                String[] vc = supplierCashPaymentReceive.voucher.split("-");
                supplierCashPaymentReceive.id = TypeConvertion.parseInt(vc[vc.length - 1]);
            }catch (Exception e){}
            supplierCashPaymentReceive.if_data_synced=1;
            supplierCashPaymentReceive.remark="";
            supplierCashPaymentReceive.account_name="";

            String con = db.COLUMN_cash_payemnt_account_name+"='"+supplierCashPaymentReceive.account_name+"' AND "+
                    db.COLUMN_cash_payemnt_remark+"='"+supplierCashPaymentReceive.remark+"' AND "+
                    db.COLUMN_cash_payemnt_ca_amount+"='"+supplierCashPaymentReceive.ca_amount+"' AND "+
                    db.COLUMN_cash_payemnt_cash_amount+"='"+supplierCashPaymentReceive.cash_amount+"' AND "+
                    db.COLUMN_cash_payemnt_card_amount+"='"+supplierCashPaymentReceive.card_amount+"' AND "+
                    db.COLUMN_cash_payemnt_card_type+"='"+supplierCashPaymentReceive.card_type+"' AND "+
                    db.COLUMN_cash_payemnt_bank_account+"='"+supplierCashPaymentReceive.bank_account+"' AND "+
                    db.COLUMN_cash_payemnt_bank_name+"='"+supplierCashPaymentReceive.bank_name+"' AND "+
                    db.COLUMN_cash_payemnt_customer_name+"='"+supplierCashPaymentReceive.customer_name+"' AND "+
                    db.COLUMN_cash_payemnt_cheque_no+"='"+supplierCashPaymentReceive.cheque_no+"' AND "+
                    db.COLUMN_cash_payemnt_received_by+"='"+supplierCashPaymentReceive.received_by+"' AND "+
                    db.COLUMN_cash_payemnt_date_time+"='"+supplierCashPaymentReceive.date_time+"'";
            System.out.println(con);

            supplierCashPaymentReceive.insert(db1,con);
        }

        BaseDataService m = new BaseDataService();
        m.url = url_receive_download_details;
        CashPaymentReceivedDetails mv = new CashPaymentReceivedDetails();
        m.outputToModel = mv;
        m.context = context;
        m.parameterdataClass = cSystemInfo;
        m.outputModelVariableMap.put("account_name",url_payment_download_details_acc_name);
        m.outputModelVariableMap.put("amount",url_payment_download_details_amount);
        m.outputModelVariableMap.put("remarks",url_payment_download_details_remarks);
        m.getDataFromURLModel(context,"recevie_paid_details_download_output");
    }


    public void recevie_paid_details_download_output(ArrayList<?> data,Context context)
    {
        for(Object ob : data)
        {
            try {
                CashPaymentReceivedDetails coExpAccountReceiveDetails = (CashPaymentReceivedDetails) ob;
                CashPaymentReceive cs = CashPaymentReceive.select(db1,db.COLUMN_cash_payemnt_po_id+"='"+coExpAccountReceiveDetails.getPo_id()+"'").get(0);
                coExpAccountReceiveDetails.setReceiveId(cs.getId());
                coExpAccountReceiveDetails.setDateTime(cs.getDate_time());
                coExpAccountReceiveDetails.insert(db1);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        BaseDataService m = new BaseDataService();
        m.url = url_invoice_product_download;
        InvoiceProduct mv = new InvoiceProduct();
        m.outputToModel = mv;
        m.context = context;
        m.parameterdataClass = cSystemInfo;
        m.getDataFromURLModel(context,"downloadInvoiceProduct");
    }

    public void downloadInvoiceProduct(ArrayList<?> data,Context context)
    {
        for(Object ob : data)
        {
            ((InvoiceProduct) ob).insert(db1);
        }
        DebugHelper.print(InvoiceProduct.select(db1,"1=1"));
        BaseDataService m = new BaseDataService();
        m.url = url_cash_transfer_download;
        CashAccountTransfer mv = new CashAccountTransfer();
        m.outputToModel = mv;
        m.context = context;
        m.parameterdataClass = cSystemInfo;
        m.outputModelVariableMap.put("account_name",url_cash_transfer_download_account);
        m.outputModelVariableMap.put("remarks",url_cash_transfer_download_remarks);
        m.outputModelVariableMap.put("approve_date",url_cash_transfer_download_amount);
        m.outputModelVariableMap.put("receive_type",url_cash_transfer_download_receive_type);
        m.outputModelVariableMap.put("receive_from",url_cash_transfer_download_receive_from);
        m.outputModelVariableMap.put("receive_by",url_cash_transfer_download_receive_by);
        m.outputModelVariableMap.put("receive_date",url_cash_transfer_download_receive_date);
        m.outputModelVariableMap.put("approve_by",url_cash_transfer_download_approve_by);
        m.getDataFromURLModel(context,"cash_transfer_download_output");
    }


    public void cash_transfer_download_output(ArrayList<?> data,Context context)
    {
        for(Object ob : data)
        {
            try {
                CashAccountTransfer cashAccountTransfer = (CashAccountTransfer) ob;
                cashAccountTransfer.setAmount(TypeConvertion.parseDouble(cashAccountTransfer.getApprove_date()));
                cashAccountTransfer.setApprove_date("");
                if(cashAccountTransfer.receive_type.toUpperCase().equals("CASH DEPOSIT"))
                {
                    cashAccountTransfer.setReceive_type("Bank");
                }
                else
                {
                    cashAccountTransfer.setReceive_type("Cash");
                }
                try {
                    String new_po_id = cashAccountTransfer.contra;
                    String new_id = new_po_id.split("-")[new_po_id.split("-").length - 1];
                    cashAccountTransfer.id = TypeConvertion.parseInt(new_id);
                }catch (Exception e){}
                String con =
                        db.COLUMN_cash_account_account_name+"='"+cashAccountTransfer.account_name+"' AND "+
                        db.COLUMN_cash_account_remarks+"='"+cashAccountTransfer.remarks+"' AND "+
                        db.COLUMN_cash_account_amount+"='"+cashAccountTransfer.amount+"' AND "+
                        db.COLUMN_cash_account_receive_type+"='"+cashAccountTransfer.receive_type+"' AND "+
                        db.COLUMN_cash_account_receive_by+"='"+cashAccountTransfer.receive_by+"' AND "+
                        db.COLUMN_cash_account_receive_from+"='"+cashAccountTransfer.receive_from+"' AND "+
                        db.COLUMN_cash_account_receive_date+"='"+cashAccountTransfer.receive_date+"' AND "+
                        db.COLUMN_cash_account_approve_by+"='"+cashAccountTransfer.approve_by+"' AND "+
                        db.COLUMN_cash_account_pos_by+"='"+cashAccountTransfer.pos_by+"' AND "+
                        db.COLUMN_cash_account_approve_date+"='"+cashAccountTransfer.approve_date+"'";
                cashAccountTransfer.insert(db1,con);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertRetailProduct");
        DataDownload.downloadData(context,url_retail_product_download,"",cSystemInfo,c);

    }



    public void retail_product_download(final String response,final Context context)
    {
        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertRetailProduct");
        DataDownload.downloadData(context,url_retail_product_download,"",cSystemInfo,c);
    }

    public void jsonInsertRetailProduct(final String response,final Context context) {
        final ArrayList<PhotoPathTarDir> paths = new ArrayList<PhotoPathTarDir>();
        new Thread(new Runnable()
        {
            public void run() {
                try {
                    final JSONArray jr = new JSONArray(response.toString());
                    ArrayList<NewRetailProduct> newRetailProducts = new ArrayList<>();
                    for (int i = 0; i < jr.length(); i++) {
                        try{
                            NewRetailProduct cProduct = new NewRetailProduct();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);

                            cProduct.setCategory(jb.get(url_retail_product_category).toString());
                            cProduct.setSub_category(jb.get(url_retail_product_sub_category).toString());
                            cProduct.setTitle(jb.get(url_retail_product_title).toString());
                            cProduct.setSku(jb.get(url_retail_product_sku).toString());
                            cProduct.setDescription(jb.get(url_retail_product_description).toString());
                            cProduct.setWeight(jb.get(url_retail_product_weight).toString());
                            cProduct.setDimension(jb.get(url_retail_product_dimensions).toString());
                            cProduct.setAccessories(jb.get(url_retail_product_accessories).toString());
                            cProduct.setWarrenty(jb.get(url_retail_product_warrenty).toString());
                            cProduct.setImages(jb.get(url_retail_product_images).toString());
                            cProduct.setManufacturer(jb.get(url_retail_product_manufacturer).toString());
                            cProduct.setBrand(jb.get(url_retail_product_brand).toString());
                            cProduct.setMarket_place(jb.get(url_retail_product_marketplace).toString());
                            try {
                                cProduct.setApproximate_cost(Double.parseDouble(jb.get(url_retail_product_approximate_cost).toString()));
                            }catch (Exception e){}
                            try {
                                cProduct.setWhole_sale_price(Double.parseDouble(jb.get(url_retail_product_whole_sale_price).toString()));
                            }catch (Exception e){}
                            try {
                                cProduct.setRetail_price(Double.parseDouble(jb.get(url_retail_product_retail_price).toString()));
                            }catch (Exception e){}
                            cProduct.setPurchase_tax_group(jb.get(url_retail_product_purchase_tax_group).toString());
                            cProduct.setSales_tax_group(jb.get(url_retail_product_sales_tax_group).toString());
                            cProduct.setEntry_by(jb.get(url_retail_product_entry_by).toString());
                            cProduct.setEntry_date_time(jb.get(url_retail_product_entry_time).toString());

                            newRetailProducts.add(cProduct);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    paths.addAll(setRetailEntryHeader(newRetailProducts));
                   // DebugHelper.print(paths);
                }catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        DataloadImage di = new DataloadImage();
                        CallBack c = new CallBack("Module.Loading.Loading", "eachimg");
                        CallBack c1 = new CallBack("Module.Loading.Loading", "finalimg");
                        di.downloadData(context,paths,c,c1);

                       // CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertPosInvoice");
                        //DataDownload.downloadData(context,url_pos_invoice_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }
    public ArrayList<PhotoPathTarDir> setRetailEntryHeader(ArrayList<NewRetailProduct> retailEntryHeader)
    {
        ArrayList<PhotoPathTarDir> paths = new ArrayList<>();
        ArrayList<String> sku = new ArrayList<>();

        for(NewRetailProduct newRetailProduct : retailEntryHeader)
        {
            if(!newRetailProduct.getSku().trim().equals("")) {
                String[] strings = newRetailProduct.getSku().split("-");
                if (strings.length > 1) {
                    if (!sku.contains(strings[0] + "-" + strings[1])) {
                        sku.add(strings[0] + "-" + strings[1]);
                    }
                } else {
                    if (!sku.contains(newRetailProduct.getSku())) {
                        sku.add(newRetailProduct.getSku());
                    }
                }
            }
        }

        int entry_id = 1;
        for(String title : sku)
        {
            NewRetailProductEntryHead newRetailProductEntryHead = new NewRetailProductEntryHead();
            newRetailProductEntryHead.setId(entry_id);
            newRetailProductEntryHead.setSku(title);

            for(NewRetailProduct product : retailEntryHeader)
            {
                if(product.getSku().contains(title))
                {
                   // System.out.println(title+"->"+product.getSku());
                    String img= "";
                    int count=1;
                    //System.out.println("p: "+product.getImages());
                    for(String s: product.getImages().split(";"))
                    {
                        if(!s.equals("")) {
                            //System.out.println("s: "+s);
                            String file_name = "new-retail-sku-" + product.getSku() + "-" + entry_id + "-" + count + "_" + DateTimeCalculation.getCurrentTimeStamp();
                            PhotoPathTarDir p = new PhotoPathTarDir();
                            p.setUrl(url_retail_product_download_image + cSystemInfo.getCompany_id() + "/" + s);
                            p.setTarget(FileManagerSetting.new_retail_folder + "/" + file_name);
                            img += file_name + ".png;";
                            paths.add(p);
                            //DebugHelper.print(p);
                            count++;
                        }
                    }
                    product.setImages(img);
                    product.setEntry_id(entry_id);
                    newRetailProductEntryHead.setEntry_by(product.getEntry_by());
                    newRetailProductEntryHead.setEntry_date(product.getEntry_date_time());
                    product.insert(db1);

                }
            }
            newRetailProductEntryHead.insert(db1);
            entry_id++;
        }
        return paths;
    }
    public void jsonInsertProduct(final String response,final Context context) {
        new Thread(new Runnable()
        {
            public void run() {
                ArrayList<Product> products = new ArrayList<Product>();
                try {
                    final JSONArray jr = new JSONArray(response.toString());
                    System.out.println("here:"+jr.length());
                    for (int i = 0; i < jr.length(); i++) {
                        try{
                            Product cProduct = new Product();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            cProduct.setId(Integer.parseInt(jb.get(url_stock_assignment_id).toString()));
                            cProduct.setAssign_no(jb.get(url_stock_assignment_assign_no).toString());
                            cProduct.setPoint(jb.get(url_stock_assignment_point).toString());
                            cProduct.setRoute((jb.get(url_stock_assignment_route).toString()));
                            cProduct.setSection(jb.get(url_stock_assignment_section).toString());
                            cProduct.setFrequency(jb.get(url_stock_assignment_frequency).toString());
                            cProduct.setCompany((jb.get(url_stock_assignment_company).toString()));
                            cProduct.setProduct_type(jb.get(url_stock_assignment_product_type).toString());
                            cProduct.setGift_for(jb.get(url_stock_assignment_gift_for).toString());
                            cProduct.setProduct_category(jb.get(url_stock_assignment_product_category).toString());
                            cProduct.setPhoto((jb.get(url_stock_assignment_photo).toString()));
                            cProduct.setBrand(jb.get(url_stock_assignment_brand).toString());
                            cProduct.setSku(jb.get(url_stock_assignment_sku).toString());
                            cProduct.setSub_sku(jb.get(url_stock_assignment_sub_sku).toString());
                            cProduct.setSku_qty(Integer.parseInt(jb.get(url_stock_assignment_sku_qty).toString()));
                            cProduct.setSku_price(Double.parseDouble(jb.get(url_stock_assignment_sku_price).toString()));
                            cProduct.setTarget(Integer.parseInt(jb.get(url_stock_assignment_target).toString()));
                            cProduct.setDate_from((jb.get(url_stock_assignment_date_from).toString()));
                            cProduct.setDate_to(jb.get(url_stock_assignment_date_to).toString());
                            cProduct.setSold_sku(Integer.parseInt(jb.get(url_stock_assignment_sold_sku).toString()));
                            cProduct.setReturn_sku(Integer.parseInt(jb.get(url_stock_assignment_return_sku).toString()));
                            cProduct.setTotal_sku(Integer.parseInt(jb.get(url_stock_assignment_sku_qty).toString()));
                            cProduct.setAccept_date(jb.get(url_stock_assignment_accepted_time).toString());
                            cProduct.setTarget(0);
                            cProduct.setOrder_permission(jb.get(url_stock_assignment_order_sr).toString());
                            cProduct.setPayment_permission(jb.get(url_stock_assignment_payment_sr).toString());
                            cProduct.setSell_permission(jb.get(url_stock_assignment_delivery_sr).toString());
                            if(jb.get(url_stock_assignment_downloadable_status).toString().equals("1"))
                            {
                                dataDownloadableStatus=true;
                            }
                            else
                            {
                                dataDownloadableStatus=false;
                            }
                            products.add(cProduct);
                        }catch (Exception e){}
                    }
                    PendingList.pendingProductList = products;

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(context, PendingList.class);
                        context.startActivity(intent);
                    }
                });
            }
        }).start();
    }


    public void jsonInsertText(final String response, final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            TextString ts = new TextString();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_text_download_id).toString()));
                            ts.setVarname(jb.get(url_text_download_varname).toString());
                            ts.setText(jb.get(url_text_download_text).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertSalesPerson");
                        DataDownload.downloadData(context,url_pos_sales_person,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }


    public void jsonInsertSalesPerson(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            SalesPerson ts = new SalesPerson();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_pos_sales_person_id).toString()));
                            ts.setName(jb.get(url_pos_sales_person_name).toString());
                            ts.setFullName(jb.get(url_pos_sales_person_full_name).toString());
                            ts.setIsSelected(Integer.parseInt(jb.get(url_pos_sales_person_is_selected).toString()));
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertPosCustomer");
                        DataDownload.downloadData(context,url_pos_customer,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertPosCustomer(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            PosCustomer ts = new PosCustomer();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_pos_customer_id).toString()));
                            ts.setName(jb.get(url_pos_customer_name).toString());
                            ts.setFull_name(jb.get(url_pos_customer_full_name).toString());
                            ts.setPhone(jb.get(url_pos_customer_phone).toString());
                            ts.setBalance(Double.parseDouble(jb.get(url_pos_customer_balance).toString()));
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertBank");
                        DataDownload.downloadData(context,url_bank_list_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertBank(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            Bank ts = new Bank();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_bank_id).toString()));
                            ts.setBankName(jb.get(url_bank_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertWarehouse");
                        DataDownload.downloadData(context,url_warehouse_list_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();



    }

    public void jsonInsertWarehouse(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            Warehouse ts = new Warehouse();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_warehouse_list_download_id).toString()));
                            ts.setName(jb.get(url_warehouse_list_download_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertCashAccount");
                        DataDownload.downloadData(context,url_cash_account,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }
    public void jsonInsertCashAccount(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            CashAccount ts = new CashAccount();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_cash_account_id).toString()));
                            ts.setName(jb.get(url_cash_account_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertCoAccount");
                        DataDownload.downloadData(context,url_co_account,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertCoAccount(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            CoAccount ts = new CoAccount();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setAccount_name(jb.get(url_co_account_name).toString());
                            ts.setAccount_type(jb.get(url_co_account_type).toString());
                            if (!ts.getAccount_name().trim().equals("")) {
                                ts.insert(db1);
                            }
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertCoExpAccount");
                        DataDownload.downloadData(context,url_co_exp_account,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertCoExpAccount(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            CoExpAccount ts = new CoExpAccount();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setAccount_name(jb.get(url_co_exp_account_name).toString());
                            ts.setAccount_type(jb.get(url_co_exp_account_type).toString());
                            if (!ts.getAccount_name().trim().equals("")) {
                                ts.insert(db1);
                            }
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertCard");
                        DataDownload.downloadData(context,url_card_list_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertCard(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            Card ts = new Card();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_card_id).toString()));
                            ts.setCardName(jb.get(url_card_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertRetailBrand");
                        DataDownload.downloadData(context,url_retail_brand_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertRetailBrand(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            RetailProductBrand ts = new RetailProductBrand();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setName(jb.get(url_retail_brand_download_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertRetailManufacture");
                        DataDownload.downloadData(context,url_retail_manufacture_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertRetailManufacture(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            RetailProductManufacture ts = new RetailProductManufacture();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setName(jb.get(url_retail_manufacture_download_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertRetailSalesTaxGroup");
                        DataDownload.downloadData(context,url_retail_sales_tax_group_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertRetailSalesTaxGroup(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            RetailProductSalesTaxGroup ts = new RetailProductSalesTaxGroup();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setName(jb.get(url_retail_sales_tax_group_download_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertRetailPurchaseTaxGroup");
                        DataDownload.downloadData(context,url_retail_purchase_tax_group_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertRetailPurchaseTaxGroup(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            RetailProductTaxGroup ts = new RetailProductTaxGroup();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setName(jb.get(url_retail_purchase_tax_group_download_name).toString());
                            ts.setPercentage(TypeConvertion.parseDouble(jb.get(url_retail_purchase_tax_group_download_percentage).toString()));
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertMarketPlace");
                        DataDownload.downloadData(context,url_retail_market_place_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertMarketPlace(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            MarketPlace ts = new MarketPlace();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setName(jb.get(url_retail_market_place_download_name).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertRetailImageGroup");
                        DataDownload.downloadData(context,url_retail_image_group_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertRetailImageGroup(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            RetailImageGroup ts = new RetailImageGroup();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setGroup_name(jb.get(url_retail_image_group_download_name).toString());
                            ts.setHeight(TypeConvertion.parseInt(jb.get(url_retail_image_group_download_height).toString()));
                            ts.setWidth(TypeConvertion.parseInt(jb.get(url_retail_image_group_download_width).toString()));
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertSupplier");
                        DataDownload.downloadData(context,url_retail_supplier_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertSupplier(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            Supplier ts = new Supplier();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setName(jb.get(url_retail_supplier_download_name).toString());
                            ts.setAddress(jb.get(url_retail_supplier_download_address1).toString());
                            ts.setAddress2(jb.get(url_retail_supplier_download_address2).toString());
                            ts.setAddress3(jb.get(url_retail_supplier_download_address3).toString());
                            ts.setMobile(jb.get(url_retail_supplier_download_mobile).toString());
                            ts.setEmail(jb.get(url_retail_supplier_download_email).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertProductAddCategory");
                        DataDownload.downloadData(context,url_product_add_category,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }


    public void jsonInsertProductAddCategory(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            ProductAddCategory ts = new ProductAddCategory();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setName(jb.get(url_product_add_category_name).toString());
                            ts.setCategory(jb.get(url_product_add_category_category).toString());
                            ts.setProduct_class(jb.get(url_product_add_category_product_class).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertVerificationUser");
                        DataDownload.downloadData(context,url_pos_invoice_verification_user,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertVerificationUser(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            PosInvoicePayLaterUser ts = new PosInvoicePayLaterUser();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setUser_name(jb.get(url_user_name).toString());
                            ts.setPassword(jb.get(url_card_password).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertSubMenu");
                        DataDownload.downloadData(context,url_sub_menu,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertSubMenu(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            DashboardSubMenu ts = new DashboardSubMenu();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_sub_menu_id).toString()));
                            ts.setVarname(jb.get(url_sub_menu_varname).toString());
                            ts.setText(jb.get(url_sub_menu_text).toString());
                            ts.setNew_var_name(jb.get(url_sub_menu_new_var).toString());
                            ts.insert(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertMenu");
                        DataDownload.downloadData(context,url_menu_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertMenu(final String response,final Context context)
    {
        final ArrayList<PhotoPathTarDir> paths = new ArrayList<PhotoPathTarDir>();
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            DashboardMenu ts = new DashboardMenu();
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            ts.setId(Integer.parseInt(jb.get(url_menu_download_id).toString()));
                            ts.setVarname(jb.get(url_menu_download_varname).toString());
                            ts.setImage(ts.getVarname() + ".png");
                            ts.setText(jb.get(url_menu_download_text).toString());
                            ts.setCategory(jb.get(url_menu_download_category).toString());
                            PhotoPathTarDir p = new PhotoPathTarDir();
                            p.setUrl(jb.get(url_menu_download_image).toString());
                            p.setTarget(ts.getVarname());
                            paths.add(p);
                            ts.insert(db1);
                        }catch (Exception e){}
                    }

                    //  User cSystemInfo = new User();
                    //   cSystemInfo = cSystemInfo.select(db1,"1=1");
                    DebugHelper.print(cSystemInfo);
                    PhotoPathTarDir p = new PhotoPathTarDir();
                    p.setUrl(ApiSettings.url_company_logo+"/"+cSystemInfo.getLogo());
                    p.setTarget("app_logo");
                    paths.add(p);
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        DataloadImage di = new DataloadImage();
                        CallBack c = new CallBack("Module.Loading.Loading", "eachimg");
                        CallBack c1 = new CallBack("Module.Loading.Loading", "pos_product_upload");
                        di.downloadData(context,paths,c,c1);
                    }
                });
            }
        }).start();
    }

    public void eachimg(final String response,final Context context)
    {
    }
    public void finalimg(final String response,final Context context)
    {
        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertPosInvoice");
        DataDownload.downloadData(context,url_pos_invoice_download,"",cSystemInfo,c);
    }

    public void jsonInsertPosInvoice(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            String sale_type = jb.get("sale_type").toString();
                            if(sale_type.toUpperCase().equals("REGULAR"))
                            {
                                String inv = jb.get(url_pos_invoice_head_invoice_id).toString();
                                if(inv.split("-")[1].equals("W"))
                                {
                                    final PosInvoiceHeadRegularWeb posInvoiceHead = new PosInvoiceHeadRegularWeb();
                                    String invoice_id = inv.split("-")[2];
                                    posInvoiceHead.setId(TypeConvertion.parseInt(invoice_id));
                                    posInvoiceHead.setInvoice_id(inv);
                                    posInvoiceHead.setTotal_amount_full(Double.parseDouble(jb.get(url_pos_invoice_head_Total_amount_full).toString()));
                                    posInvoiceHead.setTotal_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Total_amount).toString()));
                                    posInvoiceHead.setTotal_quantity(TypeConvertion.parseInt(jb.get(url_pos_invoice_head_Total_quantity).toString()));
                                    posInvoiceHead.setTotal_tax(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Total_tax).toString()));
                                    posInvoiceHead.setIteam_discount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Iteam_discount).toString()));
                                    posInvoiceHead.setAdditional_discount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Additional_discount).toString()));
                                    posInvoiceHead.setDelivery_expense(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Delivery_expense).toString()));
                                    posInvoiceHead.setSales_person(jb.get(url_pos_invoice_head_Sales_person).toString());
                                    posInvoiceHead.setCustomer(jb.get(url_pos_invoice_head_Customer).toString());
                                    posInvoiceHead.setCash_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Cash_amount).toString()));
                                    posInvoiceHead.setNote_given(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Note_given).toString()));
                                    posInvoiceHead.setChange(posInvoiceHead.getCash_amount() - posInvoiceHead.getNote_given());
                                    posInvoiceHead.setCard_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_Card_amount).toString()));
                                    posInvoiceHead.setCard_type(jb.get(url_pos_invoice_Card_type).toString());
                                    posInvoiceHead.setCheque_no(jb.get(url_pos_invoice_Cheque_no).toString());
                                    posInvoiceHead.setBank(jb.get(url_pos_invoice_Bank).toString());
                                    posInvoiceHead.setPay_later_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_Pay_later_amount).toString()));
                                    posInvoiceHead.setVerify_user(jb.get(url_pos_invoice_Verify_user).toString());
                                    posInvoiceHead.setSale_type(jb.get(url_pos_invoice_Sale_type).toString());
                                    posInvoiceHead.setTotal_paid_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_total_paid_amount).toString()));
                                    posInvoiceHead.setInvoice_generate_by(jb.get(url_pos_invoice_Invoice_generate_by).toString());
                                    posInvoiceHead.setInvoice_date(jb.get(url_pos_invoice_Invoice_date).toString());
                                    posInvoiceHead.setNew_paid_amount(0);
                                    String payment_mode = "";
                                    if (posInvoiceHead.getCash_amount() > 0.0 && posInvoiceHead.getCard_amount() == 0.0 && posInvoiceHead.getPay_later_amount() == 0.0) {
                                        payment_mode = "Cash";
                                    } else if (posInvoiceHead.getCash_amount() == 0.0 && posInvoiceHead.getCard_amount() > 0.0 && posInvoiceHead.getPay_later_amount() == 0.0) {
                                        payment_mode = "Card";
                                    } else if (posInvoiceHead.getCash_amount() == 0.0 && posInvoiceHead.getCard_amount() == 0.0 && posInvoiceHead.getPay_later_amount() > 0.0) {
                                        payment_mode = "Pay Later";
                                    } else {
                                        payment_mode = "Multi";
                                    }
                                    posInvoiceHead.setPayment_mode(payment_mode);
                                    posInvoiceHead.setStatus(1);
                                    posInvoiceHead.insert(db1);
                                }
                            }
                            else if(sale_type.toUpperCase().equals("POS"))
                            {
                                String inv = jb.get(url_pos_invoice_head_invoice_id).toString();
                                inv = inv.replace(cSystemInfo.getSelected_pos() + "-", "");
                                if (inv.charAt(0) == 'W') {
                                    final PosInvoiceHeadWeb posInvoiceHead = new PosInvoiceHeadWeb();

                                    String invoice_id = jb.get(url_pos_invoice_head_invoice_id).toString().replace(cSystemInfo.getSelected_pos() + "-W-", "");
                                    posInvoiceHead.setId(TypeConvertion.parseInt(invoice_id));
                                    posInvoiceHead.setInvoice_id(invoice_id);
                                    posInvoiceHead.setTotal_amount_full(Double.parseDouble(jb.get(url_pos_invoice_head_Total_amount_full).toString()));
                                    posInvoiceHead.setTotal_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Total_amount).toString()));
                                    posInvoiceHead.setTotal_quantity(TypeConvertion.parseInt(jb.get(url_pos_invoice_head_Total_quantity).toString()));
                                    posInvoiceHead.setTotal_tax(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Total_tax).toString()));
                                    posInvoiceHead.setIteam_discount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Iteam_discount).toString()));
                                    posInvoiceHead.setAdditional_discount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Additional_discount).toString()));
                                    posInvoiceHead.setDelivery_expense(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Delivery_expense).toString()));
                                    posInvoiceHead.setSales_person(jb.get(url_pos_invoice_head_Sales_person).toString());
                                    posInvoiceHead.setCustomer(jb.get(url_pos_invoice_head_Customer).toString());
                                    posInvoiceHead.setCash_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Cash_amount).toString()));
                                    posInvoiceHead.setNote_given(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Note_given).toString()));
                                    posInvoiceHead.setChange(posInvoiceHead.getCash_amount() - posInvoiceHead.getNote_given());
                                    posInvoiceHead.setCard_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_Card_amount).toString()));
                                    posInvoiceHead.setCard_type(jb.get(url_pos_invoice_Card_type).toString());
                                    posInvoiceHead.setCheque_no(jb.get(url_pos_invoice_Cheque_no).toString());
                                    posInvoiceHead.setBank(jb.get(url_pos_invoice_Bank).toString());
                                    posInvoiceHead.setPay_later_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_Pay_later_amount).toString()));
                                    posInvoiceHead.setVerify_user(jb.get(url_pos_invoice_Verify_user).toString());
                                    posInvoiceHead.setSale_type(jb.get(url_pos_invoice_Sale_type).toString());
                                    posInvoiceHead.setTotal_paid_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_total_paid_amount).toString()));
                                    posInvoiceHead.setInvoice_generate_by(jb.get(url_pos_invoice_Invoice_generate_by).toString());
                                    posInvoiceHead.setInvoice_date(jb.get(url_pos_invoice_Invoice_date).toString());
                                    posInvoiceHead.setNew_paid_amount(0);
                                    String payment_mode = "";
                                    if (posInvoiceHead.getCash_amount() > 0.0 && posInvoiceHead.getCard_amount() == 0.0 && posInvoiceHead.getPay_later_amount() == 0.0) {
                                        payment_mode = "Cash";
                                    } else if (posInvoiceHead.getCash_amount() == 0.0 && posInvoiceHead.getCard_amount() > 0.0 && posInvoiceHead.getPay_later_amount() == 0.0) {
                                        payment_mode = "Card";
                                    } else if (posInvoiceHead.getCash_amount() == 0.0 && posInvoiceHead.getCard_amount() == 0.0 && posInvoiceHead.getPay_later_amount() > 0.0) {
                                        payment_mode = "Pay Later";
                                    } else {
                                        payment_mode = "Multi";
                                    }
                                    posInvoiceHead.setPayment_mode(payment_mode);
                                    posInvoiceHead.setStatus(1);
                                    posInvoiceHead.insert(db1);
                                }
                                else {
                                    final PosInvoiceHead posInvoiceHead = new PosInvoiceHead();

                                    String invoice_id = jb.get(url_pos_invoice_head_invoice_id).toString().replace(cSystemInfo.getSelected_pos() + "-P-", "");
                                    posInvoiceHead.setId(TypeConvertion.parseInt(invoice_id));
                                    posInvoiceHead.setInvoice_id(invoice_id);
                                    posInvoiceHead.setTotal_amount_full(Double.parseDouble(jb.get(url_pos_invoice_head_Total_amount_full).toString()));
                                    posInvoiceHead.setTotal_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Total_amount).toString()));
                                    posInvoiceHead.setTotal_quantity(TypeConvertion.parseInt(jb.get(url_pos_invoice_head_Total_quantity).toString()));
                                    posInvoiceHead.setTotal_tax(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Total_tax).toString()));
                                    posInvoiceHead.setIteam_discount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Iteam_discount).toString()));
                                    posInvoiceHead.setAdditional_discount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Additional_discount).toString()));
                                    posInvoiceHead.setDelivery_expense(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Delivery_expense).toString()));
                                    posInvoiceHead.setSales_person(jb.get(url_pos_invoice_head_Sales_person).toString());
                                    posInvoiceHead.setCustomer(jb.get(url_pos_invoice_head_Customer).toString());
                                    posInvoiceHead.setCash_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Cash_amount).toString()));
                                    posInvoiceHead.setNote_given(TypeConvertion.parseDouble(jb.get(url_pos_invoice_head_Note_given).toString()));
                                    posInvoiceHead.setChange(posInvoiceHead.getCash_amount() - posInvoiceHead.getNote_given());
                                    posInvoiceHead.setCard_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_Card_amount).toString()));
                                    posInvoiceHead.setCard_type(jb.get(url_pos_invoice_Card_type).toString());
                                    posInvoiceHead.setCheque_no(jb.get(url_pos_invoice_Cheque_no).toString());
                                    posInvoiceHead.setBank(jb.get(url_pos_invoice_Bank).toString());
                                    posInvoiceHead.setPay_later_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_Pay_later_amount).toString()));
                                    posInvoiceHead.setVerify_user(jb.get(url_pos_invoice_Verify_user).toString());
                                    posInvoiceHead.setSale_type(jb.get(url_pos_invoice_Sale_type).toString());
                                    posInvoiceHead.setTotal_paid_amount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_total_paid_amount).toString()));
                                    posInvoiceHead.setInvoice_generate_by(jb.get(url_pos_invoice_Invoice_generate_by).toString());
                                    posInvoiceHead.setInvoice_date(jb.get(url_pos_invoice_Invoice_date).toString());
                                    String payment_mode = "";
                                    if (posInvoiceHead.getCash_amount() > 0.0 && posInvoiceHead.getCard_amount() == 0.0 && posInvoiceHead.getPay_later_amount() == 0.0) {
                                        payment_mode = "Cash";
                                    } else if (posInvoiceHead.getCash_amount() == 0.0 && posInvoiceHead.getCard_amount() > 0.0 && posInvoiceHead.getPay_later_amount() == 0.0) {
                                        payment_mode = "Card";
                                    } else if (posInvoiceHead.getCash_amount() == 0.0 && posInvoiceHead.getCard_amount() == 0.0 && posInvoiceHead.getPay_later_amount() > 0.0) {
                                        payment_mode = "Pay Later";
                                    } else {
                                        payment_mode = "Multi";
                                    }
                                    posInvoiceHead.setPayment_mode(payment_mode);
                                    posInvoiceHead.setStatus(1);
                                    posInvoiceHead.setIf_synced(1);
                                    posInvoiceHead.insert(db1);
                                }
                            }
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        CallBack c = new CallBack("Module.Loading.Loading", "jsonInsertPosInvoiceDetail");
                        DataDownload.downloadData(context,url_pos_invoice_details_download,"",cSystemInfo,c);
                    }
                });
            }
        }).start();
    }

    public void jsonInsertPosInvoiceDetail(final String response,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    final JSONArray jr = new JSONArray(response.toString());
                    for (int i = 0; i < jr.length(); i++)
                    {
                        try {
                            JSONObject jb = (JSONObject) jr.getJSONObject(i);
                            String sale_type = jb.get("sale_type").toString();
                            if(sale_type.toUpperCase().equals("REGULAR"))
                            {
                                String inv = jb.get(url_pos_invoice_details_invoice_id).toString();
                                if(inv.split("-")[1].equals("W"))
                                {
                                    //final PosInvoiceHeadWeb posInvoiceHead = new PosInvoiceHeadWeb();
                                    String invoice_id = inv.split("-")[2];
                                    PosInvoiceRegularWeb invoice = new PosInvoiceRegularWeb();
                                    invoice.setInvoiceID(TypeConvertion.parseInt(invoice_id));
                                    invoice.setProductName(jb.get(url_pos_invoice_details_product_name).toString());
                                    try {
                                        DebugHelper.print(invoice);
                                        String con = db1.COLUMN_invoice_product_names + "='" + invoice.getProductName() + "'";
                                        InvoiceProduct cProduct = InvoiceProduct.select(db1, con).get(0);
                                        invoice.setProductId(cProduct.getId());
                                    }catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                    invoice.setUnitPrice(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_unit_price).toString()));
                                    invoice.setQuantity(TypeConvertion.parseInt(jb.get(url_pos_invoice_details_quantity).toString()));
                                    invoice.setTax(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_tax).toString()));
                                    invoice.setDiscount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_discount).toString()));
                                    invoice.setStatus(2);
                                    invoice.insert(db1);
                                }

                            }
                            else if(sale_type.toUpperCase().equals("POS")) {
                                String inv = jb.get(url_pos_invoice_details_invoice_id).toString();
                                inv = inv.replace(cSystemInfo.getSelected_pos() + "-", "");
                                if (inv.charAt(0) == 'W') {
                                    //final PosInvoiceHeadWeb posInvoiceHead = new PosInvoiceHeadWeb();
                                    String invoice_id = jb.get(url_pos_invoice_details_invoice_id).toString().replace(cSystemInfo.getSelected_pos() + "-W-", "");
                                    PosInvoiceWeb invoice = new PosInvoiceWeb();
                                    invoice.setInvoiceID(TypeConvertion.parseInt(invoice_id));
                                    invoice.setProductName(jb.get(url_pos_invoice_details_product_name).toString());
                                    String con = db1.COLUMN_pos_product_name + "='" + invoice.getProductName() + "'";
                                    PosProduct cProduct = PosProduct.select(db1, con).get(0);
                                    invoice.setProductId(cProduct.getId());
                                    invoice.setUnitPrice(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_unit_price).toString()));
                                    invoice.setQuantity(TypeConvertion.parseInt(jb.get(url_pos_invoice_details_quantity).toString()));
                                    invoice.setTax(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_tax).toString()));
                                    invoice.setDiscount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_discount).toString()));
                                    invoice.setStatus(2);
                                    invoice.insert(db1);
                                } else {
                                    //final PosInvoiceHead posInvoiceHead = new PosInvoiceHead();
                                    String invoice_id = jb.get(url_pos_invoice_details_invoice_id).toString().replace(cSystemInfo.getSelected_pos() + "-P-", "");
                                    PosInvoice invoice = new PosInvoice();
                                    invoice.setInvoiceID(TypeConvertion.parseInt(invoice_id));
                                    invoice.setProductName(jb.get(url_pos_invoice_details_product_name).toString());
                                    String con = db1.COLUMN_pos_product_name + "='" + invoice.getProductName() + "'";
                                    PosProduct cProduct = PosProduct.select(db1, con).get(0);
                                    invoice.setProductId(cProduct.getId());
                                    invoice.setUnitPrice(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_unit_price).toString()));
                                    invoice.setQuantity(TypeConvertion.parseInt(jb.get(url_pos_invoice_details_quantity).toString()));
                                    invoice.setTax(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_tax).toString()));
                                    invoice.setDiscount(TypeConvertion.parseDouble(jb.get(url_pos_invoice_details_discount).toString()));
                                    invoice.setStatus(2);
                                    invoice.insert(db1);
                                }
                            }
                        }catch (Exception e){}

                    }
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        BaseDataService m = new BaseDataService();
                        m.url = url_goods_transfer_download;
                        GoodsTransferAPI mv = new GoodsTransferAPI();
                        m.outputToModel = mv;
                        m.context = context;
                        m.parameterdataClass = cSystemInfo;
                        m.outputModelVariableMap.put("transfer_no","transfer_no");
                        m.outputModelVariableMap.put("product_name","item");
                        m.outputModelVariableMap.put("quantity","qty");
                        m.outputModelVariableMap.put("billed_qty","billed_qty");
                        m.outputModelVariableMap.put("received_qty","receive_qty");
                        m.outputModelVariableMap.put("sales_price","sales_price");
                        m.outputModelVariableMap.put("transfer_by","from_location");
                        m.outputModelVariableMap.put("transfer_to","to_location");
                        m.outputModelVariableMap.put("transfer_to","to_location");
                        m.outputModelVariableMap.put("entry_by","entry_by");
                        m.outputModelVariableMap.put("date_time","entry_time");
                        m.outputModelVariableMap.put("pos_by","pos_by");
                        m.getDataFromURLModel(context,"goods_transfer_download_output");
                    }
                });
            }
        }).start();
    }



    public void goods_transfer_download_output(final ArrayList<?> data,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    ArrayList<StockTransferHead> stockTransferHeads = new ArrayList<>();
                    ArrayList<StockTransferDetails> stockTransferDetails = new ArrayList<>();
                    ArrayList<StockTransferAPI> stockTransferAPIS = new ArrayList<>();
                    ArrayList<String> headIdList = new ArrayList<>();
                    StockTransferHead.delete(db1,"1=1");
                    StockTransferDetails.delete(db1,"1=1");
                    int cTID=0;
                    for(Object ob : data)
                    {
                        GoodsTransferAPI cg = (GoodsTransferAPI) ob;
                        DebugHelper.print(cg);
                        try
                        {
                            ///-------------check
                            if(!headIdList.contains(cg.transfer_no.trim()))
                            {
                               StockTransferHead sh = new StockTransferHead();
                               sh.transfer_by = cg.transfer_by;
                               sh.transfer_to = cg.transfer_to;
                               sh.status = cg.status;
                               sh.date_time = cg.date_time;
                               sh.entry_by = cg.entry_by;
                               sh.pos_by = cg.pos_by;
                               try {
                                   String new_id = cg.transfer_no.split("-")[cg.transfer_no.split("-").length - 1];
                                   sh.id = TypeConvertion.parseInt(new_id);
                               }catch (Exception e){}
                               if(!sh.entry_by.equals(""))
                                {
                                    sh.insert(db1);
                                }
                                if(sh.id>0)
                                {
                                    cTID = sh.id;
                                }
                                else
                                {
                                    cTID = StockTransferHead.getMax(db1);
                                }
                               headIdList.add(cg.transfer_no.trim());
                            }

                            StockTransferDetails d = new StockTransferDetails();
                            d.transfer_id = cTID;
                            try {
                                d.product_id = PosProduct.select(db, db.COLUMN_pos_product_name + "='" + cg.product_name + "'").get(0).getId();
                            }
                            catch (Exception e){}
                            d.product_name = cg.product_name;
                            d.quantity = cg.quantity;
                            d.unit_price = cg.sales_price / cg.quantity;
                            d.status = cg.status;
                            d.date_time = cg.date_time;
                            d.received_qty = cg.receive_qty;
                            d.delivered_qty = cg.billed_qty;
                            StockTransferHead sh = StockTransferHead.select(db1,db1.COLUMN_stock_transfer_id+"="+cTID).get(0);
                            //sh.setId(cTID);
                            //sh.transfer_by = cg.transfer_by;
                            //sh.transfer_to = cg.transfer_to;
                            //sh.status = cg.status;
                            //sh.date_time = cg.date_time;
                            //sh.entry_by = cg.entry_by;
                            sh.setTotal_quantity(sh.getTotal_quantity()+cg.quantity);
                            sh.setTotal_delivered_qty(sh.getTotal_delivered_qty()+cg.billed_qty);
                            sh.setTotal_received_qty(sh.getTotal_received_qty()+cg.receive_qty);
                            sh.setTotal_amount(sh.getTotal_amount()+cg.sales_price);
                            if(!sh.entry_by.equals(""))
                            {
                                sh.update(db1);
                            }
                            d.insert(db1);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        BaseDataService m = new BaseDataService();
                        m.url = url_cash_statment_download;
                        m.outputToModel = new CashStatment();
                        m.context = context;
                        m.parameterdataClass = cSystemInfo;
                        m.getDataFromURLModel(context,"cash_statment_download");
                    }
                });
            }
        }).start();
    }
    public void cash_statment_download(final ArrayList<?> data,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    for(Object ob : data)
                    {
                        ((CashStatment) ob).insert(db1);
                    }
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        BaseDataService m = new BaseDataService();
                        m.url = url_discount_list;
                        m.outputToModel = new Discount();
                        m.context = context;
                        m.parameterdataClass = cSystemInfo;
                        m.getDataFromURLModel(context,"discount_download");
                    }
                });
            }
        }).start();
    }

    public void discount_download(final ArrayList<?> data,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    for(Object ob : data)
                    {
                        ((Discount) ob).insert(db1);
                    }
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        BaseDataService m = new BaseDataService();
                        m.url = url_cash_cu_balance;
                        GoodsTransferAPI mv = new GoodsTransferAPI();
                        m.outputToModel = cSystemInfo;
                        m.context = context;
                        m.parameterdataClass = cSystemInfo;
                        m.getDataFromURLModel(context,"cash_cu_balance_download_output");
                    }
                });
            }
        }).start();
    }
    public void cash_cu_balance_download_output(final ArrayList<?> data,final Context context)
    {
        new Thread(new Runnable()
        {
            public void run() {
                try
                {
                    for(Object ob : data)
                    {
                        try {
                            cSystemInfo.setCash_cu_balance(((User) ob).getCash_cu_balance());
                            cSystemInfo.update(db1);
                        }catch (Exception e){}
                    }
                }
                catch (Exception e){System.out.println(e.toString());}
                wait_text.post(new Runnable() {
                    public void run() {
                        cSystemInfo.setStatus(1);
                        cSystemInfo.update(db1);
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
        }).start();
    }

    public int getTotalTransferQuantity(final String id,final ArrayList<StockTransferAPI> stockTransferAPIS)
    {
        int count=0;
        for(StockTransferAPI stockTransferAPI : stockTransferAPIS)
        {
            String headId = stockTransferAPI.transfer_no.split("-")[stockTransferAPI.transfer_no.split("-").length-1];
            if(headId.equals(id))
            {
                count+= TypeConvertion.parseInt(stockTransferAPI.qty);
            }
        }
        return count;
    }
    public double getTotalTransferAmount(final String id,final ArrayList<StockTransferAPI> stockTransferAPIS)
    {
        double amount=0;
        for(StockTransferAPI stockTransferAPI : stockTransferAPIS)
        {
            String headId = stockTransferAPI.transfer_no.split("-")[stockTransferAPI.transfer_no.split("-").length-1];
            if(headId.equals(id))
            {
                amount+= TypeConvertion.parseDouble(stockTransferAPI.sales_price);
            }
        }
        return amount;
    }
    public String getTotalTransferDate_time(final String id,final ArrayList<StockTransferAPI> stockTransferAPIS)
    {
        for(StockTransferAPI stockTransferAPI : stockTransferAPIS)
        {
            String headId = stockTransferAPI.transfer_no.split("-")[stockTransferAPI.transfer_no.split("-").length-1];
            if(headId.equals(id))
            {
                return stockTransferAPI.entry_time;
            }
        }
        return "";
    }

    public String getTotalTransferEntryBy(final String id,final ArrayList<StockTransferAPI> stockTransferAPIS)
    {
        for(StockTransferAPI stockTransferAPI : stockTransferAPIS)
        {
            String headId = stockTransferAPI.transfer_no.split("-")[stockTransferAPI.transfer_no.split("-").length-1];
            if(headId.equals(id))
            {
                return stockTransferAPI.entry_time;
            }
        }
        return "";
    }

    public String getUserDetails()
    {
        String username = Uri.encode(cSystemInfo.getUser_name());
        String password = Uri.encode(cSystemInfo.getPassword());
        String company = Uri.encode(cSystemInfo.getCompany_id());
        String location = Uri.encode(cSystemInfo.getSelected_location());
        String pos = Uri.encode(cSystemInfo.getSelected_pos());
        return  "user_name="+username+"&password="+password+"&company="+company+"&"+"&location="+location+"&pos="+pos+"&";
    }

}
