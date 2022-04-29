package com.iif.directpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iif.directpay.domain.enumeration.PaymentStatus;
import com.iif.directpay.domain.enumeration.PaymentType;
import com.iif.directpay.domain.enumeration.UserAccountType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recurring_period")
    private LocalDate recurringPeriod;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transaction_id")
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_source")
    private UserAccountType paymentSource;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "payment_status_details")
    private String paymentStatusDetails;

    @Column(name = "payment_start_date_time")
    private LocalDate paymentStartDateTime;

    @Column(name = "payment_complete_date_time")
    private LocalDate paymentCompleteDateTime;

    @Column(name = "payment_failure_date_time")
    private LocalDate paymentFailureDateTime;

    @Column(name = "patron_comment")
    private String patronComment;

    @Column(name = "is_auto_pay")
    private Boolean isAutoPay;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_destination_source")
    private UserAccountType paymentDestinationSource;

    @Column(name = "payment_received_date_t_ime")
    private LocalDate paymentReceivedDateTIme;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_received_status")
    private PaymentStatus paymentReceivedStatus;

    @Column(name = "payment_received_details")
    private String paymentReceivedDetails;

    @Column(name = "payment_refunded_date_time")
    private LocalDate paymentRefundedDateTime;

    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "flagged_date_time")
    private LocalDate flaggedDateTime;

    @Column(name = "flag_details")
    private String flagDetails;

    @Column(name = "flagged_email_id")
    private String flaggedEmailId;

    @Column(name = "flagged_amount")
    private Double flaggedAmount;

    @Column(name = "flag_created_date_time")
    private LocalDate flagCreatedDateTime;

    @Column(name = "is_recurring_payment")
    private Boolean isRecurringPayment;

    @Column(name = "transaction_details")
    private String transactionDetails;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "patronUserId", "patronUserOrgId", "receipientId", "receipientUserId", "receipientUserOrgId" },
        allowSetters = true
    )
    private Patron patronId;

    @ManyToOne
    private User patronUserId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization patronUserOrgId;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "userId",
            "organizationId",
            "managedOrganiztionId",
            "createdUserId",
            "createdUserOrgId",
            "approvedUserId",
            "approvedUserOrgId",
            "rejectedUserId",
            "rejectedUserOrgId",
        },
        allowSetters = true
    )
    private Receipient receipientId;

    @ManyToOne
    private User receipientUserId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization receipientUserOrgId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userId", "organizationId" }, allowSetters = true)
    private UserAccount paymentSourceAccountId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userId", "organizationId" }, allowSetters = true)
    private UserAccount paymentDestinationAccountId;

    @ManyToOne
    private User flaggedUserId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization flaggedUserOrgId;

    @ManyToOne
    private User flagClearedUserId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization flagClearedUserOrgId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRecurringPeriod() {
        return this.recurringPeriod;
    }

    public Payment recurringPeriod(LocalDate recurringPeriod) {
        this.setRecurringPeriod(recurringPeriod);
        return this;
    }

    public void setRecurringPeriod(LocalDate recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Payment amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public Payment transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public Payment paymentType(PaymentType paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public UserAccountType getPaymentSource() {
        return this.paymentSource;
    }

    public Payment paymentSource(UserAccountType paymentSource) {
        this.setPaymentSource(paymentSource);
        return this;
    }

    public void setPaymentSource(UserAccountType paymentSource) {
        this.paymentSource = paymentSource;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public Payment paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatusDetails() {
        return this.paymentStatusDetails;
    }

    public Payment paymentStatusDetails(String paymentStatusDetails) {
        this.setPaymentStatusDetails(paymentStatusDetails);
        return this;
    }

    public void setPaymentStatusDetails(String paymentStatusDetails) {
        this.paymentStatusDetails = paymentStatusDetails;
    }

    public LocalDate getPaymentStartDateTime() {
        return this.paymentStartDateTime;
    }

    public Payment paymentStartDateTime(LocalDate paymentStartDateTime) {
        this.setPaymentStartDateTime(paymentStartDateTime);
        return this;
    }

    public void setPaymentStartDateTime(LocalDate paymentStartDateTime) {
        this.paymentStartDateTime = paymentStartDateTime;
    }

    public LocalDate getPaymentCompleteDateTime() {
        return this.paymentCompleteDateTime;
    }

    public Payment paymentCompleteDateTime(LocalDate paymentCompleteDateTime) {
        this.setPaymentCompleteDateTime(paymentCompleteDateTime);
        return this;
    }

    public void setPaymentCompleteDateTime(LocalDate paymentCompleteDateTime) {
        this.paymentCompleteDateTime = paymentCompleteDateTime;
    }

    public LocalDate getPaymentFailureDateTime() {
        return this.paymentFailureDateTime;
    }

    public Payment paymentFailureDateTime(LocalDate paymentFailureDateTime) {
        this.setPaymentFailureDateTime(paymentFailureDateTime);
        return this;
    }

    public void setPaymentFailureDateTime(LocalDate paymentFailureDateTime) {
        this.paymentFailureDateTime = paymentFailureDateTime;
    }

    public String getPatronComment() {
        return this.patronComment;
    }

    public Payment patronComment(String patronComment) {
        this.setPatronComment(patronComment);
        return this;
    }

    public void setPatronComment(String patronComment) {
        this.patronComment = patronComment;
    }

    public Boolean getIsAutoPay() {
        return this.isAutoPay;
    }

    public Payment isAutoPay(Boolean isAutoPay) {
        this.setIsAutoPay(isAutoPay);
        return this;
    }

    public void setIsAutoPay(Boolean isAutoPay) {
        this.isAutoPay = isAutoPay;
    }

    public UserAccountType getPaymentDestinationSource() {
        return this.paymentDestinationSource;
    }

    public Payment paymentDestinationSource(UserAccountType paymentDestinationSource) {
        this.setPaymentDestinationSource(paymentDestinationSource);
        return this;
    }

    public void setPaymentDestinationSource(UserAccountType paymentDestinationSource) {
        this.paymentDestinationSource = paymentDestinationSource;
    }

    public LocalDate getPaymentReceivedDateTIme() {
        return this.paymentReceivedDateTIme;
    }

    public Payment paymentReceivedDateTIme(LocalDate paymentReceivedDateTIme) {
        this.setPaymentReceivedDateTIme(paymentReceivedDateTIme);
        return this;
    }

    public void setPaymentReceivedDateTIme(LocalDate paymentReceivedDateTIme) {
        this.paymentReceivedDateTIme = paymentReceivedDateTIme;
    }

    public PaymentStatus getPaymentReceivedStatus() {
        return this.paymentReceivedStatus;
    }

    public Payment paymentReceivedStatus(PaymentStatus paymentReceivedStatus) {
        this.setPaymentReceivedStatus(paymentReceivedStatus);
        return this;
    }

    public void setPaymentReceivedStatus(PaymentStatus paymentReceivedStatus) {
        this.paymentReceivedStatus = paymentReceivedStatus;
    }

    public String getPaymentReceivedDetails() {
        return this.paymentReceivedDetails;
    }

    public Payment paymentReceivedDetails(String paymentReceivedDetails) {
        this.setPaymentReceivedDetails(paymentReceivedDetails);
        return this;
    }

    public void setPaymentReceivedDetails(String paymentReceivedDetails) {
        this.paymentReceivedDetails = paymentReceivedDetails;
    }

    public LocalDate getPaymentRefundedDateTime() {
        return this.paymentRefundedDateTime;
    }

    public Payment paymentRefundedDateTime(LocalDate paymentRefundedDateTime) {
        this.setPaymentRefundedDateTime(paymentRefundedDateTime);
        return this;
    }

    public void setPaymentRefundedDateTime(LocalDate paymentRefundedDateTime) {
        this.paymentRefundedDateTime = paymentRefundedDateTime;
    }

    public String getUserComment() {
        return this.userComment;
    }

    public Payment userComment(String userComment) {
        this.setUserComment(userComment);
        return this;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public LocalDate getFlaggedDateTime() {
        return this.flaggedDateTime;
    }

    public Payment flaggedDateTime(LocalDate flaggedDateTime) {
        this.setFlaggedDateTime(flaggedDateTime);
        return this;
    }

    public void setFlaggedDateTime(LocalDate flaggedDateTime) {
        this.flaggedDateTime = flaggedDateTime;
    }

    public String getFlagDetails() {
        return this.flagDetails;
    }

    public Payment flagDetails(String flagDetails) {
        this.setFlagDetails(flagDetails);
        return this;
    }

    public void setFlagDetails(String flagDetails) {
        this.flagDetails = flagDetails;
    }

    public String getFlaggedEmailId() {
        return this.flaggedEmailId;
    }

    public Payment flaggedEmailId(String flaggedEmailId) {
        this.setFlaggedEmailId(flaggedEmailId);
        return this;
    }

    public void setFlaggedEmailId(String flaggedEmailId) {
        this.flaggedEmailId = flaggedEmailId;
    }

    public Double getFlaggedAmount() {
        return this.flaggedAmount;
    }

    public Payment flaggedAmount(Double flaggedAmount) {
        this.setFlaggedAmount(flaggedAmount);
        return this;
    }

    public void setFlaggedAmount(Double flaggedAmount) {
        this.flaggedAmount = flaggedAmount;
    }

    public LocalDate getFlagCreatedDateTime() {
        return this.flagCreatedDateTime;
    }

    public Payment flagCreatedDateTime(LocalDate flagCreatedDateTime) {
        this.setFlagCreatedDateTime(flagCreatedDateTime);
        return this;
    }

    public void setFlagCreatedDateTime(LocalDate flagCreatedDateTime) {
        this.flagCreatedDateTime = flagCreatedDateTime;
    }

    public Boolean getIsRecurringPayment() {
        return this.isRecurringPayment;
    }

    public Payment isRecurringPayment(Boolean isRecurringPayment) {
        this.setIsRecurringPayment(isRecurringPayment);
        return this;
    }

    public void setIsRecurringPayment(Boolean isRecurringPayment) {
        this.isRecurringPayment = isRecurringPayment;
    }

    public String getTransactionDetails() {
        return this.transactionDetails;
    }

    public Payment transactionDetails(String transactionDetails) {
        this.setTransactionDetails(transactionDetails);
        return this;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Payment createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Payment updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Patron getPatronId() {
        return this.patronId;
    }

    public void setPatronId(Patron patron) {
        this.patronId = patron;
    }

    public Payment patronId(Patron patron) {
        this.setPatronId(patron);
        return this;
    }

    public User getPatronUserId() {
        return this.patronUserId;
    }

    public void setPatronUserId(User user) {
        this.patronUserId = user;
    }

    public Payment patronUserId(User user) {
        this.setPatronUserId(user);
        return this;
    }

    public Organization getPatronUserOrgId() {
        return this.patronUserOrgId;
    }

    public void setPatronUserOrgId(Organization organization) {
        this.patronUserOrgId = organization;
    }

    public Payment patronUserOrgId(Organization organization) {
        this.setPatronUserOrgId(organization);
        return this;
    }

    public Receipient getReceipientId() {
        return this.receipientId;
    }

    public void setReceipientId(Receipient receipient) {
        this.receipientId = receipient;
    }

    public Payment receipientId(Receipient receipient) {
        this.setReceipientId(receipient);
        return this;
    }

    public User getReceipientUserId() {
        return this.receipientUserId;
    }

    public void setReceipientUserId(User user) {
        this.receipientUserId = user;
    }

    public Payment receipientUserId(User user) {
        this.setReceipientUserId(user);
        return this;
    }

    public Organization getReceipientUserOrgId() {
        return this.receipientUserOrgId;
    }

    public void setReceipientUserOrgId(Organization organization) {
        this.receipientUserOrgId = organization;
    }

    public Payment receipientUserOrgId(Organization organization) {
        this.setReceipientUserOrgId(organization);
        return this;
    }

    public UserAccount getPaymentSourceAccountId() {
        return this.paymentSourceAccountId;
    }

    public void setPaymentSourceAccountId(UserAccount userAccount) {
        this.paymentSourceAccountId = userAccount;
    }

    public Payment paymentSourceAccountId(UserAccount userAccount) {
        this.setPaymentSourceAccountId(userAccount);
        return this;
    }

    public UserAccount getPaymentDestinationAccountId() {
        return this.paymentDestinationAccountId;
    }

    public void setPaymentDestinationAccountId(UserAccount userAccount) {
        this.paymentDestinationAccountId = userAccount;
    }

    public Payment paymentDestinationAccountId(UserAccount userAccount) {
        this.setPaymentDestinationAccountId(userAccount);
        return this;
    }

    public User getFlaggedUserId() {
        return this.flaggedUserId;
    }

    public void setFlaggedUserId(User user) {
        this.flaggedUserId = user;
    }

    public Payment flaggedUserId(User user) {
        this.setFlaggedUserId(user);
        return this;
    }

    public Organization getFlaggedUserOrgId() {
        return this.flaggedUserOrgId;
    }

    public void setFlaggedUserOrgId(Organization organization) {
        this.flaggedUserOrgId = organization;
    }

    public Payment flaggedUserOrgId(Organization organization) {
        this.setFlaggedUserOrgId(organization);
        return this;
    }

    public User getFlagClearedUserId() {
        return this.flagClearedUserId;
    }

    public void setFlagClearedUserId(User user) {
        this.flagClearedUserId = user;
    }

    public Payment flagClearedUserId(User user) {
        this.setFlagClearedUserId(user);
        return this;
    }

    public Organization getFlagClearedUserOrgId() {
        return this.flagClearedUserOrgId;
    }

    public void setFlagClearedUserOrgId(Organization organization) {
        this.flagClearedUserOrgId = organization;
    }

    public Payment flagClearedUserOrgId(Organization organization) {
        this.setFlagClearedUserOrgId(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", recurringPeriod='" + getRecurringPeriod() + "'" +
            ", amount=" + getAmount() +
            ", transactionId='" + getTransactionId() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", paymentSource='" + getPaymentSource() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", paymentStatusDetails='" + getPaymentStatusDetails() + "'" +
            ", paymentStartDateTime='" + getPaymentStartDateTime() + "'" +
            ", paymentCompleteDateTime='" + getPaymentCompleteDateTime() + "'" +
            ", paymentFailureDateTime='" + getPaymentFailureDateTime() + "'" +
            ", patronComment='" + getPatronComment() + "'" +
            ", isAutoPay='" + getIsAutoPay() + "'" +
            ", paymentDestinationSource='" + getPaymentDestinationSource() + "'" +
            ", paymentReceivedDateTIme='" + getPaymentReceivedDateTIme() + "'" +
            ", paymentReceivedStatus='" + getPaymentReceivedStatus() + "'" +
            ", paymentReceivedDetails='" + getPaymentReceivedDetails() + "'" +
            ", paymentRefundedDateTime='" + getPaymentRefundedDateTime() + "'" +
            ", userComment='" + getUserComment() + "'" +
            ", flaggedDateTime='" + getFlaggedDateTime() + "'" +
            ", flagDetails='" + getFlagDetails() + "'" +
            ", flaggedEmailId='" + getFlaggedEmailId() + "'" +
            ", flaggedAmount=" + getFlaggedAmount() +
            ", flagCreatedDateTime='" + getFlagCreatedDateTime() + "'" +
            ", isRecurringPayment='" + getIsRecurringPayment() + "'" +
            ", transactionDetails='" + getTransactionDetails() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
