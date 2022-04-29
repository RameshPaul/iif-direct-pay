import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserDevice, UserDevice } from '../user-device.model';
import { UserDeviceService } from '../service/user-device.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { UserDeviceType } from 'app/entities/enumerations/user-device-type.model';
import { UserDeviceStatus } from 'app/entities/enumerations/user-device-status.model';

@Component({
  selector: 'jhi-user-device-update',
  templateUrl: './user-device-update.component.html',
})
export class UserDeviceUpdateComponent implements OnInit {
  isSaving = false;
  userDeviceTypeValues = Object.keys(UserDeviceType);
  userDeviceStatusValues = Object.keys(UserDeviceStatus);

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    deviceName: [null, [Validators.required]],
    deviceId: [null, [Validators.required]],
    deviceToken: [],
    deviceType: [null, [Validators.required]],
    deviceOS: [null, [Validators.required]],
    notificationEnabled: [null, [Validators.required]],
    lastActivityDetails: [null, [Validators.required]],
    lastActiveDate: [null, [Validators.required]],
    lastActiveLocation: [null, [Validators.required]],
    appVersion: [null, [Validators.required]],
    forceLogin: [null, [Validators.required]],
    status: [],
    loginDateTime: [],
    exitDate: [],
    createdDate: [],
    updatedDate: [],
    userId: [],
  });

  constructor(
    protected userDeviceService: UserDeviceService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDevice }) => {
      if (userDevice.id === undefined) {
        const today = dayjs().startOf('day');
        userDevice.loginDateTime = today;
        userDevice.exitDate = today;
        userDevice.createdDate = today;
        userDevice.updatedDate = today;
      }

      this.updateForm(userDevice);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userDevice = this.createFromForm();
    if (userDevice.id !== undefined) {
      this.subscribeToSaveResponse(this.userDeviceService.update(userDevice));
    } else {
      this.subscribeToSaveResponse(this.userDeviceService.create(userDevice));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserDevice>>): void {
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

  protected updateForm(userDevice: IUserDevice): void {
    this.editForm.patchValue({
      id: userDevice.id,
      deviceName: userDevice.deviceName,
      deviceId: userDevice.deviceId,
      deviceToken: userDevice.deviceToken,
      deviceType: userDevice.deviceType,
      deviceOS: userDevice.deviceOS,
      notificationEnabled: userDevice.notificationEnabled,
      lastActivityDetails: userDevice.lastActivityDetails,
      lastActiveDate: userDevice.lastActiveDate,
      lastActiveLocation: userDevice.lastActiveLocation,
      appVersion: userDevice.appVersion,
      forceLogin: userDevice.forceLogin,
      status: userDevice.status,
      loginDateTime: userDevice.loginDateTime ? userDevice.loginDateTime.format(DATE_TIME_FORMAT) : null,
      exitDate: userDevice.exitDate ? userDevice.exitDate.format(DATE_TIME_FORMAT) : null,
      createdDate: userDevice.createdDate ? userDevice.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: userDevice.updatedDate ? userDevice.updatedDate.format(DATE_TIME_FORMAT) : null,
      userId: userDevice.userId,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, userDevice.userId);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('userId')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IUserDevice {
    return {
      ...new UserDevice(),
      id: this.editForm.get(['id'])!.value,
      deviceName: this.editForm.get(['deviceName'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
      deviceToken: this.editForm.get(['deviceToken'])!.value,
      deviceType: this.editForm.get(['deviceType'])!.value,
      deviceOS: this.editForm.get(['deviceOS'])!.value,
      notificationEnabled: this.editForm.get(['notificationEnabled'])!.value,
      lastActivityDetails: this.editForm.get(['lastActivityDetails'])!.value,
      lastActiveDate: this.editForm.get(['lastActiveDate'])!.value,
      lastActiveLocation: this.editForm.get(['lastActiveLocation'])!.value,
      appVersion: this.editForm.get(['appVersion'])!.value,
      forceLogin: this.editForm.get(['forceLogin'])!.value,
      status: this.editForm.get(['status'])!.value,
      loginDateTime: this.editForm.get(['loginDateTime'])!.value
        ? dayjs(this.editForm.get(['loginDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      exitDate: this.editForm.get(['exitDate'])!.value ? dayjs(this.editForm.get(['exitDate'])!.value, DATE_TIME_FORMAT) : undefined,
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
