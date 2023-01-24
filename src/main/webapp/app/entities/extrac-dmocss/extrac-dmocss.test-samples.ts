import dayjs from 'dayjs/esm';

import { Etat } from 'app/entities/enumerations/etat.model';

import { IExtracDMOCSS, NewExtracDMOCSS } from './extrac-dmocss.model';

export const sampleWithRequiredData: IExtracDMOCSS = {
  id: '19833ca3-09b3-4048-afed-e77b6a228472',
};

export const sampleWithPartialData: IExtracDMOCSS = {
  id: '04f8b1e5-e9bc-4ccc-8599-8a1ed0ce81b1',
  adressePhysiqueDGFiP: 'cohesive',
  date: dayjs('2023-01-01'),
  ipVpnIPSEC: 'Markets Loan HTTP',
  ioTeletravail: 'Architect Applications Frozen',
};

export const sampleWithFullData: IExtracDMOCSS = {
  id: 'b3eb8864-91ff-44a8-8557-4c9ac57357e3',
  adressePhysiqueDGFiP: 'navigating Creek',
  bureauActuel: 'Buckinghamshire sticky',
  bureauDeplacement: 'indexing generate Rustic',
  date: dayjs('2023-01-02'),
  ipPcDgfip: 'auxiliary',
  ipVpnIPSEC: 'rich',
  ioTeletravail: 'Tasty Optimization invoice',
  statut: Etat['EnCours'],
  numVersion: 'generating Assurance',
};

export const sampleWithNewData: NewExtracDMOCSS = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
