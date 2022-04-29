package com.iif.directpay.repository;

import com.iif.directpay.domain.UserDevice;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    @Query("select userDevice from UserDevice userDevice where userDevice.userId.login = ?#{principal.username}")
    List<UserDevice> findByUserIdIsCurrentUser();
}
