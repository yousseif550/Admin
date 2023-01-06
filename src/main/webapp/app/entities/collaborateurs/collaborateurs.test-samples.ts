import dayjs from 'dayjs/esm';

import { ICollaborateurs, NewCollaborateurs } from './collaborateurs.model';

export const sampleWithRequiredData: ICollaborateurs = {
  id: 'f6bdc9f0-c1a4-4ad9-aee9-085f0be47eb8',
};

export const sampleWithPartialData: ICollaborateurs = {
  id: 'a625f119-48bc-48eb-b7b0-4a26418d41af',
  identifiant: 91960,
  prestataire: true,
};

export const sampleWithFullData: ICollaborateurs = {
  id: 'cef0cc97-fa78-4e39-813e-9f2c679810fe',
  nom: 'Rubber communities Maryland',
  identifiant: 76422,
  tel: 88156,
  prestataire: true,
  isActif: false,
  dateEntree: dayjs('2023-01-02'),
  dateSortie: dayjs('2023-01-02'),
};

export const sampleWithNewData: NewCollaborateurs = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
