import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserLogin } from '../user-login.model';
import { UserLoginService } from '../service/user-login.service';
import { UserLoginDeleteDialogComponent } from '../delete/user-login-delete-dialog.component';

@Component({
  selector: 'jhi-user-login',
  templateUrl: './user-login.component.html',
})
export class UserLoginComponent implements OnInit {
  userLogins?: IUserLogin[];
  isLoading = false;

  constructor(protected userLoginService: UserLoginService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.userLoginService.query().subscribe({
      next: (res: HttpResponse<IUserLogin[]>) => {
        this.isLoading = false;
        this.userLogins = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IUserLogin): number {
    return item.id!;
  }

  delete(userLogin: IUserLogin): void {
    const modalRef = this.modalService.open(UserLoginDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userLogin = userLogin;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
