import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserAccount, getUserAccountIdentifier } from '../user-account.model';

export type EntityResponseType = HttpResponse<IUserAccount>;
export type EntityArrayResponseType = HttpResponse<IUserAccount[]>;

@Injectable({ providedIn: 'root' })
export class UserAccountService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-accounts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userAccount: IUserAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccount);
    return this.http
      .post<IUserAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userAccount: IUserAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccount);
    return this.http
      .put<IUserAccount>(`${this.resourceUrl}/${getUserAccountIdentifier(userAccount) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userAccount: IUserAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccount);
    return this.http
      .patch<IUserAccount>(`${this.resourceUrl}/${getUserAccountIdentifier(userAccount) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserAccountToCollectionIfMissing(
    userAccountCollection: IUserAccount[],
    ...userAccountsToCheck: (IUserAccount | null | undefined)[]
  ): IUserAccount[] {
    const userAccounts: IUserAccount[] = userAccountsToCheck.filter(isPresent);
    if (userAccounts.length > 0) {
      const userAccountCollectionIdentifiers = userAccountCollection.map(userAccountItem => getUserAccountIdentifier(userAccountItem)!);
      const userAccountsToAdd = userAccounts.filter(userAccountItem => {
        const userAccountIdentifier = getUserAccountIdentifier(userAccountItem);
        if (userAccountIdentifier == null || userAccountCollectionIdentifiers.includes(userAccountIdentifier)) {
          return false;
        }
        userAccountCollectionIdentifiers.push(userAccountIdentifier);
        return true;
      });
      return [...userAccountsToAdd, ...userAccountCollection];
    }
    return userAccountCollection;
  }

  protected convertDateFromClient(userAccount: IUserAccount): IUserAccount {
    return Object.assign({}, userAccount, {
      upiActiveDate: userAccount.upiActiveDate?.isValid() ? userAccount.upiActiveDate.format(DATE_FORMAT) : undefined,
      upiSuspendedDate: userAccount.upiSuspendedDate?.isValid() ? userAccount.upiSuspendedDate.toJSON() : undefined,
      upiDeletedDate: userAccount.upiDeletedDate?.isValid() ? userAccount.upiDeletedDate.toJSON() : undefined,
      bankActiveDate: userAccount.bankActiveDate?.isValid() ? userAccount.bankActiveDate.toJSON() : undefined,
      bankSuspendedDate: userAccount.bankSuspendedDate?.isValid() ? userAccount.bankSuspendedDate.toJSON() : undefined,
      bankDeletedDate: userAccount.bankDeletedDate?.isValid() ? userAccount.bankDeletedDate.toJSON() : undefined,
      wallterActiveDate: userAccount.wallterActiveDate?.isValid() ? userAccount.wallterActiveDate.toJSON() : undefined,
      walletSuspendedDate: userAccount.walletSuspendedDate?.isValid() ? userAccount.walletSuspendedDate.toJSON() : undefined,
      walletDeletedDate: userAccount.walletDeletedDate?.isValid() ? userAccount.walletDeletedDate.toJSON() : undefined,
      createdDate: userAccount.createdDate?.isValid() ? userAccount.createdDate.toJSON() : undefined,
      updatedDate: userAccount.updatedDate?.isValid() ? userAccount.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.upiActiveDate = res.body.upiActiveDate ? dayjs(res.body.upiActiveDate) : undefined;
      res.body.upiSuspendedDate = res.body.upiSuspendedDate ? dayjs(res.body.upiSuspendedDate) : undefined;
      res.body.upiDeletedDate = res.body.upiDeletedDate ? dayjs(res.body.upiDeletedDate) : undefined;
      res.body.bankActiveDate = res.body.bankActiveDate ? dayjs(res.body.bankActiveDate) : undefined;
      res.body.bankSuspendedDate = res.body.bankSuspendedDate ? dayjs(res.body.bankSuspendedDate) : undefined;
      res.body.bankDeletedDate = res.body.bankDeletedDate ? dayjs(res.body.bankDeletedDate) : undefined;
      res.body.wallterActiveDate = res.body.wallterActiveDate ? dayjs(res.body.wallterActiveDate) : undefined;
      res.body.walletSuspendedDate = res.body.walletSuspendedDate ? dayjs(res.body.walletSuspendedDate) : undefined;
      res.body.walletDeletedDate = res.body.walletDeletedDate ? dayjs(res.body.walletDeletedDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userAccount: IUserAccount) => {
        userAccount.upiActiveDate = userAccount.upiActiveDate ? dayjs(userAccount.upiActiveDate) : undefined;
        userAccount.upiSuspendedDate = userAccount.upiSuspendedDate ? dayjs(userAccount.upiSuspendedDate) : undefined;
        userAccount.upiDeletedDate = userAccount.upiDeletedDate ? dayjs(userAccount.upiDeletedDate) : undefined;
        userAccount.bankActiveDate = userAccount.bankActiveDate ? dayjs(userAccount.bankActiveDate) : undefined;
        userAccount.bankSuspendedDate = userAccount.bankSuspendedDate ? dayjs(userAccount.bankSuspendedDate) : undefined;
        userAccount.bankDeletedDate = userAccount.bankDeletedDate ? dayjs(userAccount.bankDeletedDate) : undefined;
        userAccount.wallterActiveDate = userAccount.wallterActiveDate ? dayjs(userAccount.wallterActiveDate) : undefined;
        userAccount.walletSuspendedDate = userAccount.walletSuspendedDate ? dayjs(userAccount.walletSuspendedDate) : undefined;
        userAccount.walletDeletedDate = userAccount.walletDeletedDate ? dayjs(userAccount.walletDeletedDate) : undefined;
        userAccount.createdDate = userAccount.createdDate ? dayjs(userAccount.createdDate) : undefined;
        userAccount.updatedDate = userAccount.updatedDate ? dayjs(userAccount.updatedDate) : undefined;
      });
    }
    return res;
  }
}
