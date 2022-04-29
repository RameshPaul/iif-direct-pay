import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserLogin, UserLogin } from '../user-login.model';
import { UserLoginService } from '../service/user-login.service';

@Injectable({ providedIn: 'root' })
export class UserLoginRoutingResolveService implements Resolve<IUserLogin> {
  constructor(protected service: UserLoginService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserLogin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userLogin: HttpResponse<UserLogin>) => {
          if (userLogin.body) {
            return of(userLogin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserLogin());
  }
}
