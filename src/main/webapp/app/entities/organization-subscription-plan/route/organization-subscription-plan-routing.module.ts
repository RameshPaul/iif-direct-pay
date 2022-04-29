import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizationSubscriptionPlanComponent } from '../list/organization-subscription-plan.component';
import { OrganizationSubscriptionPlanDetailComponent } from '../detail/organization-subscription-plan-detail.component';
import { OrganizationSubscriptionPlanUpdateComponent } from '../update/organization-subscription-plan-update.component';
import { OrganizationSubscriptionPlanRoutingResolveService } from './organization-subscription-plan-routing-resolve.service';

const organizationSubscriptionPlanRoute: Routes = [
  {
    path: '',
    component: OrganizationSubscriptionPlanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganizationSubscriptionPlanDetailComponent,
    resolve: {
      organizationSubscriptionPlan: OrganizationSubscriptionPlanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizationSubscriptionPlanUpdateComponent,
    resolve: {
      organizationSubscriptionPlan: OrganizationSubscriptionPlanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganizationSubscriptionPlanUpdateComponent,
    resolve: {
      organizationSubscriptionPlan: OrganizationSubscriptionPlanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizationSubscriptionPlanRoute)],
  exports: [RouterModule],
})
export class OrganizationSubscriptionPlanRoutingModule {}
