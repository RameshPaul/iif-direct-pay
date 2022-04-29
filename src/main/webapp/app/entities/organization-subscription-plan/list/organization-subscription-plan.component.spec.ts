import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { OrganizationSubscriptionPlanService } from '../service/organization-subscription-plan.service';

import { OrganizationSubscriptionPlanComponent } from './organization-subscription-plan.component';

describe('OrganizationSubscriptionPlan Management Component', () => {
  let comp: OrganizationSubscriptionPlanComponent;
  let fixture: ComponentFixture<OrganizationSubscriptionPlanComponent>;
  let service: OrganizationSubscriptionPlanService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [OrganizationSubscriptionPlanComponent],
    })
      .overrideTemplate(OrganizationSubscriptionPlanComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationSubscriptionPlanComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OrganizationSubscriptionPlanService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.organizationSubscriptionPlans?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
