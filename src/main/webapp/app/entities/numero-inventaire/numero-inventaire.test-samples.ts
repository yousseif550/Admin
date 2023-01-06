import dayjs from 'dayjs/esm';

import { INumeroInventaire, NewNumeroInventaire } from './numero-inventaire.model';

export const sampleWithRequiredData: INumeroInventaire = {
  id: 'e017a2c7-502d-43b9-9eab-e28aaba202f3',
};

export const sampleWithPartialData: INumeroInventaire = {
  id: '18a99bdc-439b-4ee6-9cdf-a12adb7d9f29',
  zone: 'invoice',
  valeur: 87803,
  disponible: false,
};

export const sampleWithFullData: INumeroInventaire = {
  id: 'c579c6ee-3cae-48df-900e-f084821ceaa1',
  zone: 'driver',
  valeur: 42096,
  disponible: true,
  ancienMateriel: 'Sausages protocol',
  dateModification: dayjs('2023-01-02'),
};

export const sampleWithNewData: NewNumeroInventaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
