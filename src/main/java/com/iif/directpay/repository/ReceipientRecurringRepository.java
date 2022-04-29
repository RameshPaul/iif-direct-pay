package com.iif.directpay.repository;

import com.iif.directpay.domain.ReceipientRecurring;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReceipientRecurring entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceipientRecurringRepository extends JpaRepository<ReceipientRecurring, Long> {
    @Query(
        "select receipientRecurring from ReceipientRecurring receipientRecurring where receipientRecurring.userId.login = ?#{principal.username}"
    )
    List<ReceipientRecurring> findByUserIdIsCurrentUser();
}
