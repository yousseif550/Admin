import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICollaborateurs, NewCollaborateurs } from '../collaborateurs.model';

import { IMateriel } from 'app/entities/materiel/materiel.model';
export type PartialUpdateCollaborateurs = Partial<ICollaborateurs> & Pick<ICollaborateurs, 'id'>;

type RestOf<T extends ICollaborateurs | NewCollaborateurs> = Omit<T, 'dateEntree' | 'dateSortie'> & {
  dateEntree?: string | null;
  dateSortie?: string | null;
};

export type RestCollaborateurs = RestOf<ICollaborateurs>;

export type NewRestCollaborateurs = RestOf<NewCollaborateurs>;

export type PartialUpdateRestCollaborateurs = RestOf<PartialUpdateCollaborateurs>;

export type EntityResponseType = HttpResponse<ICollaborateurs>;
export type EntityArrayResponseType = HttpResponse<ICollaborateurs[]>;

@Injectable({ providedIn: 'root' })
export class CollaborateursService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/collaborateurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getMaterielsForCollaborateur(id: string): Observable<HttpResponse<IMateriel[]>> {
    return this.http.get<IMateriel[]>(`api/materiels`, { observe: 'response' });
  }

  create(collaborateurs: NewCollaborateurs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateurs);
    return this.http
      .post<RestCollaborateurs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(collaborateurs: ICollaborateurs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateurs);
    return this.http
      .put<RestCollaborateurs>(`${this.resourceUrl}/${this.getCollaborateursIdentifier(collaborateurs)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(collaborateurs: PartialUpdateCollaborateurs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateurs);
    return this.http
      .patch<RestCollaborateurs>(`${this.resourceUrl}/${this.getCollaborateursIdentifier(collaborateurs)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCollaborateurs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCollaborateurs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCollaborateursIdentifier(collaborateurs: Pick<ICollaborateurs, 'id'>): string {
    return collaborateurs.id;
  }

  compareCollaborateurs(o1: Pick<ICollaborateurs, 'id'> | null, o2: Pick<ICollaborateurs, 'id'> | null): boolean {
    return o1 && o2 ? this.getCollaborateursIdentifier(o1) === this.getCollaborateursIdentifier(o2) : o1 === o2;
  }

  addCollaborateursToCollectionIfMissing<Type extends Pick<ICollaborateurs, 'id'>>(
    collaborateursCollection: Type[],
    ...collaborateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const collaborateurs: Type[] = collaborateursToCheck.filter(isPresent);
    if (collaborateurs.length > 0) {
      const collaborateursCollectionIdentifiers = collaborateursCollection.map(
        collaborateursItem => this.getCollaborateursIdentifier(collaborateursItem)!
      );
      const collaborateursToAdd = collaborateurs.filter(collaborateursItem => {
        const collaborateursIdentifier = this.getCollaborateursIdentifier(collaborateursItem);
        if (collaborateursCollectionIdentifiers.includes(collaborateursIdentifier)) {
          return false;
        }
        collaborateursCollectionIdentifiers.push(collaborateursIdentifier);
        return true;
      });
      return [...collaborateursToAdd, ...collaborateursCollection];
    }
    return collaborateursCollection;
  }

  protected convertDateFromClient<T extends ICollaborateurs | NewCollaborateurs | PartialUpdateCollaborateurs>(
    collaborateurs: T
  ): RestOf<T> {
    return {
      ...collaborateurs,
      dateEntree: collaborateurs.dateEntree?.format(DATE_FORMAT) ?? null,
      dateSortie: collaborateurs.dateSortie?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCollaborateurs: RestCollaborateurs): ICollaborateurs {
    return {
      ...restCollaborateurs,
      dateEntree: restCollaborateurs.dateEntree ? dayjs(restCollaborateurs.dateEntree) : undefined,
      dateSortie: restCollaborateurs.dateSortie ? dayjs(restCollaborateurs.dateSortie) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCollaborateurs>): HttpResponse<ICollaborateurs> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCollaborateurs[]>): HttpResponse<ICollaborateurs[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
