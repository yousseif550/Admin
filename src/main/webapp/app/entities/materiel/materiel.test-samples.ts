import dayjs from 'dayjs/esm';

import { IMateriel, NewMateriel } from './materiel.model';

export const sampleWithRequiredData: IMateriel = {
  id: 'ef1dc0c2-70d2-4ddc-b553-527c15f08a50',
};

export const sampleWithPartialData: IMateriel = {
  id: '156bfede-941e-4162-9ef8-ac350afd538d',
  modele: 'THX Israeli markets',
  asset: 'optical Global network',
  commentaire: 'Future Towels Account',
};

export const sampleWithFullData: IMateriel = {
  id: '4ba39874-8776-44d2-89a3-510ce4e6ad51',
  utilisation: 'Unbranded Brook',
  modele: 'strategize',
  asset: 'Sleek Indiana',
  actif: false,
  dateAttribution: dayjs('2023-01-01'),
  dateRendu: dayjs('2023-01-02'),
  commentaire: 'Checking array',
  isHs: true,
};

export const sampleWithNewData: NewMateriel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
