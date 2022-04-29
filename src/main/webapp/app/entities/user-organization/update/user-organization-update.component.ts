import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserOrganization, UserOrganization } from '../user-organization.model';
import { UserOrganizationService } from '../service/user-organization.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { UserOrganizationStatus } from 'app/entities/enumerations/user-organization-status.model';

@Component({
  selector: 'jhi-user-organization-update',
  templateUrl: './user-organization-update.component.html',
})
export class UserOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  userOrganizationStatusValues = Object.keys(UserOrganizationStatus);

  organizationsSharedCollection: IOrganization[] = [];
  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    joiningDate: [],
    exitDate: [],
    status: [],
    suspendedDate: [],
    deletedDate: [],
    createdDate: [],
    updatedDate: [],
    organiationId: [],
    userId: [],
  });

  constructor(
    protected userOrganizationService: UserOrganizationService,
    protected organizationService: OrganizationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userOrganization }) => {
      if (userOrganization.id === undefined) {
        const today = dayjs().startOf('day');
        userOrganization.suspendedDate = today;
        userOrganization.deletedDate = today;
        userOrganization.createdDate = today;
        userOrganization.updatedDate = today;
      }

      this.updateForm(userOrganization);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userOrganization = this.createFromForm();
    if (userOrganization.id !== undefined) {
      this.subscribeToSaveResponse(this.userOrganizationService.update(userOrganization));
    } else {
      this.subscribeToSaveResponse(this.userOrganizationService.create(userOrganization));
    }
  }

  trackOrganizationById(_index: number, item: IOrganization): number {
    return item.id!;
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserOrganization>>): void {
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

  protected updateForm(userOrganization: IUserOrganization): void {
    this.editForm.patchValue({
      id: userOrganization.id,
      joiningDate: userOrganization.joiningDate,
      exitDate: userOrganization.exitDate,
      status: userOrganization.status,
      suspendedDate: userOrganization.suspendedDate ? userOrganization.suspendedDate.format(DATE_TIME_FORMAT) : null,
      deletedDate: userOrganization.deletedDate ? userOrganization.deletedDate.format(DATE_TIME_FORMAT) : null,
      createdDate: userOrganization.createdDate ? userOrganization.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: userOrganization.updatedDate ? userOrganization.updatedDate.format(DATE_TIME_FORMAT) : null,
      organiationId: userOrganization.organiationId,
      userId: userOrganization.userId,
    });

    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing(
      this.organizationsSharedCollection,
      userOrganization.organiationId
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, userOrganization.userId);
  }

  protected loadRelationshipsOptions(): void {
    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing(organizations, this.editForm.get('organiationId')!.value)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('userId')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IUserOrganization {
    return {
      ...new UserOrganization(),
      id: this.editForm.get(['id'])!.value,
      joiningDate: this.editForm.get(['joiningDate'])!.value,
      exitDate: this.editForm.get(['exitDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      suspendedDate: this.editForm.get(['suspendedDate'])!.value
        ? dayjs(this.editForm.get(['suspendedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deletedDate: this.editForm.get(['deletedDate'])!.value
        ? dayjs(this.editForm.get(['deletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? dayjs(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      organiationId: this.editForm.get(['organiationId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }
}
