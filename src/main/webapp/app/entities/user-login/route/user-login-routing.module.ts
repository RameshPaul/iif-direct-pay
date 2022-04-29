import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserLoginComponent } from '../list/user-login.component';
import { UserLoginDetailComponent } from '../detail/user-login-detail.component';
import { UserLoginUpdateComponent } from '../update/user-login-update.component';
import { UserLoginRoutingResolveService } from './user-login-routing-resolve.service';

const userLoginRoute: Routes = [
  {
    path: '',
    component: UserLoginComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserLoginDetailComponent,
    resolve: {
      userLogin: UserLoginRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserLoginUpdateComponent,
    resolve: {
      userLogin: UserLoginRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserLoginUpdateComponent,
    resolve: {
      userLogin: UserLoginRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userLoginRoute)],
  exports: [RouterModule],
})
export class UserLoginRoutingModule {}
