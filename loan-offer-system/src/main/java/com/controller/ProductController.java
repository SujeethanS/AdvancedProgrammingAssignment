package com.controller;

import com.dto.product.Request.NewBrandReq;
import com.dto.product.Request.NewCategoryReq;
import com.dto.product.Request.NewProductReq;
import com.dto.product.Request.ProductDetailsReq;
import com.dto.response.LoanOfferResponse;

public interface ProductController {

    /**
     * getProductDetails
     * @param productDetailsReq
     * @return
     */
    public LoanOfferResponse getProductDetails(ProductDetailsReq productDetailsReq);

    /**
     * getAllCategory
     * @return
     */
    public LoanOfferResponse getAllCategory();

    /**
     * getAllBrand
     * @return
     */
    public LoanOfferResponse getAllBrand();

    /**
     * createNewProduct
     * @param newProductReq
     * @return
     */
    public LoanOfferResponse createNewProduct(NewProductReq newProductReq);

    /**
     * createNewCategory
     * @param newCategory
     * @return
     */
    public LoanOfferResponse createNewCategory(NewCategoryReq newCategory);

    /**
     * createNewBrand
     * @param newBrandReq
     * @return
     */
    public LoanOfferResponse createNewBrand(NewBrandReq newBrandReq);
}
