import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PatronService } from '../service/patron.service';
import { IPatron, Patron } from '../patron.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IReceipient } from 'app/entities/receipient/receipient.model';
import { ReceipientService } from 'app/entities/receipient/service/receipient.service';

import { PatronUpdateComponent } from './patron-update.component';

describe('Patron Management Update Component', () => {
  let comp: PatronUpdateComponent;
  let fixture: ComponentFixture<PatronUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let patronService: PatronService;
  let userService: UserService;
  let organizationService: OrganizationService;
  let receipientService: ReceipientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PatronUpdateComponent],
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
      .overrideTemplate(PatronUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PatronUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    patronService = TestBed.inject(PatronService);
    userService = TestBed.inject(UserService);
    organizationService = TestBed.inject(OrganizationService);
    receipientService = TestBed.inject(ReceipientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const patron: IPatron = { id: 456 };
      const patronUserId: IUser = { id: 83198 };
      patron.patronUserId = patronUserId;
      const receipientUserId: IUser = { id: 91092 };
      patron.receipientUserId = receipientUserId;

      const userCollection: IUser[] = [{ id: 43048 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [patronUserId, receipientUserId];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const patron: IPatron = { id: 456 };
      const patronUserOrgId: IOrganization = { id: 2858 };
      patron.patronUserOrgId = patronUserOrgId;
      const receipientUserOrgId: IOrganization = { id: 6376 };
      patron.receipientUserOrgId = receipientUserOrgId;

      const organizationCollection: IOrganization[] = [{ id: 8941 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [patronUserOrgId, receipientUserOrgId];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Receipient query and add missing value', () => {
      const patron: IPatron = { id: 456 };
      const receipientId: IReceipient = { id: 94754 };
      patron.receipientId = receipientId;

      const receipientCollection: IReceipient[] = [{ id: 5004 }];
      jest.spyOn(receipientService, 'query').mockReturnValue(of(new HttpResponse({ body: receipientCollection })));
      const additionalReceipients = [receipientId];
      const expectedCollection: IReceipient[] = [...additionalReceipients, ...receipientCollection];
      jest.spyOn(receipientService, 'addReceipientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      expect(receipientService.query).toHaveBeenCalled();
      expect(receipientService.addReceipientToCollectionIfMissing).toHaveBeenCalledWith(receipientCollection, ...additionalReceipients);
      expect(comp.receipientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const patron: IPatron = { id: 456 };
      const patronUserId: IUser = { id: 46495 };
      patron.patronUserId = patronUserId;
      const receipientUserId: IUser = { id: 9059 };
      patron.receipientUserId = receipientUserId;
      const patronUserOrgId: IOrganization = { id: 35242 };
      patron.patronUserOrgId = patronUserOrgId;
      const receipientUserOrgId: IOrganization = { id: 34780 };
      patron.receipientUserOrgId = receipientUserOrgId;
      const receipientId: IReceipient = { id: 90171 };
      patron.receipientId = receipientId;

      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(patron));
      expect(comp.usersSharedCollection).toContain(patronUserId);
      expect(comp.usersSharedCollection).toContain(receipientUserId);
      expect(comp.organizationsSharedCollection).toContain(patronUserOrgId);
      expect(comp.organizationsSharedCollection).toContain(receipientUserOrgId);
      expect(comp.receipientsSharedCollection).toContain(receipientId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Patron>>();
      const patron = { id: 123 };
      jest.spyOn(patronService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: patron }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(patronService.update).toHaveBeenCalledWith(patron);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Patron>>();
      const patron = new Patron();
      jest.spyOn(patronService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: patron }));
      saveSubject.complete();

      // THEN
      expect(patronService.create).toHaveBeenCalledWith(patron);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Patron>>();
      const patron = { id: 123 };
      jest.spyOn(patronService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ patron });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(patronService.update).toHaveBeenCalledWith(patron);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
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
  });
});
