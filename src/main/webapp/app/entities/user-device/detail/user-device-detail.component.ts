import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserDevice } from '../user-device.model';

@Component({
  selector: 'jhi-user-device-detail',
  templateUrl: './user-device-detail.component.html',
})
export class UserDeviceDetailComponent implements OnInit {
  userDevice: IUserDevice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDevice }) => {
      this.userDevice = userDevice;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
