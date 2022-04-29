package com.iif.directpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iif.directpay.domain.enumeration.ReceipientPatronRecurringType;
import com.iif.directpay.domain.enumeration.ReceipientPatronStatus;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Receipient.
 */
@Entity
@Table(name = "receipient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Receipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_recurring")
    private Boolean isRecurring;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recurring_type", nullable = false)
    private ReceipientPatronRecurringType recurringType;

    @Column(name = "recurring_start_date")
    private LocalDate recurringStartDate;

    @Column(name = "recurring_end_date")
    private LocalDate recurringEndDate;

    @Column(name = "enable_reminder")
    private Boolean enableReminder;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_auto_pay")
    private Boolean isAutoPay;

    @Column(name = "amount_requisite")
    private Double amountRequisite;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReceipientPatronStatus status;

    @Column(name = "is_managed_by_org")
    private Boolean isManagedByOrg;

    @Column(name = "approved_date_time")
    private Instant approvedDateTime;

    @Column(name = "rejected_date_time")
    private Instant rejectedDateTime;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "onboarded_date")
    private LocalDate onboardedDate;

    @Column(name = "reccuring_pause_date")
    private LocalDate reccuringPauseDate;

    @Column(name = "recurring_resume_date")
    private LocalDate recurringResumeDate;

    @Column(name = "recurring_pause_reason")
    private String recurringPauseReason;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @ManyToOne
    private User userId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization organizationId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization managedOrganiztionId;

    @ManyToOne
    private User createdUserId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization createdUserOrgId;

    @ManyToOne
    private User approvedUserId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization approvedUserOrgId;

    @ManyToOne
    private User rejectedUserId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization rejectedUserOrgId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Receipient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsRecurring() {
        return this.isRecurring;
    }

    public Receipient isRecurring(Boolean isRecurring) {
        this.setIsRecurring(isRecurring);
        return this;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public ReceipientPatronRecurringType getRecurringType() {
        return this.recurringType;
    }

    public Receipient recurringType(ReceipientPatronRecurringType recurringType) {
        this.setRecurringType(recurringType);
        return this;
    }

    public void setRecurringType(ReceipientPatronRecurringType recurringType) {
        this.recurringType = recurringType;
    }

    public LocalDate getRecurringStartDate() {
        return this.recurringStartDate;
    }

    public Receipient recurringStartDate(LocalDate recurringStartDate) {
        this.setRecurringStartDate(recurringStartDate);
        return this;
    }

    public void setRecurringStartDate(LocalDate recurringStartDate) {
        this.recurringStartDate = recurringStartDate;
    }

    public LocalDate getRecurringEndDate() {
        return this.recurringEndDate;
    }

    public Receipient recurringEndDate(LocalDate recurringEndDate) {
        this.setRecurringEndDate(recurringEndDate);
        return this;
    }

    public void setRecurringEndDate(LocalDate recurringEndDate) {
        this.recurringEndDate = recurringEndDate;
    }

    public Boolean getEnableReminder() {
        return this.enableReminder;
    }

    public Receipient enableReminder(Boolean enableReminder) {
        this.setEnableReminder(enableReminder);
        return this;
    }

    public void setEnableReminder(Boolean enableReminder) {
        this.enableReminder = enableReminder;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Receipient startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Receipient endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsAutoPay() {
        return this.isAutoPay;
    }

    public Receipient isAutoPay(Boolean isAutoPay) {
        this.setIsAutoPay(isAutoPay);
        return this;
    }

    public void setIsAutoPay(Boolean isAutoPay) {
        this.isAutoPay = isAutoPay;
    }

    public Double getAmountRequisite() {
        return this.amountRequisite;
    }

    public Receipient amountRequisite(Double amountRequisite) {
        this.setAmountRequisite(amountRequisite);
        return this;
    }

    public void setAmountRequisite(Double amountRequisite) {
        this.amountRequisite = amountRequisite;
    }

    public ReceipientPatronStatus getStatus() {
        return this.status;
    }

    public Receipient status(ReceipientPatronStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ReceipientPatronStatus status) {
        this.status = status;
    }

    public Boolean getIsManagedByOrg() {
        return this.isManagedByOrg;
    }

    public Receipient isManagedByOrg(Boolean isManagedByOrg) {
        this.setIsManagedByOrg(isManagedByOrg);
        return this;
    }

    public void setIsManagedByOrg(Boolean isManagedByOrg) {
        this.isManagedByOrg = isManagedByOrg;
    }

    public Instant getApprovedDateTime() {
        return this.approvedDateTime;
    }

    public Receipient approvedDateTime(Instant approvedDateTime) {
        this.setApprovedDateTime(approvedDateTime);
        return this;
    }

    public void setApprovedDateTime(Instant approvedDateTime) {
        this.approvedDateTime = approvedDateTime;
    }

    public Instant getRejectedDateTime() {
        return this.rejectedDateTime;
    }

    public Receipient rejectedDateTime(Instant rejectedDateTime) {
        this.setRejectedDateTime(rejectedDateTime);
        return this;
    }

    public void setRejectedDateTime(Instant rejectedDateTime) {
        this.rejectedDateTime = rejectedDateTime;
    }

    public String getRejectReason() {
        return this.rejectReason;
    }

    public Receipient rejectReason(String rejectReason) {
        this.setRejectReason(rejectReason);
        return this;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public LocalDate getOnboardedDate() {
        return this.onboardedDate;
    }

    public Receipient onboardedDate(LocalDate onboardedDate) {
        this.setOnboardedDate(onboardedDate);
        return this;
    }

    public void setOnboardedDate(LocalDate onboardedDate) {
        this.onboardedDate = onboardedDate;
    }

    public LocalDate getReccuringPauseDate() {
        return this.reccuringPauseDate;
    }

    public Receipient reccuringPauseDate(LocalDate reccuringPauseDate) {
        this.setReccuringPauseDate(reccuringPauseDate);
        return this;
    }

    public void setReccuringPauseDate(LocalDate reccuringPauseDate) {
        this.reccuringPauseDate = reccuringPauseDate;
    }

    public LocalDate getRecurringResumeDate() {
        return this.recurringResumeDate;
    }

    public Receipient recurringResumeDate(LocalDate recurringResumeDate) {
        this.setRecurringResumeDate(recurringResumeDate);
        return this;
    }

    public void setRecurringResumeDate(LocalDate recurringResumeDate) {
        this.recurringResumeDate = recurringResumeDate;
    }

    public String getRecurringPauseReason() {
        return this.recurringPauseReason;
    }

    public Receipient recurringPauseReason(String recurringPauseReason) {
        this.setRecurringPauseReason(recurringPauseReason);
        return this;
    }

    public void setRecurringPauseReason(String recurringPauseReason) {
        this.recurringPauseReason = recurringPauseReason;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Receipient createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Receipient updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public Receipient deletedDate(Instant deletedDate) {
        this.setDeletedDate(deletedDate);
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public Receipient userId(User user) {
        this.setUserId(user);
        return this;
    }

    public Organization getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Organization organization) {
        this.organizationId = organization;
    }

    public Receipient organizationId(Organization organization) {
        this.setOrganizationId(organization);
        return this;
    }

    public Organization getManagedOrganiztionId() {
        return this.managedOrganiztionId;
    }

    public void setManagedOrganiztionId(Organization organization) {
        this.managedOrganiztionId = organization;
    }

    public Receipient managedOrganiztionId(Organization organization) {
        this.setManagedOrganiztionId(organization);
        return this;
    }

    public User getCreatedUserId() {
        return this.createdUserId;
    }

    public void setCreatedUserId(User user) {
        this.createdUserId = user;
    }

    public Receipient createdUserId(User user) {
        this.setCreatedUserId(user);
        return this;
    }

    public Organization getCreatedUserOrgId() {
        return this.createdUserOrgId;
    }

    public void setCreatedUserOrgId(Organization organization) {
        this.createdUserOrgId = organization;
    }

    public Receipient createdUserOrgId(Organization organization) {
        this.setCreatedUserOrgId(organization);
        return this;
    }

    public User getApprovedUserId() {
        return this.approvedUserId;
    }

    public void setApprovedUserId(User user) {
        this.approvedUserId = user;
    }

    public Receipient approvedUserId(User user) {
        this.setApprovedUserId(user);
        return this;
    }

    public Organization getApprovedUserOrgId() {
        return this.approvedUserOrgId;
    }

    public void setApprovedUserOrgId(Organization organization) {
        this.approvedUserOrgId = organization;
    }

    public Receipient approvedUserOrgId(Organization organization) {
        this.setApprovedUserOrgId(organization);
        return this;
    }

    public User getRejectedUserId() {
        return this.rejectedUserId;
    }

    public void setRejectedUserId(User user) {
        this.rejectedUserId = user;
    }

    public Receipient rejectedUserId(User user) {
        this.setRejectedUserId(user);
        return this;
    }

    public Organization getRejectedUserOrgId() {
        return this.rejectedUserOrgId;
    }

    public void setRejectedUserOrgId(Organization organization) {
        this.rejectedUserOrgId = organization;
    }

    public Receipient rejectedUserOrgId(Organization organization) {
        this.setRejectedUserOrgId(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receipient)) {
            return false;
        }
        return id != null && id.equals(((Receipient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Receipient{" +
            "id=" + getId() +
            ", isRecurring='" + getIsRecurring() + "'" +
            ", recurringType='" + getRecurringType() + "'" +
            ", recurringStartDate='" + getRecurringStartDate() + "'" +
            ", recurringEndDate='" + getRecurringEndDate() + "'" +
            ", enableReminder='" + getEnableReminder() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isAutoPay='" + getIsAutoPay() + "'" +
            ", amountRequisite=" + getAmountRequisite() +
            ", status='" + getStatus() + "'" +
            ", isManagedByOrg='" + getIsManagedByOrg() + "'" +
            ", approvedDateTime='" + getApprovedDateTime() + "'" +
            ", rejectedDateTime='" + getRejectedDateTime() + "'" +
            ", rejectReason='" + getRejectReason() + "'" +
            ", onboardedDate='" + getOnboardedDate() + "'" +
            ", reccuringPauseDate='" + getReccuringPauseDate() + "'" +
            ", recurringResumeDate='" + getRecurringResumeDate() + "'" +
            ", recurringPauseReason='" + getRecurringPauseReason() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
