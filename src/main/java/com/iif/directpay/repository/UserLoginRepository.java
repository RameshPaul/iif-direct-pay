package com.iif.directpay.repository;

import com.iif.directpay.domain.UserLogin;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserLogin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    @Query("select userLogin from UserLogin userLogin where userLogin.userId.login = ?#{principal.username}")
    List<UserLogin> findByUserIdIsCurrentUser();
}
