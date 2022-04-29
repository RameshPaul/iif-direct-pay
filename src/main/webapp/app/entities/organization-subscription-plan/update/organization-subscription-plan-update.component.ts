import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrganizationSubscriptionPlan, OrganizationSubscriptionPlan } from '../organization-subscription-plan.model';
import { OrganizationSubscriptionPlanService } from '../service/organization-subscription-plan.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { ISubscriptionPlan } from 'app/entities/subscription-plan/subscription-plan.model';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/service/subscription-plan.service';
import { SubscriptionType } from 'app/entities/enumerations/subscription-type.model';
import { SubscriptionStatus } from 'app/entities/enumerations/subscription-status.model';

@Component({
  selector: 'jhi-organization-subscription-plan-update',
  templateUrl: './organization-subscription-plan-update.component.html',
})
export class OrganizationSubscriptionPlanUpdateComponent implements OnInit {
  isSaving = false;
  subscriptionTypeValues = Object.keys(SubscriptionType);
  subscriptionStatusValues = Object.keys(SubscriptionStatus);

  organizationsSharedCollection: IOrganization[] = [];
  subscriptionPlansSharedCollection: ISubscriptionPlan[] = [];

  editForm = this.fb.group({
    id: [],
    subscriptionName: [null, [Validators.required]],
    subscriptionTitle: [null, [Validators.required]],
    subscriptionType: [null, [Validators.required]],
    subscriptionPrice: [null, [Validators.required]],
    subscriptionQuantity: [null, [Validators.required]],
    subscriptionPeriod: [null, [Validators.required]],
    subscriptionTerms: [],
    couponCode: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    suspendedDate: [],
    deletedDate: [],
    status: [null, [Validators.required]],
    createdDate: [],
    updatedDate: [],
    organizationId: [],
    subscriptionId: [],
  });

  constructor(
    protected organizationSubscriptionPlanService: OrganizationSubscriptionPlanService,
    protected organizationService: OrganizationService,
    protected subscriptionPlanService: SubscriptionPlanService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationSubscriptionPlan }) => {
      if (organizationSubscriptionPlan.id === undefined) {
        const today = dayjs().startOf('day');
        organizationSubscriptionPlan.suspendedDate = today;
        organizationSubscriptionPlan.deletedDate = today;
        organizationSubscriptionPlan.createdDate = today;
        organizationSubscriptionPlan.updatedDate = today;
      }

      this.updateForm(organizationSubscriptionPlan);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organizationSubscriptionPlan = this.createFromForm();
    if (organizationSubscriptionPlan.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationSubscriptionPlanService.update(organizationSubscriptionPlan));
    } else {
      this.subscribeToSaveResponse(this.organizationSubscriptionPlanService.create(organizationSubscriptionPlan));
    }
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  trackSubscriptionPlanById(_index: number, item: ISubscriptionPlan): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationSubscriptionPlan>>): void {
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

  protected updateForm(organizationSubscriptionPlan: IOrganizationSubscriptionPlan): void {
    this.editForm.patchValue({
      id: organizationSubscriptionPlan.id,
      subscriptionName: organizationSubscriptionPlan.subscriptionName,
      subscriptionTitle: organizationSubscriptionPlan.subscriptionTitle,
      subscriptionType: organizationSubscriptionPlan.subscriptionType,
      subscriptionPrice: organizationSubscriptionPlan.subscriptionPrice,
      subscriptionQuantity: organizationSubscriptionPlan.subscriptionQuantity,
      subscriptionPeriod: organizationSubscriptionPlan.subscriptionPeriod,
      subscriptionTerms: organizationSubscriptionPlan.subscriptionTerms,
      couponCode: organizationSubscriptionPlan.couponCode,
      startDate: organizationSubscriptionPlan.startDate,
      endDate: organizationSubscriptionPlan.endDate,
      suspendedDate: organizationSubscriptionPlan.suspendedDate
        ? organizationSubscriptionPlan.suspendedDate.format(DATE_TIME_FORMAT)
        : null,
      deletedDate: organizationSubscriptionPlan.deletedDate ? organizationSubscriptionPlan.deletedDate.format(DATE_TIME_FORMAT) : null,
      status: organizationSubscriptionPlan.status,
      createdDate: organizationSubscriptionPlan.createdDate ? organizationSubscriptionPlan.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: organizationSubscriptionPlan.updatedDate ? organizationSubscriptionPlan.updatedDate.format(DATE_TIME_FORMAT) : null,
      organizationId: organizationSubscriptionPlan.organizationId,
      subscriptionId: organizationSubscriptionPlan.subscriptionId,
    });

    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      organizationSubscriptionPlan.organizationId
    );
    this.subscriptionPlansSharedCollection = this.subscriptionPlanService.addSubscriptionPlanToCollectionIfMissing(
      this.subscriptionPlansSharedCollection,
      organizationSubscriptionPlan.subscriptionId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing(organizations, this.editForm.get('organizationId')!.value)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));

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

  protected createFromForm(): IOrganizationSubscriptionPlan {
    return {
      ...new OrganizationSubscriptionPlan(),
      id: this.editForm.get(['id'])!.value,
      subscriptionName: this.editForm.get(['subscriptionName'])!.value,
      subscriptionTitle: this.editForm.get(['subscriptionTitle'])!.value,
      subscriptionType: this.editForm.get(['subscriptionType'])!.value,
      subscriptionPrice: this.editForm.get(['subscriptionPrice'])!.value,
      subscriptionQuantity: this.editForm.get(['subscriptionQuantity'])!.value,
      subscriptionPeriod: this.editForm.get(['subscriptionPeriod'])!.value,
      subscriptionTerms: this.editForm.get(['subscriptionTerms'])!.value,
      couponCode: this.editForm.get(['couponCode'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      suspendedDate: this.editForm.get(['suspendedDate'])!.value
        ? dayjs(this.editForm.get(['suspendedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deletedDate: this.editForm.get(['deletedDate'])!.value
        ? dayjs(this.editForm.get(['deletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      status: this.editForm.get(['status'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      organizationId: this.editForm.get(['organizationId'])!.value,
      subscriptionId: this.editForm.get(['subscriptionId'])!.value,
    };
  }
}
