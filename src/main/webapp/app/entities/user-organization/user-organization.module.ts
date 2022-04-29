import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserOrganizationComponent } from './list/user-organization.component';
import { UserOrganizationDetailComponent } from './detail/user-organization-detail.component';
import { UserOrganizationUpdateComponent } from './update/user-organization-update.component';
import { UserOrganizationDeleteDialogComponent } from './delete/user-organization-delete-dialog.component';
import { UserOrganizationRoutingModule } from './route/user-organization-routing.module';

@NgModule({
  imports: [SharedModule, UserOrganizationRoutingModule],
  declarations: [
    UserOrganizationComponent,
    UserOrganizationDetailComponent,
    UserOrganizationUpdateComponent,
    UserOrganizationDeleteDialogComponent,
  ],
  entryComponents: [UserOrganizationDeleteDialogComponent],
})
export class UserOrganizationModule {}
