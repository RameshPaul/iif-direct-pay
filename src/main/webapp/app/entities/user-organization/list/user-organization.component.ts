import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserOrganization } from '../user-organization.model';
import { UserOrganizationService } from '../service/user-organization.service';
import { UserOrganizationDeleteDialogComponent } from '../delete/user-organization-delete-dialog.component';

@Component({
  selector: 'jhi-user-organization',
  templateUrl: './user-organization.component.html',
})
export class UserOrganizationComponent implements OnInit {
  userOrganizations?: IUserOrganization[];
  isLoading = false;

  constructor(protected userOrganizationService: UserOrganizationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.userOrganizationService.query().subscribe({
      next: (res: HttpResponse<IUserOrganization[]>) => {
        this.isLoading = false;
        this.userOrganizations = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IUserOrganization): number {
    return item.id!;
  }

  delete(userOrganization: IUserOrganization): void {
    const modalRef = this.modalService.open(UserOrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userOrganization = userOrganization;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
