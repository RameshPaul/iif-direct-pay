import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISubscriptionPlan, SubscriptionPlan } from '../subscription-plan.model';
import { SubscriptionPlanService } from '../service/subscription-plan.service';
import { SubscriptionType } from 'app/entities/enumerations/subscription-type.model';
import { SubscriptionStatus } from 'app/entities/enumerations/subscription-status.model';

@Component({
  selector: 'jhi-subscription-plan-update',
  templateUrl: './subscription-plan-update.component.html',
})
export class SubscriptionPlanUpdateComponent implements OnInit {
  isSaving = false;
  subscriptionTypeValues = Object.keys(SubscriptionType);
  subscriptionStatusValues = Object.keys(SubscriptionStatus);

  editForm = this.fb.group({
    id: [],
    subscriptionName: [null, [Validators.required]],
    subscriptionTitle: [null, [Validators.required]],
    subscriptionType: [null, [Validators.required]],
    subscriptionPrice: [null, [Validators.required]],
    subscriptionQuantity: [null, [Validators.required]],
    subscriptionPeriod: [null, [Validators.required]],
    subscriptionTerms: [],
    status: [null, [Validators.required]],
    createdDate: [],
    updatedDate: [],
  });

  constructor(
    protected subscriptionPlanService: SubscriptionPlanService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscriptionPlan }) => {
      if (subscriptionPlan.id === undefined) {
        const today = dayjs().startOf('day');
        subscriptionPlan.createdDate = today;
        subscriptionPlan.updatedDate = today;
      }

      this.updateForm(subscriptionPlan);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subscriptionPlan = this.createFromForm();
    if (subscriptionPlan.id !== undefined) {
      this.subscribeToSaveResponse(this.subscriptionPlanService.update(subscriptionPlan));
    } else {
      this.subscribeToSaveResponse(this.subscriptionPlanService.create(subscriptionPlan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriptionPlan>>): void {
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

  protected updateForm(subscriptionPlan: ISubscriptionPlan): void {
    this.editForm.patchValue({
      id: subscriptionPlan.id,
      subscriptionName: subscriptionPlan.subscriptionName,
      subscriptionTitle: subscriptionPlan.subscriptionTitle,
      subscriptionType: subscriptionPlan.subscriptionType,
      subscriptionPrice: subscriptionPlan.subscriptionPrice,
      subscriptionQuantity: subscriptionPlan.subscriptionQuantity,
      subscriptionPeriod: subscriptionPlan.subscriptionPeriod,
      subscriptionTerms: subscriptionPlan.subscriptionTerms,
      status: subscriptionPlan.status,
      createdDate: subscriptionPlan.createdDate ? subscriptionPlan.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: subscriptionPlan.updatedDate ? subscriptionPlan.updatedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISubscriptionPlan {
    return {
      ...new SubscriptionPlan(),
      id: this.editForm.get(['id'])!.value,
      subscriptionName: this.editForm.get(['subscriptionName'])!.value,
      subscriptionTitle: this.editForm.get(['subscriptionTitle'])!.value,
      subscriptionType: this.editForm.get(['subscriptionType'])!.value,
      subscriptionPrice: this.editForm.get(['subscriptionPrice'])!.value,
      subscriptionQuantity: this.editForm.get(['subscriptionQuantity'])!.value,
      subscriptionPeriod: this.editForm.get(['subscriptionPeriod'])!.value,
      subscriptionTerms: this.editForm.get(['subscriptionTerms'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
