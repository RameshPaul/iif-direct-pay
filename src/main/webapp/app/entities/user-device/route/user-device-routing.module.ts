import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserDeviceComponent } from '../list/user-device.component';
import { UserDeviceDetailComponent } from '../detail/user-device-detail.component';
import { UserDeviceUpdateComponent } from '../update/user-device-update.component';
import { UserDeviceRoutingResolveService } from './user-device-routing-resolve.service';

const userDeviceRoute: Routes = [
  {
    path: '',
    component: UserDeviceComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserDeviceDetailComponent,
    resolve: {
      userDevice: UserDeviceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserDeviceUpdateComponent,
    resolve: {
      userDevice: UserDeviceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserDeviceUpdateComponent,
    resolve: {
      userDevice: UserDeviceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userDeviceRoute)],
  exports: [RouterModule],
})
export class UserDeviceRoutingModule {}
