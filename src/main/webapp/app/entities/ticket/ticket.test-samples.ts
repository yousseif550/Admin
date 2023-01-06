import dayjs from 'dayjs/esm';

import { Etat } from 'app/entities/enumerations/etat.model';

import { ITicket, NewTicket } from './ticket.model';

export const sampleWithRequiredData: ITicket = {
  id: 'ac17e310-01cb-4f72-98a0-34cce4a0030e',
};

export const sampleWithPartialData: ITicket = {
  id: '8f0d38f1-2ded-425d-b508-5e70723a9f50',
  type: 'empower Guyana',
  statut: Etat['EnCours'],
  dateCreation: dayjs('2023-01-02'),
  dateFin: dayjs('2023-01-02'),
};

export const sampleWithFullData: ITicket = {
  id: 'e526d16e-6a73-4225-bc16-d6858c2d91c9',
  type: 'HDD Pizza',
  statut: Etat['Envoyer'],
  dateCreation: dayjs('2023-01-02'),
  dateFin: dayjs('2023-01-01'),
};

export const sampleWithNewData: NewTicket = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
