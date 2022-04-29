import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReceipientRecurring, ReceipientRecurring } from '../receipient-recurring.model';
import { ReceipientRecurringService } from '../service/receipient-recurring.service';

@Injectable({ providedIn: 'root' })
export class ReceipientRecurringRoutingResolveService implements Resolve<IReceipientRecurring> {
  constructor(protected service: ReceipientRecurringService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceipientRecurring> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((receipientRecurring: HttpResponse<ReceipientRecurring>) => {
          if (receipientRecurring.body) {
            return of(receipientRecurring.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReceipientRecurring());
  }
}
