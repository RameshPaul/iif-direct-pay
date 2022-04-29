package com.iif.directpay.domain;

import com.iif.directpay.domain.enumeration.OrganizationStatus;
import com.iif.directpay.domain.enumeration.OrganizationTaxCategory;
import com.iif.directpay.domain.enumeration.OrganizationType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alias_name")
    private String aliasName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "website")
    private String website;

    @NotNull
    @Column(name = "phone", nullable = false)
    private Integer phone;

    @Column(name = "mobile")
    private Integer mobile;

    @Column(name = "representative_name")
    private String representativeName;

    @Column(name = "representative_email")
    private String representativeEmail;

    @Column(name = "representative_phone")
    private Integer representativePhone;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type")
    private OrganizationType organizationType;

    @Column(name = "organization_type_other")
    private String organizationTypeOther;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_tax_category")
    private OrganizationTaxCategory organizationTaxCategory;

    @Column(name = "organization_tax_category_other")
    private String organizationTaxCategoryOther;

    @Column(name = "established_date")
    private LocalDate establishedDate;

    @Column(name = "total_employees_number")
    private Integer totalEmployeesNumber;

    @Column(name = "join_date")
    private Instant joinDate;

    @Column(name = "subscription_start_date")
    private LocalDate subscriptionStartDate;

    @Column(name = "subscription_end_date")
    private LocalDate subscriptionEndDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrganizationStatus status;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "activated_date")
    private Instant activatedDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @Column(name = "suspended_date")
    private Instant suspendedDate;

    @ManyToOne
    private SubscriptionPlan subscriptionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Organization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return this.aliasName;
    }

    public Organization aliasName(String aliasName) {
        this.setAliasName(aliasName);
        return this;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getEmail() {
        return this.email;
    }

    public Organization email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return this.website;
    }

    public Organization website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getPhone() {
        return this.phone;
    }

    public Organization phone(Integer phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getMobile() {
        return this.mobile;
    }

    public Organization mobile(Integer mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getRepresentativeName() {
        return this.representativeName;
    }

    public Organization representativeName(String representativeName) {
        this.setRepresentativeName(representativeName);
        return this;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getRepresentativeEmail() {
        return this.representativeEmail;
    }

    public Organization representativeEmail(String representativeEmail) {
        this.setRepresentativeEmail(representativeEmail);
        return this;
    }

    public void setRepresentativeEmail(String representativeEmail) {
        this.representativeEmail = representativeEmail;
    }

    public Integer getRepresentativePhone() {
        return this.representativePhone;
    }

    public Organization representativePhone(Integer representativePhone) {
        this.setRepresentativePhone(representativePhone);
        return this;
    }

    public void setRepresentativePhone(Integer representativePhone) {
        this.representativePhone = representativePhone;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Organization registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public OrganizationType getOrganizationType() {
        return this.organizationType;
    }

    public Organization organizationType(OrganizationType organizationType) {
        this.setOrganizationType(organizationType);
        return this;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationTypeOther() {
        return this.organizationTypeOther;
    }

    public Organization organizationTypeOther(String organizationTypeOther) {
        this.setOrganizationTypeOther(organizationTypeOther);
        return this;
    }

    public void setOrganizationTypeOther(String organizationTypeOther) {
        this.organizationTypeOther = organizationTypeOther;
    }

    public OrganizationTaxCategory getOrganizationTaxCategory() {
        return this.organizationTaxCategory;
    }

    public Organization organizationTaxCategory(OrganizationTaxCategory organizationTaxCategory) {
        this.setOrganizationTaxCategory(organizationTaxCategory);
        return this;
    }

    public void setOrganizationTaxCategory(OrganizationTaxCategory organizationTaxCategory) {
        this.organizationTaxCategory = organizationTaxCategory;
    }

    public String getOrganizationTaxCategoryOther() {
        return this.organizationTaxCategoryOther;
    }

    public Organization organizationTaxCategoryOther(String organizationTaxCategoryOther) {
        this.setOrganizationTaxCategoryOther(organizationTaxCategoryOther);
        return this;
    }

    public void setOrganizationTaxCategoryOther(String organizationTaxCategoryOther) {
        this.organizationTaxCategoryOther = organizationTaxCategoryOther;
    }

    public LocalDate getEstablishedDate() {
        return this.establishedDate;
    }

    public Organization establishedDate(LocalDate establishedDate) {
        this.setEstablishedDate(establishedDate);
        return this;
    }

    public void setEstablishedDate(LocalDate establishedDate) {
        this.establishedDate = establishedDate;
    }

    public Integer getTotalEmployeesNumber() {
        return this.totalEmployeesNumber;
    }

    public Organization totalEmployeesNumber(Integer totalEmployeesNumber) {
        this.setTotalEmployeesNumber(totalEmployeesNumber);
        return this;
    }

    public void setTotalEmployeesNumber(Integer totalEmployeesNumber) {
        this.totalEmployeesNumber = totalEmployeesNumber;
    }

    public Instant getJoinDate() {
        return this.joinDate;
    }

    public Organization joinDate(Instant joinDate) {
        this.setJoinDate(joinDate);
        return this;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getSubscriptionStartDate() {
        return this.subscriptionStartDate;
    }

    public Organization subscriptionStartDate(LocalDate subscriptionStartDate) {
        this.setSubscriptionStartDate(subscriptionStartDate);
        return this;
    }

    public void setSubscriptionStartDate(LocalDate subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public LocalDate getSubscriptionEndDate() {
        return this.subscriptionEndDate;
    }

    public Organization subscriptionEndDate(LocalDate subscriptionEndDate) {
        this.setSubscriptionEndDate(subscriptionEndDate);
        return this;
    }

    public void setSubscriptionEndDate(LocalDate subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public OrganizationStatus getStatus() {
        return this.status;
    }

    public Organization status(OrganizationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrganizationStatus status) {
        this.status = status;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public Organization isVerified(Boolean isVerified) {
        this.setIsVerified(isVerified);
        return this;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Instant getActivatedDate() {
        return this.activatedDate;
    }

    public Organization activatedDate(Instant activatedDate) {
        this.setActivatedDate(activatedDate);
        return this;
    }

    public void setActivatedDate(Instant activatedDate) {
        this.activatedDate = activatedDate;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Organization createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Organization updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public Organization deletedDate(Instant deletedDate) {
        this.setDeletedDate(deletedDate);
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Instant getSuspendedDate() {
        return this.suspendedDate;
    }

    public Organization suspendedDate(Instant suspendedDate) {
        this.setSuspendedDate(suspendedDate);
        return this;
    }

    public void setSuspendedDate(Instant suspendedDate) {
        this.suspendedDate = suspendedDate;
    }

    public SubscriptionPlan getSubscriptionId() {
        return this.subscriptionId;
    }

    public void setSubscriptionId(SubscriptionPlan subscriptionPlan) {
        this.subscriptionId = subscriptionPlan;
    }

    public Organization subscriptionId(SubscriptionPlan subscriptionPlan) {
        this.setSubscriptionId(subscriptionPlan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", aliasName='" + getAliasName() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", phone=" + getPhone() +
            ", mobile=" + getMobile() +
            ", representativeName='" + getRepresentativeName() + "'" +
            ", representativeEmail='" + getRepresentativeEmail() + "'" +
            ", representativePhone=" + getRepresentativePhone() +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", organizationType='" + getOrganizationType() + "'" +
            ", organizationTypeOther='" + getOrganizationTypeOther() + "'" +
            ", organizationTaxCategory='" + getOrganizationTaxCategory() + "'" +
            ", organizationTaxCategoryOther='" + getOrganizationTaxCategoryOther() + "'" +
            ", establishedDate='" + getEstablishedDate() + "'" +
            ", totalEmployeesNumber=" + getTotalEmployeesNumber() +
            ", joinDate='" + getJoinDate() + "'" +
            ", subscriptionStartDate='" + getSubscriptionStartDate() + "'" +
            ", subscriptionEndDate='" + getSubscriptionEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", isVerified='" + getIsVerified() + "'" +
            ", activatedDate='" + getActivatedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            ", suspendedDate='" + getSuspendedDate() + "'" +
            "}";
    }
}
