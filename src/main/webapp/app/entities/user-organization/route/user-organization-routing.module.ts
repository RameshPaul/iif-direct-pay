import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserOrganizationComponent } from '../list/user-organization.component';
import { UserOrganizationDetailComponent } from '../detail/user-organization-detail.component';
import { UserOrganizationUpdateComponent } from '../update/user-organization-update.component';
import { UserOrganizationRoutingResolveService } from './user-organization-routing-resolve.service';

const userOrganizationRoute: Routes = [
  {
    path: '',
    component: UserOrganizationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserOrganizationDetailComponent,
    resolve: {
      userOrganization: UserOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserOrganizationUpdateComponent,
    resolve: {
      userOrganization: UserOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserOrganizationUpdateComponent,
    resolve: {
      userOrganization: UserOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userOrganizationRoute)],
  exports: [RouterModule],
})
export class UserOrganizationRoutingModule {}
