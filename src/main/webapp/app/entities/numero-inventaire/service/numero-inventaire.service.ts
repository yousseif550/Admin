import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INumeroInventaire, NewNumeroInventaire } from '../numero-inventaire.model';

export type PartialUpdateNumeroInventaire = Partial<INumeroInventaire> & Pick<INumeroInventaire, 'id'>;

type RestOf<T extends INumeroInventaire | NewNumeroInventaire> = Omit<T, 'dateModification'> & {
  dateModification?: string | null;
};

export type RestNumeroInventaire = RestOf<INumeroInventaire>;

export type NewRestNumeroInventaire = RestOf<NewNumeroInventaire>;

export type PartialUpdateRestNumeroInventaire = RestOf<PartialUpdateNumeroInventaire>;

export type EntityResponseType = HttpResponse<INumeroInventaire>;
export type EntityArrayResponseType = HttpResponse<INumeroInventaire[]>;

@Injectable({ providedIn: 'root' })
export class NumeroInventaireService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/numero-inventaires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(numeroInventaire: NewNumeroInventaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(numeroInventaire);
    return this.http
      .post<RestNumeroInventaire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(numeroInventaire: INumeroInventaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(numeroInventaire);
    return this.http
      .put<RestNumeroInventaire>(`${this.resourceUrl}/${this.getNumeroInventaireIdentifier(numeroInventaire)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(numeroInventaire: PartialUpdateNumeroInventaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(numeroInventaire);
    return this.http
      .patch<RestNumeroInventaire>(`${this.resourceUrl}/${this.getNumeroInventaireIdentifier(numeroInventaire)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestNumeroInventaire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNumeroInventaire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNumeroInventaireIdentifier(numeroInventaire: Pick<INumeroInventaire, 'id'>): string {
    return numeroInventaire.id;
  }

  compareNumeroInventaire(o1: Pick<INumeroInventaire, 'id'> | null, o2: Pick<INumeroInventaire, 'id'> | null): boolean {
    return o1 && o2 ? this.getNumeroInventaireIdentifier(o1) === this.getNumeroInventaireIdentifier(o2) : o1 === o2;
  }

  addNumeroInventaireToCollectionIfMissing<Type extends Pick<INumeroInventaire, 'id'>>(
    numeroInventaireCollection: Type[],
    ...numeroInventairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const numeroInventaires: Type[] = numeroInventairesToCheck.filter(isPresent);
    if (numeroInventaires.length > 0) {
      const numeroInventaireCollectionIdentifiers = numeroInventaireCollection.map(
        numeroInventaireItem => this.getNumeroInventaireIdentifier(numeroInventaireItem)!
      );
      const numeroInventairesToAdd = numeroInventaires.filter(numeroInventaireItem => {
        const numeroInventaireIdentifier = this.getNumeroInventaireIdentifier(numeroInventaireItem);
        if (numeroInventaireCollectionIdentifiers.includes(numeroInventaireIdentifier)) {
          return false;
        }
        numeroInventaireCollectionIdentifiers.push(numeroInventaireIdentifier);
        return true;
      });
      return [...numeroInventairesToAdd, ...numeroInventaireCollection];
    }
    return numeroInventaireCollection;
  }

  protected convertDateFromClient<T extends INumeroInventaire | NewNumeroInventaire | PartialUpdateNumeroInventaire>(
    numeroInventaire: T
  ): RestOf<T> {
    return {
      ...numeroInventaire,
      dateModification: numeroInventaire.dateModification?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restNumeroInventaire: RestNumeroInventaire): INumeroInventaire {
    return {
      ...restNumeroInventaire,
      dateModification: restNumeroInventaire.dateModification ? dayjs(restNumeroInventaire.dateModification) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNumeroInventaire>): HttpResponse<INumeroInventaire> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNumeroInventaire[]>): HttpResponse<INumeroInventaire[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
