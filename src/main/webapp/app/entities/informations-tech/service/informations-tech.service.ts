import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInformationsTech, NewInformationsTech } from '../informations-tech.model';

export type PartialUpdateInformationsTech = Partial<IInformationsTech> & Pick<IInformationsTech, 'id'>;

export type EntityResponseType = HttpResponse<IInformationsTech>;
export type EntityArrayResponseType = HttpResponse<IInformationsTech[]>;

@Injectable({ providedIn: 'root' })
export class InformationsTechService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/informations-teches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(informationsTech: NewInformationsTech): Observable<EntityResponseType> {
    return this.http.post<IInformationsTech>(this.resourceUrl, informationsTech, { observe: 'response' });
  }

  update(informationsTech: IInformationsTech): Observable<EntityResponseType> {
    return this.http.put<IInformationsTech>(
      `${this.resourceUrl}/${this.getInformationsTechIdentifier(informationsTech)}`,
      informationsTech,
      { observe: 'response' }
    );
  }

  partialUpdate(informationsTech: PartialUpdateInformationsTech): Observable<EntityResponseType> {
    return this.http.patch<IInformationsTech>(
      `${this.resourceUrl}/${this.getInformationsTechIdentifier(informationsTech)}`,
      informationsTech,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IInformationsTech>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInformationsTech[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInformationsTechIdentifier(informationsTech: Pick<IInformationsTech, 'id'>): string {
    return informationsTech.id;
  }

  compareInformationsTech(o1: Pick<IInformationsTech, 'id'> | null, o2: Pick<IInformationsTech, 'id'> | null): boolean {
    return o1 && o2 ? this.getInformationsTechIdentifier(o1) === this.getInformationsTechIdentifier(o2) : o1 === o2;
  }

  addInformationsTechToCollectionIfMissing<Type extends Pick<IInformationsTech, 'id'>>(
    informationsTechCollection: Type[],
    ...informationsTechesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const informationsTeches: Type[] = informationsTechesToCheck.filter(isPresent);
    if (informationsTeches.length > 0) {
      const informationsTechCollectionIdentifiers = informationsTechCollection.map(
        informationsTechItem => this.getInformationsTechIdentifier(informationsTechItem)!
      );
      const informationsTechesToAdd = informationsTeches.filter(informationsTechItem => {
        const informationsTechIdentifier = this.getInformationsTechIdentifier(informationsTechItem);
        if (informationsTechCollectionIdentifiers.includes(informationsTechIdentifier)) {
          return false;
        }
        informationsTechCollectionIdentifiers.push(informationsTechIdentifier);
        return true;
      });
      return [...informationsTechesToAdd, ...informationsTechCollection];
    }
    return informationsTechCollection;
  }
}
