import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserOrganization, getUserOrganizationIdentifier } from '../user-organization.model';

export type EntityResponseType = HttpResponse<IUserOrganization>;
export type EntityArrayResponseType = HttpResponse<IUserOrganization[]>;

@Injectable({ providedIn: 'root' })
export class UserOrganizationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userOrganization: IUserOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userOrganization);
    return this.http
      .post<IUserOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userOrganization: IUserOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userOrganization);
    return this.http
      .put<IUserOrganization>(`${this.resourceUrl}/${getUserOrganizationIdentifier(userOrganization) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userOrganization: IUserOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userOrganization);
    return this.http
      .patch<IUserOrganization>(`${this.resourceUrl}/${getUserOrganizationIdentifier(userOrganization) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserOrganizationToCollectionIfMissing(
    userOrganizationCollection: IUserOrganization[],
    ...userOrganizationsToCheck: (IUserOrganization | null | undefined)[]
  ): IUserOrganization[] {
    const userOrganizations: IUserOrganization[] = userOrganizationsToCheck.filter(isPresent);
    if (userOrganizations.length > 0) {
      const userOrganizationCollectionIdentifiers = userOrganizationCollection.map(
        userOrganizationItem => getUserOrganizationIdentifier(userOrganizationItem)!
      );
      const userOrganizationsToAdd = userOrganizations.filter(userOrganizationItem => {
        const userOrganizationIdentifier = getUserOrganizationIdentifier(userOrganizationItem);
        if (userOrganizationIdentifier == null || userOrganizationCollectionIdentifiers.includes(userOrganizationIdentifier)) {
          return false;
        }
        userOrganizationCollectionIdentifiers.push(userOrganizationIdentifier);
        return true;
      });
      return [...userOrganizationsToAdd, ...userOrganizationCollection];
    }
    return userOrganizationCollection;
  }

  protected convertDateFromClient(userOrganization: IUserOrganization): IUserOrganization {
    return Object.assign({}, userOrganization, {
      joiningDate: userOrganization.joiningDate?.isValid() ? userOrganization.joiningDate.format(DATE_FORMAT) : undefined,
      exitDate: userOrganization.exitDate?.isValid() ? userOrganization.exitDate.format(DATE_FORMAT) : undefined,
      suspendedDate: userOrganization.suspendedDate?.isValid() ? userOrganization.suspendedDate.toJSON() : undefined,
      deletedDate: userOrganization.deletedDate?.isValid() ? userOrganization.deletedDate.toJSON() : undefined,
      createdDate: userOrganization.createdDate?.isValid() ? userOrganization.createdDate.toJSON() : undefined,
      updatedDate: userOrganization.updatedDate?.isValid() ? userOrganization.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.joiningDate = res.body.joiningDate ? dayjs(res.body.joiningDate) : undefined;
      res.body.exitDate = res.body.exitDate ? dayjs(res.body.exitDate) : undefined;
      res.body.suspendedDate = res.body.suspendedDate ? dayjs(res.body.suspendedDate) : undefined;
      res.body.deletedDate = res.body.deletedDate ? dayjs(res.body.deletedDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userOrganization: IUserOrganization) => {
        userOrganization.joiningDate = userOrganization.joiningDate ? dayjs(userOrganization.joiningDate) : undefined;
        userOrganization.exitDate = userOrganization.exitDate ? dayjs(userOrganization.exitDate) : undefined;
        userOrganization.suspendedDate = userOrganization.suspendedDate ? dayjs(userOrganization.suspendedDate) : undefined;
        userOrganization.deletedDate = userOrganization.deletedDate ? dayjs(userOrganization.deletedDate) : undefined;
        userOrganization.createdDate = userOrganization.createdDate ? dayjs(userOrganization.createdDate) : undefined;
        userOrganization.updatedDate = userOrganization.updatedDate ? dayjs(userOrganization.updatedDate) : undefined;
      });
    }
    return res;
  }
}
