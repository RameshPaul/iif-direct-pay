package com.iif.directpay.repository;

import com.iif.directpay.domain.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Query("select userAccount from UserAccount userAccount where userAccount.userId.login = ?#{principal.username}")
    List<UserAccount> findByUserIdIsCurrentUser();
}
