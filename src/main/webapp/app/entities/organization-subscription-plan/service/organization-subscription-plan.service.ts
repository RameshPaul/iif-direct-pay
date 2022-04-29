import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationSubscriptionPlan, getOrganizationSubscriptionPlanIdentifier } from '../organization-subscription-plan.model';

export type EntityResponseType = HttpResponse<IOrganizationSubscriptionPlan>;
export type EntityArrayResponseType = HttpResponse<IOrganizationSubscriptionPlan[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationSubscriptionPlanService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organization-subscription-plans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organizationSubscriptionPlan: IOrganizationSubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationSubscriptionPlan);
    return this.http
      .post<IOrganizationSubscriptionPlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(organizationSubscriptionPlan: IOrganizationSubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationSubscriptionPlan);
    return this.http
      .put<IOrganizationSubscriptionPlan>(
        `${this.resourceUrl}/${getOrganizationSubscriptionPlanIdentifier(organizationSubscriptionPlan) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(organizationSubscriptionPlan: IOrganizationSubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organizationSubscriptionPlan);
    return this.http
      .patch<IOrganizationSubscriptionPlan>(
        `${this.resourceUrl}/${getOrganizationSubscriptionPlanIdentifier(organizationSubscriptionPlan) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrganizationSubscriptionPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrganizationSubscriptionPlan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrganizationSubscriptionPlanToCollectionIfMissing(
    organizationSubscriptionPlanCollection: IOrganizationSubscriptionPlan[],
    ...organizationSubscriptionPlansToCheck: (IOrganizationSubscriptionPlan | null | undefined)[]
  ): IOrganizationSubscriptionPlan[] {
    const organizationSubscriptionPlans: IOrganizationSubscriptionPlan[] = organizationSubscriptionPlansToCheck.filter(isPresent);
    if (organizationSubscriptionPlans.length > 0) {
      const organizationSubscriptionPlanCollectionIdentifiers = organizationSubscriptionPlanCollection.map(
        organizationSubscriptionPlanItem => getOrganizationSubscriptionPlanIdentifier(organizationSubscriptionPlanItem)!
      );
      const organizationSubscriptionPlansToAdd = organizationSubscriptionPlans.filter(organizationSubscriptionPlanItem => {
        const organizationSubscriptionPlanIdentifier = getOrganizationSubscriptionPlanIdentifier(organizationSubscriptionPlanItem);
        if (
          organizationSubscriptionPlanIdentifier == null ||
          organizationSubscriptionPlanCollectionIdentifiers.includes(organizationSubscriptionPlanIdentifier)
        ) {
          return false;
        }
        organizationSubscriptionPlanCollectionIdentifiers.push(organizationSubscriptionPlanIdentifier);
        return true;
      });
      return [...organizationSubscriptionPlansToAdd, ...organizationSubscriptionPlanCollection];
    }
    return organizationSubscriptionPlanCollection;
  }

  protected convertDateFromClient(organizationSubscriptionPlan: IOrganizationSubscriptionPlan): IOrganizationSubscriptionPlan {
    return Object.assign({}, organizationSubscriptionPlan, {
      startDate: organizationSubscriptionPlan.startDate?.isValid() ? organizationSubscriptionPlan.startDate.format(DATE_FORMAT) : undefined,
      endDate: organizationSubscriptionPlan.endDate?.isValid() ? organizationSubscriptionPlan.endDate.format(DATE_FORMAT) : undefined,
      suspendedDate: organizationSubscriptionPlan.suspendedDate?.isValid()
        ? organizationSubscriptionPlan.suspendedDate.toJSON()
        : undefined,
      deletedDate: organizationSubscriptionPlan.deletedDate?.isValid() ? organizationSubscriptionPlan.deletedDate.toJSON() : undefined,
      createdDate: organizationSubscriptionPlan.createdDate?.isValid() ? organizationSubscriptionPlan.createdDate.toJSON() : undefined,
      updatedDate: organizationSubscriptionPlan.updatedDate?.isValid() ? organizationSubscriptionPlan.updatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
      res.body.suspendedDate = res.body.suspendedDate ? dayjs(res.body.suspendedDate) : undefined;
      res.body.deletedDate = res.body.deletedDate ? dayjs(res.body.deletedDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((organizationSubscriptionPlan: IOrganizationSubscriptionPlan) => {
        organizationSubscriptionPlan.startDate = organizationSubscriptionPlan.startDate
          ? dayjs(organizationSubscriptionPlan.startDate)
          : undefined;
        organizationSubscriptionPlan.endDate = organizationSubscriptionPlan.endDate
          ? dayjs(organizationSubscriptionPlan.endDate)
          : undefined;
        organizationSubscriptionPlan.suspendedDate = organizationSubscriptionPlan.suspendedDate
          ? dayjs(organizationSubscriptionPlan.suspendedDate)
          : undefined;
        organizationSubscriptionPlan.deletedDate = organizationSubscriptionPlan.deletedDate
          ? dayjs(organizationSubscriptionPlan.deletedDate)
          : undefined;
        organizationSubscriptionPlan.createdDate = organizationSubscriptionPlan.createdDate
          ? dayjs(organizationSubscriptionPlan.createdDate)
          : undefined;
        organizationSubscriptionPlan.updatedDate = organizationSubscriptionPlan.updatedDate
          ? dayjs(organizationSubscriptionPlan.updatedDate)
          : undefined;
      });
    }
    return res;
  }
}
