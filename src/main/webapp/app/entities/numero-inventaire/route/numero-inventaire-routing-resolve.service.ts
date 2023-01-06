import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INumeroInventaire } from '../numero-inventaire.model';
import { NumeroInventaireService } from '../service/numero-inventaire.service';

@Injectable({ providedIn: 'root' })
export class NumeroInventaireRoutingResolveService implements Resolve<INumeroInventaire | null> {
  constructor(protected service: NumeroInventaireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INumeroInventaire | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((numeroInventaire: HttpResponse<INumeroInventaire>) => {
          if (numeroInventaire.body) {
            return of(numeroInventaire.body);
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
