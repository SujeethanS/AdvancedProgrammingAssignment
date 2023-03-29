package com.business.impl;

import com.business.ProductBusiness;
import com.dao.ProductDAO;
import com.dto.product.Request.NewBrandReq;
import com.dto.product.Request.NewCategoryReq;
import com.dto.product.Request.NewProductReq;
import com.dto.product.Request.ProductDetailsReq;
import com.dto.product.Response.BrandRes;
import com.dto.product.Response.CategoryRes;
import com.dto.product.Response.ProductDetailsRes;
import com.dto.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductBusinessImpl implements ProductBusiness {

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    ProductDAO productDAO;
    @Override
    public List<ProductDetailsRes> getProductDetails(ProductDetailsReq productDetailsReq) {
        logger.info("ProductBusinessImpl-getProductDetails-initiated");
        return productDAO.getProductDetails(productDetailsReq);
    }

    @Override
    public List<CategoryRes> getAllCategory() {
        logger.info("ProductBusinessImpl-getAllCategory-initiated");
        return productDAO.getAllCategory();
    }

    @Override
    public List<BrandRes> getAllBrand() {
        logger.info("ProductBusinessImpl-getAllBrand-initiated");
        return productDAO.getAllBrand();
    }

    @Override
    public GeneralResponse createNewProduct(NewProductReq newProductReq) {
        logger.info("ProductBusinessImpl-createNewProduct-initiated");
        return productDAO.createNewProduct(newProductReq);
    }

    @Override
    public GeneralResponse createNewCategory(NewCategoryReq newCategory) {
        logger.info("ProductBusinessImpl-createNewCategory-initiated");
        return productDAO.createNewCategory(newCategory);
    }

    @Override
    public GeneralResponse createNewBrand(NewBrandReq newBrandReq) {
        logger.info("ProductBusinessImpl-createNewBrand-initiated");
        return productDAO.createNewBrand(newBrandReq);
    }


}
