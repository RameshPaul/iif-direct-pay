import dayjs from 'dayjs/esm';
import { IPatron } from 'app/entities/patron/patron.model';
import { IUser } from 'app/entities/user/user.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { IUserAccount } from 'app/entities/user-account/user-account.model';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';
import { UserAccountType } from 'app/entities/enumerations/user-account-type.model';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';

export interface IPayment {
  id?: number;
  recurringPeriod?: dayjs.Dayjs | null;
  amount?: number | null;
  transactionId?: string | null;
  paymentType?: PaymentType | null;
  paymentSource?: UserAccountType | null;
  paymentStatus?: PaymentStatus | null;
  paymentStatusDetails?: string | null;
  paymentStartDateTime?: dayjs.Dayjs | null;
  paymentCompleteDateTime?: dayjs.Dayjs | null;
  paymentFailureDateTime?: dayjs.Dayjs | null;
  patronComment?: string | null;
  isAutoPay?: boolean | null;
  paymentDestinationSource?: UserAccountType | null;
  paymentReceivedDateTIme?: dayjs.Dayjs | null;
  paymentReceivedStatus?: PaymentStatus | null;
  paymentReceivedDetails?: string | null;
  paymentRefundedDateTime?: dayjs.Dayjs | null;
  userComment?: string | null;
  flaggedDateTime?: dayjs.Dayjs | null;
  flagDetails?: string | null;
  flaggedEmailId?: string | null;
  flaggedAmount?: number | null;
  flagCreatedDateTime?: dayjs.Dayjs | null;
  isRecurringPayment?: boolean | null;
  transactionDetails?: string | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  patronId?: IPatron | null;
  patronUserId?: IUser | null;
  patronUserOrgId?: IOrganization | null;
  receipientId?: IReceipient | null;
  receipientUserId?: IUser | null;
  receipientUserOrgId?: IOrganization | null;
  paymentSourceAccountId?: IUserAccount | null;
  paymentDestinationAccountId?: IUserAccount | null;
  flaggedUserId?: IUser | null;
  flaggedUserOrgId?: IOrganization | null;
  flagClearedUserId?: IUser | null;
  flagClearedUserOrgId?: IOrganization | null;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public recurringPeriod?: dayjs.Dayjs | null,
    public amount?: number | null,
    public transactionId?: string | null,
    public paymentType?: PaymentType | null,
    public paymentSource?: UserAccountType | null,
    public paymentStatus?: PaymentStatus | null,
    public paymentStatusDetails?: string | null,
    public paymentStartDateTime?: dayjs.Dayjs | null,
    public paymentCompleteDateTime?: dayjs.Dayjs | null,
    public paymentFailureDateTime?: dayjs.Dayjs | null,
    public patronComment?: string | null,
    public isAutoPay?: boolean | null,
    public paymentDestinationSource?: UserAccountType | null,
    public paymentReceivedDateTIme?: dayjs.Dayjs | null,
    public paymentReceivedStatus?: PaymentStatus | null,
    public paymentReceivedDetails?: string | null,
    public paymentRefundedDateTime?: dayjs.Dayjs | null,
    public userComment?: string | null,
    public flaggedDateTime?: dayjs.Dayjs | null,
    public flagDetails?: string | null,
    public flaggedEmailId?: string | null,
    public flaggedAmount?: number | null,
    public flagCreatedDateTime?: dayjs.Dayjs | null,
    public isRecurringPayment?: boolean | null,
    public transactionDetails?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public patronId?: IPatron | null,
    public patronUserId?: IUser | null,
    public patronUserOrgId?: IOrganization | null,
    public receipientId?: IReceipient | null,
    public receipientUserId?: IUser | null,
    public receipientUserOrgId?: IOrganization | null,
    public paymentSourceAccountId?: IUserAccount | null,
    public paymentDestinationAccountId?: IUserAccount | null,
    public flaggedUserId?: IUser | null,
    public flaggedUserOrgId?: IOrganization | null,
    public flagClearedUserId?: IUser | null,
    public flagClearedUserOrgId?: IOrganization | null
  ) {
    this.isAutoPay = this.isAutoPay ?? false;
    this.isRecurringPayment = this.isRecurringPayment ?? false;
  }
}

export function getPaymentIdentifier(payment: IPayment): number | undefined {
  return payment.id;
}
