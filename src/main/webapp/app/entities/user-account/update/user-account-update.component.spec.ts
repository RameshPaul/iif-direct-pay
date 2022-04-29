import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserAccountService } from '../service/user-account.service';
import { IUserAccount, UserAccount } from '../user-account.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

import { UserAccountUpdateComponent } from './user-account-update.component';

describe('UserAccount Management Update Component', () => {
  let comp: UserAccountUpdateComponent;
  let fixture: ComponentFixture<UserAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userAccountService: UserAccountService;
  let userService: UserService;
  let organizationService: OrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserAccountUpdateComponent],
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
      .overrideTemplate(UserAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userAccountService = TestBed.inject(UserAccountService);
    userService = TestBed.inject(UserService);
    organizationService = TestBed.inject(OrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const userAccount: IUserAccount = { id: 456 };
      const userId: IUser = { id: 48980 };
      userAccount.userId = userId;

      const userCollection: IUser[] = [{ id: 35502 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [userId];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userAccount });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const userAccount: IUserAccount = { id: 456 };
      const organizationId: IOrganization = { id: 29497 };
      userAccount.organizationId = organizationId;

      const organizationCollection: IOrganization[] = [{ id: 36448 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organizationId];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userAccount });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userAccount: IUserAccount = { id: 456 };
      const userId: IUser = { id: 59705 };
      userAccount.userId = userId;
      const organizationId: IOrganization = { id: 50949 };
      userAccount.organizationId = organizationId;

      activatedRoute.data = of({ userAccount });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userAccount));
      expect(comp.usersSharedCollection).toContain(userId);
      expect(comp.organizationsSharedCollection).toContain(organizationId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserAccount>>();
      const userAccount = { id: 123 };
      jest.spyOn(userAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userAccount }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userAccountService.update).toHaveBeenCalledWith(userAccount);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserAccount>>();
      const userAccount = new UserAccount();
      jest.spyOn(userAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userAccount }));
      saveSubject.complete();

      // THEN
      expect(userAccountService.create).toHaveBeenCalledWith(userAccount);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserAccount>>();
      const userAccount = { id: 123 };
      jest.spyOn(userAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userAccountService.update).toHaveBeenCalledWith(userAccount);
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
