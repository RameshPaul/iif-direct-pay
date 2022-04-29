import dayjs from 'dayjs/esm';
import { SubscriptionType } from 'app/entities/enumerations/subscription-type.model';
import { SubscriptionStatus } from 'app/entities/enumerations/subscription-status.model';

export interface ISubscriptionPlan {
  id?: number;
  subscriptionName?: string;
  subscriptionTitle?: string;
  subscriptionType?: SubscriptionType;
  subscriptionPrice?: number;
  subscriptionQuantity?: number;
  subscriptionPeriod?: number;
  subscriptionTerms?: string | null;
  status?: SubscriptionStatus;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
}

export class SubscriptionPlan implements ISubscriptionPlan {
  constructor(
    public id?: number,
    public subscriptionName?: string,
    public subscriptionTitle?: string,
    public subscriptionType?: SubscriptionType,
    public subscriptionPrice?: number,
    public subscriptionQuantity?: number,
    public subscriptionPeriod?: number,
    public subscriptionTerms?: string | null,
    public status?: SubscriptionStatus,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null
  ) {}
}

export function getSubscriptionPlanIdentifier(subscriptionPlan: ISubscriptionPlan): number | undefined {
  return subscriptionPlan.id;
}
