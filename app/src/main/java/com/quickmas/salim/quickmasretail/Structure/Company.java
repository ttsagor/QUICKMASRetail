package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.RTM.Product;
import com.quickmas.salim.quickmasretail.Utility.DebugHelper;

import java.util.ArrayList;

/**
 * Created by Forhad on 28/03/2018.
 */

public class Company {
    private String company_name;
    private int total_status_1;
    private int total_status_2;
    private int total_status_3;
    private int totalQuantityAllProduct;
    private int totalQuantityAllGift;
    private double totalQuantityAllProductPrice;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Product> gifts = new ArrayList<>();
    private ArrayList<Product> posm = new ArrayList<>();
    private ArrayList<Product> productAndGift = new ArrayList<>();
    private ArrayList<Product> freeProduct = new ArrayList<>();
    private ArrayList<Product> crm = new ArrayList<>();
    private ArrayList<Product> mi = new ArrayList<>();

    public static Company getCompanyStructAllCompany(ArrayList<Product> allProduct)
    {
        Company cStruc_company = new Company();
        cStruc_company.setCompany_name("");
        cStruc_company.setProducts(getProductsAll(allProduct,"FINISHED GOODS"));
        cStruc_company.setGifts(getProductsAll(allProduct,"GIFT"));
        cStruc_company.setFreeProduct(getProductsAll(allProduct,"FREE PRODUCT"));
        cStruc_company.setProductAndGift(getProductsAll(allProduct,"PRODUCTS AND GIFTS"));
        cStruc_company.setPosm(getProductsAll(allProduct,"POSM"));
        cStruc_company.setCrm(getProductsAll(allProduct,"CRM"));
        cStruc_company.setMi(getProductsAll(allProduct,"MI"));
        return cStruc_company;
    }

    public static ArrayList<Company> getCompanyStruct(ArrayList<Product> allProduct)
    {
        ArrayList<String> allCompany = new ArrayList<String>();
        ArrayList<Company> allSCompany = new ArrayList<Company>();
        for(Product cProduct : allProduct)
        {
            if(cProduct.getProduct_category().toUpperCase().equals("FREE PRODUCT"))
            {
                if(!allCompany.contains(cProduct.getGift_for()))
                {
                    allCompany.add(cProduct.getGift_for());
                }
            }
            else
            {
                if(!allCompany.contains(cProduct.getCompany()))
                {
                    allCompany.add(cProduct.getCompany());
                }
            }
        }
        for(String cCompany : allCompany)
        {
            Company cStruc_company = new Company();
            cStruc_company.setCompany_name(cCompany);
            cStruc_company.setProducts(getProductsByCompanyName(allProduct,cCompany,"FINISHED GOODS"));
            cStruc_company.setGifts(getProductsByCompanyName(allProduct,cCompany,"GIFT"));
            cStruc_company.setFreeProduct(getProductsByCompanyName(allProduct,cCompany,"FREE PRODUCT"));
            cStruc_company.setProductAndGift(getProductsByCompanyName(allProduct,cCompany,"PRODUCTS AND GIFTS"));
            cStruc_company.setPosm(getProductsByCompanyName(allProduct,cCompany,"POSM"));
            cStruc_company.setCrm(getProductsByCompanyName(allProduct,cCompany,"CRM"));
            cStruc_company.setMi(getProductsByCompanyName(allProduct,cCompany,"MI"));
            allSCompany.add(cStruc_company);
        }

        return allSCompany;
    }


    public static ArrayList<Company> getCompanyStructOnlyProductAndGift(ArrayList<Product> allProduct)
    {
        ArrayList<String> allCompany = new ArrayList<String>();
        ArrayList<Company> allSCompany = new ArrayList<Company>();
        ArrayList<String> category = new ArrayList<>();
        category.add("POSM");
        category.add("CRM");
        category.add("GIFT");
        category.add("MI");
        for(Product cProduct : allProduct)
        {
            if(cProduct.getProduct_category().toUpperCase().equals("FREE PRODUCT"))
            {
                if(!allCompany.contains(cProduct.getGift_for()))
                {
                    allCompany.add(cProduct.getGift_for());
                }
            }
            else if(!category.contains(cProduct.getProduct_category().toUpperCase()))
            {
                if(!allCompany.contains(cProduct.getCompany()))
                {
                    allCompany.add(cProduct.getCompany());
                }
            }

        }
        for(String cCompany : allCompany)
        {
            Company cStruc_company = new Company();
            cStruc_company.setCompany_name(cCompany);
            cStruc_company.setProducts(getProductsByCompanyName(allProduct,cCompany,"FINISHED GOODS"));
            cStruc_company.setFreeProduct(getProductsByCompanyName(allProduct,cCompany,"FREE PRODUCT"));
            cStruc_company.setGifts(getProductsByCompanyName(allProduct,cCompany,"GIFT"));
            cStruc_company.setProductAndGift(getProductsByCompanyName(allProduct,cCompany,"PRODUCTS AND GIFTS"));
            cStruc_company.setPosm(getProductsByCompanyName(allProduct,cCompany,"POSM"));
            cStruc_company.setCrm(getProductsByCompanyName(allProduct,cCompany,"CRM"));
            cStruc_company.setMi(getProductsByCompanyName(allProduct,cCompany,"MI"));
            if(cStruc_company.getProducts().size()>0 || cStruc_company.getGifts().size()>0)
            {
                allSCompany.add(cStruc_company);
            }
        }

        return allSCompany;
    }

    public static ArrayList<Company> getCompanyStructOnlyFreeProduct(ArrayList<Product> allProduct)
    {
        ArrayList<String> allCompany = new ArrayList<String>();
        ArrayList<Company> allSCompany = new ArrayList<Company>();

        for(Product cProduct : allProduct)
        {
            if(cProduct.getProduct_category().toUpperCase().equals("GIFT"))
            {
                if(!allCompany.contains(cProduct.getCompany()))
                {
                    allCompany.add(cProduct.getCompany());
                }
            }

        }
        for(String cCompany : allCompany)
        {
            Company cStruc_company = new Company();
            cStruc_company.setCompany_name(cCompany);
            cStruc_company.setProducts(getProductsByCompanyName(allProduct,cCompany,"FINISHED GOODS"));
            cStruc_company.setFreeProduct(getProductsByCompanyName(allProduct,cCompany,"FREE PRODUCT"));
            cStruc_company.setGifts(getProductsByCompanyName(allProduct,cCompany,"GIFT"));
            cStruc_company.setProductAndGift(getProductsByCompanyName(allProduct,cCompany,"PRODUCTS AND GIFTS"));
            cStruc_company.setPosm(getProductsByCompanyName(allProduct,cCompany,"POSM"));
            cStruc_company.setCrm(getProductsByCompanyName(allProduct,cCompany,"CRM"));
            cStruc_company.setMi(getProductsByCompanyName(allProduct,cCompany,"MI"));
            if(cStruc_company.getProducts().size()>0 || cStruc_company.getGifts().size()>0)
            {
                allSCompany.add(cStruc_company);
            }
        }

        return allSCompany;
    }

    public static ArrayList<Company> getCompanyStructOnlyCRM(ArrayList<Product> allProduct)
    {
        ArrayList<String> allCompany = new ArrayList<String>();
        ArrayList<Company> allSCompany = new ArrayList<Company>();

        for(Product cProduct : allProduct)
        {
            if(cProduct.getProduct_category().toUpperCase().equals("CRM"))
            {
                if(!allCompany.contains(cProduct.getCompany()))
                {
                    allCompany.add(cProduct.getCompany());
                }
            }

        }
        for(String cCompany : allCompany)
        {
            Company cStruc_company = new Company();
            cStruc_company.setCompany_name(cCompany);
            cStruc_company.setProducts(getProductsByCompanyName(allProduct,cCompany,"FINISHED GOODS"));
            cStruc_company.setFreeProduct(getProductsByCompanyName(allProduct,cCompany,"FREE PRODUCT"));
            cStruc_company.setGifts(getProductsByCompanyName(allProduct,cCompany,"GIFT"));
            cStruc_company.setProductAndGift(getProductsByCompanyName(allProduct,cCompany,"PRODUCTS AND GIFTS"));
            cStruc_company.setPosm(getProductsByCompanyName(allProduct,cCompany,"POSM"));
            cStruc_company.setCrm(getProductsByCompanyName(allProduct,cCompany,"CRM"));
            cStruc_company.setMi(getProductsByCompanyName(allProduct,cCompany,"MI"));
            if(cStruc_company.getProducts().size()>0 || cStruc_company.getGifts().size()>0)
            {
                allSCompany.add(cStruc_company);
            }
        }

        return allSCompany;
    }

    public static ArrayList<Company> getCompanyStructOnlyPOSM(ArrayList<Product> allProduct)
    {
        ArrayList<String> allCompany = new ArrayList<String>();
        ArrayList<Company> allSCompany = new ArrayList<Company>();
        for(Product cProduct : allProduct)
        {
            if(!allCompany.contains(cProduct.getCompany()))
            {
                allCompany.add(cProduct.getCompany());
            }
        }
        for(String cCompany : allCompany)
        {
            Company cStruc_company = new Company();
            cStruc_company.setCompany_name(cCompany);
            cStruc_company.setProducts(getProductsByCompanyName(allProduct,cCompany,"FINISHED GOODS"));
            cStruc_company.setFreeProduct(getProductsByCompanyName(allProduct,cCompany,"FREE PRODUCT"));
            cStruc_company.setGifts(getProductsByCompanyName(allProduct,cCompany,"GIFT"));
            cStruc_company.setProductAndGift(getProductsByCompanyName(allProduct,cCompany,"PRODUCTS AND GIFTS"));
            cStruc_company.setPosm(getProductsByCompanyName(allProduct,cCompany,"POSM"));
            cStruc_company.setCrm(getProductsByCompanyName(allProduct,cCompany,"CRM"));
            cStruc_company.setMi(getProductsByCompanyName(allProduct,cCompany,"MI"));
            if(cStruc_company.getPosm().size()>0)
            {
                allSCompany.add(cStruc_company);
            }
        }

        return allSCompany;
    }
    public static ArrayList<Product> getProductsByCompanyName(ArrayList<Product> allProduct,String companyName,String product_type)
    {
        ArrayList<Product> products = new ArrayList<Product>();
        ArrayList<String> category = new ArrayList<>();
        category.add("POSM");
        category.add("CRM");
        category.add("GIFT");
        category.add("FREE PRODUCT");
        category.add("MI");

        for(Product cProduct : allProduct)
        {
            if(product_type.contains("PRODUCTS AND GIFTS"))
            {
                if(cProduct.getGift_for().equals(companyName) && cProduct.getProduct_category().toUpperCase().equals("FREE PRODUCT"))
                {
                    products.add(cProduct);
                }
                else if(cProduct.getCompany().equals(companyName) && !category.contains(cProduct.getProduct_category().toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("FREE PRODUCT"))
            {
                if(cProduct.getGift_for().equals(companyName) && cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("GIFT"))
            {
                if(cProduct.getCompany().equals(companyName) && cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("POSM"))
            {
                if(cProduct.getCompany().equals(companyName) && cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("CRM"))
            {
                if(cProduct.getCompany().equals(companyName) && cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("MI"))
            {
                if(cProduct.getCompany().equals(companyName) && cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("FINISHED GOODS"))
            {
                if(cProduct.getCompany().equals(companyName) && !category.contains(cProduct.getProduct_category().toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
        }
        return products;
    }

    public static ArrayList<Product> getProductsAll(ArrayList<Product> allProduct,String product_type)
    {
        ArrayList<Product> products = new ArrayList<Product>();
        ArrayList<String> category = new ArrayList<>();
        category.add("POSM");
        category.add("CRM");
        category.add("GIFT");
        category.add("FREE PRODUCT");
        category.add("MI");

        for(Product cProduct : allProduct)
        {
            if(product_type.contains("PRODUCTS AND GIFTS"))
            {
                if(cProduct.getProduct_category().toUpperCase().equals("FREE PRODUCT"))
                {
                    products.add(cProduct);
                }
                else if(!category.contains(cProduct.getProduct_category().toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("FREE PRODUCT"))
            {
                if(cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("GIFT"))
            {
                if(cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("POSM"))
            {
                if(cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("CRM"))
            {
                if(cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("MI"))
            {
                if(cProduct.getProduct_category().toUpperCase().equals(product_type.toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
            else if(product_type.contains("FINISHED GOODS"))
            {
                if(!category.contains(cProduct.getProduct_category().toUpperCase()))
                {
                    products.add(cProduct);
                }
            }
        }
        return products;
    }

    public static int getSkuCountCompany(ArrayList<Company> companies,String category)
    {
        int total_product=0;
        for(Company company : companies)
        {
            if(category.toUpperCase().equals("PRODUCT"))
            {
                total_product+=getSkuCount(company.getProducts());
            }
            else if(category.toUpperCase().equals("GIFT"))
            {
                total_product+=getSkuCount(company.getGifts());
            }
            else if(category.toUpperCase().equals("FREE PRODUCT"))
            {
                total_product+=getSkuCount(company.getFreeProduct());
            }
            else if(category.toUpperCase().equals("POSM"))
            {
                total_product+=getSkuCount(company.getFreeProduct());
            }
            else if(category.toUpperCase().equals("MI"))
            {
                total_product+=getSkuCount(company.getMi());
            }
        }

        return total_product;
    }
    public static int getSkuCount(ArrayList<Product> products)
    {
        int total_product=0;
        for(Product product : products)
        {
            total_product+=product.getSku_qty();
        }
        return total_product;
    }
    public ArrayList<Product> getProductAndGift() {
        return productAndGift;
    }

    public void setProductAndGift(ArrayList<Product> productAndGift) {
        this.productAndGift = productAndGift;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setTotal_status_1(int total_status_1) {
        this.total_status_1 = total_status_1;
    }

    public void setTotal_status_2(int total_status_2) {
        this.total_status_2 = total_status_2;
    }

    public void setTotal_status_3(int total_status_3) {
        this.total_status_3 = total_status_3;
    }

    public void setTotalQuantityAllProduct(int totalQuantityAllProduct) {
        this.totalQuantityAllProduct = totalQuantityAllProduct;
    }

    public void setTotalQuantityAllProductPrice(double totalQuantityAllProductPrice) {
        this.totalQuantityAllProductPrice = totalQuantityAllProductPrice;
    }

    public int getTotalQuantityAllGift() {
        return totalQuantityAllGift;
    }

    public void setTotalQuantityAllGift(int totalQuantityAllGift) {
        this.totalQuantityAllGift = totalQuantityAllGift;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setGifts(ArrayList<Product> gifts) {
        this.gifts = gifts;
    }

    public void setPosm(ArrayList<Product> posm) {
        this.posm = posm;
    }

    public String getCompany_name() {
        return company_name;
    }

    public int getTotal_status_1() {
        return total_status_1;
    }

    public int getTotal_status_2() {
        return total_status_2;
    }

    public int getTotal_status_3() {
        return total_status_3;
    }

    public int getTotalQuantityAllProduct() {
        return totalQuantityAllProduct;
    }

    public double getTotalQuantityAllProductPrice() {
        return totalQuantityAllProductPrice;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getGifts() {
        return gifts;
    }

    public ArrayList<Product> getPosm() {
        return posm;
    }

    public void setFreeProduct(ArrayList<Product> freeProduct) {
        this.freeProduct = freeProduct;
    }

    public ArrayList<Product> getFreeProduct() {
        return freeProduct;
    }

    public ArrayList<Product> getCrm() {
        return crm;
    }

    public void setCrm(ArrayList<Product> crm) {
        this.crm = crm;
    }

    public void setMi(ArrayList<Product> mi) {
        this.mi = mi;
    }

    public ArrayList<Product> getMi() {
        return mi;
    }
}
