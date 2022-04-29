import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizationSubscriptionPlanService } from '../service/organization-subscription-plan.service';
import { IOrganizationSubscriptionPlan, OrganizationSubscriptionPlan } from '../organization-subscription-plan.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { ISubscriptionPlan } from 'app/entities/subscription-plan/subscription-plan.model';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/service/subscription-plan.service';

import { OrganizationSubscriptionPlanUpdateComponent } from './organization-subscription-plan-update.component';

describe('OrganizationSubscriptionPlan Management Update Component', () => {
  let comp: OrganizationSubscriptionPlanUpdateComponent;
  let fixture: ComponentFixture<OrganizationSubscriptionPlanUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationSubscriptionPlanService: OrganizationSubscriptionPlanService;
  let organizationService: OrganizationService;
  let subscriptionPlanService: SubscriptionPlanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizationSubscriptionPlanUpdateComponent],
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
      .overrideTemplate(OrganizationSubscriptionPlanUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationSubscriptionPlanUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationSubscriptionPlanService = TestBed.inject(OrganizationSubscriptionPlanService);
    organizationService = TestBed.inject(OrganizationService);
    subscriptionPlanService = TestBed.inject(SubscriptionPlanService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Organization query and add missing value', () => {
      const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 456 };
      const organizationId: IOrganization = { id: 71008 };
      organizationSubscriptionPlan.organizationId = organizationId;

      const organizationCollection: IOrganization[] = [{ id: 14035 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organizationId];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organizationSubscriptionPlan });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SubscriptionPlan query and add missing value', () => {
      const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 456 };
      const subscriptionId: ISubscriptionPlan = { id: 60215 };
      organizationSubscriptionPlan.subscriptionId = subscriptionId;

      const subscriptionPlanCollection: ISubscriptionPlan[] = [{ id: 36369 }];
      jest.spyOn(subscriptionPlanService, 'query').mockReturnValue(of(new HttpResponse({ body: subscriptionPlanCollection })));
      const additionalSubscriptionPlans = [subscriptionId];
      const expectedCollection: ISubscriptionPlan[] = [...additionalSubscriptionPlans, ...subscriptionPlanCollection];
      jest.spyOn(subscriptionPlanService, 'addSubscriptionPlanToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organizationSubscriptionPlan });
      comp.ngOnInit();

      expect(subscriptionPlanService.query).toHaveBeenCalled();
      expect(subscriptionPlanService.addSubscriptionPlanToCollectionIfMissing).toHaveBeenCalledWith(
        subscriptionPlanCollection,
        ...additionalSubscriptionPlans
      );
      expect(comp.subscriptionPlansSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organizationSubscriptionPlan: IOrganizationSubscriptionPlan = { id: 456 };
      const organizationId: IOrganization = { id: 55361 };
      organizationSubscriptionPlan.organizationId = organizationId;
      const subscriptionId: ISubscriptionPlan = { id: 64185 };
      organizationSubscriptionPlan.subscriptionId = subscriptionId;

      activatedRoute.data = of({ organizationSubscriptionPlan });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(organizationSubscriptionPlan));
      expect(comp.organizationsSharedCollection).toContain(organizationId);
      expect(comp.subscriptionPlansSharedCollection).toContain(subscriptionId);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrganizationSubscriptionPlan>>();
      const organizationSubscriptionPlan = { id: 123 };
      jest.spyOn(organizationSubscriptionPlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationSubscriptionPlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationSubscriptionPlan }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationSubscriptionPlanService.update).toHaveBeenCalledWith(organizationSubscriptionPlan);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrganizationSubscriptionPlan>>();
      const organizationSubscriptionPlan = new OrganizationSubscriptionPlan();
      jest.spyOn(organizationSubscriptionPlanService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationSubscriptionPlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationSubscriptionPlan }));
      saveSubject.complete();

      // THEN
      expect(organizationSubscriptionPlanService.create).toHaveBeenCalledWith(organizationSubscriptionPlan);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OrganizationSubscriptionPlan>>();
      const organizationSubscriptionPlan = { id: 123 };
      jest.spyOn(organizationSubscriptionPlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationSubscriptionPlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationSubscriptionPlanService.update).toHaveBeenCalledWith(organizationSubscriptionPlan);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackOrganizationById', () => {
      it('Should return tracked Organization primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrganizationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSubscriptionPlanById', () => {
      it('Should return tracked SubscriptionPlan primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSubscriptionPlanById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
