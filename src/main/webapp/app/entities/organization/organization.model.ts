import dayjs from 'dayjs/esm';
import { ISubscriptionPlan } from 'app/entities/subscription-plan/subscription-plan.model';
import { OrganizationType } from 'app/entities/enumerations/organization-type.model';
import { OrganizationTaxCategory } from 'app/entities/enumerations/organization-tax-category.model';
import { OrganizationStatus } from 'app/entities/enumerations/organization-status.model';

export interface IOrganization {
  id?: number;
  name?: string;
  aliasName?: string | null;
  email?: string;
  website?: string | null;
  phone?: number;
  mobile?: number | null;
  representativeName?: string | null;
  representativeEmail?: string | null;
  representativePhone?: number | null;
  registrationNumber?: string | null;
  organizationType?: OrganizationType | null;
  organizationTypeOther?: string | null;
  organizationTaxCategory?: OrganizationTaxCategory | null;
  organizationTaxCategoryOther?: string | null;
  establishedDate?: dayjs.Dayjs | null;
  totalEmployeesNumber?: number | null;
  joinDate?: dayjs.Dayjs | null;
  subscriptionStartDate?: dayjs.Dayjs | null;
  subscriptionEndDate?: dayjs.Dayjs | null;
  status?: OrganizationStatus;
  isVerified?: boolean | null;
  activatedDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  deletedDate?: dayjs.Dayjs | null;
  suspendedDate?: dayjs.Dayjs | null;
  subscriptionId?: ISubscriptionPlan | null;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public name?: string,
    public aliasName?: string | null,
    public email?: string,
    public website?: string | null,
    public phone?: number,
    public mobile?: number | null,
    public representativeName?: string | null,
    public representativeEmail?: string | null,
    public representativePhone?: number | null,
    public registrationNumber?: string | null,
    public organizationType?: OrganizationType | null,
    public organizationTypeOther?: string | null,
    public organizationTaxCategory?: OrganizationTaxCategory | null,
    public organizationTaxCategoryOther?: string | null,
    public establishedDate?: dayjs.Dayjs | null,
    public totalEmployeesNumber?: number | null,
    public joinDate?: dayjs.Dayjs | null,
    public subscriptionStartDate?: dayjs.Dayjs | null,
    public subscriptionEndDate?: dayjs.Dayjs | null,
    public status?: OrganizationStatus,
    public isVerified?: boolean | null,
    public activatedDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public deletedDate?: dayjs.Dayjs | null,
    public suspendedDate?: dayjs.Dayjs | null,
    public subscriptionId?: ISubscriptionPlan | null
  ) {
    this.isVerified = this.isVerified ?? false;
  }
}

export function getOrganizationIdentifier(organization: IOrganization): number | undefined {
  return organization.id;
}
