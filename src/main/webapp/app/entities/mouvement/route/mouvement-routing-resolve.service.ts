import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMouvement } from '../mouvement.model';
import { MouvementService } from '../service/mouvement.service';

@Injectable({ providedIn: 'root' })
export class MouvementRoutingResolveService implements Resolve<IMouvement | null> {
  constructor(protected service: MouvementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMouvement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mouvement: HttpResponse<IMouvement>) => {
          if (mouvement.body) {
            return of(mouvement.body);
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
