package com.junit.order;

import com.business.OrderBusiness;
import com.dto.order.Request.GetAllOrderReq;
import com.dto.order.Request.OrderDetailsReq;
import com.dto.order.Request.OrderReq;
import com.dto.order.Request.PaymentReq;
import com.dto.order.response.GetAllOrderRes;
import com.dto.order.response.OrderDetailRes;
import com.dto.order.response.PaymentRes;
import com.dto.response.GeneralResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderBusinessTest {

    @Autowired
    OrderBusiness orderBusiness;

    @Test
    public void createOrderTest(){
        OrderReq orderReq = new OrderReq();

        orderReq.setOrderDetails("1||2,2||3,4||1");
        orderReq.setInstallmentStatus(1);
        orderReq.setUserId(1);

        GeneralResponse response = orderBusiness.createOrder(orderReq);
        assertEquals(true, response.isRes());
    }

    @Test
    public void GetAllOrdersTest(){
        GetAllOrderReq getAllOrderReq = new GetAllOrderReq();

        getAllOrderReq.setUserId(0);

        List<GetAllOrderRes> response = orderBusiness.getAllOrders(getAllOrderReq);
        assertEquals(true, response.size() > 0);
    }

    @Test
    public void GetOrdersByUserTest(){
        GetAllOrderReq getAllOrderReq = new GetAllOrderReq();

        getAllOrderReq.setUserId(1);

        List<GetAllOrderRes> response = orderBusiness.getAllOrders(getAllOrderReq);
        assertEquals(true, response.size() > 0);
    }

    @Test
    public void GetOrdersDetailsByOrderTest(){
        OrderDetailsReq orderDetailsReq = new OrderDetailsReq();

        orderDetailsReq.setOrderId(3);

        List<OrderDetailRes> response = orderBusiness.getAllOrderDetails(orderDetailsReq);
        assertEquals(true, response.size() > 0);
    }

    @Test
    public void GetOrdersPaymentByPaymentTest(){
        PaymentReq paymentReq = new PaymentReq();

        paymentReq.setOrderId(3);

        List<PaymentRes> response = orderBusiness.getAllPaymentDetails(paymentReq);
        assertEquals(true, response.size() > 0);
    }
}
