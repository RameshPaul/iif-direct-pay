import dayjs from 'dayjs/esm';
import { IOrganization } from 'app/entities/organization/organization.model';
import { IUser } from 'app/entities/user/user.model';
import { UserOrganizationStatus } from 'app/entities/enumerations/user-organization-status.model';

export interface IUserOrganization {
  id?: number;
  joiningDate?: dayjs.Dayjs | null;
  exitDate?: dayjs.Dayjs | null;
  status?: UserOrganizationStatus | null;
  suspendedDate?: dayjs.Dayjs | null;
  deletedDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  organiationId?: IOrganization | null;
  userId?: IUser | null;
}

export class UserOrganization implements IUserOrganization {
  constructor(
    public id?: number,
    public joiningDate?: dayjs.Dayjs | null,
    public exitDate?: dayjs.Dayjs | null,
    public status?: UserOrganizationStatus | null,
    public suspendedDate?: dayjs.Dayjs | null,
    public deletedDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public organiationId?: IOrganization | null,
    public userId?: IUser | null
  ) {}
}

export function getUserOrganizationIdentifier(userOrganization: IUserOrganization): number | undefined {
  return userOrganization.id;
}
