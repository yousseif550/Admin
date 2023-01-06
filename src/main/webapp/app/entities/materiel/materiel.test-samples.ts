import dayjs from 'dayjs/esm';

import { IMateriel, NewMateriel } from './materiel.model';

export const sampleWithRequiredData: IMateriel = {
  id: 'ef1dc0c2-70d2-4ddc-b553-527c15f08a50',
};

export const sampleWithPartialData: IMateriel = {
  id: '156bfede-941e-4162-9ef8-ac350afd538d',
  modele: 'THX Israeli markets',
  asset: 'optical Global network',
  dateRendu: dayjs('2023-01-01'),
};

export const sampleWithFullData: IMateriel = {
  id: 'e421298e-a5c4-4ba3-9874-87764d289a35',
  type: 'Jewelery',
  modele: 'Regional',
  asset: 'embrace Unbranded Brook',
  commentaire: 'strategize',
  actif: false,
  dateAttribution: dayjs('2023-01-02'),
  dateRendu: dayjs('2023-01-02'),
  isHS: false,
};

export const sampleWithNewData: NewMateriel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
