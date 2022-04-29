import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganizationSubscriptionPlanDetailComponent } from './organization-subscription-plan-detail.component';

describe('OrganizationSubscriptionPlan Management Detail Component', () => {
  let comp: OrganizationSubscriptionPlanDetailComponent;
  let fixture: ComponentFixture<OrganizationSubscriptionPlanDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganizationSubscriptionPlanDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organizationSubscriptionPlan: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganizationSubscriptionPlanDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganizationSubscriptionPlanDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organizationSubscriptionPlan on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organizationSubscriptionPlan).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
