import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypemateriel } from '../typemateriel.model';
import { TypematerielService } from '../service/typemateriel.service';

@Injectable({ providedIn: 'root' })
export class TypematerielRoutingResolveService implements Resolve<ITypemateriel | null> {
  constructor(protected service: TypematerielService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypemateriel | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typemateriel: HttpResponse<ITypemateriel>) => {
          if (typemateriel.body) {
            return of(typemateriel.body);
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
