package com.quickmas.salim.quickmasretail.Module.AddProductRetail.AddProduct;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.MultiSpinner;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.quickmas.salim.quickmasretail.Database.DBInitialization;
import com.quickmas.salim.quickmasretail.Model.POS.MarketPlace;
import com.quickmas.salim.quickmasretail.Model.POS.NewRetailProduct;
import com.quickmas.salim.quickmasretail.Model.POS.NewRetailProductEntryHead;
import com.quickmas.salim.quickmasretail.Model.POS.ProductAddCategory;
import com.quickmas.salim.quickmasretail.Model.POS.RetailImageGroup;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductBrand;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductManufacture;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductSalesTaxGroup;
import com.quickmas.salim.quickmasretail.Model.POS.RetailProductTaxGroup;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferDetails;
import com.quickmas.salim.quickmasretail.Model.POS.StockTransferHead;
import com.quickmas.salim.quickmasretail.Model.System.DashboardMenu;
import com.quickmas.salim.quickmasretail.Model.System.TextString;
import com.quickmas.salim.quickmasretail.Model.System.User;
import com.quickmas.salim.quickmasretail.Module.AddProductRetail.ProductList.ProductList;
import com.quickmas.salim.quickmasretail.R;
import com.quickmas.salim.quickmasretail.Service.UploadData;
import com.quickmas.salim.quickmasretail.Structure.GoodsTransferAPI;
import com.quickmas.salim.quickmasretail.Utility.DateTimeCalculation;
import com.quickmas.salim.quickmasretail.Utility.FileManagerSetting;
import com.quickmas.salim.quickmasretail.Utility.FontSettings;
import com.quickmas.salim.quickmasretail.Utility.FormDataValidation;
import com.quickmas.salim.quickmasretail.Utility.TypeConvertion;
import com.quickmas.salim.quickmasretail.Utility.UI.ScrollListView;
import com.quickmas.salim.quickmasretail.Utility.UI.ViewData;
import com.quickmas.salim.quickmasretail.Utility.UIComponent;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.quickmas.salim.quickmasretail.Model.POS.PosInvoice.updateInvoiceStatus;
import static com.quickmas.salim.quickmasretail.Model.POS.ProductAddCategory.getListData;
import static com.quickmas.salim.quickmasretail.Model.POS.ProductAddCategory.getSubCategory;
import static com.quickmas.salim.quickmasretail.Model.POS.RetailImageGroup.getImageGroup;
import static com.quickmas.salim.quickmasretail.Model.System.DashboardMenu.selectByVarname;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_retail_product_upload;
import static com.quickmas.salim.quickmasretail.Service.ApiSettings.url_stock_transfer_upload;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFile;
import static com.quickmas.salim.quickmasretail.Utility.FileManagerSetting.getImageFromFileBase;
import static com.quickmas.salim.quickmasretail.Utility.FontSettings.getFont;
import static com.quickmas.salim.quickmasretail.Utility.NetworkConfiguration.isInternetOn;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.hideSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.setLinearLayoutBackground;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.showSoftKeyboard;
import static com.quickmas.salim.quickmasretail.Utility.UIComponent.spinnerDataLoad;

public class AddProduct extends AppCompatActivity {
    DBInitialization db; Context mContex;
    Button btn_product_info;
    ArrayList<NewRetailProduct> newRetailProduct = new ArrayList<>();
    EditText product_main;
    EditText product_info;
    EditText product_code;
    EditText product_size;
    Button product_btn_update;
    int skuAddUpdatePosition=-1;
    String pre_sub_category = "";
    ProgressDialog progressDoalog;
    ArrayList<Uri> image_list = new ArrayList<>();
    int currentEntryId=0;
    //LifeCycleCallBackManager lifeCycleCallBackManager;
    int in_entry_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("add-product-create-product-sku"));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_add_product);
        mContex = this;
        db = new DBInitialization(mContex,null,null,1);
        DashboardMenu cMenu =  selectByVarname(db,"dashboard_sell_pos_list");
        LinearLayout headimg = (LinearLayout) findViewById(R.id.image1);
        headimg = setLinearLayoutBackground(headimg, cMenu.getImage(), this);
        TextView headtext = (TextView) findViewById(R.id.text1);
        headtext.setTypeface(getFont(this));
        headtext.setText("Add Product");




        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            in_entry_id = TypeConvertion.parseInt((String) b.get("entry_id"));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btn_product_info = FontSettings.setTextFont((Button) findViewById(R.id.btn_product_info),mContex,"Product Info");
        final Button btn_add_image = FontSettings.setTextFont((Button) findViewById(R.id.btn_add_image),mContex,"Add Image");
        final Button btn_supplier = FontSettings.setTextFont((Button) findViewById(R.id.btn_supplier),mContex,"Pricing");

        final Spinner category_spinner = (Spinner) findViewById(R.id.category_spinner);
        final Spinner subcategory_spinner = (Spinner) findViewById(R.id.subcategory_spinner);

        final Spinner manufacture_spinner = (Spinner) findViewById(R.id.manufacture_spinner);
        final Spinner brand_spinner = (Spinner) findViewById(R.id.brand_spinner);
        final Spinner image_group_spinner = (Spinner) findViewById(R.id.image_group_spinner);
        final Spinner purchase_group_spinner = (Spinner) findViewById(R.id.purchase_group_spinner);
        final Spinner sales_group_spinner = (Spinner) findViewById(R.id.sales_group_spinner);

        ArrayList<String> categoryList = ProductAddCategory.getCategory(db,"No Category");
        categoryList.add(0,"Select Iteam");
        categoryList.add("Add New");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContex,R.layout.spinner_item,categoryList){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(tv.getText().toString().equals("Add New")) {
                    tv.setBackgroundColor(Color.parseColor("#00a81e"));
                }
                else {
                    tv.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                return view;
            }
            @Override
            public boolean isEnabled(int position) {
                return true;
            }
        };
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        category_spinner.setAdapter(dataAdapter);


        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==category_spinner.getCount()-1)
                {
                    ProductAddCategory productAddCategory = new ProductAddCategory();
                    productAddCategory.setCategory("Product Class");
                    upAddPopUp(productAddCategory,category_spinner,db.COLUMN_product_add_category_category+"='Product Class'","No Category","Add New");
                }
                else
                {
                    ArrayList<String> subCategory = getSubCategory(db, category_spinner.getSelectedItem().toString(), "No Sub Category");
                    subCategory.add(0,"Select Iteam");
                    if (!category_spinner.getSelectedItem().toString().equals("No Category") && position!=0)
                    {
                        subCategory.add("Add New");
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContex, R.layout.spinner_item, subCategory) {
                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (tv.getText().toString().equals("Add New")) {
                                tv.setBackgroundColor(Color.parseColor("#00a81e"));
                            } else {
                                tv.setBackgroundColor(Color.parseColor("#ffffff"));
                            }
                            return view;
                        }

                        @Override
                        public boolean isEnabled(int position) {
                            return true;
                        }
                    };
                    dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                    subcategory_spinner.setAdapter(dataAdapter);
                    setSeletedIteamSpinner(subcategory_spinner,pre_sub_category);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        updateSpinner("Add Manufacture",manufacture_spinner,"",0,0,"manufacture");
        updateSpinner("Add Brand",brand_spinner,"",0,0,"brand");
        updateSpinner("Add Image Group",image_group_spinner,"",0,0,"image_group");
        updateSpinner("Add Purchase Group",purchase_group_spinner,"",0,0,"purchase_group");
        updateSpinner("Add Sales Group",sales_group_spinner,"",0,0,"sales_group");

        final ArrayList<String> market_places = MarketPlace.getAllMarketPlace(db);
        final MultiSpinner simpleSpinner = (MultiSpinner) findViewById(R.id.market_place);

        LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<String, Boolean>();
       // linkedHashMap.put("All", true);
        for(String market : market_places)
        {
            linkedHashMap.put(market, true);
        }

        simpleSpinner.setItems(linkedHashMap, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {

            }

        });


        final LinearLayout product_info_holder = (LinearLayout) findViewById(R.id.product_info_holder);
        final LinearLayout image_holder = (LinearLayout) findViewById(R.id.image_holder);
        final LinearLayout pricing_holder = (LinearLayout) findViewById(R.id.pricing_holder);
        final Button product_info_next = (Button) findViewById(R.id.product_info_next);
        final Button add_image_next = (Button) findViewById(R.id.add_image_next);
        final Button pick_photo_camera = (Button) findViewById(R.id.pick_photo_camera);

        pick_photo_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    RetailImageGroup retailImageGroup = getImageGroup(db, image_group_spinner.getSelectedItem().toString());
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(retailImageGroup.getHeight(), retailImageGroup.getWidth())
                            .start((Activity) mContex);
                }
                catch (Exception e)
                {
                  Toasty.error(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT, true).show();
                }
                //FilePickUtils filePickUtils = new FilePickUtils((Activity) mContex,onFileChoose);
                //filePickUtils.requestImageCamera(CAMERA_PERMISSION, false, true);
                //lifeCycleCallBackManager = filePickUtils.getCallBackManager();
            }
        });


        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_product_info.setTextColor(Color.parseColor("#00a81e"));
                btn_add_image.setTextColor(Color.parseColor("#ffffff"));
                btn_supplier.setTextColor(Color.parseColor("#00a81e"));

                btn_product_info.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_add_image.setBackgroundColor(Color.parseColor("#00a81e"));
                btn_supplier.setBackgroundColor(Color.parseColor("#ffffff"));

                pricing_holder.setVisibility(View.GONE);
                product_info_holder.setVisibility(View.GONE);
                image_holder.setVisibility(View.VISIBLE);
                add_image_next.setVisibility(View.VISIBLE);


            }
        });

        product_info_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_product_info.setTextColor(Color.parseColor("#00a81e"));
                btn_add_image.setTextColor(Color.parseColor("#ffffff"));
                btn_supplier.setTextColor(Color.parseColor("#00a81e"));

                btn_product_info.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_add_image.setBackgroundColor(Color.parseColor("#00a81e"));
                btn_supplier.setBackgroundColor(Color.parseColor("#ffffff"));
                pricing_holder.setVisibility(View.GONE);
                product_info_holder.setVisibility(View.GONE);
                image_holder.setVisibility(View.VISIBLE);
                add_image_next.setVisibility(View.VISIBLE);

            }
        });

        btn_product_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_product_info.setTextColor(Color.parseColor("#ffffff"));
                btn_add_image.setTextColor(Color.parseColor("#00a81e"));
                btn_supplier.setTextColor(Color.parseColor("#00a81e"));

                btn_product_info.setBackgroundColor(Color.parseColor("#00a81e"));
                btn_add_image.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_supplier.setBackgroundColor(Color.parseColor("#ffffff"));

                product_info_holder.setVisibility(View.VISIBLE);
                pricing_holder.setVisibility(View.GONE);
                image_holder.setVisibility(View.GONE);
                add_image_next.setVisibility(View.GONE);

            }
        });

        btn_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_product_info.setTextColor(Color.parseColor("#00a81e"));
                btn_add_image.setTextColor(Color.parseColor("#00a81e"));
                btn_supplier.setTextColor(Color.parseColor("#ffffff"));

                btn_product_info.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_add_image.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_supplier.setBackgroundColor(Color.parseColor("#00a81e"));

                product_info_holder.setVisibility(View.GONE);
                pricing_holder.setVisibility(View.VISIBLE);

                image_holder.setVisibility(View.GONE);
                add_image_next.setVisibility(View.GONE);
            }
        });

        add_image_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_product_info.setTextColor(Color.parseColor("#00a81e"));
                btn_add_image.setTextColor(Color.parseColor("#00a81e"));
                btn_supplier.setTextColor(Color.parseColor("#ffffff"));

                btn_product_info.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_add_image.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_supplier.setBackgroundColor(Color.parseColor("#00a81e"));

                pricing_holder.setVisibility(View.VISIBLE);

                product_info_holder.setVisibility(View.GONE);
                image_holder.setVisibility(View.GONE);
                add_image_next.setVisibility(View.GONE);
            }
        });

        btn_product_info.setTextColor(Color.parseColor("#ffffff"));
        btn_add_image.setTextColor(Color.parseColor("#00a81e"));
        btn_supplier.setTextColor(Color.parseColor("#00a81e"));

        btn_product_info.setBackgroundColor(Color.parseColor("#00a81e"));
        btn_add_image.setBackgroundColor(Color.parseColor("#ffffff"));
        btn_supplier.setBackgroundColor(Color.parseColor("#ffffff"));
        image_holder.setVisibility(View.GONE);
        add_image_next.setVisibility(View.GONE);
        pricing_holder.setVisibility(View.GONE);
        subcategory_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // System.out.println(subcategory_spinner.getSelectedItem().toString().toUpperCase());
                if(position==subcategory_spinner.getCount()-1 && position!=0 && subcategory_spinner.getSelectedItem().toString().toUpperCase().equals("ADD NEW"))
                {
                    ProductAddCategory productAddCategory = new ProductAddCategory();
                    productAddCategory.setCategory("Product Category");
                    productAddCategory.setProduct_class(category_spinner.getSelectedItem().toString());
                    upAddPopUp(productAddCategory,subcategory_spinner,db.COLUMN_product_add_category_category+"='Product Category' AND "+db.COLUMN_product_add_category_product_class+"='"+category_spinner.getSelectedItem().toString()+"'","No Sub Category","Add New");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        final Button add_product_sku  = FontSettings.setTextFont((Button) findViewById(R.id.add_product_sku),mContex,db,"Add SKU");
        add_product_sku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sku_add_pop_up();

            }
        });

        final Button submit_data  = FontSettings.setTextFont((Button) findViewById(R.id.submit_data),mContex,db,"Submit");
        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verifyData()){return;}

                new SweetAlertDialog(mContex, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                        .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText())
                        .setConfirmText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText())
                        .setCancelText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText())
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog ssDialog) {
                                ssDialog.dismiss();
                                progressDoalog = new ProgressDialog(mContex);
                                try {
                                    progressDoalog.setMessage("Product Creating....");
                                    progressDoalog.setTitle("Please Wait");
                                    progressDoalog.show();

                                }catch (Exception e){

                                }
                                final User cUser = new User();
                                cUser.select(db,"1=1");
                                new Thread(new Runnable() {
                                    public void run() {
                                        submitForm();
                                        try {
                                            Thread.sleep(3000);
                                        }catch (Exception e){}
                                        btn_product_info.post(new Runnable() {
                                            public void run() {
                                                hideSoftKeyboard(AddProduct.this);
                                                if(cUser.getActive_online()==1 && isInternetOn(mContex)) {
                                                    ArrayList<NewRetailProduct> c = NewRetailProduct.select(db, db.COLUMN_new_retail_product_entry_id + "=" + currentEntryId);

                                                    final User cSystemInfo = new User();
                                                    cSystemInfo.select(db, "1=1");
                                                    String username = cSystemInfo.getUser_name();
                                                    String password = cSystemInfo.getPassword();
                                                    String company = cSystemInfo.getCompany_id();
                                                    String location = cSystemInfo.getSelected_location();
                                                    String pos = cSystemInfo.getSelected_pos();
                                                    UploadData uploadData = new UploadData();
                                                    uploadData.data = c;
                                                    uploadData.url = url_retail_product_upload + "user_name=" + username + "&password=" + password + "&company=" + company + "&" + "&location=" + location + "&pos=" + pos + "&";
                                                    uploadData.uploaddata(mContex, mContex, "", mContex, "uploadComplete");
                                                }
                                            }
                                        });
                                    }
                                }).start();

                            }
                        })
                        .setCancelButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();

            }
        });

        if(in_entry_id>0)
        {
            ListView product_sku_list = findViewById(R.id.product_sku_list);
            newRetailProduct = NewRetailProduct.select(db,db.COLUMN_new_retail_product_entry_id+"="+in_entry_id);

            ScrollListView.loadListViewUpdateHeight(mContex, product_sku_list, R.layout.adaptar_add_product_sku_iteam_list_main, newRetailProduct, "productSKUMainList", 0, 100, false);


            String[] imgArray = newRetailProduct.get(0).getImages().split(";");
            for(String img : imgArray)
            {
                if(!img.equals("")) {
                    System.out.println(Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FileManagerSetting.folder_name + "/" + FileManagerSetting.new_retail_folder + "/" + img.trim()));
                    image_list.add(Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FileManagerSetting.folder_name + "/" + FileManagerSetting.new_retail_folder + "/" + img.trim()));
                }
            }
            createImageList();


            NewRetailProduct product = newRetailProduct.get(0);

            pre_sub_category = product.getSub_category();
            setSeletedIteamSpinner(category_spinner,product.getCategory());
            setSeletedIteamSpinner(subcategory_spinner,product.getSub_category());
            setSeletedIteamSpinner(manufacture_spinner,product.getManufacturer());
            setSeletedIteamSpinner(brand_spinner,product.getBrand());
            setSeletedIteamSpinner(purchase_group_spinner,product.getPurchase_tax_group());
            setSeletedIteamSpinner(sales_group_spinner,product.getSales_tax_group());
            setSeletedIteamSpinner(image_group_spinner,product.getImage_group());


            LinearLayout after_sku_holder  = (LinearLayout) findViewById(R.id.after_sku_holder);
            after_sku_holder.setVisibility(View.VISIBLE);
            EditText good_title  = (EditText) findViewById(R.id.good_title);
            EditText product_description  = (EditText) findViewById(R.id.product_description);
            EditText product_weight  = (EditText) findViewById(R.id.product_weight);
            EditText product_dimension  = (EditText) findViewById(R.id.product_dimension);
            EditText product_accessory  = (EditText) findViewById(R.id.product_accessory);
            EditText product_warranty  = (EditText) findViewById(R.id.product_warranty);
            EditText appro_cost_price  = (EditText) findViewById(R.id.appro_cost_price);
            EditText whole_sale_price  = (EditText) findViewById(R.id.whole_sale_price);
            EditText retail_price  = (EditText) findViewById(R.id.retail_price);

            good_title.setText(String.valueOf(product.getTitle()));
            product_description.setText(String.valueOf(product.getDescription()));
            product_weight.setText(String.valueOf(product.getWeight()));
            product_dimension.setText(String.valueOf(product.getDimension()));
            product_accessory.setText(String.valueOf(product.getAccessories()));
            product_warranty.setText(String.valueOf(product.getWarrenty()));
            appro_cost_price.setText(String.valueOf(product.getApproximate_cost()));
            whole_sale_price.setText(String.valueOf(product.getWhole_sale_price()));
            retail_price.setText(String.valueOf(product.getRetail_price()));

            linkedHashMap = new LinkedHashMap<String, Boolean>();

            ArrayList<String> mp = new ArrayList<String>();

            String[] mrtArray = product.getMarket_place().split(",");
            for(int i=0;i<mrtArray.length;i++)
            {
                mp.add(mrtArray[i].trim());
            }

            for(String market : market_places)
            {
                if(mp.contains(market.trim()))
                {
                    linkedHashMap.put(market, true);
                }
                else
                {
                    linkedHashMap.put(market, false);
                }

            }

            simpleSpinner.setItems(linkedHashMap, new MultiSpinnerListener() {
                @Override
                public void onItemsSelected(boolean[] selected) {

                }

            });

        }
    }

    void setSeletedIteamSpinner(Spinner spinner,String txt)
    {
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(txt)){
                spinner.setSelection(i);
                return;
            }
        }
        spinner.setSelection(0);
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
        LinearLayout image_list_holder = findViewById(R.id.image_list_holder);
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
            }
            else
            {
                LinearLayout product1  = (LinearLayout) product.findViewById(R.id.product2);
                product1.setVisibility(View.GONE);
            }
            image_list_holder.addView(product);
        }
    }

    void upAddPopUp(final ProductAddCategory productAddCategory, final Spinner spinner, final String con,final String upTxt, final String endTxt)
    {
        UIComponent.hideSoftKeyboard(AddProduct.this);
        LayoutInflater layoutInflater = (LayoutInflater) mContex
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_product_add_category, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);


        Button add_name  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add_name),mContex,db,"Add");

        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        TextView product_category  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.product_category),mContex,productAddCategory.getCategory());
        TextView product_class  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.product_class),mContex,productAddCategory.getProduct_class());
        final EditText product_name  = (EditText) popupView.findViewById(R.id.product_name);




        add_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                User cSystemInfo = new User();
                                cSystemInfo.select(db,"1=1");
                                productAddCategory.setEntry_by(cSystemInfo.getUser_name());
                                productAddCategory.setDateTime(DateTimeCalculation.getCurrentDateTime());
                                productAddCategory.setName(product_name.getText().toString());
                                productAddCategory.insert(db);
                                ArrayList<String> categoryList = getListData(db,con,upTxt,endTxt);
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContex,R.layout.spinner_item,categoryList){
                                    @Override
                                    public View getDropDownView(int position, View convertView,
                                                                ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        TextView tv = (TextView) view;
                                        if(tv.getText().toString().equals("Add New")) {
                                            tv.setBackgroundColor(Color.parseColor("#00a81e"));
                                        }
                                        else {
                                            tv.setBackgroundColor(Color.parseColor("#ffffff"));
                                        }
                                        return view;
                                    }
                                    @Override
                                    public boolean isEnabled(int position) {
                                        return true;
                                    }
                                };
                                dataAdapter.setDropDownViewResource(R.layout.spinner_item);
                                spinner.setAdapter(dataAdapter);
                                spinner.setSelection(dataAdapter.getPosition(productAddCategory.getName()));
                                hideSoftKeyboard(AddProduct.this);
                                dialog.dismiss();
                                popupWindow.dismiss();
                                Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                        .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(btn_product_info, Gravity.CENTER, 0, 0);
        showSoftKeyboard(AddProduct.this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showSoftKeyboard(AddProduct.this);
            }
        });
    }


    void detailsInfoAddPopUp(final String title,final Spinner spinner,final String data)
    {
        UIComponent.hideSoftKeyboard(AddProduct.this);
        LayoutInflater layoutInflater = (LayoutInflater) mContex
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_new_product_retail_details_add, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);


        Button add_name  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add_name),mContex,db,"Add");

        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        TextView payment_title  = FontSettings.setTextFont((TextView) popupView.findViewById(R.id.payment_title),mContex,title);
        final EditText product_name  = (EditText) popupView.findViewById(R.id.product_name);
        final EditText height  = (EditText) popupView.findViewById(R.id.height);
        final EditText width  = (EditText) popupView.findViewById(R.id.width);

        final LinearLayout width_holder = (LinearLayout) popupView.findViewById(R.id.width_holder);
        final LinearLayout height_holder = (LinearLayout) popupView.findViewById(R.id.height_holder);

        if(!data.toUpperCase().equals("IMAGE_GROUP"))
        {
            width_holder.setVisibility(View.GONE);
            height_holder.setVisibility(View.GONE);
        }

        add_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(product_name.getText().toString().equals(""))
                                {
                                    return;
                                }
                                if(data.toUpperCase().equals("IMAGE_GROUP"))
                                {
                                    if(TypeConvertion.parseInt(height.getText().toString())==0 || TypeConvertion.parseInt(width.getText().toString())==0)
                                    {
                                        return;
                                    }
                                }
                                updateSpinner(title,spinner,product_name.getText().toString(),TypeConvertion.parseInt(height.getText().toString()),TypeConvertion.parseInt(width.getText().toString()),data);
                                hideSoftKeyboard(AddProduct.this);
                                dialog.dismiss();
                                popupWindow.dismiss();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                builder.setMessage(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation").getText()).setPositiveButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_yes").getText(), dialogClickListener)
                        .setNegativeButton(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_confermation_no").getText(), dialogClickListener).show();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(btn_product_info, Gravity.CENTER, 0, 0);
        showSoftKeyboard(AddProduct.this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showSoftKeyboard(AddProduct.this);
            }
        });
    }

    void updateSpinner(final String title,final Spinner spinner,final String txt,final int height,final int width, final String type)
    {
        ArrayList<String> list = new ArrayList<>();

        if(type.toUpperCase().equals("MANUFACTURE"))
        {
            if(!txt.equals(""))
            {
                RetailProductManufacture retailProductManufacture = new RetailProductManufacture();
                retailProductManufacture.setName(txt);
                retailProductManufacture.insert(db);
            }

            list = RetailProductManufacture.getAllManufacture(db);
            list.add(0,"Select Iteam");
            list.add(1,"Others");
            list.add("Add New");

        }
        else if(type.toUpperCase().equals("BRAND"))
        {
            if(!txt.equals(""))
            {
                RetailProductBrand retailProductManufacture = new RetailProductBrand();
                retailProductManufacture.setName(txt);
                retailProductManufacture.insert(db);
            }

            list = RetailProductBrand.getAllBrand(db);
            list.add(0,"Select Iteam");
            list.add(1,"Others");
            list.add("Add New");

        }
        else if(type.toUpperCase().equals("IMAGE_GROUP"))
        {
            if(!txt.equals(""))
            {
                RetailImageGroup retailProductManufacture = new RetailImageGroup();
                retailProductManufacture.setGroup_name(txt);
                retailProductManufacture.setHeight(height);
                retailProductManufacture.setWidth(width);
                retailProductManufacture.insert(db);
            }

            list = RetailImageGroup.getAllImageGroup(db);
            list.add(0,"Select Iteam");
            list.add("Add New");

        }
        else if(type.toUpperCase().equals("PURCHASE_GROUP"))
        {
            if(!txt.equals(""))
            {
                RetailProductTaxGroup retailProductManufacture = new RetailProductTaxGroup();
                retailProductManufacture.setName(txt);
                retailProductManufacture.insert(db);
            }

            list = RetailProductTaxGroup.getAllPurchaseTax(db);
            list.add(0,"Select Iteam");
            list.add("Add New");

        }
        else if(type.toUpperCase().equals("SALES_GROUP"))
        {
            if(!txt.equals(""))
            {
                RetailProductSalesTaxGroup retailProductManufacture = new RetailProductSalesTaxGroup();
                retailProductManufacture.setName(txt);
                retailProductManufacture.insert(db);
            }

            list = RetailProductSalesTaxGroup.getAllSalesTax(db);
            list.add(0,"Select Iteam");
            list.add("Add New");

        }

        ArrayAdapter<String> BranddataAdapter = new ArrayAdapter<String>(mContex,R.layout.spinner_item,list){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(tv.getText().toString().equals("Add New")) {
                    tv.setBackgroundColor(Color.parseColor("#00a81e"));
                }
                else {
                    tv.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                return view;
            }
            @Override
            public boolean isEnabled(int position) {
                return true;
            }
        };
        BranddataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(BranddataAdapter);
        spinner.setSelection(BranddataAdapter.getPosition(txt));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==spinner.getCount()-1)
                {
                    detailsInfoAddPopUp(title,spinner,type);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        if(!txt.equals(""))
        {
            Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
        }
    }
    void sku_add_pop_up()
    {
        UIComponent.hideSoftKeyboard(AddProduct.this);
        LayoutInflater layoutInflater = (LayoutInflater) mContex
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_product_category_add_product_sku, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        final ImageView close_tab = (ImageView) popupView.findViewById(R.id.close_tab);


        Button add_product  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.add_product),mContex,db,"Add");
        Button receive  = FontSettings.setTextFont((Button) popupView.findViewById(R.id.receive),mContex,db,"Done");

        close_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        product_main  = (EditText) popupView.findViewById(R.id.product_main);
        product_info  = (EditText) popupView.findViewById(R.id.product_info);
        product_code  = (EditText) popupView.findViewById(R.id.product_code);
        product_size  = (EditText) popupView.findViewById(R.id.product_size);
        product_btn_update  = (Button) popupView.findViewById(R.id.add_product);
        Button remove_product  = (Button) popupView.findViewById(R.id.remove_product);

        final ListView product_list  = (ListView) popupView.findViewById(R.id.product_list);

        ScrollListView.loadListViewUpdateHeight(mContex, product_list, R.layout.adapter_add_product_sku_iteam_list, newRetailProduct, "productSKUList", 0, 100, false);


        if(newRetailProduct.size()>0)
        {
            String[] sku = newRetailProduct.get(0).getSku().split("-");

            try {
                product_main.setText(sku[0]);
            }catch (Exception e){product_main.setText("");}

            try {
                product_info.setText(sku[1]);
            }catch (Exception e){product_info.setText("");}

            product_main.setEnabled(false);
            product_info.setEnabled(false);
        }



        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(product_main.getText().toString().equals("") || product_info.getText().toString().equals(""))
                {
                    Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"Please fill up properly").getText(), Toast.LENGTH_SHORT, true).show();
                    return;
                }
                NewRetailProduct retailProduct = new NewRetailProduct();
                String sku = product_main.getText().toString()+"-"+product_info.getText().toString();

                if(!product_code.getText().toString().equals(""))
                {
                    sku+="-"+product_code.getText().toString();
                }
                else if(product_code.getText().toString().equals("") && !product_size.getText().toString().equals(""))
                {
                    sku+="-N/A";
                }
                if(!product_size.getText().toString().equals(""))
                {
                    sku+="-"+product_size.getText().toString();
                }
                retailProduct.setSku(sku);

                if(product_btn_update.getText().toString().toUpperCase().equals("UPDATE"))
                {

                    for(NewRetailProduct newRetailProduct1 : newRetailProduct)
                    {
                        if(newRetailProduct1.getSku().equals(sku))
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"SKU Already Added").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                    }

                    if(NewRetailProduct.getSkuCount(db,sku)>0)
                    {
                        Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"SKU Already Added").getText(), Toast.LENGTH_SHORT, true).show();
                        return;
                    }

                    newRetailProduct.set(skuAddUpdatePosition,retailProduct);
                    product_btn_update.setText("Add");
                    skuAddUpdatePosition=-1;
                }
                else
                {
                    for(NewRetailProduct newRetailProduct1 : newRetailProduct)
                    {
                        if(newRetailProduct1.getSku().equals(sku))
                        {
                            Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"SKU Already Added").getText(), Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                    }

                    if(NewRetailProduct.getSkuCount(db,sku)>0)
                    {
                        Toasty.error(getApplicationContext(), TextString.textSelectByVarname(db,"SKU Already Added").getText(), Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    newRetailProduct.add(retailProduct);
                }
                product_main.setEnabled(false);
                product_info.setEnabled(false);
                product_code.setText("");
                product_size.setText("");
                if(newRetailProduct.size()==0)
                {
                    product_main.setEnabled(true);
                    product_info.setEnabled(true);
                    product_main.setText("");
                    product_info.setText("");
                }

                ScrollListView.loadListViewUpdateHeight(mContex, product_list, R.layout.adapter_add_product_sku_iteam_list, newRetailProduct, "productSKUList", 0, 100, false);



                ListView product_sku_list = (ListView) findViewById(R.id.product_sku_list);
                ScrollListView.loadListViewUpdateHeight(mContex, product_sku_list, R.layout.adaptar_add_product_sku_iteam_list_main, newRetailProduct, "productSKUMainList", 0, 100, false);

                if(newRetailProduct.size()>0) {
                    LinearLayout after_sku_holder = (LinearLayout) findViewById(R.id.after_sku_holder);
                    after_sku_holder.setVisibility(View.VISIBLE);
                }else {
                    LinearLayout after_sku_holder = (LinearLayout) findViewById(R.id.after_sku_holder);
                    after_sku_holder.setVisibility(View.GONE);
                }
            }
        });

        remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skuAddUpdatePosition>-1) {
                    newRetailProduct.remove(skuAddUpdatePosition);
                    product_btn_update.setText("Add");
                    skuAddUpdatePosition = -1;
                    //product_main.setText("");
                    //product_info.setText("");
                    product_code.setText("");
                    product_size.setText("");
                    if(newRetailProduct.size()==0)
                    {
                        product_main.setEnabled(true);
                        product_info.setEnabled(true);
                        product_main.setText("");
                        product_info.setText("");
                    }
                    ScrollListView.loadListViewUpdateHeight(mContex, product_list, R.layout.adapter_add_product_sku_iteam_list, newRetailProduct, "productSKUList", 0, 100, false);


                    ListView product_sku_list = (ListView) findViewById(R.id.product_sku_list);
                    ScrollListView.loadListViewUpdateHeight(mContex, product_sku_list, R.layout.adaptar_add_product_sku_iteam_list_main, newRetailProduct, "productSKUMainList", 0, 100, false);

                    if(newRetailProduct.size()>0) {
                        LinearLayout after_sku_holder = (LinearLayout) findViewById(R.id.after_sku_holder);
                        after_sku_holder.setVisibility(View.VISIBLE);
                    }else {
                        LinearLayout after_sku_holder = (LinearLayout) findViewById(R.id.after_sku_holder);
                        after_sku_holder.setVisibility(View.GONE);
                    }
                }
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView product_sku_list = (ListView) findViewById(R.id.product_sku_list);
                ScrollListView.loadListViewUpdateHeight(mContex, product_sku_list, R.layout.adaptar_add_product_sku_iteam_list_main, newRetailProduct, "productSKUMainList", 0, 100, false);

                hideSoftKeyboard(AddProduct.this);
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(btn_product_info, Gravity.CENTER, 0, 0);
        showSoftKeyboard(AddProduct.this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showSoftKeyboard(AddProduct.this);
            }
        });
    }

    boolean verifyData()
    {
        if(newRetailProduct.size()==0)
        {

            btn_product_info.performClick();
            Toasty.error(mContex, "Please Add SKU", Toast.LENGTH_SHORT, true).show();
            return false;
        }



        Spinner category_spinner = (Spinner) findViewById(R.id.category_spinner);
        Spinner subcategory_spinner = (Spinner) findViewById(R.id.subcategory_spinner);
        Spinner manufacture_spinner = (Spinner) findViewById(R.id.manufacture_spinner);
        Spinner brand_spinner = (Spinner) findViewById(R.id.brand_spinner);
        EditText good_title = (EditText) findViewById(R.id.good_title);
        EditText whole_sale_price = (EditText) findViewById(R.id.whole_sale_price);

        EditText retail_price = (EditText) findViewById(R.id.retail_price);

        EditText product_description = (EditText) findViewById(R.id.product_description);
        EditText product_weight = (EditText) findViewById(R.id.product_weight);
        EditText product_dimension = (EditText) findViewById(R.id.product_dimension);
        EditText product_accessory = (EditText) findViewById(R.id.product_accessory);
        EditText product_warranty = (EditText) findViewById(R.id.product_warranty);
        EditText appro_cost_price = (EditText) findViewById(R.id.appro_cost_price);
        Spinner image_group_spinner = (Spinner) findViewById(R.id.image_group_spinner);
        Spinner purchase_group_spinner = (Spinner) findViewById(R.id.purchase_group_spinner);
        Spinner sales_group_spinner = (Spinner) findViewById(R.id.sales_group_spinner);
        MultiSpinner market_place = (MultiSpinner) findViewById(R.id.market_place);



        LinkedHashMap<View, String> linkedHashMap = new LinkedHashMap<View, String>();
        linkedHashMap.put(category_spinner,"Please Select Category");
        linkedHashMap.put(subcategory_spinner,"Please Select subcategory");
        linkedHashMap.put(good_title,"Please Select Good's Title");
        linkedHashMap.put(manufacture_spinner,"Please Select Manufacture");
        linkedHashMap.put(brand_spinner,"Please Select Brand");
        linkedHashMap.put(whole_sale_price,"Please Select Whole Price");
        linkedHashMap.put(retail_price,"Please Select Retail Price");
        ArrayList<View> validateData = FormDataValidation.validateDataList(linkedHashMap,mContex,true,false);
        if(validateData.size()>0)
        {
            showRequiredTab(validateData.get(0));
            return false;
        }
        return true;
    }

    void showRequiredTab(View field)
    {
        Button btn_product_info =(Button) findViewById(R.id.btn_product_info);
        Button btn_add_image =(Button) findViewById(R.id.btn_add_image);
        Button btn_supplier =(Button) findViewById(R.id.btn_supplier);

        LinkedHashMap<String, Button> linkedHashMap = new LinkedHashMap<String, Button>();
        linkedHashMap.put("category_spinner",btn_product_info);
        linkedHashMap.put("subcategory_spinner",btn_product_info);
        linkedHashMap.put("manufacture_spinner",btn_product_info);
        linkedHashMap.put("brand_spinner",btn_product_info);
        linkedHashMap.put("good_title",btn_product_info);
        linkedHashMap.put("whole_sale_price",btn_supplier);
        linkedHashMap.put("retail_price",btn_supplier);
        linkedHashMap.get(getResources().getResourceEntryName(field.getId())).performClick();
        if(field instanceof EditText)
        {
            ((EditText) field).requestFocus();
            ((EditText) field).requestFocus();
        }
        else if(field instanceof Spinner)
        {
            ((Spinner) field).requestFocus();
        }
    }

    void submitForm()
    {
        Spinner category_spinner = (Spinner) findViewById(R.id.category_spinner);
        Spinner subcategory_spinner = (Spinner) findViewById(R.id.subcategory_spinner);
        Spinner manufacture_spinner = (Spinner) findViewById(R.id.manufacture_spinner);
        Spinner brand_spinner = (Spinner) findViewById(R.id.brand_spinner);
        EditText good_title = (EditText) findViewById(R.id.good_title);
        EditText whole_sale_price = (EditText) findViewById(R.id.whole_sale_price);

        EditText retail_price = (EditText) findViewById(R.id.retail_price);

        EditText product_description = (EditText) findViewById(R.id.product_description);
        EditText product_weight = (EditText) findViewById(R.id.product_weight);
        EditText product_dimension = (EditText) findViewById(R.id.product_dimension);
        EditText product_accessory = (EditText) findViewById(R.id.product_accessory);
        EditText product_warranty = (EditText) findViewById(R.id.product_warranty);
        EditText appro_cost_price = (EditText) findViewById(R.id.appro_cost_price);
        Spinner image_group_spinner = (Spinner) findViewById(R.id.image_group_spinner);
        Spinner purchase_group_spinner = (Spinner) findViewById(R.id.purchase_group_spinner);
        Spinner sales_group_spinner = (Spinner) findViewById(R.id.sales_group_spinner);
        MultiSpinner market_place = (MultiSpinner) findViewById(R.id.market_place);

        User cUser = new User();
        cUser.select(db,"1=1");

        String img_url = "";
        String sku = newRetailProduct.get(0).getSku().split("-")[0]+"-"+newRetailProduct.get(0).getSku().split("-")[1];

        NewRetailProductEntryHead newRetailProductEntryHead = new NewRetailProductEntryHead();
        if(in_entry_id>0)
        {
            newRetailProductEntryHead.setId(in_entry_id);
        }
        newRetailProductEntryHead.setSku(sku);
        newRetailProductEntryHead.setPhotoQuantity(image_list.size());
        newRetailProductEntryHead.setQuantity(newRetailProduct.size());
        newRetailProductEntryHead.setEntry_by(cUser.getUser_name());
        newRetailProductEntryHead.setEntry_date(DateTimeCalculation.getCurrentDateTime());
        newRetailProductEntryHead.insert(db);

        int entry_id = in_entry_id;
        if(in_entry_id==0)
        {
            entry_id = NewRetailProductEntryHead.getMaxId(db);
        }
        currentEntryId = entry_id;
        if(image_list.size()>0) {

            for (int i = 0; i < image_list.size(); i++) {
                try {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_list.get(i));
                    }
                    catch (Exception e)
                    {
                        bitmap = getImageFromFileBase (image_list.get(i).toString(),mContex);
                    }
                    String fileName = "new-retail-sku-" + sku + "-" + entry_id + "-" + i + "_" + DateTimeCalculation.getCurrentTimeStamp();
                    System.out.println(image_list.get(i)+"---"+fileName);
                    String root = Environment.getExternalStorageDirectory().toString() + "/" + FileManagerSetting.folder_name + "/" + FileManagerSetting.new_retail_folder + "/" + fileName;
                    if (!FileManagerSetting.saveImageToLocation(mContex, bitmap, root)) {
                        System.out.println("Problem Saving Photo- " + image_list.get(i).toString());
                    } else {
                        img_url += fileName + ".png;";
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        for(NewRetailProduct rp : newRetailProduct)
        {
            rp.setEntry_id(entry_id);
            rp.setCategory(category_spinner.getSelectedItem().toString());
            rp.setSub_category(subcategory_spinner.getSelectedItem().toString());
            rp.setTitle(good_title.getText().toString());
            rp.setDescription(FormDataValidation.getData(product_description));
            rp.setWeight(FormDataValidation.getData(product_weight));
            rp.setDimension(FormDataValidation.getData(product_dimension));
            rp.setAccessories(FormDataValidation.getData(product_accessory));
            rp.setWarrenty(FormDataValidation.getData(product_warranty));
            rp.setManufacturer(FormDataValidation.getData(manufacture_spinner));
            rp.setBrand(FormDataValidation.getData(brand_spinner));
            rp.setMarket_place(market_place.getSelectedItem().toString());
            rp.setImage_group(FormDataValidation.getData(image_group_spinner));
            rp.setImages(img_url);
            rp.setApproximate_cost(TypeConvertion.parseDouble(FormDataValidation.getData(appro_cost_price)));
            rp.setWhole_sale_price(TypeConvertion.parseDouble(FormDataValidation.getData(whole_sale_price)));
            rp.setRetail_price(TypeConvertion.parseDouble(FormDataValidation.getData(retail_price)));
            rp.setPurchase_tax_group(FormDataValidation.getData(purchase_group_spinner));
            rp.setPurchase_tax_group(FormDataValidation.getData(purchase_group_spinner));
            rp.setSales_tax_group(FormDataValidation.getData(sales_group_spinner));
            rp.setEntry_by(cUser.getUser_name());
            rp.setEntry_date_time(DateTimeCalculation.getCurrentDateTime());
            rp.insert(db);
        }
    }
    public void uploadComplete(ArrayList<String> data)
    {

        progressDoalog.dismiss();
        System.out.println("here11");
        SweetAlertDialog sDialog = new SweetAlertDialog(mContex, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successful")
                .setContentText(TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText())
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Toasty.success(getApplicationContext(),TextString.textSelectByVarname(db,"pos_sale_payment_popbox_invoice_complete").getText(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(mContex, ProductList.class);
                        startActivity(intent);
                        finish();
                    }
                });
        sDialog.setCancelable(false);
        sDialog.show();
    }
    public void productSKUMainList(final ViewData data)
    {
        NewRetailProduct newRetailProduct = (NewRetailProduct) data.object;
        String[] sku = newRetailProduct.getSku().split("-");
        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.main),mContex,sku[0]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.main),mContex,"");
        }

        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.info),mContex,sku[1]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.info),mContex,"");
        }

        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.color),mContex,sku[2]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.color),mContex,"");
        }

        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.size),mContex,sku[3]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.size),mContex,"");
        }
    }
    public void productSKUList(final ViewData data)
    {
        NewRetailProduct newRetailProduct = (NewRetailProduct) data.object;
        String[] sku = newRetailProduct.getSku().split("-");
        Button update  = (Button) data.view.findViewById(R.id.update);
        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.main),mContex,sku[0]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.main),mContex,"");
        }

        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.info),mContex,sku[1]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.info),mContex,"");
        }

        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.color),mContex,sku[2]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.color),mContex,"");
        }

        try {
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.size),mContex,sku[3]);
        }catch (Exception e){
            FontSettings.setTextFont((TextView) data.view.findViewById(R.id.size),mContex,"");
        }
        if(newRetailProduct.getEntry_id()>0)
        {
            update.setVisibility(View.GONE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("add-product-create-product-sku");
                intent.putExtra("position",String.valueOf(data.position));
                LocalBroadcastManager.getInstance(mContex).sendBroadcast(intent);
            }
        });

    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String position = intent.getStringExtra("position");
            skuAddUpdatePosition= TypeConvertion.parseInt(position);
            try {
                String[] sku = newRetailProduct.get(skuAddUpdatePosition).getSku().split("-");

                try {
                    product_main.setText(sku[0]);
                }catch (Exception e){product_main.setText("");}

                try {
                    product_info.setText(sku[1]);
                }catch (Exception e){product_info.setText("");}

                try {
                    product_code.setText(sku[2]);
                }catch (Exception e){product_code.setText("");}

                try {
                    product_size.setText(sku[3]);
                }catch (Exception e){product_size.setText("");}
                product_btn_update.setText("Update");
            }catch (Exception e){}
        }
    };
}
