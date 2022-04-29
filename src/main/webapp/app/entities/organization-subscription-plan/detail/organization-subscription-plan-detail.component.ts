import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationSubscriptionPlan } from '../organization-subscription-plan.model';

@Component({
  selector: 'jhi-organization-subscription-plan-detail',
  templateUrl: './organization-subscription-plan-detail.component.html',
})
export class OrganizationSubscriptionPlanDetailComponent implements OnInit {
  organizationSubscriptionPlan: IOrganizationSubscriptionPlan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organizationSubscriptionPlan }) => {
      this.organizationSubscriptionPlan = organizationSubscriptionPlan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
