import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentService } from '../service/payment.service';
import { IPayment, Payment } from '../payment.model';
import { IPatron } from 'app/entities/patron/patron.model';
import { PatronService } from 'app/entities/patron/service/patron.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { ReceipientService } from 'app/entities/receipient/service/receipient.service';
import { IUserAccount } from 'app/entities/user-account/user-account.model';
import { UserAccountService } from 'app/entities/user-account/service/user-account.service';

import { PaymentUpdateComponent } from './payment-update.component';

describe('Payment Management Update Component', () => {
  let comp: PaymentUpdateComponent;
  let fixture: ComponentFixture<PaymentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentService: PaymentService;
  let patronService: PatronService;
  let userService: UserService;
  let organizationService: OrganizationService;
  let receipientService: ReceipientService;
  let userAccountService: UserAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PaymentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentService = TestBed.inject(PaymentService);
    patronService = TestBed.inject(PatronService);
    userService = TestBed.inject(UserService);
    organizationService = TestBed.inject(OrganizationService);
    receipientService = TestBed.inject(ReceipientService);
    userAccountService = TestBed.inject(UserAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Patron query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const patronId: IPatron = { id: 92244 };
      payment.patronId = patronId;

      const patronCollection: IPatron[] = [{ id: 93056 }];
      jest.spyOn(patronService, 'query').mockReturnValue(of(new HttpResponse({ body: patronCollection })));
      const additionalPatrons = [patronId];
      const expectedCollection: IPatron[] = [...additionalPatrons, ...patronCollection];
      jest.spyOn(patronService, 'addPatronToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(patronService.query).toHaveBeenCalled();
      expect(patronService.addPatronToCollectionIfMissing).toHaveBeenCalledWith(patronCollection, ...additionalPatrons);
      expect(comp.patronsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const patronUserId: IUser = { id: 95509 };
      payment.patronUserId = patronUserId;
      const receipientUserId: IUser = { id: 94532 };
      payment.receipientUserId = receipientUserId;
      const flaggedUserId: IUser = { id: 22414 };
      payment.flaggedUserId = flaggedUserId;
      const flagClearedUserId: IUser = { id: 22230 };
      payment.flagClearedUserId = flagClearedUserId;

      const userCollection: IUser[] = [{ id: 59005 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [patronUserId, receipientUserId, flaggedUserId, flagClearedUserId];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const patronUserOrgId: IOrganization = { id: 11640 };
      payment.patronUserOrgId = patronUserOrgId;
      const receipientUserOrgId: IOrganization = { id: 58470 };
      payment.receipientUserOrgId = receipientUserOrgId;
      const flaggedUserOrgId: IOrganization = { id: 59540 };
      payment.flaggedUserOrgId = flaggedUserOrgId;
      const flagClearedUserOrgId: IOrganization = { id: 78536 };
      payment.flagClearedUserOrgId = flagClearedUserOrgId;

      const organizationCollection: IOrganization[] = [{ id: 36345 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [patronUserOrgId, receipientUserOrgId, flaggedUserOrgId, flagClearedUserOrgId];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Receipient query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const receipientId: IReceipient = { id: 42046 };
      payment.receipientId = receipientId;

      const receipientCollection: IReceipient[] = [{ id: 65807 }];
      jest.spyOn(receipientService, 'query').mockReturnValue(of(new HttpResponse({ body: receipientCollection })));
      const additionalReceipients = [receipientId];
      const expectedCollection: IReceipient[] = [...additionalReceipients, ...receipientCollection];
      jest.spyOn(receipientService, 'addReceipientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(receipientService.query).toHaveBeenCalled();
      expect(receipientService.addReceipientToCollectionIfMissing).toHaveBeenCalledWith(receipientCollection, ...additionalReceipients);
      expect(comp.receipientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UserAccount query and add missing value', () => {
      const payment: IPayment = { id: 456 };
      const paymentSourceAccountId: IUserAccount = { id: 24759 };
      payment.paymentSourceAccountId = paymentSourceAccountId;
      const paymentDestinationAccountId: IUserAccount = { id: 11203 };
      payment.paymentDestinationAccountId = paymentDestinationAccountId;

      const userAccountCollection: IUserAccount[] = [{ id: 54114 }];
      jest.spyOn(userAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: userAccountCollection })));
      const additionalUserAccounts = [paymentSourceAccountId, paymentDestinationAccountId];
      const expectedCollection: IUserAccount[] = [...additionalUserAccounts, ...userAccountCollection];
      jest.spyOn(userAccountService, 'addUserAccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(userAccountService.query).toHaveBeenCalled();
      expect(userAccountService.addUserAccountToCollectionIfMissing).toHaveBeenCalledWith(userAccountCollection, ...additionalUserAccounts);
      expect(comp.userAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const payment: IPayment = { id: 456 };
      const patronId: IPatron = { id: 15887 };
      payment.patronId = patronId;
      const patronUserId: IUser = { id: 35170 };
      payment.patronUserId = patronUserId;
      const receipientUserId: IUser = { id: 47909 };
      payment.receipientUserId = receipientUserId;
      const flaggedUserId: IUser = { id: 73346 };
      payment.flaggedUserId = flaggedUserId;
      const flagClearedUserId: IUser = { id: 28820 };
      payment.flagClearedUserId = flagClearedUserId;
      const patronUserOrgId: IOrganization = { id: 16156 };
      payment.patronUserOrgId = patronUserOrgId;
      const receipientUserOrgId: IOrganization = { id: 72351 };
      payment.receipientUserOrgId = receipientUserOrgId;
      const flaggedUserOrgId: IOrganization = { id: 12871 };
      payment.flaggedUserOrgId = flaggedUserOrgId;
      const flagClearedUserOrgId: IOrganization = { id: 25946 };
      payment.flagClearedUserOrgId = flagClearedUserOrgId;
      const receipientId: IReceipient = { id: 46034 };
      payment.receipientId = receipientId;
      const paymentSourceAccountId: IUserAccount = { id: 33728 };
      payment.paymentSourceAccountId = paymentSourceAccountId;
      const paymentDestinationAccountId: IUserAccount = { id: 55711 };
      payment.paymentDestinationAccountId = paymentDestinationAccountId;

      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(payment));
      expect(comp.patronsSharedCollection).toContain(patronId);
      expect(comp.usersSharedCollection).toContain(patronUserId);
      expect(comp.usersSharedCollection).toContain(receipientUserId);
      expect(comp.usersSharedCollection).toContain(flaggedUserId);
      expect(comp.usersSharedCollection).toContain(flagClearedUserId);
      expect(comp.organizationsSharedCollection).toContain(patronUserOrgId);
      expect(comp.organizationsSharedCollection).toContain(receipientUserOrgId);
      expect(comp.organizationsSharedCollection).toContain(flaggedUserOrgId);
      expect(comp.organizationsSharedCollection).toContain(flagClearedUserOrgId);
      expect(comp.receipientsSharedCollection).toContain(receipientId);
      expect(comp.userAccountsSharedCollection).toContain(paymentSourceAccountId);
      expect(comp.userAccountsSharedCollection).toContain(paymentDestinationAccountId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payment>>();
      const payment = { id: 123 };
      jest.spyOn(paymentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payment }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentService.update).toHaveBeenCalledWith(payment);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payment>>();
      const payment = new Payment();
      jest.spyOn(paymentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: payment }));
      saveSubject.complete();

      // THEN
      expect(paymentService.create).toHaveBeenCalledWith(payment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Payment>>();
      const payment = { id: 123 };
      jest.spyOn(paymentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ payment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentService.update).toHaveBeenCalledWith(payment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPatronById', () => {
      it('Should return tracked Patron primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPatronById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackOrganizationById', () => {
      it('Should return tracked Organization primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrganizationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackReceipientById', () => {
      it('Should return tracked Receipient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReceipientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserAccountById', () => {
      it('Should return tracked UserAccount primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserAccountById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
