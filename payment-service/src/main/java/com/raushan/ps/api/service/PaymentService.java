package com.raushan.ps.api.service;

import com.raushan.ps.api.entity.Payment;
import com.raushan.ps.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository repository;

    public Payment doPayment(Payment payment){
        payment.setTransactionId(UUID.randomUUID().toString());
        return repository.save(payment);
    }
}
