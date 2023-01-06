import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILocalisation, NewLocalisation } from '../localisation.model';

export type PartialUpdateLocalisation = Partial<ILocalisation> & Pick<ILocalisation, 'id'>;

export type EntityResponseType = HttpResponse<ILocalisation>;
export type EntityArrayResponseType = HttpResponse<ILocalisation[]>;

@Injectable({ providedIn: 'root' })
export class LocalisationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/localisations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(localisation: NewLocalisation): Observable<EntityResponseType> {
    return this.http.post<ILocalisation>(this.resourceUrl, localisation, { observe: 'response' });
  }

  update(localisation: ILocalisation): Observable<EntityResponseType> {
    return this.http.put<ILocalisation>(`${this.resourceUrl}/${this.getLocalisationIdentifier(localisation)}`, localisation, {
      observe: 'response',
    });
  }

  partialUpdate(localisation: PartialUpdateLocalisation): Observable<EntityResponseType> {
    return this.http.patch<ILocalisation>(`${this.resourceUrl}/${this.getLocalisationIdentifier(localisation)}`, localisation, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ILocalisation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalisation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLocalisationIdentifier(localisation: Pick<ILocalisation, 'id'>): string {
    return localisation.id;
  }

  compareLocalisation(o1: Pick<ILocalisation, 'id'> | null, o2: Pick<ILocalisation, 'id'> | null): boolean {
    return o1 && o2 ? this.getLocalisationIdentifier(o1) === this.getLocalisationIdentifier(o2) : o1 === o2;
  }

  addLocalisationToCollectionIfMissing<Type extends Pick<ILocalisation, 'id'>>(
    localisationCollection: Type[],
    ...localisationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const localisations: Type[] = localisationsToCheck.filter(isPresent);
    if (localisations.length > 0) {
      const localisationCollectionIdentifiers = localisationCollection.map(
        localisationItem => this.getLocalisationIdentifier(localisationItem)!
      );
      const localisationsToAdd = localisations.filter(localisationItem => {
        const localisationIdentifier = this.getLocalisationIdentifier(localisationItem);
        if (localisationCollectionIdentifiers.includes(localisationIdentifier)) {
          return false;
        }
        localisationCollectionIdentifiers.push(localisationIdentifier);
        return true;
      });
      return [...localisationsToAdd, ...localisationCollection];
    }
    return localisationCollection;
  }
}
