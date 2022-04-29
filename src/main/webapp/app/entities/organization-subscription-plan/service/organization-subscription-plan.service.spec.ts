import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { SubscriptionType } from 'app/entities/enumerations/subscription-type.model';
import { SubscriptionStatus } from 'app/entities/enumerations/subscription-status.model';
import { IOrganizationSubscriptionPlan, OrganizationSubscriptionPlan } from '../organization-subscription-plan.model';

import { OrganizationSubscriptionPlanService } from './organization-subscription-plan.service';

describe('OrganizationSubscriptionPlan Service', () => {
  let service: OrganizationSubscriptionPlanService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrganizationSubscriptionPlan;
  let expectedResult: IOrganizationSubscriptionPlan | IOrganizationSubscriptionPlan[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationSubscriptionPlanService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      subscriptionName: 'AAAAAAA',
      subscriptionTitle: 'AAAAAAA',
      subscriptionType: SubscriptionType.FREE,
      subscriptionPrice: 0,
      subscriptionQuantity: 0,
      subscriptionPeriod: 'AAAAAAA',
      subscriptionTerms: 'AAAAAAA',
      couponCode: 'AAAAAAA',
      startDate: currentDate,
      endDate: currentDate,
      suspendedDate: currentDate,
      deletedDate: currentDate,
      status: SubscriptionStatus.INACTIVE,
      createdDate: currentDate,
      updatedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
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

    it('should create a OrganizationSubscriptionPlan', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
          suspendedDate: currentDate,
          deletedDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new OrganizationSubscriptionPlan()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationSubscriptionPlan', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          subscriptionName: 'BBBBBB',
          subscriptionTitle: 'BBBBBB',
          subscriptionType: 'BBBBBB',
          subscriptionPrice: 1,
          subscriptionQuantity: 1,
          subscriptionPeriod: 'BBBBBB',
          subscriptionTerms: 'BBBBBB',
          couponCode: 'BBBBBB',
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
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

    it('should partial update a OrganizationSubscriptionPlan', () => {
      const patchObject = Object.assign(
        {
          subscriptionType: 'BBBBBB',
          subscriptionPrice: 1,
          subscriptionTerms: 'BBBBBB',
          couponCode: 'BBBBBB',
          startDate: currentDate.format(DATE_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new OrganizationSubscriptionPlan()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
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

    it('should return a list of OrganizationSubscriptionPlan', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          subscriptionName: 'BBBBBB',
          subscriptionTitle: 'BBBBBB',
          subscriptionType: 'BBBBBB',
          subscriptionPrice: 1,
          subscriptionQuantity: 1,
          subscriptionPeriod: 'BBBBBB',
          subscriptionTerms: 'BBBBBB',
          couponCode: 'BBBBBB',
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          suspendedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
          status: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
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

    it('should delete a OrganizationSubscriptionPlan', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrganizationSubscriptionPlanToCollectionIfMissing', () => {
      it('should add a OrganizationSubscriptionPlan to an empty array', () => {
        const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 123 };
        expectedResult = service.addOrganizationSubscriptionPlanToCollectionIfMissing([], organizationSubscriptionPlan);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationSubscriptionPlan);
      });

      it('should not add a OrganizationSubscriptionPlan to an array that contains it', () => {
        const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 123 };
        const organizationSubscriptionPlanCollection: IOrganizationSubscriptionPlan[] = [
          {
            ...organizationSubscriptionPlan,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrganizationSubscriptionPlanToCollectionIfMissing(
          organizationSubscriptionPlanCollection,
          organizationSubscriptionPlan
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationSubscriptionPlan to an array that doesn't contain it", () => {
        const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 123 };
        const organizationSubscriptionPlanCollection: IOrganizationSubscriptionPlan[] = [{ id: 456 }];
        expectedResult = service.addOrganizationSubscriptionPlanToCollectionIfMissing(
          organizationSubscriptionPlanCollection,
          organizationSubscriptionPlan
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationSubscriptionPlan);
      });

      it('should add only unique OrganizationSubscriptionPlan to an array', () => {
        const organizationSubscriptionPlanArray: IOrganizationSubscriptionPlan[] = [{ id: 123 }, { id: 456 }, { id: 37919 }];
        const organizationSubscriptionPlanCollection: IOrganizationSubscriptionPlan[] = [{ id: 123 }];
        expectedResult = service.addOrganizationSubscriptionPlanToCollectionIfMissing(
          organizationSubscriptionPlanCollection,
          ...organizationSubscriptionPlanArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 123 };
        const organizationSubscriptionPlan2: IOrganizationSubscriptionPlan = { id: 456 };
        expectedResult = service.addOrganizationSubscriptionPlanToCollectionIfMissing(
          [],
          organizationSubscriptionPlan,
          organizationSubscriptionPlan2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organizationSubscriptionPlan);
        expect(expectedResult).toContain(organizationSubscriptionPlan2);
      });

      it('should accept null and undefined values', () => {
        const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 123 };
        expectedResult = service.addOrganizationSubscriptionPlanToCollectionIfMissing([], null, organizationSubscriptionPlan, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organizationSubscriptionPlan);
      });

      it('should return initial array if no OrganizationSubscriptionPlan is added', () => {
        const organizationSubscriptionPlanCollection: IOrganizationSubscriptionPlan[] = [{ id: 123 }];
        expectedResult = service.addOrganizationSubscriptionPlanToCollectionIfMissing(
          organizationSubscriptionPlanCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(organizationSubscriptionPlanCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
