import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOrganizationSubscriptionPlan, OrganizationSubscriptionPlan } from '../organization-subscription-plan.model';
import { OrganizationSubscriptionPlanService } from '../service/organization-subscription-plan.service';

import { OrganizationSubscriptionPlanRoutingResolveService } from './organization-subscription-plan-routing-resolve.service';

describe('OrganizationSubscriptionPlan routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OrganizationSubscriptionPlanRoutingResolveService;
  let service: OrganizationSubscriptionPlanService;
  let resultOrganizationSubscriptionPlan: IOrganizationSubscriptionPlan | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(OrganizationSubscriptionPlanRoutingResolveService);
    service = TestBed.inject(OrganizationSubscriptionPlanService);
    resultOrganizationSubscriptionPlan = undefined;
  });

  describe('resolve', () => {
    it('should return IOrganizationSubscriptionPlan returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationSubscriptionPlan = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationSubscriptionPlan).toEqual({ id: 123 });
    });

    it('should return new IOrganizationSubscriptionPlan if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationSubscriptionPlan = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOrganizationSubscriptionPlan).toEqual(new OrganizationSubscriptionPlan());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as OrganizationSubscriptionPlan })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationSubscriptionPlan = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizationSubscriptionPlan).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
