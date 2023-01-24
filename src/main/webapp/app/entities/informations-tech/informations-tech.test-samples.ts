import { IInformationsTech, NewInformationsTech } from './informations-tech.model';

export const sampleWithRequiredData: IInformationsTech = {
  id: '57a0181d-f867-4f81-a2ff-ab73f6ed25d5',
};

export const sampleWithPartialData: IInformationsTech = {
  id: '9cd2ee01-e8d1-45ab-814a-12c892eb85b4',
  ipfixDMOCSS: 'Buckinghamshire',
  iPTeletravail: 'payment Escudo IB',
};

export const sampleWithFullData: IInformationsTech = {
  id: '07838f3a-6fd8-4663-b987-999f97963000',
  ipdfipConnexion: 'incubate Dollar Practical',
  ipfixDMOCSS: 'Electronics compressing Home',
  adressMAC: 'workforce',
  iPTeletravail: 'turquoise portal withdrawal',
  adresseDGFiP: 'Applications Tasty Lebanon',
};

export const sampleWithNewData: NewInformationsTech = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
