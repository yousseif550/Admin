import dayjs from 'dayjs/esm';

import { Etat } from 'app/entities/enumerations/etat.model';

import { IExtracDMOCSS, NewExtracDMOCSS } from './extrac-dmocss.model';

export const sampleWithRequiredData: IExtracDMOCSS = {
  id: '19833ca3-09b3-4048-afed-e77b6a228472',
};

export const sampleWithPartialData: IExtracDMOCSS = {
  id: '004f8b1e-5e9b-4ccc-8859-98a1ed0ce81b',
  adressePhysiqueDGFiP: 'Concrete',
  ipPcDgfip: '1080p utilize',
  ipTeletravail: 'e-business Salad models',
  statut: Etat['EnCours'],
};

export const sampleWithFullData: IExtracDMOCSS = {
  id: '403f38b3-eb88-4649-9ff4-a845574c9ac5',
  adressePhysiqueDGFiP: 'grey Implementation',
  date: dayjs('2023-01-01'),
  bureauDeplacement: 'Investment AI',
  ipPcDgfip: 'PCI Creative Hills',
  ipVpnIPSEC: 'green Open-source',
  ipTeletravail: 'Licensed',
  statut: Etat['NonRealiser'],
  numVersion: 'rich',
};

export const sampleWithNewData: NewExtracDMOCSS = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
