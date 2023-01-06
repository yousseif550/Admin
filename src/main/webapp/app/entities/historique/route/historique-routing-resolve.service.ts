import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHistorique } from '../historique.model';
import { HistoriqueService } from '../service/historique.service';

@Injectable({ providedIn: 'root' })
export class HistoriqueRoutingResolveService implements Resolve<IHistorique | null> {
  constructor(protected service: HistoriqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistorique | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((historique: HttpResponse<IHistorique>) => {
          if (historique.body) {
            return of(historique.body);
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
