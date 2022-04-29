import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationSubscriptionPlan, OrganizationSubscriptionPlan } from '../organization-subscription-plan.model';
import { OrganizationSubscriptionPlanService } from '../service/organization-subscription-plan.service';

@Injectable({ providedIn: 'root' })
export class OrganizationSubscriptionPlanRoutingResolveService implements Resolve<IOrganizationSubscriptionPlan> {
  constructor(protected service: OrganizationSubscriptionPlanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganizationSubscriptionPlan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organizationSubscriptionPlan: HttpResponse<OrganizationSubscriptionPlan>) => {
          if (organizationSubscriptionPlan.body) {
            return of(organizationSubscriptionPlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrganizationSubscriptionPlan());
  }
}
