import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizationSubscriptionPlan } from '../organization-subscription-plan.model';
import { OrganizationSubscriptionPlanService } from '../service/organization-subscription-plan.service';

@Component({
  templateUrl: './organization-subscription-plan-delete-dialog.component.html',
})
export class OrganizationSubscriptionPlanDeleteDialogComponent {
  organizationSubscriptionPlan?: IOrganizationSubscriptionPlan;

  constructor(protected organizationSubscriptionPlanService: OrganizationSubscriptionPlanService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizationSubscriptionPlanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
