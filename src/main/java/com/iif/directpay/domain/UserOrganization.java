package com.iif.directpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iif.directpay.domain.enumeration.UserOrganizationStatus;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserOrganization.
 */
@Entity
@Table(name = "user_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "exit_date")
    private LocalDate exitDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserOrganizationStatus status;

    @Column(name = "suspended_date")
    private Instant suspendedDate;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subscriptionId" }, allowSetters = true)
    private Organization organiationId;

    @ManyToOne
    private User userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserOrganization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getJoiningDate() {
        return this.joiningDate;
    }

    public UserOrganization joiningDate(LocalDate joiningDate) {
        this.setJoiningDate(joiningDate);
        return this;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalDate getExitDate() {
        return this.exitDate;
    }

    public UserOrganization exitDate(LocalDate exitDate) {
        this.setExitDate(exitDate);
        return this;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public UserOrganizationStatus getStatus() {
        return this.status;
    }

    public UserOrganization status(UserOrganizationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(UserOrganizationStatus status) {
        this.status = status;
    }

    public Instant getSuspendedDate() {
        return this.suspendedDate;
    }

    public UserOrganization suspendedDate(Instant suspendedDate) {
        this.setSuspendedDate(suspendedDate);
        return this;
    }

    public void setSuspendedDate(Instant suspendedDate) {
        this.suspendedDate = suspendedDate;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public UserOrganization deletedDate(Instant deletedDate) {
        this.setDeletedDate(deletedDate);
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public UserOrganization createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public UserOrganization updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Organization getOrganiationId() {
        return this.organiationId;
    }

    public void setOrganiationId(Organization organization) {
        this.organiationId = organization;
    }

    public UserOrganization organiationId(Organization organization) {
        this.setOrganiationId(organization);
        return this;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public UserOrganization userId(User user) {
        this.setUserId(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOrganization)) {
            return false;
        }
        return id != null && id.equals(((UserOrganization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserOrganization{" +
            "id=" + getId() +
            ", joiningDate='" + getJoiningDate() + "'" +
            ", exitDate='" + getExitDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", suspendedDate='" + getSuspendedDate() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
