package com.junit.product;

import com.business.ProductBusiness;
import com.dto.product.Request.NewBrandReq;
import com.dto.product.Request.NewCategoryReq;
import com.dto.product.Request.NewProductReq;
import com.dto.product.Request.ProductDetailsReq;
import com.dto.product.Response.BrandRes;
import com.dto.product.Response.CategoryRes;
import com.dto.product.Response.ProductDetailsRes;
import com.dto.response.GeneralResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductBusinessTest {

    @Autowired
    ProductBusiness productBusiness;

    @Test
    public void createNewProductTest(){

        NewProductReq newProductReq = new NewProductReq();

        newProductReq.setProductName("mouse");
        newProductReq.setBrandId(1);
        newProductReq.setCategoryId(1);
        newProductReq.setSalePrice(100);

        GeneralResponse response = productBusiness.createNewProduct(newProductReq);
        assertEquals(true, response.isRes());
    }

    @Test
    public void createNewCategoryTest(){

        NewCategoryReq newCategoryReq = new NewCategoryReq();

        newCategoryReq.setCategoryName("Computer");

        GeneralResponse response = productBusiness.createNewCategory(newCategoryReq);
        assertEquals(true, response.isRes());
    }

    @Test
    public void createNewBrandTest(){

        NewBrandReq newBrandReq = new NewBrandReq();

        newBrandReq.setBrandName("Dell");

        GeneralResponse response = productBusiness.createNewBrand(newBrandReq);
        assertEquals(true, response.isRes());
    }

    @Test
    public void GetAllProductTest(){

        ProductDetailsReq productDetailsReq = new ProductDetailsReq();

        productDetailsReq.setBrand("");
        productDetailsReq.setCategory("");
        List<ProductDetailsRes> response = productBusiness.getProductDetails(productDetailsReq);
        assertEquals(true, response.size() > 0);
    }

    @Test
    public void GetProductTest(){

        ProductDetailsReq productDetailsReq = new ProductDetailsReq();

        productDetailsReq.setBrand("Dell");
        productDetailsReq.setCategory("Computers");
        List<ProductDetailsRes> response = productBusiness.getProductDetails(productDetailsReq);
        assertEquals(true, response.size() > 0);
    }

    @Test
    public void GetAllCategoryTest(){

        List<CategoryRes> response = productBusiness.getAllCategory();
        assertEquals(true, response.size() > 0);
    }

    @Test
    public void GetAllBrandTest(){

        List<BrandRes> response = productBusiness.getAllBrand();
        assertEquals(true, response.size() > 0);
    }
}
