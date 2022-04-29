package com.iif.directpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iif.directpay.domain.enumeration.SubscriptionStatus;
import com.iif.directpay.domain.enumeration.SubscriptionType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrganizationSubscriptionPlan.
 */
@Entity
@Table(name = "organization_subscription_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganizationSubscriptionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "subscription_name", nullable = false)
    private String subscriptionName;

    @NotNull
    @Column(name = "subscription_title", nullable = false)
    private String subscriptionTitle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type", nullable = false)
    private SubscriptionType subscriptionType;

    @NotNull
    @Column(name = "subscription_price", nullable = false)
    private Double subscriptionPrice;

    @NotNull
    @Column(name = "subscription_quantity", nullable = false)
    private Integer subscriptionQuantity;

    @NotNull
    @Column(name = "subscription_period", nullable = false)
    private String subscriptionPeriod;

    @Column(name = "subscription_terms")
    private String subscriptionTerms;

    @Column(name = "coupon_code")
    private String couponCode;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "suspended_date")
    private Instant suspendedDate;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization organizationId;

    @ManyToOne
    private SubscriptionPlan subscriptionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationSubscriptionPlan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    public OrganizationSubscriptionPlan subscriptionName(String subscriptionName) {
        this.setSubscriptionName(subscriptionName);
        return this;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getSubscriptionTitle() {
        return this.subscriptionTitle;
    }

    public OrganizationSubscriptionPlan subscriptionTitle(String subscriptionTitle) {
        this.setSubscriptionTitle(subscriptionTitle);
        return this;
    }

    public void setSubscriptionTitle(String subscriptionTitle) {
        this.subscriptionTitle = subscriptionTitle;
    }

    public SubscriptionType getSubscriptionType() {
        return this.subscriptionType;
    }

    public OrganizationSubscriptionPlan subscriptionType(SubscriptionType subscriptionType) {
        this.setSubscriptionType(subscriptionType);
        return this;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Double getSubscriptionPrice() {
        return this.subscriptionPrice;
    }

    public OrganizationSubscriptionPlan subscriptionPrice(Double subscriptionPrice) {
        this.setSubscriptionPrice(subscriptionPrice);
        return this;
    }

    public void setSubscriptionPrice(Double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Integer getSubscriptionQuantity() {
        return this.subscriptionQuantity;
    }

    public OrganizationSubscriptionPlan subscriptionQuantity(Integer subscriptionQuantity) {
        this.setSubscriptionQuantity(subscriptionQuantity);
        return this;
    }

    public void setSubscriptionQuantity(Integer subscriptionQuantity) {
        this.subscriptionQuantity = subscriptionQuantity;
    }

    public String getSubscriptionPeriod() {
        return this.subscriptionPeriod;
    }

    public OrganizationSubscriptionPlan subscriptionPeriod(String subscriptionPeriod) {
        this.setSubscriptionPeriod(subscriptionPeriod);
        return this;
    }

    public void setSubscriptionPeriod(String subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }

    public String getSubscriptionTerms() {
        return this.subscriptionTerms;
    }

    public OrganizationSubscriptionPlan subscriptionTerms(String subscriptionTerms) {
        this.setSubscriptionTerms(subscriptionTerms);
        return this;
    }

    public void setSubscriptionTerms(String subscriptionTerms) {
        this.subscriptionTerms = subscriptionTerms;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public OrganizationSubscriptionPlan couponCode(String couponCode) {
        this.setCouponCode(couponCode);
        return this;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public OrganizationSubscriptionPlan startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public OrganizationSubscriptionPlan endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Instant getSuspendedDate() {
        return this.suspendedDate;
    }

    public OrganizationSubscriptionPlan suspendedDate(Instant suspendedDate) {
        this.setSuspendedDate(suspendedDate);
        return this;
    }

    public void setSuspendedDate(Instant suspendedDate) {
        this.suspendedDate = suspendedDate;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public OrganizationSubscriptionPlan deletedDate(Instant deletedDate) {
        this.setDeletedDate(deletedDate);
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public SubscriptionStatus getStatus() {
        return this.status;
    }

    public OrganizationSubscriptionPlan status(SubscriptionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OrganizationSubscriptionPlan createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public OrganizationSubscriptionPlan updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Organization getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Organization organization) {
        this.organizationId = organization;
    }

    public OrganizationSubscriptionPlan organizationId(Organization organization) {
        this.setOrganizationId(organization);
        return this;
    }

    public SubscriptionPlan getSubscriptionId() {
        return this.subscriptionId;
    }

    public void setSubscriptionId(SubscriptionPlan subscriptionPlan) {
        this.subscriptionId = subscriptionPlan;
    }

    public OrganizationSubscriptionPlan subscriptionId(SubscriptionPlan subscriptionPlan) {
        this.setSubscriptionId(subscriptionPlan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationSubscriptionPlan)) {
            return false;
        }
        return id != null && id.equals(((OrganizationSubscriptionPlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationSubscriptionPlan{" +
            "id=" + getId() +
            ", subscriptionName='" + getSubscriptionName() + "'" +
            ", subscriptionTitle='" + getSubscriptionTitle() + "'" +
            ", subscriptionType='" + getSubscriptionType() + "'" +
            ", subscriptionPrice=" + getSubscriptionPrice() +
            ", subscriptionQuantity=" + getSubscriptionQuantity() +
            ", subscriptionPeriod='" + getSubscriptionPeriod() + "'" +
            ", subscriptionTerms='" + getSubscriptionTerms() + "'" +
            ", couponCode='" + getCouponCode() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", suspendedDate='" + getSuspendedDate() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
