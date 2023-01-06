import dayjs from 'dayjs/esm';

import { IExtracDMOCSS, NewExtracDMOCSS } from './extrac-dmocss.model';

export const sampleWithRequiredData: IExtracDMOCSS = {
  id: '19833ca3-09b3-4048-afed-e77b6a228472',
};

export const sampleWithPartialData: IExtracDMOCSS = {
  id: '96004f8b-1e5e-49bc-8cc8-5998a1ed0ce8',
  adressePhysiqueDGFiP: 'Dollar',
  date: dayjs('2023-01-02'),
  ipVpnIPSEC: 'extend',
};

export const sampleWithFullData: IExtracDMOCSS = {
  id: 'f609d6e0-2ef7-4be7-803f-38b3eb886491',
  adressePhysiqueDGFiP: 'Paradigm Southern interactive',
  bureauActuel: 'York brand bleeding-edge',
  bureauDeplacement: 'transparent',
  date: dayjs('2023-01-01'),
  ipPcDGFiP: 'Fresh Buckinghamshire',
  ipVpnIPSEC: 'lavender indexing',
};

export const sampleWithNewData: NewExtracDMOCSS = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
