import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganization, getOrganizationIdentifier } from '../organization.model';

export type EntityResponseType = HttpResponse<IOrganization>;
export type EntityArrayResponseType = HttpResponse<IOrganization[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organization: IOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organization);
    return this.http
      .post<IOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(organization: IOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organization);
    return this.http
      .put<IOrganization>(`${this.resourceUrl}/${getOrganizationIdentifier(organization) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(organization: IOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organization);
    return this.http
      .patch<IOrganization>(`${this.resourceUrl}/${getOrganizationIdentifier(organization) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrganizationToCollectionIfMissing(
    organizationCollection: IOrganization[],
    ...organizationsToCheck: (IOrganization | null | undefined)[]
  ): IOrganization[] {
    const organizations: IOrganization[] = organizationsToCheck.filter(isPresent);
    if (organizations.length > 0) {
      const organizationCollectionIdentifiers = organizationCollection.map(
        organizationItem => getOrganizationIdentifier(organizationItem)!
      );
      const organizationsToAdd = organizations.filter(organizationItem => {
        const organizationIdentifier = getOrganizationIdentifier(organizationItem);
        if (organizationIdentifier == null || organizationCollectionIdentifiers.includes(organizationIdentifier)) {
          return false;
        }
        organizationCollectionIdentifiers.push(organizationIdentifier);
        return true;
      });
      return [...organizationsToAdd, ...organizationCollection];
    }
    return organizationCollection;
  }

  protected convertDateFromClient(organization: IOrganization): IOrganization {
    return Object.assign({}, organization, {
      establishedDate: organization.establishedDate?.isValid() ? organization.establishedDate.format(DATE_FORMAT) : undefined,
      joinDate: organization.joinDate?.isValid() ? organization.joinDate.toJSON() : undefined,
      subscriptionStartDate: organization.subscriptionStartDate?.isValid()
        ? organization.subscriptionStartDate.format(DATE_FORMAT)
        : undefined,
      subscriptionEndDate: organization.subscriptionEndDate?.isValid() ? organization.subscriptionEndDate.format(DATE_FORMAT) : undefined,
      activatedDate: organization.activatedDate?.isValid() ? organization.activatedDate.toJSON() : undefined,
      createdDate: organization.createdDate?.isValid() ? organization.createdDate.toJSON() : undefined,
      updatedDate: organization.updatedDate?.isValid() ? organization.updatedDate.toJSON() : undefined,
      deletedDate: organization.deletedDate?.isValid() ? organization.deletedDate.toJSON() : undefined,
      suspendedDate: organization.suspendedDate?.isValid() ? organization.suspendedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.establishedDate = res.body.establishedDate ? dayjs(res.body.establishedDate) : undefined;
      res.body.joinDate = res.body.joinDate ? dayjs(res.body.joinDate) : undefined;
      res.body.subscriptionStartDate = res.body.subscriptionStartDate ? dayjs(res.body.subscriptionStartDate) : undefined;
      res.body.subscriptionEndDate = res.body.subscriptionEndDate ? dayjs(res.body.subscriptionEndDate) : undefined;
      res.body.activatedDate = res.body.activatedDate ? dayjs(res.body.activatedDate) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? dayjs(res.body.updatedDate) : undefined;
      res.body.deletedDate = res.body.deletedDate ? dayjs(res.body.deletedDate) : undefined;
      res.body.suspendedDate = res.body.suspendedDate ? dayjs(res.body.suspendedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((organization: IOrganization) => {
        organization.establishedDate = organization.establishedDate ? dayjs(organization.establishedDate) : undefined;
        organization.joinDate = organization.joinDate ? dayjs(organization.joinDate) : undefined;
        organization.subscriptionStartDate = organization.subscriptionStartDate ? dayjs(organization.subscriptionStartDate) : undefined;
        organization.subscriptionEndDate = organization.subscriptionEndDate ? dayjs(organization.subscriptionEndDate) : undefined;
        organization.activatedDate = organization.activatedDate ? dayjs(organization.activatedDate) : undefined;
        organization.createdDate = organization.createdDate ? dayjs(organization.createdDate) : undefined;
        organization.updatedDate = organization.updatedDate ? dayjs(organization.updatedDate) : undefined;
        organization.deletedDate = organization.deletedDate ? dayjs(organization.deletedDate) : undefined;
        organization.suspendedDate = organization.suspendedDate ? dayjs(organization.suspendedDate) : undefined;
      });
    }
    return res;
  }
}
