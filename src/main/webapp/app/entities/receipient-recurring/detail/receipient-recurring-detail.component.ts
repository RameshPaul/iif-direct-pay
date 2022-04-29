import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceipientRecurring } from '../receipient-recurring.model';

@Component({
  selector: 'jhi-receipient-recurring-detail',
  templateUrl: './receipient-recurring-detail.component.html',
})
export class ReceipientRecurringDetailComponent implements OnInit {
  receipientRecurring: IReceipientRecurring | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receipientRecurring }) => {
      this.receipientRecurring = receipientRecurring;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
