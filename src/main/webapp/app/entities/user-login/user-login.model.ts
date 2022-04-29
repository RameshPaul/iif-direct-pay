import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { UserLoginType } from 'app/entities/enumerations/user-login-type.model';

export interface IUserLogin {
  id?: number;
  loginType?: UserLoginType | null;
  emailOTP?: string | null;
  phoneOTP?: string | null;
  emailOTPExpiryDate?: dayjs.Dayjs | null;
  phoneOTPExpiryDate?: dayjs.Dayjs | null;
  locationIP?: string;
  locationDetails?: string;
  latlog?: string;
  browser?: string;
  device?: string;
  loginDateTime?: dayjs.Dayjs | null;
  loginToken?: string | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  userId?: IUser | null;
}

export class UserLogin implements IUserLogin {
  constructor(
    public id?: number,
    public loginType?: UserLoginType | null,
    public emailOTP?: string | null,
    public phoneOTP?: string | null,
    public emailOTPExpiryDate?: dayjs.Dayjs | null,
    public phoneOTPExpiryDate?: dayjs.Dayjs | null,
    public locationIP?: string,
    public locationDetails?: string,
    public latlog?: string,
    public browser?: string,
    public device?: string,
    public loginDateTime?: dayjs.Dayjs | null,
    public loginToken?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public userId?: IUser | null
  ) {}
}

export function getUserLoginIdentifier(userLogin: IUserLogin): number | undefined {
  return userLogin.id;
}
