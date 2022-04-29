import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserOrganization, UserOrganization } from '../user-organization.model';
import { UserOrganizationService } from '../service/user-organization.service';

@Injectable({ providedIn: 'root' })
export class UserOrganizationRoutingResolveService implements Resolve<IUserOrganization> {
  constructor(protected service: UserOrganizationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserOrganization> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userOrganization: HttpResponse<UserOrganization>) => {
          if (userOrganization.body) {
            return of(userOrganization.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserOrganization());
  }
}
