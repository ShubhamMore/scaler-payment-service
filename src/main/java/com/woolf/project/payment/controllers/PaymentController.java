package com.woolf.project.payment.controllers;

import com.razorpay.RazorpayException;
import com.woolf.project.payment.dtos.PaymentCallbackRequestDTO;
import com.woolf.project.payment.dtos.PaymentDTO;
import com.woolf.project.payment.exceptions.RefundInvalidException;
import com.woolf.project.payment.exceptions.NotFoundException;
import com.woolf.project.payment.models.PaymentModel;
import com.woolf.project.payment.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPaymentLink")
    public ResponseEntity<PaymentDTO> createPaymentLink(@RequestParam Double amount,
                                                        @RequestParam String currency,
                                                        @RequestParam String invoiceNo) throws RazorpayException {

        PaymentDTO paymentDto= paymentService.createPaymentLink(amount,currency,invoiceNo);
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentDetails(@PathVariable String orderId) throws NotFoundException {
        PaymentModel payment = paymentService.getPaymentDetails(orderId);
        return new ResponseEntity<>(PaymentDTO.from(payment,""), HttpStatus.OK);
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String paymentId) throws NotFoundException, RazorpayException {
        String response = paymentService.fetchPaymentStatus_Razorpay(paymentId).toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/callback")
    public String handlePaymentCallback(
            @RequestParam("razorpay_payment_id") String paymentId,
            @RequestParam("razorpay_payment_link_id") String paymentLinkId,
            @RequestParam("razorpay_payment_link_reference_id") String orderId,
            @RequestParam("razorpay_payment_link_status") String status,
            @RequestParam("razorpay_signature") String signature) throws RazorpayException, NotFoundException {

        PaymentCallbackRequestDTO paymentCallbackRequest = new PaymentCallbackRequestDTO();
        paymentCallbackRequest.setRazorpay_payment_id(paymentId);
        paymentCallbackRequest.setRazorpay_payment_link_id(paymentLinkId);
        paymentCallbackRequest.setRazorpay_payment_link_reference_id(orderId);
        paymentCallbackRequest.setRazorpay_payment_link_status(status);
        paymentCallbackRequest.setRazorpay_signature(signature);

        if(paymentService.verifySignature(paymentCallbackRequest))
        {
            paymentService.updatePaymentDetails(paymentCallbackRequest);
        }
        else {
            return "Signature verification failed";
        }

        return "paid".equals(status)?"Payment Successful !":"Payment Failed !";
    }

    @PostMapping("/createRefund/{orderId}")
    public ResponseEntity<PaymentDTO> createRefund(@PathVariable String orderId) throws NotFoundException, RefundInvalidException, RazorpayException {
        PaymentModel payment = paymentService.createRefund(orderId);
        return new ResponseEntity<>(PaymentDTO.from(payment), HttpStatus.CREATED);
    }

    @GetMapping("/status/refund/{refundId}")
    public ResponseEntity<String> getRefundStatus(@PathVariable String refundId) throws RazorpayException {
        String response = paymentService.fetchRefundStatus(refundId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
