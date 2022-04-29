import dayjs from 'dayjs/esm';
import { IOrganization } from 'app/entities/organization/organization.model';
import { ISubscriptionPlan } from 'app/entities/subscription-plan/subscription-plan.model';
import { SubscriptionType } from 'app/entities/enumerations/subscription-type.model';
import { SubscriptionStatus } from 'app/entities/enumerations/subscription-status.model';

export interface IOrganizationSubscriptionPlan {
  id?: number;
  subscriptionName?: string;
  subscriptionTitle?: string;
  subscriptionType?: SubscriptionType;
  subscriptionPrice?: number;
  subscriptionQuantity?: number;
  subscriptionPeriod?: string;
  subscriptionTerms?: string | null;
  couponCode?: string | null;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  suspendedDate?: dayjs.Dayjs | null;
  deletedDate?: dayjs.Dayjs | null;
  status?: SubscriptionStatus;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  organizationId?: IOrganization | null;
  subscriptionId?: ISubscriptionPlan | null;
}

export class OrganizationSubscriptionPlan implements IOrganizationSubscriptionPlan {
  constructor(
    public id?: number,
    public subscriptionName?: string,
    public subscriptionTitle?: string,
    public subscriptionType?: SubscriptionType,
    public subscriptionPrice?: number,
    public subscriptionQuantity?: number,
    public subscriptionPeriod?: string,
    public subscriptionTerms?: string | null,
    public couponCode?: string | null,
    public startDate?: dayjs.Dayjs,
    public endDate?: dayjs.Dayjs,
    public suspendedDate?: dayjs.Dayjs | null,
    public deletedDate?: dayjs.Dayjs | null,
    public status?: SubscriptionStatus,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public organizationId?: IOrganization | null,
    public subscriptionId?: ISubscriptionPlan | null
  ) {}
}

export function getOrganizationSubscriptionPlanIdentifier(organizationSubscriptionPlan: IOrganizationSubscriptionPlan): number | undefined {
  return organizationSubscriptionPlan.id;
}
