import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IReceipient, Receipient } from '../receipient.model';
import { ReceipientService } from '../service/receipient.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { ReceipientPatronRecurringType } from 'app/entities/enumerations/receipient-patron-recurring-type.model';
import { ReceipientPatronStatus } from 'app/entities/enumerations/receipient-patron-status.model';

@Component({
  selector: 'jhi-receipient-update',
  templateUrl: './receipient-update.component.html',
})
export class ReceipientUpdateComponent implements OnInit {
  isSaving = false;
  receipientPatronRecurringTypeValues = Object.keys(ReceipientPatronRecurringType);
  receipientPatronStatusValues = Object.keys(ReceipientPatronStatus);

  usersSharedCollection: IUser[] = [];
  organizationsSharedCollection: IOrganization[] = [];

  editForm = this.fb.group({
    id: [],
    isRecurring: [],
    recurringType: [null, [Validators.required]],
    recurringStartDate: [],
    recurringEndDate: [],
    enableReminder: [],
    startDate: [],
    endDate: [],
    isAutoPay: [],
    amountRequisite: [],
    status: [],
    isManagedByOrg: [],
    approvedDateTime: [],
    rejectedDateTime: [],
    rejectReason: [],
    onboardedDate: [],
    reccuringPauseDate: [],
    recurringResumeDate: [],
    recurringPauseReason: [],
    createdDate: [],
    updatedDate: [],
    deletedDate: [],
    userId: [],
    organizationId: [],
    managedOrganiztionId: [],
    createdUserId: [],
    createdUserOrgId: [],
    approvedUserId: [],
    approvedUserOrgId: [],
    rejectedUserId: [],
    rejectedUserOrgId: [],
  });

  constructor(
    protected receipientService: ReceipientService,
    protected userService: UserService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receipient }) => {
      if (receipient.id === undefined) {
        const today = dayjs().startOf('day');
        receipient.approvedDateTime = today;
        receipient.rejectedDateTime = today;
        receipient.createdDate = today;
        receipient.updatedDate = today;
        receipient.deletedDate = today;
      }

      this.updateForm(receipient);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receipient = this.createFromForm();
    if (receipient.id !== undefined) {
      this.subscribeToSaveResponse(this.receipientService.update(receipient));
    } else {
      this.subscribeToSaveResponse(this.receipientService.create(receipient));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceipient>>): void {
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

  protected updateForm(receipient: IReceipient): void {
    this.editForm.patchValue({
      id: receipient.id,
      isRecurring: receipient.isRecurring,
      recurringType: receipient.recurringType,
      recurringStartDate: receipient.recurringStartDate,
      recurringEndDate: receipient.recurringEndDate,
      enableReminder: receipient.enableReminder,
      startDate: receipient.startDate,
      endDate: receipient.endDate,
      isAutoPay: receipient.isAutoPay,
      amountRequisite: receipient.amountRequisite,
      status: receipient.status,
      isManagedByOrg: receipient.isManagedByOrg,
      approvedDateTime: receipient.approvedDateTime ? receipient.approvedDateTime.format(DATE_TIME_FORMAT) : null,
      rejectedDateTime: receipient.rejectedDateTime ? receipient.rejectedDateTime.format(DATE_TIME_FORMAT) : null,
      rejectReason: receipient.rejectReason,
      onboardedDate: receipient.onboardedDate,
      reccuringPauseDate: receipient.reccuringPauseDate,
      recurringResumeDate: receipient.recurringResumeDate,
      recurringPauseReason: receipient.recurringPauseReason,
      createdDate: receipient.createdDate ? receipient.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: receipient.updatedDate ? receipient.updatedDate.format(DATE_TIME_FORMAT) : null,
      deletedDate: receipient.deletedDate ? receipient.deletedDate.format(DATE_TIME_FORMAT) : null,
      userId: receipient.userId,
      organizationId: receipient.organizationId,
      managedOrganiztionId: receipient.managedOrganiztionId,
      createdUserId: receipient.createdUserId,
      createdUserOrgId: receipient.createdUserOrgId,
      approvedUserId: receipient.approvedUserId,
      approvedUserOrgId: receipient.approvedUserOrgId,
      rejectedUserId: receipient.rejectedUserId,
      rejectedUserOrgId: receipient.rejectedUserOrgId,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(
      this.usersSharedCollection,
      receipient.userId,
      receipient.createdUserId,
      receipient.approvedUserId,
      receipient.rejectedUserId
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      receipient.organizationId,
      receipient.managedOrganiztionId,
      receipient.createdUserOrgId,
      receipient.approvedUserOrgId,
      receipient.rejectedUserOrgId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(
        map((users: IUser[]) =>
          this.userService.addUserToCollectionIfMissing(
            users,
            this.editForm.get('userId')!.value,
            this.editForm.get('createdUserId')!.value,
            this.editForm.get('approvedUserId')!.value,
            this.editForm.get('rejectedUserId')!.value
          )
        )
      )
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing(
            organizations,
            this.editForm.get('organizationId')!.value,
            this.editForm.get('managedOrganiztionId')!.value,
            this.editForm.get('createdUserOrgId')!.value,
            this.editForm.get('approvedUserOrgId')!.value,
            this.editForm.get('rejectedUserOrgId')!.value
          )
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }

  protected createFromForm(): IReceipient {
    return {
      ...new Receipient(),
      id: this.editForm.get(['id'])!.value,
      isRecurring: this.editForm.get(['isRecurring'])!.value,
      recurringType: this.editForm.get(['recurringType'])!.value,
      recurringStartDate: this.editForm.get(['recurringStartDate'])!.value,
      recurringEndDate: this.editForm.get(['recurringEndDate'])!.value,
      enableReminder: this.editForm.get(['enableReminder'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      isAutoPay: this.editForm.get(['isAutoPay'])!.value,
      amountRequisite: this.editForm.get(['amountRequisite'])!.value,
      status: this.editForm.get(['status'])!.value,
      isManagedByOrg: this.editForm.get(['isManagedByOrg'])!.value,
      approvedDateTime: this.editForm.get(['approvedDateTime'])!.value
        ? dayjs(this.editForm.get(['approvedDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      rejectedDateTime: this.editForm.get(['rejectedDateTime'])!.value
        ? dayjs(this.editForm.get(['rejectedDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      rejectReason: this.editForm.get(['rejectReason'])!.value,
      onboardedDate: this.editForm.get(['onboardedDate'])!.value,
      reccuringPauseDate: this.editForm.get(['reccuringPauseDate'])!.value,
      recurringResumeDate: this.editForm.get(['recurringResumeDate'])!.value,
      recurringPauseReason: this.editForm.get(['recurringPauseReason'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deletedDate: this.editForm.get(['deletedDate'])!.value
        ? dayjs(this.editForm.get(['deletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      organizationId: this.editForm.get(['organizationId'])!.value,
      managedOrganiztionId: this.editForm.get(['managedOrganiztionId'])!.value,
      createdUserId: this.editForm.get(['createdUserId'])!.value,
      createdUserOrgId: this.editForm.get(['createdUserOrgId'])!.value,
      approvedUserId: this.editForm.get(['approvedUserId'])!.value,
      approvedUserOrgId: this.editForm.get(['approvedUserOrgId'])!.value,
      rejectedUserId: this.editForm.get(['rejectedUserId'])!.value,
      rejectedUserOrgId: this.editForm.get(['rejectedUserOrgId'])!.value,
    };
  }
}
