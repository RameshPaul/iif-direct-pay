import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserDeviceComponent } from './list/user-device.component';
import { UserDeviceDetailComponent } from './detail/user-device-detail.component';
import { UserDeviceUpdateComponent } from './update/user-device-update.component';
import { UserDeviceDeleteDialogComponent } from './delete/user-device-delete-dialog.component';
import { UserDeviceRoutingModule } from './route/user-device-routing.module';

@NgModule({
  imports: [SharedModule, UserDeviceRoutingModule],
  declarations: [UserDeviceComponent, UserDeviceDetailComponent, UserDeviceUpdateComponent, UserDeviceDeleteDialogComponent],
  entryComponents: [UserDeviceDeleteDialogComponent],
})
export class UserDeviceModule {}
