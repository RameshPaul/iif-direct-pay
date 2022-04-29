import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceipient } from '../receipient.model';
import { ReceipientService } from '../service/receipient.service';

@Component({
  templateUrl: './receipient-delete-dialog.component.html',
})
export class ReceipientDeleteDialogComponent {
  receipient?: IReceipient;

  constructor(protected receipientService: ReceipientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receipientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
