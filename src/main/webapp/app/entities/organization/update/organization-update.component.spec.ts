import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationService } from '../service/organization.service';
import { IOrganization, Organization } from '../organization.model';
import { ISubscriptionPlan } from 'app/entities/subscription-plan/subscription-plan.model';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/service/subscription-plan.service';

import { OrganizationUpdateComponent } from './organization-update.component';

describe('Organization Management Update Component', () => {
  let comp: OrganizationUpdateComponent;
  let fixture: ComponentFixture<OrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationService: OrganizationService;
  let subscriptionPlanService: SubscriptionPlanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationUpdateComponent],
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
      .overrideTemplate(OrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationService = TestBed.inject(OrganizationService);
    subscriptionPlanService = TestBed.inject(SubscriptionPlanService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SubscriptionPlan query and add missing value', () => {
      const organization: IOrganization = { id: 456 };
      const subscriptionId: ISubscriptionPlan = { id: 94111 };
      organization.subscriptionId = subscriptionId;

      const subscriptionPlanCollection: ISubscriptionPlan[] = [{ id: 30611 }];
      jest.spyOn(subscriptionPlanService, 'query').mockReturnValue(of(new HttpResponse({ body: subscriptionPlanCollection })));
      const additionalSubscriptionPlans = [subscriptionId];
      const expectedCollection: ISubscriptionPlan[] = [...additionalSubscriptionPlans, ...subscriptionPlanCollection];
      jest.spyOn(subscriptionPlanService, 'addSubscriptionPlanToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(subscriptionPlanService.query).toHaveBeenCalled();
      expect(subscriptionPlanService.addSubscriptionPlanToCollectionIfMissing).toHaveBeenCalledWith(
        subscriptionPlanCollection,
        ...additionalSubscriptionPlans
      );
      expect(comp.subscriptionPlansSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organization: IOrganization = { id: 456 };
      const subscriptionId: ISubscriptionPlan = { id: 22104 };
      organization.subscriptionId = subscriptionId;

      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(organization));
      expect(comp.subscriptionPlansSharedCollection).toContain(subscriptionId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Organization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organization }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationService.update).toHaveBeenCalledWith(organization);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Organization>>();
      const organization = new Organization();
      jest.spyOn(organizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organization }));
      saveSubject.complete();

      // THEN
      expect(organizationService.create).toHaveBeenCalledWith(organization);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Organization>>();
      const organization = { id: 123 };
      jest.spyOn(organizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationService.update).toHaveBeenCalledWith(organization);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSubscriptionPlanById', () => {
      it('Should return tracked SubscriptionPlan primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSubscriptionPlanById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
