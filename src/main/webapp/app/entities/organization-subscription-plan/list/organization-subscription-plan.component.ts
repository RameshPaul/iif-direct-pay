import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizationSubscriptionPlan } from '../organization-subscription-plan.model';
import { OrganizationSubscriptionPlanService } from '../service/organization-subscription-plan.service';
import { OrganizationSubscriptionPlanDeleteDialogComponent } from '../delete/organization-subscription-plan-delete-dialog.component';

@Component({
  selector: 'jhi-organization-subscription-plan',
  templateUrl: './organization-subscription-plan.component.html',
})
export class OrganizationSubscriptionPlanComponent implements OnInit {
  organizationSubscriptionPlans?: IOrganizationSubscriptionPlan[];
  isLoading = false;

  constructor(protected organizationSubscriptionPlanService: OrganizationSubscriptionPlanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.organizationSubscriptionPlanService.query().subscribe({
      next: (res: HttpResponse<IOrganizationSubscriptionPlan[]>) => {
        this.isLoading = false;
        this.organizationSubscriptionPlans = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IOrganizationSubscriptionPlan): number {
    return item.id!;
  }

  delete(organizationSubscriptionPlan: IOrganizationSubscriptionPlan): void {
    const modalRef = this.modalService.open(OrganizationSubscriptionPlanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.organizationSubscriptionPlan = organizationSubscriptionPlan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
