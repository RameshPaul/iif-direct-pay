import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceipientRecurring } from '../receipient-recurring.model';
import { ReceipientRecurringService } from '../service/receipient-recurring.service';
import { ReceipientRecurringDeleteDialogComponent } from '../delete/receipient-recurring-delete-dialog.component';

@Component({
  selector: 'jhi-receipient-recurring',
  templateUrl: './receipient-recurring.component.html',
})
export class ReceipientRecurringComponent implements OnInit {
  receipientRecurrings?: IReceipientRecurring[];
  isLoading = false;

  constructor(protected receipientRecurringService: ReceipientRecurringService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.receipientRecurringService.query().subscribe({
      next: (res: HttpResponse<IReceipientRecurring[]>) => {
        this.isLoading = false;
        this.receipientRecurrings = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IReceipientRecurring): number {
    return item.id!;
  }

  delete(receipientRecurring: IReceipientRecurring): void {
    const modalRef = this.modalService.open(ReceipientRecurringDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.receipientRecurring = receipientRecurring;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
