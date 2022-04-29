import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ReceipientPatronRecurringType } from 'app/entities/enumerations/receipient-patron-recurring-type.model';
import { ReceipientPatronStatus } from 'app/entities/enumerations/receipient-patron-status.model';
import { IReceipient, Receipient } from '../receipient.model';

import { ReceipientService } from './receipient.service';

describe('Receipient Service', () => {
  let service: ReceipientService;
  let httpMock: HttpTestingController;
  let elemDefault: IReceipient;
  let expectedResult: IReceipient | IReceipient[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReceipientService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      isRecurring: false,
      recurringType: ReceipientPatronRecurringType.WEEKLY,
      recurringStartDate: currentDate,
      recurringEndDate: currentDate,
      enableReminder: false,
      startDate: currentDate,
      endDate: currentDate,
      isAutoPay: false,
      amountRequisite: 0,
      status: ReceipientPatronStatus.INACTIVE,
      isManagedByOrg: false,
      approvedDateTime: currentDate,
      rejectedDateTime: currentDate,
      rejectReason: 'AAAAAAA',
      onboardedDate: currentDate,
      reccuringPauseDate: currentDate,
      recurringResumeDate: currentDate,
      recurringPauseReason: 'AAAAAAA',
      createdDate: currentDate,
      updatedDate: currentDate,
      deletedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          recurringStartDate: currentDate.format(DATE_FORMAT),
          recurringEndDate: currentDate.format(DATE_FORMAT),
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          approvedDateTime: currentDate.format(DATE_TIME_FORMAT),
          rejectedDateTime: currentDate.format(DATE_TIME_FORMAT),
          onboardedDate: currentDate.format(DATE_FORMAT),
          reccuringPauseDate: currentDate.format(DATE_FORMAT),
          recurringResumeDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Receipient', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          recurringStartDate: currentDate.format(DATE_FORMAT),
          recurringEndDate: currentDate.format(DATE_FORMAT),
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          approvedDateTime: currentDate.format(DATE_TIME_FORMAT),
          rejectedDateTime: currentDate.format(DATE_TIME_FORMAT),
          onboardedDate: currentDate.format(DATE_FORMAT),
          reccuringPauseDate: currentDate.format(DATE_FORMAT),
          recurringResumeDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringStartDate: currentDate,
          recurringEndDate: currentDate,
          startDate: currentDate,
          endDate: currentDate,
          approvedDateTime: currentDate,
          rejectedDateTime: currentDate,
          onboardedDate: currentDate,
          reccuringPauseDate: currentDate,
          recurringResumeDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Receipient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Receipient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isRecurring: true,
          recurringType: 'BBBBBB',
          recurringStartDate: currentDate.format(DATE_FORMAT),
          recurringEndDate: currentDate.format(DATE_FORMAT),
          enableReminder: true,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          isAutoPay: true,
          amountRequisite: 1,
          status: 'BBBBBB',
          isManagedByOrg: true,
          approvedDateTime: currentDate.format(DATE_TIME_FORMAT),
          rejectedDateTime: currentDate.format(DATE_TIME_FORMAT),
          rejectReason: 'BBBBBB',
          onboardedDate: currentDate.format(DATE_FORMAT),
          reccuringPauseDate: currentDate.format(DATE_FORMAT),
          recurringResumeDate: currentDate.format(DATE_FORMAT),
          recurringPauseReason: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringStartDate: currentDate,
          recurringEndDate: currentDate,
          startDate: currentDate,
          endDate: currentDate,
          approvedDateTime: currentDate,
          rejectedDateTime: currentDate,
          onboardedDate: currentDate,
          reccuringPauseDate: currentDate,
          recurringResumeDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Receipient', () => {
      const patchObject = Object.assign(
        {
          recurringType: 'BBBBBB',
          recurringStartDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          isAutoPay: true,
          approvedDateTime: currentDate.format(DATE_TIME_FORMAT),
          rejectReason: 'BBBBBB',
          onboardedDate: currentDate.format(DATE_FORMAT),
          reccuringPauseDate: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Receipient()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          recurringStartDate: currentDate,
          recurringEndDate: currentDate,
          startDate: currentDate,
          endDate: currentDate,
          approvedDateTime: currentDate,
          rejectedDateTime: currentDate,
          onboardedDate: currentDate,
          reccuringPauseDate: currentDate,
          recurringResumeDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Receipient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isRecurring: true,
          recurringType: 'BBBBBB',
          recurringStartDate: currentDate.format(DATE_FORMAT),
          recurringEndDate: currentDate.format(DATE_FORMAT),
          enableReminder: true,
          startDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          isAutoPay: true,
          amountRequisite: 1,
          status: 'BBBBBB',
          isManagedByOrg: true,
          approvedDateTime: currentDate.format(DATE_TIME_FORMAT),
          rejectedDateTime: currentDate.format(DATE_TIME_FORMAT),
          rejectReason: 'BBBBBB',
          onboardedDate: currentDate.format(DATE_FORMAT),
          reccuringPauseDate: currentDate.format(DATE_FORMAT),
          recurringResumeDate: currentDate.format(DATE_FORMAT),
          recurringPauseReason: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
          deletedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringStartDate: currentDate,
          recurringEndDate: currentDate,
          startDate: currentDate,
          endDate: currentDate,
          approvedDateTime: currentDate,
          rejectedDateTime: currentDate,
          onboardedDate: currentDate,
          reccuringPauseDate: currentDate,
          recurringResumeDate: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
          deletedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Receipient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReceipientToCollectionIfMissing', () => {
      it('should add a Receipient to an empty array', () => {
        const receipient: IReceipient = { id: 123 };
        expectedResult = service.addReceipientToCollectionIfMissing([], receipient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receipient);
      });

      it('should not add a Receipient to an array that contains it', () => {
        const receipient: IReceipient = { id: 123 };
        const receipientCollection: IReceipient[] = [
          {
            ...receipient,
          },
          { id: 456 },
        ];
        expectedResult = service.addReceipientToCollectionIfMissing(receipientCollection, receipient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Receipient to an array that doesn't contain it", () => {
        const receipient: IReceipient = { id: 123 };
        const receipientCollection: IReceipient[] = [{ id: 456 }];
        expectedResult = service.addReceipientToCollectionIfMissing(receipientCollection, receipient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receipient);
      });

      it('should add only unique Receipient to an array', () => {
        const receipientArray: IReceipient[] = [{ id: 123 }, { id: 456 }, { id: 5797 }];
        const receipientCollection: IReceipient[] = [{ id: 123 }];
        expectedResult = service.addReceipientToCollectionIfMissing(receipientCollection, ...receipientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const receipient: IReceipient = { id: 123 };
        const receipient2: IReceipient = { id: 456 };
        expectedResult = service.addReceipientToCollectionIfMissing([], receipient, receipient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receipient);
        expect(expectedResult).toContain(receipient2);
      });

      it('should accept null and undefined values', () => {
        const receipient: IReceipient = { id: 123 };
        expectedResult = service.addReceipientToCollectionIfMissing([], null, receipient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receipient);
      });

      it('should return initial array if no Receipient is added', () => {
        const receipientCollection: IReceipient[] = [{ id: 123 }];
        expectedResult = service.addReceipientToCollectionIfMissing(receipientCollection, undefined, null);
        expect(expectedResult).toEqual(receipientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
