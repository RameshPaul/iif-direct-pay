package com.iif.directpay.domain;

import com.iif.directpay.domain.enumeration.SubscriptionStatus;
import com.iif.directpay.domain.enumeration.SubscriptionType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubscriptionPlan.
 */
@Entity
@Table(name = "subscription_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubscriptionPlan implements Serializable {

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
    private Integer subscriptionPeriod;

    @Column(name = "subscription_terms")
    private String subscriptionTerms;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubscriptionPlan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    public SubscriptionPlan subscriptionName(String subscriptionName) {
        this.setSubscriptionName(subscriptionName);
        return this;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getSubscriptionTitle() {
        return this.subscriptionTitle;
    }

    public SubscriptionPlan subscriptionTitle(String subscriptionTitle) {
        this.setSubscriptionTitle(subscriptionTitle);
        return this;
    }

    public void setSubscriptionTitle(String subscriptionTitle) {
        this.subscriptionTitle = subscriptionTitle;
    }

    public SubscriptionType getSubscriptionType() {
        return this.subscriptionType;
    }

    public SubscriptionPlan subscriptionType(SubscriptionType subscriptionType) {
        this.setSubscriptionType(subscriptionType);
        return this;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Double getSubscriptionPrice() {
        return this.subscriptionPrice;
    }

    public SubscriptionPlan subscriptionPrice(Double subscriptionPrice) {
        this.setSubscriptionPrice(subscriptionPrice);
        return this;
    }

    public void setSubscriptionPrice(Double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Integer getSubscriptionQuantity() {
        return this.subscriptionQuantity;
    }

    public SubscriptionPlan subscriptionQuantity(Integer subscriptionQuantity) {
        this.setSubscriptionQuantity(subscriptionQuantity);
        return this;
    }

    public void setSubscriptionQuantity(Integer subscriptionQuantity) {
        this.subscriptionQuantity = subscriptionQuantity;
    }

    public Integer getSubscriptionPeriod() {
        return this.subscriptionPeriod;
    }

    public SubscriptionPlan subscriptionPeriod(Integer subscriptionPeriod) {
        this.setSubscriptionPeriod(subscriptionPeriod);
        return this;
    }

    public void setSubscriptionPeriod(Integer subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }

    public String getSubscriptionTerms() {
        return this.subscriptionTerms;
    }

    public SubscriptionPlan subscriptionTerms(String subscriptionTerms) {
        this.setSubscriptionTerms(subscriptionTerms);
        return this;
    }

    public void setSubscriptionTerms(String subscriptionTerms) {
        this.subscriptionTerms = subscriptionTerms;
    }

    public SubscriptionStatus getStatus() {
        return this.status;
    }

    public SubscriptionPlan status(SubscriptionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public SubscriptionPlan createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public SubscriptionPlan updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionPlan)) {
            return false;
        }
        return id != null && id.equals(((SubscriptionPlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubscriptionPlan{" +
            "id=" + getId() +
            ", subscriptionName='" + getSubscriptionName() + "'" +
            ", subscriptionTitle='" + getSubscriptionTitle() + "'" +
            ", subscriptionType='" + getSubscriptionType() + "'" +
            ", subscriptionPrice=" + getSubscriptionPrice() +
            ", subscriptionQuantity=" + getSubscriptionQuantity() +
            ", subscriptionPeriod=" + getSubscriptionPeriod() +
            ", subscriptionTerms='" + getSubscriptionTerms() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
