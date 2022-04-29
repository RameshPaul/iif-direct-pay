import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserDevice } from '../user-device.model';
import { UserDeviceService } from '../service/user-device.service';

@Component({
  templateUrl: './user-device-delete-dialog.component.html',
})
export class UserDeviceDeleteDialogComponent {
  userDevice?: IUserDevice;

  constructor(protected userDeviceService: UserDeviceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userDeviceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
