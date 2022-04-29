import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganizationSubscriptionPlanComponent } from './list/organization-subscription-plan.component';
import { OrganizationSubscriptionPlanDetailComponent } from './detail/organization-subscription-plan-detail.component';
import { OrganizationSubscriptionPlanUpdateComponent } from './update/organization-subscription-plan-update.component';
import { OrganizationSubscriptionPlanDeleteDialogComponent } from './delete/organization-subscription-plan-delete-dialog.component';
import { OrganizationSubscriptionPlanRoutingModule } from './route/organization-subscription-plan-routing.module';

@NgModule({
  imports: [SharedModule, OrganizationSubscriptionPlanRoutingModule],
  declarations: [
    OrganizationSubscriptionPlanComponent,
    OrganizationSubscriptionPlanDetailComponent,
    OrganizationSubscriptionPlanUpdateComponent,
    OrganizationSubscriptionPlanDeleteDialogComponent,
  ],
  entryComponents: [OrganizationSubscriptionPlanDeleteDialogComponent],
})
export class OrganizationSubscriptionPlanModule {}
