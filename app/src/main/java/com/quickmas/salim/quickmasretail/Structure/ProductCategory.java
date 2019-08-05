package com.quickmas.salim.quickmasretail.Structure;

import com.quickmas.salim.quickmasretail.Model.POS.PosProduct;

import java.util.ArrayList;

/**
 * Created by Forhad on 18/04/2018.
 */

public class ProductCategory {
    public String category_name;
    public ArrayList<PosProduct> products= new ArrayList<>();
    public ArrayList<String> sub_category = new ArrayList<>();


    public static ArrayList<ProductCategory> getAllCategory(ArrayList<PosProduct> allProduct)
    {
        ArrayList<String> allCategory = new ArrayList<String>();
        ArrayList<ProductCategory> allSCategory = new ArrayList<ProductCategory>();
        ArrayList<String> subCategory= new ArrayList<String>();
        for(PosProduct cProduct : allProduct)
        {
            if(!allCategory.contains(cProduct.getType()))
            {
                allCategory.add(cProduct.getType());
            }
        }
        for(String cCategory : allCategory)
        {
            ProductCategory cProduct = new ProductCategory();
            cProduct.setCategory_name(cCategory);
            cProduct.setProducts(getProductsByCategory(allProduct,cCategory));
            cProduct.setSub_category(getSubCategoryByCategory(allProduct,cCategory));
            allSCategory.add(cProduct);
        }
        ProductCategory all = new ProductCategory();
        all.setCategory_name("All");
        all.setProducts(allProduct);
        allSCategory.add(0,all);
        return allSCategory;
    }

    public static ArrayList<ProductCategory> getAllCategory(ArrayList<PosProduct> allProduct,String category,String sub_category)
    {
        ArrayList<String> allCategory = new ArrayList<String>();
        ArrayList<ProductCategory> allSCategory = new ArrayList<ProductCategory>();
        ArrayList<String> subCategory= new ArrayList<String>();
        for(PosProduct cProduct : allProduct)
        {
            if(cProduct.getType().equals(category) && cProduct.getSub_type().equals(sub_category) && !allCategory.contains(cProduct.getType()))
            {
                allCategory.add(cProduct.getType());
            }
        }
        for(String cCategory : allCategory)
        {
            ProductCategory cProduct = new ProductCategory();
            cProduct.setCategory_name(cCategory);
            cProduct.setProducts(getProductsByCategory(allProduct,cCategory,sub_category));
            cProduct.setSub_category(getSubCategoryByCategory(allProduct,cCategory));
            allSCategory.add(cProduct);
        }
        ProductCategory all = new ProductCategory();
        all.setCategory_name("All");
        all.setProducts(allProduct);
        allSCategory.add(0,all);
        return allSCategory;
    }

    public static ArrayList<ProductCategory> getAllCategory(ArrayList<PosProduct> allProduct,String category)
    {
        ArrayList<String> allCategory = new ArrayList<String>();
        ArrayList<ProductCategory> allSCategory = new ArrayList<ProductCategory>();
        ArrayList<String> subCategory= new ArrayList<String>();
        for(PosProduct cProduct : allProduct)
        {
            if(cProduct.getType().equals(category) && !allCategory.contains(cProduct.getType()))
            {
                allCategory.add(cProduct.getType());
            }
        }
        for(String cCategory : allCategory)
        {
            ProductCategory cProduct = new ProductCategory();
            cProduct.setCategory_name(cCategory);
            cProduct.setProducts(getProductsByCategory(allProduct,cCategory));
            cProduct.setSub_category(getSubCategoryByCategory(allProduct,cCategory));
            allSCategory.add(cProduct);
        }
        ProductCategory all = new ProductCategory();
        all.setCategory_name("All");
        all.setProducts(allProduct);
        allSCategory.add(0,all);
        return allSCategory;
    }

    public static ArrayList<PosProduct> getProductsByCategory(ArrayList<PosProduct> allProduct,String companyName)
    {
        ArrayList<PosProduct> products = new ArrayList<PosProduct>();

        for(PosProduct cProduct : allProduct)
        {
            if(cProduct.getType().equals(companyName))
            {
                products.add(cProduct);
            }
        }
        return products;
    }
    public static ArrayList<PosProduct> getProductsByCategory(ArrayList<PosProduct> allProduct,String companyName,String sub_category)
    {
        ArrayList<PosProduct> products = new ArrayList<PosProduct>();

        for(PosProduct cProduct : allProduct)
        {
            if(cProduct.getType().equals(companyName) && cProduct.getSub_type().equals(sub_category))
            {
                products.add(cProduct);
            }
        }
        return products;
    }
    public static ArrayList<String> getSubCategoryByCategory(ArrayList<PosProduct> allProduct,String companyName)
    {
        ArrayList<String> sub_type = new ArrayList<String>();

        for(PosProduct cProduct : allProduct)
        {
            if(cProduct.getType().equals(companyName) && !sub_type.contains(cProduct.getSub_type()))
            {
                sub_type.add(cProduct.getSub_type());
            }
        }
        return sub_type;
    }
    public static ArrayList<PosProduct> findProductByCategory(ArrayList<ProductCategory> posProuct,String category)
    {
       for(ProductCategory cProduct : posProuct)
        {
            if(cProduct.getCategory_name().toUpperCase().equals(category.toUpperCase()))
            {
                return cProduct.getProducts();
            }
        }
        return new ArrayList<PosProduct>();
    }



    public void setSub_category(ArrayList<String> sub_category) {
        this.sub_category = sub_category;
    }

    public ArrayList<String> getSub_category() {
        return sub_category;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setProducts(ArrayList<PosProduct> products) {
        this.products = products;
    }

    public String getCategory_name() {
        return category_name;
    }

    public ArrayList<PosProduct> getProducts() {
        return products;
    }
}
