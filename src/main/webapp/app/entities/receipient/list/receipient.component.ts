import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReceipient } from '../receipient.model';
import { ReceipientService } from '../service/receipient.service';
import { ReceipientDeleteDialogComponent } from '../delete/receipient-delete-dialog.component';

@Component({
  selector: 'jhi-receipient',
  templateUrl: './receipient.component.html',
})
export class ReceipientComponent implements OnInit {
  receipients?: IReceipient[];
  isLoading = false;

  constructor(protected receipientService: ReceipientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.receipientService.query().subscribe({
      next: (res: HttpResponse<IReceipient[]>) => {
        this.isLoading = false;
        this.receipients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IReceipient): number {
    return item.id!;
  }

  delete(receipient: IReceipient): void {
    const modalRef = this.modalService.open(ReceipientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.receipient = receipient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
