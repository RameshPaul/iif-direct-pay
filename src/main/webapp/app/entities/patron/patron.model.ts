import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { ReceipientPatronRecurringType } from 'app/entities/enumerations/receipient-patron-recurring-type.model';
import { ReceipientPatronStatus } from 'app/entities/enumerations/receipient-patron-status.model';

export interface IPatron {
  id?: number;
  isRecurring?: boolean | null;
  recurringType?: ReceipientPatronRecurringType;
  recurringPeriod?: dayjs.Dayjs | null;
  enableReminder?: boolean | null;
  isAutoPay?: boolean | null;
  amountReceipientRequisite?: number | null;
  amountPatronPledge?: number | null;
  amountPatronActual?: number | null;
  status?: ReceipientPatronStatus | null;
  commitedStartDate?: dayjs.Dayjs | null;
  commitedEndDate?: dayjs.Dayjs | null;
  actualStartDate?: dayjs.Dayjs | null;
  actualEndDate?: dayjs.Dayjs | null;
  reccuringPauseDate?: dayjs.Dayjs | null;
  recurringResumeDate?: dayjs.Dayjs | null;
  recurringPauseReason?: string | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  deletedDate?: dayjs.Dayjs | null;
  patronUserId?: IUser | null;
  patronUserOrgId?: IOrganization | null;
  receipientId?: IReceipient | null;
  receipientUserId?: IUser | null;
  receipientUserOrgId?: IOrganization | null;
}

export class Patron implements IPatron {
  constructor(
    public id?: number,
    public isRecurring?: boolean | null,
    public recurringType?: ReceipientPatronRecurringType,
    public recurringPeriod?: dayjs.Dayjs | null,
    public enableReminder?: boolean | null,
    public isAutoPay?: boolean | null,
    public amountReceipientRequisite?: number | null,
    public amountPatronPledge?: number | null,
    public amountPatronActual?: number | null,
    public status?: ReceipientPatronStatus | null,
    public commitedStartDate?: dayjs.Dayjs | null,
    public commitedEndDate?: dayjs.Dayjs | null,
    public actualStartDate?: dayjs.Dayjs | null,
    public actualEndDate?: dayjs.Dayjs | null,
    public reccuringPauseDate?: dayjs.Dayjs | null,
    public recurringResumeDate?: dayjs.Dayjs | null,
    public recurringPauseReason?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public deletedDate?: dayjs.Dayjs | null,
    public patronUserId?: IUser | null,
    public patronUserOrgId?: IOrganization | null,
    public receipientId?: IReceipient | null,
    public receipientUserId?: IUser | null,
    public receipientUserOrgId?: IOrganization | null
  ) {
    this.isRecurring = this.isRecurring ?? false;
    this.enableReminder = this.enableReminder ?? false;
    this.isAutoPay = this.isAutoPay ?? false;
  }
}

export function getPatronIdentifier(patron: IPatron): number | undefined {
  return patron.id;
}
