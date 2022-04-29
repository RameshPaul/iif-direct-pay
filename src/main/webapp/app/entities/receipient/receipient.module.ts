import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReceipientComponent } from './list/receipient.component';
import { ReceipientDetailComponent } from './detail/receipient-detail.component';
import { ReceipientUpdateComponent } from './update/receipient-update.component';
import { ReceipientDeleteDialogComponent } from './delete/receipient-delete-dialog.component';
import { ReceipientRoutingModule } from './route/receipient-routing.module';

@NgModule({
  imports: [SharedModule, ReceipientRoutingModule],
  declarations: [ReceipientComponent, ReceipientDetailComponent, ReceipientUpdateComponent, ReceipientDeleteDialogComponent],
  entryComponents: [ReceipientDeleteDialogComponent],
})
export class ReceipientModule {}
