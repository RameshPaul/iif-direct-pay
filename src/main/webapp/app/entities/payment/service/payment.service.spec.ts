import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';
import { UserAccountType } from 'app/entities/enumerations/user-account-type.model';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';
import { IPayment, Payment } from '../payment.model';

import { PaymentService } from './payment.service';

describe('Payment Service', () => {
  let service: PaymentService;
  let httpMock: HttpTestingController;
  let elemDefault: IPayment;
  let expectedResult: IPayment | IPayment[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      recurringPeriod: currentDate,
      amount: 0,
      transactionId: 'AAAAAAA',
      paymentType: PaymentType.CREDIT,
      paymentSource: UserAccountType.UPI,
      paymentStatus: PaymentStatus.INITIATED,
      paymentStatusDetails: 'AAAAAAA',
      paymentStartDateTime: currentDate,
      paymentCompleteDateTime: currentDate,
      paymentFailureDateTime: currentDate,
      patronComment: 'AAAAAAA',
      isAutoPay: false,
      paymentDestinationSource: UserAccountType.UPI,
      paymentReceivedDateTIme: currentDate,
      paymentReceivedStatus: PaymentStatus.INITIATED,
      paymentReceivedDetails: 'AAAAAAA',
      paymentRefundedDateTime: currentDate,
      userComment: 'AAAAAAA',
      flaggedDateTime: currentDate,
      flagDetails: 'AAAAAAA',
      flaggedEmailId: 'AAAAAAA',
      flaggedAmount: 0,
      flagCreatedDateTime: currentDate,
      isRecurringPayment: false,
      transactionDetails: 'AAAAAAA',
      createdDate: currentDate,
      updatedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          recurringPeriod: currentDate.format(DATE_FORMAT),
          paymentStartDateTime: currentDate.format(DATE_FORMAT),
          paymentCompleteDateTime: currentDate.format(DATE_FORMAT),
          paymentFailureDateTime: currentDate.format(DATE_FORMAT),
          paymentReceivedDateTIme: currentDate.format(DATE_FORMAT),
          paymentRefundedDateTime: currentDate.format(DATE_FORMAT),
          flaggedDateTime: currentDate.format(DATE_FORMAT),
          flagCreatedDateTime: currentDate.format(DATE_FORMAT),
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

    it('should create a Payment', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          recurringPeriod: currentDate.format(DATE_FORMAT),
          paymentStartDateTime: currentDate.format(DATE_FORMAT),
          paymentCompleteDateTime: currentDate.format(DATE_FORMAT),
          paymentFailureDateTime: currentDate.format(DATE_FORMAT),
          paymentReceivedDateTIme: currentDate.format(DATE_FORMAT),
          paymentRefundedDateTime: currentDate.format(DATE_FORMAT),
          flaggedDateTime: currentDate.format(DATE_FORMAT),
          flagCreatedDateTime: currentDate.format(DATE_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringPeriod: currentDate,
          paymentStartDateTime: currentDate,
          paymentCompleteDateTime: currentDate,
          paymentFailureDateTime: currentDate,
          paymentReceivedDateTIme: currentDate,
          paymentRefundedDateTime: currentDate,
          flaggedDateTime: currentDate,
          flagCreatedDateTime: currentDate,
          createdDate: currentDate,
          updatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Payment()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Payment', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          recurringPeriod: currentDate.format(DATE_FORMAT),
          amount: 1,
          transactionId: 'BBBBBB',
          paymentType: 'BBBBBB',
          paymentSource: 'BBBBBB',
          paymentStatus: 'BBBBBB',
          paymentStatusDetails: 'BBBBBB',
          paymentStartDateTime: currentDate.format(DATE_FORMAT),
          paymentCompleteDateTime: currentDate.format(DATE_FORMAT),
          paymentFailureDateTime: currentDate.format(DATE_FORMAT),
          patronComment: 'BBBBBB',
          isAutoPay: true,
          paymentDestinationSource: 'BBBBBB',
          paymentReceivedDateTIme: currentDate.format(DATE_FORMAT),
          paymentReceivedStatus: 'BBBBBB',
          paymentReceivedDetails: 'BBBBBB',
          paymentRefundedDateTime: currentDate.format(DATE_FORMAT),
          userComment: 'BBBBBB',
          flaggedDateTime: currentDate.format(DATE_FORMAT),
          flagDetails: 'BBBBBB',
          flaggedEmailId: 'BBBBBB',
          flaggedAmount: 1,
          flagCreatedDateTime: currentDate.format(DATE_FORMAT),
          isRecurringPayment: true,
          transactionDetails: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringPeriod: currentDate,
          paymentStartDateTime: currentDate,
          paymentCompleteDateTime: currentDate,
          paymentFailureDateTime: currentDate,
          paymentReceivedDateTIme: currentDate,
          paymentRefundedDateTime: currentDate,
          flaggedDateTime: currentDate,
          flagCreatedDateTime: currentDate,
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

    it('should partial update a Payment', () => {
      const patchObject = Object.assign(
        {
          amount: 1,
          paymentSource: 'BBBBBB',
          paymentStatus: 'BBBBBB',
          paymentCompleteDateTime: currentDate.format(DATE_FORMAT),
          isAutoPay: true,
          paymentReceivedDateTIme: currentDate.format(DATE_FORMAT),
          paymentReceivedDetails: 'BBBBBB',
          userComment: 'BBBBBB',
          flaggedEmailId: 'BBBBBB',
          flaggedAmount: 1,
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Payment()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          recurringPeriod: currentDate,
          paymentStartDateTime: currentDate,
          paymentCompleteDateTime: currentDate,
          paymentFailureDateTime: currentDate,
          paymentReceivedDateTIme: currentDate,
          paymentRefundedDateTime: currentDate,
          flaggedDateTime: currentDate,
          flagCreatedDateTime: currentDate,
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

    it('should return a list of Payment', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          recurringPeriod: currentDate.format(DATE_FORMAT),
          amount: 1,
          transactionId: 'BBBBBB',
          paymentType: 'BBBBBB',
          paymentSource: 'BBBBBB',
          paymentStatus: 'BBBBBB',
          paymentStatusDetails: 'BBBBBB',
          paymentStartDateTime: currentDate.format(DATE_FORMAT),
          paymentCompleteDateTime: currentDate.format(DATE_FORMAT),
          paymentFailureDateTime: currentDate.format(DATE_FORMAT),
          patronComment: 'BBBBBB',
          isAutoPay: true,
          paymentDestinationSource: 'BBBBBB',
          paymentReceivedDateTIme: currentDate.format(DATE_FORMAT),
          paymentReceivedStatus: 'BBBBBB',
          paymentReceivedDetails: 'BBBBBB',
          paymentRefundedDateTime: currentDate.format(DATE_FORMAT),
          userComment: 'BBBBBB',
          flaggedDateTime: currentDate.format(DATE_FORMAT),
          flagDetails: 'BBBBBB',
          flaggedEmailId: 'BBBBBB',
          flaggedAmount: 1,
          flagCreatedDateTime: currentDate.format(DATE_FORMAT),
          isRecurringPayment: true,
          transactionDetails: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          updatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          recurringPeriod: currentDate,
          paymentStartDateTime: currentDate,
          paymentCompleteDateTime: currentDate,
          paymentFailureDateTime: currentDate,
          paymentReceivedDateTIme: currentDate,
          paymentRefundedDateTime: currentDate,
          flaggedDateTime: currentDate,
          flagCreatedDateTime: currentDate,
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

    it('should delete a Payment', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentToCollectionIfMissing', () => {
      it('should add a Payment to an empty array', () => {
        const payment: IPayment = { id: 123 };
        expectedResult = service.addPaymentToCollectionIfMissing([], payment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payment);
      });

      it('should not add a Payment to an array that contains it', () => {
        const payment: IPayment = { id: 123 };
        const paymentCollection: IPayment[] = [
          {
            ...payment,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaymentToCollectionIfMissing(paymentCollection, payment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Payment to an array that doesn't contain it", () => {
        const payment: IPayment = { id: 123 };
        const paymentCollection: IPayment[] = [{ id: 456 }];
        expectedResult = service.addPaymentToCollectionIfMissing(paymentCollection, payment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payment);
      });

      it('should add only unique Payment to an array', () => {
        const paymentArray: IPayment[] = [{ id: 123 }, { id: 456 }, { id: 50888 }];
        const paymentCollection: IPayment[] = [{ id: 123 }];
        expectedResult = service.addPaymentToCollectionIfMissing(paymentCollection, ...paymentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const payment: IPayment = { id: 123 };
        const payment2: IPayment = { id: 456 };
        expectedResult = service.addPaymentToCollectionIfMissing([], payment, payment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(payment);
        expect(expectedResult).toContain(payment2);
      });

      it('should accept null and undefined values', () => {
        const payment: IPayment = { id: 123 };
        expectedResult = service.addPaymentToCollectionIfMissing([], null, payment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(payment);
      });

      it('should return initial array if no Payment is added', () => {
        const paymentCollection: IPayment[] = [{ id: 123 }];
        expectedResult = service.addPaymentToCollectionIfMissing(paymentCollection, undefined, null);
        expect(expectedResult).toEqual(paymentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
