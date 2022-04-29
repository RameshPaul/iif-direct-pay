import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserOrganization } from '../user-organization.model';

@Component({
  selector: 'jhi-user-organization-detail',
  templateUrl: './user-organization-detail.component.html',
})
export class UserOrganizationDetailComponent implements OnInit {
  userOrganization: IUserOrganization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userOrganization }) => {
      this.userOrganization = userOrganization;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
