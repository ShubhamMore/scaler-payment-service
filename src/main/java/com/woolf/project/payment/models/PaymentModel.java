package com.woolf.project.payment.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "payment")
public class PaymentModel extends BaseModel{
    private String orderId;
    private String paymentId;
    private String refundId;
    private String status;
    private Double amount;
    private String currency;
    private String invoiceNumber;
}
