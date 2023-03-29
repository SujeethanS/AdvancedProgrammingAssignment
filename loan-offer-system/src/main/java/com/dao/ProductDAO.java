package com.dao;

import com.dto.product.Request.NewBrandReq;
import com.dto.product.Request.NewCategoryReq;
import com.dto.product.Request.NewProductReq;
import com.dto.product.Request.ProductDetailsReq;
import com.dto.product.Response.BrandRes;
import com.dto.product.Response.CategoryRes;
import com.dto.product.Response.ProductDetailsRes;
import com.dto.response.GeneralResponse;

import java.util.List;

public interface ProductDAO {

    /**
     * getProductDetails
     * @param productDetailsReq
     * @return
     */
    List<ProductDetailsRes> getProductDetails(ProductDetailsReq productDetailsReq);

    /**
     * getAllCategory
     * @return
     */
    List<CategoryRes> getAllCategory();

    /**
     * getAllBrand
     * @return
     */
    List<BrandRes> getAllBrand();

    /**
     *
     * @param newProductReq
     * @return
     */
    GeneralResponse createNewProduct(NewProductReq newProductReq);

    /**
     * createNewCategory
     * @param newCategory
     * @return
     */
    GeneralResponse createNewCategory(NewCategoryReq newCategory);

    /**
     * createNewBrand
     * @param newBrandReq
     * @return
     */
    GeneralResponse createNewBrand(NewBrandReq newBrandReq);
}
