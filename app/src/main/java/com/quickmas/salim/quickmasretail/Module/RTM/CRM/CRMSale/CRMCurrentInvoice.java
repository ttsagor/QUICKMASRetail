package com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMSale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoice;
import com.quickmas.salim.quickmasretail.Model.RTM.CRMInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.FreeProductInvoiceHead;
import com.quickmas.salim.quickmasretail.Model.RTM.Outlet;
import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.DashboardSubMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.Dashboard.MainActivity;
import com.quickmas.salim.quickmasretail.Module.RTM.CRM.CRMList.CRMList;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Structure.Company;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Model.System.TextString.textSelectByVarname;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFileBase;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.setTextFont;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;

public class CRMCurrentInvoice extends AppCompatActivity {
    DBInitialization db;
    int amountCount=0;
    int uPriceCount=0;
    int totalPriceCount=0;
    Context mContex;
    User cSystemInfo = new User();
    ArrayList<Uri> image_list = new ArrayList<>();
    ArrayList<CRMInvoice> invoices = new ArrayList<>();
    Outlet cOutlet = new Outlet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_crmcurrent_invoice);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_free_product_current_invoice);
        //setContentView(R.layout.activity_free_product_current_invoice);
        mContex = this;
        db = new DBInitialization(this,null,null,1);
        DebugHelper.print(invoices);
        DebugHelper.print(FreeProductInvoiceHead.select(db,"1=1"));
        cSystemInfo.select(db,"1=1");

        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_rtm");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText(DashboardSubMenu.getMenuText(db, "rtm_activity_3"));

        TextView sell_invoice_date_txt = (TextView) findViewById(R.id.sell_invoice_date_txt);
        final TextView sell_invoice_date = (TextView) findViewById(R.id.sell_invoice_date);
        TextView sell_invoice_invoice_txt = (TextView) findViewById(R.id.sell_invoice_invoice_txt);
        TextView sell_invoice_invoice = (TextView) findViewById(R.id.sell_invoice_invoice);
        TextView sell_invoice_outlet_txt = (TextView) findViewById(R.id.sell_invoice_outlet_txt);
        TextView sell_invoice_outlet = (TextView) findViewById(R.id.sell_invoice_outlet);
        TextView sku = (TextView) findViewById(R.id.sku);
        TextView qnty = (TextView) findViewById(R.id.qnty);
        //TextView uprice = (TextView) findViewById(R.id.uprice);
        //TextView amount = (TextView) findViewById(R.id.amount);
        Button sell_invoice_proceed = (Button) findViewById(R.id.sell_invoice_proceed);
        Button sell_invoice_add_new = (Button) findViewById(R.id.sell_invoice_add_new);
        Button sell_invoice_discard = (Button) findViewById(R.id.sell_invoice_discard);
        Button sell_invoice_add_photo = (Button) findViewById(R.id.sell_invoice_add_photo);

        ListView invoice_list = (ListView) findViewById(R.id.invoice_list);

        sell_invoice_date_txt = setTextFont(sell_invoice_date_txt, this,db,"sellInvoice_sell_date");

        sell_invoice_invoice_txt = setTextFont(sell_invoice_invoice_txt, this,db,"sellInvoice_sell_invoice");
        sell_invoice_outlet_txt = setTextFont(sell_invoice_outlet_txt, this,db,"sellInvoice_sell_outlet");
        sku = setTextFont(sku, this,db,"sellInvoice_sell_sku");
        qnty = setTextFont(qnty, this,db,"Comment");
        //uprice = setTextFont(uprice, this,db,"sellInvoice_sell_uprice");
        //amount = setTextFont(amount, this,db,"sellInvoice_sell_amount");
        sell_invoice_proceed = setTextFont(sell_invoice_proceed, this,db,"sellInvoice_invoice_proceed");
        sell_invoice_add_new = setTextFont(sell_invoice_add_new, this,db,"sellInvoice_invoice_add_new");
        sell_invoice_discard = setTextFont(sell_invoice_discard, this,db,"sellInvoice_sell_invoice_discard");
        sell_invoice_add_photo = setTextFont(sell_invoice_add_photo, this,db,"Add Photo");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        String currentDate = DateFormat.format("dd-MM-yyyy", currentTimestamp).toString();

        sell_invoice_date.setTypeface(getFont(this));
        sell_invoice_date.setText(currentDate);

        //DebugHelper.print(CRMInvoiceHead.select(db,"1=1"));
        //DebugHelper.print(CRMInvoice.select(db,"1=1"));

        invoices = CRMInvoice.getPendingInvoices(db);

        CRMInvoiceHead crmInvoiceHead = CRMInvoiceHead.getPendingInvoices(db).get(0);


        cOutlet = Outlet.select(db,db.COLUMN_o_id+"="+crmInvoiceHead.getOutlet()).get(0);
        //final String outlet = db.getOutlet(db.COLUMN_o_id+"="+invoices.get(0).getOutlet_id()).get(0).getOutlet_id();
        String invoice = invoices.get(0).getPrefix() + String.valueOf(invoices.get(0).getInvoice_id());

        sell_invoice_outlet = setTextFont(sell_invoice_outlet, this, cOutlet.getOutlet_id());
        sell_invoice_invoice = setTextFont(sell_invoice_invoice, this, invoice);


        ArrayList<Product> products = new ArrayList<Product>();

        for(CRMInvoice cInvoice : invoices)
        {
            Product product = Product.select(db,db.COLUMN_product_id+"="+cInvoice.getProduct_id()).get(0);
            product.setSub_sku(cInvoice.getComment());
            //product.setSku_price(cInvoice.getUnit_price());
            //amountCount += cInvoice.getProduct_quantity();
            //uPriceCount +=cInvoice.getUnit_price();
            //totalPriceCount+= (cInvoice.getQuantity()*cInvoice.getUnit_price());
            products.add(product);
        }


        ArrayList<Company> allCompany = Company.getCompanyStruct(products);

        ScrollListView.loadListView(mContex, invoice_list, R.layout.sell_invoice_company, allCompany, "showList", 0, allCompany.size(), false);

        LayoutInflater layoutinflater = getLayoutInflater();
        ViewGroup footer = (ViewGroup)layoutinflater.inflate(R.layout.adaptar_free_invoice_company,invoice_list,false);

        TextView grand_total = (TextView) footer.findViewById(R.id.sku);
        TextView total_qnty = (TextView) footer.findViewById(R.id.qnty);
        //TextView total_sell = (TextView) footer.findViewById(R.id.total_sell);
        //TextView total_closing = (TextView) footer.findViewById(R.id.total_closing);
        grand_total.setTypeface(getFont(this));
        total_qnty.setTypeface(getFont(this));
        //total_sell.setTypeface(getFont(this));
        // total_closing.setTypeface(getFont(this));


        grand_total = setTextFont(grand_total, this,  db, "");
        total_qnty = setTextFont(total_qnty,this,"");
        //total_sell = setTextFont(total_sell,this,String.valueOf(""));
        // total_closing= setTextFont(total_closing,this,String.valueOf(totalPriceCount));
        invoice_list.addFooterView(footer);


        sell_invoice_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image_list.size() >= cSystemInfo.getRtm_photo_limit())
                {
                    Toasty.error(getApplicationContext(),"Cannot Add More Image", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                try
                {
                    CropImage.activity()
                            .setAspectRatio(1000, 1000)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start((Activity) mContex);
                }
                catch (Exception e)
                {
                    Toasty.error(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT, true).show();
                }

            }
        });

        sell_invoice_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( CRMCurrentInvoice.this ,CRMSale.class);
                i.putExtra("invoiceOutlet", String.valueOf(cOutlet.getId()));
                startActivity(i);
            }
        });
        sell_invoice_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                final ProgressDialog progressDoalog = new ProgressDialog(CRMCurrentInvoice.this);

                                try {
                                    progressDoalog.setMessage("Data Saving....");
                                    progressDoalog.setTitle("Please Wait");
                                    progressDoalog.show();

                                }catch (Exception e){}
                                new Thread(new Runnable() {
                                    public void run() {
                                        saveData();
                                        try {
                                            Thread.sleep(3000);
                                        }catch (Exception e){}
                                        sell_invoice_date.post(new Runnable() {
                                            public void run() {
                                                progressDoalog.dismiss();
                                                Toasty.success(getApplicationContext(), TextString.textSelectByVarname(db,"Product Entry Sucessful").getText(), Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CRMCurrentInvoice.this,CRMList.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                    }
                                }).start();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                final DialogInterface.OnClickListener dialogClickListenerPhoto = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    CropImage.activity()
                                            .setAspectRatio(1000, 1000)
                                            .setGuidelines(CropImageView.Guidelines.ON)
                                            .start((Activity) mContex);
                                } catch (Exception e) {
                                    Toasty.error(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT, true).show();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                                        .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                                break;
                        }
                    }
                };



                if(image_list.size()==0)
                {
                    builder.setMessage(TextString.textSelectByVarname(db,"Do you want to take photo").getText()).setPositiveButton(TextString.textSelectByVarname(db,"Add Photo Now!!").getText(), dialogClickListenerPhoto)
                            .setNegativeButton(TextString.textSelectByVarname(db,"No").getText(), dialogClickListenerPhoto).show();
                }
                else
                {
                    builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                            .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
                }

            }
        });

        sell_invoice_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CRMCurrentInvoice.this);

                builder.setTitle(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_title").getText());
                builder.setMessage(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_text").getText());

                builder.setPositiveButton(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_yes").getText(), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        CRMInvoice.deletePendingInvoices(db);
                        CRMInvoiceHead.deletePendingInvoices(db);
                        Intent intent = new Intent(CRMCurrentInvoice.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                });

                builder.setNegativeButton(textSelectByVarname(db,"sellInvoice_sell_invoice_dialog_no").getText(), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    void saveData()
    {
        if(invoices.size()>0)
        {
            String img_url="";
            for (int i = 0; i < image_list.size(); i++) {
                try
                {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_list.get(i));
                    } catch (Exception e) {
                        bitmap = getImageFromFileBase(image_list.get(i).toString(), mContex);
                    }
                    String fileName = "free-rtm-" + invoices.get(0).getInvoice_id() + "-" + i + "_" + DateTimeCalculation.getCurrentTimeStamp();
                    System.out.println(image_list.get(i) + "---" + fileName);
                    String root = Environment.getExternalStorageDirectory().toString() + "/" + FileManagerSetting.folder_name + "/" + FileManagerSetting.rtm_crm_product + "/" + fileName;
                    if (!FileManagerSetting.saveImageToLocation(mContex, bitmap, root)) {
                        System.out.println("Problem Saving Photo- " + image_list.get(i).toString());
                    } else {
                        img_url += fileName + ".png;";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            int quantity = 0;
            for(CRMInvoice invoice : invoices)
            {
                //Product cProduct = Product.select(db,db.COLUMN_product_id+"="+invoice.getProduct_id()).get(0);
                //cProduct.setSku_qty(cProduct.getSku_qty()-invoice.getProduct_quantity());
                //cProduct.setSold_sku(cProduct.getSold_sku()+invoice.getProduct_quantity());
                //cProduct.update(db);

                invoice.setInvoice_by(cSystemInfo.getUser_name());
                invoice.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
                invoice.setStatus(1);
                invoice.insert(db);
                quantity++;
            }

            CRMInvoiceHead invoiceHead = CRMInvoiceHead.getPendingInvoices(db).get(0);
            invoiceHead.setInvoice_by(cSystemInfo.getUser_name());
            invoiceHead.setInvoice_date(DateTimeCalculation.getCurrentDateTime());
            invoiceHead.setPhoto(img_url);
            invoiceHead.setQuantity(quantity);
            invoiceHead.setOutlet(cOutlet.getOutlet_name());
            invoiceHead.setStatus(1);
            invoiceHead.insert(db);

            //DebugHelper.print(invoices);
            //DebugHelper.print(FreeProductInvoiceHead.select(db,"1=1"));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                image_list.add(resultUri);
                createImageList();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toasty.error(mContex,"Problem Selecting Photo");
            }
        }
    }

    void createImageList()
    {
        final LinearLayout image_list_holder = findViewById(R.id.image_list_holder);
        if(((LinearLayout) image_list_holder).getChildCount() > 0)
        {
            ((LinearLayout) image_list_holder).removeAllViews();
        }

        int lim = image_list.size()/2;
        double div = image_list.size()%2;
        if(div>0)
        {
            lim++;
        }

        for(int i=0;i<lim;i++)
        {
            final int img1c = i * 2;
            final int img2c = i * 2 + 1;

            LayoutInflater inflater = LayoutInflater.from(mContex);
            ViewGroup product = (ViewGroup) inflater.inflate(R.layout.retail_new_product_image_list, image_list_holder, false);

            if(image_list.size()>img1c)
            {
                final Uri img1 = image_list.get(img1c);
                ImageView myImageView1  = (ImageView) product.findViewById(R.id.myImageView1);
                ImageView close_btn1  = (ImageView) product.findViewById(R.id.close_btn1);
                close_btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image_list.remove(img1);
                        createImageList();
                        return;
                    }
                });
                myImageView1.setImageURI(img1);

                myImageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater) mContex
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                        final View popupView = layoutInflater.inflate(R.layout.pop_up_free_product_image_list_product_view, null);
                        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
                        final ImageView image = (ImageView) popupView.findViewById(R.id.image);
                        close_tab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        image.setImageURI(img1);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupWindow.showAtLocation(image_list_holder, Gravity.CENTER, 0, 0);

                    }
                });
            }
            else
            {
                LinearLayout product1  = (LinearLayout) product.findViewById(R.id.product1);
                product1.setVisibility(View.GONE);
            }
            if(image_list.size()>img2c)
            {
                final Uri img2 = image_list.get(img2c);
                ImageView myImageView2  = (ImageView) product.findViewById(R.id.myImageView2);
                ImageView close_btn2  = (ImageView) product.findViewById(R.id.close_btn2);
                close_btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image_list.remove(img2);
                        createImageList();
                        return;
                    }
                });
                myImageView2.setImageURI(img2);

                myImageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater) mContex
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                        final View popupView = layoutInflater.inflate(R.layout.pop_up_free_product_image_list_product_view, null);
                        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);
                        final ImageView image = (ImageView) popupView.findViewById(R.id.image);
                        close_tab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        image.setImageURI(img2);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupWindow.showAtLocation(image_list_holder, Gravity.CENTER, 0, 0);

                    }
                });
            }
            else
            {
                LinearLayout product1  = (LinearLayout) product.findViewById(R.id.product2);
                product1.setVisibility(View.GONE);
            }
            image_list_holder.addView(product);
        }
    }

    public void showList(ViewData data)
    {
        Company company = (Company) data.object;
        TextView company_name  = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.company_name),mContex,company.getCompany_name());
        ListView product_list  = (ListView) data.view.findViewById(R.id.product_list);
        ScrollListView.loadListView(mContex, product_list, R.layout.adaptar_free_invoice_company, company.getCrm(), "showProduct", 0, company.getCrm().size(), false);
    }

    public void showProduct(ViewData data)
    {
        Product product = (Product) data.object;
        int qnty = product.getSku_qty();
        double uprice = product.getSku_price();
        double totalPrice = qnty*uprice;


        TextView sku = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.sku),mContex,product.getSku());
        TextView qntyT = FontSettings.setTextFont((TextView) data.view.findViewById(R.id.qnty),mContex,String.valueOf(product.getSub_sku()));

    }

}
