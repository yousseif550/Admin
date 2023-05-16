import dayjs from 'dayjs/esm';

import { IMateriel, NewMateriel } from './materiel.model';

export const sampleWithRequiredData: IMateriel = {
  id: 'ef1dc0c2-70d2-4ddc-b553-527c15f08a50',
};

export const sampleWithPartialData: IMateriel = {
  id: 'e941e162-9ef8-4ac3-90af-d538deeada56',
  modele: 'Rustic optical',
  asset: 'Buckinghamshire Vermont Future',
  isHs: false,
  iPTeletravail: 'Account',
  bios: 'unleash alarm',
  majBios: true,
  commentaire: 'Bedfordshire Berkshire',
};

export const sampleWithFullData: IMateriel = {
  id: '4d289a35-10ce-44e6-ad51-91e270206617',
  utilisation: 'Berkshire',
  modele: 'withdrawal',
  asset: 'Netherlands Cotton',
  dateAttribution: dayjs('2023-01-02'),
  dateRendu: dayjs('2023-01-02'),
  actif: true,
  isHs: false,
  cleAntiVol: 'discrete Account capacitor',
  adressMAC: 'Principal Sleek',
  stationDgfip: 'transmit sky Uruguay',
  ipdfip: 'Finland Concrete',
  iPTeletravail: 'feed',
  bios: 'Pants',
  majBios: true,
  commentaire: 'Metal Philippine',
};

export const sampleWithNewData: NewMateriel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
