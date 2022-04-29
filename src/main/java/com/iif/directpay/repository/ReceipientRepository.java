package com.iif.directpay.repository;

import com.iif.directpay.domain.Receipient;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Receipient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceipientRepository extends JpaRepository<Receipient, Long> {
    @Query("select receipient from Receipient receipient where receipient.userId.login = ?#{principal.username}")
    List<Receipient> findByUserIdIsCurrentUser();

    @Query("select receipient from Receipient receipient where receipient.createdUserId.login = ?#{principal.username}")
    List<Receipient> findByCreatedUserIdIsCurrentUser();

    @Query("select receipient from Receipient receipient where receipient.approvedUserId.login = ?#{principal.username}")
    List<Receipient> findByApprovedUserIdIsCurrentUser();

    @Query("select receipient from Receipient receipient where receipient.rejectedUserId.login = ?#{principal.username}")
    List<Receipient> findByRejectedUserIdIsCurrentUser();
}
