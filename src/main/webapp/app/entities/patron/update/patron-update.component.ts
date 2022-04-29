import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPatron, Patron } from '../patron.model';
import { PatronService } from '../service/patron.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { ReceipientService } from 'app/entities/receipient/service/receipient.service';
import { ReceipientPatronRecurringType } from 'app/entities/enumerations/receipient-patron-recurring-type.model';
import { ReceipientPatronStatus } from 'app/entities/enumerations/receipient-patron-status.model';

@Component({
  selector: 'jhi-patron-update',
  templateUrl: './patron-update.component.html',
})
export class PatronUpdateComponent implements OnInit {
  isSaving = false;
  receipientPatronRecurringTypeValues = Object.keys(ReceipientPatronRecurringType);
  receipientPatronStatusValues = Object.keys(ReceipientPatronStatus);

  usersSharedCollection: IUser[] = [];
  organizationsSharedCollection: IOrganization[] = [];
  receipientsSharedCollection: IReceipient[] = [];

  editForm = this.fb.group({
    id: [],
    isRecurring: [],
    recurringType: [null, [Validators.required]],
    recurringPeriod: [],
    enableReminder: [],
    isAutoPay: [],
    amountReceipientRequisite: [],
    amountPatronPledge: [],
    amountPatronActual: [],
    status: [],
    commitedStartDate: [],
    commitedEndDate: [],
    actualStartDate: [],
    actualEndDate: [],
    reccuringPauseDate: [],
    recurringResumeDate: [],
    recurringPauseReason: [],
    createdDate: [],
    updatedDate: [],
    deletedDate: [],
    patronUserId: [],
    patronUserOrgId: [],
    receipientId: [],
    receipientUserId: [],
    receipientUserOrgId: [],
  });

  constructor(
    protected patronService: PatronService,
    protected userService: UserService,
    protected organizationService: OrganizationService,
    protected receipientService: ReceipientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patron }) => {
      if (patron.id === undefined) {
        const today = dayjs().startOf('day');
        patron.createdDate = today;
        patron.updatedDate = today;
        patron.deletedDate = today;
      }

      this.updateForm(patron);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patron = this.createFromForm();
    if (patron.id !== undefined) {
      this.subscribeToSaveResponse(this.patronService.update(patron));
    } else {
      this.subscribeToSaveResponse(this.patronService.create(patron));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  trackReceipientById(_index: number, item: IReceipient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatron>>): void {
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

  protected updateForm(patron: IPatron): void {
    this.editForm.patchValue({
      id: patron.id,
      isRecurring: patron.isRecurring,
      recurringType: patron.recurringType,
      recurringPeriod: patron.recurringPeriod,
      enableReminder: patron.enableReminder,
      isAutoPay: patron.isAutoPay,
      amountReceipientRequisite: patron.amountReceipientRequisite,
      amountPatronPledge: patron.amountPatronPledge,
      amountPatronActual: patron.amountPatronActual,
      status: patron.status,
      commitedStartDate: patron.commitedStartDate,
      commitedEndDate: patron.commitedEndDate,
      actualStartDate: patron.actualStartDate,
      actualEndDate: patron.actualEndDate,
      reccuringPauseDate: patron.reccuringPauseDate,
      recurringResumeDate: patron.recurringResumeDate,
      recurringPauseReason: patron.recurringPauseReason,
      createdDate: patron.createdDate ? patron.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: patron.updatedDate ? patron.updatedDate.format(DATE_TIME_FORMAT) : null,
      deletedDate: patron.deletedDate ? patron.deletedDate.format(DATE_TIME_FORMAT) : null,
      patronUserId: patron.patronUserId,
      patronUserOrgId: patron.patronUserOrgId,
      receipientId: patron.receipientId,
      receipientUserId: patron.receipientUserId,
      receipientUserOrgId: patron.receipientUserOrgId,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(
      this.usersSharedCollection,
      patron.patronUserId,
      patron.receipientUserId
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      patron.patronUserOrgId,
      patron.receipientUserOrgId
    );
    this.receipientsSharedCollection = this.receipientService.addReceipientToCollectionIfMissing(
      this.receipientsSharedCollection,
      patron.receipientId
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
            this.editForm.get('patronUserId')!.value,
            this.editForm.get('receipientUserId')!.value
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
            this.editForm.get('patronUserOrgId')!.value,
            this.editForm.get('receipientUserOrgId')!.value
          )
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));

    this.receipientService
      .query()
      .pipe(map((res: HttpResponse<IReceipient[]>) => res.body ?? []))
      .pipe(
        map((receipients: IReceipient[]) =>
          this.receipientService.addReceipientToCollectionIfMissing(receipients, this.editForm.get('receipientId')!.value)
        )
      )
      .subscribe((receipients: IReceipient[]) => (this.receipientsSharedCollection = receipients));
  }

  protected createFromForm(): IPatron {
    return {
      ...new Patron(),
      id: this.editForm.get(['id'])!.value,
      isRecurring: this.editForm.get(['isRecurring'])!.value,
      recurringType: this.editForm.get(['recurringType'])!.value,
      recurringPeriod: this.editForm.get(['recurringPeriod'])!.value,
      enableReminder: this.editForm.get(['enableReminder'])!.value,
      isAutoPay: this.editForm.get(['isAutoPay'])!.value,
      amountReceipientRequisite: this.editForm.get(['amountReceipientRequisite'])!.value,
      amountPatronPledge: this.editForm.get(['amountPatronPledge'])!.value,
      amountPatronActual: this.editForm.get(['amountPatronActual'])!.value,
      status: this.editForm.get(['status'])!.value,
      commitedStartDate: this.editForm.get(['commitedStartDate'])!.value,
      commitedEndDate: this.editForm.get(['commitedEndDate'])!.value,
      actualStartDate: this.editForm.get(['actualStartDate'])!.value,
      actualEndDate: this.editForm.get(['actualEndDate'])!.value,
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
      patronUserId: this.editForm.get(['patronUserId'])!.value,
      patronUserOrgId: this.editForm.get(['patronUserOrgId'])!.value,
      receipientId: this.editForm.get(['receipientId'])!.value,
      receipientUserId: this.editForm.get(['receipientUserId'])!.value,
      receipientUserOrgId: this.editForm.get(['receipientUserOrgId'])!.value,
    };
  }
}
