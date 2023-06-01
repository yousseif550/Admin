import dayjs from 'dayjs/esm';

import { ICollaborateurs, NewCollaborateurs } from './collaborateurs.model';

export const sampleWithRequiredData: ICollaborateurs = {
  id: 'f6bdc9f0-c1a4-4ad9-aee9-085f0be47eb8',
};

export const sampleWithPartialData: ICollaborateurs = {
  id: '625f1194-8bc8-4eb3-bb04-a26418d41afe',
  societe: 'reboot Jewelery',
  tel: 57879,
  dateSortie: dayjs('2023-01-02'),
};

export const sampleWithFullData: ICollaborateurs = {
  id: 'fa78e39c-13e9-4f2c-a798-10fed19c63f9',
  nom: 'invoice Barbados',
  societe: 'Planner override',
  email: 'red',
  tel: 26445,
  prestataire: false,
  isActif: true,
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
