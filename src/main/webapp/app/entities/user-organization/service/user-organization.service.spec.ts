import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { UserOrganizationStatus } from 'app/entities/enumerations/user-organization-status.model';
import { IUserOrganization, UserOrganization } from '../user-organization.model';

import { UserOrganizationService } from './user-organization.service';

describe('UserOrganization Service', () => {
  let service: UserOrganizationService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserOrganization;
  let expectedResult: IUserOrganization | IUserOrganization[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      joiningDate: currentDate,
      exitDate: currentDate,
      status: UserOrganizationStatus.INACTIVE,
      suspendedDate: currentDate,
      deletedDate: currentDate,
      createdDate: currentDate,
      updatedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          joiningDate: currentDate.format(DATE_FORMAT),
          exitDate: currentDate.format(DATE_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a UserOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          joiningDate: currentDate.format(DATE_FORMAT),
          exitDate: currentDate.format(DATE_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          joiningDate: currentDate,
          exitDate: currentDate,
          suspendedDate: currentDate,
          deletedDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new UserOrganization()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          joiningDate: currentDate.format(DATE_FORMAT),
          exitDate: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          joiningDate: currentDate,
          exitDate: currentDate,
          suspendedDate: currentDate,
          deletedDate: currentDate,
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

    it('should partial update a UserOrganization', () => {
      const patchObject = Object.assign(
        {
          exitDate: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new UserOrganization()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          joiningDate: currentDate,
          exitDate: currentDate,
          suspendedDate: currentDate,
          deletedDate: currentDate,
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

    it('should return a list of UserOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          joiningDate: currentDate.format(DATE_FORMAT),
          exitDate: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          joiningDate: currentDate,
          exitDate: currentDate,
          suspendedDate: currentDate,
          deletedDate: currentDate,
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

    it('should delete a UserOrganization', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserOrganizationToCollectionIfMissing', () => {
      it('should add a UserOrganization to an empty array', () => {
        const userOrganization: IUserOrganization = { id: 123 };
        expectedResult = service.addUserOrganizationToCollectionIfMissing([], userOrganization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userOrganization);
      });

      it('should not add a UserOrganization to an array that contains it', () => {
        const userOrganization: IUserOrganization = { id: 123 };
        const userOrganizationCollection: IUserOrganization[] = [
          {
            ...userOrganization,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserOrganizationToCollectionIfMissing(userOrganizationCollection, userOrganization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserOrganization to an array that doesn't contain it", () => {
        const userOrganization: IUserOrganization = { id: 123 };
        const userOrganizationCollection: IUserOrganization[] = [{ id: 456 }];
        expectedResult = service.addUserOrganizationToCollectionIfMissing(userOrganizationCollection, userOrganization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userOrganization);
      });

      it('should add only unique UserOrganization to an array', () => {
        const userOrganizationArray: IUserOrganization[] = [{ id: 123 }, { id: 456 }, { id: 56041 }];
        const userOrganizationCollection: IUserOrganization[] = [{ id: 123 }];
        expectedResult = service.addUserOrganizationToCollectionIfMissing(userOrganizationCollection, ...userOrganizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userOrganization: IUserOrganization = { id: 123 };
        const userOrganization2: IUserOrganization = { id: 456 };
        expectedResult = service.addUserOrganizationToCollectionIfMissing([], userOrganization, userOrganization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userOrganization);
        expect(expectedResult).toContain(userOrganization2);
      });

      it('should accept null and undefined values', () => {
        const userOrganization: IUserOrganization = { id: 123 };
        expectedResult = service.addUserOrganizationToCollectionIfMissing([], null, userOrganization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userOrganization);
      });

      it('should return initial array if no UserOrganization is added', () => {
        const userOrganizationCollection: IUserOrganization[] = [{ id: 123 }];
        expectedResult = service.addUserOrganizationToCollectionIfMissing(userOrganizationCollection, undefined, null);
        expect(expectedResult).toEqual(userOrganizationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
