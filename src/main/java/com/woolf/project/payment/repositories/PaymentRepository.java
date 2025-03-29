package com.woolf.project.payment.repositories;

import com.woolf.project.payment.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {

    Optional<PaymentModel> findByOrderId(String orderId);
    Optional<PaymentModel> findByPaymentId(String paymentId);

}
