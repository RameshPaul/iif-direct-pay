import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { ReceipientPatronRecurringType } from 'app/entities/enumerations/receipient-patron-recurring-type.model';
import { ReceipientPatronStatus } from 'app/entities/enumerations/receipient-patron-status.model';

export interface IReceipient {
  id?: number;
  isRecurring?: boolean | null;
  recurringType?: ReceipientPatronRecurringType;
  recurringStartDate?: dayjs.Dayjs | null;
  recurringEndDate?: dayjs.Dayjs | null;
  enableReminder?: boolean | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  isAutoPay?: boolean | null;
  amountRequisite?: number | null;
  status?: ReceipientPatronStatus | null;
  isManagedByOrg?: boolean | null;
  approvedDateTime?: dayjs.Dayjs | null;
  rejectedDateTime?: dayjs.Dayjs | null;
  rejectReason?: string | null;
  onboardedDate?: dayjs.Dayjs | null;
  reccuringPauseDate?: dayjs.Dayjs | null;
  recurringResumeDate?: dayjs.Dayjs | null;
  recurringPauseReason?: string | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  deletedDate?: dayjs.Dayjs | null;
  userId?: IUser | null;
  organizationId?: IOrganization | null;
  managedOrganiztionId?: IOrganization | null;
  createdUserId?: IUser | null;
  createdUserOrgId?: IOrganization | null;
  approvedUserId?: IUser | null;
  approvedUserOrgId?: IOrganization | null;
  rejectedUserId?: IUser | null;
  rejectedUserOrgId?: IOrganization | null;
}

export class Receipient implements IReceipient {
  constructor(
    public id?: number,
    public isRecurring?: boolean | null,
    public recurringType?: ReceipientPatronRecurringType,
    public recurringStartDate?: dayjs.Dayjs | null,
    public recurringEndDate?: dayjs.Dayjs | null,
    public enableReminder?: boolean | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public isAutoPay?: boolean | null,
    public amountRequisite?: number | null,
    public status?: ReceipientPatronStatus | null,
    public isManagedByOrg?: boolean | null,
    public approvedDateTime?: dayjs.Dayjs | null,
    public rejectedDateTime?: dayjs.Dayjs | null,
    public rejectReason?: string | null,
    public onboardedDate?: dayjs.Dayjs | null,
    public reccuringPauseDate?: dayjs.Dayjs | null,
    public recurringResumeDate?: dayjs.Dayjs | null,
    public recurringPauseReason?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public deletedDate?: dayjs.Dayjs | null,
    public userId?: IUser | null,
    public organizationId?: IOrganization | null,
    public managedOrganiztionId?: IOrganization | null,
    public createdUserId?: IUser | null,
    public createdUserOrgId?: IOrganization | null,
    public approvedUserId?: IUser | null,
    public approvedUserOrgId?: IOrganization | null,
    public rejectedUserId?: IUser | null,
    public rejectedUserOrgId?: IOrganization | null
  ) {
    this.isRecurring = this.isRecurring ?? false;
    this.enableReminder = this.enableReminder ?? false;
    this.isAutoPay = this.isAutoPay ?? false;
    this.isManagedByOrg = this.isManagedByOrg ?? false;
  }
}

export function getReceipientIdentifier(receipient: IReceipient): number | undefined {
  return receipient.id;
}
