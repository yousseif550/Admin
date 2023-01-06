import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMateriel, NewMateriel } from '../materiel.model';

export type PartialUpdateMateriel = Partial<IMateriel> & Pick<IMateriel, 'id'>;

type RestOf<T extends IMateriel | NewMateriel> = Omit<T, 'dateAttribution' | 'dateRendu'> & {
  dateAttribution?: string | null;
  dateRendu?: string | null;
};

export type RestMateriel = RestOf<IMateriel>;

export type NewRestMateriel = RestOf<NewMateriel>;

export type PartialUpdateRestMateriel = RestOf<PartialUpdateMateriel>;

export type EntityResponseType = HttpResponse<IMateriel>;
export type EntityArrayResponseType = HttpResponse<IMateriel[]>;

@Injectable({ providedIn: 'root' })
export class MaterielService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/materiels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(materiel: NewMateriel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(materiel);
    return this.http
      .post<RestMateriel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(materiel: IMateriel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(materiel);
    return this.http
      .put<RestMateriel>(`${this.resourceUrl}/${this.getMaterielIdentifier(materiel)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(materiel: PartialUpdateMateriel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(materiel);
    return this.http
      .patch<RestMateriel>(`${this.resourceUrl}/${this.getMaterielIdentifier(materiel)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestMateriel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMateriel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMaterielIdentifier(materiel: Pick<IMateriel, 'id'>): string {
    return materiel.id;
  }

  compareMateriel(o1: Pick<IMateriel, 'id'> | null, o2: Pick<IMateriel, 'id'> | null): boolean {
    return o1 && o2 ? this.getMaterielIdentifier(o1) === this.getMaterielIdentifier(o2) : o1 === o2;
  }

  addMaterielToCollectionIfMissing<Type extends Pick<IMateriel, 'id'>>(
    materielCollection: Type[],
    ...materielsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const materiels: Type[] = materielsToCheck.filter(isPresent);
    if (materiels.length > 0) {
      const materielCollectionIdentifiers = materielCollection.map(materielItem => this.getMaterielIdentifier(materielItem)!);
      const materielsToAdd = materiels.filter(materielItem => {
        const materielIdentifier = this.getMaterielIdentifier(materielItem);
        if (materielCollectionIdentifiers.includes(materielIdentifier)) {
          return false;
        }
        materielCollectionIdentifiers.push(materielIdentifier);
        return true;
      });
      return [...materielsToAdd, ...materielCollection];
    }
    return materielCollection;
  }

  protected convertDateFromClient<T extends IMateriel | NewMateriel | PartialUpdateMateriel>(materiel: T): RestOf<T> {
    return {
      ...materiel,
      dateAttribution: materiel.dateAttribution?.format(DATE_FORMAT) ?? null,
      dateRendu: materiel.dateRendu?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMateriel: RestMateriel): IMateriel {
    return {
      ...restMateriel,
      dateAttribution: restMateriel.dateAttribution ? dayjs(restMateriel.dateAttribution) : undefined,
      dateRendu: restMateriel.dateRendu ? dayjs(restMateriel.dateRendu) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMateriel>): HttpResponse<IMateriel> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMateriel[]>): HttpResponse<IMateriel[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
