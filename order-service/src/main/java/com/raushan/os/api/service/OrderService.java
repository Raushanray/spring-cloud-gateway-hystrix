package com.raushan.os.api.service;

import com.raushan.os.api.common.Payment;
import com.raushan.os.api.common.TransactionRequest;
import com.raushan.os.api.common.TransactionResponse;
import com.raushan.os.api.entity.Order;
import com.raushan.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private RestTemplate template;
    @Autowired
    private OrderRepository repository;

    public TransactionResponse saveOrder(TransactionRequest request){
        String respose="";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        //rest call
       Payment paymentResponse =  template.postForObject("http://localhost:9191/payment/doPayment",payment,Payment.class);
        respose= paymentResponse.getPaymentStatus().equals("success")?
                "payment processing successful and order placed":
                "there is a failure in payment api , order added to cart";

         repository.save(order);
         return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),respose);
    }
}
