import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPayment, Payment } from '../payment.model';
import { PaymentService } from '../service/payment.service';
import { IPatron } from 'app/entities/patron/patron.model';
import { PatronService } from 'app/entities/patron/service/patron.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { ReceipientService } from 'app/entities/receipient/service/receipient.service';
import { IUserAccount } from 'app/entities/user-account/user-account.model';
import { UserAccountService } from 'app/entities/user-account/service/user-account.service';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';
import { UserAccountType } from 'app/entities/enumerations/user-account-type.model';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html',
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  paymentTypeValues = Object.keys(PaymentType);
  userAccountTypeValues = Object.keys(UserAccountType);
  paymentStatusValues = Object.keys(PaymentStatus);

  patronsSharedCollection: IPatron[] = [];
  usersSharedCollection: IUser[] = [];
  organizationsSharedCollection: IOrganization[] = [];
  receipientsSharedCollection: IReceipient[] = [];
  userAccountsSharedCollection: IUserAccount[] = [];

  editForm = this.fb.group({
    id: [],
    recurringPeriod: [],
    amount: [],
    transactionId: [],
    paymentType: [],
    paymentSource: [],
    paymentStatus: [],
    paymentStatusDetails: [],
    paymentStartDateTime: [],
    paymentCompleteDateTime: [],
    paymentFailureDateTime: [],
    patronComment: [],
    isAutoPay: [],
    paymentDestinationSource: [],
    paymentReceivedDateTIme: [],
    paymentReceivedStatus: [],
    paymentReceivedDetails: [],
    paymentRefundedDateTime: [],
    userComment: [],
    flaggedDateTime: [],
    flagDetails: [],
    flaggedEmailId: [],
    flaggedAmount: [],
    flagCreatedDateTime: [],
    isRecurringPayment: [],
    transactionDetails: [],
    createdDate: [],
    updatedDate: [],
    patronId: [],
    patronUserId: [],
    patronUserOrgId: [],
    receipientId: [],
    receipientUserId: [],
    receipientUserOrgId: [],
    paymentSourceAccountId: [],
    paymentDestinationAccountId: [],
    flaggedUserId: [],
    flaggedUserOrgId: [],
    flagClearedUserId: [],
    flagClearedUserOrgId: [],
  });

  constructor(
    protected paymentService: PaymentService,
    protected patronService: PatronService,
    protected userService: UserService,
    protected organizationService: OrganizationService,
    protected receipientService: ReceipientService,
    protected userAccountService: UserAccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      if (payment.id === undefined) {
        const today = dayjs().startOf('day');
        payment.createdDate = today;
        payment.updatedDate = today;
      }

      this.updateForm(payment);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  trackPatronById(_index: number, item: IPatron): number {
    return item.id!;
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

  trackUserAccountById(_index: number, item: IUserAccount): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
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

  protected updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      recurringPeriod: payment.recurringPeriod,
      amount: payment.amount,
      transactionId: payment.transactionId,
      paymentType: payment.paymentType,
      paymentSource: payment.paymentSource,
      paymentStatus: payment.paymentStatus,
      paymentStatusDetails: payment.paymentStatusDetails,
      paymentStartDateTime: payment.paymentStartDateTime,
      paymentCompleteDateTime: payment.paymentCompleteDateTime,
      paymentFailureDateTime: payment.paymentFailureDateTime,
      patronComment: payment.patronComment,
      isAutoPay: payment.isAutoPay,
      paymentDestinationSource: payment.paymentDestinationSource,
      paymentReceivedDateTIme: payment.paymentReceivedDateTIme,
      paymentReceivedStatus: payment.paymentReceivedStatus,
      paymentReceivedDetails: payment.paymentReceivedDetails,
      paymentRefundedDateTime: payment.paymentRefundedDateTime,
      userComment: payment.userComment,
      flaggedDateTime: payment.flaggedDateTime,
      flagDetails: payment.flagDetails,
      flaggedEmailId: payment.flaggedEmailId,
      flaggedAmount: payment.flaggedAmount,
      flagCreatedDateTime: payment.flagCreatedDateTime,
      isRecurringPayment: payment.isRecurringPayment,
      transactionDetails: payment.transactionDetails,
      createdDate: payment.createdDate ? payment.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: payment.updatedDate ? payment.updatedDate.format(DATE_TIME_FORMAT) : null,
      patronId: payment.patronId,
      patronUserId: payment.patronUserId,
      patronUserOrgId: payment.patronUserOrgId,
      receipientId: payment.receipientId,
      receipientUserId: payment.receipientUserId,
      receipientUserOrgId: payment.receipientUserOrgId,
      paymentSourceAccountId: payment.paymentSourceAccountId,
      paymentDestinationAccountId: payment.paymentDestinationAccountId,
      flaggedUserId: payment.flaggedUserId,
      flaggedUserOrgId: payment.flaggedUserOrgId,
      flagClearedUserId: payment.flagClearedUserId,
      flagClearedUserOrgId: payment.flagClearedUserOrgId,
    });

    this.patronsSharedCollection = this.patronService.addPatronToCollectionIfMissing(this.patronsSharedCollection, payment.patronId);
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(
      this.usersSharedCollection,
      payment.patronUserId,
      payment.receipientUserId,
      payment.flaggedUserId,
      payment.flagClearedUserId
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      payment.patronUserOrgId,
      payment.receipientUserOrgId,
      payment.flaggedUserOrgId,
      payment.flagClearedUserOrgId
    );
    this.receipientsSharedCollection = this.receipientService.addReceipientToCollectionIfMissing(
      this.receipientsSharedCollection,
      payment.receipientId
    );
    this.userAccountsSharedCollection = this.userAccountService.addUserAccountToCollectionIfMissing(
      this.userAccountsSharedCollection,
      payment.paymentSourceAccountId,
      payment.paymentDestinationAccountId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.patronService
      .query()
      .pipe(map((res: HttpResponse<IPatron[]>) => res.body ?? []))
      .pipe(map((patrons: IPatron[]) => this.patronService.addPatronToCollectionIfMissing(patrons, this.editForm.get('patronId')!.value)))
      .subscribe((patrons: IPatron[]) => (this.patronsSharedCollection = patrons));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(
        map((users: IUser[]) =>
          this.userService.addUserToCollectionIfMissing(
            users,
            this.editForm.get('patronUserId')!.value,
            this.editForm.get('receipientUserId')!.value,
            this.editForm.get('flaggedUserId')!.value,
            this.editForm.get('flagClearedUserId')!.value
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
            this.editForm.get('receipientUserOrgId')!.value,
            this.editForm.get('flaggedUserOrgId')!.value,
            this.editForm.get('flagClearedUserOrgId')!.value
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

    this.userAccountService
      .query()
      .pipe(map((res: HttpResponse<IUserAccount[]>) => res.body ?? []))
      .pipe(
        map((userAccounts: IUserAccount[]) =>
          this.userAccountService.addUserAccountToCollectionIfMissing(
            userAccounts,
            this.editForm.get('paymentSourceAccountId')!.value,
            this.editForm.get('paymentDestinationAccountId')!.value
          )
        )
      )
      .subscribe((userAccounts: IUserAccount[]) => (this.userAccountsSharedCollection = userAccounts));
  }

  protected createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      recurringPeriod: this.editForm.get(['recurringPeriod'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      transactionId: this.editForm.get(['transactionId'])!.value,
      paymentType: this.editForm.get(['paymentType'])!.value,
      paymentSource: this.editForm.get(['paymentSource'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
      paymentStatusDetails: this.editForm.get(['paymentStatusDetails'])!.value,
      paymentStartDateTime: this.editForm.get(['paymentStartDateTime'])!.value,
      paymentCompleteDateTime: this.editForm.get(['paymentCompleteDateTime'])!.value,
      paymentFailureDateTime: this.editForm.get(['paymentFailureDateTime'])!.value,
      patronComment: this.editForm.get(['patronComment'])!.value,
      isAutoPay: this.editForm.get(['isAutoPay'])!.value,
      paymentDestinationSource: this.editForm.get(['paymentDestinationSource'])!.value,
      paymentReceivedDateTIme: this.editForm.get(['paymentReceivedDateTIme'])!.value,
      paymentReceivedStatus: this.editForm.get(['paymentReceivedStatus'])!.value,
      paymentReceivedDetails: this.editForm.get(['paymentReceivedDetails'])!.value,
      paymentRefundedDateTime: this.editForm.get(['paymentRefundedDateTime'])!.value,
      userComment: this.editForm.get(['userComment'])!.value,
      flaggedDateTime: this.editForm.get(['flaggedDateTime'])!.value,
      flagDetails: this.editForm.get(['flagDetails'])!.value,
      flaggedEmailId: this.editForm.get(['flaggedEmailId'])!.value,
      flaggedAmount: this.editForm.get(['flaggedAmount'])!.value,
      flagCreatedDateTime: this.editForm.get(['flagCreatedDateTime'])!.value,
      isRecurringPayment: this.editForm.get(['isRecurringPayment'])!.value,
      transactionDetails: this.editForm.get(['transactionDetails'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      patronId: this.editForm.get(['patronId'])!.value,
      patronUserId: this.editForm.get(['patronUserId'])!.value,
      patronUserOrgId: this.editForm.get(['patronUserOrgId'])!.value,
      receipientId: this.editForm.get(['receipientId'])!.value,
      receipientUserId: this.editForm.get(['receipientUserId'])!.value,
      receipientUserOrgId: this.editForm.get(['receipientUserOrgId'])!.value,
      paymentSourceAccountId: this.editForm.get(['paymentSourceAccountId'])!.value,
      paymentDestinationAccountId: this.editForm.get(['paymentDestinationAccountId'])!.value,
      flaggedUserId: this.editForm.get(['flaggedUserId'])!.value,
      flaggedUserOrgId: this.editForm.get(['flaggedUserOrgId'])!.value,
      flagClearedUserId: this.editForm.get(['flagClearedUserId'])!.value,
      flagClearedUserOrgId: this.editForm.get(['flagClearedUserOrgId'])!.value,
    };
  }
}
