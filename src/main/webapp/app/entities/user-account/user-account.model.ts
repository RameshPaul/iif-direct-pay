import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { UserAccountType } from 'app/entities/enumerations/user-account-type.model';
import { UserAccountUpiStatus } from 'app/entities/enumerations/user-account-upi-status.model';
import { UserAccountBankStatus } from 'app/entities/enumerations/user-account-bank-status.model';
import { UserAccountWalletType } from 'app/entities/enumerations/user-account-wallet-type.model';
import { UserAccountWalletStatus } from 'app/entities/enumerations/user-account-wallet-status.model';

export interface IUserAccount {
  id?: number;
  accountType?: UserAccountType;
  upiAddress?: string | null;
  mobileNumber?: number | null;
  upiActiveDate?: dayjs.Dayjs | null;
  upiStatus?: UserAccountUpiStatus | null;
  upiSuspendedDate?: dayjs.Dayjs | null;
  upiDeletedDate?: dayjs.Dayjs | null;
  upiAutoDebitEnabled?: boolean | null;
  bankName?: string | null;
  bankAccountNumber?: number | null;
  bankIFSCCode?: string | null;
  bankSWIFTCode?: string | null;
  bankBranchAddress?: string | null;
  bankStatus?: UserAccountBankStatus | null;
  bankActiveDate?: dayjs.Dayjs | null;
  bankSuspendedDate?: dayjs.Dayjs | null;
  bankDeletedDate?: dayjs.Dayjs | null;
  bankAutoDebitEnabled?: boolean | null;
  walletType?: UserAccountWalletType | null;
  walletAddress?: string | null;
  walletStatus?: UserAccountWalletStatus | null;
  wallterActiveDate?: dayjs.Dayjs | null;
  walletSuspendedDate?: dayjs.Dayjs | null;
  walletDeletedDate?: dayjs.Dayjs | null;
  walletAutoDebitEnabled?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  userId?: IUser | null;
  organizationId?: IOrganization | null;
}

export class UserAccount implements IUserAccount {
  constructor(
    public id?: number,
    public accountType?: UserAccountType,
    public upiAddress?: string | null,
    public mobileNumber?: number | null,
    public upiActiveDate?: dayjs.Dayjs | null,
    public upiStatus?: UserAccountUpiStatus | null,
    public upiSuspendedDate?: dayjs.Dayjs | null,
    public upiDeletedDate?: dayjs.Dayjs | null,
    public upiAutoDebitEnabled?: boolean | null,
    public bankName?: string | null,
    public bankAccountNumber?: number | null,
    public bankIFSCCode?: string | null,
    public bankSWIFTCode?: string | null,
    public bankBranchAddress?: string | null,
    public bankStatus?: UserAccountBankStatus | null,
    public bankActiveDate?: dayjs.Dayjs | null,
    public bankSuspendedDate?: dayjs.Dayjs | null,
    public bankDeletedDate?: dayjs.Dayjs | null,
    public bankAutoDebitEnabled?: boolean | null,
    public walletType?: UserAccountWalletType | null,
    public walletAddress?: string | null,
    public walletStatus?: UserAccountWalletStatus | null,
    public wallterActiveDate?: dayjs.Dayjs | null,
    public walletSuspendedDate?: dayjs.Dayjs | null,
    public walletDeletedDate?: dayjs.Dayjs | null,
    public walletAutoDebitEnabled?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public userId?: IUser | null,
    public organizationId?: IOrganization | null
  ) {
    this.upiAutoDebitEnabled = this.upiAutoDebitEnabled ?? false;
    this.bankAutoDebitEnabled = this.bankAutoDebitEnabled ?? false;
    this.walletAutoDebitEnabled = this.walletAutoDebitEnabled ?? false;
  }
}

export function getUserAccountIdentifier(userAccount: IUserAccount): number | undefined {
  return userAccount.id;
}
