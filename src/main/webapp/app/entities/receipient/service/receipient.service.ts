import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReceipient, getReceipientIdentifier } from '../receipient.model';

export type EntityResponseType = HttpResponse<IReceipient>;
export type EntityArrayResponseType = HttpResponse<IReceipient[]>;

@Injectable({ providedIn: 'root' })
export class ReceipientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/receipients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(receipient: IReceipient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipient);
    return this.http
      .post<IReceipient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(receipient: IReceipient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipient);
    return this.http
      .put<IReceipient>(`${this.resourceUrl}/${getReceipientIdentifier(receipient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(receipient: IReceipient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receipient);
    return this.http
      .patch<IReceipient>(`${this.resourceUrl}/${getReceipientIdentifier(receipient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReceipient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceipient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addReceipientToCollectionIfMissing(
    receipientCollection: IReceipient[],
    ...receipientsToCheck: (IReceipient | null | undefined)[]
  ): IReceipient[] {
    const receipients: IReceipient[] = receipientsToCheck.filter(isPresent);
    if (receipients.length > 0) {
      const receipientCollectionIdentifiers = receipientCollection.map(receipientItem => getReceipientIdentifier(receipientItem)!);
      const receipientsToAdd = receipients.filter(receipientItem => {
        const receipientIdentifier = getReceipientIdentifier(receipientItem);
        if (receipientIdentifier == null || receipientCollectionIdentifiers.includes(receipientIdentifier)) {
          return false;
        }
        receipientCollectionIdentifiers.push(receipientIdentifier);
        return true;
      });
      return [...receipientsToAdd, ...receipientCollection];
    }
    return receipientCollection;
  }

  protected convertDateFromClient(receipient: IReceipient): IReceipient {
    return Object.assign({}, receipient, {
      recurringStartDate: receipient.recurringStartDate?.isValid() ? receipient.recurringStartDate.format(DATE_FORMAT) : undefined,
      recurringEndDate: receipient.recurringEndDate?.isValid() ? receipient.recurringEndDate.format(DATE_FORMAT) : undefined,
      startDate: receipient.startDate?.isValid() ? receipient.startDate.format(DATE_FORMAT) : undefined,
      endDate: receipient.endDate?.isValid() ? receipient.endDate.format(DATE_FORMAT) : undefined,
      approvedDateTime: receipient.approvedDateTime?.isValid() ? receipient.approvedDateTime.toJSON() : undefined,
      rejectedDateTime: receipient.rejectedDateTime?.isValid() ? receipient.rejectedDateTime.toJSON() : undefined,
      onboardedDate: receipient.onboardedDate?.isValid() ? receipient.onboardedDate.format(DATE_FORMAT) : undefined,
      reccuringPauseDate: receipient.reccuringPauseDate?.isValid() ? receipient.reccuringPauseDate.format(DATE_FORMAT) : undefined,
      recurringResumeDate: receipient.recurringResumeDate?.isValid() ? receipient.recurringResumeDate.format(DATE_FORMAT) : undefined,
      createdDate: receipient.createdDate?.isValid() ? receipient.createdDate.toJSON() : undefined,
      updatedDate: receipient.updatedDate?.isValid() ? receipient.updatedDate.toJSON() : undefined,
      deletedDate: receipient.deletedDate?.isValid() ? receipient.deletedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.recurringStartDate = res.body.recurringStartDate ? dayjs(res.body.recurringStartDate) : undefined;
      res.body.recurringEndDate = res.body.recurringEndDate ? dayjs(res.body.recurringEndDate) : undefined;
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
      res.body.approvedDateTime = res.body.approvedDateTime ? dayjs(res.body.approvedDateTime) : undefined;
      res.body.rejectedDateTime = res.body.rejectedDateTime ? dayjs(res.body.rejectedDateTime) : undefined;
      res.body.onboardedDate = res.body.onboardedDate ? dayjs(res.body.onboardedDate) : undefined;
      res.body.reccuringPauseDate = res.body.reccuringPauseDate ? dayjs(res.body.reccuringPauseDate) : undefined;
      res.body.recurringResumeDate = res.body.recurringResumeDate ? dayjs(res.body.recurringResumeDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
      res.body.deletedDate = res.body.deletedDate ? dayjs(res.body.deletedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((receipient: IReceipient) => {
        receipient.recurringStartDate = receipient.recurringStartDate ? dayjs(receipient.recurringStartDate) : undefined;
        receipient.recurringEndDate = receipient.recurringEndDate ? dayjs(receipient.recurringEndDate) : undefined;
        receipient.startDate = receipient.startDate ? dayjs(receipient.startDate) : undefined;
        receipient.endDate = receipient.endDate ? dayjs(receipient.endDate) : undefined;
        receipient.approvedDateTime = receipient.approvedDateTime ? dayjs(receipient.approvedDateTime) : undefined;
        receipient.rejectedDateTime = receipient.rejectedDateTime ? dayjs(receipient.rejectedDateTime) : undefined;
        receipient.onboardedDate = receipient.onboardedDate ? dayjs(receipient.onboardedDate) : undefined;
        receipient.reccuringPauseDate = receipient.reccuringPauseDate ? dayjs(receipient.reccuringPauseDate) : undefined;
        receipient.recurringResumeDate = receipient.recurringResumeDate ? dayjs(receipient.recurringResumeDate) : undefined;
        receipient.createdDate = receipient.createdDate ? dayjs(receipient.createdDate) : undefined;
        receipient.updatedDate = receipient.updatedDate ? dayjs(receipient.updatedDate) : undefined;
        receipient.deletedDate = receipient.deletedDate ? dayjs(receipient.deletedDate) : undefined;
      });
    }
    return res;
  }
}
