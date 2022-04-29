import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserLogin } from '../user-login.model';

@Component({
  selector: 'jhi-user-login-detail',
  templateUrl: './user-login-detail.component.html',
})
export class UserLoginDetailComponent implements OnInit {
  userLogin: IUserLogin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userLogin }) => {
      this.userLogin = userLogin;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
