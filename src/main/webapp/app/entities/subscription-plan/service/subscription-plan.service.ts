import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubscriptionPlan, getSubscriptionPlanIdentifier } from '../subscription-plan.model';

export type EntityResponseType = HttpResponse<ISubscriptionPlan>;
export type EntityArrayResponseType = HttpResponse<ISubscriptionPlan[]>;

@Injectable({ providedIn: 'root' })
export class SubscriptionPlanService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subscription-plans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subscriptionPlan: ISubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionPlan);
    return this.http
      .post<ISubscriptionPlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subscriptionPlan: ISubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionPlan);
    return this.http
      .put<ISubscriptionPlan>(`${this.resourceUrl}/${getSubscriptionPlanIdentifier(subscriptionPlan) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(subscriptionPlan: ISubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionPlan);
    return this.http
      .patch<ISubscriptionPlan>(`${this.resourceUrl}/${getSubscriptionPlanIdentifier(subscriptionPlan) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubscriptionPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubscriptionPlan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubscriptionPlanToCollectionIfMissing(
    subscriptionPlanCollection: ISubscriptionPlan[],
    ...subscriptionPlansToCheck: (ISubscriptionPlan | null | undefined)[]
  ): ISubscriptionPlan[] {
    const subscriptionPlans: ISubscriptionPlan[] = subscriptionPlansToCheck.filter(isPresent);
    if (subscriptionPlans.length > 0) {
      const subscriptionPlanCollectionIdentifiers = subscriptionPlanCollection.map(
        subscriptionPlanItem => getSubscriptionPlanIdentifier(subscriptionPlanItem)!
      );
      const subscriptionPlansToAdd = subscriptionPlans.filter(subscriptionPlanItem => {
        const subscriptionPlanIdentifier = getSubscriptionPlanIdentifier(subscriptionPlanItem);
        if (subscriptionPlanIdentifier == null || subscriptionPlanCollectionIdentifiers.includes(subscriptionPlanIdentifier)) {
          return false;
        }
        subscriptionPlanCollectionIdentifiers.push(subscriptionPlanIdentifier);
        return true;
      });
      return [...subscriptionPlansToAdd, ...subscriptionPlanCollection];
    }
    return subscriptionPlanCollection;
  }

  protected convertDateFromClient(subscriptionPlan: ISubscriptionPlan): ISubscriptionPlan {
    return Object.assign({}, subscriptionPlan, {
      createdDate: subscriptionPlan.createdDate?.isValid() ? subscriptionPlan.createdDate.toJSON() : undefined,
      updatedDate: subscriptionPlan.updatedDate?.isValid() ? subscriptionPlan.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((subscriptionPlan: ISubscriptionPlan) => {
        subscriptionPlan.createdDate = subscriptionPlan.createdDate ? dayjs(subscriptionPlan.createdDate) : undefined;
        subscriptionPlan.updatedDate = subscriptionPlan.updatedDate ? dayjs(subscriptionPlan.updatedDate) : undefined;
      });
    }
    return res;
  }
}
