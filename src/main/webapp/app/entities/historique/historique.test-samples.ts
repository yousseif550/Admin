import dayjs from 'dayjs/esm';

import { IHistorique, NewHistorique } from './historique.model';

export const sampleWithRequiredData: IHistorique = {
  id: 'ec8dad37-712f-4ee0-9d90-1ee4890f0e87',
};

export const sampleWithPartialData: IHistorique = {
  id: '7cac86a1-ca71-492d-85c5-ec6699d21b09',
  pc: 'SDD',
  zone: 'Unbranded Tuvalu',
  dateMouvement: dayjs('2023-01-02'),
};

export const sampleWithFullData: IHistorique = {
  id: '7d4ab86a-341e-4c3c-8253-89e3c7e44023',
  pc: 'Jewelery impactful Credit',
  zone: 'solutions',
  dateMouvement: dayjs('2023-01-02'),
};

export const sampleWithNewData: NewHistorique = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
