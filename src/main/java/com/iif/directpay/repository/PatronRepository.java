package com.iif.directpay.repository;

import com.iif.directpay.domain.Patron;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Patron entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    @Query("select patron from Patron patron where patron.patronUserId.login = ?#{principal.username}")
    List<Patron> findByPatronUserIdIsCurrentUser();

    @Query("select patron from Patron patron where patron.receipientUserId.login = ?#{principal.username}")
    List<Patron> findByReceipientUserIdIsCurrentUser();
}
