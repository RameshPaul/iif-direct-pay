import dayjs from 'dayjs/esm';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { IUser } from 'app/entities/user/user.model';
import { ReceipientRecurringStatus } from 'app/entities/enumerations/receipient-recurring-status.model';

export interface IReceipientRecurring {
  id?: number;
  recurringPeriod?: number | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  amountRequisite?: number | null;
  amountPatronCommited?: number | null;
  amountReceived?: number | null;
  amountBalance?: number | null;
  totalPatrons?: number | null;
  detailsText?: string | null;
  status?: ReceipientRecurringStatus | null;
  pauseDate?: dayjs.Dayjs | null;
  resumeDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  receipientId?: IReceipient | null;
  userId?: IUser | null;
}

export class ReceipientRecurring implements IReceipientRecurring {
  constructor(
    public id?: number,
    public recurringPeriod?: number | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public amountRequisite?: number | null,
    public amountPatronCommited?: number | null,
    public amountReceived?: number | null,
    public amountBalance?: number | null,
    public totalPatrons?: number | null,
    public detailsText?: string | null,
    public status?: ReceipientRecurringStatus | null,
    public pauseDate?: dayjs.Dayjs | null,
    public resumeDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public receipientId?: IReceipient | null,
    public userId?: IUser | null
  ) {}
}

export function getReceipientRecurringIdentifier(receipientRecurring: IReceipientRecurring): number | undefined {
  return receipientRecurring.id;
}
