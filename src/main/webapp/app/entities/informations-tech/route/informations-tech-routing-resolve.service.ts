import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInformationsTech } from '../informations-tech.model';
import { InformationsTechService } from '../service/informations-tech.service';

@Injectable({ providedIn: 'root' })
export class InformationsTechRoutingResolveService implements Resolve<IInformationsTech | null> {
  constructor(protected service: InformationsTechService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInformationsTech | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((informationsTech: HttpResponse<IInformationsTech>) => {
          if (informationsTech.body) {
            return of(informationsTech.body);
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
