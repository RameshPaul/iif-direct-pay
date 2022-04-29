import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserOrganization } from '../user-organization.model';
import { UserOrganizationService } from '../service/user-organization.service';

@Component({
  templateUrl: './user-organization-delete-dialog.component.html',
})
export class UserOrganizationDeleteDialogComponent {
  userOrganization?: IUserOrganization;

  constructor(protected userOrganizationService: UserOrganizationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userOrganizationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
