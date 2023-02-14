import dayjs from 'dayjs/esm';

import { Etat } from 'app/entities/enumerations/etat.model';

import { IExtracDMOCSS, NewExtracDMOCSS } from './extrac-dmocss.model';

export const sampleWithRequiredData: IExtracDMOCSS = {
  id: '19833ca3-09b3-4048-afed-e77b6a228472',
};

export const sampleWithPartialData: IExtracDMOCSS = {
  id: '6004f8b1-e5e9-4bcc-8c85-998a1ed0ce81',
  adressePhysiqueDGFiP: 'Rustic grey parse',
  ipVpnIPSEC: 'Loan',
  statut: Etat['Reatribution'],
  numVersion: 'Salad',
};

export const sampleWithFullData: IExtracDMOCSS = {
  id: '7be7403f-38b3-4eb8-8649-1ff4a845574c',
  adressePhysiqueDGFiP: 'Loan Extension',
  date: dayjs('2023-01-02'),
  ipPcDgfip: 'pink maximize Fresh',
  ipVpnIPSEC: 'optical lavender',
  ioTeletravail: 'Hills Colombia Concrete',
  statut: Etat['OK'],
  numVersion: 'Licensed',
};

export const sampleWithNewData: NewExtracDMOCSS = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
