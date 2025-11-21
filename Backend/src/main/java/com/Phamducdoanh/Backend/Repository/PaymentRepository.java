package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByTransactionId(String transactionId);
}
