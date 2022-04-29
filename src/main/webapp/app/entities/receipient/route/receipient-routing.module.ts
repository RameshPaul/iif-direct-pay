import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReceipientComponent } from '../list/receipient.component';
import { ReceipientDetailComponent } from '../detail/receipient-detail.component';
import { ReceipientUpdateComponent } from '../update/receipient-update.component';
import { ReceipientRoutingResolveService } from './receipient-routing-resolve.service';

const receipientRoute: Routes = [
  {
    path: '',
    component: ReceipientComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReceipientDetailComponent,
    resolve: {
      receipient: ReceipientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReceipientUpdateComponent,
    resolve: {
      receipient: ReceipientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReceipientUpdateComponent,
    resolve: {
      receipient: ReceipientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(receipientRoute)],
  exports: [RouterModule],
})
export class ReceipientRoutingModule {}
