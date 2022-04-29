import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPatron, getPatronIdentifier } from '../patron.model';

export type EntityResponseType = HttpResponse<IPatron>;
export type EntityArrayResponseType = HttpResponse<IPatron[]>;

@Injectable({ providedIn: 'root' })
export class PatronService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/patrons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(patron: IPatron): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patron);
    return this.http
      .post<IPatron>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patron: IPatron): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patron);
    return this.http
      .put<IPatron>(`${this.resourceUrl}/${getPatronIdentifier(patron) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(patron: IPatron): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patron);
    return this.http
      .patch<IPatron>(`${this.resourceUrl}/${getPatronIdentifier(patron) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatron>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatron[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPatronToCollectionIfMissing(patronCollection: IPatron[], ...patronsToCheck: (IPatron | null | undefined)[]): IPatron[] {
    const patrons: IPatron[] = patronsToCheck.filter(isPresent);
    if (patrons.length > 0) {
      const patronCollectionIdentifiers = patronCollection.map(patronItem => getPatronIdentifier(patronItem)!);
      const patronsToAdd = patrons.filter(patronItem => {
        const patronIdentifier = getPatronIdentifier(patronItem);
        if (patronIdentifier == null || patronCollectionIdentifiers.includes(patronIdentifier)) {
          return false;
        }
        patronCollectionIdentifiers.push(patronIdentifier);
        return true;
      });
      return [...patronsToAdd, ...patronCollection];
    }
    return patronCollection;
  }

  protected convertDateFromClient(patron: IPatron): IPatron {
    return Object.assign({}, patron, {
      recurringPeriod: patron.recurringPeriod?.isValid() ? patron.recurringPeriod.format(DATE_FORMAT) : undefined,
      commitedStartDate: patron.commitedStartDate?.isValid() ? patron.commitedStartDate.format(DATE_FORMAT) : undefined,
      commitedEndDate: patron.commitedEndDate?.isValid() ? patron.commitedEndDate.format(DATE_FORMAT) : undefined,
      actualStartDate: patron.actualStartDate?.isValid() ? patron.actualStartDate.format(DATE_FORMAT) : undefined,
      actualEndDate: patron.actualEndDate?.isValid() ? patron.actualEndDate.format(DATE_FORMAT) : undefined,
      reccuringPauseDate: patron.reccuringPauseDate?.isValid() ? patron.reccuringPauseDate.format(DATE_FORMAT) : undefined,
      recurringResumeDate: patron.recurringResumeDate?.isValid() ? patron.recurringResumeDate.format(DATE_FORMAT) : undefined,
      createdDate: patron.createdDate?.isValid() ? patron.createdDate.toJSON() : undefined,
      updatedDate: patron.updatedDate?.isValid() ? patron.updatedDate.toJSON() : undefined,
      deletedDate: patron.deletedDate?.isValid() ? patron.deletedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.recurringPeriod = res.body.recurringPeriod ? dayjs(res.body.recurringPeriod) : undefined;
      res.body.commitedStartDate = res.body.commitedStartDate ? dayjs(res.body.commitedStartDate) : undefined;
      res.body.commitedEndDate = res.body.commitedEndDate ? dayjs(res.body.commitedEndDate) : undefined;
      res.body.actualStartDate = res.body.actualStartDate ? dayjs(res.body.actualStartDate) : undefined;
      res.body.actualEndDate = res.body.actualEndDate ? dayjs(res.body.actualEndDate) : undefined;
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
      res.body.forEach((patron: IPatron) => {
        patron.recurringPeriod = patron.recurringPeriod ? dayjs(patron.recurringPeriod) : undefined;
        patron.commitedStartDate = patron.commitedStartDate ? dayjs(patron.commitedStartDate) : undefined;
        patron.commitedEndDate = patron.commitedEndDate ? dayjs(patron.commitedEndDate) : undefined;
        patron.actualStartDate = patron.actualStartDate ? dayjs(patron.actualStartDate) : undefined;
        patron.actualEndDate = patron.actualEndDate ? dayjs(patron.actualEndDate) : undefined;
        patron.reccuringPauseDate = patron.reccuringPauseDate ? dayjs(patron.reccuringPauseDate) : undefined;
        patron.recurringResumeDate = patron.recurringResumeDate ? dayjs(patron.recurringResumeDate) : undefined;
        patron.createdDate = patron.createdDate ? dayjs(patron.createdDate) : undefined;
        patron.updatedDate = patron.updatedDate ? dayjs(patron.updatedDate) : undefined;
        patron.deletedDate = patron.deletedDate ? dayjs(patron.deletedDate) : undefined;
      });
    }
    return res;
  }
}
