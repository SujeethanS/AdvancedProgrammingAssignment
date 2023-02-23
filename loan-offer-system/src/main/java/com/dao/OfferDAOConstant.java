package com.dao;

public class OfferDAOConstant {

    public interface UserConstant{

        String INSERT_UPDATE_ADMIN_CUSTOMER = "{call insert_update_admin_customer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

        String GET_CUSTOMER_DETAIL = "{call get_customer_detail(?)}";

        String GET_CUSTOMER_LIST = "{call get_customer_list()}";

        String LOGIN_USER = "{call user_login(?,?,?,?,?,?,?,?,?,?,?)}";

        String GET_INSTALLMENT_PLAN_LIST = "{call get_all_intallment_plans_proc()}";

    }
}
