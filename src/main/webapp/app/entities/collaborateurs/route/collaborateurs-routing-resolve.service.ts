import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICollaborateurs } from '../collaborateurs.model';
import { CollaborateursService } from '../service/collaborateurs.service';

@Injectable({ providedIn: 'root' })
export class CollaborateursRoutingResolveService implements Resolve<ICollaborateurs | null> {
  constructor(protected service: CollaborateursService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollaborateurs | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((collaborateurs: HttpResponse<ICollaborateurs>) => {
          if (collaborateurs.body) {
            return of(collaborateurs.body);
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
