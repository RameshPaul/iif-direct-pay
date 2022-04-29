import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReceipientRecurringService } from '../service/receipient-recurring.service';
import { IReceipientRecurring, ReceipientRecurring } from '../receipient-recurring.model';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { ReceipientService } from 'app/entities/receipient/service/receipient.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { ReceipientRecurringUpdateComponent } from './receipient-recurring-update.component';

describe('ReceipientRecurring Management Update Component', () => {
  let comp: ReceipientRecurringUpdateComponent;
  let fixture: ComponentFixture<ReceipientRecurringUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let receipientRecurringService: ReceipientRecurringService;
  let receipientService: ReceipientService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReceipientRecurringUpdateComponent],
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
      .overrideTemplate(ReceipientRecurringUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceipientRecurringUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    receipientRecurringService = TestBed.inject(ReceipientRecurringService);
    receipientService = TestBed.inject(ReceipientService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Receipient query and add missing value', () => {
      const receipientRecurring: IReceipientRecurring = { id: 456 };
      const receipientId: IReceipient = { id: 8704 };
      receipientRecurring.receipientId = receipientId;

      const receipientCollection: IReceipient[] = [{ id: 10866 }];
      jest.spyOn(receipientService, 'query').mockReturnValue(of(new HttpResponse({ body: receipientCollection })));
      const additionalReceipients = [receipientId];
      const expectedCollection: IReceipient[] = [...additionalReceipients, ...receipientCollection];
      jest.spyOn(receipientService, 'addReceipientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receipientRecurring });
      comp.ngOnInit();

      expect(receipientService.query).toHaveBeenCalled();
      expect(receipientService.addReceipientToCollectionIfMissing).toHaveBeenCalledWith(receipientCollection, ...additionalReceipients);
      expect(comp.receipientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const receipientRecurring: IReceipientRecurring = { id: 456 };
      const userId: IUser = { id: 21247 };
      receipientRecurring.userId = userId;

      const userCollection: IUser[] = [{ id: 6727 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [userId];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receipientRecurring });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const receipientRecurring: IReceipientRecurring = { id: 456 };
      const receipientId: IReceipient = { id: 13461 };
      receipientRecurring.receipientId = receipientId;
      const userId: IUser = { id: 93665 };
      receipientRecurring.userId = userId;

      activatedRoute.data = of({ receipientRecurring });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(receipientRecurring));
      expect(comp.receipientsSharedCollection).toContain(receipientId);
      expect(comp.usersSharedCollection).toContain(userId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReceipientRecurring>>();
      const receipientRecurring = { id: 123 };
      jest.spyOn(receipientRecurringService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipientRecurring });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receipientRecurring }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(receipientRecurringService.update).toHaveBeenCalledWith(receipientRecurring);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReceipientRecurring>>();
      const receipientRecurring = new ReceipientRecurring();
      jest.spyOn(receipientRecurringService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipientRecurring });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receipientRecurring }));
      saveSubject.complete();

      // THEN
      expect(receipientRecurringService.create).toHaveBeenCalledWith(receipientRecurring);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReceipientRecurring>>();
      const receipientRecurring = { id: 123 };
      jest.spyOn(receipientRecurringService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipientRecurring });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(receipientRecurringService.update).toHaveBeenCalledWith(receipientRecurring);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackReceipientById', () => {
      it('Should return tracked Receipient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReceipientById(0, entity);
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
  });
});
