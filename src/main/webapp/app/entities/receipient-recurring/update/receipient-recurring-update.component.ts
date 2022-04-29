import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IReceipientRecurring, ReceipientRecurring } from '../receipient-recurring.model';
import { ReceipientRecurringService } from '../service/receipient-recurring.service';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { ReceipientService } from 'app/entities/receipient/service/receipient.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ReceipientRecurringStatus } from 'app/entities/enumerations/receipient-recurring-status.model';

@Component({
  selector: 'jhi-receipient-recurring-update',
  templateUrl: './receipient-recurring-update.component.html',
})
export class ReceipientRecurringUpdateComponent implements OnInit {
  isSaving = false;
  receipientRecurringStatusValues = Object.keys(ReceipientRecurringStatus);

  receipientsSharedCollection: IReceipient[] = [];
  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    recurringPeriod: [],
    startDate: [],
    endDate: [],
    amountRequisite: [],
    amountPatronCommited: [],
    amountReceived: [],
    amountBalance: [],
    totalPatrons: [],
    detailsText: [],
    status: [],
    pauseDate: [],
    resumeDate: [],
    createdDate: [],
    updatedDate: [],
    receipientId: [],
    userId: [],
  });

  constructor(
    protected receipientRecurringService: ReceipientRecurringService,
    protected receipientService: ReceipientService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receipientRecurring }) => {
      if (receipientRecurring.id === undefined) {
        const today = dayjs().startOf('day');
        receipientRecurring.createdDate = today;
        receipientRecurring.updatedDate = today;
      }

      this.updateForm(receipientRecurring);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receipientRecurring = this.createFromForm();
    if (receipientRecurring.id !== undefined) {
      this.subscribeToSaveResponse(this.receipientRecurringService.update(receipientRecurring));
    } else {
      this.subscribeToSaveResponse(this.receipientRecurringService.create(receipientRecurring));
    }
  }

  trackReceipientById(_index: number, item: IReceipient): number {
    return item.id!;
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceipientRecurring>>): void {
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

  protected updateForm(receipientRecurring: IReceipientRecurring): void {
    this.editForm.patchValue({
      id: receipientRecurring.id,
      recurringPeriod: receipientRecurring.recurringPeriod,
      startDate: receipientRecurring.startDate,
      endDate: receipientRecurring.endDate,
      amountRequisite: receipientRecurring.amountRequisite,
      amountPatronCommited: receipientRecurring.amountPatronCommited,
      amountReceived: receipientRecurring.amountReceived,
      amountBalance: receipientRecurring.amountBalance,
      totalPatrons: receipientRecurring.totalPatrons,
      detailsText: receipientRecurring.detailsText,
      status: receipientRecurring.status,
      pauseDate: receipientRecurring.pauseDate,
      resumeDate: receipientRecurring.resumeDate,
      createdDate: receipientRecurring.createdDate ? receipientRecurring.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: receipientRecurring.updatedDate ? receipientRecurring.updatedDate.format(DATE_TIME_FORMAT) : null,
      receipientId: receipientRecurring.receipientId,
      userId: receipientRecurring.userId,
    });

    this.receipientsSharedCollection = this.receipientService.addReceipientToCollectionIfMissing(
      this.receipientsSharedCollection,
      receipientRecurring.receipientId
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, receipientRecurring.userId);
  }

  protected loadRelationshipsOptions(): void {
    this.receipientService
      .query()
      .pipe(map((res: HttpResponse<IReceipient[]>) => res.body ?? []))
      .pipe(
        map((receipients: IReceipient[]) =>
          this.receipientService.addReceipientToCollectionIfMissing(receipients, this.editForm.get('receipientId')!.value)
        )
      )
      .subscribe((receipients: IReceipient[]) => (this.receipientsSharedCollection = receipients));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('userId')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IReceipientRecurring {
    return {
      ...new ReceipientRecurring(),
      id: this.editForm.get(['id'])!.value,
      recurringPeriod: this.editForm.get(['recurringPeriod'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      amountRequisite: this.editForm.get(['amountRequisite'])!.value,
      amountPatronCommited: this.editForm.get(['amountPatronCommited'])!.value,
      amountReceived: this.editForm.get(['amountReceived'])!.value,
      amountBalance: this.editForm.get(['amountBalance'])!.value,
      totalPatrons: this.editForm.get(['totalPatrons'])!.value,
      detailsText: this.editForm.get(['detailsText'])!.value,
      status: this.editForm.get(['status'])!.value,
      pauseDate: this.editForm.get(['pauseDate'])!.value,
      resumeDate: this.editForm.get(['resumeDate'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      receipientId: this.editForm.get(['receipientId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }
}
