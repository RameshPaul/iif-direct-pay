import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { OrganizationType } from 'app/entities/enumerations/organization-type.model';
import { OrganizationTaxCategory } from 'app/entities/enumerations/organization-tax-category.model';
import { OrganizationStatus } from 'app/entities/enumerations/organization-status.model';
import { IOrganization, Organization } from '../organization.model';

import { OrganizationService } from './organization.service';

describe('Organization Service', () => {
  let service: OrganizationService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrganization;
  let expectedResult: IOrganization | IOrganization[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      aliasName: 'AAAAAAA',
      email: 'AAAAAAA',
      website: 'AAAAAAA',
      phone: 0,
      mobile: 0,
      representativeName: 'AAAAAAA',
      representativeEmail: 'AAAAAAA',
      representativePhone: 0,
      registrationNumber: 'AAAAAAA',
      organizationType: OrganizationType.NON_PROFIT,
      organizationTypeOther: 'AAAAAAA',
      organizationTaxCategory: OrganizationTaxCategory.NO_TAX_EXEMPTION,
      organizationTaxCategoryOther: 'AAAAAAA',
      establishedDate: currentDate,
      totalEmployeesNumber: 0,
      joinDate: currentDate,
      subscriptionStartDate: currentDate,
      subscriptionEndDate: currentDate,
      status: OrganizationStatus.INACTIVE,
      isVerified: false,
      activatedDate: currentDate,
      createdDate: currentDate,
      updatedDate: currentDate,
      deletedDate: currentDate,
      suspendedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          establishedDate: currentDate.format(DATE_FORMAT),
          joinDate: currentDate.format(DATE_TIME_FORMAT),
          subscriptionStartDate: currentDate.format(DATE_FORMAT),
          subscriptionEndDate: currentDate.format(DATE_FORMAT),
          activatedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Organization', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          establishedDate: currentDate.format(DATE_FORMAT),
          joinDate: currentDate.format(DATE_TIME_FORMAT),
          subscriptionStartDate: currentDate.format(DATE_FORMAT),
          subscriptionEndDate: currentDate.format(DATE_FORMAT),
          activatedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          establishedDate: currentDate,
          joinDate: currentDate,
          subscriptionStartDate: currentDate,
          subscriptionEndDate: currentDate,
          activatedDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
          suspendedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Organization()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Organization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          aliasName: 'BBBBBB',
          email: 'BBBBBB',
          website: 'BBBBBB',
          phone: 1,
          mobile: 1,
          representativeName: 'BBBBBB',
          representativeEmail: 'BBBBBB',
          representativePhone: 1,
          registrationNumber: 'BBBBBB',
          organizationType: 'BBBBBB',
          organizationTypeOther: 'BBBBBB',
          organizationTaxCategory: 'BBBBBB',
          organizationTaxCategoryOther: 'BBBBBB',
          establishedDate: currentDate.format(DATE_FORMAT),
          totalEmployeesNumber: 1,
          joinDate: currentDate.format(DATE_TIME_FORMAT),
          subscriptionStartDate: currentDate.format(DATE_FORMAT),
          subscriptionEndDate: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          isVerified: true,
          activatedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          establishedDate: currentDate,
          joinDate: currentDate,
          subscriptionStartDate: currentDate,
          subscriptionEndDate: currentDate,
          activatedDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
          suspendedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Organization', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          aliasName: 'BBBBBB',
          website: 'BBBBBB',
          phone: 1,
          mobile: 1,
          representativeName: 'BBBBBB',
          representativeEmail: 'BBBBBB',
          organizationType: 'BBBBBB',
          totalEmployeesNumber: 1,
          joinDate: currentDate.format(DATE_TIME_FORMAT),
          activatedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Organization()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          establishedDate: currentDate,
          joinDate: currentDate,
          subscriptionStartDate: currentDate,
          subscriptionEndDate: currentDate,
          activatedDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
          suspendedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Organization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          aliasName: 'BBBBBB',
          email: 'BBBBBB',
          website: 'BBBBBB',
          phone: 1,
          mobile: 1,
          representativeName: 'BBBBBB',
          representativeEmail: 'BBBBBB',
          representativePhone: 1,
          registrationNumber: 'BBBBBB',
          organizationType: 'BBBBBB',
          organizationTypeOther: 'BBBBBB',
          organizationTaxCategory: 'BBBBBB',
          organizationTaxCategoryOther: 'BBBBBB',
          establishedDate: currentDate.format(DATE_FORMAT),
          totalEmployeesNumber: 1,
          joinDate: currentDate.format(DATE_TIME_FORMAT),
          subscriptionStartDate: currentDate.format(DATE_FORMAT),
          subscriptionEndDate: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          isVerified: true,
          activatedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          establishedDate: currentDate,
          joinDate: currentDate,
          subscriptionStartDate: currentDate,
          subscriptionEndDate: currentDate,
          activatedDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
          suspendedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Organization', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrganizationToCollectionIfMissing', () => {
      it('should add a Organization to an empty array', () => {
        const organization: IOrganization = { id: 123 };
        expectedResult = service.addOrganizationToCollectionIfMissing([], organization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organization);
      });

      it('should not add a Organization to an array that contains it', () => {
        const organization: IOrganization = { id: 123 };
        const organizationCollection: IOrganization[] = [
          {
            ...organization,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, organization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Organization to an array that doesn't contain it", () => {
        const organization: IOrganization = { id: 123 };
        const organizationCollection: IOrganization[] = [{ id: 456 }];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, organization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organization);
      });

      it('should add only unique Organization to an array', () => {
        const organizationArray: IOrganization[] = [{ id: 123 }, { id: 456 }, { id: 82285 }];
        const organizationCollection: IOrganization[] = [{ id: 123 }];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, ...organizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organization: IOrganization = { id: 123 };
        const organization2: IOrganization = { id: 456 };
        expectedResult = service.addOrganizationToCollectionIfMissing([], organization, organization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organization);
        expect(expectedResult).toContain(organization2);
      });

      it('should accept null and undefined values', () => {
        const organization: IOrganization = { id: 123 };
        expectedResult = service.addOrganizationToCollectionIfMissing([], null, organization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organization);
      });

      it('should return initial array if no Organization is added', () => {
        const organizationCollection: IOrganization[] = [{ id: 123 }];
        expectedResult = service.addOrganizationToCollectionIfMissing(organizationCollection, undefined, null);
        expect(expectedResult).toEqual(organizationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
