package com.iif.directpay.domain;

import com.iif.directpay.domain.enumeration.UserLoginType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserLogin.
 */
@Entity
@Table(name = "user_login")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type")
    private UserLoginType loginType;

    @Column(name = "email_otp")
    private String emailOTP;

    @Column(name = "phone_otp")
    private String phoneOTP;

    @Column(name = "email_otp_expiry_date")
    private LocalDate emailOTPExpiryDate;

    @Column(name = "phone_otp_expiry_date")
    private LocalDate phoneOTPExpiryDate;

    @NotNull
    @Column(name = "location_ip", nullable = false)
    private String locationIP;

    @NotNull
    @Column(name = "location_details", nullable = false)
    private String locationDetails;

    @NotNull
    @Column(name = "latlog", nullable = false)
    private String latlog;

    @NotNull
    @Column(name = "browser", nullable = false)
    private String browser;

    @NotNull
    @Column(name = "device", nullable = false)
    private String device;

    @Column(name = "login_date_time")
    private Instant loginDateTime;

    @Column(name = "login_token")
    private String loginToken;

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

    public UserLogin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserLoginType getLoginType() {
        return this.loginType;
    }

    public UserLogin loginType(UserLoginType loginType) {
        this.setLoginType(loginType);
        return this;
    }

    public void setLoginType(UserLoginType loginType) {
        this.loginType = loginType;
    }

    public String getEmailOTP() {
        return this.emailOTP;
    }

    public UserLogin emailOTP(String emailOTP) {
        this.setEmailOTP(emailOTP);
        return this;
    }

    public void setEmailOTP(String emailOTP) {
        this.emailOTP = emailOTP;
    }

    public String getPhoneOTP() {
        return this.phoneOTP;
    }

    public UserLogin phoneOTP(String phoneOTP) {
        this.setPhoneOTP(phoneOTP);
        return this;
    }

    public void setPhoneOTP(String phoneOTP) {
        this.phoneOTP = phoneOTP;
    }

    public LocalDate getEmailOTPExpiryDate() {
        return this.emailOTPExpiryDate;
    }

    public UserLogin emailOTPExpiryDate(LocalDate emailOTPExpiryDate) {
        this.setEmailOTPExpiryDate(emailOTPExpiryDate);
        return this;
    }

    public void setEmailOTPExpiryDate(LocalDate emailOTPExpiryDate) {
        this.emailOTPExpiryDate = emailOTPExpiryDate;
    }

    public LocalDate getPhoneOTPExpiryDate() {
        return this.phoneOTPExpiryDate;
    }

    public UserLogin phoneOTPExpiryDate(LocalDate phoneOTPExpiryDate) {
        this.setPhoneOTPExpiryDate(phoneOTPExpiryDate);
        return this;
    }

    public void setPhoneOTPExpiryDate(LocalDate phoneOTPExpiryDate) {
        this.phoneOTPExpiryDate = phoneOTPExpiryDate;
    }

    public String getLocationIP() {
        return this.locationIP;
    }

    public UserLogin locationIP(String locationIP) {
        this.setLocationIP(locationIP);
        return this;
    }

    public void setLocationIP(String locationIP) {
        this.locationIP = locationIP;
    }

    public String getLocationDetails() {
        return this.locationDetails;
    }

    public UserLogin locationDetails(String locationDetails) {
        this.setLocationDetails(locationDetails);
        return this;
    }

    public void setLocationDetails(String locationDetails) {
        this.locationDetails = locationDetails;
    }

    public String getLatlog() {
        return this.latlog;
    }

    public UserLogin latlog(String latlog) {
        this.setLatlog(latlog);
        return this;
    }

    public void setLatlog(String latlog) {
        this.latlog = latlog;
    }

    public String getBrowser() {
        return this.browser;
    }

    public UserLogin browser(String browser) {
        this.setBrowser(browser);
        return this;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDevice() {
        return this.device;
    }

    public UserLogin device(String device) {
        this.setDevice(device);
        return this;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Instant getLoginDateTime() {
        return this.loginDateTime;
    }

    public UserLogin loginDateTime(Instant loginDateTime) {
        this.setLoginDateTime(loginDateTime);
        return this;
    }

    public void setLoginDateTime(Instant loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public String getLoginToken() {
        return this.loginToken;
    }

    public UserLogin loginToken(String loginToken) {
        this.setLoginToken(loginToken);
        return this;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public UserLogin createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public UserLogin updatedDate(Instant updatedDate) {
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

    public UserLogin userId(User user) {
        this.setUserId(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserLogin)) {
            return false;
        }
        return id != null && id.equals(((UserLogin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserLogin{" +
            "id=" + getId() +
            ", loginType='" + getLoginType() + "'" +
            ", emailOTP='" + getEmailOTP() + "'" +
            ", phoneOTP='" + getPhoneOTP() + "'" +
            ", emailOTPExpiryDate='" + getEmailOTPExpiryDate() + "'" +
            ", phoneOTPExpiryDate='" + getPhoneOTPExpiryDate() + "'" +
            ", locationIP='" + getLocationIP() + "'" +
            ", locationDetails='" + getLocationDetails() + "'" +
            ", latlog='" + getLatlog() + "'" +
            ", browser='" + getBrowser() + "'" +
            ", device='" + getDevice() + "'" +
            ", loginDateTime='" + getLoginDateTime() + "'" +
            ", loginToken='" + getLoginToken() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
