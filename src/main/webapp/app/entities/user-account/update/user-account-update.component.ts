import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserAccount, UserAccount } from '../user-account.model';
import { UserAccountService } from '../service/user-account.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { UserAccountType } from 'app/entities/enumerations/user-account-type.model';
import { UserAccountUpiStatus } from 'app/entities/enumerations/user-account-upi-status.model';
import { UserAccountBankStatus } from 'app/entities/enumerations/user-account-bank-status.model';
import { UserAccountWalletType } from 'app/entities/enumerations/user-account-wallet-type.model';
import { UserAccountWalletStatus } from 'app/entities/enumerations/user-account-wallet-status.model';

@Component({
  selector: 'jhi-user-account-update',
  templateUrl: './user-account-update.component.html',
})
export class UserAccountUpdateComponent implements OnInit {
  isSaving = false;
  userAccountTypeValues = Object.keys(UserAccountType);
  userAccountUpiStatusValues = Object.keys(UserAccountUpiStatus);
  userAccountBankStatusValues = Object.keys(UserAccountBankStatus);
  userAccountWalletTypeValues = Object.keys(UserAccountWalletType);
  userAccountWalletStatusValues = Object.keys(UserAccountWalletStatus);

  usersSharedCollection: IUser[] = [];
  organizationsSharedCollection: IOrganization[] = [];

  editForm = this.fb.group({
    id: [],
    accountType: [null, [Validators.required]],
    upiAddress: [],
    mobileNumber: [],
    upiActiveDate: [],
    upiStatus: [],
    upiSuspendedDate: [],
    upiDeletedDate: [],
    upiAutoDebitEnabled: [],
    bankName: [],
    bankAccountNumber: [],
    bankIFSCCode: [],
    bankSWIFTCode: [],
    bankBranchAddress: [],
    bankStatus: [],
    bankActiveDate: [],
    bankSuspendedDate: [],
    bankDeletedDate: [],
    bankAutoDebitEnabled: [],
    walletType: [],
    walletAddress: [],
    walletStatus: [],
    wallterActiveDate: [],
    walletSuspendedDate: [],
    walletDeletedDate: [],
    walletAutoDebitEnabled: [],
    createdDate: [],
    updatedDate: [],
    userId: [],
    organizationId: [],
  });

  constructor(
    protected userAccountService: UserAccountService,
    protected userService: UserService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAccount }) => {
      if (userAccount.id === undefined) {
        const today = dayjs().startOf('day');
        userAccount.upiSuspendedDate = today;
        userAccount.upiDeletedDate = today;
        userAccount.bankActiveDate = today;
        userAccount.bankSuspendedDate = today;
        userAccount.bankDeletedDate = today;
        userAccount.wallterActiveDate = today;
        userAccount.walletSuspendedDate = today;
        userAccount.walletDeletedDate = today;
        userAccount.createdDate = today;
        userAccount.updatedDate = today;
      }

      this.updateForm(userAccount);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userAccount = this.createFromForm();
    if (userAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.userAccountService.update(userAccount));
    } else {
      this.subscribeToSaveResponse(this.userAccountService.create(userAccount));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAccount>>): void {
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

  protected updateForm(userAccount: IUserAccount): void {
    this.editForm.patchValue({
      id: userAccount.id,
      accountType: userAccount.accountType,
      upiAddress: userAccount.upiAddress,
      mobileNumber: userAccount.mobileNumber,
      upiActiveDate: userAccount.upiActiveDate,
      upiStatus: userAccount.upiStatus,
      upiSuspendedDate: userAccount.upiSuspendedDate ? userAccount.upiSuspendedDate.format(DATE_TIME_FORMAT) : null,
      upiDeletedDate: userAccount.upiDeletedDate ? userAccount.upiDeletedDate.format(DATE_TIME_FORMAT) : null,
      upiAutoDebitEnabled: userAccount.upiAutoDebitEnabled,
      bankName: userAccount.bankName,
      bankAccountNumber: userAccount.bankAccountNumber,
      bankIFSCCode: userAccount.bankIFSCCode,
      bankSWIFTCode: userAccount.bankSWIFTCode,
      bankBranchAddress: userAccount.bankBranchAddress,
      bankStatus: userAccount.bankStatus,
      bankActiveDate: userAccount.bankActiveDate ? userAccount.bankActiveDate.format(DATE_TIME_FORMAT) : null,
      bankSuspendedDate: userAccount.bankSuspendedDate ? userAccount.bankSuspendedDate.format(DATE_TIME_FORMAT) : null,
      bankDeletedDate: userAccount.bankDeletedDate ? userAccount.bankDeletedDate.format(DATE_TIME_FORMAT) : null,
      bankAutoDebitEnabled: userAccount.bankAutoDebitEnabled,
      walletType: userAccount.walletType,
      walletAddress: userAccount.walletAddress,
      walletStatus: userAccount.walletStatus,
      wallterActiveDate: userAccount.wallterActiveDate ? userAccount.wallterActiveDate.format(DATE_TIME_FORMAT) : null,
      walletSuspendedDate: userAccount.walletSuspendedDate ? userAccount.walletSuspendedDate.format(DATE_TIME_FORMAT) : null,
      walletDeletedDate: userAccount.walletDeletedDate ? userAccount.walletDeletedDate.format(DATE_TIME_FORMAT) : null,
      walletAutoDebitEnabled: userAccount.walletAutoDebitEnabled,
      createdDate: userAccount.createdDate ? userAccount.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: userAccount.updatedDate ? userAccount.updatedDate.format(DATE_TIME_FORMAT) : null,
      userId: userAccount.userId,
      organizationId: userAccount.organizationId,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, userAccount.userId);
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      userAccount.organizationId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('userId')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing(organizations, this.editForm.get('organizationId')!.value)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }

  protected createFromForm(): IUserAccount {
    return {
      ...new UserAccount(),
      id: this.editForm.get(['id'])!.value,
      accountType: this.editForm.get(['accountType'])!.value,
      upiAddress: this.editForm.get(['upiAddress'])!.value,
      mobileNumber: this.editForm.get(['mobileNumber'])!.value,
      upiActiveDate: this.editForm.get(['upiActiveDate'])!.value,
      upiStatus: this.editForm.get(['upiStatus'])!.value,
      upiSuspendedDate: this.editForm.get(['upiSuspendedDate'])!.value
        ? dayjs(this.editForm.get(['upiSuspendedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      upiDeletedDate: this.editForm.get(['upiDeletedDate'])!.value
        ? dayjs(this.editForm.get(['upiDeletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      upiAutoDebitEnabled: this.editForm.get(['upiAutoDebitEnabled'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      bankAccountNumber: this.editForm.get(['bankAccountNumber'])!.value,
      bankIFSCCode: this.editForm.get(['bankIFSCCode'])!.value,
      bankSWIFTCode: this.editForm.get(['bankSWIFTCode'])!.value,
      bankBranchAddress: this.editForm.get(['bankBranchAddress'])!.value,
      bankStatus: this.editForm.get(['bankStatus'])!.value,
      bankActiveDate: this.editForm.get(['bankActiveDate'])!.value
        ? dayjs(this.editForm.get(['bankActiveDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      bankSuspendedDate: this.editForm.get(['bankSuspendedDate'])!.value
        ? dayjs(this.editForm.get(['bankSuspendedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      bankDeletedDate: this.editForm.get(['bankDeletedDate'])!.value
        ? dayjs(this.editForm.get(['bankDeletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      bankAutoDebitEnabled: this.editForm.get(['bankAutoDebitEnabled'])!.value,
      walletType: this.editForm.get(['walletType'])!.value,
      walletAddress: this.editForm.get(['walletAddress'])!.value,
      walletStatus: this.editForm.get(['walletStatus'])!.value,
      wallterActiveDate: this.editForm.get(['wallterActiveDate'])!.value
        ? dayjs(this.editForm.get(['wallterActiveDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      walletSuspendedDate: this.editForm.get(['walletSuspendedDate'])!.value
        ? dayjs(this.editForm.get(['walletSuspendedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      walletDeletedDate: this.editForm.get(['walletDeletedDate'])!.value
        ? dayjs(this.editForm.get(['walletDeletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      walletAutoDebitEnabled: this.editForm.get(['walletAutoDebitEnabled'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
      organizationId: this.editForm.get(['organizationId'])!.value,
    };
  }
}
