import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserDevice } from '../user-device.model';
import { UserDeviceService } from '../service/user-device.service';
import { UserDeviceDeleteDialogComponent } from '../delete/user-device-delete-dialog.component';

@Component({
  selector: 'jhi-user-device',
  templateUrl: './user-device.component.html',
})
export class UserDeviceComponent implements OnInit {
  userDevices?: IUserDevice[];
  isLoading = false;

  constructor(protected userDeviceService: UserDeviceService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.userDeviceService.query().subscribe({
      next: (res: HttpResponse<IUserDevice[]>) => {
        this.isLoading = false;
        this.userDevices = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IUserDevice): number {
    return item.id!;
  }

  delete(userDevice: IUserDevice): void {
    const modalRef = this.modalService.open(UserDeviceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userDevice = userDevice;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
