package com.iif.directpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iif.directpay.domain.enumeration.UserAccountBankStatus;
import com.iif.directpay.domain.enumeration.UserAccountType;
import com.iif.directpay.domain.enumeration.UserAccountUpiStatus;
import com.iif.directpay.domain.enumeration.UserAccountWalletStatus;
import com.iif.directpay.domain.enumeration.UserAccountWalletType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserAccount.
 */
@Entity
@Table(name = "user_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private UserAccountType accountType;

    @Column(name = "upi_address")
    private String upiAddress;

    @Column(name = "mobile_number")
    private Integer mobileNumber;

    @Column(name = "upi_active_date")
    private LocalDate upiActiveDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "upi_status")
    private UserAccountUpiStatus upiStatus;

    @Column(name = "upi_suspended_date")
    private Instant upiSuspendedDate;

    @Column(name = "upi_deleted_date")
    private Instant upiDeletedDate;

    @Column(name = "upi_auto_debit_enabled")
    private Boolean upiAutoDebitEnabled;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_account_number")
    private Integer bankAccountNumber;

    @Column(name = "bank_ifsc_code")
    private String bankIFSCCode;

    @Column(name = "bank_swift_code")
    private String bankSWIFTCode;

    @Column(name = "bank_branch_address")
    private String bankBranchAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_status")
    private UserAccountBankStatus bankStatus;

    @Column(name = "bank_active_date")
    private Instant bankActiveDate;

    @Column(name = "bank_suspended_date")
    private Instant bankSuspendedDate;

    @Column(name = "bank_deleted_date")
    private Instant bankDeletedDate;

    @Column(name = "bank_auto_debit_enabled")
    private Boolean bankAutoDebitEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "wallet_type")
    private UserAccountWalletType walletType;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "wallet_status")
    private UserAccountWalletStatus walletStatus;

    @Column(name = "wallter_active_date")
    private Instant wallterActiveDate;

    @Column(name = "wallet_suspended_date")
    private Instant walletSuspendedDate;

    @Column(name = "wallet_deleted_date")
    private Instant walletDeletedDate;

    @Column(name = "wallet_auto_debit_enabled")
    private Boolean walletAutoDebitEnabled;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne
    private User userId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization organizationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccountType getAccountType() {
        return this.accountType;
    }

    public UserAccount accountType(UserAccountType accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(UserAccountType accountType) {
        this.accountType = accountType;
    }

    public String getUpiAddress() {
        return this.upiAddress;
    }

    public UserAccount upiAddress(String upiAddress) {
        this.setUpiAddress(upiAddress);
        return this;
    }

    public void setUpiAddress(String upiAddress) {
        this.upiAddress = upiAddress;
    }

    public Integer getMobileNumber() {
        return this.mobileNumber;
    }

    public UserAccount mobileNumber(Integer mobileNumber) {
        this.setMobileNumber(mobileNumber);
        return this;
    }

    public void setMobileNumber(Integer mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public LocalDate getUpiActiveDate() {
        return this.upiActiveDate;
    }

    public UserAccount upiActiveDate(LocalDate upiActiveDate) {
        this.setUpiActiveDate(upiActiveDate);
        return this;
    }

    public void setUpiActiveDate(LocalDate upiActiveDate) {
        this.upiActiveDate = upiActiveDate;
    }

    public UserAccountUpiStatus getUpiStatus() {
        return this.upiStatus;
    }

    public UserAccount upiStatus(UserAccountUpiStatus upiStatus) {
        this.setUpiStatus(upiStatus);
        return this;
    }

    public void setUpiStatus(UserAccountUpiStatus upiStatus) {
        this.upiStatus = upiStatus;
    }

    public Instant getUpiSuspendedDate() {
        return this.upiSuspendedDate;
    }

    public UserAccount upiSuspendedDate(Instant upiSuspendedDate) {
        this.setUpiSuspendedDate(upiSuspendedDate);
        return this;
    }

    public void setUpiSuspendedDate(Instant upiSuspendedDate) {
        this.upiSuspendedDate = upiSuspendedDate;
    }

    public Instant getUpiDeletedDate() {
        return this.upiDeletedDate;
    }

    public UserAccount upiDeletedDate(Instant upiDeletedDate) {
        this.setUpiDeletedDate(upiDeletedDate);
        return this;
    }

    public void setUpiDeletedDate(Instant upiDeletedDate) {
        this.upiDeletedDate = upiDeletedDate;
    }

    public Boolean getUpiAutoDebitEnabled() {
        return this.upiAutoDebitEnabled;
    }

    public UserAccount upiAutoDebitEnabled(Boolean upiAutoDebitEnabled) {
        this.setUpiAutoDebitEnabled(upiAutoDebitEnabled);
        return this;
    }

    public void setUpiAutoDebitEnabled(Boolean upiAutoDebitEnabled) {
        this.upiAutoDebitEnabled = upiAutoDebitEnabled;
    }

    public String getBankName() {
        return this.bankName;
    }

    public UserAccount bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    public UserAccount bankAccountNumber(Integer bankAccountNumber) {
        this.setBankAccountNumber(bankAccountNumber);
        return this;
    }

    public void setBankAccountNumber(Integer bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankIFSCCode() {
        return this.bankIFSCCode;
    }

    public UserAccount bankIFSCCode(String bankIFSCCode) {
        this.setBankIFSCCode(bankIFSCCode);
        return this;
    }

    public void setBankIFSCCode(String bankIFSCCode) {
        this.bankIFSCCode = bankIFSCCode;
    }

    public String getBankSWIFTCode() {
        return this.bankSWIFTCode;
    }

    public UserAccount bankSWIFTCode(String bankSWIFTCode) {
        this.setBankSWIFTCode(bankSWIFTCode);
        return this;
    }

    public void setBankSWIFTCode(String bankSWIFTCode) {
        this.bankSWIFTCode = bankSWIFTCode;
    }

    public String getBankBranchAddress() {
        return this.bankBranchAddress;
    }

    public UserAccount bankBranchAddress(String bankBranchAddress) {
        this.setBankBranchAddress(bankBranchAddress);
        return this;
    }

    public void setBankBranchAddress(String bankBranchAddress) {
        this.bankBranchAddress = bankBranchAddress;
    }

    public UserAccountBankStatus getBankStatus() {
        return this.bankStatus;
    }

    public UserAccount bankStatus(UserAccountBankStatus bankStatus) {
        this.setBankStatus(bankStatus);
        return this;
    }

    public void setBankStatus(UserAccountBankStatus bankStatus) {
        this.bankStatus = bankStatus;
    }

    public Instant getBankActiveDate() {
        return this.bankActiveDate;
    }

    public UserAccount bankActiveDate(Instant bankActiveDate) {
        this.setBankActiveDate(bankActiveDate);
        return this;
    }

    public void setBankActiveDate(Instant bankActiveDate) {
        this.bankActiveDate = bankActiveDate;
    }

    public Instant getBankSuspendedDate() {
        return this.bankSuspendedDate;
    }

    public UserAccount bankSuspendedDate(Instant bankSuspendedDate) {
        this.setBankSuspendedDate(bankSuspendedDate);
        return this;
    }

    public void setBankSuspendedDate(Instant bankSuspendedDate) {
        this.bankSuspendedDate = bankSuspendedDate;
    }

    public Instant getBankDeletedDate() {
        return this.bankDeletedDate;
    }

    public UserAccount bankDeletedDate(Instant bankDeletedDate) {
        this.setBankDeletedDate(bankDeletedDate);
        return this;
    }

    public void setBankDeletedDate(Instant bankDeletedDate) {
        this.bankDeletedDate = bankDeletedDate;
    }

    public Boolean getBankAutoDebitEnabled() {
        return this.bankAutoDebitEnabled;
    }

    public UserAccount bankAutoDebitEnabled(Boolean bankAutoDebitEnabled) {
        this.setBankAutoDebitEnabled(bankAutoDebitEnabled);
        return this;
    }

    public void setBankAutoDebitEnabled(Boolean bankAutoDebitEnabled) {
        this.bankAutoDebitEnabled = bankAutoDebitEnabled;
    }

    public UserAccountWalletType getWalletType() {
        return this.walletType;
    }

    public UserAccount walletType(UserAccountWalletType walletType) {
        this.setWalletType(walletType);
        return this;
    }

    public void setWalletType(UserAccountWalletType walletType) {
        this.walletType = walletType;
    }

    public String getWalletAddress() {
        return this.walletAddress;
    }

    public UserAccount walletAddress(String walletAddress) {
        this.setWalletAddress(walletAddress);
        return this;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public UserAccountWalletStatus getWalletStatus() {
        return this.walletStatus;
    }

    public UserAccount walletStatus(UserAccountWalletStatus walletStatus) {
        this.setWalletStatus(walletStatus);
        return this;
    }

    public void setWalletStatus(UserAccountWalletStatus walletStatus) {
        this.walletStatus = walletStatus;
    }

    public Instant getWallterActiveDate() {
        return this.wallterActiveDate;
    }

    public UserAccount wallterActiveDate(Instant wallterActiveDate) {
        this.setWallterActiveDate(wallterActiveDate);
        return this;
    }

    public void setWallterActiveDate(Instant wallterActiveDate) {
        this.wallterActiveDate = wallterActiveDate;
    }

    public Instant getWalletSuspendedDate() {
        return this.walletSuspendedDate;
    }

    public UserAccount walletSuspendedDate(Instant walletSuspendedDate) {
        this.setWalletSuspendedDate(walletSuspendedDate);
        return this;
    }

    public void setWalletSuspendedDate(Instant walletSuspendedDate) {
        this.walletSuspendedDate = walletSuspendedDate;
    }

    public Instant getWalletDeletedDate() {
        return this.walletDeletedDate;
    }

    public UserAccount walletDeletedDate(Instant walletDeletedDate) {
        this.setWalletDeletedDate(walletDeletedDate);
        return this;
    }

    public void setWalletDeletedDate(Instant walletDeletedDate) {
        this.walletDeletedDate = walletDeletedDate;
    }

    public Boolean getWalletAutoDebitEnabled() {
        return this.walletAutoDebitEnabled;
    }

    public UserAccount walletAutoDebitEnabled(Boolean walletAutoDebitEnabled) {
        this.setWalletAutoDebitEnabled(walletAutoDebitEnabled);
        return this;
    }

    public void setWalletAutoDebitEnabled(Boolean walletAutoDebitEnabled) {
        this.walletAutoDebitEnabled = walletAutoDebitEnabled;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public UserAccount createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public UserAccount updatedDate(Instant updatedDate) {
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

    public UserAccount userId(User user) {
        this.setUserId(user);
        return this;
    }

    public Organization getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Organization organization) {
        this.organizationId = organization;
    }

    public UserAccount organizationId(Organization organization) {
        this.setOrganizationId(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccount)) {
            return false;
        }
        return id != null && id.equals(((UserAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", accountType='" + getAccountType() + "'" +
            ", upiAddress='" + getUpiAddress() + "'" +
            ", mobileNumber=" + getMobileNumber() +
            ", upiActiveDate='" + getUpiActiveDate() + "'" +
            ", upiStatus='" + getUpiStatus() + "'" +
            ", upiSuspendedDate='" + getUpiSuspendedDate() + "'" +
            ", upiDeletedDate='" + getUpiDeletedDate() + "'" +
            ", upiAutoDebitEnabled='" + getUpiAutoDebitEnabled() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankAccountNumber=" + getBankAccountNumber() +
            ", bankIFSCCode='" + getBankIFSCCode() + "'" +
            ", bankSWIFTCode='" + getBankSWIFTCode() + "'" +
            ", bankBranchAddress='" + getBankBranchAddress() + "'" +
            ", bankStatus='" + getBankStatus() + "'" +
            ", bankActiveDate='" + getBankActiveDate() + "'" +
            ", bankSuspendedDate='" + getBankSuspendedDate() + "'" +
            ", bankDeletedDate='" + getBankDeletedDate() + "'" +
            ", bankAutoDebitEnabled='" + getBankAutoDebitEnabled() + "'" +
            ", walletType='" + getWalletType() + "'" +
            ", walletAddress='" + getWalletAddress() + "'" +
            ", walletStatus='" + getWalletStatus() + "'" +
            ", wallterActiveDate='" + getWallterActiveDate() + "'" +
            ", walletSuspendedDate='" + getWalletSuspendedDate() + "'" +
            ", walletDeletedDate='" + getWalletDeletedDate() + "'" +
            ", walletAutoDebitEnabled='" + getWalletAutoDebitEnabled() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
