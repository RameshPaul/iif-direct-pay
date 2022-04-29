import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrganization, Organization } from '../organization.model';
import { OrganizationService } from '../service/organization.service';
import { ISubscriptionPlan } from 'app/entities/subscription-plan/subscription-plan.model';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/service/subscription-plan.service';
import { OrganizationType } from 'app/entities/enumerations/organization-type.model';
import { OrganizationTaxCategory } from 'app/entities/enumerations/organization-tax-category.model';
import { OrganizationStatus } from 'app/entities/enumerations/organization-status.model';

@Component({
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html',
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving = false;
  organizationTypeValues = Object.keys(OrganizationType);
  organizationTaxCategoryValues = Object.keys(OrganizationTaxCategory);
  organizationStatusValues = Object.keys(OrganizationStatus);

  subscriptionPlansSharedCollection: ISubscriptionPlan[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    aliasName: [],
    email: [null, [Validators.required]],
    website: [],
    phone: [null, [Validators.required]],
    mobile: [],
    representativeName: [],
    representativeEmail: [],
    representativePhone: [],
    registrationNumber: [],
    organizationType: [],
    organizationTypeOther: [],
    organizationTaxCategory: [],
    organizationTaxCategoryOther: [],
    establishedDate: [],
    totalEmployeesNumber: [],
    joinDate: [],
    subscriptionStartDate: [],
    subscriptionEndDate: [],
    status: [null, [Validators.required]],
    isVerified: [],
    activatedDate: [],
    createdDate: [],
    updatedDate: [],
    deletedDate: [],
    suspendedDate: [],
    subscriptionId: [],
  });

  constructor(
    protected organizationService: OrganizationService,
    protected subscriptionPlanService: SubscriptionPlanService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      if (organization.id === undefined) {
        const today = dayjs().startOf('day');
        organization.joinDate = today;
        organization.activatedDate = today;
        organization.createdDate = today;
        organization.updatedDate = today;
        organization.deletedDate = today;
        organization.suspendedDate = today;
      }

      this.updateForm(organization);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.createFromForm();
    if (organization.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  trackSubscriptionPlanById(_index: number, item: ISubscriptionPlan): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(organization: IOrganization): void {
    this.editForm.patchValue({
      id: organization.id,
      name: organization.name,
      aliasName: organization.aliasName,
      email: organization.email,
      website: organization.website,
      phone: organization.phone,
      mobile: organization.mobile,
      representativeName: organization.representativeName,
      representativeEmail: organization.representativeEmail,
      representativePhone: organization.representativePhone,
      registrationNumber: organization.registrationNumber,
      organizationType: organization.organizationType,
      organizationTypeOther: organization.organizationTypeOther,
      organizationTaxCategory: organization.organizationTaxCategory,
      organizationTaxCategoryOther: organization.organizationTaxCategoryOther,
      establishedDate: organization.establishedDate,
      totalEmployeesNumber: organization.totalEmployeesNumber,
      joinDate: organization.joinDate ? organization.joinDate.format(DATE_TIME_FORMAT) : null,
      subscriptionStartDate: organization.subscriptionStartDate,
      subscriptionEndDate: organization.subscriptionEndDate,
      status: organization.status,
      isVerified: organization.isVerified,
      activatedDate: organization.activatedDate ? organization.activatedDate.format(DATE_TIME_FORMAT) : null,
      createdDate: organization.createdDate ? organization.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: organization.updatedDate ? organization.updatedDate.format(DATE_TIME_FORMAT) : null,
      deletedDate: organization.deletedDate ? organization.deletedDate.format(DATE_TIME_FORMAT) : null,
      suspendedDate: organization.suspendedDate ? organization.suspendedDate.format(DATE_TIME_FORMAT) : null,
      subscriptionId: organization.subscriptionId,
    });

    this.subscriptionPlansSharedCollection = this.subscriptionPlanService.addSubscriptionPlanToCollectionIfMissing(
      this.subscriptionPlansSharedCollection,
      organization.subscriptionId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.subscriptionPlanService
      .query()
      .pipe(map((res: HttpResponse<ISubscriptionPlan[]>) => res.body ?? []))
      .pipe(
        map((subscriptionPlans: ISubscriptionPlan[]) =>
          this.subscriptionPlanService.addSubscriptionPlanToCollectionIfMissing(
            subscriptionPlans,
            this.editForm.get('subscriptionId')!.value
          )
        )
      )
      .subscribe((subscriptionPlans: ISubscriptionPlan[]) => (this.subscriptionPlansSharedCollection = subscriptionPlans));
  }

  protected createFromForm(): IOrganization {
    return {
      ...new Organization(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      aliasName: this.editForm.get(['aliasName'])!.value,
      email: this.editForm.get(['email'])!.value,
      website: this.editForm.get(['website'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      representativeName: this.editForm.get(['representativeName'])!.value,
      representativeEmail: this.editForm.get(['representativeEmail'])!.value,
      representativePhone: this.editForm.get(['representativePhone'])!.value,
      registrationNumber: this.editForm.get(['registrationNumber'])!.value,
      organizationType: this.editForm.get(['organizationType'])!.value,
      organizationTypeOther: this.editForm.get(['organizationTypeOther'])!.value,
      organizationTaxCategory: this.editForm.get(['organizationTaxCategory'])!.value,
      organizationTaxCategoryOther: this.editForm.get(['organizationTaxCategoryOther'])!.value,
      establishedDate: this.editForm.get(['establishedDate'])!.value,
      totalEmployeesNumber: this.editForm.get(['totalEmployeesNumber'])!.value,
      joinDate: this.editForm.get(['joinDate'])!.value ? dayjs(this.editForm.get(['joinDate'])!.value, DATE_TIME_FORMAT) : undefined,
      subscriptionStartDate: this.editForm.get(['subscriptionStartDate'])!.value,
      subscriptionEndDate: this.editForm.get(['subscriptionEndDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      isVerified: this.editForm.get(['isVerified'])!.value,
      activatedDate: this.editForm.get(['activatedDate'])!.value
        ? dayjs(this.editForm.get(['activatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deletedDate: this.editForm.get(['deletedDate'])!.value
        ? dayjs(this.editForm.get(['deletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      suspendedDate: this.editForm.get(['suspendedDate'])!.value
        ? dayjs(this.editForm.get(['suspendedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      subscriptionId: this.editForm.get(['subscriptionId'])!.value,
    };
  }
}
