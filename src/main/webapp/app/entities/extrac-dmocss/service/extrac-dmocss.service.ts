import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExtracDMOCSS, NewExtracDMOCSS } from '../extrac-dmocss.model';

export type PartialUpdateExtracDMOCSS = Partial<IExtracDMOCSS> & Pick<IExtracDMOCSS, 'id'>;

type RestOf<T extends IExtracDMOCSS | NewExtracDMOCSS> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestExtracDMOCSS = RestOf<IExtracDMOCSS>;

export type NewRestExtracDMOCSS = RestOf<NewExtracDMOCSS>;

export type PartialUpdateRestExtracDMOCSS = RestOf<PartialUpdateExtracDMOCSS>;

export type EntityResponseType = HttpResponse<IExtracDMOCSS>;
export type EntityArrayResponseType = HttpResponse<IExtracDMOCSS[]>;

@Injectable({ providedIn: 'root' })
export class ExtracDMOCSSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/extrac-dmocsses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(extracDMOCSS: NewExtracDMOCSS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extracDMOCSS);
    return this.http
      .post<RestExtracDMOCSS>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(extracDMOCSS: IExtracDMOCSS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extracDMOCSS);
    return this.http
      .put<RestExtracDMOCSS>(`${this.resourceUrl}/${this.getExtracDMOCSSIdentifier(extracDMOCSS)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(extracDMOCSS: PartialUpdateExtracDMOCSS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extracDMOCSS);
    return this.http
      .patch<RestExtracDMOCSS>(`${this.resourceUrl}/${this.getExtracDMOCSSIdentifier(extracDMOCSS)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestExtracDMOCSS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestExtracDMOCSS[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getExtracDMOCSSIdentifier(extracDMOCSS: Pick<IExtracDMOCSS, 'id'>): string {
    return extracDMOCSS.id;
  }

  compareExtracDMOCSS(o1: Pick<IExtracDMOCSS, 'id'> | null, o2: Pick<IExtracDMOCSS, 'id'> | null): boolean {
    return o1 && o2 ? this.getExtracDMOCSSIdentifier(o1) === this.getExtracDMOCSSIdentifier(o2) : o1 === o2;
  }

  addExtracDMOCSSToCollectionIfMissing<Type extends Pick<IExtracDMOCSS, 'id'>>(
    extracDMOCSSCollection: Type[],
    ...extracDMOCSSESToCheck: (Type | null | undefined)[]
  ): Type[] {
    const extracDMOCSSES: Type[] = extracDMOCSSESToCheck.filter(isPresent);
    if (extracDMOCSSES.length > 0) {
      const extracDMOCSSCollectionIdentifiers = extracDMOCSSCollection.map(
        extracDMOCSSItem => this.getExtracDMOCSSIdentifier(extracDMOCSSItem)!
      );
      const extracDMOCSSESToAdd = extracDMOCSSES.filter(extracDMOCSSItem => {
        const extracDMOCSSIdentifier = this.getExtracDMOCSSIdentifier(extracDMOCSSItem);
        if (extracDMOCSSCollectionIdentifiers.includes(extracDMOCSSIdentifier)) {
          return false;
        }
        extracDMOCSSCollectionIdentifiers.push(extracDMOCSSIdentifier);
        return true;
      });
      return [...extracDMOCSSESToAdd, ...extracDMOCSSCollection];
    }
    return extracDMOCSSCollection;
  }

  protected convertDateFromClient<T extends IExtracDMOCSS | NewExtracDMOCSS | PartialUpdateExtracDMOCSS>(extracDMOCSS: T): RestOf<T> {
    return {
      ...extracDMOCSS,
      date: extracDMOCSS.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restExtracDMOCSS: RestExtracDMOCSS): IExtracDMOCSS {
    return {
      ...restExtracDMOCSS,
      date: restExtracDMOCSS.date ? dayjs(restExtracDMOCSS.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestExtracDMOCSS>): HttpResponse<IExtracDMOCSS> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestExtracDMOCSS[]>): HttpResponse<IExtracDMOCSS[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
