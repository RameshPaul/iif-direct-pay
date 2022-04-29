import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReceipient, Receipient } from '../receipient.model';
import { ReceipientService } from '../service/receipient.service';

@Injectable({ providedIn: 'root' })
export class ReceipientRoutingResolveService implements Resolve<IReceipient> {
  constructor(protected service: ReceipientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceipient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((receipient: HttpResponse<Receipient>) => {
          if (receipient.body) {
            return of(receipient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Receipient());
  }
}
