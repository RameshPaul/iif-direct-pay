import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserLogin, getUserLoginIdentifier } from '../user-login.model';

export type EntityResponseType = HttpResponse<IUserLogin>;
export type EntityArrayResponseType = HttpResponse<IUserLogin[]>;

@Injectable({ providedIn: 'root' })
export class UserLoginService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-logins');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userLogin: IUserLogin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userLogin);
    return this.http
      .post<IUserLogin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userLogin: IUserLogin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userLogin);
    return this.http
      .put<IUserLogin>(`${this.resourceUrl}/${getUserLoginIdentifier(userLogin) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userLogin: IUserLogin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userLogin);
    return this.http
      .patch<IUserLogin>(`${this.resourceUrl}/${getUserLoginIdentifier(userLogin) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserLogin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserLogin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserLoginToCollectionIfMissing(
    userLoginCollection: IUserLogin[],
    ...userLoginsToCheck: (IUserLogin | null | undefined)[]
  ): IUserLogin[] {
    const userLogins: IUserLogin[] = userLoginsToCheck.filter(isPresent);
    if (userLogins.length > 0) {
      const userLoginCollectionIdentifiers = userLoginCollection.map(userLoginItem => getUserLoginIdentifier(userLoginItem)!);
      const userLoginsToAdd = userLogins.filter(userLoginItem => {
        const userLoginIdentifier = getUserLoginIdentifier(userLoginItem);
        if (userLoginIdentifier == null || userLoginCollectionIdentifiers.includes(userLoginIdentifier)) {
          return false;
        }
        userLoginCollectionIdentifiers.push(userLoginIdentifier);
        return true;
      });
      return [...userLoginsToAdd, ...userLoginCollection];
    }
    return userLoginCollection;
  }

  protected convertDateFromClient(userLogin: IUserLogin): IUserLogin {
    return Object.assign({}, userLogin, {
      emailOTPExpiryDate: userLogin.emailOTPExpiryDate?.isValid() ? userLogin.emailOTPExpiryDate.format(DATE_FORMAT) : undefined,
      phoneOTPExpiryDate: userLogin.phoneOTPExpiryDate?.isValid() ? userLogin.phoneOTPExpiryDate.format(DATE_FORMAT) : undefined,
      loginDateTime: userLogin.loginDateTime?.isValid() ? userLogin.loginDateTime.toJSON() : undefined,
      createdDate: userLogin.createdDate?.isValid() ? userLogin.createdDate.toJSON() : undefined,
      updatedDate: userLogin.updatedDate?.isValid() ? userLogin.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.emailOTPExpiryDate = res.body.emailOTPExpiryDate ? dayjs(res.body.emailOTPExpiryDate) : undefined;
      res.body.phoneOTPExpiryDate = res.body.phoneOTPExpiryDate ? dayjs(res.body.phoneOTPExpiryDate) : undefined;
      res.body.loginDateTime = res.body.loginDateTime ? dayjs(res.body.loginDateTime) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userLogin: IUserLogin) => {
        userLogin.emailOTPExpiryDate = userLogin.emailOTPExpiryDate ? dayjs(userLogin.emailOTPExpiryDate) : undefined;
        userLogin.phoneOTPExpiryDate = userLogin.phoneOTPExpiryDate ? dayjs(userLogin.phoneOTPExpiryDate) : undefined;
        userLogin.loginDateTime = userLogin.loginDateTime ? dayjs(userLogin.loginDateTime) : undefined;
        userLogin.createdDate = userLogin.createdDate ? dayjs(userLogin.createdDate) : undefined;
        userLogin.updatedDate = userLogin.updatedDate ? dayjs(userLogin.updatedDate) : undefined;
      });
    }
    return res;
  }
}
