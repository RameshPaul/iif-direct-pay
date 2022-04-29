import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { UserDeviceType } from 'app/entities/enumerations/user-device-type.model';
import { UserDeviceStatus } from 'app/entities/enumerations/user-device-status.model';
import { IUserDevice, UserDevice } from '../user-device.model';

import { UserDeviceService } from './user-device.service';

describe('UserDevice Service', () => {
  let service: UserDeviceService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserDevice;
  let expectedResult: IUserDevice | IUserDevice[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserDeviceService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      deviceName: 'AAAAAAA',
      deviceId: 'AAAAAAA',
      deviceToken: 'AAAAAAA',
      deviceType: UserDeviceType.MOBILE_PHONE,
      deviceOS: 'AAAAAAA',
      notificationEnabled: false,
      lastActivityDetails: 'AAAAAAA',
      lastActiveDate: currentDate,
      lastActiveLocation: 'AAAAAAA',
      appVersion: 'AAAAAAA',
      forceLogin: false,
      status: UserDeviceStatus.INACTIVE,
      loginDateTime: currentDate,
      exitDate: currentDate,
      createdDate: currentDate,
      updatedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastActiveDate: currentDate.format(DATE_FORMAT),
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          exitDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a UserDevice', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          lastActiveDate: currentDate.format(DATE_FORMAT),
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          exitDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastActiveDate: currentDate,
          loginDateTime: currentDate,
          exitDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new UserDevice()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserDevice', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          deviceName: 'BBBBBB',
          deviceId: 'BBBBBB',
          deviceToken: 'BBBBBB',
          deviceType: 'BBBBBB',
          deviceOS: 'BBBBBB',
          notificationEnabled: true,
          lastActivityDetails: 'BBBBBB',
          lastActiveDate: currentDate.format(DATE_FORMAT),
          lastActiveLocation: 'BBBBBB',
          appVersion: 'BBBBBB',
          forceLogin: true,
          status: 'BBBBBB',
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          exitDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastActiveDate: currentDate,
          loginDateTime: currentDate,
          exitDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserDevice', () => {
      const patchObject = Object.assign(
        {
          deviceName: 'BBBBBB',
          notificationEnabled: true,
          lastActivityDetails: 'BBBBBB',
          lastActiveLocation: 'BBBBBB',
          appVersion: 'BBBBBB',
          forceLogin: true,
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          exitDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new UserDevice()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastActiveDate: currentDate,
          loginDateTime: currentDate,
          exitDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserDevice', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          deviceName: 'BBBBBB',
          deviceId: 'BBBBBB',
          deviceToken: 'BBBBBB',
          deviceType: 'BBBBBB',
          deviceOS: 'BBBBBB',
          notificationEnabled: true,
          lastActivityDetails: 'BBBBBB',
          lastActiveDate: currentDate.format(DATE_FORMAT),
          lastActiveLocation: 'BBBBBB',
          appVersion: 'BBBBBB',
          forceLogin: true,
          status: 'BBBBBB',
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          exitDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastActiveDate: currentDate,
          loginDateTime: currentDate,
          exitDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a UserDevice', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserDeviceToCollectionIfMissing', () => {
      it('should add a UserDevice to an empty array', () => {
        const userDevice: IUserDevice = { id: 123 };
        expectedResult = service.addUserDeviceToCollectionIfMissing([], userDevice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userDevice);
      });

      it('should not add a UserDevice to an array that contains it', () => {
        const userDevice: IUserDevice = { id: 123 };
        const userDeviceCollection: IUserDevice[] = [
          {
            ...userDevice,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserDeviceToCollectionIfMissing(userDeviceCollection, userDevice);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserDevice to an array that doesn't contain it", () => {
        const userDevice: IUserDevice = { id: 123 };
        const userDeviceCollection: IUserDevice[] = [{ id: 456 }];
        expectedResult = service.addUserDeviceToCollectionIfMissing(userDeviceCollection, userDevice);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userDevice);
      });

      it('should add only unique UserDevice to an array', () => {
        const userDeviceArray: IUserDevice[] = [{ id: 123 }, { id: 456 }, { id: 95427 }];
        const userDeviceCollection: IUserDevice[] = [{ id: 123 }];
        expectedResult = service.addUserDeviceToCollectionIfMissing(userDeviceCollection, ...userDeviceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userDevice: IUserDevice = { id: 123 };
        const userDevice2: IUserDevice = { id: 456 };
        expectedResult = service.addUserDeviceToCollectionIfMissing([], userDevice, userDevice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userDevice);
        expect(expectedResult).toContain(userDevice2);
      });

      it('should accept null and undefined values', () => {
        const userDevice: IUserDevice = { id: 123 };
        expectedResult = service.addUserDeviceToCollectionIfMissing([], null, userDevice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userDevice);
      });

      it('should return initial array if no UserDevice is added', () => {
        const userDeviceCollection: IUserDevice[] = [{ id: 123 }];
        expectedResult = service.addUserDeviceToCollectionIfMissing(userDeviceCollection, undefined, null);
        expect(expectedResult).toEqual(userDeviceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
