package com.iif.directpay.repository;

import com.iif.directpay.domain.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select payment from Payment payment where payment.patronUserId.login = ?#{principal.username}")
    List<Payment> findByPatronUserIdIsCurrentUser();

    @Query("select payment from Payment payment where payment.receipientUserId.login = ?#{principal.username}")
    List<Payment> findByReceipientUserIdIsCurrentUser();

    @Query("select payment from Payment payment where payment.flaggedUserId.login = ?#{principal.username}")
    List<Payment> findByFlaggedUserIdIsCurrentUser();

    @Query("select payment from Payment payment where payment.flagClearedUserId.login = ?#{principal.username}")
    List<Payment> findByFlagClearedUserIdIsCurrentUser();
}
