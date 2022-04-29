import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceipient } from '../receipient.model';

@Component({
  selector: 'jhi-receipient-detail',
  templateUrl: './receipient-detail.component.html',
})
export class ReceipientDetailComponent implements OnInit {
  receipient: IReceipient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receipient }) => {
      this.receipient = receipient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
