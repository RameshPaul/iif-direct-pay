import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserLogin } from '../user-login.model';
import { UserLoginService } from '../service/user-login.service';

@Component({
  templateUrl: './user-login-delete-dialog.component.html',
})
export class UserLoginDeleteDialogComponent {
  userLogin?: IUserLogin;

  constructor(protected userLoginService: UserLoginService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userLoginService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
