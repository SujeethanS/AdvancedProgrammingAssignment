package com.dao.impl;

import com.dao.OfferDAOConstant;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Repository
public class ProductDAOImpl implements ProductDAO {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<ProductDetailsRes> getProductDetails(ProductDetailsReq productDetailsReq) {
        long startTime = System.currentTimeMillis();
        List<ProductDetailsRes> productDetailsResList = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            logger.info("getProductDetails-request------------------>"+productDetailsReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_PRODUCT_DETAILS);
            callableStatement.setObject(1,productDetailsReq.getCategory(), Types.VARCHAR);
            callableStatement.setObject(2,productDetailsReq.getBrand(),Types.VARCHAR);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                productDetailsResList = new ArrayList<>();
                while (resultSet.next()){
                    ProductDetailsRes response = new ProductDetailsRes();
                    response.setProductId(resultSet.getInt(1));
                    response.setProductDetails(resultSet.getString(2));

                    productDetailsResList.add(response);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getProductDetails "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getProductDetails in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return productDetailsResList;
    }

    @Override
    public List<CategoryRes> getAllCategory() {
        long startTime = System.currentTimeMillis();
        List<CategoryRes> categoryList = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_ALL_CATEGORY);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                categoryList = new ArrayList<>();
                while (resultSet.next()){
                    CategoryRes categoryRes = new CategoryRes();
                    categoryRes.setCategoryId(resultSet.getInt(1));
                    categoryRes.setCategoryName(resultSet.getString(2));
                    categoryList.add(categoryRes);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getAllCategory "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getAllCategory in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return categoryList;
    }

    @Override
    public List<BrandRes> getAllBrand() {
        long startTime = System.currentTimeMillis();
        List<BrandRes> brandList = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_ALL_BRAND);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                brandList = new ArrayList<>();
                while (resultSet.next()){
                    BrandRes brandRes = new BrandRes();
                    brandRes.setBrandId(resultSet.getInt(1));
                    brandRes.setBrandName(resultSet.getString(2));
                    brandList.add(brandRes);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getAllCategory "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getAllCategory in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return brandList;
    }

    @Override
    public GeneralResponse createNewProduct(NewProductReq newProductReq) {
        long startTime = System.currentTimeMillis();
        GeneralResponse response = new GeneralResponse();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            logger.info("createNewProduct-request------------------>"+newProductReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.INSERT_PRODUCT_DETAILS);
            callableStatement.setObject(1,newProductReq.getProductName(), Types.VARCHAR);
            callableStatement.setObject(2,newProductReq.getCategoryId(),Types.INTEGER);
            callableStatement.setObject(3,newProductReq.getBrandId(),Types.INTEGER);
            callableStatement.setObject(4,newProductReq.getSalePrice(),Types.DOUBLE);

            callableStatement.registerOutParameter(5,Types.BOOLEAN);
            callableStatement.registerOutParameter(6,Types.INTEGER);
            callableStatement.registerOutParameter(7,Types.VARCHAR);

            callableStatement.executeUpdate();
            callableStatement.getResultSet();
            response.setRes((Boolean) callableStatement.getObject(5));
            response.setStatusCode((Integer) callableStatement.getObject(6));
            response.setMsg((String) callableStatement.getObject(7));
        }catch (SQLException exception){
            logger.info("An error occurred in createNewProduct "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for createNewProduct in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

    @Override
    public GeneralResponse createNewCategory(NewCategoryReq newCategory) {
        long startTime = System.currentTimeMillis();
        GeneralResponse response = new GeneralResponse();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            logger.info("createNewCategory-request------------------>"+newCategory.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.INSERT_CATEGORY_DETAILS);
            callableStatement.setObject(1,newCategory.getCategoryName(), Types.VARCHAR);

            callableStatement.registerOutParameter(2,Types.BOOLEAN);
            callableStatement.registerOutParameter(3,Types.INTEGER);
            callableStatement.registerOutParameter(4,Types.VARCHAR);

            callableStatement.executeUpdate();
            callableStatement.getResultSet();
            response.setRes((Boolean) callableStatement.getObject(2));
            response.setStatusCode((Integer) callableStatement.getObject(3));
            response.setMsg((String) callableStatement.getObject(4));
        }catch (SQLException exception){
            logger.info("An error occurred in createNewCategory "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for createNewCategory in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

    @Override
    public GeneralResponse createNewBrand(NewBrandReq newBrandReq) {
        long startTime = System.currentTimeMillis();
        GeneralResponse response = new GeneralResponse();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            logger.info("createNewBrand-request------------------>"+newBrandReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.INSERT_BRAND_DETAILS);
            callableStatement.setObject(1,newBrandReq.getBrandName(), Types.VARCHAR);

            callableStatement.registerOutParameter(2,Types.BOOLEAN);
            callableStatement.registerOutParameter(3,Types.INTEGER);
            callableStatement.registerOutParameter(4,Types.VARCHAR);

            callableStatement.executeUpdate();
            callableStatement.getResultSet();
            response.setRes((Boolean) callableStatement.getObject(2));
            response.setStatusCode((Integer) callableStatement.getObject(3));
            response.setMsg((String) callableStatement.getObject(4));
        }catch (SQLException exception){
            logger.info("An error occurred in createNewBrand "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for createNewBrand in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }
}
