import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPatron } from '../patron.model';
import { PatronService } from '../service/patron.service';
import { PatronDeleteDialogComponent } from '../delete/patron-delete-dialog.component';

@Component({
  selector: 'jhi-patron',
  templateUrl: './patron.component.html',
})
export class PatronComponent implements OnInit {
  patrons?: IPatron[];
  isLoading = false;

  constructor(protected patronService: PatronService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.patronService.query().subscribe({
      next: (res: HttpResponse<IPatron[]>) => {
        this.isLoading = false;
        this.patrons = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPatron): number {
    return item.id!;
  }

  delete(patron: IPatron): void {
    const modalRef = this.modalService.open(PatronDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.patron = patron;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
