import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { UserDeviceType } from 'app/entities/enumerations/user-device-type.model';
import { UserDeviceStatus } from 'app/entities/enumerations/user-device-status.model';

export interface IUserDevice {
  id?: number;
  deviceName?: string;
  deviceId?: string;
  deviceToken?: string | null;
  deviceType?: UserDeviceType;
  deviceOS?: string;
  notificationEnabled?: boolean;
  lastActivityDetails?: string;
  lastActiveDate?: dayjs.Dayjs;
  lastActiveLocation?: string;
  appVersion?: string;
  forceLogin?: boolean;
  status?: UserDeviceStatus | null;
  loginDateTime?: dayjs.Dayjs | null;
  exitDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  userId?: IUser | null;
}

export class UserDevice implements IUserDevice {
  constructor(
    public id?: number,
    public deviceName?: string,
    public deviceId?: string,
    public deviceToken?: string | null,
    public deviceType?: UserDeviceType,
    public deviceOS?: string,
    public notificationEnabled?: boolean,
    public lastActivityDetails?: string,
    public lastActiveDate?: dayjs.Dayjs,
    public lastActiveLocation?: string,
    public appVersion?: string,
    public forceLogin?: boolean,
    public status?: UserDeviceStatus | null,
    public loginDateTime?: dayjs.Dayjs | null,
    public exitDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public userId?: IUser | null
  ) {
    this.notificationEnabled = this.notificationEnabled ?? false;
    this.forceLogin = this.forceLogin ?? false;
  }
}

export function getUserDeviceIdentifier(userDevice: IUserDevice): number | undefined {
  return userDevice.id;
}
