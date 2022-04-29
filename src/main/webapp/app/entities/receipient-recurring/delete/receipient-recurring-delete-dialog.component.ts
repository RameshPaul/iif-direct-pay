import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceipientRecurring } from '../receipient-recurring.model';
import { ReceipientRecurringService } from '../service/receipient-recurring.service';

@Component({
  templateUrl: './receipient-recurring-delete-dialog.component.html',
})
export class ReceipientRecurringDeleteDialogComponent {
  receipientRecurring?: IReceipientRecurring;

  constructor(protected receipientRecurringService: ReceipientRecurringService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receipientRecurringService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
