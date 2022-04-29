import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserDeviceService } from '../service/user-device.service';
import { IUserDevice, UserDevice } from '../user-device.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { UserDeviceUpdateComponent } from './user-device-update.component';

describe('UserDevice Management Update Component', () => {
  let comp: UserDeviceUpdateComponent;
  let fixture: ComponentFixture<UserDeviceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userDeviceService: UserDeviceService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserDeviceUpdateComponent],
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
      .overrideTemplate(UserDeviceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserDeviceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userDeviceService = TestBed.inject(UserDeviceService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const userDevice: IUserDevice = { id: 456 };
      const userId: IUser = { id: 78033 };
      userDevice.userId = userId;

      const userCollection: IUser[] = [{ id: 43197 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [userId];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userDevice });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userDevice: IUserDevice = { id: 456 };
      const userId: IUser = { id: 79079 };
      userDevice.userId = userId;

      activatedRoute.data = of({ userDevice });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userDevice));
      expect(comp.usersSharedCollection).toContain(userId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserDevice>>();
      const userDevice = { id: 123 };
      jest.spyOn(userDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userDevice }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userDeviceService.update).toHaveBeenCalledWith(userDevice);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserDevice>>();
      const userDevice = new UserDevice();
      jest.spyOn(userDeviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userDevice }));
      saveSubject.complete();

      // THEN
      expect(userDeviceService.create).toHaveBeenCalledWith(userDevice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserDevice>>();
      const userDevice = { id: 123 };
      jest.spyOn(userDeviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userDevice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userDeviceService.update).toHaveBeenCalledWith(userDevice);
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
  });
});
