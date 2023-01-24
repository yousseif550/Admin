import dayjs from 'dayjs/esm';

import { INumeroInventaire, NewNumeroInventaire } from './numero-inventaire.model';

export const sampleWithRequiredData: INumeroInventaire = {
  id: 'e017a2c7-502d-43b9-9eab-e28aaba202f3',
};

export const sampleWithPartialData: INumeroInventaire = {
  id: '18a99bdc-439b-4ee6-9cdf-a12adb7d9f29',
  type: 'invoice',
  disponible: true,
  ancienMateriel: 'cross-platform',
};

export const sampleWithFullData: INumeroInventaire = {
  id: '9c6ee3ca-e8df-4d00-af08-4821ceaa12b0',
  type: 'networks',
  disponible: false,
  ancienMateriel: 'Valley',
  dateModification: dayjs('2023-01-02'),
  commentaire: 'up SMTP Forward',
};

export const sampleWithNewData: NewNumeroInventaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
