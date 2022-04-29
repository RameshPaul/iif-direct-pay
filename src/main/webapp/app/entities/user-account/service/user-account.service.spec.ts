import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { UserAccountType } from 'app/entities/enumerations/user-account-type.model';
import { UserAccountUpiStatus } from 'app/entities/enumerations/user-account-upi-status.model';
import { UserAccountBankStatus } from 'app/entities/enumerations/user-account-bank-status.model';
import { UserAccountWalletType } from 'app/entities/enumerations/user-account-wallet-type.model';
import { UserAccountWalletStatus } from 'app/entities/enumerations/user-account-wallet-status.model';
import { IUserAccount, UserAccount } from '../user-account.model';

import { UserAccountService } from './user-account.service';

describe('UserAccount Service', () => {
  let service: UserAccountService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserAccount;
  let expectedResult: IUserAccount | IUserAccount[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserAccountService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      accountType: UserAccountType.UPI,
      upiAddress: 'AAAAAAA',
      mobileNumber: 0,
      upiActiveDate: currentDate,
      upiStatus: UserAccountUpiStatus.INACTIVE,
      upiSuspendedDate: currentDate,
      upiDeletedDate: currentDate,
      upiAutoDebitEnabled: false,
      bankName: 'AAAAAAA',
      bankAccountNumber: 0,
      bankIFSCCode: 'AAAAAAA',
      bankSWIFTCode: 'AAAAAAA',
      bankBranchAddress: 'AAAAAAA',
      bankStatus: UserAccountBankStatus.INACTIVE,
      bankActiveDate: currentDate,
      bankSuspendedDate: currentDate,
      bankDeletedDate: currentDate,
      bankAutoDebitEnabled: false,
      walletType: UserAccountWalletType.PAYTM,
      walletAddress: 'AAAAAAA',
      walletStatus: UserAccountWalletStatus.INACTIVE,
      wallterActiveDate: currentDate,
      walletSuspendedDate: currentDate,
      walletDeletedDate: currentDate,
      walletAutoDebitEnabled: false,
      createdDate: currentDate,
      updatedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          upiActiveDate: currentDate.format(DATE_FORMAT),
          upiSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          upiDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          bankActiveDate: currentDate.format(DATE_TIME_FORMAT),
          bankSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          bankDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          wallterActiveDate: currentDate.format(DATE_TIME_FORMAT),
          walletSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          walletDeletedDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a UserAccount', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          upiActiveDate: currentDate.format(DATE_FORMAT),
          upiSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          upiDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          bankActiveDate: currentDate.format(DATE_TIME_FORMAT),
          bankSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          bankDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          wallterActiveDate: currentDate.format(DATE_TIME_FORMAT),
          walletSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          walletDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          upiActiveDate: currentDate,
          upiSuspendedDate: currentDate,
          upiDeletedDate: currentDate,
          bankActiveDate: currentDate,
          bankSuspendedDate: currentDate,
          bankDeletedDate: currentDate,
          wallterActiveDate: currentDate,
          walletSuspendedDate: currentDate,
          walletDeletedDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new UserAccount()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserAccount', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          accountType: 'BBBBBB',
          upiAddress: 'BBBBBB',
          mobileNumber: 1,
          upiActiveDate: currentDate.format(DATE_FORMAT),
          upiStatus: 'BBBBBB',
          upiSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          upiDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          upiAutoDebitEnabled: true,
          bankName: 'BBBBBB',
          bankAccountNumber: 1,
          bankIFSCCode: 'BBBBBB',
          bankSWIFTCode: 'BBBBBB',
          bankBranchAddress: 'BBBBBB',
          bankStatus: 'BBBBBB',
          bankActiveDate: currentDate.format(DATE_TIME_FORMAT),
          bankSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          bankDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          bankAutoDebitEnabled: true,
          walletType: 'BBBBBB',
          walletAddress: 'BBBBBB',
          walletStatus: 'BBBBBB',
          wallterActiveDate: currentDate.format(DATE_TIME_FORMAT),
          walletSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          walletDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          walletAutoDebitEnabled: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          upiActiveDate: currentDate,
          upiSuspendedDate: currentDate,
          upiDeletedDate: currentDate,
          bankActiveDate: currentDate,
          bankSuspendedDate: currentDate,
          bankDeletedDate: currentDate,
          wallterActiveDate: currentDate,
          walletSuspendedDate: currentDate,
          walletDeletedDate: currentDate,
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

    it('should partial update a UserAccount', () => {
      const patchObject = Object.assign(
        {
          upiAddress: 'BBBBBB',
          upiSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          upiDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          upiAutoDebitEnabled: true,
          bankName: 'BBBBBB',
          bankBranchAddress: 'BBBBBB',
          bankStatus: 'BBBBBB',
          bankActiveDate: currentDate.format(DATE_TIME_FORMAT),
          walletAddress: 'BBBBBB',
          walletStatus: 'BBBBBB',
          walletDeletedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new UserAccount()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          upiActiveDate: currentDate,
          upiSuspendedDate: currentDate,
          upiDeletedDate: currentDate,
          bankActiveDate: currentDate,
          bankSuspendedDate: currentDate,
          bankDeletedDate: currentDate,
          wallterActiveDate: currentDate,
          walletSuspendedDate: currentDate,
          walletDeletedDate: currentDate,
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

    it('should return a list of UserAccount', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          accountType: 'BBBBBB',
          upiAddress: 'BBBBBB',
          mobileNumber: 1,
          upiActiveDate: currentDate.format(DATE_FORMAT),
          upiStatus: 'BBBBBB',
          upiSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          upiDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          upiAutoDebitEnabled: true,
          bankName: 'BBBBBB',
          bankAccountNumber: 1,
          bankIFSCCode: 'BBBBBB',
          bankSWIFTCode: 'BBBBBB',
          bankBranchAddress: 'BBBBBB',
          bankStatus: 'BBBBBB',
          bankActiveDate: currentDate.format(DATE_TIME_FORMAT),
          bankSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          bankDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          bankAutoDebitEnabled: true,
          walletType: 'BBBBBB',
          walletAddress: 'BBBBBB',
          walletStatus: 'BBBBBB',
          wallterActiveDate: currentDate.format(DATE_TIME_FORMAT),
          walletSuspendedDate: currentDate.format(DATE_TIME_FORMAT),
          walletDeletedDate: currentDate.format(DATE_TIME_FORMAT),
          walletAutoDebitEnabled: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          upiActiveDate: currentDate,
          upiSuspendedDate: currentDate,
          upiDeletedDate: currentDate,
          bankActiveDate: currentDate,
          bankSuspendedDate: currentDate,
          bankDeletedDate: currentDate,
          wallterActiveDate: currentDate,
          walletSuspendedDate: currentDate,
          walletDeletedDate: currentDate,
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

    it('should delete a UserAccount', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserAccountToCollectionIfMissing', () => {
      it('should add a UserAccount to an empty array', () => {
        const userAccount: IUserAccount = { id: 123 };
        expectedResult = service.addUserAccountToCollectionIfMissing([], userAccount);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userAccount);
      });

      it('should not add a UserAccount to an array that contains it', () => {
        const userAccount: IUserAccount = { id: 123 };
        const userAccountCollection: IUserAccount[] = [
          {
            ...userAccount,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserAccountToCollectionIfMissing(userAccountCollection, userAccount);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserAccount to an array that doesn't contain it", () => {
        const userAccount: IUserAccount = { id: 123 };
        const userAccountCollection: IUserAccount[] = [{ id: 456 }];
        expectedResult = service.addUserAccountToCollectionIfMissing(userAccountCollection, userAccount);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userAccount);
      });

      it('should add only unique UserAccount to an array', () => {
        const userAccountArray: IUserAccount[] = [{ id: 123 }, { id: 456 }, { id: 89025 }];
        const userAccountCollection: IUserAccount[] = [{ id: 123 }];
        expectedResult = service.addUserAccountToCollectionIfMissing(userAccountCollection, ...userAccountArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userAccount: IUserAccount = { id: 123 };
        const userAccount2: IUserAccount = { id: 456 };
        expectedResult = service.addUserAccountToCollectionIfMissing([], userAccount, userAccount2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userAccount);
        expect(expectedResult).toContain(userAccount2);
      });

      it('should accept null and undefined values', () => {
        const userAccount: IUserAccount = { id: 123 };
        expectedResult = service.addUserAccountToCollectionIfMissing([], null, userAccount, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userAccount);
      });

      it('should return initial array if no UserAccount is added', () => {
        const userAccountCollection: IUserAccount[] = [{ id: 123 }];
        expectedResult = service.addUserAccountToCollectionIfMissing(userAccountCollection, undefined, null);
        expect(expectedResult).toEqual(userAccountCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
