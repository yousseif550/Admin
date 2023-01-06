import dayjs from 'dayjs/esm';

import { Type } from 'app/entities/enumerations/type.model';

import { IMouvement, NewMouvement } from './mouvement.model';

export const sampleWithRequiredData: IMouvement = {
  id: '6754b761-6e22-4c83-848b-5ad0b217136f',
};

export const sampleWithPartialData: IMouvement = {
  id: 'd2408572-44dd-4bf4-9329-cfe5818e21d0',
  date: dayjs('2023-01-02'),
  type: Type['Demande'],
};

export const sampleWithFullData: IMouvement = {
  id: 'b4e62709-6f0e-4d8f-b2ad-ee662649a08f',
  date: dayjs('2023-01-01'),
  type: Type['Demande'],
  source: 'Rubber',
  destination: 'program monitor Brand',
  user: 'mobile Rufiyaa',
};

export const sampleWithNewData: NewMouvement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
