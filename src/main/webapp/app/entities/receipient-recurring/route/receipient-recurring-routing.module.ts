import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReceipientRecurringComponent } from '../list/receipient-recurring.component';
import { ReceipientRecurringDetailComponent } from '../detail/receipient-recurring-detail.component';
import { ReceipientRecurringUpdateComponent } from '../update/receipient-recurring-update.component';
import { ReceipientRecurringRoutingResolveService } from './receipient-recurring-routing-resolve.service';

const receipientRecurringRoute: Routes = [
  {
    path: '',
    component: ReceipientRecurringComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReceipientRecurringDetailComponent,
    resolve: {
      receipientRecurring: ReceipientRecurringRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReceipientRecurringUpdateComponent,
    resolve: {
      receipientRecurring: ReceipientRecurringRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReceipientRecurringUpdateComponent,
    resolve: {
      receipientRecurring: ReceipientRecurringRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(receipientRecurringRoute)],
  exports: [RouterModule],
})
export class ReceipientRecurringRoutingModule {}
