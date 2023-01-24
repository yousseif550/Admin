import dayjs from 'dayjs/esm';

import { IMouvement, NewMouvement } from './mouvement.model';

export const sampleWithRequiredData: IMouvement = {
  id: '6754b761-6e22-4c83-848b-5ad0b217136f',
};

export const sampleWithPartialData: IMouvement = {
  id: '24085724-4ddb-4f49-b29c-fe5818e21d01',
  date: dayjs('2023-01-02'),
  type: 'transitional Shoes Michigan',
  commentaire: 'Configuration',
};

export const sampleWithFullData: IMouvement = {
  id: 'f72adee6-6264-49a0-8fe2-029fccdebb5f',
  date: dayjs('2023-01-02'),
  type: 'Account synthesize Loan',
  source: 'Functionality',
  destination: 'Saint Accountability implement',
  user: 'action-items',
  commentaire: 'compressing',
};

export const sampleWithNewData: NewMouvement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
