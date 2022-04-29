import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReceipientService } from '../service/receipient.service';
import { IReceipient, Receipient } from '../receipient.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { ReceipientUpdateComponent } from './receipient-update.component';

describe('Receipient Management Update Component', () => {
  let comp: ReceipientUpdateComponent;
  let fixture: ComponentFixture<ReceipientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let receipientService: ReceipientService;
  let userService: UserService;
  let organizationService: OrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReceipientUpdateComponent],
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
      .overrideTemplate(ReceipientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceipientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    receipientService = TestBed.inject(ReceipientService);
    userService = TestBed.inject(UserService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const receipient: IReceipient = { id: 456 };
      const userId: IUser = { id: 26355 };
      receipient.userId = userId;
      const createdUserId: IUser = { id: 90026 };
      receipient.createdUserId = createdUserId;
      const approvedUserId: IUser = { id: 39114 };
      receipient.approvedUserId = approvedUserId;
      const rejectedUserId: IUser = { id: 83532 };
      receipient.rejectedUserId = rejectedUserId;

      const userCollection: IUser[] = [{ id: 89263 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [userId, createdUserId, approvedUserId, rejectedUserId];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receipient });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const receipient: IReceipient = { id: 456 };
      const organizationId: IOrganization = { id: 72740 };
      receipient.organizationId = organizationId;
      const managedOrganiztionId: IOrganization = { id: 95902 };
      receipient.managedOrganiztionId = managedOrganiztionId;
      const createdUserOrgId: IOrganization = { id: 41789 };
      receipient.createdUserOrgId = createdUserOrgId;
      const approvedUserOrgId: IOrganization = { id: 4339 };
      receipient.approvedUserOrgId = approvedUserOrgId;
      const rejectedUserOrgId: IOrganization = { id: 77461 };
      receipient.rejectedUserOrgId = rejectedUserOrgId;

      const organizationCollection: IOrganization[] = [{ id: 92706 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organizationId, managedOrganiztionId, createdUserOrgId, approvedUserOrgId, rejectedUserOrgId];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receipient });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const receipient: IReceipient = { id: 456 };
      const userId: IUser = { id: 52747 };
      receipient.userId = userId;
      const createdUserId: IUser = { id: 24362 };
      receipient.createdUserId = createdUserId;
      const approvedUserId: IUser = { id: 29601 };
      receipient.approvedUserId = approvedUserId;
      const rejectedUserId: IUser = { id: 54148 };
      receipient.rejectedUserId = rejectedUserId;
      const organizationId: IOrganization = { id: 17089 };
      receipient.organizationId = organizationId;
      const managedOrganiztionId: IOrganization = { id: 79128 };
      receipient.managedOrganiztionId = managedOrganiztionId;
      const createdUserOrgId: IOrganization = { id: 79128 };
      receipient.createdUserOrgId = createdUserOrgId;
      const approvedUserOrgId: IOrganization = { id: 32882 };
      receipient.approvedUserOrgId = approvedUserOrgId;
      const rejectedUserOrgId: IOrganization = { id: 33302 };
      receipient.rejectedUserOrgId = rejectedUserOrgId;

      activatedRoute.data = of({ receipient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(receipient));
      expect(comp.usersSharedCollection).toContain(userId);
      expect(comp.usersSharedCollection).toContain(createdUserId);
      expect(comp.usersSharedCollection).toContain(approvedUserId);
      expect(comp.usersSharedCollection).toContain(rejectedUserId);
      expect(comp.organizationsSharedCollection).toContain(organizationId);
      expect(comp.organizationsSharedCollection).toContain(managedOrganiztionId);
      expect(comp.organizationsSharedCollection).toContain(createdUserOrgId);
      expect(comp.organizationsSharedCollection).toContain(approvedUserOrgId);
      expect(comp.organizationsSharedCollection).toContain(rejectedUserOrgId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receipient>>();
      const receipient = { id: 123 };
      jest.spyOn(receipientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receipient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(receipientService.update).toHaveBeenCalledWith(receipient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receipient>>();
      const receipient = new Receipient();
      jest.spyOn(receipientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receipient }));
      saveSubject.complete();

      // THEN
      expect(receipientService.create).toHaveBeenCalledWith(receipient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receipient>>();
      const receipient = { id: 123 };
      jest.spyOn(receipientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(receipientService.update).toHaveBeenCalledWith(receipient);
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
  });
});
