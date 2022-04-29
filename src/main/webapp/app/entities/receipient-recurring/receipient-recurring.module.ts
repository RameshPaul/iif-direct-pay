import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReceipientRecurringComponent } from './list/receipient-recurring.component';
import { ReceipientRecurringDetailComponent } from './detail/receipient-recurring-detail.component';
import { ReceipientRecurringUpdateComponent } from './update/receipient-recurring-update.component';
import { ReceipientRecurringDeleteDialogComponent } from './delete/receipient-recurring-delete-dialog.component';
import { ReceipientRecurringRoutingModule } from './route/receipient-recurring-routing.module';

@NgModule({
  imports: [SharedModule, ReceipientRecurringRoutingModule],
  declarations: [
    ReceipientRecurringComponent,
    ReceipientRecurringDetailComponent,
    ReceipientRecurringUpdateComponent,
    ReceipientRecurringDeleteDialogComponent,
  ],
  entryComponents: [ReceipientRecurringDeleteDialogComponent],
})
export class ReceipientRecurringModule {}
