import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserLogin, UserLogin } from '../user-login.model';
import { UserLoginService } from '../service/user-login.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { UserLoginType } from 'app/entities/enumerations/user-login-type.model';

@Component({
  selector: 'jhi-user-login-update',
  templateUrl: './user-login-update.component.html',
})
export class UserLoginUpdateComponent implements OnInit {
  isSaving = false;
  userLoginTypeValues = Object.keys(UserLoginType);

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    loginType: [],
    emailOTP: [],
    phoneOTP: [],
    emailOTPExpiryDate: [],
    phoneOTPExpiryDate: [],
    locationIP: [null, [Validators.required]],
    locationDetails: [null, [Validators.required]],
    latlog: [null, [Validators.required]],
    browser: [null, [Validators.required]],
    device: [null, [Validators.required]],
    loginDateTime: [],
    loginToken: [],
    createdDate: [],
    updatedDate: [],
    userId: [],
  });

  constructor(
    protected userLoginService: UserLoginService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userLogin }) => {
      if (userLogin.id === undefined) {
        const today = dayjs().startOf('day');
        userLogin.loginDateTime = today;
        userLogin.createdDate = today;
        userLogin.updatedDate = today;
      }

      this.updateForm(userLogin);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userLogin = this.createFromForm();
    if (userLogin.id !== undefined) {
      this.subscribeToSaveResponse(this.userLoginService.update(userLogin));
    } else {
      this.subscribeToSaveResponse(this.userLoginService.create(userLogin));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserLogin>>): void {
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

  protected updateForm(userLogin: IUserLogin): void {
    this.editForm.patchValue({
      id: userLogin.id,
      loginType: userLogin.loginType,
      emailOTP: userLogin.emailOTP,
      phoneOTP: userLogin.phoneOTP,
      emailOTPExpiryDate: userLogin.emailOTPExpiryDate,
      phoneOTPExpiryDate: userLogin.phoneOTPExpiryDate,
      locationIP: userLogin.locationIP,
      locationDetails: userLogin.locationDetails,
      latlog: userLogin.latlog,
      browser: userLogin.browser,
      device: userLogin.device,
      loginDateTime: userLogin.loginDateTime ? userLogin.loginDateTime.format(DATE_TIME_FORMAT) : null,
      loginToken: userLogin.loginToken,
      createdDate: userLogin.createdDate ? userLogin.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: userLogin.updatedDate ? userLogin.updatedDate.format(DATE_TIME_FORMAT) : null,
      userId: userLogin.userId,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, userLogin.userId);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('userId')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IUserLogin {
    return {
      ...new UserLogin(),
      id: this.editForm.get(['id'])!.value,
      loginType: this.editForm.get(['loginType'])!.value,
      emailOTP: this.editForm.get(['emailOTP'])!.value,
      phoneOTP: this.editForm.get(['phoneOTP'])!.value,
      emailOTPExpiryDate: this.editForm.get(['emailOTPExpiryDate'])!.value,
      phoneOTPExpiryDate: this.editForm.get(['phoneOTPExpiryDate'])!.value,
      locationIP: this.editForm.get(['locationIP'])!.value,
      locationDetails: this.editForm.get(['locationDetails'])!.value,
      latlog: this.editForm.get(['latlog'])!.value,
      browser: this.editForm.get(['browser'])!.value,
      device: this.editForm.get(['device'])!.value,
      loginDateTime: this.editForm.get(['loginDateTime'])!.value
        ? dayjs(this.editForm.get(['loginDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      loginToken: this.editForm.get(['loginToken'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userId: this.editForm.get(['userId'])!.value,
    };
  }
}
