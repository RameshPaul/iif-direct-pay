import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserDevice, getUserDeviceIdentifier } from '../user-device.model';

export type EntityResponseType = HttpResponse<IUserDevice>;
export type EntityArrayResponseType = HttpResponse<IUserDevice[]>;

@Injectable({ providedIn: 'root' })
export class UserDeviceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-devices');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userDevice: IUserDevice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userDevice);
    return this.http
      .post<IUserDevice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userDevice: IUserDevice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userDevice);
    return this.http
      .put<IUserDevice>(`${this.resourceUrl}/${getUserDeviceIdentifier(userDevice) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userDevice: IUserDevice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userDevice);
    return this.http
      .patch<IUserDevice>(`${this.resourceUrl}/${getUserDeviceIdentifier(userDevice) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserDevice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserDevice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserDeviceToCollectionIfMissing(
    userDeviceCollection: IUserDevice[],
    ...userDevicesToCheck: (IUserDevice | null | undefined)[]
  ): IUserDevice[] {
    const userDevices: IUserDevice[] = userDevicesToCheck.filter(isPresent);
    if (userDevices.length > 0) {
      const userDeviceCollectionIdentifiers = userDeviceCollection.map(userDeviceItem => getUserDeviceIdentifier(userDeviceItem)!);
      const userDevicesToAdd = userDevices.filter(userDeviceItem => {
        const userDeviceIdentifier = getUserDeviceIdentifier(userDeviceItem);
        if (userDeviceIdentifier == null || userDeviceCollectionIdentifiers.includes(userDeviceIdentifier)) {
          return false;
        }
        userDeviceCollectionIdentifiers.push(userDeviceIdentifier);
        return true;
      });
      return [...userDevicesToAdd, ...userDeviceCollection];
    }
    return userDeviceCollection;
  }

  protected convertDateFromClient(userDevice: IUserDevice): IUserDevice {
    return Object.assign({}, userDevice, {
      lastActiveDate: userDevice.lastActiveDate?.isValid() ? userDevice.lastActiveDate.format(DATE_FORMAT) : undefined,
      loginDateTime: userDevice.loginDateTime?.isValid() ? userDevice.loginDateTime.toJSON() : undefined,
      exitDate: userDevice.exitDate?.isValid() ? userDevice.exitDate.toJSON() : undefined,
      createdDate: userDevice.createdDate?.isValid() ? userDevice.createdDate.toJSON() : undefined,
      updatedDate: userDevice.updatedDate?.isValid() ? userDevice.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastActiveDate = res.body.lastActiveDate ? dayjs(res.body.lastActiveDate) : undefined;
      res.body.loginDateTime = res.body.loginDateTime ? dayjs(res.body.loginDateTime) : undefined;
      res.body.exitDate = res.body.exitDate ? dayjs(res.body.exitDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userDevice: IUserDevice) => {
        userDevice.lastActiveDate = userDevice.lastActiveDate ? dayjs(userDevice.lastActiveDate) : undefined;
        userDevice.loginDateTime = userDevice.loginDateTime ? dayjs(userDevice.loginDateTime) : undefined;
        userDevice.exitDate = userDevice.exitDate ? dayjs(userDevice.exitDate) : undefined;
        userDevice.createdDate = userDevice.createdDate ? dayjs(userDevice.createdDate) : undefined;
        userDevice.updatedDate = userDevice.updatedDate ? dayjs(userDevice.updatedDate) : undefined;
      });
    }
    return res;
  }
}
