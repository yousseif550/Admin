import dayjs from 'dayjs/esm';

import { ICollaborateurs, NewCollaborateurs } from './collaborateurs.model';

export const sampleWithRequiredData: ICollaborateurs = {
  id: 'f6bdc9f0-c1a4-4ad9-aee9-085f0be47eb8',
};

export const sampleWithPartialData: ICollaborateurs = {
  id: 'a625f119-48bc-48eb-b7b0-4a26418d41af',
  identifiant: 'Grenadines Supervisor multi-byte',
  prestataire: true,
};

export const sampleWithFullData: ICollaborateurs = {
  id: 'a78e39c1-3e9f-42c6-b981-0fed19c63f96',
  nom: 'program Cotton',
  identifiant: 'override Mouse',
  tel: 62376,
  prestataire: false,
  isActif: false,
  dateEntree: dayjs('2023-01-01'),
  dateSortie: dayjs('2023-01-02'),
};

export const sampleWithNewData: NewCollaborateurs = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
