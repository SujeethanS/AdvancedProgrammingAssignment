package com.controller.impl;

import com.business.ProductBusiness;
import com.controller.ProductController;
import com.dto.product.Request.NewBrandReq;
import com.dto.product.Request.NewCategoryReq;
import com.dto.product.Request.NewProductReq;
import com.dto.product.Request.ProductDetailsReq;
import com.dto.product.Response.BrandRes;
import com.dto.product.Response.CategoryRes;
import com.dto.product.Response.ProductDetailsRes;
import com.dto.response.GeneralResponse;
import com.dto.response.LoanOfferResponse;
import com.util.ApplicationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class ProductControllerImpl implements ProductController {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    ProductBusiness productBusiness;
    @Override
    @PostMapping("/get/product/details")
    public LoanOfferResponse getProductDetails(@RequestBody ProductDetailsReq productDetailsReq) {
        logger.info("ProductControllerImpl-getProductDetails-initiated");
        List<ProductDetailsRes> productDetailsRes = productBusiness.getProductDetails(productDetailsReq);
        return LoanOfferResponse.generateResponse(
                productDetailsRes,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }
    @Override
    @PostMapping("/get/category")
    public LoanOfferResponse getAllCategory() {
        logger.info("ProductControllerImpl-getAllCategory-initiated");
        List<CategoryRes> allCategory = productBusiness.getAllCategory();
        return LoanOfferResponse.generateResponse(
                allCategory,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }
    @Override
    @PostMapping("/get/brand")
    public LoanOfferResponse getAllBrand() {
        logger.info("ProductControllerImpl-getAllBrand-initiated");
        List<BrandRes> allBrand = productBusiness.getAllBrand();
        return LoanOfferResponse.generateResponse(
                allBrand,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }
    @Override
    @PostMapping("/create/new/product")
    public LoanOfferResponse createNewProduct(@RequestBody NewProductReq newProductReq) {
        logger.info("ProductControllerImpl-createNewProduct-initiated");
        GeneralResponse response = productBusiness.createNewProduct(newProductReq);
        return LoanOfferResponse.generateResponse(null,response.getStatusCode(),response.getMsg());
    }
    @Override
    @PostMapping("/create/new/category")
    public LoanOfferResponse createNewCategory(@RequestBody NewCategoryReq newCategory) {
        logger.info("ProductControllerImpl-createNewProduct-initiated");
        GeneralResponse response = productBusiness.createNewCategory(newCategory);
        return LoanOfferResponse.generateResponse(null,response.getStatusCode(),response.getMsg());
    }
    @Override
    @PostMapping("/create/new/brand")
    public LoanOfferResponse createNewBrand(@RequestBody NewBrandReq newBrandReq) {
        logger.info("ProductControllerImpl-createNewProduct-initiated");
        GeneralResponse response = productBusiness.createNewBrand(newBrandReq);
        return LoanOfferResponse.generateResponse(null,response.getStatusCode(),response.getMsg());
    }
}
