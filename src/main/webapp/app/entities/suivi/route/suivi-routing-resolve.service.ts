import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISuivi } from '../suivi.model';
import { SuiviService } from '../service/suivi.service';

@Injectable({ providedIn: 'root' })
export class SuiviRoutingResolveService implements Resolve<ISuivi | null> {
  constructor(protected service: SuiviService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISuivi | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((suivi: HttpResponse<ISuivi>) => {
          if (suivi.body) {
            return of(suivi.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
