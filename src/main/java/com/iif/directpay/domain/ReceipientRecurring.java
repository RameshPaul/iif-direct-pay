package com.iif.directpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iif.directpay.domain.enumeration.ReceipientRecurringStatus;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReceipientRecurring.
 */
@Entity
@Table(name = "receipient_recurring")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReceipientRecurring implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recurring_period")
    private Integer recurringPeriod;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "amount_requisite")
    private Double amountRequisite;

    @Column(name = "amount_patron_commited")
    private Double amountPatronCommited;

    @Column(name = "amount_received")
    private Double amountReceived;

    @Column(name = "amount_balance")
    private Double amountBalance;

    @Column(name = "total_patrons")
    private Integer totalPatrons;

    @Column(name = "details_text")
    private String detailsText;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReceipientRecurringStatus status;

    @Column(name = "pause_date")
    private LocalDate pauseDate;

    @Column(name = "resume_date")
    private LocalDate resumeDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

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
    private User userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReceipientRecurring id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRecurringPeriod() {
        return this.recurringPeriod;
    }

    public ReceipientRecurring recurringPeriod(Integer recurringPeriod) {
        this.setRecurringPeriod(recurringPeriod);
        return this;
    }

    public void setRecurringPeriod(Integer recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public ReceipientRecurring startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public ReceipientRecurring endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getAmountRequisite() {
        return this.amountRequisite;
    }

    public ReceipientRecurring amountRequisite(Double amountRequisite) {
        this.setAmountRequisite(amountRequisite);
        return this;
    }

    public void setAmountRequisite(Double amountRequisite) {
        this.amountRequisite = amountRequisite;
    }

    public Double getAmountPatronCommited() {
        return this.amountPatronCommited;
    }

    public ReceipientRecurring amountPatronCommited(Double amountPatronCommited) {
        this.setAmountPatronCommited(amountPatronCommited);
        return this;
    }

    public void setAmountPatronCommited(Double amountPatronCommited) {
        this.amountPatronCommited = amountPatronCommited;
    }

    public Double getAmountReceived() {
        return this.amountReceived;
    }

    public ReceipientRecurring amountReceived(Double amountReceived) {
        this.setAmountReceived(amountReceived);
        return this;
    }

    public void setAmountReceived(Double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public Double getAmountBalance() {
        return this.amountBalance;
    }

    public ReceipientRecurring amountBalance(Double amountBalance) {
        this.setAmountBalance(amountBalance);
        return this;
    }

    public void setAmountBalance(Double amountBalance) {
        this.amountBalance = amountBalance;
    }

    public Integer getTotalPatrons() {
        return this.totalPatrons;
    }

    public ReceipientRecurring totalPatrons(Integer totalPatrons) {
        this.setTotalPatrons(totalPatrons);
        return this;
    }

    public void setTotalPatrons(Integer totalPatrons) {
        this.totalPatrons = totalPatrons;
    }

    public String getDetailsText() {
        return this.detailsText;
    }

    public ReceipientRecurring detailsText(String detailsText) {
        this.setDetailsText(detailsText);
        return this;
    }

    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }

    public ReceipientRecurringStatus getStatus() {
        return this.status;
    }

    public ReceipientRecurring status(ReceipientRecurringStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ReceipientRecurringStatus status) {
        this.status = status;
    }

    public LocalDate getPauseDate() {
        return this.pauseDate;
    }

    public ReceipientRecurring pauseDate(LocalDate pauseDate) {
        this.setPauseDate(pauseDate);
        return this;
    }

    public void setPauseDate(LocalDate pauseDate) {
        this.pauseDate = pauseDate;
    }

    public LocalDate getResumeDate() {
        return this.resumeDate;
    }

    public ReceipientRecurring resumeDate(LocalDate resumeDate) {
        this.setResumeDate(resumeDate);
        return this;
    }

    public void setResumeDate(LocalDate resumeDate) {
        this.resumeDate = resumeDate;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ReceipientRecurring createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public ReceipientRecurring updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Receipient getReceipientId() {
        return this.receipientId;
    }

    public void setReceipientId(Receipient receipient) {
        this.receipientId = receipient;
    }

    public ReceipientRecurring receipientId(Receipient receipient) {
        this.setReceipientId(receipient);
        return this;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public ReceipientRecurring userId(User user) {
        this.setUserId(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceipientRecurring)) {
            return false;
        }
        return id != null && id.equals(((ReceipientRecurring) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceipientRecurring{" +
            "id=" + getId() +
            ", recurringPeriod=" + getRecurringPeriod() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", amountRequisite=" + getAmountRequisite() +
            ", amountPatronCommited=" + getAmountPatronCommited() +
            ", amountReceived=" + getAmountReceived() +
            ", amountBalance=" + getAmountBalance() +
            ", totalPatrons=" + getTotalPatrons() +
            ", detailsText='" + getDetailsText() + "'" +
            ", status='" + getStatus() + "'" +
            ", pauseDate='" + getPauseDate() + "'" +
            ", resumeDate='" + getResumeDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
