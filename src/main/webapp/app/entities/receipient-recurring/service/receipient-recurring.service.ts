import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReceipientRecurring, getReceipientRecurringIdentifier } from '../receipient-recurring.model';

export type EntityResponseType = HttpResponse<IReceipientRecurring>;
export type EntityArrayResponseType = HttpResponse<IReceipientRecurring[]>;

@Injectable({ providedIn: 'root' })
export class ReceipientRecurringService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/receipient-recurrings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(receipientRecurring: IReceipientRecurring): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipientRecurring);
    return this.http
      .post<IReceipientRecurring>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(receipientRecurring: IReceipientRecurring): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipientRecurring);
    return this.http
      .put<IReceipientRecurring>(`${this.resourceUrl}/${getReceipientRecurringIdentifier(receipientRecurring) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(receipientRecurring: IReceipientRecurring): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipientRecurring);
    return this.http
      .patch<IReceipientRecurring>(`${this.resourceUrl}/${getReceipientRecurringIdentifier(receipientRecurring) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReceipientRecurring>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceipientRecurring[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addReceipientRecurringToCollectionIfMissing(
    receipientRecurringCollection: IReceipientRecurring[],
    ...receipientRecurringsToCheck: (IReceipientRecurring | null | undefined)[]
  ): IReceipientRecurring[] {
    const receipientRecurrings: IReceipientRecurring[] = receipientRecurringsToCheck.filter(isPresent);
    if (receipientRecurrings.length > 0) {
      const receipientRecurringCollectionIdentifiers = receipientRecurringCollection.map(
        receipientRecurringItem => getReceipientRecurringIdentifier(receipientRecurringItem)!
      );
      const receipientRecurringsToAdd = receipientRecurrings.filter(receipientRecurringItem => {
        const receipientRecurringIdentifier = getReceipientRecurringIdentifier(receipientRecurringItem);
        if (receipientRecurringIdentifier == null || receipientRecurringCollectionIdentifiers.includes(receipientRecurringIdentifier)) {
          return false;
        }
        receipientRecurringCollectionIdentifiers.push(receipientRecurringIdentifier);
        return true;
      });
      return [...receipientRecurringsToAdd, ...receipientRecurringCollection];
    }
    return receipientRecurringCollection;
  }

  protected convertDateFromClient(receipientRecurring: IReceipientRecurring): IReceipientRecurring {
    return Object.assign({}, receipientRecurring, {
      startDate: receipientRecurring.startDate?.isValid() ? receipientRecurring.startDate.format(DATE_FORMAT) : undefined,
      endDate: receipientRecurring.endDate?.isValid() ? receipientRecurring.endDate.format(DATE_FORMAT) : undefined,
      pauseDate: receipientRecurring.pauseDate?.isValid() ? receipientRecurring.pauseDate.format(DATE_FORMAT) : undefined,
      resumeDate: receipientRecurring.resumeDate?.isValid() ? receipientRecurring.resumeDate.format(DATE_FORMAT) : undefined,
      createdDate: receipientRecurring.createdDate?.isValid() ? receipientRecurring.createdDate.toJSON() : undefined,
      updatedDate: receipientRecurring.updatedDate?.isValid() ? receipientRecurring.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
      res.body.pauseDate = res.body.pauseDate ? dayjs(res.body.pauseDate) : undefined;
      res.body.resumeDate = res.body.resumeDate ? dayjs(res.body.resumeDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((receipientRecurring: IReceipientRecurring) => {
        receipientRecurring.startDate = receipientRecurring.startDate ? dayjs(receipientRecurring.startDate) : undefined;
        receipientRecurring.endDate = receipientRecurring.endDate ? dayjs(receipientRecurring.endDate) : undefined;
        receipientRecurring.pauseDate = receipientRecurring.pauseDate ? dayjs(receipientRecurring.pauseDate) : undefined;
        receipientRecurring.resumeDate = receipientRecurring.resumeDate ? dayjs(receipientRecurring.resumeDate) : undefined;
        receipientRecurring.createdDate = receipientRecurring.createdDate ? dayjs(receipientRecurring.createdDate) : undefined;
        receipientRecurring.updatedDate = receipientRecurring.updatedDate ? dayjs(receipientRecurring.updatedDate) : undefined;
      });
    }
    return res;
  }
}
