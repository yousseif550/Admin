import dayjs from 'dayjs/esm';

import { INumeroInventaire, NewNumeroInventaire } from './numero-inventaire.model';

export const sampleWithRequiredData: INumeroInventaire = {
  id: 'e017a2c7-502d-43b9-9eab-e28aaba202f3',
};

export const sampleWithPartialData: INumeroInventaire = {
  id: '5618a99b-dc43-49be-a61c-dfa12adb7d9f',
  disponible: false,
  dateModification: dayjs('2023-01-01'),
  commentaire: 'invoice',
};

export const sampleWithFullData: INumeroInventaire = {
  id: 'e1c579c6-ee3c-4ae8-9fd0-0ef084821cea',
  disponible: true,
  dateModification: dayjs('2023-01-02'),
  commentaire: 'driver',
};

export const sampleWithNewData: NewNumeroInventaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
