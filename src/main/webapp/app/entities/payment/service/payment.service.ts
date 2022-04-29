import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPayment, getPaymentIdentifier } from '../payment.model';

export type EntityResponseType = HttpResponse<IPayment>;
export type EntityArrayResponseType = HttpResponse<IPayment[]>;

@Injectable({ providedIn: 'root' })
export class PaymentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(payment: IPayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payment);
    return this.http
      .post<IPayment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payment: IPayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payment);
    return this.http
      .put<IPayment>(`${this.resourceUrl}/${getPaymentIdentifier(payment) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(payment: IPayment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payment);
    return this.http
      .patch<IPayment>(`${this.resourceUrl}/${getPaymentIdentifier(payment) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentToCollectionIfMissing(paymentCollection: IPayment[], ...paymentsToCheck: (IPayment | null | undefined)[]): IPayment[] {
    const payments: IPayment[] = paymentsToCheck.filter(isPresent);
    if (payments.length > 0) {
      const paymentCollectionIdentifiers = paymentCollection.map(paymentItem => getPaymentIdentifier(paymentItem)!);
      const paymentsToAdd = payments.filter(paymentItem => {
        const paymentIdentifier = getPaymentIdentifier(paymentItem);
        if (paymentIdentifier == null || paymentCollectionIdentifiers.includes(paymentIdentifier)) {
          return false;
        }
        paymentCollectionIdentifiers.push(paymentIdentifier);
        return true;
      });
      return [...paymentsToAdd, ...paymentCollection];
    }
    return paymentCollection;
  }

  protected convertDateFromClient(payment: IPayment): IPayment {
    return Object.assign({}, payment, {
      recurringPeriod: payment.recurringPeriod?.isValid() ? payment.recurringPeriod.format(DATE_FORMAT) : undefined,
      paymentStartDateTime: payment.paymentStartDateTime?.isValid() ? payment.paymentStartDateTime.format(DATE_FORMAT) : undefined,
      paymentCompleteDateTime: payment.paymentCompleteDateTime?.isValid() ? payment.paymentCompleteDateTime.format(DATE_FORMAT) : undefined,
      paymentFailureDateTime: payment.paymentFailureDateTime?.isValid() ? payment.paymentFailureDateTime.format(DATE_FORMAT) : undefined,
      paymentReceivedDateTIme: payment.paymentReceivedDateTIme?.isValid() ? payment.paymentReceivedDateTIme.format(DATE_FORMAT) : undefined,
      paymentRefundedDateTime: payment.paymentRefundedDateTime?.isValid() ? payment.paymentRefundedDateTime.format(DATE_FORMAT) : undefined,
      flaggedDateTime: payment.flaggedDateTime?.isValid() ? payment.flaggedDateTime.format(DATE_FORMAT) : undefined,
      flagCreatedDateTime: payment.flagCreatedDateTime?.isValid() ? payment.flagCreatedDateTime.format(DATE_FORMAT) : undefined,
      createdDate: payment.createdDate?.isValid() ? payment.createdDate.toJSON() : undefined,
      updatedDate: payment.updatedDate?.isValid() ? payment.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.recurringPeriod = res.body.recurringPeriod ? dayjs(res.body.recurringPeriod) : undefined;
      res.body.paymentStartDateTime = res.body.paymentStartDateTime ? dayjs(res.body.paymentStartDateTime) : undefined;
      res.body.paymentCompleteDateTime = res.body.paymentCompleteDateTime ? dayjs(res.body.paymentCompleteDateTime) : undefined;
      res.body.paymentFailureDateTime = res.body.paymentFailureDateTime ? dayjs(res.body.paymentFailureDateTime) : undefined;
      res.body.paymentReceivedDateTIme = res.body.paymentReceivedDateTIme ? dayjs(res.body.paymentReceivedDateTIme) : undefined;
      res.body.paymentRefundedDateTime = res.body.paymentRefundedDateTime ? dayjs(res.body.paymentRefundedDateTime) : undefined;
      res.body.flaggedDateTime = res.body.flaggedDateTime ? dayjs(res.body.flaggedDateTime) : undefined;
      res.body.flagCreatedDateTime = res.body.flagCreatedDateTime ? dayjs(res.body.flagCreatedDateTime) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payment: IPayment) => {
        payment.recurringPeriod = payment.recurringPeriod ? dayjs(payment.recurringPeriod) : undefined;
        payment.paymentStartDateTime = payment.paymentStartDateTime ? dayjs(payment.paymentStartDateTime) : undefined;
        payment.paymentCompleteDateTime = payment.paymentCompleteDateTime ? dayjs(payment.paymentCompleteDateTime) : undefined;
        payment.paymentFailureDateTime = payment.paymentFailureDateTime ? dayjs(payment.paymentFailureDateTime) : undefined;
        payment.paymentReceivedDateTIme = payment.paymentReceivedDateTIme ? dayjs(payment.paymentReceivedDateTIme) : undefined;
        payment.paymentRefundedDateTime = payment.paymentRefundedDateTime ? dayjs(payment.paymentRefundedDateTime) : undefined;
        payment.flaggedDateTime = payment.flaggedDateTime ? dayjs(payment.flaggedDateTime) : undefined;
        payment.flagCreatedDateTime = payment.flagCreatedDateTime ? dayjs(payment.flagCreatedDateTime) : undefined;
        payment.createdDate = payment.createdDate ? dayjs(payment.createdDate) : undefined;
        payment.updatedDate = payment.updatedDate ? dayjs(payment.updatedDate) : undefined;
      });
    }
    return res;
  }
}
