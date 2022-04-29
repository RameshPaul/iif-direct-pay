package com.iif.directpay.domain;

import com.iif.directpay.domain.enumeration.UserDeviceStatus;
import com.iif.directpay.domain.enumeration.UserDeviceType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserDevice.
 */
@Entity
@Table(name = "user_device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @NotNull
    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "device_token")
    private String deviceToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private UserDeviceType deviceType;

    @NotNull
    @Column(name = "device_os", nullable = false)
    private String deviceOS;

    @NotNull
    @Column(name = "notification_enabled", nullable = false)
    private Boolean notificationEnabled;

    @NotNull
    @Column(name = "last_activity_details", nullable = false)
    private String lastActivityDetails;

    @NotNull
    @Column(name = "last_active_date", nullable = false)
    private LocalDate lastActiveDate;

    @NotNull
    @Column(name = "last_active_location", nullable = false)
    private String lastActiveLocation;

    @NotNull
    @Column(name = "app_version", nullable = false)
    private String appVersion;

    @NotNull
    @Column(name = "force_login", nullable = false)
    private Boolean forceLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserDeviceStatus status;

    @Column(name = "login_date_time")
    private Instant loginDateTime;

    @Column(name = "exit_date")
    private Instant exitDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne
    private User userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserDevice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public UserDevice deviceName(String deviceName) {
        this.setDeviceName(deviceName);
        return this;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public UserDevice deviceId(String deviceId) {
        this.setDeviceId(deviceId);
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public UserDevice deviceToken(String deviceToken) {
        this.setDeviceToken(deviceToken);
        return this;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public UserDeviceType getDeviceType() {
        return this.deviceType;
    }

    public UserDevice deviceType(UserDeviceType deviceType) {
        this.setDeviceType(deviceType);
        return this;
    }

    public void setDeviceType(UserDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceOS() {
        return this.deviceOS;
    }

    public UserDevice deviceOS(String deviceOS) {
        this.setDeviceOS(deviceOS);
        return this;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public Boolean getNotificationEnabled() {
        return this.notificationEnabled;
    }

    public UserDevice notificationEnabled(Boolean notificationEnabled) {
        this.setNotificationEnabled(notificationEnabled);
        return this;
    }

    public void setNotificationEnabled(Boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public String getLastActivityDetails() {
        return this.lastActivityDetails;
    }

    public UserDevice lastActivityDetails(String lastActivityDetails) {
        this.setLastActivityDetails(lastActivityDetails);
        return this;
    }

    public void setLastActivityDetails(String lastActivityDetails) {
        this.lastActivityDetails = lastActivityDetails;
    }

    public LocalDate getLastActiveDate() {
        return this.lastActiveDate;
    }

    public UserDevice lastActiveDate(LocalDate lastActiveDate) {
        this.setLastActiveDate(lastActiveDate);
        return this;
    }

    public void setLastActiveDate(LocalDate lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public String getLastActiveLocation() {
        return this.lastActiveLocation;
    }

    public UserDevice lastActiveLocation(String lastActiveLocation) {
        this.setLastActiveLocation(lastActiveLocation);
        return this;
    }

    public void setLastActiveLocation(String lastActiveLocation) {
        this.lastActiveLocation = lastActiveLocation;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public UserDevice appVersion(String appVersion) {
        this.setAppVersion(appVersion);
        return this;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Boolean getForceLogin() {
        return this.forceLogin;
    }

    public UserDevice forceLogin(Boolean forceLogin) {
        this.setForceLogin(forceLogin);
        return this;
    }

    public void setForceLogin(Boolean forceLogin) {
        this.forceLogin = forceLogin;
    }

    public UserDeviceStatus getStatus() {
        return this.status;
    }

    public UserDevice status(UserDeviceStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(UserDeviceStatus status) {
        this.status = status;
    }

    public Instant getLoginDateTime() {
        return this.loginDateTime;
    }

    public UserDevice loginDateTime(Instant loginDateTime) {
        this.setLoginDateTime(loginDateTime);
        return this;
    }

    public void setLoginDateTime(Instant loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public Instant getExitDate() {
        return this.exitDate;
    }

    public UserDevice exitDate(Instant exitDate) {
        this.setExitDate(exitDate);
        return this;
    }

    public void setExitDate(Instant exitDate) {
        this.exitDate = exitDate;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public UserDevice createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public UserDevice updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public UserDevice userId(User user) {
        this.setUserId(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDevice)) {
            return false;
        }
        return id != null && id.equals(((UserDevice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDevice{" +
            "id=" + getId() +
            ", deviceName='" + getDeviceName() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", deviceToken='" + getDeviceToken() + "'" +
            ", deviceType='" + getDeviceType() + "'" +
            ", deviceOS='" + getDeviceOS() + "'" +
            ", notificationEnabled='" + getNotificationEnabled() + "'" +
            ", lastActivityDetails='" + getLastActivityDetails() + "'" +
            ", lastActiveDate='" + getLastActiveDate() + "'" +
            ", lastActiveLocation='" + getLastActiveLocation() + "'" +
            ", appVersion='" + getAppVersion() + "'" +
            ", forceLogin='" + getForceLogin() + "'" +
            ", status='" + getStatus() + "'" +
            ", loginDateTime='" + getLoginDateTime() + "'" +
            ", exitDate='" + getExitDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
