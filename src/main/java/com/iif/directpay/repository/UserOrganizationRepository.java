package com.iif.directpay.repository;

import com.iif.directpay.domain.UserOrganization;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Long> {
    @Query("select userOrganization from UserOrganization userOrganization where userOrganization.userId.login = ?#{principal.username}")
    List<UserOrganization> findByUserIdIsCurrentUser();
}
