import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypemateriel, NewTypemateriel } from '../typemateriel.model';

export type PartialUpdateTypemateriel = Partial<ITypemateriel> & Pick<ITypemateriel, 'id'>;

export type EntityResponseType = HttpResponse<ITypemateriel>;
export type EntityArrayResponseType = HttpResponse<ITypemateriel[]>;

@Injectable({ providedIn: 'root' })
export class TypematerielService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/typemateriels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typemateriel: NewTypemateriel): Observable<EntityResponseType> {
    return this.http.post<ITypemateriel>(this.resourceUrl, typemateriel, { observe: 'response' });
  }

  update(typemateriel: ITypemateriel): Observable<EntityResponseType> {
    return this.http.put<ITypemateriel>(`${this.resourceUrl}/${this.getTypematerielIdentifier(typemateriel)}`, typemateriel, {
      observe: 'response',
    });
  }

  partialUpdate(typemateriel: PartialUpdateTypemateriel): Observable<EntityResponseType> {
    return this.http.patch<ITypemateriel>(`${this.resourceUrl}/${this.getTypematerielIdentifier(typemateriel)}`, typemateriel, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITypemateriel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypemateriel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTypematerielIdentifier(typemateriel: Pick<ITypemateriel, 'id'>): string {
    return typemateriel.id;
  }

  compareTypemateriel(o1: Pick<ITypemateriel, 'id'> | null, o2: Pick<ITypemateriel, 'id'> | null): boolean {
    return o1 && o2 ? this.getTypematerielIdentifier(o1) === this.getTypematerielIdentifier(o2) : o1 === o2;
  }

  addTypematerielToCollectionIfMissing<Type extends Pick<ITypemateriel, 'id'>>(
    typematerielCollection: Type[],
    ...typematerielsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const typemateriels: Type[] = typematerielsToCheck.filter(isPresent);
    if (typemateriels.length > 0) {
      const typematerielCollectionIdentifiers = typematerielCollection.map(
        typematerielItem => this.getTypematerielIdentifier(typematerielItem)!
      );
      const typematerielsToAdd = typemateriels.filter(typematerielItem => {
        const typematerielIdentifier = this.getTypematerielIdentifier(typematerielItem);
        if (typematerielCollectionIdentifiers.includes(typematerielIdentifier)) {
          return false;
        }
        typematerielCollectionIdentifiers.push(typematerielIdentifier);
        return true;
      });
      return [...typematerielsToAdd, ...typematerielCollection];
    }
    return typematerielCollection;
  }
}
