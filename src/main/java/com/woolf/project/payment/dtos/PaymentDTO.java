package com.woolf.project.payment.dtos;

import com.woolf.project.payment.models.PaymentModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String orderId; // Razorpay order ID (e.g., "order_123")
    private String paymentId; // Razorpay payment ID (e.g., "pay_123")
    private String refundId; //Razorpay refund ID
    private String status;
    private String paymentLink;

    public static PaymentDTO from(PaymentModel payment, String paymentLink) {
        PaymentDTO paymentDto = from(payment);
        paymentDto.setPaymentLink(paymentLink);
        return paymentDto;
    }

    public static PaymentDTO from(PaymentModel payment) {
        PaymentDTO paymentDto = new PaymentDTO();
        paymentDto.setOrderId(payment.getOrderId());
        paymentDto.setPaymentId(payment.getPaymentId());
        paymentDto.setRefundId(payment.getRefundId());
        paymentDto.setStatus(payment.getStatus());
        return paymentDto;
    }
}
