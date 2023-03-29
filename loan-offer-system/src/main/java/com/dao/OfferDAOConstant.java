package com.dao;
public class OfferDAOConstant {

    public interface UserConstant{

        String INSERT_UPDATE_ADMIN_CUSTOMER = "{call insert_update_admin_customer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        String GET_CUSTOMER_DETAIL = "{call get_customer_detail(?)}";
        String GET_CUSTOMER_LIST = "{call get_customer_list()}";
        String LOGIN_USER = "{call user_login(?,?,?,?,?,?,?,?,?,?,?,?)}";
        String GET_INSTALLMENT_PLAN_LIST = "{call get_all_intallment_plans_proc()}";
        String GET_DASHBOARD_SUMMARY = "{call get_dashboard_summary_proc(?,?,?,?,?,?,?,?)}";
        String GET_PRODUCT_DETAILS = "{call get_all_products_proc(?,?)}";
        String GET_ALL_CATEGORY = "{call get_all_category_proc()}";
        String GET_ALL_BRAND = "{call get_all_brand_proc()}";
        String INSERT_ORDER_DETAILS = "{call create_order_request_proc(?,?,?,?,?,?,?)}";
        String GET_ALL_ORDERS = "{call get_all_orders_proc(?)}";
        String GET_ALL_ORDER_DETAILS = "{call get_order_details_proc(?)}";
        String GET_ALL_PAYMENT_DETAILS = "{call get_all_payment_history_proc(?)}";
        String INSERT_PRODUCT_DETAILS = "{call create_new_product_proc(?,?,?,?,?,?,?)}";
        String INSERT_CATEGORY_DETAILS = "{call create_new_category_proc(?,?,?,?)}";
        String INSERT_BRAND_DETAILS = "{call create_new_brand_proc(?,?,?,?)}";
    }
}
