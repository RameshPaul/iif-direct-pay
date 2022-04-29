import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserLoginComponent } from './list/user-login.component';
import { UserLoginDetailComponent } from './detail/user-login-detail.component';
import { UserLoginUpdateComponent } from './update/user-login-update.component';
import { UserLoginDeleteDialogComponent } from './delete/user-login-delete-dialog.component';
import { UserLoginRoutingModule } from './route/user-login-routing.module';

@NgModule({
  imports: [SharedModule, UserLoginRoutingModule],
  declarations: [UserLoginComponent, UserLoginDetailComponent, UserLoginUpdateComponent, UserLoginDeleteDialogComponent],
  entryComponents: [UserLoginDeleteDialogComponent],
})
export class UserLoginModule {}
