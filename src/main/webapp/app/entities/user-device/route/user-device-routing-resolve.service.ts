import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserDevice, UserDevice } from '../user-device.model';
import { UserDeviceService } from '../service/user-device.service';

@Injectable({ providedIn: 'root' })
export class UserDeviceRoutingResolveService implements Resolve<IUserDevice> {
  constructor(protected service: UserDeviceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserDevice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userDevice: HttpResponse<UserDevice>) => {
          if (userDevice.body) {
            return of(userDevice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserDevice());
  }
}
