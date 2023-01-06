import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMouvement, NewMouvement } from '../mouvement.model';

export type PartialUpdateMouvement = Partial<IMouvement> & Pick<IMouvement, 'id'>;

type RestOf<T extends IMouvement | NewMouvement> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestMouvement = RestOf<IMouvement>;

export type NewRestMouvement = RestOf<NewMouvement>;

export type PartialUpdateRestMouvement = RestOf<PartialUpdateMouvement>;

export type EntityResponseType = HttpResponse<IMouvement>;
export type EntityArrayResponseType = HttpResponse<IMouvement[]>;

@Injectable({ providedIn: 'root' })
export class MouvementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mouvements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mouvement: NewMouvement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvement);
    return this.http
      .post<RestMouvement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(mouvement: IMouvement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvement);
    return this.http
      .put<RestMouvement>(`${this.resourceUrl}/${this.getMouvementIdentifier(mouvement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(mouvement: PartialUpdateMouvement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvement);
    return this.http
      .patch<RestMouvement>(`${this.resourceUrl}/${this.getMouvementIdentifier(mouvement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestMouvement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMouvement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMouvementIdentifier(mouvement: Pick<IMouvement, 'id'>): string {
    return mouvement.id;
  }

  compareMouvement(o1: Pick<IMouvement, 'id'> | null, o2: Pick<IMouvement, 'id'> | null): boolean {
    return o1 && o2 ? this.getMouvementIdentifier(o1) === this.getMouvementIdentifier(o2) : o1 === o2;
  }

  addMouvementToCollectionIfMissing<Type extends Pick<IMouvement, 'id'>>(
    mouvementCollection: Type[],
    ...mouvementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mouvements: Type[] = mouvementsToCheck.filter(isPresent);
    if (mouvements.length > 0) {
      const mouvementCollectionIdentifiers = mouvementCollection.map(mouvementItem => this.getMouvementIdentifier(mouvementItem)!);
      const mouvementsToAdd = mouvements.filter(mouvementItem => {
        const mouvementIdentifier = this.getMouvementIdentifier(mouvementItem);
        if (mouvementCollectionIdentifiers.includes(mouvementIdentifier)) {
          return false;
        }
        mouvementCollectionIdentifiers.push(mouvementIdentifier);
        return true;
      });
      return [...mouvementsToAdd, ...mouvementCollection];
    }
    return mouvementCollection;
  }

  protected convertDateFromClient<T extends IMouvement | NewMouvement | PartialUpdateMouvement>(mouvement: T): RestOf<T> {
    return {
      ...mouvement,
      date: mouvement.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMouvement: RestMouvement): IMouvement {
    return {
      ...restMouvement,
      date: restMouvement.date ? dayjs(restMouvement.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMouvement>): HttpResponse<IMouvement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMouvement[]>): HttpResponse<IMouvement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
