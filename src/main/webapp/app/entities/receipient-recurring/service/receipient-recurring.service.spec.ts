import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ReceipientRecurringStatus } from 'app/entities/enumerations/receipient-recurring-status.model';
import { IReceipientRecurring, ReceipientRecurring } from '../receipient-recurring.model';

import { ReceipientRecurringService } from './receipient-recurring.service';

describe('ReceipientRecurring Service', () => {
  let service: ReceipientRecurringService;
  let httpMock: HttpTestingController;
  let elemDefault: IReceipientRecurring;
  let expectedResult: IReceipientRecurring | IReceipientRecurring[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReceipientRecurringService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      recurringPeriod: 0,
      startDate: currentDate,
      endDate: currentDate,
      amountRequisite: 0,
      amountPatronCommited: 0,
      amountReceived: 0,
      amountBalance: 0,
      totalPatrons: 0,
      detailsText: 'AAAAAAA',
      status: ReceipientRecurringStatus.UNDER_FUNDED,
      pauseDate: currentDate,
      resumeDate: currentDate,
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
          pauseDate: currentDate.format(DATE_FORMAT),
          resumeDate: currentDate.format(DATE_FORMAT),
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

    it('should create a ReceipientRecurring', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          pauseDate: currentDate.format(DATE_FORMAT),
          resumeDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
          pauseDate: currentDate,
          resumeDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ReceipientRecurring()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReceipientRecurring', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          recurringPeriod: 1,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          amountRequisite: 1,
          amountPatronCommited: 1,
          amountReceived: 1,
          amountBalance: 1,
          totalPatrons: 1,
          detailsText: 'BBBBBB',
          status: 'BBBBBB',
          pauseDate: currentDate.format(DATE_FORMAT),
          resumeDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
          pauseDate: currentDate,
          resumeDate: currentDate,
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

    it('should partial update a ReceipientRecurring', () => {
      const patchObject = Object.assign(
        {
          recurringPeriod: 1,
          amountRequisite: 1,
          amountBalance: 1,
          pauseDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new ReceipientRecurring()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
          pauseDate: currentDate,
          resumeDate: currentDate,
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

    it('should return a list of ReceipientRecurring', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          recurringPeriod: 1,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          amountRequisite: 1,
          amountPatronCommited: 1,
          amountReceived: 1,
          amountBalance: 1,
          totalPatrons: 1,
          detailsText: 'BBBBBB',
          status: 'BBBBBB',
          pauseDate: currentDate.format(DATE_FORMAT),
          resumeDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          endDate: currentDate,
          pauseDate: currentDate,
          resumeDate: currentDate,
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

    it('should delete a ReceipientRecurring', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReceipientRecurringToCollectionIfMissing', () => {
      it('should add a ReceipientRecurring to an empty array', () => {
        const receipientRecurring: IReceipientRecurring = { id: 123 };
        expectedResult = service.addReceipientRecurringToCollectionIfMissing([], receipientRecurring);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receipientRecurring);
      });

      it('should not add a ReceipientRecurring to an array that contains it', () => {
        const receipientRecurring: IReceipientRecurring = { id: 123 };
        const receipientRecurringCollection: IReceipientRecurring[] = [
          {
            ...receipientRecurring,
          },
          { id: 456 },
        ];
        expectedResult = service.addReceipientRecurringToCollectionIfMissing(receipientRecurringCollection, receipientRecurring);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReceipientRecurring to an array that doesn't contain it", () => {
        const receipientRecurring: IReceipientRecurring = { id: 123 };
        const receipientRecurringCollection: IReceipientRecurring[] = [{ id: 456 }];
        expectedResult = service.addReceipientRecurringToCollectionIfMissing(receipientRecurringCollection, receipientRecurring);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receipientRecurring);
      });

      it('should add only unique ReceipientRecurring to an array', () => {
        const receipientRecurringArray: IReceipientRecurring[] = [{ id: 123 }, { id: 456 }, { id: 41942 }];
        const receipientRecurringCollection: IReceipientRecurring[] = [{ id: 123 }];
        expectedResult = service.addReceipientRecurringToCollectionIfMissing(receipientRecurringCollection, ...receipientRecurringArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const receipientRecurring: IReceipientRecurring = { id: 123 };
        const receipientRecurring2: IReceipientRecurring = { id: 456 };
        expectedResult = service.addReceipientRecurringToCollectionIfMissing([], receipientRecurring, receipientRecurring2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receipientRecurring);
        expect(expectedResult).toContain(receipientRecurring2);
      });

      it('should accept null and undefined values', () => {
        const receipientRecurring: IReceipientRecurring = { id: 123 };
        expectedResult = service.addReceipientRecurringToCollectionIfMissing([], null, receipientRecurring, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receipientRecurring);
      });

      it('should return initial array if no ReceipientRecurring is added', () => {
        const receipientRecurringCollection: IReceipientRecurring[] = [{ id: 123 }];
        expectedResult = service.addReceipientRecurringToCollectionIfMissing(receipientRecurringCollection, undefined, null);
        expect(expectedResult).toEqual(receipientRecurringCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
