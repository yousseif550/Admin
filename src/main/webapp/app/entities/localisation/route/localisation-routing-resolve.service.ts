import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILocalisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';

@Injectable({ providedIn: 'root' })
export class LocalisationRoutingResolveService implements Resolve<ILocalisation | null> {
  constructor(protected service: LocalisationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalisation | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((localisation: HttpResponse<ILocalisation>) => {
          if (localisation.body) {
            return of(localisation.body);
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
