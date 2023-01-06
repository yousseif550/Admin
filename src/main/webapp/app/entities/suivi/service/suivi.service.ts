import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISuivi, NewSuivi } from '../suivi.model';

export type PartialUpdateSuivi = Partial<ISuivi> & Pick<ISuivi, 'id'>;

export type EntityResponseType = HttpResponse<ISuivi>;
export type EntityArrayResponseType = HttpResponse<ISuivi[]>;

@Injectable({ providedIn: 'root' })
export class SuiviService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/suivis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(suivi: NewSuivi): Observable<EntityResponseType> {
    return this.http.post<ISuivi>(this.resourceUrl, suivi, { observe: 'response' });
  }

  update(suivi: ISuivi): Observable<EntityResponseType> {
    return this.http.put<ISuivi>(`${this.resourceUrl}/${this.getSuiviIdentifier(suivi)}`, suivi, { observe: 'response' });
  }

  partialUpdate(suivi: PartialUpdateSuivi): Observable<EntityResponseType> {
    return this.http.patch<ISuivi>(`${this.resourceUrl}/${this.getSuiviIdentifier(suivi)}`, suivi, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISuivi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuivi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSuiviIdentifier(suivi: Pick<ISuivi, 'id'>): string {
    return suivi.id;
  }

  compareSuivi(o1: Pick<ISuivi, 'id'> | null, o2: Pick<ISuivi, 'id'> | null): boolean {
    return o1 && o2 ? this.getSuiviIdentifier(o1) === this.getSuiviIdentifier(o2) : o1 === o2;
  }

  addSuiviToCollectionIfMissing<Type extends Pick<ISuivi, 'id'>>(
    suiviCollection: Type[],
    ...suivisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const suivis: Type[] = suivisToCheck.filter(isPresent);
    if (suivis.length > 0) {
      const suiviCollectionIdentifiers = suiviCollection.map(suiviItem => this.getSuiviIdentifier(suiviItem)!);
      const suivisToAdd = suivis.filter(suiviItem => {
        const suiviIdentifier = this.getSuiviIdentifier(suiviItem);
        if (suiviCollectionIdentifiers.includes(suiviIdentifier)) {
          return false;
        }
        suiviCollectionIdentifiers.push(suiviIdentifier);
        return true;
      });
      return [...suivisToAdd, ...suiviCollection];
    }
    return suiviCollection;
  }
}
