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
 * A Patron.
 */
@Entity
@Table(name = "patron")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Patron implements Serializable {

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

    @Column(name = "recurring_period")
    private LocalDate recurringPeriod;

    @Column(name = "enable_reminder")
    private Boolean enableReminder;

    @Column(name = "is_auto_pay")
    private Boolean isAutoPay;

    @Column(name = "amount_receipient_requisite")
    private Double amountReceipientRequisite;

    @Column(name = "amount_patron_pledge")
    private Double amountPatronPledge;

    @Column(name = "amount_patron_actual")
    private Double amountPatronActual;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReceipientPatronStatus status;

    @Column(name = "commited_start_date")
    private LocalDate commitedStartDate;

    @Column(name = "commited_end_date")
    private LocalDate commitedEndDate;

    @Column(name = "actual_start_date")
    private LocalDate actualStartDate;

    @Column(name = "actual_end_date")
    private LocalDate actualEndDate;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Patron id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsRecurring() {
        return this.isRecurring;
    }

    public Patron isRecurring(Boolean isRecurring) {
        this.setIsRecurring(isRecurring);
        return this;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public ReceipientPatronRecurringType getRecurringType() {
        return this.recurringType;
    }

    public Patron recurringType(ReceipientPatronRecurringType recurringType) {
        this.setRecurringType(recurringType);
        return this;
    }

    public void setRecurringType(ReceipientPatronRecurringType recurringType) {
        this.recurringType = recurringType;
    }

    public LocalDate getRecurringPeriod() {
        return this.recurringPeriod;
    }

    public Patron recurringPeriod(LocalDate recurringPeriod) {
        this.setRecurringPeriod(recurringPeriod);
        return this;
    }

    public void setRecurringPeriod(LocalDate recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }

    public Boolean getEnableReminder() {
        return this.enableReminder;
    }

    public Patron enableReminder(Boolean enableReminder) {
        this.setEnableReminder(enableReminder);
        return this;
    }

    public void setEnableReminder(Boolean enableReminder) {
        this.enableReminder = enableReminder;
    }

    public Boolean getIsAutoPay() {
        return this.isAutoPay;
    }

    public Patron isAutoPay(Boolean isAutoPay) {
        this.setIsAutoPay(isAutoPay);
        return this;
    }

    public void setIsAutoPay(Boolean isAutoPay) {
        this.isAutoPay = isAutoPay;
    }

    public Double getAmountReceipientRequisite() {
        return this.amountReceipientRequisite;
    }

    public Patron amountReceipientRequisite(Double amountReceipientRequisite) {
        this.setAmountReceipientRequisite(amountReceipientRequisite);
        return this;
    }

    public void setAmountReceipientRequisite(Double amountReceipientRequisite) {
        this.amountReceipientRequisite = amountReceipientRequisite;
    }

    public Double getAmountPatronPledge() {
        return this.amountPatronPledge;
    }

    public Patron amountPatronPledge(Double amountPatronPledge) {
        this.setAmountPatronPledge(amountPatronPledge);
        return this;
    }

    public void setAmountPatronPledge(Double amountPatronPledge) {
        this.amountPatronPledge = amountPatronPledge;
    }

    public Double getAmountPatronActual() {
        return this.amountPatronActual;
    }

    public Patron amountPatronActual(Double amountPatronActual) {
        this.setAmountPatronActual(amountPatronActual);
        return this;
    }

    public void setAmountPatronActual(Double amountPatronActual) {
        this.amountPatronActual = amountPatronActual;
    }

    public ReceipientPatronStatus getStatus() {
        return this.status;
    }

    public Patron status(ReceipientPatronStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ReceipientPatronStatus status) {
        this.status = status;
    }

    public LocalDate getCommitedStartDate() {
        return this.commitedStartDate;
    }

    public Patron commitedStartDate(LocalDate commitedStartDate) {
        this.setCommitedStartDate(commitedStartDate);
        return this;
    }

    public void setCommitedStartDate(LocalDate commitedStartDate) {
        this.commitedStartDate = commitedStartDate;
    }

    public LocalDate getCommitedEndDate() {
        return this.commitedEndDate;
    }

    public Patron commitedEndDate(LocalDate commitedEndDate) {
        this.setCommitedEndDate(commitedEndDate);
        return this;
    }

    public void setCommitedEndDate(LocalDate commitedEndDate) {
        this.commitedEndDate = commitedEndDate;
    }

    public LocalDate getActualStartDate() {
        return this.actualStartDate;
    }

    public Patron actualStartDate(LocalDate actualStartDate) {
        this.setActualStartDate(actualStartDate);
        return this;
    }

    public void setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public LocalDate getActualEndDate() {
        return this.actualEndDate;
    }

    public Patron actualEndDate(LocalDate actualEndDate) {
        this.setActualEndDate(actualEndDate);
        return this;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public LocalDate getReccuringPauseDate() {
        return this.reccuringPauseDate;
    }

    public Patron reccuringPauseDate(LocalDate reccuringPauseDate) {
        this.setReccuringPauseDate(reccuringPauseDate);
        return this;
    }

    public void setReccuringPauseDate(LocalDate reccuringPauseDate) {
        this.reccuringPauseDate = reccuringPauseDate;
    }

    public LocalDate getRecurringResumeDate() {
        return this.recurringResumeDate;
    }

    public Patron recurringResumeDate(LocalDate recurringResumeDate) {
        this.setRecurringResumeDate(recurringResumeDate);
        return this;
    }

    public void setRecurringResumeDate(LocalDate recurringResumeDate) {
        this.recurringResumeDate = recurringResumeDate;
    }

    public String getRecurringPauseReason() {
        return this.recurringPauseReason;
    }

    public Patron recurringPauseReason(String recurringPauseReason) {
        this.setRecurringPauseReason(recurringPauseReason);
        return this;
    }

    public void setRecurringPauseReason(String recurringPauseReason) {
        this.recurringPauseReason = recurringPauseReason;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Patron createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Patron updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public Patron deletedDate(Instant deletedDate) {
        this.setDeletedDate(deletedDate);
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public User getPatronUserId() {
        return this.patronUserId;
    }

    public void setPatronUserId(User user) {
        this.patronUserId = user;
    }

    public Patron patronUserId(User user) {
        this.setPatronUserId(user);
        return this;
    }

    public Organization getPatronUserOrgId() {
        return this.patronUserOrgId;
    }

    public void setPatronUserOrgId(Organization organization) {
        this.patronUserOrgId = organization;
    }

    public Patron patronUserOrgId(Organization organization) {
        this.setPatronUserOrgId(organization);
        return this;
    }

    public Receipient getReceipientId() {
        return this.receipientId;
    }

    public void setReceipientId(Receipient receipient) {
        this.receipientId = receipient;
    }

    public Patron receipientId(Receipient receipient) {
        this.setReceipientId(receipient);
        return this;
    }

    public User getReceipientUserId() {
        return this.receipientUserId;
    }

    public void setReceipientUserId(User user) {
        this.receipientUserId = user;
    }

    public Patron receipientUserId(User user) {
        this.setReceipientUserId(user);
        return this;
    }

    public Organization getReceipientUserOrgId() {
        return this.receipientUserOrgId;
    }

    public void setReceipientUserOrgId(Organization organization) {
        this.receipientUserOrgId = organization;
    }

    public Patron receipientUserOrgId(Organization organization) {
        this.setReceipientUserOrgId(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patron)) {
            return false;
        }
        return id != null && id.equals(((Patron) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patron{" +
            "id=" + getId() +
            ", isRecurring='" + getIsRecurring() + "'" +
            ", recurringType='" + getRecurringType() + "'" +
            ", recurringPeriod='" + getRecurringPeriod() + "'" +
            ", enableReminder='" + getEnableReminder() + "'" +
            ", isAutoPay='" + getIsAutoPay() + "'" +
            ", amountReceipientRequisite=" + getAmountReceipientRequisite() +
            ", amountPatronPledge=" + getAmountPatronPledge() +
            ", amountPatronActual=" + getAmountPatronActual() +
            ", status='" + getStatus() + "'" +
            ", commitedStartDate='" + getCommitedStartDate() + "'" +
            ", commitedEndDate='" + getCommitedEndDate() + "'" +
            ", actualStartDate='" + getActualStartDate() + "'" +
            ", actualEndDate='" + getActualEndDate() + "'" +
            ", reccuringPauseDate='" + getReccuringPauseDate() + "'" +
            ", recurringResumeDate='" + getRecurringResumeDate() + "'" +
            ", recurringPauseReason='" + getRecurringPauseReason() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
