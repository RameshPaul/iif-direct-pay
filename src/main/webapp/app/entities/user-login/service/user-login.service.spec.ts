import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { UserLoginType } from 'app/entities/enumerations/user-login-type.model';
import { IUserLogin, UserLogin } from '../user-login.model';

import { UserLoginService } from './user-login.service';

describe('UserLogin Service', () => {
  let service: UserLoginService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserLogin;
  let expectedResult: IUserLogin | IUserLogin[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserLoginService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      loginType: UserLoginType.EMAIL,
      emailOTP: 'AAAAAAA',
      phoneOTP: 'AAAAAAA',
      emailOTPExpiryDate: currentDate,
      phoneOTPExpiryDate: currentDate,
      locationIP: 'AAAAAAA',
      locationDetails: 'AAAAAAA',
      latlog: 'AAAAAAA',
      browser: 'AAAAAAA',
      device: 'AAAAAAA',
      loginDateTime: currentDate,
      loginToken: 'AAAAAAA',
      createdDate: currentDate,
      updatedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          emailOTPExpiryDate: currentDate.format(DATE_FORMAT),
          phoneOTPExpiryDate: currentDate.format(DATE_FORMAT),
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a UserLogin', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          emailOTPExpiryDate: currentDate.format(DATE_FORMAT),
          phoneOTPExpiryDate: currentDate.format(DATE_FORMAT),
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          emailOTPExpiryDate: currentDate,
          phoneOTPExpiryDate: currentDate,
          loginDateTime: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new UserLogin()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserLogin', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          loginType: 'BBBBBB',
          emailOTP: 'BBBBBB',
          phoneOTP: 'BBBBBB',
          emailOTPExpiryDate: currentDate.format(DATE_FORMAT),
          phoneOTPExpiryDate: currentDate.format(DATE_FORMAT),
          locationIP: 'BBBBBB',
          locationDetails: 'BBBBBB',
          latlog: 'BBBBBB',
          browser: 'BBBBBB',
          device: 'BBBBBB',
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          loginToken: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          emailOTPExpiryDate: currentDate,
          phoneOTPExpiryDate: currentDate,
          loginDateTime: currentDate,
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

    it('should partial update a UserLogin', () => {
      const patchObject = Object.assign(
        {
          loginType: 'BBBBBB',
          emailOTPExpiryDate: currentDate.format(DATE_FORMAT),
          locationIP: 'BBBBBB',
          locationDetails: 'BBBBBB',
        },
        new UserLogin()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          emailOTPExpiryDate: currentDate,
          phoneOTPExpiryDate: currentDate,
          loginDateTime: currentDate,
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

    it('should return a list of UserLogin', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          loginType: 'BBBBBB',
          emailOTP: 'BBBBBB',
          phoneOTP: 'BBBBBB',
          emailOTPExpiryDate: currentDate.format(DATE_FORMAT),
          phoneOTPExpiryDate: currentDate.format(DATE_FORMAT),
          locationIP: 'BBBBBB',
          locationDetails: 'BBBBBB',
          latlog: 'BBBBBB',
          browser: 'BBBBBB',
          device: 'BBBBBB',
          loginDateTime: currentDate.format(DATE_TIME_FORMAT),
          loginToken: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          emailOTPExpiryDate: currentDate,
          phoneOTPExpiryDate: currentDate,
          loginDateTime: currentDate,
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

    it('should delete a UserLogin', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserLoginToCollectionIfMissing', () => {
      it('should add a UserLogin to an empty array', () => {
        const userLogin: IUserLogin = { id: 123 };
        expectedResult = service.addUserLoginToCollectionIfMissing([], userLogin);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userLogin);
      });

      it('should not add a UserLogin to an array that contains it', () => {
        const userLogin: IUserLogin = { id: 123 };
        const userLoginCollection: IUserLogin[] = [
          {
            ...userLogin,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserLoginToCollectionIfMissing(userLoginCollection, userLogin);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserLogin to an array that doesn't contain it", () => {
        const userLogin: IUserLogin = { id: 123 };
        const userLoginCollection: IUserLogin[] = [{ id: 456 }];
        expectedResult = service.addUserLoginToCollectionIfMissing(userLoginCollection, userLogin);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userLogin);
      });

      it('should add only unique UserLogin to an array', () => {
        const userLoginArray: IUserLogin[] = [{ id: 123 }, { id: 456 }, { id: 52423 }];
        const userLoginCollection: IUserLogin[] = [{ id: 123 }];
        expectedResult = service.addUserLoginToCollectionIfMissing(userLoginCollection, ...userLoginArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userLogin: IUserLogin = { id: 123 };
        const userLogin2: IUserLogin = { id: 456 };
        expectedResult = service.addUserLoginToCollectionIfMissing([], userLogin, userLogin2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userLogin);
        expect(expectedResult).toContain(userLogin2);
      });

      it('should accept null and undefined values', () => {
        const userLogin: IUserLogin = { id: 123 };
        expectedResult = service.addUserLoginToCollectionIfMissing([], null, userLogin, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userLogin);
      });

      it('should return initial array if no UserLogin is added', () => {
        const userLoginCollection: IUserLogin[] = [{ id: 123 }];
        expectedResult = service.addUserLoginToCollectionIfMissing(userLoginCollection, undefined, null);
        expect(expectedResult).toEqual(userLoginCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
