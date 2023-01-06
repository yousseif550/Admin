import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExtracDMOCSS } from '../extrac-dmocss.model';
import { ExtracDMOCSSService } from '../service/extrac-dmocss.service';

@Injectable({ providedIn: 'root' })
export class ExtracDMOCSSRoutingResolveService implements Resolve<IExtracDMOCSS | null> {
  constructor(protected service: ExtracDMOCSSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExtracDMOCSS | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((extracDMOCSS: HttpResponse<IExtracDMOCSS>) => {
          if (extracDMOCSS.body) {
            return of(extracDMOCSS.body);
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
